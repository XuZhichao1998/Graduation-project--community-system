package com.example.test.mapper;

import com.example.test.entity.VolunteerTotalDuration;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface VolunteerTotalDurationMapper {
    List<VolunteerTotalDuration> getVolunteerTotalDurationTop3List();
}
