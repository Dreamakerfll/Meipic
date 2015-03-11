package com.dianfeng.entity;

public class ActionRecordInfo
{
	/**
	 * 操作记录ID
	 */
	private String id;
	/**
	 * 操作状态,1上线,2示忙,3下线
	 */
	private String status;
	/**
	 * 操作开始触发时间
	 */
	private String beginTime;
	/**
	 * 操作结束触发时间
	 */
	private String endTime;
	/**
	 * 账号
	 */
	private String account;
	/**
	 * 分机号
	 */
	private String agent;
	/**
	 * 时间间隔
	 */
	private String intervalTime;
	public String getId()
	{
		return id;
	}
	public void setId(String id)
	{
		this.id = id;
	}
	public String getStatus()
	{
		return status;
	}
	public void setStatus(String status)
	{
		this.status = status;
	}
	public String getBeginTime()
	{
		return beginTime;
	}
	public void setBeginTime(String beginTime)
	{
		this.beginTime = beginTime;
	}
	public String getEndTime()
	{
		return endTime;
	}
	public void setEndTime(String endTime)
	{
		this.endTime = endTime;
	}
	public String getAccount()
	{
		return account;
	}
	public void setAccount(String account)
	{
		this.account = account;
	}
	public String getAgent()
	{
		return agent;
	}
	public void setAgent(String agent)
	{
		this.agent = agent;
	}
	public String getIntervalTime()
	{
		return intervalTime;
	}
	public void setIntervalTime(String intervalTime)
	{
		this.intervalTime = intervalTime;
	}
	
}
