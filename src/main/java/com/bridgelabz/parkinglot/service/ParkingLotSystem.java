package com.bridgelabz.parkinglot.service;

import com.bridgelabz.parkinglot.enums.DriverType;
import com.bridgelabz.parkinglot.exception.ParkingLotException;
import com.bridgelabz.parkinglot.model.ParkedVehicleDetails;

import java.util.*;

public class ParkingLotSystem {

    private final ArrayList<ParkingLot> numOfLots;
    public ParkingLotSystem(ParkingLot... parkingLot) {
        this.numOfLots = new ArrayList<>(Arrays.asList(parkingLot));
    }
    public void addParking(ParkingLot parkingLot) {
        this.numOfLots.add(parkingLot);
    }
    public int getNumberOfParkingLots() {
        return this.numOfLots.size();
    }

    public void park(ParkedVehicleDetails vehicleDetails) throws ParkingLotException {
        ParkingLot parkingLot = vehicleDetails.getVehicleSize().getLot(getLotsList(), vehicleDetails.getDriverType());
        parkingLot.parkVehicle(vehicleDetails);
    }

    public void unPark(Object vehicle) throws ParkingLotException {
        ParkingLot parkingLotOfThisVehicle = this.getParkingLotInWhichThisVehicleIsParked(vehicle);
        parkingLotOfThisVehicle.unParkVehicle(vehicle);
    }

    public ParkingLot getParkingLotInWhichThisVehicleIsParked(Object vehicle) throws ParkingLotException {
        return this.numOfLots.stream().filter(parkingLot -> parkingLot.vehicleAlreadyPresent(vehicle)).findFirst()
                .orElseThrow(() -> new ParkingLotException
                        ("vehicle not parked in any lot!", ParkingLotException.ExceptionType.NO_SUCH_CAR_PARKED));
    }

    public ArrayList<ParkingLot> getLotsList() {
        return this.numOfLots;
    }
}
