package com.bridgelabz.parkinglottest;

import com.bridgelabz.parkinglot.observer.ParkingLotObserver;
import com.bridgelabz.parkinglot.exception.ParkingLotException;
import com.bridgelabz.parkinglot.service.ParkingLot;
import com.bridgelabz.parkinglot.utility.SlotAllotment;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class ParkingLotTest {

    ParkingLot parkingLot;
    Object vehicle;

    @Before
    public void setUp() {
        parkingLot = new ParkingLot(2);
        vehicle = new Object();
    }

    @Test
    public void givenAVehicle_WhenParkedInParkingLot_ShouldReturnTrue() {
        try {
            parkingLot.parkVehicle(vehicle);
            int isVehicleParked = parkingLot.isVehiclePresent(vehicle);
            Assert.assertEquals(0, isVehicleParked);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenAVehicle_WhenUnParked_ShouldReturnTrue() {
        try {
            parkingLot.parkVehicle(vehicle);
            parkingLot.unParkVehicle(vehicle);
            int isVehicleParked = parkingLot.isVehiclePresent(vehicle);
            Assert.assertEquals(-1, isVehicleParked);
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
    public void givenAVehicle_IfTriedToRePark_ShouldThrowAnException() {
        try {
            parkingLot.parkVehicle(vehicle);
            parkingLot.parkVehicle(vehicle);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.CAR_ALREADY_PARKED, e.type);
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
            Assert.assertTrue(ParkingLotObserver.OWNER.isParkingFull);
        }
    }

    @Test
    public void givenParkingCapacityIsFull_WhenInformedToAirportSecurity_ShouldReturnTrue() {
        try {
            this.parkingLot.parkVehicle(vehicle);
            Object vehicle2 = new Object();
            parkingLot.parkVehicle(vehicle2);
            Object vehicle3 = new Object();
            parkingLot.parkVehicle(vehicle3);
        } catch (ParkingLotException e) {
            Assert.assertTrue(ParkingLotObserver.AIRPORT_SECURITY.isParkingFull);
        }
    }

    @Test
    public void givenParkingCapacityNotFull_WhenInformedToOwner_ShouldReturnTrue() {
        try {
            this.parkingLot.parkVehicle(vehicle);
            Object vehicle2 = new Object();
            parkingLot.parkVehicle(vehicle2);
            parkingLot.unParkVehicle(vehicle2);
        } catch (ParkingLotException e) {
            Assert.assertTrue(ParkingLotObserver.OWNER.isParkingFull);
        }
    }

    @Test
    public void givenARequestToViewAllAvailableSlots_WhenFound_ShouldReturnAllAvailableSlots() {
        List availableSlots = this.parkingLot.getAvailableSlots();
        Assert.assertEquals(this.parkingLot.slotAllotment.availableParkingSlots.size(), availableSlots.size());
    }

    @Test
    public void givenAVehicleToPark_InAnEmptyOccupiedList_ShouldReturnSize1() {
        parkingLot.slotAllotment.parkUpdate(1);
        Assert.assertEquals(1, parkingLot.slotAllotment.availableParkingSlots.size());
    }

    @Test
    public void givenAnEmptyParkingLot_WhenAskedForNearestParkingSlot_ShouldReturnFirstSlot() {
        try {
            Assert.assertEquals(1, parkingLot.slotAllotment.getNearestParkingSlot());
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenARequestFromOwnerToParkAtGivenSlot_SystemShouldAllotParkingSlotAccordingly() {
        try {
            parkingLot.parkAtFollowingSlot(1, vehicle);
            int vehicleSlot = parkingLot.isVehiclePresent(vehicle);
            Assert.assertEquals(1, vehicleSlot);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenARequestToFindAVehicleWhichIsParked_WhenFound_ShouldReturnSlotNumber() {
        try {
            parkingLot.parkVehicle(vehicle);
            int vehicleSlot = parkingLot.isVehiclePresent(vehicle);
            Assert.assertEquals(0, vehicleSlot);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenARequestToFindAVehicleWhichIsNotParked_WhenNotFound_ShouldReturnNegative1() {
        int vehicleSlot = parkingLot.isVehiclePresent(vehicle);
        Assert.assertEquals(-1, vehicleSlot);
    }
}