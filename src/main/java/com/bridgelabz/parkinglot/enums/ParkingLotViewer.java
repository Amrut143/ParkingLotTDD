package com.bridgelabz.parkinglot.enums;

public enum ParkingLotViewer {
    OWNER(false), AIRPORT_SECURITY(false);
    public boolean isParkingFull;

    ParkingLotViewer(boolean isParkingFull) {
        this.isParkingFull = isParkingFull;
    }
}
