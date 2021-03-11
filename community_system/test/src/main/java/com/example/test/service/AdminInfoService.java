package com.example.test.service;

import com.example.test.entity.AdminInfo;

public interface AdminInfoService {
    AdminInfo admin_loginIn(String AdminName, String AdminPwd);
}
