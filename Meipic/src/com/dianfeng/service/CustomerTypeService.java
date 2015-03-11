package com.dianfeng.service;

import java.util.List;

import com.dianfeng.entity.CustomerType;

public interface CustomerTypeService
{
	/**
	 * 获取所有客户类别
	 * @return
	 * 所有客户类别
	 */
	List<CustomerType> getAllCustomerType();
}
