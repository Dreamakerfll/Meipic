package com.dianfeng.entity;

public class PhoneVersion
{
	/**
	 * 手机版本ID
	 */
	private String id;
	/**
	 * 手机版本
	 */
	private String phoneVersion;
	
	private String phoneVersionDisplay;
	public String getId()
	{
		return id;
	}
	public void setId(String id)
	{
		this.id = id;
	}
	public String getPhoneVersion()
	{
		return phoneVersion;
	}
	public void setPhoneVersion(String phoneVersion)
	{
		this.phoneVersion = phoneVersion;
	}
	public String getPhoneVersionDisplay()
	{
		return phoneVersionDisplay;
	}
	public void setPhoneVersionDisplay(String phoneVersionDisplay)
	{
		this.phoneVersionDisplay = phoneVersionDisplay;
	}

}
