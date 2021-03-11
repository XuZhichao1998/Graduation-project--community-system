package com.example.test.entity;

import java.sql.Timestamp;

public class ServiceActivity {
    private int pid; // 活动项目编号
    private String projectName; // 活动开始时间
    private String content; // 活动内容
    private Timestamp beginTime; // 活动开始时间
    private Timestamp registrationDeadline; // 活动报名截止时间
    private int recruitmentNumbers; // 招募人数
    private String requirement; // 志愿者要求
    private String projectStatus; // 活动状态： "招募中"，"进行中", "已完结"
    //------------------------------------
    private String hasRegist;

    public String getHasRegist() {
        return hasRegist;
    }

    public void setHasRegist(String hasRegist) {
        this.hasRegist = hasRegist;
    }

    public ServiceActivity() {}

    public ServiceActivity(int pid, String projectName, String content, Timestamp beginTime, Timestamp registrationDeadline,
                           int recruitmentNumbers, String requirement, String projectStatus) {
        this.pid = pid;
        this.projectName = projectName;
        this.content = content;
        this.beginTime = beginTime;
        this.registrationDeadline = registrationDeadline;
        this.recruitmentNumbers = recruitmentNumbers;
        this.requirement = requirement;
        this.projectStatus = projectStatus;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Timestamp beginTime) {
        this.beginTime = beginTime;
    }

    public Timestamp getRegistrationDeadline() {
        return registrationDeadline;
    }

    public void setRegistrationDeadline(Timestamp registrationDeadline) {
        this.registrationDeadline = registrationDeadline;
    }

    public int getRecruitmentNumbers() {
        return recruitmentNumbers;
    }

    public void setRecruitmentNumbers(int recruitmentNumbers) {
        this.recruitmentNumbers = recruitmentNumbers;
    }

    public String getRequirement() {
        return requirement;
    }

    public void setRequirement(String requirement) {
        this.requirement = requirement;
    }

    public String getProjectStatus() {
        return projectStatus;
    }

    public void setProjectStatus(String projectStatus) {
        this.projectStatus = projectStatus;
    }
}
