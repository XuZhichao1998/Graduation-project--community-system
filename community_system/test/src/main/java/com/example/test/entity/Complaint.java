package com.example.test.entity;

import java.sql.Timestamp;

/**
 * 投诉信息表
 * */
public class Complaint {
	private Integer complaintId; //投诉流水号
	private String complaintUserId; //投诉者的用户名(外键)
	private String complaintUserName; //投诉者的名字
	private String complaintType; //投诉类型(投诉的类型 '服务投诉', '价格投诉', '诚信投诉', '意外事故投诉')
	private String complaintMessage; //投诉内容
	private String phoneNumber; //联系方式
	private Timestamp complaintDate; //投诉日期
	private String complaintState; //投诉状态

	public Complaint() {}

	public Complaint(Integer complaintId, String complaintUserId, String complaintType, String complaintMessage, String phoneNumber, Timestamp complaintDate, String complaintState) {
		this.complaintId = complaintId;
		this.complaintUserId = complaintUserId;
		this.complaintUserName = "name";
		this.complaintType = complaintType;
		this.complaintMessage = complaintMessage;
		this.phoneNumber = phoneNumber;
		this.complaintDate = complaintDate;
		this.complaintState = complaintState;
	}

	public String getComplaintState() {
		return complaintState;
	}

	public void setComplaintState(String complaintState) {
		this.complaintState = complaintState;
	}

	public Integer getComplaintId() {
		return complaintId;
	}
	public void setComplaintId(Integer complaintId) {
		this.complaintId = complaintId;
	}
	public String getComplaintUserId() {
		return complaintUserId;
	}
	public void setComplaintUserId(String complaintUserId) {
		this.complaintUserId = complaintUserId;
	}
	public String getComplaintUserName() {
		return complaintUserName;
	}
	public void setComplaintUserName(String complaintUserName) {
		this.complaintUserName = complaintUserName;
	}
	public String getComplaintType() {
		return complaintType;
	}
	public void setComplaintType(String complaintType) {
		this.complaintType = complaintType;
	}
	public String getComplaintMessage() {
		return complaintMessage;
	}
	public void setComplaintMessage(String complaintMessage) {
		this.complaintMessage = complaintMessage;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public Timestamp getComplaintDate() {
		return complaintDate;
	}
	public void setComplaintDate(Timestamp complaintDate) {
		this.complaintDate = complaintDate;
	}
	public String showMsg()
	{
		return "cid:["+complaintId+"],uid:["+complaintUserId+"],type:["+complaintType+"],msg:["+complaintMessage
				+"],phone:["+phoneNumber+"],date:["+complaintDate.toString()+"],state:["+complaintState+"]";
	}
}