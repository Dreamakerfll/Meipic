package com.dianfeng.entity;

public class FollowHistoryInfo
{
	/**
	 * 工单跟进历史ID
	 */
	private String id;
	/**
	 * 工单ID
	 */
	private String workOrderId;
	/**
	 * 跟进坐席
	 */
	private String agent;
	/**
	 * 跟进账号
	 */
	private String account;
	/**
	 * 跟进过程
	 */
	private String treatmentScheme;
	/**
	 * 跟进时间
	 */
	private String time;
	public String getId()
	{
		return id;
	}
	public void setId(String id)
	{
		this.id = id;
	}
	public String getWorkOrderId()
	{
		return workOrderId;
	}
	public void setWorkOrderId(String workOrderId)
	{
		this.workOrderId = workOrderId;
	}
	public String getAgent()
	{
		return agent;
	}
	public void setAgent(String agent)
	{
		this.agent = agent;
	}
	public String getAccount()
	{
		return account;
	}
	public void setAccount(String account)
	{
		this.account = account;
	}
	public String getTreatmentScheme()
	{
		return treatmentScheme;
	}
	public void setTreatmentScheme(String treatmentScheme)
	{
		this.treatmentScheme = treatmentScheme;
	}
	public String getTime()
	{
		return time;
	}
	public void setTime(String time)
	{
		this.time = time;
	}
	
}
