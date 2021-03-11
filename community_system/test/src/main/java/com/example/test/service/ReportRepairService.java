package com.example.test.service;

import com.example.test.entity.ReportRepair;

import java.util.List;

public interface ReportRepairService {
    ReportRepair getReportRepairById(int reportRepairId);
    List<ReportRepair> getReportRepairList(int page,int limit);
    List<ReportRepair> getReportRepairListById(int reportRepairId,int page,int limit);
    int getReportRepairCount(); //查询报修信息的个数
    int addReportRepair(ReportRepair reportRepair); //添加一个报修信息
    int updateReportRepair(ReportRepair reportRepair); //更新报修信息
    int deleteReportRepair(int reportRepairId); //删除报修信息

    //________________________以下是用户报修模块调用的方法____________________________________________
    int getReportRepairCountForUser(String reportUserId); //查询报修信息的个数

    List<ReportRepair> getReportRepairListForUser(String reportUserId, int page, int limit); //返回所有报修元素

    List<ReportRepair> getReportRepairListByIdForUser(String reportUserId,int reportRepairId, int page, int limit);

}
