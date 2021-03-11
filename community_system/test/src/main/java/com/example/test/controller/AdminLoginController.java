package com.example.test.controller;

import com.example.test.entity.AdminInfo;
import com.example.test.service.AdminInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@Controller
public class AdminLoginController {

   //将Service注入Web层
    @Autowired
    AdminInfoService adminInfoService1;

    @RequestMapping("/admin_login")
    public String show() { return "admin_login"; }

    @RequestMapping("/to_index_admin")
    public String to_admin_index() {
        return "index_admin";
    }

    @RequestMapping(value = "/admin_loginIn",method = RequestMethod.POST)
    public String login(String AdminName, String AdminPwd, HttpServletRequest request){
        AdminInfo userBean = adminInfoService1.admin_loginIn(AdminName,AdminPwd);

        if(userBean!=null){
            System.out.println(userBean.showMsg());
            request.getSession().setAttribute("curAdminName",AdminName);
            return "index_admin";
        }
        else {
            System.out.println("Bean为null");
            return "404_admin";
        }
    }

    @RequestMapping("adminLogOut")
    public String adminLogOut(HttpServletRequest request) {
        request.getSession().removeAttribute("curAdminName");
        return "admin_login"; //admin_login.html
    }



}
