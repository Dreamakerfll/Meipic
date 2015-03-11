package com.dianfeng.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dianfeng.dao.FollowHistoryInfoDao;
import com.dianfeng.entity.FollowHistoryInfo;
import com.dianfeng.service.FollowHistoryInfoService;
@Service("FollowHistoryInfoService")
public class FollowHistoryInfoServiceImpl implements FollowHistoryInfoService
{

	@Autowired
	private FollowHistoryInfoDao followHistoryInfoDao;
	
	@Override
	public int insertFollowHistoryInfo(FollowHistoryInfo followHistoryInfo)
	{
		// TODO Auto-generated method stub
		return followHistoryInfoDao.insertFollowHistoryInfo(followHistoryInfo);
	}

	@Override
	public List<FollowHistoryInfo> getFollowHistoryInfo(String workOrderId)
	{
		// TODO Auto-generated method stub
		return followHistoryInfoDao.getFollowHistoryInfo(workOrderId);
	}

}
