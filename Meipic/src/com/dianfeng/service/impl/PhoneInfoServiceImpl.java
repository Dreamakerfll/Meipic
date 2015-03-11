package com.dianfeng.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dianfeng.dao.PhoneInfoDao;
import com.dianfeng.entity.PhoneInfo;
import com.dianfeng.service.PhoneInfoService;
@Service("PhoneInfoService")
public class PhoneInfoServiceImpl implements PhoneInfoService
{
	@Autowired
	private PhoneInfoDao phoneInfoDao;

	@Override
	public PhoneInfo getPhoneInfoByPhoneId(String phoneId)
	{
		// TODO Auto-generated method stub
		return phoneInfoDao.getPhoneInfoByPhoneId(phoneId);
	}

	@Override
	public int deletePhoneInfo(List<PhoneInfo> phoneInfoList)
	{
		// TODO Auto-generated method stub
		return phoneInfoDao.deletePhoneInfo(phoneInfoList);
	}

	@Override
	public int insertPhoneInfo(List<PhoneInfo> phoneInfoList)
	{
		// TODO Auto-generated method stub
		return phoneInfoDao.insertPhoneInfo(phoneInfoList);
	}

	@Override
	public int updatePhoneInfo(List<PhoneInfo> phoneInfoList)
	{
		// TODO Auto-generated method stub
		return phoneInfoDao.updatePhoneInfo(phoneInfoList);
	}

	@Override
	public long getMaxPhoneId()
	{
		// TODO Auto-generated method stub
		return phoneInfoDao.getMaxPhoneId();
	}

	@Override
	public List<PhoneInfo> getPhoneInfoByCustomerId(String customerId)
	{
		// TODO Auto-generated method stub
		return phoneInfoDao.getPhoneInfoByCustomerId(customerId);
	}

	@Override
	public int deletePhoneInfoByCustomerId(String customerId)
	{
		// TODO Auto-generated method stub
		return phoneInfoDao.deletePhoneInfoByCustomerId(customerId);
	}

	@Override
	public int insertOnePhoneInfo(PhoneInfo phoneInfo)
	{
		// TODO Auto-generated method stub
		return phoneInfoDao.insertOnePhoneInfo(phoneInfo);
	}

}
