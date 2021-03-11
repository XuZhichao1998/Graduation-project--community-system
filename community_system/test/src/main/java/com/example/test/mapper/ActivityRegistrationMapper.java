package com.example.test.mapper;

import com.example.test.entity.ActivityRegistration;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ActivityRegistrationMapper {
    List<ActivityRegistration> getActivityRegistrationList(@Param("pid") Integer pid, @Param("volunteerName") String volunteerName,
                                                           @Param("page") int page, @Param("limit") int limit);

    // List<ActivityRegistration> getActivityRegistrationList(Integer pid, String volunteerName, int page, int limit);


    int getActivityRegistrationCount();

    ActivityRegistration getActivityRegistrationByIds(@Param("pid") Integer pid, @Param("volunteerId") String volunteerId);

    int addActivityRegistration(@Param("activityRegistration") ActivityRegistration activityRegistration);

    int updateActivityRegistration(@Param("activityRegistration") ActivityRegistration activityRegistration);

    int deleteActivityRegistration(@Param("pid") Integer pid, @Param("volunteerId") String volunteerId);

    //-------------------志愿者已经报名的列表中查到的信息-----------------------------------------
    List<ActivityRegistration> getActivityRegistrationListForVolunteer(
            @Param("volunteerId") String volunteerId,
            @Param("page") int page,
            @Param("limit") int limit
    );
    int getActivityRegistrationCountForVolunteer(@Param("volunteerId") String volunteerId);
}
