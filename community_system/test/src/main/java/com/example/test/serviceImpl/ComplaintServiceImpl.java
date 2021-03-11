package com.example.test.serviceImpl;

import com.example.test.entity.Complaint;
import com.example.test.mapper.ComplaintMapper;
import com.example.test.service.ComplaintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComplaintServiceImpl implements ComplaintService {
    //将DAO注入Service层
    @Autowired
    private ComplaintMapper complaintMapper;


    @Override
    public int getComplaintCount() {
        return complaintMapper.getComplaintCount();
    }

    @Override
    public List<Complaint> getComplaintList(int page, int limit) {
        return complaintMapper.getComplaintList(page,limit);
    }

    @Override
    public Complaint getComplaintById(int complaintId) {
        return complaintMapper.getComplaintById(complaintId);
    }

    @Override
    public List<Complaint> getComplaintListById(int complaintId, int page, int limit) {
        return complaintMapper.getComplaintListById(complaintId,page,limit);
    }

    @Override
    public int deleteComplaint(int complaintId) {
        return complaintMapper.deleteComplaint(complaintId);
    }

    @Override
    public int updateComplaint(Complaint complaint) {
        return complaintMapper.updateComplaint(complaint);
    }

    @Override
    public int addComplaint(Complaint complaint) {
        return complaintMapper.addComplaint(complaint);
    }

    @Override
    public int getComplaintCountForUser(String complaintUserId) {
        return complaintMapper.getComplaintCountForUser(complaintUserId);
    }

    @Override
    public List<Complaint> getComplaintListForUser(String complaintUserId, int page, int limit) {
        return complaintMapper.getComplaintListForUser(complaintUserId,page,limit);
    }

    @Override
    public List<Complaint> getComplaintListByIdForUser(String complaintUserId, int complaintId, int page, int limit) {
        return complaintMapper.getComplaintListByIdForUser(complaintUserId,complaintId,page,limit);
    }
}
