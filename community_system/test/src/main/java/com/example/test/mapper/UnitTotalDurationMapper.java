package com.example.test.mapper;

import com.example.test.entity.UnitTotalDuration;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UnitTotalDurationMapper {
    List<UnitTotalDuration> getUnitTotalDurationList(@Param("page") int page, @Param("limit") int limit);
    int getUnitTotalDurationCount();
}
