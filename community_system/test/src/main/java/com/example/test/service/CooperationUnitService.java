package com.example.test.service;

import com.example.test.entity.CooperationUnit;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CooperationUnitService {
    int getCooperationUnitCount();

    CooperationUnit getCooperationUnitById(@Param("unitId") int unitId);

    List<CooperationUnit> getCooperationUnitList(@Param("unitName") String unitName, @Param("page") int page, @Param("limit") int limit);

    List<CooperationUnit> getCooperationUnitListById(@Param("unitId") int unitId, @Param("page") int page, @Param("limit") int limit);

    int addCooperationUnit(@Param("cooperationUnit") CooperationUnit cooperationUnit); //添加一条公告

    int deleteCooperationUnit(@Param("unitId") int unitId);
}
