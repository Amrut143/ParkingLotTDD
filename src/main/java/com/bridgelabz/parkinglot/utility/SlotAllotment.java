package com.bridgelabz.parkinglot.utility;

import com.bridgelabz.parkinglot.exception.ParkingLotException;
import com.bridgelabz.parkinglot.observer.ObserversInformer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

public class SlotAllotment {

    public int parkingLotCapacity;
    public List<Integer> availableParkingSlots;
    private final ObserversInformer observersInformer;

    public SlotAllotment(int parkingLotCapacity) {
        this.observersInformer = new ObserversInformer();
        this.parkingLotCapacity = parkingLotCapacity;
        this.setInitialParkingStatus(parkingLotCapacity);
    }

    private void setInitialParkingStatus(int parkingLotCapacity) {
         this.availableParkingSlots = new ArrayList<>();
            IntStream.range(1, parkingLotCapacity+1)
                    .forEach(i -> this.availableParkingSlots.add(i));
    }

    public void parkUpdate(Integer slot) {
        this.availableParkingSlots.remove(slot);
    }

    public void unParkUpdate(Integer slot) {
        this.availableParkingSlots.add(slot);
        Collections.sort(this.availableParkingSlots);
    }

    public int getNearestParkingSlot() throws ParkingLotException {
        try {
            return this.availableParkingSlots.remove(0);
        } catch (IndexOutOfBoundsException e) {
            this.observersInformer.informParkingIsFull();
            throw new ParkingLotException("No parking space available!!",
                    ParkingLotException.ExceptionType.PARKING_CAPACITY_FULL);
        }
    }
}
