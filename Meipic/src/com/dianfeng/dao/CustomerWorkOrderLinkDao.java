package com.dianfeng.dao;

import java.util.List;

import com.dianfeng.entity.CustomerWorkOrderLink;
import com.dianfeng.entity.WorkOrderInfo;

public interface CustomerWorkOrderLinkDao
{
	/**
	 * 根据客户ID获取工单信息
	 * @param customerId 客户ID
	 * @return
	 * 工单信息
	 */
	List<WorkOrderInfo> getWorkOrderByCustomerId(String customerId);
	
	/**
	 * 新增客户工单关联表信息
	 * @param customerWorkOrderLink 客户工单关联信息
	 * @return
	 * 新增的条数
	 */
	int insertCustomerWorkOrderLink(CustomerWorkOrderLink customerWorkOrderLink);
	
	/**
	 * 根据工单ID删除客户工单关联信息
	 * @param workOrderId 工单ID
	 * @return
	 * 删除的条数
	 */
	int deleteCustomerWorkOrderLinkByWorkOrderId(String workOrderId);
	
	/**
	 * 根据客户ID删除客户工单管理信息
	 * @param customerId 客户ID
	 * @return
	 * 删除的条数
	 */
	int deleteCustomerWorkOrderLinkByCustomerId(String customerId);
	
}
