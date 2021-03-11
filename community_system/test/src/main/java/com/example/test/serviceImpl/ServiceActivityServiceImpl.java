package com.example.test.serviceImpl;

import com.example.test.entity.ServiceActivity;
import com.example.test.mapper.ServiceActivityMapper;
import com.example.test.service.ServiceActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceActivityServiceImpl implements ServiceActivityService {
    //将DAO注入Service层
    @Autowired
    private ServiceActivityMapper serviceActivityMapper;


    @Override
    public int getActivityCount() {
        return serviceActivityMapper.getActivityCount();
    }

    @Override
    public int getFinishActivityCount() {
        return serviceActivityMapper.getFinishActivityCount();
    }

    @Override
    public int getOnGoingActivityCount() {
        return serviceActivityMapper.getOnGoingActivityCount();
    }

    @Override
    public int getRecruitingActivityCount() {
        return serviceActivityMapper.getRecruitingActivityCount();
    }

    @Override
    public ServiceActivity getActivityById(int pid) {
        return serviceActivityMapper.getActivityById(pid);
    }

    @Override
    public List<ServiceActivity> getActivityList(int page, int limit) {
        return serviceActivityMapper.getActivityList(page, limit);
    }

    @Override
    public List<ServiceActivity> getActivityListById(int pid, int page, int limit) {
        return serviceActivityMapper.getActivityListById(pid, page, limit);
    }

    @Override
    public List<ServiceActivity> getFinishActivityList(int page, int limit) {
        return serviceActivityMapper.getFinishActivityList(page, limit);
    }

    @Override
    public List<ServiceActivity> getOnGoingActivityList(int page, int limit) {
        return serviceActivityMapper.getOnGoingActivityList(page, limit);
    }

    @Override
    public List<ServiceActivity> getRecruitingActivityList(int page, int limit) {
        return serviceActivityMapper.getRecruitingActivityList(page, limit);
    }

    @Override
    public int addActivity(ServiceActivity activity) {
        return serviceActivityMapper.addActivity(activity);
    }

    @Override
    public int updateServiceActivity(ServiceActivity activity) {
        return  serviceActivityMapper.updateServiceActivity(activity);
    }

    @Override
    public int deleteActivity(int pid) {
        return serviceActivityMapper.deleteActivity(pid);
    }

    @Override
    public List<ServiceActivity> getActivityListForVolunteer(String volunteerId, int page, int limit) {
        return serviceActivityMapper.getActivityListForVolunteer(volunteerId, page, limit);
    }

    @Override
    public ServiceActivity getActivityByIdForVolunteer(Integer pid, String volunteerId) {
        return serviceActivityMapper.getActivityByIdForVolunteer(pid, volunteerId);
    }
}
