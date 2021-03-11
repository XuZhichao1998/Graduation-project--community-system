package com.example.test.controller;

import com.example.test.entity.ServiceActivity;
import com.example.test.service.ServiceActivityService;
import com.example.test.util.LayuiData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

@Controller
public class ServiceActivityController {
    //将Service注入Web层
    @Autowired
    private ServiceActivityService serviceActivityService;

    /**
     * 进入志愿活动管理页面的接口
     * 通过Session中是否有curAdminName值进行访问权限控制和安全检查
     * */
    @RequestMapping("/serviceActivityList1")
    public String serviceActivityList1(HttpServletRequest request) {
        if(request.getSession().getAttribute("curAdminName")==null)
        {
            return "404"; //没有管理员登陆，妄图访问志愿活动管理页面，直接跳到404
        }
        return "serviceActivityListPage"; // serviceActivityListPage.html
    }

    /**
     * 查询活动列表
     * @return
     */
    @RequestMapping("/getServiceActivityList")
    @ResponseBody
    public LayuiData getServiceActivityList (HttpServletRequest request){

        System.out.println("rid = ["+request.getParameter("pid")+"]");
        int page = Integer.parseInt(request.getParameter("page"));
        int limit = Integer.parseInt(request.getParameter("limit"));
        if(page>0){
            page = (page-1)*limit;
        }
        String pid = request.getParameter("pid");
        int rid = -1;
        try{
            rid = Integer.parseInt(pid);
        }
        catch(Exception ex) {
            rid = -1;
        }
        LayuiData layuiData = new LayuiData();
        if (pid == null || pid == "") {
            List<ServiceActivity> activityList = serviceActivityService.getActivityList(page,limit);
            layuiData.setData(activityList);
        }
        else {
            List<ServiceActivity> activityList = serviceActivityService.getActivityListById(rid,page,limit);
            layuiData.setData(activityList);
        }
        int count = serviceActivityService.getActivityCount();
        layuiData.setCode(0);
        layuiData.setCount(count);
        layuiData.setMsg("数据请求成功");
        return layuiData;
    }


    /**
     * 转到新增志愿活动的页面
     * @return
     */
    @RequestMapping("/toServiceActivity")
    public String toServiceActivity (){
        return "serviceActivityAdd";  //去到serviceActivityAdd.html
    }

    /**
     * 新增
     * @param projectName
     * @param content
     * @param beginTime
     * @param registrationDeadline
     * @param recruitmentNumbers
     * @param requirement
     * @param projectStatus
     * @return
     */
    @RequestMapping("/serviceActivityAdd")
    @Transactional
    @ResponseBody
    public Integer serviceActivityAdd (String projectName, String content, String beginTime, String registrationDeadline,
                                       Integer recruitmentNumbers, String requirement, String projectStatus) {
        System.out.println("beginTime "+beginTime+" rddl"+registrationDeadline);


        StringBuilder strBuilder = new StringBuilder(beginTime);
        strBuilder.setCharAt(10,' ');
        beginTime = strBuilder.toString();

        StringBuilder strBuilder2 = new StringBuilder(registrationDeadline);
        strBuilder2.setCharAt(10, ' ');
        registrationDeadline = strBuilder2.toString();
        beginTime = beginTime + ":00";
        registrationDeadline = registrationDeadline + ":00";

        System.out.println("转换后：beginTime["+beginTime+"] ddl["+registrationDeadline+"]");


        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Timestamp tm_beginTime = new Timestamp(System.currentTimeMillis());
        Timestamp tm_ddl = new Timestamp(System.currentTimeMillis());

        try {
            tm_beginTime = Timestamp.valueOf(sdf.format(sdf.parse(beginTime)));
            tm_ddl = Timestamp.valueOf(sdf.format(sdf.parse(registrationDeadline)));
        } catch(Exception exception) {
            System.out.println("----------------\n时间日期格式转换错误\n---------------------\n");
        }

        ServiceActivity bean = new ServiceActivity();
        bean.setProjectName(projectName);
        bean.setContent(content);
        bean.setBeginTime(tm_beginTime);
        bean.setRegistrationDeadline(tm_ddl);
        bean.setRecruitmentNumbers(recruitmentNumbers);
        bean.setRequirement(requirement);
        bean.setProjectStatus(projectStatus);
        int num = serviceActivityService.addActivity(bean);
        return num;
    }

