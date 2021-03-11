package com.example.test.mapper;

import com.example.test.entity.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserInfoMapper { //interface类中的接口方法默认是public
    UserInfo getInfo(@Param(value = "name") String name, @Param(value = "password") String password);
    int userInfoAdd(@Param("userInfo") UserInfo userInfo); //插入一条用户信息，返回受影响的行数
    int userInfoDelete(@Param("userId") String userId); //通过userId删除用户信息
    int userInfoUpdate(@Param("userInfo") UserInfo userInfo); //修改用户
    UserInfo getUserInfoById(@Param("userId") String userId); //通过userId查找用户信息
    int getUserInfoCount(); //获取用户数量
    List<UserInfo> getUserInfoList(@Param("userId") String userId,@Param("page") int page,@Param("limit") int limit);

    // ____________________________以下是用户个人信息模块用到的方法______________________________________________________
    int getUserInfoCountForUser(@Param("userId") String userId); //获取用户数量

}
