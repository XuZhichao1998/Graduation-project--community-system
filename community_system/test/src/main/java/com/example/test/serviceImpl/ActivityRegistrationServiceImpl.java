package com.example.test.serviceImpl;

import com.example.test.entity.ActivityRegistration;
import com.example.test.mapper.ActivityRegistrationMapper;
import com.example.test.service.ActivityRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActivityRegistrationServiceImpl implements ActivityRegistrationService {
    //将DAO注入Service层
    @Autowired
    private ActivityRegistrationMapper activityRegistrationMapper;

    @Override
    public List<ActivityRegistration> getActivityRegistrationList(Integer pid, String volunteerName, int page, int limit) {
        return activityRegistrationMapper.getActivityRegistrationList(pid, volunteerName, page, limit);
    }

    @Override
    public int getActivityRegistrationCount() {
        return activityRegistrationMapper.getActivityRegistrationCount();
    }

    @Override
    public ActivityRegistration getActivityRegistrationByIds(Integer pid, String volunteerId) {
        return activityRegistrationMapper.getActivityRegistrationByIds(pid, volunteerId);
    }

    @Override
    public int addActivityRegistration(ActivityRegistration activityRegistration) {
        return activityRegistrationMapper.addActivityRegistration(activityRegistration);
    }

    @Override
    public int updateActivityRegistration(ActivityRegistration activityRegistration) {
        return activityRegistrationMapper.updateActivityRegistration(activityRegistration);
    }

    @Override
    public int deleteActivityRegistration(Integer pid, String volunteerId) {
        return activityRegistrationMapper.deleteActivityRegistration(pid, volunteerId);
    }

    @Override
    public List<ActivityRegistration> getActivityRegistrationListForVolunteer(String volunteerId, int page, int limit) {
        return activityRegistrationMapper.getActivityRegistrationListForVolunteer(volunteerId, page, limit);
    }

    @Override
    public int getActivityRegistrationCountForVolunteer(String volunteerId) {
        return activityRegistrationMapper.getActivityRegistrationCountForVolunteer(volunteerId);
    }
}
