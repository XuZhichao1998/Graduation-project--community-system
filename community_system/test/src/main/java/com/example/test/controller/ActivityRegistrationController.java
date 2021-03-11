package com.example.test.controller;

import com.example.test.entity.ActivityRegistration;
import com.example.test.service.ActivityRegistrationService;
import com.example.test.util.LayuiData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class ActivityRegistrationController {
    //将Service注入Web层
    @Autowired
    private ActivityRegistrationService activityRegistrationService;

    /**
     * 进入党员下沉志愿活动报名信息查看的接口
     * 通过Session中是否有curAdminName值进行访问权限控制和安全检查
     * */
    @RequestMapping("/activityRegistrationList1")
    public String activityRegistrationList1(HttpServletRequest request) {
        if(request.getSession().getAttribute("curAdminName")==null)
        {
            return "404"; //没有管理员登陆，妄图访问投诉管理页面，直接跳到404
        }
        return "activityRegistrationListPage";
    }

    /**
     * 查询活动报名列表
     * @return
     */
    @RequestMapping("/getActivityRegistrationList")
    @ResponseBody
    public LayuiData getActivityRegistrationList (HttpServletRequest request){
        int page = Integer.parseInt(request.getParameter("page"));
        int limit = Integer.parseInt(request.getParameter("limit"));
        Integer pid = null;
        String str_pid = request.getParameter("pid");
        try{
            if (str_pid != null && str_pid != "") {
                pid = Integer.parseInt(str_pid);
            }
        }
        catch (Exception exception) {
            System.out.print(exception.toString());
        }

        String volunteerName = request.getParameter("volunteerName");
        if(page>0){
            page = (page-1)*limit;
        }
        LayuiData layuiData = new LayuiData();

        List<ActivityRegistration> activityRegistrationList = activityRegistrationService.getActivityRegistrationList(pid,volunteerName,page,limit);
        layuiData.setData(activityRegistrationList);

        int count = activityRegistrationService.getActivityRegistrationCount();
        layuiData.setCode(0);
        layuiData.setCount(count);
        layuiData.setMsg("数据请求成功");
        return layuiData;
    }

    /**
     * 到转到新增活动报名信息页面
     * @return
     */
    @RequestMapping("/toActivityRegistration")
    public String toActivityRegistration (){
        return "activityRegistrationAdd";  //activityRegistrationAdd.html
    }


    /**
     * 志愿者进行活动报名
     * @return
     * */
    /**
     * 到转到新增活动报名信息页面
     * @param pid
     * @return
     */

    @RequestMapping("/v_toActivityRegistration")
    public String v_toActivityRegistration (Integer pid, Model model, HttpServletRequest request){
        String volunteerId = (String)request.getSession().getAttribute("curvolunteerid");
        String volunteerName = (String)request.getSession().getAttribute("curvolunteername");
        ActivityRegistration bean = new ActivityRegistration();
        bean.setVolunteerId(volunteerId);
        bean.setVolunteerName(volunteerName);
        bean.setPid(pid);
        model.addAttribute("activityRegistration",bean);

        return "v_activityRegistrationAdd";  //v_activityRegistrationAdd.html
    }

    /**
     * 新增
     * @param pid
     * @param volunteerId
     * @param volunteerName
     * @return
     */
    @RequestMapping("/activityRegistrationAdd")
    @Transactional
    @ResponseBody
    public Integer activityRegistrationAdd (Integer pid, String volunteerId, String volunteerName) {
        System.out.println("pid:["+pid.toString()+"],volunteer:["+volunteerId+"],volunteerName:["+volunteerName+"]");
        ActivityRegistration bean = new ActivityRegistration();
        bean.setPid(pid);
        bean.setVolunteerId(volunteerId);
        bean.setVolunteerName(volunteerName);
        int num = activityRegistrationService.addActivityRegistration(bean);
        return num;
    }

    /**
     * 根据pid和volunteerId删除报修信息
     * @param pid
     * @param volunteerId
     * @return
     */
    @RequestMapping("/deleteActivityRegistration")
    @ResponseBody
    public Integer deleteActivityRegistration(Integer pid, String volunteerId) {
        int num = activityRegistrationService.deleteActivityRegistration(pid, volunteerId);
        return num;
    }

    /**
     * 去查看界面
     * @param pid
     * @param volunteerId
     * @param model
     * @return
     */
    @RequestMapping("/toActivityRegistrationDetail")
    public String toActivityRegistrationDetail(Integer pid, String volunteerId, Model model) {
        ActivityRegistration activityRegistration = activityRegistrationService.getActivityRegistrationByIds(pid, volunteerId);
        model.addAttribute("activityRegistration",activityRegistration);
        return "activityRegistrationDetail";
    }

    /**
     * 去修改界面
     * @param pid
     * @param volunteerId
     * @param model
     * @return
     */
    @RequestMapping("/toUpdateActivityRegistration")
    public String toUpdateActivityRegistration(Integer pid, String volunteerId, Model model) {
        ActivityRegistration activityRegistration = activityRegistrationService.getActivityRegistrationByIds(pid, volunteerId);
        model.addAttribute("activityRegistration",activityRegistration);
        return "activityRegistrationUpdate";
    }

    /**
     * 根据pid和volunteerId修改活动报名信息(审核)
     * @param pid
     * @param volunteerId
     * @param  approval
     * @return
     */

    @RequestMapping("/activityRegistrationUpdate")
    @Transactional
    @ResponseBody
    public int activityRegistrationUpdate(Integer pid,String volunteerId,String approval) {
        System.out.println("pid["+pid.toString()+"] volunteerId["+volunteerId+"] approval["+approval+"]");
        ActivityRegistration bean = new ActivityRegistration();
        bean.setPid(pid);
        bean.setVolunteerId(volunteerId);
        bean.setApproval(approval);
        int num = activityRegistrationService.updateActivityRegistration(bean);
        return num;
    }

    //________________志愿者查看党员下沉志愿活动报名需要的方法______________________
    /**
     * 进入党员下沉志愿活动报名信息查看的接口
     * 通过Session中是否有curAdminName值进行访问权限控制和安全检查
     * */
    @RequestMapping("/v_activityRegistrationList1")
    public String v_activityRegistrationList1(HttpServletRequest request) {
        if(request.getSession().getAttribute("curvolunteerid")==null) {
            return "404"; //没有党员志愿者登陆，不能访问相应的页面
        }
        return "v_activityRegistrationListPage";
    }

    /**
     * 查询该党员志愿者报名过的活动的报名列表
     * @return
     */
    @RequestMapping("/v_getActivityRegistrationList")
    @ResponseBody
    public LayuiData v_getActivityRegistrationList (HttpServletRequest request) {
        int page = Integer.parseInt(request.getParameter("page"));
        int limit = Integer.parseInt(request.getParameter("limit"));
        String volunteerId = (String)request.getSession().getAttribute("curvolunteerid");
        System.out.println("####################******************!!!!!!!!curvolunteerId:["+volunteerId+"]");
        if(page>0){
            page = (page-1)*limit;
        }
        LayuiData layuiData = new LayuiData();

        List<ActivityRegistration> activityRegistrationList = activityRegistrationService.getActivityRegistrationListForVolunteer(volunteerId, page, limit);
        layuiData.setData(activityRegistrationList);

        int count = activityRegistrationService.getActivityRegistrationCountForVolunteer(volunteerId);
        layuiData.setCode(0);
        layuiData.setCount(count);
        layuiData.setMsg("数据请求成功");
        return layuiData;
    }



}
