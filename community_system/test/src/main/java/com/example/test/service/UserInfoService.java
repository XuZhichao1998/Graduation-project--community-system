package com.example.test.service;

import com.example.test.entity.UserInfo;

import java.util.List;

public interface UserInfoService {
    int userRegister(UserInfo userInfo); //将参数封装成userInfo对象,insert之后看返回影响的行数是否为0
    UserInfo loginIn(String name, String password); //通过用户名和密码查找，返回匹配的用户对象。null表示没有匹配到
    int getUserInfoCount(); //获取用户数量
    int userInfoAdd(UserInfo userInfo); //插入一条用户信息，返回受影响的行数
    int userInfoDelete(String userId); //通过userId删除用户信息
    int userInfoUpdate(UserInfo userInfo); //修改用户
    UserInfo getUserInfoById(String userId); //通过userId查找用户信息
    List<UserInfo> getUserInfoList(String userId,int page,int limit); //通过userId模糊匹配

    //_______________________用户信息模块调用的方法____________________________________
    int getUserInfoCountForUser(String userId); //获取用户数量
}
