package com.example.test.entity;

import java.sql.Timestamp;


public class ReportRepair {
	private int reportRepairId;
	private String reportUserId;
	private String reportProblem;
	private Timestamp reportDatetime;
	private String reportState;
	private Timestamp repairFinishDatetime;
	public int getReportRepairId() {
		return reportRepairId;
	}
	public void setReportRepairId(int reportRepairId) {
		this.reportRepairId = reportRepairId;
	}
	public String getReportUserId() {
		return reportUserId;
	}
	public void setReportUserId(String reportUserId) {
		this.reportUserId = reportUserId;
	}
	public String getReportProblem() {
		return reportProblem;
	}
	public void setReportProblem(String reportProblem) {
		this.reportProblem = reportProblem;
	}
	public Timestamp getReportDatetime() {
		return reportDatetime;
	}
	public void setReportDatetime(Timestamp reportDatetime) {
		this.reportDatetime = reportDatetime;
	}
	public String getReportState() {
		return reportState;
	}
	public void setReportState(String reportState) {
		this.reportState = reportState;
	}
	public Timestamp getRepairFinishDatetime() {
		return repairFinishDatetime;
	}
	public void setRepairFinishDatetime(Timestamp repairFinishDatetime) {
		this.repairFinishDatetime = repairFinishDatetime;
	}
	public ReportRepair() {}
	public ReportRepair(int reportRepairId, String reportUserId, String reportProblem, Timestamp reportDatetime, String reportState, Timestamp repairFinishDatetime) {
		this.reportRepairId = reportRepairId;
		this.reportUserId = reportUserId;
		this.reportProblem = reportProblem;
		this.reportDatetime = reportDatetime;
		this.reportState = reportState;
		this.repairFinishDatetime = repairFinishDatetime;
	}


}
