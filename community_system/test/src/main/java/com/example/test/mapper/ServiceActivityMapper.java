package com.example.test.mapper;

import com.example.test.entity.ServiceActivity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ServiceActivityMapper {
    // @Select("SELECT * FROM service_activity WHERE 1 = 1 AND pid = #{pid};")
    ServiceActivity getActivityById(@Param("pid") int pid);
    // @Select("SELECT COUNT(*) FROM service_activity;")
    int getActivityCount();
    // @Select("SELECT COUNT(*) FROM service_activity WHERE project_status = '已结束';")
    int getFinishActivityCount();
    // @Select("SELECT COUNT(*) FROM service_activity WHERE project_status = '进行中';")
    int getOnGoingActivityCount();
    // @Select("SELECT COUNT(*) FROM service_activity WHERE project_status = '招募中';")
    int getRecruitingActivityCount();
    // @Select("SELECT * FROM service_activity WHERE 1=1 LIMIT #{page}, #{limit};")
    List<ServiceActivity> getActivityList(@Param("page") int page, @Param("limit") int limit);

    // @Select("SELECT * FROM service_activity WHERE pid = #{pid} LIMIT #{page}, #{limit};")
    List<ServiceActivity> getActivityListById(@Param("pid") int pid, @Param("page") int page, @Param("limit") int limit);

    // @Select("SELECT * FROM service_activity WHERE project_status = '已结束' LIMIT #{page}, #{limit};")
    List<ServiceActivity> getFinishActivityList(@Param("page") int page, @Param("limit") int limit);

    // @Select("SELECT * FROM service_activity WHERE project_status = '进行中' LIMIT #{page}, #{limit};")
    List<ServiceActivity> getOnGoingActivityList(@Param("page") int page, @Param("limit") int limit);

    // @Select("SELECT * FROM service_activity WHERE project_status = '招募中' LIMIT #{page}, #{limit};")
    List<ServiceActivity> getRecruitingActivityList(@Param("page") int page, @Param("limit") int limit);

    //@Select("INSERT INTO service_activity(project_name, content, begin_time, registration_deadline, recruitment_numbers, " +
    //        "requirement, project_status) " +
    //        "VALUES(#{projectName},#{content},#{beginTime},#{registrationDeadline},#{recruitmentNumbers},#{requirement}," +
    //        "#{projectStatus});")
    int addActivity(@Param("activity") ServiceActivity activity);

    //@Select("DELETE FROM service_activity WHERE pid = #{pid};")
    int deleteActivity(@Param("pid") int pid);


    int updateServiceActivity(@Param("activity") ServiceActivity activity);

    List<ServiceActivity> getActivityListForVolunteer(
            @Param("volunteerId") String volunteerId,
            @Param("page") int page,
            @Param("limit") int limit
    );

    ServiceActivity getActivityByIdForVolunteer(@Param("pid") Integer pid, @Param("volunteerId") String volunteerId);
}
