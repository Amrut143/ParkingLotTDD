package com.bridgelabz.parkinglottest;

import com.bridgelabz.parkinglot.enums.DriverType;
import com.bridgelabz.parkinglot.observer.ParkingLotObserver;
import com.bridgelabz.parkinglot.exception.ParkingLotException;
import com.bridgelabz.parkinglot.service.ParkingLot;
import com.bridgelabz.parkinglot.service.ParkingLotSystem;
import com.bridgelabz.parkinglot.utility.ParkingTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

public class ParkingLotTest {

    ParkingLot parkingLot;
    Object vehicle;
    private ParkingLot firstLot;
    private ParkingLot secondLot;
    private ParkingLotSystem parkingSystem;
    private Object firstVehicle;
    private Object secondVehicle;
    ParkingTime timeManager;


    @Before
    public void setUp() {
        parkingLot = new ParkingLot(2);
        vehicle = new Object();
        timeManager = new ParkingTime();
        parkingLot.setParkingTime(timeManager);
        this.firstLot = new ParkingLot(2, timeManager);
        this.secondLot = new ParkingLot(2, timeManager);
        this.firstVehicle = new Object();
        this.secondVehicle = new Object();
        this.parkingSystem = new ParkingLotSystem(firstLot, secondLot);
    }

    @Test
    public void givenAVehicle_WhenParkedInParkingLot_ShouldReturnTrue() {
        try {
            parkingLot.parkVehicle(vehicle);
            int isVehicleParked = parkingLot.isVehiclePresent(vehicle)+1;
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
        int availableSlots = this.parkingLot.getAvailableSlots().size();
        Assert.assertEquals(2, availableSlots);
    }

    @Test
    public void givenAVehicleToPark_InAnEmptyOccupiedList_ShouldReturnSize1() {
        parkingLot.slotAllotment.parkUpdate(1);
        Assert.assertEquals(1, parkingLot.getAvailableSlots().size());
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
            parkingLot.parkVehicleAtSpecifiedSlot(1, vehicle);
            int vehicleSlot = parkingLot.isVehiclePresent(vehicle)+2;
            Assert.assertEquals(1, vehicleSlot);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenARequestToFindAVehicleWhichIsParked_WhenFound_ShouldReturnSlotNumber() {
        try {
            parkingLot.parkVehicle(vehicle);
            int vehicleSlot = parkingLot.isVehiclePresent(vehicle)+1;
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

    @Test
    public void givenAVehicle_WhenParked_ShouldReturnParkingStartTime() {
        ParkingTime timeManager = new ParkingTime();
        parkingLot.setParkingTime(timeManager);
        LocalDateTime currTime = timeManager.getCurrentTime();
        try {
            parkingLot.parkVehicle(vehicle);
            int tempSlot = parkingLot.isVehiclePresent(vehicle);
            LocalDateTime details =  parkingLot.getVehicleTimingDetails(tempSlot);
            Assert.assertEquals(currTime, details);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenAParkingLot_ShouldGetAddedToTheParkingLotsManagedByTheSystem() {
        ParkingLot parkingLot3 = new ParkingLot(5);
        parkingSystem.addParking(parkingLot3);
        int numberOfParkingLots = parkingSystem.getNumberOfParkingLots();
        Assert.assertEquals(3, numberOfParkingLots);
    }

    @Test
    public void givenParkingLotsAreEmpty_FirstVehicleShouldGetParkedInFirstParkingLot() {
        try {
            parkingSystem.park(firstVehicle);
            ParkingLot lot = parkingSystem.getParkingLotInWhichThisVehicleIsParked(firstVehicle);
            Assert.assertEquals(firstLot, lot);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenParkingLotsAreEmpty_SecondVehicleShouldGetParkedInSecondParkingLot() {
        try {
            parkingSystem.park(firstVehicle);
            parkingSystem.park(secondVehicle);
            ParkingLot lot = parkingSystem.getParkingLotInWhichThisVehicleIsParked(secondVehicle);
            Assert.assertEquals(secondLot, lot);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenARequestToUnParkAVehicle_ShouldGetUnParked() {
        try {
            parkingSystem.park(firstVehicle);
            parkingSystem.unPark(firstVehicle);
            ParkingLot lot = parkingSystem.getParkingLotInWhichThisVehicleIsParked(firstVehicle);
        } catch (ParkingLotException e) {
            e.printStackTrace();
            Assert.assertEquals(ParkingLotException.ExceptionType.NO_SUCH_CAR_PARKED, e.type);
        }
    }

    @Test
    public void givenAVehicleWithHandicappedDriver_IfFirstLotHasEmptySlots_TheVehicleShouldGetParkedInTheFirstParkingLot() {
        try {
            parkingSystem.park(firstVehicle);
            parkingSystem.park(secondVehicle, DriverType.HANDICAPPED);
            ParkingLot presentLot = parkingSystem.getParkingLotInWhichThisVehicleIsParked(secondVehicle);
            Assert.assertEquals(firstLot, presentLot);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }
}