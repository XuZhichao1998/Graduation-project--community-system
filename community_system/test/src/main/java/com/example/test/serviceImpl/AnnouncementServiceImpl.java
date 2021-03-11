package com.example.test.serviceImpl;

import com.example.test.entity.Announcement;
import com.example.test.mapper.AnnouncementMapper;
import com.example.test.service.AnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnnouncementServiceImpl implements AnnouncementService {
    //将DAO注入Service层
    @Autowired
    private AnnouncementMapper announcementMapper;

    @Override
    public int getAnnouncementCount() {
        return announcementMapper.getAnnouncementCount();
    }

    @Override
    public Announcement getAnnouncementById(int announcementId) {
        return announcementMapper.getAnnouncementById(announcementId);
    }

    @Override
    public List<Announcement> getAnnouncementList(int page, int limit) {
        return announcementMapper.getAnnouncementList(page,limit);
    }

    @Override
    public List<Announcement> getAnnouncementListById(int announcementId, int page, int limit) {
        return announcementMapper.getAnnouncementListById(announcementId,page,limit);
    }

    @Override
    public int addAnnouncement(Announcement announcement) {
        return announcementMapper.addAnnouncement(announcement);
    }

    @Override
    public int deleteAnnouncement(int announcementId) {
        return announcementMapper.deleteAnnouncement(announcementId);
    }
}
