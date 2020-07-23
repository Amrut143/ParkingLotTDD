package com.bridgelabz.parkinglot.service;

import com.bridgelabz.parkinglot.exception.ParkingLotException;
import com.bridgelabz.parkinglot.model.ParkingSlot;
import com.bridgelabz.parkinglot.utility.ParkingTime;
import com.bridgelabz.parkinglot.utility.SlotAllotment;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class ParkingLot {

    public SlotAllotment slotAllotment;
    private ArrayList<ParkingSlot> parkingSlots;
    private ParkingTime parkingTime;
    private int parkingLotCapacity;
    private boolean parkingCapacityFull;
    private int numberOfVehicles = 0;

    public ParkingLot(int parkingLotCapacity) {
        this.parkingSlots = new ArrayList(parkingLotCapacity);
        this.slotAllotment = new SlotAllotment(parkingLotCapacity);
        this.setInitialValuesToSlots(parkingLotCapacity);
        this.parkingLotCapacity = parkingLotCapacity;
        this.parkingTime = new ParkingTime();
    }

    private void setInitialValuesToSlots(int parkingLotCapacity) {
        IntStream.range(0, parkingLotCapacity)
                .forEach(index -> this.parkingSlots.add(index, null));
    }

    public List getAvailableSlots() {
        return this.slotAllotment.getAvailableSlotsList();
    }

    public int getParkingCapacity() {
        return parkingLotCapacity;
    }

    public LocalDateTime getVehicleTimingDetails(Object vehicle) {
        ParkingSlot parkingSlot = new ParkingSlot(vehicle);
        ParkingSlot time = this.parkingSlots.get(this.parkingSlots.indexOf(parkingSlot));
        return time.getParkingStartTime();
    }

    public void parkVehicle(Object vehicle) throws ParkingLotException {
        int slot = this.getSlotToParkVehicle();
        this.parkVehicleAtSpecifiedSlot(slot, vehicle);
    }

    public void parkVehicleAtSpecifiedSlot(int slotNumber, Object vehicle) throws ParkingLotException {
        if (this.vehicleAlreadyPresent(vehicle)) {
            throw new ParkingLotException("No such car present in parking lot!",
                    ParkingLotException.ExceptionType.CAR_ALREADY_PARKED);
        }
        this.parkAtSlot(slotNumber, vehicle);
    }

    public boolean vehicleAlreadyPresent(Object vehicle) {
        int vehiclePresent = this.findSlotOfThisVehicle(vehicle);
        if (vehiclePresent == -1) {
            return false;
        }
        return true;
    }

    private int getSlotToParkVehicle() throws ParkingLotException {
        try {
            return slotAllotment.getNearestParkingSlot();
        } catch (ParkingLotException e) {
            this.parkingCapacityFull = true;
            throw e;
        }
    }

    private void parkAtSlot(int slotNumber, Object vehicle) {
        ParkingSlot parkingSlot = new ParkingSlot(vehicle, this.parkingTime.getCurrentTime());
        this.parkingSlots.set(slotNumber - 1, parkingSlot);
        this.slotAllotment.parkUpdate(slotNumber);
        this.numberOfVehicles++;
    }

    /**
     * @param vehicle
     * @return
     */
    public void unParkVehicle(Object vehicle) throws ParkingLotException {
        int vehiclePresent = this.findSlotOfThisVehicle(vehicle);
        if (vehiclePresent == -1) {
            throw new ParkingLotException("No such car present in parking lot!",
                    ParkingLotException.ExceptionType.NO_SUCH_CAR_PARKED);
        }
        this.parkingSlots.set(vehiclePresent, null);
        this.slotAllotment.unParkUpdate(vehiclePresent + 1);
        this.numberOfVehicles--;
    }

    public int findSlotOfThisVehicle(Object vehicle) {
        ParkingSlot parkingSlot = new ParkingSlot(vehicle);
        return IntStream.range(0, this.parkingSlots.size())
                .filter(index -> parkingSlot.equals(this.parkingSlots.get(index)))
                .findFirst()
                .orElse(-1);
    }

    public int getNumberOfVehiclesParked() {
        return this.numberOfVehicles;
    }
}
