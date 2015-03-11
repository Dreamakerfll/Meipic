package com.dianfeng.entity;

public class CustomerType
{

	/**
	 * 用户类别ID
	 */
	private String id;
	/**
	 * 用户类别
	 */
	private String customerType;
	/**
	 * 用户类别页面显示
	 */
	private String customerTypeDisplay;
	public String getId()
	{
		return id;
	}
	public void setId(String id)
	{
		this.id = id;
	}
	public String getCustomerType()
	{
		return customerType;
	}
	public void setCustomerType(String customerType)
	{
		this.customerType = customerType;
	}
	public String getCustomerTypeDisplay()
	{
		return customerTypeDisplay;
	}
	public void setCustomerTypeDisplay(String customerTypeDisplay)
	{
		this.customerTypeDisplay = customerTypeDisplay;
	}
	
}
