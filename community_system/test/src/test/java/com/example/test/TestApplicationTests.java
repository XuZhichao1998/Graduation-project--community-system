package com.example.test;

import com.example.test.entity.UserInfo;
import com.example.test.service.UserInfoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

//import org.junit.Test;

@RunWith(SpringRunner.class)
@SpringBootTest
class TestApplicationTests {
    @Autowired
    UserInfoService UserInfoService;

    @Test
    void contextLoads() {
        UserInfo userBean = UserInfoService.loginIn("a","a");
        System.out.println("该用户ID为：");
        System.out.println(userBean.getUserId());
    }
}
