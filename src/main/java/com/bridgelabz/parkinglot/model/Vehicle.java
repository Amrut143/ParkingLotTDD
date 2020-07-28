package com.bridgelabz.parkinglot.model;

import com.bridgelabz.parkinglot.enums.VehicleColor;
import com.bridgelabz.parkinglot.enums.VehicleMake;

import java.util.Objects;

public class Vehicle {

    private final String plateNum;
    private final VehicleMake vehicleMake;
    private final VehicleColor vehicleColor;

    public Vehicle(String plateNum, VehicleMake make, VehicleColor vehicleColor) {
        this.plateNum = plateNum;
        this.vehicleMake = make;
        this.vehicleColor = vehicleColor;
    }

    public VehicleMake getMake() {
        return vehicleMake;
    }

    public VehicleColor getVehicleColor() {
        return vehicleColor;
    }

    public String getNumberPlate() {
        return this.plateNum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vehicle vehicle = (Vehicle) o;
        return Objects.equals(plateNum, vehicle.plateNum) &&
                Objects.equals(vehicleMake, vehicle.vehicleMake) &&
                vehicleColor == vehicle.vehicleColor;
    }

}
