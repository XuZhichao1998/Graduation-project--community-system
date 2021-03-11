package com.example.test.entity;

import java.sql.Timestamp;

public class ActivityRegistration { // 活动报名
    private int pid; // 党员志愿活动编号
    private String pName; // 党员志愿活动的编号
    private String volunteerId; // 党员志愿者的身份证号
    private String volunteerName; // 党员志愿者的姓名
    private String approval; // 报名审核状态："待审核" or "已通过" or "人数已满"
    private Timestamp volunteerRegistTime; // 党员志愿者报名的时间

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public ActivityRegistration(int pid, String pName, String volunteerId, String volunteerName, String approval, Timestamp volunteerRegistTime) {
        this.pid = pid;
        this.pName = pName;
        this.volunteerId = volunteerId;
        this.volunteerName = volunteerName;
        this.approval = approval;
        this.volunteerRegistTime = volunteerRegistTime;
    }

    public ActivityRegistration() {}

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getVolunteerId() {
        return volunteerId;
    }

    public void setVolunteerId(String volunteerId) {
        this.volunteerId = volunteerId;
    }

    public String getVolunteerName() {
        return volunteerName;
    }

    public void setVolunteerName(String volunteerName) {
        this.volunteerName = volunteerName;
    }

    public String getApproval() {
        return approval;
    }

    public void setApproval(String approval) {
        this.approval = approval;
    }

    public Timestamp getVolunteerRegistTime() {
        return volunteerRegistTime;
    }

    public void setVolunteerRegistTime(Timestamp volunteerRegistTime) {
        this.volunteerRegistTime = volunteerRegistTime;
    }
}
