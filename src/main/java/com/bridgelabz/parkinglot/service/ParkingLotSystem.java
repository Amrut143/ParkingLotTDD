package com.bridgelabz.parkinglot.service;

import com.bridgelabz.parkinglot.enums.VehicleColor;
import com.bridgelabz.parkinglot.enums.VehicleMake;
import com.bridgelabz.parkinglot.exception.ParkingLotException;
import com.bridgelabz.parkinglot.model.ParkedVehicleDetails;
import com.bridgelabz.parkinglot.model.Vehicle;

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

    public void unPark(Vehicle vehicle) throws ParkingLotException {
        ParkingLot parkingLotOfThisVehicle = this.getParkingLotInWhichThisVehicleIsParked(vehicle);
        parkingLotOfThisVehicle.unParkVehicle(vehicle);
    }

    public ParkingLot getParkingLotInWhichThisVehicleIsParked(Vehicle vehicle) throws ParkingLotException {
        return this.numOfLots.stream().filter(parkingLot -> parkingLot.vehicleAlreadyPresent(vehicle)).findFirst()
                .orElseThrow(() -> new ParkingLotException
                        ("vehicle not parked in any lot!", ParkingLotException.ExceptionType.NO_SUCH_CAR_PARKED));
    }

    public ArrayList<ParkingLot> getLotsList() {
        return this.numOfLots;
    }

    public ArrayList<List<Integer>> getSlotNumberListOfVehiclesByColor(VehicleColor vehicleColor) {
        ArrayList<List<Integer>> listOfSlots = new ArrayList<>();
        this.numOfLots.stream().
                forEach(parkingLot -> listOfSlots.add(parkingLot.getSlotNumberListOfVehiclesByColor(vehicleColor)));
        return listOfSlots;
    }

    public ArrayList<List<Integer>> getSlotNumberListOfVehiclesByMakeAndColor(VehicleMake vehicleMake, VehicleColor vehicleColor) {
        ArrayList<List<Integer>> listOfSlots = new ArrayList<>();
        this.numOfLots.stream().
                forEach(parkingLot -> listOfSlots.add(parkingLot.getSlotNumberListOfVehiclesByMakeAndColor(vehicleMake, vehicleColor)));
        return listOfSlots;
    }

    public ArrayList<List<Integer>> getSlotNumberListOfVehiclesByMake(VehicleMake vehicleMake) {
        ArrayList<List<Integer>> listOfSlots = new ArrayList<>();
        this.numOfLots.stream().
                forEach(parkingLot -> listOfSlots.add(parkingLot.getSlotNumberListOfVehiclesByMake(vehicleMake)));
        return listOfSlots;
    }

    public ArrayList<List<Integer>> getVehiclesWhichIsParkedFrom30Min(int minute) {
        ArrayList<List<Integer>> listOfSlots = new ArrayList<>();
        this.numOfLots.stream().
                forEach(parkingLot -> listOfSlots.add(parkingLot.getVehiclesWhichIsParkedFrom30Min(minute)));
        return listOfSlots;
    }
}
