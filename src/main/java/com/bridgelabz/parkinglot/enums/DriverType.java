package com.bridgelabz.parkinglot.enums;

import com.bridgelabz.parkinglot.exception.ParkingLotException;
import com.bridgelabz.parkinglot.service.ParkingLot;

import java.util.ArrayList;

public enum DriverType {

    HANDICAPPED {

        @Override
        public ParkingLot getLot(ArrayList<ParkingLot> lots) throws ParkingLotException {
            return lots.stream()
                    .filter(lot -> lot.getNumberOfVehiclesParked() != lot.getParkingCapacity())
                    .findFirst()
                    .orElseThrow(() -> new ParkingLotException("All lots full....!! Please come back later",
                            ParkingLotException.ExceptionType.PARKING_CAPACITY_FULL));
        }
    };

    public abstract ParkingLot getLot(ArrayList<ParkingLot> lots) throws ParkingLotException;
}
