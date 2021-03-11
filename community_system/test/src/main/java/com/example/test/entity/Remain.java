package com.example.test.entity;

import java.math.BigDecimal;

public class Remain {
	private int id;
	private int householdId;
	private String remainType;
	private BigDecimal remainAmount;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getHouseholdId() {
		return householdId;
	}
	public void setHouseholdId(int householdId) {
		this.householdId = householdId;
	}
	public String getRemainType() {
		return remainType;
	}
	public void setRemainType(String remainType) {
		this.remainType = remainType;
	}
	public BigDecimal getRemainAmount() {
		return remainAmount;
	}
	public void setRemainAmount(BigDecimal remainAmount) {
		this.remainAmount = remainAmount;
	}
	
}
