package com.dianfeng.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dianfeng.dao.PhoneVersionDao;
import com.dianfeng.entity.PhoneVersion;
import com.dianfeng.service.PhoneVersionService;

@Service("PhoneVersionService")
public class PhoneVersionServiceImpl implements PhoneVersionService
{
	@Autowired
	private PhoneVersionDao phoneVersionDao;

	@Override
	public List<PhoneVersion> getAllPhoneVersion()
	{
		// TODO Auto-generated method stub
		return phoneVersionDao.getAllPhoneVersion();
	}

}