    /**
     * 根据pid删除志愿活动信息
     * @param pid
     * @return
     */
    @RequestMapping("/deleteServiceActivity")
    @ResponseBody
    public Integer deleteServiceActivity(Integer pid) {
        int num = serviceActivityService.deleteActivity(pid);
        return num;
    }


    /**
     * 去查看界面
     * @param pid
     * @param model
     * @return
     */
    @RequestMapping("/toServiceActivityDetail")
    public String toServiceActivityDetail(Integer pid, Model model) {
        ServiceActivity bean = serviceActivityService.getActivityById(pid);
        model.addAttribute("serviceActivity", bean);
        return "serviceActivityDetail";
    }

    /**
     * 去修改界面
     * @param pid
     * @param model
     * @return
     */
    @RequestMapping("/toUpdateServiceActivity")
    public String toUpdateServiceActivity(Integer pid, Model model) {
        ServiceActivity bean = serviceActivityService.getActivityById(pid);
        model.addAttribute("serviceActivity", bean);
        return "serviceActivityUpdate";
    }

    /**
     * 根据pid修改活动信息
     * @return
     */
    @RequestMapping("/serviceActivityUpdate")
    @Transactional
    @ResponseBody
    public int serviceActivityUpdate (
            @RequestParam("pid")Integer pid,
            @RequestParam("projectName")String projectName,
            @RequestParam("content") String content,
            //@RequestParam("beginTime") Timestamp beginTime,
            //@RequestParam("registrationDeadline") Timestamp registrationDeadline,
            @RequestParam("recruitmentNumbers")Integer numbers,
            @RequestParam("requirement") String requirement,
            @RequestParam("projectStatus") String projectStatus
    ) {
        ServiceActivity bean = new ServiceActivity(pid, projectName, content, null, null, numbers,
                                                   requirement, projectStatus);
        int num = serviceActivityService.updateServiceActivity(bean);
        return num;
    }


    //__________________党员志愿者查看、报名志愿活动的方法_________________________
    /**
     * 进入党员查看志愿活动页面的接口
     * 通过Session中是否有curvolunteerid值进行访问权限控制和安全检查
     * */
    @RequestMapping("/v_serviceActivityList1")
    public String v_serviceActivityList1(HttpServletRequest request) {
        if(request.getSession().getAttribute("curvolunteerid")==null) {
            return "404"; //党员志愿者没有登陆，session中没有对象，不能访问页面
        }
        return "v_serviceActivityListPage"; // v_serviceActivityListPage.html
    }

    /**
     * 党员志愿者查询活动列表和自己的报名状态
     * @return
     */
    @RequestMapping("/v_getServiceActivityList")
    @ResponseBody
    public LayuiData v_getServiceActivityList (HttpServletRequest request){

        int page = Integer.parseInt(request.getParameter("page"));
        int limit = Integer.parseInt(request.getParameter("limit"));
        if(page>0){
            page = (page-1)*limit;
        }
        String volunteerId = (String)request.getSession().getAttribute("curvolunteerid");

        LayuiData layuiData = new LayuiData();
        List<ServiceActivity> activityList = serviceActivityService.getActivityListForVolunteer(volunteerId, page, limit);
        layuiData.setData(activityList);
        int count = serviceActivityService.getActivityCount();
        layuiData.setCode(0);
        layuiData.setCount(count);
        layuiData.setMsg("数据请求成功");
        return layuiData;
    }

    /**
     * 去查看界面
     * @param pid
     * @param model
     * @return
     */
    @RequestMapping("/v_toServiceActivityDetail")
    public String v_toServiceActivityDetail(Integer pid, Model model, HttpServletRequest request) {
        String volunteerId = (String)request.getSession().getAttribute("curvolunteerid");
        ServiceActivity bean = serviceActivityService.getActivityByIdForVolunteer(pid, volunteerId);
        model.addAttribute("serviceActivity", bean);
        return "v_serviceActivityDetail";  // v_serviceActivityDetail.html
    }




}
