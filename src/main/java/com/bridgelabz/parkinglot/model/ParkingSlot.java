package com.bridgelabz.parkinglot.model;

import com.bridgelabz.parkinglot.enums.VehicleColor;
import com.bridgelabz.parkinglot.enums.VehicleMake;

import java.time.LocalDateTime;
import java.util.Objects;

public class ParkingSlot {

    private ParkedVehicleDetails vehicleDetails;
    private LocalDateTime parkingStartTime;
    private int slotNumber;

    public ParkingSlot(ParkedVehicleDetails vehicleDetails) {
        this.vehicleDetails = vehicleDetails;
    }

    public ParkingSlot(int slot, ParkedVehicleDetails vehicleDetails, LocalDateTime parkingStartTime) {
        this.slotNumber = slot;
        this.vehicleDetails = vehicleDetails;
        this.parkingStartTime = parkingStartTime;
    }

    public LocalDateTime getParkingStartTime() {
        return parkingStartTime;
    }

    public Vehicle getVehicle() {
        return this.vehicleDetails.getVehicle();
    }

    public VehicleMake getVehicleMake() {
        return this.vehicleDetails.getVehicle().getMake();
    }

    public VehicleColor getVehicleColor() {
        return this.vehicleDetails.getVehicleColor();
    }

    public int getSlotNumber() {
        return this.slotNumber;
    }

    public ParkedVehicleDetails getDetails() {
        return this.vehicleDetails;
    }
}
