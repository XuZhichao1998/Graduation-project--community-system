package com.example.test.entity;

public class CooperationUnit {
    private int unitId;
    private String unitName;
    public CooperationUnit() {} //无参构造方法
    public CooperationUnit(int unitId, String unitName) { // 带参构造方法
        this.unitId = unitId;
        this.unitName = unitName;
    }

    public int getUnitId() {
        return unitId;
    }

    public void setUnitId(int unitId) {
        this.unitId = unitId;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }
}
