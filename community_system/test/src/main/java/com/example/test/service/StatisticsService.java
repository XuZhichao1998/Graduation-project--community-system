package com.example.test.service;

import com.example.test.entity.UnitTotalDuration;
import com.example.test.entity.VolunteerTotalDuration;

import java.util.List;

public interface StatisticsService {
    List<VolunteerTotalDuration> getVolunteerTotalDurationTop3List();
    List<UnitTotalDuration> getUnitTotalDurationList(int page, int limit);
    int getUnitTotalDurationCount();
}
