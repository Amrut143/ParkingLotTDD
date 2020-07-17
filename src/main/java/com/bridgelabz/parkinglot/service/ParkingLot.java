package com.bridgelabz.parkinglot;

import java.util.ArrayList;
import java.util.List;

public class ParkingLot {

    private List vehicle;
    private int parkingCapacity;
    private boolean parkingCapacityFull;

    public ParkingLot() {
        this.vehicle = new ArrayList();
    }

    public void setParkingLotCapacity(int capacity) {
        this.parkingCapacity = capacity;
    }

    /**
     *
     * @param vehicle
     * @return
     */
    public boolean parkVehicle(Object vehicle) {
        if (this.vehicle.size() == this.parkingCapacity) {
            this.parkingCapacityFull = true;
            throw new ParkingLotException("No space available in the parking lot!",
                    ParkingLotException.ExceptionType.PARKING_CAPACITY_FULL);
        }
        if (this.vehicle.equals(vehicle)) {
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
    public boolean unParkVehicle(Object vehicle) {
        if (this.vehicle != null && this.vehicle.equals(vehicle)) {
            this.parkedVehicles.remove(vehicle);
            return;
        }
        throw new ParkingLotException("No such car present in parking lot!",
                ParkingLotException.ExceptionType.NO_SUCH_CAR_PARKED);
    }
}
