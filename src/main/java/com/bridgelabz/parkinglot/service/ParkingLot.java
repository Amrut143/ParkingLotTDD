package com.bridgelabz.parkinglot.service;

import com.bridgelabz.parkinglot.exception.ParkingLotException;
import com.bridgelabz.parkinglot.model.Slot;
import com.bridgelabz.parkinglot.utility.ParkingTime;
import com.bridgelabz.parkinglot.utility.SlotAllotment;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class ParkingLot {

    public SlotAllotment slotAllotment;
    private ParkingTime parkingTime;
    private ArrayList<Slot> parkingSlots;
    private int parkingLotCapacity;
    private boolean parkingCapacityFull;
    private int numberOfVehicles = 0;

    public ParkingLot(int parkingLotCapacity) {
        this.parkingSlots = new ArrayList(parkingLotCapacity);
        this.slotAllotment = new SlotAllotment(parkingLotCapacity);
        this.setInitialValuesToSlots(parkingLotCapacity);
    }


    public ParkingLot(int parkingLotCapacity, ParkingTime timeManager) {
        this.parkingSlots = new ArrayList(parkingLotCapacity);
        this.slotAllotment = new SlotAllotment(parkingLotCapacity);
        this.setInitialValuesToSlots(parkingLotCapacity);
        this.parkingLotCapacity = parkingLotCapacity;
        this.parkingTime = timeManager;
    }

    private void setInitialValuesToSlots(int parkingLotCapacity) {
        IntStream.range(0, parkingLotCapacity)
                .forEach(i -> this.parkingSlots.add(i, null));
    }

    public void setParkingTime(ParkingTime parkingTime) {
        this.parkingTime = parkingTime;
    }

    public List getAvailableSlots() {
        return this.slotAllotment.getAvailableSlotsList();
    }

    public int getParkingCapacity() {
        return parkingLotCapacity;
    }

    public LocalDateTime getVehicleTimingDetails(Object vehicle) {
        Slot tempSlot = new Slot(vehicle);
        Slot time = this.parkingSlots.get(this.parkingSlots.indexOf(tempSlot));
        return time.getParkingStartTime();
    }

    public void parkVehicle(Object vehicle) throws ParkingLotException {
        if (this.vehicleAlreadyPresent(vehicle)) {
            throw new ParkingLotException("No such car present in parking lot!",
                    ParkingLotException.ExceptionType.CAR_ALREADY_PARKED);
        }
        int slot = this.getSlotToParkVehicle();
        this.parkAtSlot(slot, vehicle);
    }

    public void parkVehicleAtSpecifiedSlot(int slotNumber, Object vehicle) throws ParkingLotException {
        if (this.vehicleAlreadyPresent(vehicle)) {
            this.parkAtSlot(slotNumber, vehicle);
            return;
        }
        throw new ParkingLotException("No such car present in parking lot!",
                ParkingLotException.ExceptionType.CAR_ALREADY_PARKED);
    }

    public boolean vehicleAlreadyPresent(Object vehicle) {
        int isVehiclePresent = this.isVehiclePresent(vehicle);
        if (isVehiclePresent == -1) {
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

    private void parkAtSlot(int slot, Object vehicle) {
        Slot tempSlot = new Slot(vehicle, this.parkingTime.getCurrentTime());
        this.parkingSlots.set(slot - 1, tempSlot);
        this.slotAllotment.parkUpdate(slot);
        this.numberOfVehicles++;
    }

    /**
     * @param vehicle
     * @return
     */
    public void unParkVehicle(Object vehicle) throws ParkingLotException {
        Integer isVehiclePresent = this.isVehiclePresent(vehicle);
        if (isVehiclePresent == -1) {
            throw new ParkingLotException("No such car present in parking lot!",
                    ParkingLotException.ExceptionType.NO_SUCH_CAR_PARKED);
        }
        this.parkingSlots.set(isVehiclePresent, null);
        this.slotAllotment.unParkUpdate(isVehiclePresent + 1);
        this.numberOfVehicles--;
    }

    public int isVehiclePresent(Object vehicle) {
        Slot tempSlot = new Slot(vehicle);
        return IntStream.range(0, this.parkingSlots.size())
                .filter(i -> tempSlot.equals(this.parkingSlots.get(i)))
                .findFirst()
                .orElse(-1);
    }

    public int getNumberOfVehiclesParked() {
        return this.numberOfVehicles;
    }
}
