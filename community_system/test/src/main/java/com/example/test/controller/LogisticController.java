package com.example.test.controller;

import com.example.test.entity.Logistic;
import com.example.test.service.LogisticService;
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
import java.util.List;

@Controller
public class LogisticController {
    //将Service注入到Controller
    @Autowired
    private LogisticService logisticService;

    /**
     * 进入物流管理页面的接口
     * 通过Session中是否有curAdminName值进行访问权限控制和安全检查
     * */
    @RequestMapping("/logisticList1")
    public String logisticList1(HttpServletRequest request) {
        if(request.getSession().getAttribute("curAdminName")==null)
        {
            return "404_admin_login_expired"; //没有管理员登陆，妄图访问投诉管理页面，直接跳到404
        }
        return "logisticListPage";
    }


    /**
     * 查询物流信息列表
     * @return
     */
    @RequestMapping("/getLogisticList")
    @ResponseBody
    public LayuiData getLogisticList (HttpServletRequest request){
        int page = Integer.parseInt(request.getParameter("page"));
        int limit = Integer.parseInt(request.getParameter("limit"));
        if(page>0){
            page = (page-1)*limit;
        }
        String logisticId = request.getParameter("logisticId");
        int Lid = -1;
        try{
            Lid = Integer.parseInt(logisticId);
        }
        catch(Exception ex) {
            Lid = -1;
        }
        LayuiData layuiData = new LayuiData();
        if(logisticId==null||logisticId=="") {
            List<Logistic> logisticList = logisticService.getLogisticList(page,limit);
            layuiData.setData(logisticList);
        }
        else {
            List<Logistic> logisticList = logisticService.getLogisticListById(Lid,page,limit);
            layuiData.setData(logisticList);
        }
        int count = logisticService.getLogisticCount();
        layuiData.setCode(0);
        layuiData.setCount(count);
        layuiData.setMsg("数据请求成功");
        return layuiData;
    }


    /**
     * 到转到新增物流信息页面
     * @return
     */
    @RequestMapping("/toLogistic")
    public String toLogistic (){
        return "logisticAdd";  //去到logisticAdd.html
    }



    /**
     * 新增
     *
     *
     * @return
     */
    @RequestMapping("/logisticAdd")
    @Transactional
    @ResponseBody
    public Integer logisticAdd (@RequestParam("userId") String uid
            , @RequestParam("senderName") String sName
            , @RequestParam("senderPhoneNumber") String sPhone
            , @RequestParam("senderProvince") String sProv
            , @RequestParam("senderCity") String sCity
            , @RequestParam("senderDistrict") String sDist
            , @RequestParam("senderPostalCode") String sPcode
            , @RequestParam("senderDetailedAddress") String sAddress
            , @RequestParam("receiverName") String rName
            , @RequestParam("receiverPhoneNumber") String rPhone
            , @RequestParam("receiverProvince") String rProv
            , @RequestParam("receiverCity") String rCity
            , @RequestParam("receiverDistrict") String rDist
            , @RequestParam("receiverPostalCode") String rPcode
            , @RequestParam("receiverDetailedAddress") String rAddress
            , @RequestParam("logisticType") Integer logisticType
    ) {
        //System.out.println("进入了logisticAdd()方法");
        Logistic bean = new Logistic();
        if(logisticType==1) {
            bean.setLogisticMoney(new BigDecimal(10));
        }
        else if(logisticType==7||logisticType==8) {
            bean.setLogisticMoney(new BigDecimal(20));
        }
        else {
            bean.setLogisticMoney(new BigDecimal(13));
        }
        bean.setLogisticType(logisticType);

        bean.setUserId(uid);
        bean.setSenderName(sName);
        bean.setSenderPhoneNumber(sPhone);
        bean.setSenderProvince(sProv);
        bean.setSenderCity(sCity);
        bean.setSenderDistrict(sDist);
        bean.setSenderPostalCode(sPcode);
        bean.setSenderDetailedAddress(sAddress);
        bean.setReceiverName(rName);
        bean.setReceiverPhoneNumber(rPhone);
        bean.setReceiverProvince(rProv);
        bean.setReceiverCity(rCity);
        bean.setReceiverDistrict(rDist);
        bean.setReceiverPostalCode(rPcode);
        bean.setReceiverDetailedAddress(rAddress);
        bean.setLogisticType(logisticType);
        bean.setTypeNameById();
        int num = logisticService.addLogistic(bean);
        return num;
    }


