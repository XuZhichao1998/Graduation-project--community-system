package com.example.test.controller;

import com.example.test.entity.UserInfo;
import com.example.test.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LoginController {

    //将Service注入Web层
    @Autowired
    UserInfoService userInfoService1;

    @RequestMapping("/login")
    public String show(){
        return "login";
    }

    @RequestMapping("/guestLogin")
    public String guestLogin() {
        return "guest_login";
    }

//loginIn是网页的请求
    @RequestMapping(value = "/loginIn",method = RequestMethod.POST)
    public String login(String name, String password, HttpServletRequest request){
        UserInfo userBean = userInfoService1.loginIn(name,password);

        if(userBean!=null){
            System.out.println(userBean.showMsg());
            request.getSession().setAttribute("curUserId",name);
            request.getSession().setAttribute("curUserInfo",userBean); //存入一个对象类型(object)
            return "index_user";  //跳转到index_user.html 用户主界面
        }
        else {
            System.out.println("Bean为null");
            return "404"; //跳转到404.html
        }
    }

    @RequestMapping(value = "/guest_loginIn",method = RequestMethod.POST)
    public String guest_loginIn(String personName, String personId, HttpServletRequest request){
        if (personName=="" || personName==null || personId=="" || personId==null) {
            System.out.println("游客姓名或者身份证号为空，不能登录！");
            return "404"; //跳转到404.html
        }
        else {
            request.getSession().setAttribute("guestPersonId", personId);
            request.getSession().setAttribute("guestPersonName", personName); //存入一个对象类型(object)
            return "g_temporaryAccessApplicationListPage";  //跳转到index_user.html 用户主界面
        }
    }

    @RequestMapping("/to_index_user")
    public String to_index_user() {
        return "index_user";
    }

    @RequestMapping("userLogOut")
    public String userLogOut(HttpServletRequest request) {
        request.getSession().removeAttribute("curUserId");
        return "login"; //login.html
    }

    //_________________以下是党员志愿者登录要用到的方法_____________________
    @RequestMapping("/volunteerLogin")
    public String volunteerLogin() {
        return "volunteer_login";
    }

    @RequestMapping(value = "/volunteer_loginIn",method = RequestMethod.POST)
    public String volunteer_loginIn(String vname, String vid, HttpServletRequest request){
        if (vname=="" || vname==null || vid=="" || vid==null) {
            System.out.println("党员志愿者姓名或者身份证号为空，不能登录！");
            return "404"; //跳转到404.html
        }
        else {
            request.getSession().setAttribute("curvolunteerid", vid);
            request.getSession().setAttribute("curvolunteername", vname); //存入一个对象类型(object)
            return "v_activityRegistrationListPage";  //跳转到v_activityRegistrationListPage.html 查看党员所有报名过的活动
        }
    }
}

