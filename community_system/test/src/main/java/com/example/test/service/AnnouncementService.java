package com.example.test.service;

import com.example.test.entity.Announcement;

import java.util.List;

public interface AnnouncementService {
    int getAnnouncementCount();
    Announcement getAnnouncementById(int announcementId);
    List<Announcement> getAnnouncementList(int page, int limit);
    List<Announcement> getAnnouncementListById(int announcementId,int page,int limit);
    int addAnnouncement(Announcement announcement); //添加一条公告
    int deleteAnnouncement(int announcementId);
}