    /**
     * 根据paymentId删除报修信息
     * @param logisticId
     * @return
     */
    @RequestMapping("/deleteLogistic")
    @ResponseBody
    public Integer deleteLogistic(Integer logisticId) {
        int num = logisticService.deleteLogistic(logisticId);
        return num;
    }


    /**
     * 去查看界面
     * @param logisticId
     * @param model
     * @return
     */
    @RequestMapping("/toLogisticDetail")
    public String toLogisticDetail(Integer logisticId, Model model) {
        Logistic logistic = logisticService.getLogisticById(logisticId);
        model.addAttribute("logistic",logistic);
        return "logisticDetail";
    }

    /**
     * 去修改界面
     * @param logisticId
     * @param model
     * @return
     */
    @RequestMapping("/toUpdateLogistic")
    public String toUpdateLogistic(Integer logisticId, Model model) {
        Logistic logistic = logisticService.getLogisticById(logisticId);
        model.addAttribute("logistic",logistic);
        return "logisticUpdate";
    }

    /**
     * 根据id修改商品信息
     * @return
     */
    @RequestMapping("/logisticUpdate")
    @Transactional
    @ResponseBody
    public int logisticUpdate (@RequestParam("logisticMoney") BigDecimal money
            , @RequestParam("courierId") Integer cid
            , @RequestParam("logisticLastState") String state
            , @RequestParam("logisticFinish") Integer finish
            , @RequestParam("logisticId") Integer logisticId
                               ) {
        if(finish==null) {
            finish = 0;
        }
        Logistic bean = new Logistic();
        bean.setLogisticId(logisticId);
        bean.setLogisticMoney(money);
        bean.setCourierId(cid);
        bean.setLogisticLastState(state);
        bean.setLogisticFinish(finish);
        int num = logisticService.updateLogistic(bean);
        return num;
    }

    //_____________________________以下是用户模块的方法__________________________________

    /**
     * 用户进入物流管理页面的接口
     * 通过Session中是否有curAdminName值进行访问权限控制和安全检查
     * */
    @RequestMapping("/u_logisticList1")
    public String u_logisticList1(HttpServletRequest request) {
        if(request.getSession().getAttribute("curUserId")==null)
        {
            return "404_user_login_expired"; //用户没登陆就想通过url进入物流信息查看界面，被禁止
        }
        return "u_logisticListPage";
    }


    /**
     * 到转到用户物流下单页面
     * @return
     */
    @RequestMapping("/toU_Logistic")
    public String toU_Logistic (){
        return "u_logisticAdd";  //去到paymentAdd.html
    }


    /**
     * 查询本用户的物流信息列表
     * @return
     */
    @RequestMapping("/getU_LogisticList")
    @ResponseBody
    public LayuiData getU_LogisticList (HttpServletRequest request){
        int page = Integer.parseInt(request.getParameter("page"));
        int limit = Integer.parseInt(request.getParameter("limit"));
        String userId = (String) request.getSession().getAttribute("curUserId");
        if(page>0){
            page = (page-1)*limit;
        }
        String logisticId = request.getParameter("logisticId");
        int Lid = -1;
        try{
            Lid = Integer.parseInt(logisticId);
        }
        catch(Exception ex) {
            Lid = -1;
        }
        LayuiData layuiData = new LayuiData();
        if(logisticId==null||logisticId=="") {
            List<Logistic> logisticList = logisticService.getLogisticListForUser(userId,page,limit);
            layuiData.setData(logisticList);
        }
        else {
            List<Logistic> logisticList = logisticService.getLogisticListByIdForUser(userId,Lid,page,limit);
            layuiData.setData(logisticList);
        }
        int count = logisticService.getLogisticCountForUser(userId);
        layuiData.setCode(0);
        layuiData.setCount(count);
        layuiData.setMsg("数据请求成功");
        return layuiData;
    }




}
