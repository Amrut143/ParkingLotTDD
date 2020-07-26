package com.bridgelabz.parkinglot.service;

import com.bridgelabz.parkinglot.enums.DriverType;
import com.bridgelabz.parkinglot.enums.VehicleColor;
import com.bridgelabz.parkinglot.enums.VehicleMake;
import com.bridgelabz.parkinglot.enums.VehicleSize;
import com.bridgelabz.parkinglot.exception.ParkingLotException;
import com.bridgelabz.parkinglot.model.ParkedVehicleDetails;
import com.bridgelabz.parkinglot.model.ParkingSlot;
import com.bridgelabz.parkinglot.model.Vehicle;
import com.bridgelabz.parkinglot.utility.ParkingTime;
import com.bridgelabz.parkinglot.utility.SlotAllotment;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class ParkingLot {

    public SlotAllotment slotAllotment;
    private ArrayList<ParkingSlot> parkingSlots;
    private ParkingTime parkingTime;
    private int parkingLotCapacity;
    private boolean parkingCapacityFull;
    private int numberOfVehicles = 0;

    public ParkingLot(int parkingLotCapacity) {
        this.parkingSlots = new ArrayList(parkingLotCapacity);
        this.slotAllotment = new SlotAllotment(parkingLotCapacity);
        this.setInitialValuesToSlots(parkingLotCapacity);
        this.parkingLotCapacity = parkingLotCapacity;
        this.parkingTime = new ParkingTime();
    }

    private void setInitialValuesToSlots(int parkingLotCapacity) {
        IntStream.range(0, parkingLotCapacity)
                .forEach(index -> this.parkingSlots.add(index, null));
    }

    public List getAvailableSlots() {
        return this.slotAllotment.getAvailableSlotsList();
    }

    public int getParkingCapacity() {
        return parkingLotCapacity;
    }

    public LocalDateTime getVehicleTimingDetails(Vehicle vehicle) {
        ParkingSlot parkingSlot = new ParkingSlot(new ParkedVehicleDetails(vehicle));
        ParkingSlot time = this.parkingSlots.get(this.parkingSlots.indexOf(parkingSlot));
        return time.getParkingStartTime();
    }

    public void parkVehicle(ParkedVehicleDetails vehicleDetails) throws ParkingLotException {
        int slot = this.getSlotToParkVehicle();
        this.parkVehicleAtSpecifiedSlot(slot, vehicleDetails);
    }

    public void parkVehicleAtSpecifiedSlot(int slotNumber, ParkedVehicleDetails vehicleDetails) throws ParkingLotException {
        if (this.vehicleAlreadyPresent(vehicleDetails.getVehicle())) {
            throw new ParkingLotException("No such car present in parking lot!",
                    ParkingLotException.ExceptionType.CAR_ALREADY_PARKED);
        }
        this.parkVehicleAtSlot(slotNumber, vehicleDetails);
    }

    public boolean vehicleAlreadyPresent(Vehicle vehicle) {
        int vehiclePresent = this.findSlotOfThisVehicle(vehicle);
        if (vehiclePresent == -1) {
            return false;
        }
        return true;
    }

    private int getSlotToParkVehicle() throws ParkingLotException {
        try {
            return slotAllotment.getNearestParkingSlot();
        } catch (ParkingLotException e) {
            this.parkingCapacityFull = true;
            throw e;
        }
    }

    private void parkVehicleAtSlot(int slotNumber, ParkedVehicleDetails vehicleDetails) {
        ParkingSlot parkingSlot = new ParkingSlot(slotNumber, vehicleDetails, this.parkingTime.getCurrentTime());
        this.parkingSlots.set(slotNumber - 1, parkingSlot);
        this.slotAllotment.parkUpdate(slotNumber);
        this.numberOfVehicles++;
    }

    /**
     * @param vehicle
     * @return
     */
    public void unParkVehicle(Vehicle vehicle) throws ParkingLotException {
        int vehiclePresent = this.findSlotOfThisVehicle(vehicle);
        if (vehiclePresent == -1) {
            throw new ParkingLotException("No such car present in parking lot!",
                    ParkingLotException.ExceptionType.NO_SUCH_CAR_PARKED);
        }
        this.parkingSlots.set(vehiclePresent, null);
        this.slotAllotment.unParkUpdate(vehiclePresent + 1);
        this.numberOfVehicles--;
    }

    public int findSlotOfThisVehicle(Vehicle vehicle) {
        return IntStream.range(0, this.parkingSlots.size())
                .filter(index ->
                        this.parkingSlots.get(index) != null &&
                                vehicle.equals(this.parkingSlots.get(index).getVehicle()))
                .findFirst()
                .orElse(-1);
    }

    public int getNumberOfVehiclesParked() {
        return this.numberOfVehicles;
    }

    public List<Integer> getSlotNumberListOfVehiclesByColor(VehicleColor vehicleColor) {
        List<Integer> slotsList = new ArrayList<>();
        IntStream.range(0, this.parkingSlots.size())
                .filter(index ->
                        this.parkingSlots.get(index) != null &&
                                this.parkingSlots.get(index).getVehicleColor().equals(vehicleColor))
                .forEach(slotsList::add);
        return slotsList;
    }

    public List<String> getSlotNumberListOfVehiclesByMakeAndColor(VehicleMake make, VehicleColor color) {
        List<String> slotsList = new ArrayList();
        IntStream.range(0, this.parkingSlots.size())
                .filter(index ->
                        this.parkingSlots.get(index) != null &&
                                this.parkingSlots.get(index).getVehicleMake().equals(make) &&
                                this.parkingSlots.get(index).getVehicleColor().equals(color))
                .forEach(parkingSlot -> slotsList.add(parkingSlots.get(parkingSlot).getVehicle().getNumberPlate()+
                        " "+parkingSlots.get(parkingSlot).getDetails().getAttendantName()));
        return slotsList;
    }

    public List<Integer> getSlotNumberListOfVehiclesByMake(VehicleMake make) {
        List<Integer> slotsList = new ArrayList();
        IntStream.range(0, this.parkingSlots.size())
                .filter(index ->
                        this.parkingSlots.get(index) != null &&
                                this.parkingSlots.get(index).getVehicleMake().equals(make))
                .forEach(slotsList::add);
        return slotsList;
    }

    public List<Integer> getVehiclesWhichIsParkedFrom30Min(int minute) {
        List<Integer> slotsList = new ArrayList();
        IntStream.range(0, this.parkingSlots.size())
                .filter(parkingSlot ->
                        this.parkingSlots.get(parkingSlot) != null &&
                                this.parkingSlots.get(parkingSlot).getParkingStartTime().getMinute() -
                                        this.parkingTime.getCurrentTime().getMinute() <= minute)
                .forEach(slotsList::add);
        return slotsList;
    }

    public List<Integer> getSlotNumberListOfVehiclesBySizeAndDriverType(DriverType driverType, VehicleSize vehicleSize) {
        List<Integer> slotsList = new ArrayList();
        IntStream.range(0, this.parkingSlots.size())
                .filter(index ->
                        this.parkingSlots.get(index) != null &&
                                this.parkingSlots.get(index).getDetails().getVehicleSize().equals(vehicleSize) &&
                                this.parkingSlots.get(index).getDetails().getDriverType().equals(driverType))
                .forEach(slotsList::add);
        return slotsList;
    }

    public List<Integer> getAllVehiclesParkedInParkingLot() {
        List<Integer> slotsList = new ArrayList();
        IntStream.range(0, this.parkingSlots.size())
                .filter(parkingSlot -> this.parkingSlots.get(parkingSlot) != null)
                .forEach(parkingSlot -> slotsList.add(parkingSlots.get(parkingSlot).getSlotNumber()));
        return slotsList;
    }
}
