package com.dianfeng.dao;

import java.util.List;

import com.dianfeng.entity.Announcement;

public interface AnnouncementDao {
	Announcement selectAnnouncementById(String announcementId);
	List<Announcement> selectAllAnnouncement();
	int deleteAnnouncement(String announcementId);
	int deleteNotice(String announcementId);
	int addAnnouncement(Announcement announcement);
}
