package com.dianfeng.service;

import java.util.List;

import com.dianfeng.entity.CustomerInfo;

public interface CustomerInfoService
{
	/**
	 * 根据联系电话获取用户信息
	 * @param tel
	 * @return
	 * 用户详细信息
	 */
	List<CustomerInfo> getCustomerInfoByTel(String tel);
	
	/**
	 * 更新客户信息
	 * @param customerInfo 客户信息
	 * @return
	 * 更新条数
	 */
	int updateCustomerInfo(CustomerInfo customerInfo);
	
	/**
	 * 插入客户信息
	 * @param customerInfo 客户信息
	 * @return
	 * 新增条数
	 */
	int insertCustomerInfo(CustomerInfo customerInfo);
	
	/**
	 * 删除客户信息
	 * @param customerId 客户ID
	 * @return
	 * 删除条数
	 */
	int deleteCustomerInfo(String customerId);
}
