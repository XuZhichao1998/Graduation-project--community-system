package com.example.test.controller;

import com.example.test.entity.CooperationUnit;
import com.example.test.service.CooperationUnitService;
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
public class CooperationUnitController {
    //将Service注入web层
    @Autowired
    private CooperationUnitService cooperationUnitService;

    /**
     * 合作单位管理页面的入口请求
     * 通过Session进行权限访问控制
     * */
    @RequestMapping("/cooperationUnitList1")
    public String cooperationUnitList1(HttpServletRequest request) {
        if(request.getSession().getAttribute("curAdminName")==null) {
            return "404_admin_login_expired"; //没有管理员登陆，妄图访问投诉管理页面，直接跳到404
        }
        return "cooperationUnitListPage";
    }

    /**
     * 管理员查询合作单位的列表
     * @return
     */
    @RequestMapping("/getCooperationUnitList")
    @ResponseBody
    public LayuiData getCooperationUnitList (HttpServletRequest request){
        int page = Integer.parseInt(request.getParameter("page"));
        int limit = Integer.parseInt(request.getParameter("limit"));
        String unitName = request.getParameter("unitName");
        if (unitName == null) {
            unitName = "";
        }
        if(page>0){
            page = (page-1)*limit;
        }
        LayuiData layuiData = new LayuiData();

        List<CooperationUnit> cooperationUnitList = cooperationUnitService.getCooperationUnitList(unitName, page, limit);
        layuiData.setData(cooperationUnitList);

        int count = cooperationUnitService.getCooperationUnitCount();
        layuiData.setCode(0);
        layuiData.setCount(count);
        layuiData.setMsg("数据请求成功");
        return layuiData;
    }

    /**
     * 到转到新增合作单位页面
     * @return
     */
    @RequestMapping("/toCooperationUnit")
    public String toCooperationUnit (){
        return "cooperationUnitAdd";  //去到cooperationUnitAdd.html
    }

    /**
     * 新增合作单位(党组织部)
     *
     *
     * @return
     */
    @RequestMapping("/cooperationUnitAdd")
    @Transactional
    @ResponseBody
    public Integer cooperationUnitAdd (@RequestParam("unitName") String unitName) {
        CooperationUnit bean = new CooperationUnit();
        bean.setUnitName(unitName);
        int num = cooperationUnitService.addCooperationUnit(bean);
        return num;
    }

    /**
     * 根据unitId删除合作单位(取消合作)
     * @param unitId
     * @return
     */
    @RequestMapping("/deleteCooperationUnit")
    @ResponseBody
    public Integer deleteCooperationUnit(Integer unitId) {
        int num = cooperationUnitService.deleteCooperationUnit(unitId);
        return num;
    }

    /**
     * 去查看合作单位信息界面
     * @param unitId
     * @param model
     * @return
     */
    @RequestMapping("/toCooperationUnitDetail")
    public String toCooperationUnitDetail(Integer unitId, Model model) {
        CooperationUnit cooperationUnit = cooperationUnitService.getCooperationUnitById(unitId);
        model.addAttribute("cooperationUnit",cooperationUnit);
        return "cooperationUnitDetail";
    }

}
