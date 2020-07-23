package com.bridgelabz.parkinglot.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class ParkingSlot {

    private Object vehicle;
    private LocalDateTime parkingStartTime;

    public ParkingSlot(Object vehicle) {
        this.vehicle = vehicle;
    }

    public ParkingSlot(Object vehicle, LocalDateTime parkingStartTime) {
        this.vehicle = vehicle;
        this.parkingStartTime = parkingStartTime;
    }

    public LocalDateTime getParkingStartTime() {
        return parkingStartTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParkingSlot slot = (ParkingSlot) o;
        return Objects.equals(vehicle, slot.vehicle) &&
                Objects.equals(parkingStartTime, slot.parkingStartTime);
    }
}
