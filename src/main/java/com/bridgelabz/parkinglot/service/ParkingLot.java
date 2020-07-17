package com.bridgelabz.parkinglot.service;

import com.bridgelabz.parkinglot.exception.ParkingLotException;

public class ParkingLot {

    private Object vehicle;
    private int parkingLotCapacity;
    private int currentParkingLotSize;

    public ParkingLot(int parkingLotCapacity) {
        this.parkingLotCapacity = parkingLotCapacity;
    }

    /**
     *
     * @param vehicle
     * @return
     */
    public boolean parkVehicle(Object vehicle) throws ParkingLotException {
        if (this.currentParkingLotSize == this.parkingLotCapacity)
            throw new ParkingLotException("No space available in the parking lot!",
                    ParkingLotException.ExceptionType.PARKING_CAPACITY_FULL);
        this.vehicle = vehicle;
        currentParkingLotSize++;
        return true;
    }

    /**
     *
     * @param vehicle
     * @return
     */
    public boolean unParkVehicle(Object vehicle) throws ParkingLotException {
        if (this.vehicle != null && this.vehicle.equals(vehicle)) {
            return true;
        }
        throw new ParkingLotException("No such car present in parking lot!",
                ParkingLotException.ExceptionType.NO_SUCH_CAR_PARKED);
    }
}
