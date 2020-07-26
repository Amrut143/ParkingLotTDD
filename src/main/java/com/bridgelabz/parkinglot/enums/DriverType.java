package com.bridgelabz.parkinglot.enums;

import com.bridgelabz.parkinglot.exception.ParkingLotException;
import com.bridgelabz.parkinglot.service.ParkingLot;

import java.util.ArrayList;
import java.util.Comparator;

public enum DriverType {

    NORMAL {
        @Override
        public ParkingLot getParkingLot(ArrayList<ParkingLot> lots) throws ParkingLotException {
            return lots.stream()
                    .sorted(Comparator.comparing(ParkingLot::getNumberOfVehiclesParked))
                    .filter(lot -> lot.getNumberOfVehiclesParked() != lot.getParkingCapacity())
                    .findFirst()
                    .orElseThrow(() -> new ParkingLotException("All lots full....!! Please come back later",
                            ParkingLotException.ExceptionType.PARKING_CAPACITY_FULL));
        }

    }, HANDICAPPED {
        @Override
        public ParkingLot getParkingLot(ArrayList<ParkingLot> lots) throws ParkingLotException {
            return lots.stream()
                    .filter(lot -> lot.getNumberOfVehiclesParked() != lot.getParkingCapacity())
                    .findFirst()
                    .orElseThrow(() -> new ParkingLotException("All lots full....!! Please come back later",
                            ParkingLotException.ExceptionType.PARKING_CAPACITY_FULL));
        }
    };

    public abstract ParkingLot getParkingLot(ArrayList<ParkingLot> lots) throws ParkingLotException;
}
