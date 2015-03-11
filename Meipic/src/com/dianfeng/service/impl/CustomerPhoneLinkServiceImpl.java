package com.dianfeng.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dianfeng.dao.CustomerPhoneLinkDao;
import com.dianfeng.entity.CustomerPhoneLink;
import com.dianfeng.entity.CustomerPhoneLinkDetailInfo;
import com.dianfeng.service.CustomerPhoneLinkService;
@Service("CustomerPhoneLinkService")
public class CustomerPhoneLinkServiceImpl implements CustomerPhoneLinkService
{
	@Autowired
	private CustomerPhoneLinkDao customerPhoneLinkDao;

//	@Override
//	public List<CustomerPhoneLink> getCustomerPhoneLinkByCustomerId(String CustomerId)
//	{
//		// TODO Auto-generated method stub
//		return customerPhoneLinkDao.getCustomerPhoneLinkByCustomerId(CustomerId);
//	}

	@Override
	public List<CustomerPhoneLinkDetailInfo> getCustomerPhoneLinkDetailInfoByCondition(CustomerPhoneLinkDetailInfo customerPhoneLinkDetailInfo)
	{
		// TODO Auto-generated method stub
		return customerPhoneLinkDao.getCustomerPhoneLinkDetailInfoByCondition(customerPhoneLinkDetailInfo);
	}

	@Override
	public int deleteCustomerPhoneLink(List<CustomerPhoneLink> customerPhoneLinkList)
	{
		// TODO Auto-generated method stub
		return customerPhoneLinkDao.deleteCustomerPhoneLink(customerPhoneLinkList);
	}

	@Override
	public int insertCustomerPhoneLink(List<CustomerPhoneLink> customerPhoneLinkList)
	{
		// TODO Auto-generated method stub
		return customerPhoneLinkDao.insertCustomerPhoneLink(customerPhoneLinkList);
	}

	@Override
	public int getCustomerPhoneLinkDetailInfoByConditionCount(CustomerPhoneLinkDetailInfo customerPhoneLinkDetailInfo)
	{
		// TODO Auto-generated method stub
		return customerPhoneLinkDao.getCustomerPhoneLinkDetailInfoByConditionCount(customerPhoneLinkDetailInfo);
	}

	@Override
	public int deleteCustomerPhoneLinkByCustomerId(String customerId)
	{
		// TODO Auto-generated method stub
		return customerPhoneLinkDao.deleteCustomerPhoneLinkByCustomerId(customerId);
	}

}
