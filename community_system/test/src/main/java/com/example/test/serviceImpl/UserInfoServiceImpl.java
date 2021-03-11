package com.example.test.serviceImpl;

import com.example.test.entity.UserInfo;
import com.example.test.mapper.UserInfoMapper;
import com.example.test.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserInfoServiceImpl implements UserInfoService {
    //将DAO注入Service层
    @Autowired
    private UserInfoMapper userInfoMapper;


    @Override
    public int userRegister(UserInfo userInfo) {
        return userInfoMapper.userInfoAdd(userInfo);
    }

    @Override
    public UserInfo loginIn(String name, String password) {
        UserInfo result = userInfoMapper.getInfo(name,password);
        System.out.println("ServiceImpl:");
        if(result==null)
        {
            System.out.println("null!");
        }
        else
        {
            System.out.println(result.showMsg());
        }
        return result;

    }

    @Override
    public int getUserInfoCount() {
        return userInfoMapper.getUserInfoCount();
    }

    @Override
    public int userInfoAdd(UserInfo userInfo) {
        return userInfoMapper.userInfoAdd(userInfo);
    }

    @Override
    public int userInfoDelete(String userId) {
        return userInfoMapper.userInfoDelete(userId);
    }

    @Override
    public int userInfoUpdate(UserInfo userInfo) {
        return userInfoMapper.userInfoUpdate(userInfo);
    }

    @Override
    public UserInfo getUserInfoById(String userId) {
        return userInfoMapper.getUserInfoById(userId);
    }

    @Override
    public List<UserInfo> getUserInfoList(String userId, int page, int limit) {
        return userInfoMapper.getUserInfoList(userId,page,limit);
    }

    @Override
    public int getUserInfoCountForUser(String userId) {
        return userInfoMapper.getUserInfoCountForUser(userId);
    }
}
