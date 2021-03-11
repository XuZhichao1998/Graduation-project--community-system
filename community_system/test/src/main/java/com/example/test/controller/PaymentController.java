package com.example.test.controller;

import com.example.test.entity.Payment;
import com.example.test.entity.UserInfo;
import com.example.test.service.PaymentService;
import com.example.test.util.LayuiData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Controller
public class PaymentController {
    //将Service注入Web层
    @Autowired
    private PaymentService paymentService;

    /**
     * 进入缴费管理页面的接口
     * 通过Session中是否有curAdminName值进行访问权限控制和安全检查
     * */
    @RequestMapping("/paymentList1")
    public String paymentList1(HttpServletRequest request) {
        if(request.getSession().getAttribute("curAdminName")==null)
        {
            return "404"; //没有管理员登陆，妄图访问投诉管理页面，直接跳到404
        }
        return "paymentListPage";
    }


    /**
     * 查询缴费列表
     * @return
     */
    @RequestMapping("/getPaymentList")
    @ResponseBody
    public LayuiData getPaymentList (HttpServletRequest request){
        int page = Integer.parseInt(request.getParameter("page"));
        int limit = Integer.parseInt(request.getParameter("limit"));
        if(page>0){
            page = (page-1)*limit;
        }
        String paymentId = request.getParameter("paymentId");
        int pid = -1;
        try{
            pid = Integer.parseInt(paymentId);
        }
        catch(Exception ex) {
            pid = -1;
        }
        LayuiData layuiData = new LayuiData();
        if(paymentId==null||paymentId=="") {
            List<Payment> paymentList = paymentService.getPaymentList(page,limit);
            layuiData.setData(paymentList);
        }
        else {
            List<Payment> paymentList = paymentService.getPaymentListById(pid,page,limit);
            layuiData.setData(paymentList);
        }
        int count = paymentService.getPaymentCount();
        layuiData.setCode(0);
        layuiData.setCount(count);
        layuiData.setMsg("数据请求成功");
        return layuiData;
    }

    /**
     * 到转到新增缴费信息页面
     * @return
     */
    @RequestMapping("/toPayment")
    public String toPayment (){
        return "paymentAdd";  //去到paymentAdd.html
    }

    /**
     * 新增
     *
     *
     * @return
     */
    @RequestMapping("/paymentAdd")
    @Transactional
    @ResponseBody
    public Integer paymentAdd (@RequestParam("householdId") Integer hid, @RequestParam("paymentAmount") BigDecimal amn,
                               @RequestParam("paymentDeadline") String ddl, @RequestParam("paymentType") String pty) {
        Timestamp pddl = null;
        if(ddl!="") {
            ddl += " 18:00:00";
            pddl = Timestamp.valueOf(ddl);
        }
        Payment bean = new Payment();
        bean.setHouseholdId(hid);
        bean.setPaymentAmount(amn);
        bean.setPaymentDeadline(pddl);
        bean.setPaymentType(pty);
        int num = paymentService.addPayment(bean);
        return num;
    }

    /**
     * 根据paymentId删除报修信息
     * @param paymentId
     * @return
     */
    @RequestMapping("/deletePayment")
    @ResponseBody
    public Integer deletePayment(Integer paymentId) {
        int num = paymentService.deletePayment(paymentId);
        return num;
    }

    /**
     * 去查看界面
     * @param paymentId
     * @param model
     * @return
     */
    @RequestMapping("/toPaymentDetail")
    public String toPaymentDetail(Integer paymentId, Model model) {
        Payment payment = paymentService.getPaymentById(paymentId);
        model.addAttribute("payment",payment);
        return "paymentDetail";
    }

    /**
     * 去修改界面
     * @param paymentId
     * @param model
     * @return
     */
    @RequestMapping("/toUpdatePayment")
    public String toUpdatePayment(Integer paymentId, Model model) {
        Payment payment = paymentService.getPaymentById(paymentId);
        model.addAttribute("payment",payment);
        return "paymentUpdate";
    }

    /**
     * 根据id修改商品信息
     * @return
     */
    @RequestMapping("/paymentUpdate")
    @Transactional
    @ResponseBody
    public int PaymentUpdate (@RequestParam("paymentId")Integer pid, @RequestParam("paymentAmount")BigDecimal amt,
                              @RequestParam("paymentType") String pty, @RequestParam("paymentState")Integer state,
                              @RequestParam("paymentFinishTime") String fdt) {
        Timestamp pfdt = null;
        if(fdt!="") {
            fdt += " 14:00:00";
            pfdt = Timestamp.valueOf(fdt);
        }

        Payment bean = new Payment();
        bean.setPaymentId(pid);
        bean.setPaymentType(pty);
        bean.setPaymentState(state);
        bean.setPaymentAmount(amt);
        bean.setPaymentFinishTime(pfdt);
        int num = paymentService.updatePayment(bean);
        return num;
    }

    //__________________________以下是用户缴费模块调用的方法________________________________________

    /**
     * 用户进入缴费查看页面的接口
     * 通过Session中是否有curUserInfo值进行访问权限控制和安全检查
     * */
    @RequestMapping("/u_paymentList1")
    public String u_paymentList1(HttpServletRequest request) {
        if(request.getSession().getAttribute("curUserInfo")==null)
        {
            return "404"; //没有管理员登陆，妄图访问投诉管理页面，直接跳到404
        }
        return "u_paymentListPage";
    }


    /**
     * 用户查询缴费列表
     * @return
     */
    @RequestMapping("/getU_PaymentList")
    @ResponseBody
    public LayuiData getU_PaymentList (HttpServletRequest request){
        int page = Integer.parseInt(request.getParameter("page"));
        int limit = Integer.parseInt(request.getParameter("limit"));
        if(page>0){
            page = (page-1)*limit;
        }
        String paymentId = (String)request.getParameter("paymentId");
        Integer curHouseholdId = ((UserInfo)request.getSession().getAttribute("curUserInfo")).getHouseholdId();
        int pid = -1;
        try{
            pid = Integer.parseInt(paymentId);
        }
        catch(Exception ex) {
            pid = -1;
        }
        LayuiData layuiData = new LayuiData();
        if(paymentId==null||paymentId=="") {
            List<Payment> paymentList = paymentService.getPaymentListForUser(curHouseholdId,page,limit);
            layuiData.setData(paymentList);
        }
        else {
            List<Payment> paymentList = paymentService.getPaymentListByIdForUser(curHouseholdId,pid,page,limit);
            layuiData.setData(paymentList);
        }
        int count = paymentService.getPaymentCountForUser(curHouseholdId);
        layuiData.setCode(0);
        layuiData.setCount(count);
        layuiData.setMsg("数据请求成功");
        return layuiData;
    }

}
