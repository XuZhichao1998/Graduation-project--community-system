package com.example.test.controller;

import com.example.test.entity.ReportRepair;
import com.example.test.entity.ServiceActivity;
import com.example.test.service.ReportRepairService;
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
import java.util.List;

@Controller
public class ReportRepairController {
    //将Service注入Web层
    @Autowired
    private ReportRepairService reportRepairService;


    /**
     * 进入报修管理页面的接口
     * 通过Session中是否有curAdminName值进行访问权限控制和安全检查
     * */
    @RequestMapping("/reportRepairList1")
    public String reportRepairList1(HttpServletRequest request) {
        if(request.getSession().getAttribute("curAdminName")==null)
        {
            return "404"; //没有管理员登陆，妄图访问投诉管理页面，直接跳到404
        }
        return "reportRepairListPage";
    }

    /**
     * 查询报修列表
     * @return
     */
    @RequestMapping("/getReportRepairList")
    @ResponseBody
    public LayuiData getReportRepairList (HttpServletRequest request){
        int page = Integer.parseInt(request.getParameter("page"));
        int limit = Integer.parseInt(request.getParameter("limit"));
        if(page>0){
            page = (page-1)*limit;
        }
        String reportRepairId = request.getParameter("reportRepairId");
        int rid = -1;
        try{
            rid = Integer.parseInt(reportRepairId);
        }
        catch(Exception ex) {
            rid = -1;
        }
        LayuiData layuiData = new LayuiData();
        if(reportRepairId==null||reportRepairId=="") {
            List<ReportRepair> reportRepairList = reportRepairService.getReportRepairList(page,limit);
            layuiData.setData(reportRepairList);
        }
        else {
            List<ReportRepair> reportRepairList = reportRepairService.getReportRepairListById(rid,page,limit);
            layuiData.setData(reportRepairList);
        }
        int count = reportRepairService.getReportRepairCount();
        layuiData.setCode(0);
        layuiData.setCount(count);
        layuiData.setMsg("数据请求成功");
        return layuiData;
    }

    /**
     * 到转到新增报修信息页面
     * @return
     */
    @RequestMapping("/toReportRepair")
    public String toReportRepair (){
        return "reportRepairAdd";  //去到reportRepairAdd.html
    }

    /**
     * 新增
     * @param reportUserId
     * @param reportProblem
     * @return
     */
    @RequestMapping("/reportRepairAdd")
    @Transactional
    @ResponseBody
    public Integer reportRepairAdd (String reportUserId, String reportProblem) {
        ReportRepair bean = new ReportRepair();
        bean.setReportUserId(reportUserId);
        bean.setReportProblem(reportProblem);
        int num = reportRepairService.addReportRepair(bean);
        return num;
    }

    /**
     * 根据reportRepairId删除报修信息
     * @param reportRepairId
     * @return
     */
    @RequestMapping("/deleteReportRepair")
    @ResponseBody
    public Integer deleteReportRepair(Integer reportRepairId) {
        int num = reportRepairService.deleteReportRepair(reportRepairId);
        return num;
    }


    /**
     * 去查看界面
     * @param reportRepairId
     * @param model
     * @return
     */
    @RequestMapping("/toReportRepairDetail")
    public String toReportRepairDetail(Integer reportRepairId, Model model) {
        ReportRepair reportRepair = reportRepairService.getReportRepairById(reportRepairId);
        model.addAttribute("reportRepair",reportRepair);
        return "reportRepairDetail";
    }

    /**
     * 去修改界面
     * @param reportRepairId
     * @param model
     * @return
     */
    @RequestMapping("/toUpdateReportRepair")
    public String toUpdateReportRepair(Integer reportRepairId, Model model) {
        ReportRepair reportRepair = reportRepairService.getReportRepairById(reportRepairId);
        model.addAttribute("reportRepair",reportRepair);
        return "reportRepairUpdate";
    }

    /**
     * 根据id修改商品信息
     * @return
     */
    @RequestMapping("/reportRepairUpdate")
    @Transactional
    @ResponseBody
    public int reportRepairUpdate (@RequestParam("reportRepairId")Integer rid, @RequestParam("reportUserId")String uid,
                                   @RequestParam("reportProblem") String pro, @RequestParam("reportDatetime") Timestamp subdt,
                                   @RequestParam("reportState")String state, @RequestParam("repairFinishDatetime") String rdt){
        Timestamp repairDate = null;
        if(rdt!="") {
            rdt += " 09:00:00";
            repairDate = Timestamp.valueOf(rdt);
        }

        ReportRepair bean = new ReportRepair(rid,uid,pro,subdt,state,repairDate);
        int num = reportRepairService.updateReportRepair(bean);
        return num;
    }

    //__________________________以下是用户报修模块调用的方法_______________________________________________________
    /**
     * 进入用户报修管理页面的接口
     * 通过Session中是否有curUserId值进行访问权限控制和安全检查
     * */
    @RequestMapping("/u_reportRepairList1")
    public String u_reportRepairList1(HttpServletRequest request) {
        if(request.getSession().getAttribute("curUserId")==null)
        {
            return "404"; //没有用户登录，无法访问页面，直接跳到404
        }
        return "u_reportRepairListPage";
    }


    /**
     * 用户查询报修列表
     * @return
     */

    @RequestMapping("/getU_ReportRepairList")
    @ResponseBody
    public LayuiData getU_ReportRepairList (HttpServletRequest request){
        int page = Integer.parseInt(request.getParameter("page"));
        int limit = Integer.parseInt(request.getParameter("limit"));
        String reportUserId = (String)(request.getSession().getAttribute("curUserId"));
        if(page>0){
            page = (page-1)*limit;
        }
        String reportRepairId = request.getParameter("reportRepairId");
        int rid = -1;
        try{
            rid = Integer.parseInt(reportRepairId);
        }
        catch(Exception ex) {
            rid = -1;
        }
        LayuiData layuiData = new LayuiData();
        if(reportRepairId==null||reportRepairId=="") {
            List<ReportRepair> reportRepairList = reportRepairService.getReportRepairListForUser(reportUserId,page,limit);
            layuiData.setData(reportRepairList);
        }
        else {
            List<ReportRepair> reportRepairList = reportRepairService.getReportRepairListByIdForUser(reportUserId,rid,page,limit);
            layuiData.setData(reportRepairList);
        }
        int count = reportRepairService.getReportRepairCountForUser(reportUserId);
        layuiData.setCode(0);
        layuiData.setCount(count);
        layuiData.setMsg("数据请求成功");
        return layuiData;
    }

    /**
     * 到转到用户新增报修信息页面
     * @return
     */
    @RequestMapping("/toU_ReportRepair")
    public String toU_ReportRepair (){
        return "u_reportRepairAdd";  //去到u_reportRepairAdd.html
    }

 


}
