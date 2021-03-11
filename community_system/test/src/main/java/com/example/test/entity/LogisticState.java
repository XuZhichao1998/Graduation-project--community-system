package com.example.test.entity;

import java.sql.Timestamp;

public class LogisticState {
	private int logisticStateId;
	private int logisticId;
	private Timestamp logisticStateTime;
	private String logisticStateDescription;
	public int getLogisticStateId() {
		return logisticStateId;
	}
	public void setLogisticStateId(int logisticStateId) {
		this.logisticStateId = logisticStateId;
	}
	public int getLogisticId() {
		return logisticId;
	}
	public void setLogisticId(int logisticId) {
		this.logisticId = logisticId;
	}
	public Timestamp getLogisticStateTime() {
		return logisticStateTime;
	}
	public void setLogisticStateTime(Timestamp logisticStateTime) {
		this.logisticStateTime = logisticStateTime;
	}
	public String getLogisticStateDescription() {
		return logisticStateDescription;
	}
	public void setLogisticStateDescription(String logisticStateDescription) {
		this.logisticStateDescription = logisticStateDescription;
	}
	
}
