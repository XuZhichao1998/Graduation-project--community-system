package com.example.test.controller;

import com.example.test.entity.TemporaryAccessApplication;
import com.example.test.service.TemporaryAccessApplicationService;
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
public class TemporaryAccessApplicationController {
    //将Service注入Web层
    @Autowired
    private TemporaryAccessApplicationService temporaryAccessApplicationService;

    /**
     * 进入临时出入申请管理页面的接口
     * 通过Session中是否有curAdminName值进行访问权限控制和安全检查
     * */
    @RequestMapping("/temporaryAccessApplicationList1")
    public String temporaryAccessApplicationList1(HttpServletRequest request) {
        if(request.getSession().getAttribute("curAdminName")==null)
        {
            return "404"; //没有管理员登陆，妄图访问投诉管理页面，直接跳到404
        }
        return "temporaryAccessApplicationListPage";
    }

    /**
     * 查询临时出入申请列表
     *@return
     */
    @RequestMapping("/getTemporaryAccessApplicationList")
    @ResponseBody
    public LayuiData getTemporaryAccessApplicationList (HttpServletRequest request){
        int page = Integer.parseInt(request.getParameter("page"));
        int limit = Integer.parseInt(request.getParameter("limit"));
        String personName = request.getParameter("personName");
        System.out.println("personName:["+personName+"]");
        if (personName == null) {
            personName = "";
        }
        LayuiData layuiData = new LayuiData();
        List<TemporaryAccessApplication> temporaryAccessApplicationList = temporaryAccessApplicationService.getTemporaryAccessApplicationList(personName,page,limit);
        layuiData.setData(temporaryAccessApplicationList);
        int count = temporaryAccessApplicationService.getTemporaryAccessApplicationCount();
        layuiData.setCode(0);
        layuiData.setCount(count);
        layuiData.setMsg("数据请求成功");
        return layuiData;
    }

    /**
     * 到转到新增临时出入申请页面
     * @return
     */
    @RequestMapping("/toTemporaryAccessApplication")
    public String toTemporaryAccessApplication (){
        return "temporaryAccessApplicationAdd";  //去到temporaryAccessApplicationAdd.html
    }

    /**
     * 新增
     * @param personName
     * @param personId
     * @param phoneNumber
     * @param carNumber
     * @param reason
     * @param estimatedEnterTime
     * @param estimatedLeaveTime
     * @return
     */
    @RequestMapping("/temporaryAccessApplicationAdd")
    @Transactional
    @ResponseBody
    public Integer temporaryAccessApplicationAdd (
        String personName,
        String personId,
        String phoneNumber,
        String carNumber,
        String reason,
        String estimatedEnterTime,
        String estimatedLeaveTime
    ) {
        StringBuilder strBuilder2 = new StringBuilder(estimatedEnterTime);
        strBuilder2.setCharAt(10, ' ');
        estimatedEnterTime = strBuilder2.toString();
        estimatedEnterTime = estimatedEnterTime + ":00";


        StringBuilder strBuilder = new StringBuilder(estimatedLeaveTime);
        strBuilder.setCharAt(10, ' ');
        estimatedLeaveTime = strBuilder.toString();
        estimatedLeaveTime = estimatedLeaveTime + ":00";

        Timestamp enterTime = null;
        Timestamp leaveTime = null;
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try{
            enterTime = Timestamp.valueOf(sdf.format(sdf.parse(estimatedEnterTime)));
            leaveTime = Timestamp.valueOf(sdf.format(sdf.parse(estimatedLeaveTime)));

        } catch(Exception exception) {
            System.out.println("日期转换异常："+exception.toString());
        }

        if (carNumber==null || carNumber=="") {
            carNumber = "无";
        }

        TemporaryAccessApplication bean = new TemporaryAccessApplication();
        bean.setPersonName(personName);
        bean.setPersonId(personId);
        bean.setPhoneNumber(phoneNumber);
        bean.setCarNumber(carNumber);
        bean.setReason(reason);
        bean.setEstimatedEnterTime(enterTime);
        bean.setEstimatedLeaveTime(leaveTime);
        int num = temporaryAccessApplicationService.addTemporaryAccessApplication(bean);
        return num;
    }

