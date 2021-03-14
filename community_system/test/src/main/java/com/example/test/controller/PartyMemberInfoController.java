package com.example.test.controller;

import com.example.test.entity.PartyMemberInfo;
import com.example.test.service.PartyMemberInfoService;
import com.example.test.util.LayuiData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class PartyMemberInfoController {
    //将Service注入Web层
    @Autowired
    private PartyMemberInfoService partyMemberInfoService;

    /**
     * 进入党员信息管理页面的接口
     * 通过Session中是否有curAdminName值进行访问权限控制和安全检查
     * */
    @RequestMapping("/partyMemberInfoList1")
    public String partyMemberInfoList1(HttpServletRequest request) {
        if(request.getSession().getAttribute("curAdminName")==null) {
            return "404_admin_login_expired"; //没有管理员登陆，妄图访问投诉管理页面，直接跳到404
        }
        return "partyMemberInfoListPage";
    }

    /**
     * 查询党员信息列表
     * @return
     */
    @RequestMapping("/getPartyMemberInfoList")
    @ResponseBody
    public LayuiData getPartyMemberInfoList (HttpServletRequest request){
        int page = Integer.parseInt(request.getParameter("page"));
        int limit = Integer.parseInt(request.getParameter("limit"));
        String fullName = request.getParameter("fullName");
        if (page>0){
            page = (page-1)*limit;
        }
        if (fullName == null) {
            fullName = "";
        }
        LayuiData layuiData = new LayuiData();
        List<PartyMemberInfo> partyMemberInfoList = partyMemberInfoService.getPartyMemberInfoList(fullName, page, limit);
        layuiData.setData(partyMemberInfoList);
        int count = partyMemberInfoService.getPartyMemberInfoCount();
        layuiData.setCode(0);
        layuiData.setCount(count);
        layuiData.setMsg("数据请求成功");
        return layuiData;
    }

    /**
     * 到转到新增党员信息页面
     * @return
     */
    @RequestMapping("/toPartyMemberInfo")
    public String toPartyMemberInfo (){
        return "partyMemberInfoAdd";  //partyMemberInfoAdd.html
    }

    /**
     * 新增
     * @param partyMemberId
     * @param fullName
     * @param unitId
     * @param sex
     * @return
     */
    @RequestMapping("/partyMemberInfoAdd")
    @Transactional
    @ResponseBody
    public Integer partyMemberInfoAdd (String partyMemberId, String fullName, Integer unitId, String sex) {
        PartyMemberInfo bean = new PartyMemberInfo(partyMemberId, fullName, unitId, sex);
        int num = partyMemberInfoService.addPartyMemberInfo(bean);
        return num;
    }

    /**
     * 根据partyMemberId删除报修信息
     * @param partyMemberId
     * @return
     */
    @RequestMapping("/deletePartyMemberInfo")
    @ResponseBody
    public Integer deletePartyMemberInfo(String partyMemberId) {
        int num = partyMemberInfoService.deletePartyMemberInfo(partyMemberId);
        return num;
    }

    /**
     * 去查看界面
     * @param partyMemberId
     * @param model
     * @return
     */
    @RequestMapping("/toPartyMemberInfoDetail")
    public String toPartyMemberInfoDetail(String partyMemberId, Model model) {
        PartyMemberInfo bean = partyMemberInfoService.getPartyMemberInfoById(partyMemberId);
        model.addAttribute("partyMemberInfo",bean);
        return "partyMemberInfoDetail";
    }

    /**
     * 去修改界面
     * @param partyMemberId
     * @param model
     * @return
     */
    @RequestMapping("/toUpdatePartyMemberInfo")
    public String toUpdatePartyMemberInfo(String partyMemberId, Model model) {
        PartyMemberInfo bean = partyMemberInfoService.getPartyMemberInfoById(partyMemberId);
        model.addAttribute("partyMemberInfo", bean);
        return "partyMemberInfoUpdate";
    }

    /**
     * 根据partyMemberId修改商品信息
     * @return
     */
    @RequestMapping("/partyMemberInfoUpdate")
    @Transactional
    @ResponseBody
    public int partyMemberInfoUpdate (@RequestParam("partyMemberId")String partyMemberId,
                                      @RequestParam("fullName")String fullName,
                                      @RequestParam("unitId") Integer unitId,
                                      @RequestParam("sex") String sex
    ) {
        PartyMemberInfo bean = new PartyMemberInfo(partyMemberId, fullName, unitId, sex);
        int num = partyMemberInfoService.updatePartyMemberInfo(bean);
        return num;
    }
}
