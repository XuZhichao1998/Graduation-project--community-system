package com.example.test.mapper;

import com.example.test.entity.Parking;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ParkingMapper {

    //@select * from parking where parking_id = #{parkingId}
    Parking getParkingById(String parkingId); //通过车位Id查找车位信息

    //@select * from parking where  parking_car_number= #{parkingCarNumber}
//    Parking getParkingByCarNumber(String parkingCarNumber); //通过车牌号查找车位信息

    //@select parking_id,parking_place_description,parking_car_number from parking where parking_car_number like concat(concat('%',#{0}),'%')
    List<Parking> getParkingList(String parkingCarNumber,int page,int limit); //模糊查询车牌号

    //@select count(parking_id) from parking;
    int getParkingCount(); //获取停车位的个数

    //@update parking set parking_car_number = #{parkingCarNumber} where parking_id = #{parkingId};
    int updateParking(Parking parking); //更新停车位的信息
}
