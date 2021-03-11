package com.example.test.entity;

public class UserInfo { 
	private String userId;
	private String userName;
	private String userPassword;
	private String userIdentityNumber;
	private String userSex;
	private String userPhoneNumber;
	private String userEmail;
	private Integer householdId;
	//private Integer userAge;
	public String showMsg() {
		return "id:"+userId+",name:"+userName+",pwd:"+userPassword+",idn:"+userIdentityNumber+",sex:"+userSex+",phone:"+userPhoneNumber
				+",email:"+userEmail+",hid:"+householdId;
	}

	public UserInfo(String userId, String userName, String userPassword, String userIdentityNumber, String userSex, String userPhoneNumber, String userEmail, Integer householdId) {
		this.userId = userId;
		this.userName = userName;
		this.userPassword = userPassword;
		this.userIdentityNumber = userIdentityNumber;
		this.userSex = userSex;
		this.userPhoneNumber = userPhoneNumber;
		this.userEmail = userEmail;
		this.householdId = householdId;
	}
	public UserInfo() {}

	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	public String getUserIdentityNumber() {
		return userIdentityNumber;
	}
	public void setUserIdentityNumber(String userIdentityNumber) {
		this.userIdentityNumber = userIdentityNumber;
	}
	public String getUserSex() {
		return userSex;
	}
	public void setUserSex(String userSex) {
		this.userSex = userSex;
	}
	public String getUserPhoneNumber() {
		return userPhoneNumber;
	}
	public void setUserPhoneNumber(String userPhoneNumber) {
		this.userPhoneNumber = userPhoneNumber;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public Integer getHouseholdId() {
		return householdId;
	}
	public void setHouseholdId(Integer householdId) { this.householdId = householdId; }
}
