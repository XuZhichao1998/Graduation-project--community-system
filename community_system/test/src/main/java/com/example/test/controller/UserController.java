package com.example.test.controller;

import com.example.test.entity.UserInfo;
import com.example.test.service.UserInfoService;
import com.example.test.util.LayuiData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class UserController {
    //将Service注入web层
    @Autowired
    private UserInfoService userInfoService;


    /**
     * 用户管理的入口请求，用session进行访问控制，非管理员登陆不能访问
     * */
    @RequestMapping("/userInfoList1")
    public String userInfoList1(HttpServletRequest request) {
        if(request.getSession().getAttribute("curAdminName")==null)
        {
            return "404_admin_login_expired"; //没有管理员登陆，妄图访问投诉管理页面，直接跳到404
        }
        return "userListPage";
    }

    /**
     * 查询用户列表
     * */
    @RequestMapping("/getUserInfoList")
    @ResponseBody
    public LayuiData getUserInfoList (HttpServletRequest request){
        String userId = request.getParameter("userId");
        if(userId == null) {
            userId = "";
        }
        int page = Integer.parseInt(request.getParameter("page"));
        int limit = Integer.parseInt(request.getParameter("limit"));
        if(page>0){
            page = (page-1)*limit;
        }
        LayuiData layuiData = new LayuiData();
        List<UserInfo> userInfoList = userInfoService.getUserInfoList(userId,page,limit);
        int count = userInfoService.getUserInfoCount();
        layuiData.setCode(0);
        layuiData.setCount(count);
        layuiData.setMsg("数据请求成功");
        layuiData.setData(userInfoList);
        return layuiData;
    }

    /**
     * 去新增用户界面
     * @return
     */
    @RequestMapping("/toUserInfo")
    public String toUserInfo (){
        return "userAdd";  //去到productAdd.html
    }

    /**
     * 去查看界面
     * @param userId
     * @param model
     * @return
     */
    @RequestMapping("/toUserInfoDetail")
    public String toUserInfoDetail(String userId, Model model) {
        UserInfo userInfo = userInfoService.getUserInfoById(userId);
        model.addAttribute("userInfo",userInfo);
        return "userDetail";
    }

    /**
     * 根据userId删除用户
     * @param userId
     * @return
     */
    @RequestMapping("/deleteUserInfo")
    @ResponseBody
    public Integer deleteUserInfo(String userId) {
        int num = userInfoService.userInfoDelete(userId);
        return num;
    }

    /**
     * 用户信息修改界面
     * @return
     * @param userId
     * @param model
     * */
    @RequestMapping("/toUpdateUserInfo")
    public String toUpdateUserInfo(String userId, Model model) {
        UserInfo userInfo = userInfoService.getUserInfoById(userId);
        model.addAttribute("userInfo",userInfo);
        return "userUpdate";
    }
    @RequestMapping("/toU_UpdateUserInfo")
    public String toU_UpdateUserInfo(Model model,HttpServletRequest request) {
        String userId = (String) request.getSession().getAttribute("curUserId");
        UserInfo userInfo = userInfoService.getUserInfoById(userId);
        model.addAttribute("userInfo",userInfo);
        return "u_userUpdate";
    }
    @RequestMapping("/u_updateUserInfo1")
    public String u_updateUserInfo1(@RequestParam("userId")String uid, @RequestParam("userName")String uname,
                                    @RequestParam("userSex")String sex, @RequestParam("userPhoneNumber") String phone,
                                    @RequestParam("userEmail") String email, @RequestParam("userPassword") String pwd,
                                    @RequestParam("userIdentityNumber") String idn, @RequestParam("householdId") Integer hid,
                                    HttpServletRequest request
    ) {
        UserInfo userInfo = new UserInfo(uid,uname,pwd,idn,sex,phone,email,hid);
        int num = userInfoService.userInfoUpdate(userInfo);
        if(num!=0)
            System.out.println("num = "+num+"，修改成功！");
        else
            System.out.println("num = "+num+", 修改失败！");
        if(num!=0) {
            request.getSession().setAttribute("curUseInfo",userInfo);
            return "u_updateUserInfoSuccess";
        }
        else
            return "u_updateUserInfoFail";
    }


    /**
     * 根据userId去修改用户信息
     * @return
     */
    @RequestMapping("/userInfoUpdate")
    @Transactional
    @ResponseBody
    public int userInfoUpdate (@RequestParam("userId")String uid, @RequestParam("userName")String uname,
                               @RequestParam("userSex")String sex, @RequestParam("userPhoneNumber") String phone,
                               @RequestParam("userEmail") String email, @RequestParam("userPassword") String pwd,
                               @RequestParam("userIdentityNumber") String idn, @RequestParam("householdId") Integer hid) {
        UserInfo userInfo = new UserInfo(uid,uname,pwd,idn,sex,phone,email,hid);
        int num = userInfoService.userInfoUpdate(userInfo);
        return num;
    }


    /**
     * 新增
     * @return
     */
    @RequestMapping("/userInfoAdd")
    @Transactional
    @ResponseBody
    public int userInfoAdd (@RequestParam("userId")String uid, @RequestParam("userName")String uname,
                            @RequestParam("userSex")String sex, @RequestParam("userPhoneNumber") String phone,
                            @RequestParam("userEmail") String email, @RequestParam("userPassword") String pwd,
                            @RequestParam("userIdentityNumber") String idn, @RequestParam("bn") Integer bn,
                            @RequestParam("un") Integer un,@RequestParam("rn") Integer rn) {
        System.out.println("进入了/userInfoAdd");
        int fn = rn/100,hn = rn%10;
        int hid = (bn-1)*45+(un-1)*15+(fn-1)*3+hn;
        UserInfo userInfo = new UserInfo(uid,uname,pwd,idn,sex,phone,email,hid);
        int num = userInfoService.userInfoAdd(userInfo);
        return num;
    }


    /**
     * 用户注册模块
     * */
    @RequestMapping(value = "/userRegisterCheck",method = RequestMethod.POST)
//    public String userRegisterCheck(@RequestParam("uid") String uid,@RequestParam("uname") String uname,
//                                    @RequestParam("pwd") String pwd,@RequestParam("idn") String idn,@RequestParam("sex") String sex,
//    @RequestParam("phone") String phone,@RequestParam("email") String email,@RequestParam("bn") Integer bn,@RequestParam("un") Integer un,
//                                    @RequestParam("rn") Integer rn)
//    {
    public String userRegisterCheck(String uid,String uname,String pwd,String idn,String sex,String phone,String email,Integer bn,Integer un,Integer rn) {
        int fn = rn/100,hn = rn%10;
        int hid = (bn-1)*45+(un-1)*15+(fn-1)*3+hn;
        System.out.println(uid);
        System.out.println(uname);
        System.out.println(pwd);
        System.out.println(idn);
        System.out.println(sex);
        System.out.println(phone);
        System.out.println(email);
        System.out.println(bn);
        System.out.println(un);
        System.out.println(rn);
        UserInfo userInfo = new UserInfo(uid,uname,pwd,idn,sex,phone,email,hid);
        System.out.println(userInfo.showMsg());
        //int num = userRegisterService.userRegister(userInfo);
        int num = userInfoService.userRegister(userInfo);
        System.out.println(num);
        if(num==0) //注册失败
        {
            return "404";
        }
        else { //注册成功
            System.out.println("注册成功！");
            return "login";
        }

    }

    @RequestMapping("/userRegister")
    public String userRegister() {
        return "register";
    }


    //____________________________________用户个人信息模块调用的方法__________________________________

    /**
     * 用户个人信息管理的入口请求，用session进行访问控制，非用户登陆不能访问
     * */
    @RequestMapping("/u_userInfoList1")
    public String toUserInfoList1(Model model,HttpServletRequest request) {
        if(request.getSession().getAttribute("curUserId")==null) {
            return "404"; //没有管理员登陆，妄图访问投诉管理页面，直接跳到404
        }
        String userId = (String)request.getSession().getAttribute("curUserId");
        UserInfo userInfo = userInfoService.getUserInfoById(userId);
        model.addAttribute("userInfo",userInfo);
        return "u_userListPage";
    }

    /**
     * 查询用户列表
     * */
    @RequestMapping("/getU_UserInfoList")
    @ResponseBody
    public LayuiData getU_UserInfoList (HttpServletRequest request){
        String userId = (String) request.getSession().getAttribute("curUserId");
        int page = Integer.parseInt(request.getParameter("page"));
        int limit = Integer.parseInt(request.getParameter("limit"));
        if(page>0) {
            page = (page-1)*limit;
        }
        LayuiData layuiData = new LayuiData();
        List<UserInfo> userInfoList = userInfoService.getUserInfoList(userId,page,limit);
        int count = userInfoService.getUserInfoCountForUser(userId);
        layuiData.setCode(0);
        layuiData.setCount(count);
        layuiData.setMsg("数据请求成功");
        layuiData.setData(userInfoList);
        return layuiData;
    }


}
