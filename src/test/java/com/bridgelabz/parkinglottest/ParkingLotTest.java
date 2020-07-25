package com.bridgelabz.parkinglottest;

import com.bridgelabz.parkinglot.enums.DriverType;
import com.bridgelabz.parkinglot.enums.VehicleColor;
import com.bridgelabz.parkinglot.enums.VehicleMake;
import com.bridgelabz.parkinglot.enums.VehicleSize;
import com.bridgelabz.parkinglot.model.ParkedVehicleDetails;
import com.bridgelabz.parkinglot.model.Vehicle;
import com.bridgelabz.parkinglot.observer.ParkingLotObserver;
import com.bridgelabz.parkinglot.exception.ParkingLotException;
import com.bridgelabz.parkinglot.service.ParkingLot;
import com.bridgelabz.parkinglot.service.ParkingLotSystem;
import com.bridgelabz.parkinglot.utility.ParkingTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ParkingLotTest {

    ParkingLot parkingLot;
    Vehicle firstVehicle;
    Vehicle secondVehicle;
    Vehicle thirdVehicle;
    Vehicle fourthVehicle;
    Vehicle fifthVehicle;
    private ParkingLot firstLot;
    private ParkingLot secondLot;
    private ParkingLotSystem parkingSystem;
    ParkingTime parkingTime;

    private ParkedVehicleDetails firstVehicleDetails;
    private ParkedVehicleDetails secondVehicleDetails;
    private ParkedVehicleDetails thirdVehicleDetails;
    private ParkedVehicleDetails fourthVehicleDetails;
    private ParkedVehicleDetails fifthVehicleDetails;


    @Before
    public void setUp() {
        parkingLot = new ParkingLot(4);
        parkingTime = new ParkingTime();
        this.firstLot = new ParkingLot(4);
        this.secondLot = new ParkingLot(4);
        this.parkingSystem = new ParkingLotSystem(firstLot, secondLot);
        this.firstVehicle = new Vehicle("OD-14-A-100",VehicleMake.BMW, VehicleColor.WHITE);
        this.secondVehicle = new Vehicle("OD-14-P-101",VehicleMake.TOYOTA, VehicleColor.BLUE);
        this.thirdVehicle = new Vehicle("OD-14-D-102",VehicleMake.TOYOTA, VehicleColor.BLUE);
        this.fourthVehicle = new Vehicle("OD-14-G-103",VehicleMake.BMW, VehicleColor.WHITE);
        this.fifthVehicle = new Vehicle("OD-14-F-104",VehicleMake.OTHERS, VehicleColor.OTHER);
        this.firstVehicleDetails = new ParkedVehicleDetails(firstVehicle, DriverType.HANDICAPPED, VehicleSize.SMALL, "Amrut");
        this.secondVehicleDetails = new ParkedVehicleDetails(secondVehicle, DriverType.NORMAL, VehicleSize.LARGE, "Deepak");
        this.thirdVehicleDetails = new ParkedVehicleDetails(thirdVehicle, DriverType.NORMAL, VehicleSize.SMALL, "Asish");
        this.fourthVehicleDetails = new ParkedVehicleDetails(fourthVehicle, DriverType.HANDICAPPED, VehicleSize.LARGE, "Sanjeeb");
        this.fifthVehicleDetails = new ParkedVehicleDetails(fifthVehicle, DriverType.NORMAL, VehicleSize.SMALL, "AV");
    }

    @Test
    public void givenAVehicle_WhenParkedInParkingLot_ShouldReturnTrue() {
        try {
            parkingLot.parkVehicle(firstVehicleDetails);
            int isVehicleParked = parkingLot.findSlotOfThisVehicle(firstVehicle);
            Assert.assertEquals(0, isVehicleParked);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenAVehicle_WhenUnParked_ShouldReturnTrue() {
        try {
            parkingLot.parkVehicle(firstVehicleDetails);
            parkingLot.unParkVehicle(firstVehicle);
            int isVehicleParked = parkingLot.findSlotOfThisVehicle(firstVehicle);
            Assert.assertEquals(-1, isVehicleParked);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenAVehicle_WhenTriedToUnParkedEvenWhenItWasNotParked_ShouldReturnCustomException() {
        try {
            parkingLot.parkVehicle(firstVehicleDetails);
            parkingLot.unParkVehicle(secondVehicle);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.NO_SUCH_CAR_PARKED, e.type);
            e.printStackTrace();
        }
    }

    @Test
    public void givenAVehicle_IfTriedToRePark_ShouldThrowAnException() {
        try {
            parkingLot.parkVehicle(firstVehicleDetails);
            parkingLot.parkVehicle(firstVehicleDetails);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.CAR_ALREADY_PARKED, e.type);
            e.printStackTrace();
        }
    }

    @Test
    public void givenAParkingLotWithSize_WhenCapacityIsFull_ShouldThrowAnException() {
        try {
            parkingLot.parkVehicle(firstVehicleDetails);
            parkingLot.parkVehicle(secondVehicleDetails);
            parkingLot.parkVehicle(thirdVehicleDetails);
            parkingLot.parkVehicle(fourthVehicleDetails);
            parkingLot.parkVehicle(fifthVehicleDetails);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.PARKING_CAPACITY_FULL, e.type);
            e.printStackTrace();
        }
    }

    @Test
    public void givenParkingCapacityIsFull_WhenInformedToOwner_ShouldReturnTrue() {
        try {
            parkingLot.parkVehicle(firstVehicleDetails);
            parkingLot.parkVehicle(secondVehicleDetails);
            parkingLot.parkVehicle(thirdVehicleDetails);
            parkingLot.parkVehicle(fourthVehicleDetails);
            parkingLot.parkVehicle(fifthVehicleDetails);
        } catch (ParkingLotException e) {
            Assert.assertTrue(ParkingLotObserver.OWNER.isParkingFull);
        }
    }

    @Test
    public void givenParkingCapacityIsFull_WhenInformedToAirportSecurity_ShouldReturnTrue() {
        try {
            parkingLot.parkVehicle(firstVehicleDetails);
            parkingLot.parkVehicle(secondVehicleDetails);
            parkingLot.parkVehicle(thirdVehicleDetails);
            parkingLot.parkVehicle(fourthVehicleDetails);
            parkingLot.parkVehicle(fifthVehicleDetails);
        } catch (ParkingLotException e) {
            Assert.assertTrue(ParkingLotObserver.AIRPORT_SECURITY.isParkingFull);
        }
    }

    @Test
    public void givenParkingCapacityNotFull_WhenInformedToOwner_ShouldReturnTrue() {
        try {
            parkingLot.parkVehicle(firstVehicleDetails);
            parkingLot.parkVehicle(secondVehicleDetails);
            parkingLot.parkVehicle(thirdVehicleDetails);
            parkingLot.parkVehicle(fourthVehicleDetails);
            parkingLot.unParkVehicle(secondVehicle);
        } catch (ParkingLotException e) {
            Assert.assertTrue(ParkingLotObserver.OWNER.isParkingFull);
        }
    }

    @Test
    public void givenARequestToViewAllAvailableSlots_WhenFound_ShouldReturnAllAvailableSlots() {
        int availableSlots = this.parkingLot.getAvailableSlots().size();
        Assert.assertEquals(4, availableSlots);
    }

    @Test
    public void givenAVehicleToPark_WhenParkedInParkingLot_ShouldReturnAvailableSlotSize() {
        parkingLot.slotAllotment.parkUpdate(3);
        Assert.assertEquals(3, parkingLot.getAvailableSlots().size());
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
            parkingLot.parkVehicleAtSpecifiedSlot(1, firstVehicleDetails);
            int vehicleSlot = parkingLot.findSlotOfThisVehicle(firstVehicle)+1;
            Assert.assertEquals(1, vehicleSlot);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenARequestToFindAVehicleWhichIsParked_WhenFound_ShouldReturnSlotNumber() {
        try {
            parkingLot.parkVehicle(firstVehicleDetails);
            int vehicleSlot = parkingLot.findSlotOfThisVehicle(firstVehicle);
            Assert.assertEquals(0, vehicleSlot);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenARequestToFindAVehicleWhichIsNotParked_WhenNotFound_ShouldReturnNegative1() {
        int vehicleSlot = parkingLot.findSlotOfThisVehicle(firstVehicle);
        Assert.assertEquals(-1, vehicleSlot);
    }

    @Test
    public void givenAVehicle_WhenParked_ShouldReturnParkingStartTime() {
        LocalDateTime currTime = parkingTime.getCurrentTime();
        ParkingLot parkingLot = mock(ParkingLot.class);
        try {
            when(parkingLot.getVehicleTimingDetails(firstVehicle)).thenReturn(currTime);
            parkingLot.parkVehicle(firstVehicleDetails);
            int slot = parkingLot.findSlotOfThisVehicle(firstVehicle);
            LocalDateTime details =  parkingLot.getVehicleTimingDetails(firstVehicle);
            Assert.assertEquals(currTime, details);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenAParkingLot_ShouldGetAddedToTheParkingLotsManagedByTheSystem() {
        ParkingLot thirdParkingLot = new ParkingLot(5);
        parkingSystem.addParking(thirdParkingLot);
        int numberOfParkingLots = parkingSystem.getNumberOfParkingLots();
        Assert.assertEquals(3, numberOfParkingLots);
    }

    @Test
    public void givenParkingLotsAreEmpty_FirstVehicleShouldGetParkedInFirstParkingLot() {
        try {
            parkingSystem.park(firstVehicleDetails);
            ParkingLot lot = parkingSystem.getParkingLotInWhichThisVehicleIsParked(firstVehicle);
            Assert.assertEquals(firstLot, lot);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenParkingLotsAreEmpty_SecondVehicleShouldGetParkedInSecondParkingLot() {
        try {
            parkingSystem.park(firstVehicleDetails);
            ParkingLot parkingLot = parkingSystem.getParkingLotInWhichThisVehicleIsParked(firstVehicle);
            Assert.assertEquals(firstLot, parkingLot);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenARequestToUnParkAVehicle_ShouldGetUnParked() {
        try {
            parkingSystem.park(firstVehicleDetails);
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
            parkingSystem.park(firstVehicleDetails);
            parkingSystem.park(secondVehicleDetails);
            ParkingLot nearestLot = parkingSystem.getParkingLotInWhichThisVehicleIsParked(secondVehicle);
            Assert.assertEquals(secondLot, nearestLot);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenARequestToGetSmallVehicle_WhenFound_ShouldReturnVehicleSize() {
        VehicleSize vehicleSize = firstVehicleDetails.getVehicleSize();
        Assert.assertEquals(VehicleSize.SMALL, vehicleSize);
    }

    @Test
    public void givenARequestToGetLargeVehicle_WhenFound_ShouldReturnVehicleSize() {
        VehicleSize vehicleSize = secondVehicleDetails.getVehicleSize();
        Assert.assertEquals(VehicleSize.LARGE, vehicleSize);
    }

    @Test
    public void givenALargeVehicle_ShouldGetParkedInTheLotWithMostEmptySlots() {
        try {
            parkingSystem.park(firstVehicleDetails);
            parkingSystem.park(secondVehicleDetails);
            ParkingLot highestNumOfFreeLot = parkingSystem.getParkingLotInWhichThisVehicleIsParked(secondVehicle);
            Assert.assertEquals(secondLot, highestNumOfFreeLot);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenARequestToGetVehicleColour_ShouldReturnVehicleColor() {
        VehicleColor vehicleColor = firstVehicleDetails.getVehicleColor();
        Assert.assertEquals(VehicleColor.WHITE, vehicleColor);
    }

    @Test
    public void givenAQueryToGetSlotsOfAllWhiteVehicles_WhenFound_ShouldReturnListOfWhitVehicleWithSlotNumber() {
        try {
        parkingSystem.park(firstVehicleDetails);
        parkingSystem.park(secondVehicleDetails);
        parkingSystem.park(thirdVehicleDetails);
        parkingSystem.park(fourthVehicleDetails);
        ArrayList<List<Integer>> slotNumberListOfVehiclesByColor =
                parkingSystem.getSlotNumberListOfVehiclesByColor(VehicleColor.WHITE);
            Assert.assertEquals(2, slotNumberListOfVehiclesByColor.size());
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenARequestToGetSlotsOfAllBlueToyota_WhenFound_ShouldReturnListOfSimilarVehiclesSlotNumber() {
        try {
            parkingSystem.park(firstVehicleDetails);
            parkingSystem.park(secondVehicleDetails);
            parkingSystem.park(thirdVehicleDetails);
            parkingSystem.park(fourthVehicleDetails);
            ArrayList<List<Integer>> slotNumberListOfVehiclesByMakeAndColor =
                    parkingSystem.getSlotNumberListOfVehiclesByMakeAndColor(VehicleMake.TOYOTA, VehicleColor.BLUE);
            Assert.assertEquals(2, slotNumberListOfVehiclesByMakeAndColor.size());
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenARequestToGetSlotsOfAllBMWVehicle_WhenFound_ShouldReturnListOfSimilarVehiclesSlotNumber() {
        try {
            parkingSystem.park(firstVehicleDetails);
            parkingSystem.park(secondVehicleDetails);
            parkingSystem.park(thirdVehicleDetails);
            parkingSystem.park(fourthVehicleDetails);
            ArrayList<List<Integer>> slotNumberListOfVehiclesByMake =
                    parkingSystem.getSlotNumberListOfVehiclesByMake(VehicleMake.BMW);
            Assert.assertEquals(2, slotNumberListOfVehiclesByMake.size());
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }
}