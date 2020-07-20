package com.bridgelabz.parkinglot.service;

import com.bridgelabz.parkinglot.exception.ParkingLotException;
import com.bridgelabz.parkinglot.utility.SlotAllotment;

import java.util.List;
import java.util.stream.IntStream;

public class ParkingLot {

    public final SlotAllotment slotAllotment;
    private Object[] parkedVehicles;
    private boolean parkingCapacityFull;

    public ParkingLot(int parkingLotCapacity) {
        this.parkedVehicles = new Object[parkingLotCapacity];
        this.slotAllotment = new SlotAllotment(parkingLotCapacity);
    }

    /**
     *
     * @param vehicle
     * @return
     */
    public int isVehiclePresent(Object vehicle) {
        return IntStream.range(0, this.parkedVehicles.length)
                .filter(i -> vehicle.equals(this.parkedVehicles[i]))
                .findFirst()
                .orElse(-1);
    }

    /**
     * @param vehicle
     * @return
     */
    public void parkVehicle(Object vehicle) throws ParkingLotException {
        if (this.isVehiclePresent(vehicle) != -1) {
            throw new ParkingLotException("Car already present in parking lot!",
                    ParkingLotException.ExceptionType.CAR_ALREADY_PARKED);
        }
        int slot = this.getSlot();
        this.parkedVehicles[slot] = vehicle;
        this.slotAllotment.parkUpdate(slot + 1);
    }

    private int getSlot() throws ParkingLotException {
        try {
            return slotAllotment.getNearestParkingSlot() - 1;
        } catch (ParkingLotException e) {
            this.parkingCapacityFull = true;
            throw e;
        }
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
        this.parkedVehicles[isVehiclePresent] = null;
        this.slotAllotment.unParkUpdate(isVehiclePresent + 1);
        return;
    }

    public List getAvailableSlots() {
        return this.slotAllotment.availableParkingSlots;
    }

    public void parkAtFollowingSlot(int slotNumber, Object vehicle) throws ParkingLotException {
        int isCarPresent = this.isVehiclePresent(vehicle);
        if (isCarPresent != -1) {
            throw new ParkingLotException("No such car present in parking lot!",
                    ParkingLotException.ExceptionType.CAR_ALREADY_PARKED);
        }
        this.parkedVehicles[slotNumber] = vehicle;
        this.slotAllotment.unParkUpdate(slotNumber + 1);
        return;
    }
}
