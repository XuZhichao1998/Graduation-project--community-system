package com.example.test.entity;

import java.sql.Timestamp;

public class TemporaryAccessApplication {
    private int applicationId; // 临时出入申请流水号
    private String personId; // 申请者的身份证号
    private String personName; // 申请者姓名
    private String phoneNumber; // 申请者联系方式(手机号)
    private String reason; // 申请出入社区的理由
    private String carNumber; // 车辆牌号
    private Timestamp estimatedEnterTime; // 预计进入社区的时间
    private Timestamp estimatedLeaveTime; // 预计离开社区的时间
    private Timestamp applyCommitTime; // 提交申请的时间
    private String approval; // 审核状态 "待审核" or "通过" or "不通过[理由]"
    private String approvalManagerName; // 审核的管理员的姓名
    private Timestamp actualEnterTime; // 实际进入社区的时间
    private Timestamp actualLeaveTime; // 实际离开社区的时间

    public int getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(int applicationId) {
        this.applicationId = applicationId;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public Timestamp getEstimatedEnterTime() {
        return estimatedEnterTime;
    }

    public void setEstimatedEnterTime(Timestamp estimatedEnterTime) {
        this.estimatedEnterTime = estimatedEnterTime;
    }

    public Timestamp getEstimatedLeaveTime() {
        return estimatedLeaveTime;
    }

    public void setEstimatedLeaveTime(Timestamp estimatedLeaveTime) {
        this.estimatedLeaveTime = estimatedLeaveTime;
    }

    public Timestamp getApplyCommitTime() {
        return applyCommitTime;
    }

    public void setApplyCommitTime(Timestamp applyCommitTime) {
        this.applyCommitTime = applyCommitTime;
    }

    public String getApproval() {
        return approval;
    }

    public void setApproval(String approval) {
        this.approval = approval;
    }

    public String getApprovalManagerName() {
        return approvalManagerName;
    }

    public void setApprovalManagerName(String approvalManagerName) {
        this.approvalManagerName = approvalManagerName;
    }

    public Timestamp getActualEnterTime() {
        return actualEnterTime;
    }

    public void setActualEnterTime(Timestamp actualEnterTime) {
        this.actualEnterTime = actualEnterTime;
    }

    public Timestamp getActualLeaveTime() {
        return actualLeaveTime;
    }

    public void setActualLeaveTime(Timestamp actualLeaveTime) {
        this.actualLeaveTime = actualLeaveTime;
    }
}
