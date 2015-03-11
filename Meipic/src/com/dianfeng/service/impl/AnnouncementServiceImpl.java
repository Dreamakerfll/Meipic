package com.dianfeng.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dianfeng.dao.AnnouncementDao;
import com.dianfeng.entity.Announcement;
import com.dianfeng.service.AnnouncementService;
@Service("AnnouncementService")
public class AnnouncementServiceImpl implements AnnouncementService {
	@Autowired
	private AnnouncementDao announcementDao;
	
	@Override
	public int addAnnouncement(Announcement announcement) {
		return announcementDao.addAnnouncement(announcement);
	}
	@Override
	public int deleteAnnouncement(String announcementId) {
		return announcementDao.deleteAnnouncement(announcementId);
	}
	@Override
	public List<Announcement> selectAllAnnouncement() {
		return announcementDao.selectAllAnnouncement();
	}
	@Override
	public Announcement selectAnnouncementById(String announcementId) {
		return announcementDao.selectAnnouncementById(announcementId);
	}

	public AnnouncementDao getAnnouncementDao() {
		return announcementDao;
	}

	public void setAnnouncementDao(AnnouncementDao announcementDao) {
		this.announcementDao = announcementDao;
	}
	public int deleteNotice(String announcementId) {
		
		return announcementDao.deleteNotice(announcementId);
	}
}
