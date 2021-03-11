package com.example.test.mapper;

import com.example.test.entity.Announcement;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AnnouncementMapper {
    int getAnnouncementCount();
    Announcement getAnnouncementById(@Param("announcementId") int announcementId);
    List<Announcement> getAnnouncementList(@Param("page") int page,@Param("limit") int limit);
    List<Announcement> getAnnouncementListById(@Param("announcementId") int announcementId, @Param("page") int page,@Param("limit") int limit);
    int addAnnouncement(@Param("announcement") Announcement announcement); //添加一条公告
    int deleteAnnouncement(@Param("announcementId") int announcementId);
}
