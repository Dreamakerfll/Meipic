package com.dianfeng.dao;

import java.util.List;

import com.dianfeng.entity.CustomerType;

public interface CustomerTypeDao
{
	/**
	 * 获取所有客户类别
	 * @return
	 * 所有客户类别
	 */
	List<CustomerType> getAllCustomerType();
}
