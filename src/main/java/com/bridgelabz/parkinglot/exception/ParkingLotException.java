package com.bridgelabz.parkinglot.exception;

public class ParkingLotException extends Exception {
    public enum ExceptionType {
        NO_SUCH_CAR_PARKED, CAR_ALREADY_PARKED, PARKING_CAPACITY_FULL
    }
    public ExceptionType type;

    /**
     *
     * @param message
     * @param type
     */
    public ParkingLotException(String message, ExceptionType type) {
        super(message);
        this.type = type;
    }
}
