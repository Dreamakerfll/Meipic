package com.dianfeng.entity;

public class CustomerPhoneLink
{
	/**
	 * 客户和手机关联ID
	 */
	private String id;
	/**
	 * 客户ID
	 */
	private String CustomerId;
	/**
	 * 手机ID
	 */
	private String PhoneId;
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
		return CustomerId;
	}
	public void setCustomerId(String customerId)
	{
		CustomerId = customerId;
	}
	public String getPhoneId()
	{
		return PhoneId;
	}
	public void setPhoneId(String phoneId)
	{
		PhoneId = phoneId;
	}
	
}
