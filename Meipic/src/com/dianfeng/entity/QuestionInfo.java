package com.dianfeng.entity;

public class QuestionInfo
{
	/**
	 * 问题ID
	 */
	private String id;
	/**
	 * 问题大类
	 */
	private String mold;
	/**
	 * 问题类别
	 */
	private String type;
	/**
	 * 问题描述
	 */
	private String describe;
	/**
	 * 问题手机IMEI
	 */
	private String phoneImei;
	/**
	 * 问题手机型号
	 */
	private String phoneModel;
	/**
	 * 问题手机版本
	 */
	private String phoneVersion;
	/**
	 * 问题状态
	 */
	private String status;
	public String getId()
	{
		return id;
	}
	public void setId(String id)
	{
		this.id = id;
	}
	public String getMold()
	{
		return mold;
	}
	public void setMold(String mold)
	{
		this.mold = mold;
	}
	public String getType()
	{
		return type;
	}
	public void setType(String type)
	{
		this.type = type;
	}
	public String getDescribe()
	{
		return describe;
	}
	public void setDescribe(String describe)
	{
		this.describe = describe;
	}
	public String getPhoneImei()
	{
		return phoneImei;
	}
	public void setPhoneImei(String phoneImei)
	{
		this.phoneImei = phoneImei;
	}
	public String getPhoneModel()
	{
		return phoneModel;
	}
	public void setPhoneModel(String phoneModel)
	{
		this.phoneModel = phoneModel;
	}
	public String getPhoneVersion()
	{
		return phoneVersion;
	}
	public void setPhoneVersion(String phoneVersion)
	{
		this.phoneVersion = phoneVersion;
	}
	public String getStatus()
	{
		return status;
	}
	public void setStatus(String status)
	{
		this.status = status;
	}
	
	
}
