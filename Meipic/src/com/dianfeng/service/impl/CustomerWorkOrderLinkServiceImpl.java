package com.dianfeng.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dianfeng.dao.CustomerWorkOrderLinkDao;
import com.dianfeng.entity.CustomerWorkOrderLink;
import com.dianfeng.entity.WorkOrderInfo;
import com.dianfeng.service.CustomerWorkOrderLinkService;
@Service("CustomerWorkOrderLinkService")
public class CustomerWorkOrderLinkServiceImpl implements CustomerWorkOrderLinkService
{

	@Autowired
	private CustomerWorkOrderLinkDao customerWorkOrderLinkDao;
	
	@Override
	public List<WorkOrderInfo> getWorkOrderByCustomerId(String customerId)
	{
		// TODO Auto-generated method stub
		return customerWorkOrderLinkDao.getWorkOrderByCustomerId(customerId);
	}

	@Override
	public int insertCustomerWorkOrderLink(CustomerWorkOrderLink customerWorkOrderLink)
	{
		// TODO Auto-generated method stub
		return customerWorkOrderLinkDao.insertCustomerWorkOrderLink(customerWorkOrderLink);
	}

	@Override
	public int deleteCustomerWorkOrderLinkByWorkOrderId(String workOrderId)
	{
		// TODO Auto-generated method stub
		return customerWorkOrderLinkDao.deleteCustomerWorkOrderLinkByWorkOrderId(workOrderId);
	}

	@Override
	public int deleteCustomerWorkOrderLinkByCustomerId(String customerId)
	{
		// TODO Auto-generated method stub
		return customerWorkOrderLinkDao.deleteCustomerWorkOrderLinkByCustomerId(customerId);
	}

}
