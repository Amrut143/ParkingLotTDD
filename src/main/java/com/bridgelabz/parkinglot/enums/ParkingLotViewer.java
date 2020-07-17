package com.bridgelabz.parkinglot.enums;

public enum ParkingLotViewer {
    OWNER(false);
    public boolean isParkingFull;

    ParkingLotViewer(boolean isParkingFull) {
        this.isParkingFull = isParkingFull;
    }
}
