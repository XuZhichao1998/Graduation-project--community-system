package com.example.test.entity;

public class PartyMemberInfo {
    private String partyMemberId; // 党员身份证号
    private String fullName; // 党员姓名
    private int unitId; // 党员所在单位(党支部)编号
    private String sex; // 党员性别

    public PartyMemberInfo(String partyMemberId, String fullName, int unitId, String sex) {
        this.partyMemberId = partyMemberId;
        this.fullName = fullName;
        this.unitId = unitId;
        this.sex = sex;
    }
    public PartyMemberInfo() {} // 无参构造函数

    public String getPartyMemberId() {
        return partyMemberId;
    }

    public void setPartyMemberId(String partyMemberId) {
        this.partyMemberId = partyMemberId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getUnitId() {
        return unitId;
    }

    public void setUnitId(int unitId) {
        this.unitId = unitId;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
