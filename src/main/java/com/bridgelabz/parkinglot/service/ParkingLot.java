package com.bridgelabz.parkinglot.service;

import com.bridgelabz.parkinglot.exception.ParkingLotException;
import com.bridgelabz.parkinglot.observer.ObserversInformer;

import java.util.List;
import java.util.ArrayList;

public class ParkingLot {

    private List vehicle;
    private int parkingLotCapacity;
    private final ObserversInformer observersInformer;
    private boolean parkingCapacityFull;

    public ParkingLot() {
        this.vehicle = new ArrayList();
        this.observersInformer = new ObserversInformer();
    }

    /**
     *
     * @param parkingLotCapacity
     */
    public void setParkingLotCapacity(int parkingLotCapacity) {
        this.parkingLotCapacity = parkingLotCapacity;
    }

    /**
     *
     * @param vehicle
     * @return
     */
    public boolean isVehiclePresent(Object vehicle) {
        return this.vehicle.contains(vehicle);
    }

    /**
     *
     * @param vehicle
     * @return
     */
    public void parkVehicle(Object vehicle) throws ParkingLotException {
        if (this.vehicle.size() == this.parkingLotCapacity) {
            this.parkingCapacityFull = true;
            this.observersInformer.informParkingIsFull();
            throw new ParkingLotException("No space available in the parking lot!",
                    ParkingLotException.ExceptionType.PARKING_CAPACITY_FULL);
        }
        if (this.isVehiclePresent(vehicle)) {
            throw new ParkingLotException("Car already present in parking lot!",
                    ParkingLotException.ExceptionType.CAR_ALREADY_PARKED);
        }
        this.vehicle.add(vehicle);
    }

    /**
     *
     * @param vehicle
     * @return
     */
    public void unParkVehicle(Object vehicle) throws ParkingLotException {
        if (!this.isVehiclePresent(vehicle)) {
            throw new ParkingLotException("No such car present in parking lot!",
                    ParkingLotException.ExceptionType.NO_SUCH_CAR_PARKED);
        }
        this.vehicle.remove(vehicle);
        this.observersInformer.informParkingIsAvailable();
        return;
    }
}
