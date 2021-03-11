package com.example.test.controller;

import com.example.test.entity.Parking;
import com.example.test.service.ParkingService;
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
public class ParkingController {
    //将Service注入web层
    @Autowired
    private ParkingService parkingService;

    /**
     * parkingListPage页面的进入请求为”/parkingList1“
     * */
    @RequestMapping("/parkingList1")
    public String parkingList1(HttpServletRequest request) {
        if(request.getSession().getAttribute("curAdminName")==null)
        {
            return "404"; //没有管理员登陆，妄图访问投诉管理页面，直接跳到404
        }
        return "parkingListPage";
    }

    /**
     * 按照车牌号模糊查询所有的车位信息
     * */
    @RequestMapping("/getParkingList")
    @ResponseBody
    public LayuiData getParkingList (HttpServletRequest request){
        String parkingCarNumber = request.getParameter("parkingCarNumber");
        if(parkingCarNumber==null){
            parkingCarNumber = "";
        }
        int page = Integer.parseInt(request.getParameter("page"));
        int limit = Integer.parseInt(request.getParameter("limit"));
        if(page>0){
            page = (page-1)*limit;
        }
        LayuiData layuiData = new LayuiData();
        List<Parking> parkingList = parkingService.getParkingList(parkingCarNumber,page,limit);
        int count = parkingService.getParkingCount();
        layuiData.setCode(0);
        layuiData.setCount(count);
        layuiData.setMsg("数据请求成功");
        layuiData.setData(parkingList);
        return layuiData;
    }


    /**
     * 去查看界面
     * @param parkingId
     * @param model
     * @return
     */
    @RequestMapping("/toParkingDetail")
    public String toDetail(String parkingId, Model model) {

        Parking parking = parkingService.getParkingById(parkingId);
        model.addAttribute("parking",parking);
        return "parkingDetail";
    }

    /**
     * 去修改界面
     * @param parkingId
     * @param model
     * @return
     */
    @RequestMapping("/toParkingUpdate")
    public String toUpdate(String parkingId, Model model) {

        Parking parking = parkingService.getParkingById(parkingId);
        model.addAttribute("parking",parking);
        return "parkingUpdate";
    }

    /**
     * 根据parkingId修改车位信息
     * @return
     */

    @RequestMapping("/parkingUpdate")
    @Transactional
    @ResponseBody
    public int parkingUpdate (@RequestParam("parkingId")Integer pid, @RequestParam("parkingPlaceDescription")String pdes, @RequestParam("parkingCarNumber")String pcno){
        Parking parking = new Parking(pid,pdes,pcno);
        int num = parkingService.updateParking(parking);
        return num;
    }

    //_______________________________________用户停车位查看模块调用的方法__________________________________________
    /**
     * u_parkingListPage页面的进入请求为”/u_parkingList1“
     * */
    @RequestMapping("/u_parkingList1")
    public String u_parkingList1(HttpServletRequest request) {
        if(request.getSession().getAttribute("curUserId")==null)
        {
            return "404"; //没有用户登录，不能查看停车位，跳到404
        }
        return "u_parkingListPage";
    }

}
