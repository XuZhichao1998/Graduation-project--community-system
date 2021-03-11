package com.example.test.serviceImpl;

import com.example.test.entity.UnitTotalDuration;
import com.example.test.entity.VolunteerTotalDuration;
import com.example.test.mapper.UnitTotalDurationMapper;
import com.example.test.mapper.VolunteerTotalDurationMapper;
import com.example.test.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatisticsServiceImpl implements StatisticsService {
    //将DAO注入Service层
    @Autowired
    private VolunteerTotalDurationMapper volunteerTotalDurationMapper;

    @Autowired
    private UnitTotalDurationMapper unitTotalDurationMapper;

    @Override
    public List<VolunteerTotalDuration> getVolunteerTotalDurationTop3List() {
        return volunteerTotalDurationMapper.getVolunteerTotalDurationTop3List();
    }

    @Override
    public List<UnitTotalDuration> getUnitTotalDurationList(int page, int limit) {
        return unitTotalDurationMapper.getUnitTotalDurationList(page, limit);
    }

    @Override
    public int getUnitTotalDurationCount() {
        return unitTotalDurationMapper.getUnitTotalDurationCount();
    }
}
