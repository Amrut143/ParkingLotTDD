package com.bridgelabz.parkinglottest;

import com.bridgelabz.parkinglot.enums.ParkingLotViewer;
import com.bridgelabz.parkinglot.exception.ParkingLotException;
import com.bridgelabz.parkinglot.service.ParkingLot;
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
        this.parkingLot.setParkingLotCapacity(2);
    }

    @Test
    public void givenAVehicle_WhenParkedInParkingLot_ShouldReturnTrue() {
        try {
            parkingLot.parkVehicle(vehicle);
            boolean isVehicleParked = parkingLot.isVehiclePresent(vehicle);
            Assert.assertTrue(isVehicleParked);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void givenAVehicle_WhenUnParked_ShouldReturnTrue() {
        try {
            parkingLot.parkVehicle(vehicle);
            parkingLot.unParkVehicle(vehicle);
            boolean isVehicleParked = parkingLot.isVehiclePresent(vehicle);
            Assert.assertTrue(isVehicleParked);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenAVehicle_WhenTriedToUnParkedEvenWhenItWasNotParked_ShouldReturnCustomException() {
        try {
            parkingLot.parkVehicle(vehicle);
            Object vehicle2 = new Object();
            parkingLot.unParkVehicle(vehicle2);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.NO_SUCH_CAR_PARKED, e.type);
            e.printStackTrace();
        }
    }

    @Test
    public void givenAParkingLotWithSize_WhenCapacityIsFull_ShouldThrowAnException() {
        try {
            this.parkingLot.parkVehicle(vehicle);
            Object vehicle2 = new Object();
            parkingLot.parkVehicle(vehicle2);
            Object vehicle3 = new Object();
            parkingLot.parkVehicle(vehicle3);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.PARKING_CAPACITY_FULL, e.type);
            e.printStackTrace();
        }
    }

    @Test
    public void givenParkingCapacityIsFull_WhenInformedToOwner_ShouldReturnTrue() {
        try {
            this.parkingLot.parkVehicle(vehicle);
            Object vehicle2 = new Object();
            parkingLot.parkVehicle(vehicle2);
            Object vehicle3 = new Object();
            parkingLot.parkVehicle(vehicle3);
        } catch (ParkingLotException e) {
            Assert.assertTrue(ParkingLotViewer.OWNER.isParkingFull);
        }
    }
}