package com.dianfeng.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dianfeng.dao.AreaInfoDao;
import com.dianfeng.entity.AreaInfo;
import com.dianfeng.service.AreaInfoService;
@Service("AreaInfoService")
public class AreaInfoServiceImpl implements AreaInfoService
{
	@Autowired
	private AreaInfoDao areaDao;

	public List<AreaInfo> getAllArea()
	{
		// TODO Auto-generated method stub
		return areaDao.getAllArea();
	}

	@Override
	public List<AreaInfo> getAreaByCode(String tel)
	{
		// TODO Auto-generated method stub
		return areaDao.getAreaByCode(tel);
	}

	@Override
	public List<AreaInfo> getAreaByPhone(String tel)
	{
		// TODO Auto-generated method stub
		return areaDao.getAreaByPhone(tel);
	}

}
