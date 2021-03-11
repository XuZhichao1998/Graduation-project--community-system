package com.example.test.controller;

import com.example.test.entity.Complaint;
import com.example.test.service.ComplaintService;
import com.example.test.util.LayuiData;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.List;

@Controller
public class ComplaintController {
    //将Service注入Web层
    @Resource
    private ComplaintService complaintService;

    /**
     * complaintListPage网页入口的请求
     * */
    @RequestMapping("/complaintList1")
    public String complaintList1(HttpServletRequest request) {
        if(request.getSession().getAttribute("curAdminName")==null)
        {
            return "404"; //没有管理员登陆，妄图访问投诉管理页面，直接跳到404
        }
        return "complaintListPage";
    }

    /**
     * 查询投诉列表
     * @return
     */
    @RequestMapping("/getComplaintList")
    @ResponseBody
    public LayuiData getComplaintList (HttpServletRequest request){
        String complaintId = request.getParameter("complaintId");
        int page = Integer.parseInt(request.getParameter("page"));
        int limit = Integer.parseInt(request.getParameter("limit"));
        if(page>0){
            page = (page-1)*limit;
        }
        LayuiData layuiData = new LayuiData();
        int cid = -1;
        try {
            cid = Integer.parseInt(complaintId);
        }
        catch(Exception ex) {
            cid = -1;
        }
        if(complaintId==null||complaintId=="") {
            List<Complaint> complaintList = complaintService.getComplaintList(page,limit);
            layuiData.setData(complaintList);
        }
        else {
            List<Complaint> complaintList = complaintService.getComplaintListById(cid,page,limit);
            layuiData.setData(complaintList);
        }
        int count = complaintService.getComplaintCount();
        layuiData.setCode(0);
        layuiData.setCount(count);
        layuiData.setMsg("数据请求成功");

        return layuiData;
    }


    /**
     * 根据complaintId删除投诉信息
     * @param complaintId
     * @return
     */
    @RequestMapping("/deleteComplaint")
    @ResponseBody
    public Integer deleteComplaint(Integer complaintId) {
        int num = complaintService.deleteComplaint(complaintId);
        return num;
    }

    /**
     * 去查看界面
     * @param complaintId
     * @param model
     * @return
     */
    @RequestMapping("/toComplaintDetail")
    public String toComplaintDetail(Integer complaintId, Model model) {
        Complaint complaint = complaintService.getComplaintById(complaintId);
        model.addAttribute("complaint",complaint);
        return "complaintDetail";
    }
    /**
     * 去修改界面
     * @param complaintId
     * @param model
     * @return
     */
    @RequestMapping("/toUpdateComplaint")
    public String toUpdateComplaint(Integer complaintId, Model model) {
        Complaint complaint = complaintService.getComplaintById(complaintId);
        model.addAttribute("complaint",complaint);
        return "complaintUpdate";
    }


    /**
     * 根据id修改投诉信息
     * 接收/complaintUpdate请求，获取从前端传来的修改后 数据的参数，然后进行update,返回受影响的行数
     * @return
     */
    @RequestMapping("/complaintUpdate")
    @Transactional
    @ResponseBody
    public int complaintUpdate (@RequestParam("complaintId")Integer cid, @RequestParam("complaintUserId")String uid,
                              @RequestParam("complaintType")String ctype, @RequestParam("complaintMessage") String msg,
                              @RequestParam("phoneNumber")String phone, @RequestParam("complaintDate")Timestamp dt,
                              @RequestParam("complaintState") String state) {

        Complaint complaint = new Complaint(cid,uid,ctype,msg,phone,dt,state);
        System.out.println(complaint.showMsg());
        int num = complaintService.updateComplaint(complaint);
        return num;
    }

    //____________________________________以下是用户投诉模块调用的方法_____________________________________________________
    /**
     * 用户进入投诉页面的入口请求
     * u_complaintListPage网页入口的请求
     * */
    @RequestMapping("/u_complaintList1")
    public String u_complaintList1(HttpServletRequest request) {
        if(request.getSession().getAttribute("curUserId")==null)
        {
            return "404"; //没有管理员登陆，妄图访问投诉管理页面，直接跳到404
        }
        return "u_complaintListPage";
    }


    /**
     * 用户查询自己的投诉列表
     * @return
     */
    @RequestMapping("/getU_ComplaintList")
    @ResponseBody
    public LayuiData getU_ComplaintList (HttpServletRequest request){
        String complaintId = request.getParameter("complaintId");
        int page = Integer.parseInt(request.getParameter("page"));
        int limit = Integer.parseInt(request.getParameter("limit"));
        String complaintUserId = (String) request.getSession().getAttribute("curUserId");
        if(page>0){
            page = (page-1)*limit;
        }
        LayuiData layuiData = new LayuiData();
        int cid = -1;
        try {
            cid = Integer.parseInt(complaintId);
        }
        catch(Exception ex) {
            cid = -1;
        }
        if(complaintId==null||complaintId=="") {
            List<Complaint> complaintList = complaintService.getComplaintListForUser(complaintUserId,page,limit);
            layuiData.setData(complaintList);
        }
        else {
            List<Complaint> complaintList = complaintService.getComplaintListByIdForUser(complaintUserId,cid,page,limit);
            layuiData.setData(complaintList);
        }
        int count = complaintService.getComplaintCountForUser(complaintUserId);
        layuiData.setCode(0);
        layuiData.setCount(count);
        layuiData.setMsg("数据请求成功");

        return layuiData;
    }

    /**
     * 去新增投诉页面
     * @return
     * */
    @RequestMapping("/toU_Complaint")
    public String toU_Complaint() {
        return "u_complaintAdd"; //跳转到 ”u_complaintAdd.html“
    }

    /**
     * 新增投诉
     * @param uid
     * @param complaintType
     * @param complaintMessage
     * @param phoneNumber
     * @return
     */
    @RequestMapping("/u_complaintAdd")
    @Transactional
    @ResponseBody
    public Integer u_complaintAdd (@RequestParam("complaintUserId") String uid
            , @RequestParam("complaintType") String complaintType
            , @RequestParam("complaintMessage") String complaintMessage
            , @RequestParam("phoneNumber") String phoneNumber
    ){
        System.out.println("进入了后台add");
        Complaint bean = new Complaint();
        bean.setComplaintUserId(uid);
        bean.setComplaintType(complaintType);
        bean.setComplaintMessage(complaintMessage);
        bean.setPhoneNumber(phoneNumber);
        int num = complaintService.addComplaint(bean);
        return num;
    }





}
