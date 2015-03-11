package com.dianfeng.entity;

public class CustomerWorkOrderLink
{
	/**
	 * 客户工单关联ID
	 */
	private String id;
	/**
	 * 客户ID
	 */
	private String customerId;
	/**
	 * 工单ID
	 */
	private String workOrderId;
	public String getId()
	{
		return id;
	}
	public void setId(String id)
	{
		this.id = id;
	}
	public String getCustomerId()
	{
		return customerId;
	}
	public void setCustomerId(String customerId)
	{
		this.customerId = customerId;
	}
	public String getWorkOrderId()
	{
		return workOrderId;
	}
	public void setWorkOrderId(String workOrderId)
	{
		this.workOrderId = workOrderId;
	}
	
}
