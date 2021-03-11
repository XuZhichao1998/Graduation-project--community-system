package com.example.test.entity;

//import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.sql.Timestamp;

public class Payment {
	private int paymentId;
	private Timestamp paymentSendDate;
	private Timestamp paymentDeadline;
	private BigDecimal paymentAmount;
	private int householdId;
	private String paymentType;
	private int paymentState;
	private Timestamp paymentFinishTime;
	public int getPaymentId() {
		return paymentId;
	}
	public void setPaymentId(int paymentId) {
		this.paymentId = paymentId;
	}
	public Timestamp getPaymentSendDate() {
		return paymentSendDate;
	}
	public void setPaymentSendDate(Timestamp paymentSendDate) {
		this.paymentSendDate = paymentSendDate;
	}
	public Timestamp getPaymentDeadline() {
		return paymentDeadline;
	}
	public void setPaymentDeadline(Timestamp paymentDeadline) {
		this.paymentDeadline = paymentDeadline;
	}
	public BigDecimal getPaymentAmount() {
		return paymentAmount;
	}
	public void setPaymentAmount(BigDecimal paymentAmount) {
		this.paymentAmount = paymentAmount;
	}
	public int getHouseholdId() {
		return householdId;
	}
	public void setHouseholdId(int householdId) {
		this.householdId = householdId;
	}
	public String getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
	public int getPaymentState() {
		return paymentState;
	}
	public void setPaymentState(int paymentState) {
		this.paymentState = paymentState;
	}
	public Timestamp getPaymentFinishTime() {
		return paymentFinishTime;
	}
	public void setPaymentFinishTime(Timestamp paymentFinishTime) {
		this.paymentFinishTime = paymentFinishTime;
	}
	public Payment(){}

	public Payment(int paymentId, Timestamp paymentSendDate, Timestamp paymentDeadline, BigDecimal paymentAmount, int householdId, String paymentType, int paymentState, Timestamp paymentFinishTime) {
		this.paymentId = paymentId;
		this.paymentSendDate = paymentSendDate;
		this.paymentDeadline = paymentDeadline;
		this.paymentAmount = paymentAmount;
		this.householdId = householdId;
		this.paymentType = paymentType;
		this.paymentState = paymentState;
		this.paymentFinishTime = paymentFinishTime;
	}


}
