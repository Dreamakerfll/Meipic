package com.dianfeng.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dianfeng.dao.PhoneModelDao;
import com.dianfeng.entity.PhoneModel;
import com.dianfeng.service.PhoneModelService;

@Service("PhoneTypeService")
public class PhoneModelServiceImpl implements PhoneModelService
{
	@Autowired
	private PhoneModelDao phoneTypeDao;

	@Override
	public List<PhoneModel> getAllPhoneModel()
	{
		// TODO Auto-generated method stub
		return phoneTypeDao.getAllPhoneModel();
	}

}
