package com.bridgelabz.parkinglot;

public class ParkingLot {

    private Object vehicle;

    /**
     *
     * @param vehicle
     * @return
     */
    public boolean parkVehicle(Object vehicle) {
        this.vehicle = vehicle;
        return true;
    }

    /**
     *
     * @param vehicle
     * @return
     */
    public boolean unParkVehicle(Object vehicle) {
        if (this.vehicle != null && this.vehicle.equals(vehicle)) {
            return true;
        }
        return false;
    }
}
