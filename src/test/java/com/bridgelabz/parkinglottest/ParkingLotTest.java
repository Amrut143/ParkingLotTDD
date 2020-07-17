package com.bridgelabz.parkinglottest;

import com.bridgelabz.parkinglot.ParkingLot;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ParkingLotTest {

    ParkingLot parkingLot;
    Object vehicle;

    @Before
    public void setUp() {
        parkingLot = new ParkingLot();
        vehicle = new Object();
    }

    @Test
    public void givenAVehicle_WhenParkedInParkingLot_ShouldReturnTrue() {
        boolean isParked = parkingLot.parkVehicle(vehicle);
        Assert.assertTrue(isParked);
    }

    @Test
    public void givenAVehicle_WhenUnParked_ShouldReturnTrue() {
        parkingLot.parkVehicle(vehicle);
        boolean isUnParked = parkingLot.unParkVehicle(vehicle);
        Assert.assertTrue(isUnParked);
    }
}
