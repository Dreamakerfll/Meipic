package com.dianfeng.service;

import java.util.List;

import com.dianfeng.entity.CustomerPhoneLink;
import com.dianfeng.entity.CustomerPhoneLinkDetailInfo;

public interface CustomerPhoneLinkService
{
	/**
	 * 根据客户ID查找客户的手机关联信息
	 * @param CustomerId 客户ID
	 * @return
	 * 客户手机关联信息
	 */
	//List<CustomerPhoneLink> getCustomerPhoneLinkByCustomerId(String CustomerId);
	
	/**
	 * 根据条件查询客户和手机的详细信息
	 * @param customerPhoneLinkDetailInfo
	 * @return
	 * 客户和手机的详细信息
	 */
	List<CustomerPhoneLinkDetailInfo> getCustomerPhoneLinkDetailInfoByCondition(CustomerPhoneLinkDetailInfo customerPhoneLinkDetailInfo);
	
	/**
	 * 获取客户详情的条数
	 * @param customerPhoneLinkDetailInfo 客户手机详细信息
	 * @return
	 * 客户和手机详细信息的条数
	 */
	int getCustomerPhoneLinkDetailInfoByConditionCount(CustomerPhoneLinkDetailInfo customerPhoneLinkDetailInfo);
	
	/**
	 * 新增客户手机关联表数据
	 * @param customerPhoneLinkList 客户手机信息关联信息
	 * @return
	 * 新增的条数
	 */
	int insertCustomerPhoneLink(List<CustomerPhoneLink> customerPhoneLinkList);
	
	/**
	 * 删除客户手机关联表数据
	 * @param customerPhoneLinkList 客户手机信息关联信息
	 * @return
	 * 删除的条数
	 */
	int deleteCustomerPhoneLink(List<CustomerPhoneLink> customerPhoneLinkList);
	
	/**
	 * 根据客户ID删除关联表数据
	 * @param customerId
	 * @return
	 * 删除的条数
	 */
	int deleteCustomerPhoneLinkByCustomerId(String customerId);
}
