package com.example.test.serviceImpl;

import com.example.test.entity.Parking;
import com.example.test.mapper.ParkingMapper;
import com.example.test.service.ParkingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParkingServiceImpl implements ParkingService {
    //将DAO注入Service层
    @Autowired
    private ParkingMapper parkingMapper;


    @Override
    public List<Parking> getParkingList(String parkingCarNumber, int page, int limit) {
        return parkingMapper.getParkingList(parkingCarNumber,page,limit);
    }

    @Override
    public int getParkingCount() {
        return parkingMapper.getParkingCount();
    }

    @Override
    public Parking getParkingById(String parkingId) {
        return parkingMapper.getParkingById(parkingId);
    }

    @Override
    public int updateParking(Parking parking) {
        return parkingMapper.updateParking(parking);
    }
}
