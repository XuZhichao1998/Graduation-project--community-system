package com.example.test.service;

import com.example.test.entity.Parking;

import java.util.List;

public interface ParkingService {
    List<Parking> getParkingList(String parkingCarNumber,int page,int limit);
    int getParkingCount();
    Parking getParkingById(String parkingId);
    int updateParking(Parking parking);
}
