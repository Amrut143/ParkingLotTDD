package com.bridgelabz.parkinglottest;

import com.bridgelabz.parkinglot.ParkingLot;
import org.junit.Assert;
import org.junit.Test;

public class ParkingLotTest {

    @Test
    public void givenAVehicle_WhenParkedInParkingLot_ShouldReturnTrue() {
        ParkingLot parkingLot = new ParkingLot();
        Object vehicle = new Object();
        boolean isParked = parkingLot.parkVehicle(vehicle);
        Assert.assertTrue(isParked);
    }
}
