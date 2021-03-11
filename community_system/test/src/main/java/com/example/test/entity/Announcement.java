package com.example.test.entity;

import java.sql.Timestamp;

/**
 * 公告表
 * */
public class Announcement {
	private int announcementId; //公告编号
	private String announcementContent; //公告内容
	private Timestamp announcementDate; //公告发布的时间
	public int getAnnouncementId() {
		return announcementId;
	}
	public void setAnnouncementId(int announcementId) {
		this.announcementId = announcementId;
	}
	public String getAnnouncementContent() {
		return announcementContent;
	}
	public void setAnnouncementContent(String announcementContent) {
		this.announcementContent = announcementContent;
	}
	public Timestamp getAnnouncementDate() {
		return announcementDate;
	}
	public void setAnnouncementDate(Timestamp announcementDate) {
		this.announcementDate = announcementDate;
	}
	
}
