package com.bridgelabz.parkinglot.model;

import com.bridgelabz.parkinglot.enums.DriverType;
import com.bridgelabz.parkinglot.enums.VehicleColor;
import com.bridgelabz.parkinglot.enums.VehicleSize;

import java.util.Objects;

public class ParkedVehicleDetails {

    private Object vehicle;
    private VehicleSize vehicleSize;
    private DriverType driverType;
    private VehicleColor vehicleColor;

    public ParkedVehicleDetails(Object vehicle) {
        this.vehicle = vehicle;
    }

    public VehicleSize getVehicleSize() {
        return vehicleSize;
    }
    public DriverType getDriverType() {
        return driverType;
    }

    public VehicleColor getVehicleColor() {
        return this.vehicleColor;
    }

    public ParkedVehicleDetails(Object vehicle, DriverType driverType, VehicleSize vehicleSize, VehicleColor vehicleColor) {
        this.driverType = driverType;
        this.vehicleSize = vehicleSize;
        this.vehicle = vehicle;
        this.vehicleColor = vehicleColor;
    }

    public Object getVehicle() {
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
