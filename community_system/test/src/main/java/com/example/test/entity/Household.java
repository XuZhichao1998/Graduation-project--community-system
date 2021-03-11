package com.example.test.entity;

public class Household {
	private int householdId;
	private int buildingNumber;
	private int unitNumber;
	private int roomNumber;
	private int parmanentResident;
	private String userId;
	public int getHouseholdId() {
		return householdId;
	}
	public void setHouseholdId(int householdId) {
		this.householdId = householdId;
	}
	public int getBuildingNumber() {
		return buildingNumber;
	}
	public void setBuildingNumber(int buildingNumber) {
		this.buildingNumber = buildingNumber;
	}
	public int getUnitNumber() {
		return unitNumber;
	}
	public void setUnitNumber(int unitNumber) {
		this.unitNumber = unitNumber;
	}
	public int getRoomNumber() {
		return roomNumber;
	}
	public void setRoomNumber(int roomNumber) {
		this.roomNumber = roomNumber;
	}
	public int getParmanentResident() {
		return parmanentResident;
	}
	public void setParmanentResident(int parmanentResident) {
		this.parmanentResident = parmanentResident;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
}
