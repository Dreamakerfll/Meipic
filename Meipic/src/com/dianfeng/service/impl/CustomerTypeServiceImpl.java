package com.dianfeng.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dianfeng.dao.CustomerTypeDao;
import com.dianfeng.entity.CustomerType;
import com.dianfeng.service.CustomerTypeService;
@Service("CustomerTypeService")
public class CustomerTypeServiceImpl implements CustomerTypeService
{

	@Autowired
	private CustomerTypeDao customerTypeDao;
	
	@Override
	public List<CustomerType> getAllCustomerType()
	{
		// TODO Auto-generated method stub
		return customerTypeDao.getAllCustomerType();
	}

}
