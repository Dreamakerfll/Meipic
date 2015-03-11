package com.dianfeng.service;

import java.util.List;

import com.dianfeng.entity.Announcement;

public interface AnnouncementService {
	List<Announcement> selectAllAnnouncement();
	Announcement selectAnnouncementById(String announcementId);
	int deleteAnnouncement(String announcementId);
	int deleteNotice(String announcementId);
	int addAnnouncement(Announcement announcement);
}
