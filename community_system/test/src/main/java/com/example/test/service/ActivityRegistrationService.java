package com.example.test.service;

import com.example.test.entity.ActivityRegistration;

import java.util.List;

public interface ActivityRegistrationService {
    List<ActivityRegistration> getActivityRegistrationList(Integer pid, String volunteerName, int page, int limit);

    int getActivityRegistrationCount();

    ActivityRegistration getActivityRegistrationByIds(Integer pid, String volunteerId);

    int addActivityRegistration(ActivityRegistration activityRegistration);

    int updateActivityRegistration(ActivityRegistration activityRegistration);

    int deleteActivityRegistration(Integer pid, String volunteerId);

    List<ActivityRegistration> getActivityRegistrationListForVolunteer(
           String volunteerId,
           int page,
           int limit
    );
    int getActivityRegistrationCountForVolunteer(String volunteerId);
}
