package com.dianfeng.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dianfeng.dao.MisscallInfoDao;
import com.dianfeng.entity.MisscallInfo;
import com.dianfeng.service.MisscallInfoService;
@Service("MisscallInfoService")
public class MisscallServiceInfoImpl implements MisscallInfoService
{
	@Autowired
	private MisscallInfoDao misscallDao;

	public List<MisscallInfo> getAllMisscall()
	{
		// TODO Auto-generated method stub
		return misscallDao.getAllMisscall();
	}

	public int updateAccountById(String id, String account)
	{
		// TODO Auto-generated method stub
		return misscallDao.updateAccountById(id, account);
	}

	public List<MisscallInfo> getMisscallByCondition(String beginTime, String endTime, String phoneNumber, String phoneNumber_called)
	{
		// TODO Auto-generated method stub
		return misscallDao.getMisscallByCondition(beginTime, endTime, phoneNumber,phoneNumber_called);
	}

	@Override
	public int updateMisscallStatus(String id)
	{
		// TODO Auto-generated method stub
		return misscallDao.updateMisscallStatus(id);
	}



}
