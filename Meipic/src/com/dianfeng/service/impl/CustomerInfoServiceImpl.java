package com.dianfeng.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dianfeng.dao.CustomerInfoDao;
import com.dianfeng.entity.CustomerInfo;
import com.dianfeng.service.CustomerInfoService;

@Service("CustomerInfoService")
public class CustomerInfoServiceImpl implements CustomerInfoService
{
	@Autowired
	private CustomerInfoDao customerInfoDao;
	
	@Override
	public List<CustomerInfo> getCustomerInfoByTel(String tel)
	{
		// TODO Auto-generated method stub
		return customerInfoDao.getCustomerInfoByTel(tel);
	}

	@Override
	public int insertCustomerInfo(CustomerInfo customerInfo)
	{
		// TODO Auto-generated method stub
		return customerInfoDao.insertCustomerInfo(customerInfo);
	}

	@Override
	public int updateCustomerInfo(CustomerInfo customerInfo)
	{
		// TODO Auto-generated method stub
		return customerInfoDao.updateCustomerInfo(customerInfo);
	}

	@Override
	public int deleteCustomerInfo(String customerId)
	{
		// TODO Auto-generated method stub
		return customerInfoDao.deleteCustomerInfo(customerId);
	}

}
