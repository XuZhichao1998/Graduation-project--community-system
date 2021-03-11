package com.example.test.service;

import com.example.test.entity.ServiceActivity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ServiceActivityService {

    int getActivityCount();
    int getFinishActivityCount();
    int getOnGoingActivityCount();
    int getRecruitingActivityCount();
    ServiceActivity getActivityById(int pid);
    List<ServiceActivity> getActivityList(int page, int limit);
    List<ServiceActivity> getActivityListById(int pid, int page, int limit);

    List<ServiceActivity> getFinishActivityList(int page, int limit);

    List<ServiceActivity> getOnGoingActivityList(int page, int limit);

    List<ServiceActivity> getRecruitingActivityList(int page, int limit);

    int addActivity(ServiceActivity activity);
    int updateServiceActivity(ServiceActivity activity);
    int deleteActivity(int pid);

    //----------------------给党员志愿者用户的查询，多返回一列是否已报名-------------------
    List<ServiceActivity> getActivityListForVolunteer(String volunteerId, int page,int limit);

    ServiceActivity getActivityByIdForVolunteer(@Param("pid") Integer pid, @Param("volunteerId") String volunteerId);
}
