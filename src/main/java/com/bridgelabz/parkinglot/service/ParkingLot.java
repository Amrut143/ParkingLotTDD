package com.bridgelabz.parkinglot.service;

import com.bridgelabz.parkinglot.enums.ParkingLotViewer;
import com.bridgelabz.parkinglot.exception.ParkingLotException;
import java.util.List;
import java.util.ArrayList;

public class ParkingLot {

    private List vehicle;
    private int parkingLotCapacity;
    private ParkingLotViewer viewer;
    private boolean parkingCapacityFull;

    public ParkingLot() {
        this.viewer = viewer;
        this.vehicle = new ArrayList();
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
        if (this.vehicle.contains(vehicle)) {
            return true;
        }
        return false;
    }

    /**
     *
     * @param vehicle
     * @return
     */
    public void parkVehicle(Object vehicle) throws ParkingLotException {
        if (this.vehicle.size() == this.parkingLotCapacity) {
            this.parkingCapacityFull = true;
            this.viewer.OWNER.isParkingFull = true;
            throw new ParkingLotException("No space available in the parking lot!",
                    ParkingLotException.ExceptionType.PARKING_CAPACITY_FULL);
        }
        this.vehicle.add(vehicle);
    }

    /**
     *
     * @param vehicle
     * @return
     */
    public void unParkVehicle(Object vehicle) throws ParkingLotException {
        if (this.isVehiclePresent(vehicle)) {
            this.vehicle.remove(vehicle);
            this.viewer.OWNER.isParkingFull = false;
            return;
        }
        throw new ParkingLotException("No such car present in parking lot!",
                ParkingLotException.ExceptionType.NO_SUCH_CAR_PARKED);
    }
}
