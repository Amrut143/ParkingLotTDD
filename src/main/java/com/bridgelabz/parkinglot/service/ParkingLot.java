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
    private boolean parkingCapacityFull;

    public ParkingLot(int parkingLotCapacity) {
        this.parkingSlots = new ArrayList(parkingLotCapacity);
        this.slotAllotment = new SlotAllotment(parkingLotCapacity);
        this.setInitialValuesToSlots(parkingLotCapacity);
    }

    private void setInitialValuesToSlots(int parkingLotCapacity) {
        IntStream.range(0, parkingLotCapacity)
                .forEach(i->this.parkingSlots.add(i, null));
    }

    public void setParkingTime(ParkingTime parkingTime) {
        this.parkingTime = parkingTime;
    }

    public List getAvailableSlots() {
        return this.slotAllotment.getAvailableSlotsList();
    }

    public LocalDateTime getVehicleTimingDetails(Object vehicle) {
        Slot tempSlot = new Slot(vehicle);
        return this.parkingSlots.get(this.parkingSlots.indexOf(tempSlot)).getParkingStartTime();
    }

    public void parkVehicle(Object vehicle) throws ParkingLotException {
        this.vehicleAlreadyPresent(vehicle);
        int slot = this.getSlotToParkVehicle();
        this.parkAtSlot(slot, vehicle);
    }

    public void parkVehicleAtSpecifiedSlot(int slotNumber, Object vehicle) throws ParkingLotException {
        this.vehicleAlreadyPresent(vehicle);
        this.parkAtSlot(slotNumber, vehicle);
    }

    private void vehicleAlreadyPresent(Object vehicle) throws ParkingLotException {
        int isVehiclePresent = this.isVehiclePresent(vehicle);
        if (isVehiclePresent != -1) {
            throw new ParkingLotException("No such car present in parking lot!",
                    ParkingLotException.ExceptionType.CAR_ALREADY_PARKED);
        }
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
        this.parkingSlots.set(slot-1, tempSlot);
        this.slotAllotment.parkUpdate(slot);
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
        return;
    }

    public int isVehiclePresent(Object vehicle) {
        Slot tempSlot = new Slot(vehicle);
        return IntStream.range(0, this.parkingSlots.size())
                .filter(i -> tempSlot.equals(this.parkingSlots.get(i)))
                .findFirst()
                .orElse(-1);
    }
}
