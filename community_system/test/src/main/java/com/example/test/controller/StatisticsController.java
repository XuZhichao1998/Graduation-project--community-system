package com.example.test.controller;

import com.example.test.entity.UnitTotalDuration;
import com.example.test.entity.VolunteerTotalDuration;
import com.example.test.service.StatisticsService;
import com.example.test.util.LayuiData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class StatisticsController {
    //将Service注入web层
    @Autowired
    private StatisticsService statisticsService;

    /**
     * 进入个人服务总时长排名页面的接口
     * */
    @RequestMapping("/volunteerTotalDurationList1")
    public String reportRepairList1(HttpServletRequest request) {
        return "volunteerTotalDurationListPage";
    }

    /**
     * 查询个人服务总时长排名前三
     * @return
     */
    @RequestMapping("/getVolunteerTotalDurationList")
    @ResponseBody
    public LayuiData getVolunteerTotalDurationList (HttpServletRequest request){
        LayuiData layuiData = new LayuiData();
        List<VolunteerTotalDuration> volunteerTotalDurationList = statisticsService.getVolunteerTotalDurationTop3List();
        layuiData.setData(volunteerTotalDurationList);
        int count = 3;
        layuiData.setCode(0);
        layuiData.setCount(count);
        layuiData.setMsg("数据请求成功");
        return layuiData;
    }

    /**
     * 进入各单位服务总时长排名页面的接口
     * */
    @RequestMapping("/unitTotalDurationList1")
    public String unitTotalDurationList1(HttpServletRequest request) {
        return "unitTotalDurationListPage";
    }

    /**
     * 查询各单位(党组织部)服务总时长
     * @return
     */
    @RequestMapping("/getUnitTotalDurationList")
    @ResponseBody
    public LayuiData getUnitTotalDurationList (HttpServletRequest request){
        int page = Integer.parseInt(request.getParameter("page"));
        int limit = Integer.parseInt(request.getParameter("limit"));
        if(page>0){
            page = (page-1)*limit;
        }
        //System.out.println("page:"+ request.getParameter("page") +", limit:"+request.getParameter("limit"));
        LayuiData layuiData = new LayuiData();
        List<UnitTotalDuration> unitTotalDurationList = statisticsService.getUnitTotalDurationList(page, limit);
        layuiData.setData(unitTotalDurationList);
        int count = statisticsService.getUnitTotalDurationCount();
        layuiData.setCode(0);
        layuiData.setCount(count);
        layuiData.setMsg("数据请求成功");
        return layuiData;
    }


}
