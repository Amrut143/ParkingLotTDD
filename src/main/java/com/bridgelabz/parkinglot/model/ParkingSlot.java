package com.bridgelabz.parkinglot.model;

import com.bridgelabz.parkinglot.enums.VehicleColor;

import java.time.LocalDateTime;
import java.util.Objects;

public class ParkingSlot {

    private ParkedVehicleDetails vehicleDetails;
    private LocalDateTime parkingStartTime;

    public ParkingSlot(ParkedVehicleDetails vehicleDetails) {
        this.vehicleDetails = vehicleDetails;
    }

    public ParkingSlot(ParkedVehicleDetails vehicleDetails, LocalDateTime parkingStartTime) {
        this.vehicleDetails = vehicleDetails;
        this.parkingStartTime = parkingStartTime;
    }

    public LocalDateTime getParkingStartTime() {
        return parkingStartTime;
    }

    public Vehicle getVehicle() {
        return this.vehicleDetails.getVehicle();
    }

    public String getVehicleMake() {
        return this.vehicleDetails.getVehicle().getMake();
    }

    public VehicleColor getVehicleColor() {
        return this.vehicleDetails.getVehicleColor();
    }
}
