package com.bridgelabz.parkinglot.model;

import com.bridgelabz.parkinglot.enums.DriverType;
import com.bridgelabz.parkinglot.enums.VehicleColor;
import com.bridgelabz.parkinglot.enums.VehicleSize;

import java.util.Objects;

public class ParkedVehicleDetails {

    private Vehicle vehicle;
    private VehicleSize vehicleSize;
    private DriverType driverType;
    private String attendantName;

    public ParkedVehicleDetails(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public VehicleSize getVehicleSize() {
        return vehicleSize;
    }

    public DriverType getDriverType() {
        return driverType;
    }

    public VehicleColor getVehicleColor() {
        return this.vehicle.getVehicleColor();
    }

    public String getAttendantName() {
        return this.attendantName;
    }

    public ParkedVehicleDetails(Vehicle vehicle, DriverType driverType, VehicleSize vehicleSize, String attendantName) {
        this.driverType = driverType;
        this.vehicleSize = vehicleSize;
        this.vehicle = vehicle;
        this.attendantName = attendantName;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParkedVehicleDetails details = (ParkedVehicleDetails) o;
        return Objects.equals(vehicle, details.vehicle);
    }
}