    /**
     * 根据applicationId删除报修信息
     * @param applicationId
     * @return
     */
    @RequestMapping("/deleteTemporaryAccessApplication")
    @ResponseBody
    public Integer deleteTemporaryAccessApplication(Integer applicationId) {
        int num = temporaryAccessApplicationService.deleteTemporaryAccessApplication(applicationId);
        return num;
    }

    /**
     * 去查看界面
     * @param applicationId
     * @param model
     * @return
     */
    @RequestMapping("/toTemporaryAccessApplicationDetail")
    public String toTemporaryAccessApplicationDetail(Integer applicationId, Model model) {
        TemporaryAccessApplication temporaryAccessApplication = temporaryAccessApplicationService.getTemporaryAccessApplicationById(applicationId);
        model.addAttribute("temporaryAccessApplication",temporaryAccessApplication);
        return "temporaryAccessApplicationDetail";
    }

    /**
     * 去修改界面
     * @param applicationId
     * @param model
     * @return
     */
    @RequestMapping("/toUpdateTemporaryAccessApplication")
    public String toUpdateReportRepair(Integer applicationId, Model model) {
        TemporaryAccessApplication temporaryAccessApplication = temporaryAccessApplicationService.getTemporaryAccessApplicationById(applicationId);
        model.addAttribute("temporaryAccessApplication",temporaryAccessApplication);
        return "temporaryAccessApplicationUpdate";
    }

    /**
     * 根据applicationId修改临时出入申请单的审核状态
     * @return
     */
    @RequestMapping("/temporaryAccessApplicationUpdate")
    @Transactional
    @ResponseBody
    public int temporaryAccessApplicationUpdate (
            @RequestParam("applicationId")Integer applicationId,
            @RequestParam("approval")String approval,
            HttpServletRequest request
    ){
        String approvalManagerName = (String) request.getSession().getAttribute("curAdminName");
        TemporaryAccessApplication bean = new TemporaryAccessApplication();
        bean.setApplicationId(applicationId);
        bean.setApproval(approval);
        bean.setApprovalManagerName(approvalManagerName);
        int num = temporaryAccessApplicationService.updateTemporaryAccessApplication(bean);
        return num;
    }

    //____________外来人员(游客身份)登录系统，和预约查询临时出入社区申请_____________________________________________

    /**
     * 进入外来人员(游客)预约临时出入社区的接口
     * 通过Session中是否有guestPersonId值进行访问权限控制和安全检查
     * */
    @RequestMapping("/g_temporaryAccessApplicationList1")
    public String g_temporaryAccessApplicationList1(HttpServletRequest request) {
        if(request.getSession().getAttribute("guestPersonId")==null) {
            return "404"; //没有临时游客登录，跳到404
        }
        return "g_temporaryAccessApplicationListPage";  // g_temporaryAccessApplicationListPage.html
    }

    /**
     * 查询游客的所有临时申请表
     * @return
     */

    @RequestMapping("/getG_TemporaryAccessApplicationList")
    @ResponseBody
    public LayuiData getG_TemporaryAccessApplicationList (HttpServletRequest request){
        int page = Integer.parseInt(request.getParameter("page"));
        int limit = Integer.parseInt(request.getParameter("limit"));
        String personId = (String)(request.getSession().getAttribute("guestPersonId"));
        String personName = (String)(request.getSession().getAttribute("guestPersonName"));
        if(page>0){
            page = (page-1)*limit;
        }

        LayuiData layuiData = new LayuiData();
        List<TemporaryAccessApplication> temporaryAccessApplicationList = temporaryAccessApplicationService.getApplicationListForGuest(personId,personName,page,limit);
        layuiData.setData(temporaryAccessApplicationList);

        int count = temporaryAccessApplicationService.getApplicationCountForGuest(personId, personName);
        layuiData.setCode(0);
        layuiData.setCount(count);
        layuiData.setMsg("数据请求成功");
        return layuiData;
    }

    /**
     * 转到游客新增临时出入预约页面
     * @return
     */
    @RequestMapping("/toG_TemporaryAccessApplication")
    public String toG_TemporaryAccessApplication () {
        return "g_temporaryAccessApplicationAdd";  //去到g_temporaryAccessApplicationAdd.html
    }

}
