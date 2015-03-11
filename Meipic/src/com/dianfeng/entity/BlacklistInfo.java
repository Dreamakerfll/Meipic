package com.dianfeng.entity;

public class BlacklistInfo
{

	/**
	 * ID
	 */
	private int id;
	/**
	 * 开始时间
	 */
	private String beginTime;
	/**
	 * 结束时间
	 */
	private String endTime;
	/**
	 * 电话号码
	 */
	private String phoneNumber;
	/**
	 * 座席备注
	 */
	private String agentComment;
	/**
	 * 审核状态
	 */
	private String status;
	/**
	 * 提交人
	 */
	private String submitPerson;
	/**
	 * 审核人
	 */
	private String checkPerson;
	/**
	 * 班长备注
	 */
	private String monitorComment;
	
	/**
	 * 提交时间
	 */
	private String submitTime;
	public int getId()
	{
		return id;
	}
	public void setId(int id)
	{
		this.id = id;
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
	public String getPhoneNumber()
	{
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber)
	{
		this.phoneNumber = phoneNumber;
	}
	public String getAgentComment()
	{
		return agentComment;
	}
	public void setAgentComment(String agentComment)
	{
		this.agentComment = agentComment;
	}
	public String getStatus()
	{
		return status;
	}
	public void setStatus(String status)
	{
		this.status = status;
	}
	public String getSubmitPerson()
	{
		return submitPerson;
	}
	public void setSubmitPerson(String submitPerson)
	{
		this.submitPerson = submitPerson;
	}
	public String getCheckPerson()
	{
		return checkPerson;
	}
	public void setCheckPerson(String checkPerson)
	{
		this.checkPerson = checkPerson;
	}
	public String getMonitorComment()
	{
		return monitorComment;
	}
	public void setMonitorComment(String monitorComment)
	{
		this.monitorComment = monitorComment;
	}
	public String getSubmitTime()
	{
		return submitTime;
	}
	public void setSubmitTime(String submitTime)
	{
		this.submitTime = submitTime;
	}
	
	
}
