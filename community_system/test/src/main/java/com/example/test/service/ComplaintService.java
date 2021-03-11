package com.example.test.service;

import com.example.test.entity.Complaint;

import java.util.List;

public interface ComplaintService {
    int getComplaintCount();
    List<Complaint> getComplaintList(int page, int limit);
    Complaint getComplaintById(int complaintId);
    List<Complaint> getComplaintListById(int complaintId,int page,int limit);
    int deleteComplaint(int complaintId);
    int updateComplaint(Complaint complaint);
    int addComplaint(Complaint complaint);

    int getComplaintCountForUser(String complaintUserId); //获取用户的投诉记录数目

    List<Complaint> getComplaintListForUser(String complaintUserId,int page,int limit); //查询某个用户的投诉信息

    List<Complaint> getComplaintListByIdForUser(String complaintUserId, int complaintId, int page, int limit);

}
