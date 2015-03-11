package com.dianfeng.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dianfeng.dao.BlacklistInfoDao;
import com.dianfeng.entity.BlacklistInfo;
import com.dianfeng.service.BlacklistInfoService;
@Service("BlacklistInfoService")
public class BlacklistInfoServiceImpl implements BlacklistInfoService
{
	@Autowired
	private BlacklistInfoDao blacklistDao;
	
	public List<BlacklistInfo> getAllBlacklist()
	{
		// TODO Auto-generated method stub
		return blacklistDao.getAllBlacklist();
	}

	public List<BlacklistInfo> getBlacklistByCondition(String beginTime, String endTime, String phoneNumber, String submitPerson, String agentComment, String status)
	{
		// TODO Auto-generated method stub
		return blacklistDao.getBlacklistByCondition(beginTime, endTime, phoneNumber, submitPerson, agentComment, status);
	}

	public int deleteBlacklistById(String id)
	{
		// TODO Auto-generated method stub
		return blacklistDao.deleteBlacklistById(id);
	}

	public int addBlacklist(String beginTime, String endTime, String phoneNumber, String submitPerson, String agentComment, String submitTime)
	{
		// TODO Auto-generated method stub
		return blacklistDao.addBlacklist(beginTime, endTime, phoneNumber, submitPerson, agentComment, submitTime);
	}

	public int updateBlacklist(String id, String account, String monitorComment, String status)
	{
		// TODO Auto-generated method stub
		return blacklistDao.updateBlacklist(id, account, monitorComment, status);
	}

	@Override
	public int editBlacklist(String beginTime, String endTime, String phoneNumber, String agentComment, String id)
	{
		// TODO Auto-generated method stub
		return blacklistDao.editBlacklist(beginTime, endTime, phoneNumber, agentComment, id);
	}

}
