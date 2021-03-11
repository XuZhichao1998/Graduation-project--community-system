package com.example.test.serviceImpl;

import com.example.test.entity.AdminInfo;
import com.example.test.mapper.AdminInfoMapper;
import com.example.test.service.AdminInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminInfoServiceImpl implements AdminInfoService {
    //将DAO注入Service层
    @Autowired
    private AdminInfoMapper AdminInfoMapper;

    @Override
    public AdminInfo admin_loginIn(String AdminName, String AdminPwd) {
        return AdminInfoMapper.getInfo(AdminName,AdminPwd);
    }
}
