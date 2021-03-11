package com.example.test.serviceImpl;

import com.example.test.entity.ReportRepair;
import com.example.test.mapper.ReportRepairMapper;
import com.example.test.service.ReportRepairService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportRepairServiceImpl implements ReportRepairService {
    //将DAO注入Service层
    @Autowired
    private ReportRepairMapper reportRepairMapper;

    @Override
    public ReportRepair getReportRepairById(int reportRepairId) {
        return reportRepairMapper.getReportRepairById(reportRepairId);
    }

    @Override
    public List<ReportRepair> getReportRepairList(int page, int limit) {
        return reportRepairMapper.getReportRepairList(page,limit);
    }

    @Override
    public List<ReportRepair> getReportRepairListById(int reportRepairId, int page, int limit) {
        return reportRepairMapper.getReportRepairListById(reportRepairId,page,limit);
    }

    @Override
    public int getReportRepairCount() {
        return reportRepairMapper.getReportRepairCount();
    }

    @Override
    public int addReportRepair(ReportRepair reportRepair) {
        return reportRepairMapper.addReportRepair(reportRepair);
    }

    @Override
    public int updateReportRepair(ReportRepair reportRepair) {
        return reportRepairMapper.updateReportRepair(reportRepair);
    }

    @Override
    public int deleteReportRepair(int reportRepairId) {
        return reportRepairMapper.deleteReportRepair(reportRepairId);
    }

    @Override
    public int getReportRepairCountForUser(String reportUserId) {
        return reportRepairMapper.getReportRepairCountForUser(reportUserId);
    }

    @Override
    public List<ReportRepair> getReportRepairListForUser(String reportUserId, int page, int limit) {
        return reportRepairMapper.getReportRepairListForUser(reportUserId,page,limit);
    }

    @Override
    public List<ReportRepair> getReportRepairListByIdForUser(String reportUserId, int reportRepairId, int page, int limit) {
        return reportRepairMapper.getReportRepairListByIdForUser(reportUserId,reportRepairId,page,limit);
    }
}
