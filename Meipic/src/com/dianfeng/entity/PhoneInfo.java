package com.dianfeng.entity;

public class PhoneInfo
{
	/**
	 * 手机ID
	 */
	private String id;
	/**
	 * 手机IMEI
	 */
	private String imei;
	/**
	 * 手机型号
	 */
	private String model;
	/**
	 * 手机版本
	 */
	private String version;
	/**
	 * 购买时间
	 */
	private String buyTime;
	public String getId()
	{
		return id;
	}
	public void setId(String id)
	{
		this.id = id;
	}
	public String getImei()
	{
		return imei;
	}
	public void setImei(String imei)
	{
		this.imei = imei;
	}
	public String getModel()
	{
		return model;
	}
	public void setModel(String model)
	{
		this.model = model;
	}
	public String getVersion()
	{
		return version;
	}
	public void setVersion(String version)
	{
		this.version = version;
	}
	public String getBuyTime()
	{
		return buyTime;
	}
	public void setBuyTime(String buyTime)
	{
		this.buyTime = buyTime;
	}
	
}
