package com.example.test.mapper;

import com.example.test.entity.AdminInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AdminInfoMapper {

    AdminInfo getInfo(@Param(value="AdminName") String AdminName,@Param(value="AdminPwd") String AdminPwd);
}
