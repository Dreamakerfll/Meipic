package com.dianfeng.entity;

public class PhoneModel
{
	/**
	 * 手机型号ID
	 */
	private String id;
	/**
	 * 手机型号
	 */
	private String phoneModel;
	
	private String phoneModelDisplay;
	
	public String getId()
	{
		return id;
	}
	public void setId(String id)
	{
		this.id = id;
	}
	public String getPhoneModel()
	{
		return phoneModel;
	}
	public void setPhoneModel(String phoneModel)
	{
		this.phoneModel = phoneModel;
	}
	public String getPhoneModelDisplay()
	{
		return phoneModelDisplay;
	}
	public void setPhoneModelDisplay(String phoneModelDisplay)
	{
		this.phoneModelDisplay = phoneModelDisplay;
	}
	
}
