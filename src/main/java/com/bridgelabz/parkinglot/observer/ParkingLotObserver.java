package com.bridgelabz.parkinglot.observer;

public enum ParkingLotObserver {
    OWNER(false), AIRPORT_SECURITY(false);
    public boolean isParkingFull;

    ParkingLotObserver(boolean isParkingFull) {
        this.isParkingFull = isParkingFull;
    }
}
