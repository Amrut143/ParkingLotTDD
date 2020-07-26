package com.bridgelabz.parkinglot.enums;

import com.bridgelabz.parkinglot.exception.ParkingLotException;
import com.bridgelabz.parkinglot.service.ParkingLot;

import java.util.ArrayList;
import java.util.Comparator;

public enum VehicleSize {

    SMALL {
        @Override
        public ParkingLot getParkingLot(ArrayList<ParkingLot> lotsList, DriverType driverType) throws ParkingLotException {
            return driverType.getParkingLot(lotsList);
        }
    }, LARGE {
        @Override
        public ParkingLot getParkingLot(ArrayList<ParkingLot> lotsList, DriverType driverType) throws ParkingLotException {
            return lotsList.stream()
                    .sorted(Comparator.comparing(lot -> (lot.getParkingCapacity() - lot.getNumberOfVehiclesParked()),
                            Comparator.reverseOrder()))
                    .filter(lot -> lot.getNumberOfVehiclesParked() != lot.getParkingCapacity())
                    .findFirst()
                    .orElseThrow(() -> new ParkingLotException("All lots full....!! Please come back later",
                            ParkingLotException.ExceptionType.PARKING_CAPACITY_FULL));
        }
    };

    public abstract ParkingLot getParkingLot(ArrayList<ParkingLot> lotsList, DriverType driverType) throws ParkingLotException;
}
