package com.example.test.controller;

import com.example.test.entity.Announcement;
import com.example.test.service.AnnouncementService;
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
public class AnnouncementController {
    //将Service注入web层
    @Autowired
    private AnnouncementService announcementService;

    /**
     * 公告管理页面的入口请求
     * 通过Session进行权限访问控制
     * */
    @RequestMapping("/announcementList1")
    public String announcementList1(HttpServletRequest request) {
        if(request.getSession().getAttribute("curAdminName")==null)
        {
            return "404_admin_login_expired"; //没有管理员登陆，妄图访问投诉管理页面，直接跳到404_admin_login_expired.html
        }
        return "announcementListPage";
    }

    /**
     * 管理员查询公告列表
     * @return
     */
    @RequestMapping("/getAnnouncementList")
    @ResponseBody
    public LayuiData getAnnouncementList (HttpServletRequest request){
        int page = Integer.parseInt(request.getParameter("page"));
        int limit = Integer.parseInt(request.getParameter("limit"));
        if(page>0){
            page = (page-1)*limit;
        }
        String announcementId = request.getParameter("announcementId");
        int aid = -1;
        try{
            aid = Integer.parseInt(announcementId);
        }
        catch(Exception ex) {
            aid = -1;
        }
        LayuiData layuiData = new LayuiData();
        if(announcementId==null||announcementId=="") {
            List<Announcement> announcementList = announcementService.getAnnouncementList(page,limit);
            layuiData.setData(announcementList);
        }
        else {
            List<Announcement> announcementList = announcementService.getAnnouncementListById(aid,page,limit);
            layuiData.setData(announcementList);
        }
        int count = announcementService.getAnnouncementCount();
        layuiData.setCode(0);
        layuiData.setCount(count);
        layuiData.setMsg("数据请求成功");
        return layuiData;
    }

    /**
     * 到转到新增公告信息页面
     * @return
     */
    @RequestMapping("/toAnnouncement")
    public String toAnnouncement (){
        return "announcementAdd";  //去到announcementAdd.html
    }



    /**
     * 新增公告
     *
     *
     * @return
     */
    @RequestMapping("/announcementAdd")
    @Transactional
    @ResponseBody
    public Integer announcementAdd (@RequestParam("announcementContent") String announcementContent) {
        Announcement bean = new Announcement();
        bean.setAnnouncementContent(announcementContent);
        int num = announcementService.addAnnouncement(bean);
        return num;
    }

    /**
     * 根据announcementId删除报修信息
     * @param announcementId
     * @return
     */
    @RequestMapping("/deleteAnnouncement")
    @ResponseBody
    public Integer deleteAnnouncement(Integer announcementId) {
        int num = announcementService.deleteAnnouncement(announcementId);
        return num;
    }

    /**
     * 去查看公告界面
     * @param announcementId
     * @param model
     * @return
     */
    @RequestMapping("/toAnnouncementDetail")
    public String toAnnouncementDetail(Integer announcementId, Model model) {
        Announcement announcement = announcementService.getAnnouncementById(announcementId);
        model.addAttribute("announcement",announcement);
        return "announcementDetail";
    }


    //__________________________以下是用户查公告模块调用的方法____________________________________
    /**
     * 用户公告页面的入口请求
     * 通过Session进行权限访问控制
     * */
    @RequestMapping("/u_announcementList1")
    public String u_announcementList1(HttpServletRequest request) {
        if(request.getSession().getAttribute("curUserId")==null)
        {
            return "404_user_login_expired"; //用户没登录，直接跳到404
        }
        return "u_announcementListPage";
    }


}
