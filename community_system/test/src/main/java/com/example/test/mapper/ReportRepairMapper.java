package com.example.test.mapper;

import com.example.test.entity.ReportRepair;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ReportRepairMapper {

    ReportRepair getReportRepairById(@Param("reportRepairId") int reportRepairId); //通过主键查找元素

    List<ReportRepair> getReportRepairList(@Param("page") int page, @Param("limit") int limit); //返回所有报修元素

    List<ReportRepair> getReportRepairListById(@Param("reportRepairId") int reportRepairId, @Param("page") int page, @Param("limit") int limit);

    int getReportRepairCount(); //查询报修信息的个数

    int addReportRepair(@Param("reportRepair") ReportRepair reportRepair); //添加一个报修信息

    int updateReportRepair(@Param("reportRepair") ReportRepair reportRepair); //更新一个报修信息

    int deleteReportRepair(@Param("reportRepairId") int reportRepairId); //删除一个报修信息

    //________________________________以下是用户报修模块调用的方法_________________________________________________________
    int getReportRepairCountForUser(@Param("reportUserId") String reportUserId); //查询报修信息的个数

    List<ReportRepair> getReportRepairListForUser(@Param("reportUserId") String reportUserId,@Param("page") int page, @Param("limit") int limit); //返回所有报修元素

    List<ReportRepair> getReportRepairListByIdForUser(@Param("reportUserId") String reportUserId,@Param("reportRepairId") int reportRepairId, @Param("page") int page, @Param("limit") int limit);

}
