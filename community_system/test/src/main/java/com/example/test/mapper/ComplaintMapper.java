package com.example.test.mapper;

import com.example.test.entity.Complaint;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ComplaintMapper { //接口的方法默认是public

    //@select * from complaint where complaint_id = #{complaintId}
    Complaint getComplaintById(@Param("complaintId") int complaintId); //通过投诉编号查找投诉信息

    //@select * from complaint limit #{1},#{2}
    List<Complaint> getComplaintList(@Param("page") int page,@Param("limit") int limit); //查询所有的投诉信息


    List<Complaint> getComplaintListById(@Param("complaintId") int complaintId,@Param("page") int page,@Param("limit") int limit);

    //@select count(complaint_id) from complaint;
    int getComplaintCount(); //查询投诉单的个数


    // update complaint set complaint_state = #{complaintState},complaint_type = #{complaintType}
    //        where complaint_id = #{complaintId};
    int updateComplaint(@Param("complaint") Complaint complaint); //更新投诉信息

    //delete from complaint where complaint_id = #{complaintId};
    int deleteComplaint(@Param("complaintId") int complaintId); //删除投诉信息

//_______________________________________用户模块调用的方法_______________________________________________

    //insert into complaint values(null,#{complaintUserId},#{complaintType},#{complaintMessage},
    //                                 #{phoneNumber},now(),'待处理');
    int addComplaint(@Param("complaint") Complaint complaint); //增加一个投诉信息

    int getComplaintCountForUser(@Param("complaintUserId") String complaintUserId); //获取用户的投诉记录数目

    List<Complaint> getComplaintListForUser(@Param("complaintUserId") String complaintUserId,@Param("page") int page,@Param("limit") int limit); //查询某个用户的投诉信息

    List<Complaint> getComplaintListByIdForUser(@Param("complaintUserId") String complaintUserId, @Param("complaintId") int complaintId,@Param("page") int page,@Param("limit") int limit);
}
