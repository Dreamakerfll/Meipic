package com.dianfeng.entity;

public class AgentInfo
{
	/**
	 * 座席ID
	 */
	private String id;
	/**
	 * 座席号
	 */
	private String number;
	/**
	 * 页面上显示座席号
	 */
	private String numberDisplay;
	/**
	 * 座席号是否有效
	 */
	private String status;
	/**
	 * 座席有效时间
	 */
	private String deadline;
	public String getId()
	{
		return id;
	}
	public void setId(String id)
	{
		this.id = id;
	}
	public String getNumber()
	{
		return number;
	}
	public void setNumber(String number)
	{
		this.number = number;
	}
	public String getStatus()
	{
		return status;
	}
	public void setStatus(String status)
	{
		this.status = status;
	}
	public String getDeadline()
	{
		return deadline;
	}
	public void setDeadline(String deadline)
	{
		this.deadline = deadline;
	}
	public String getNumberDisplay()
	{
		return numberDisplay;
	}
	public void setNumberDisplay(String numberDisplay)
	{
		this.numberDisplay = numberDisplay;
	}
	
}
