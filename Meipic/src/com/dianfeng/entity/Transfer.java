package com.dianfeng.entity;

public class Transfer
{
	/**
	 * ID
	 */
	private String id;
	/**
	 * 开始时间
	 */
	private String beginTime;
	/**
	 * 结束时间
	 */
	private String endTime;
	/**
	 * 提交时间
	 */
	private String submitTime;
	/**
	 * 转接号码
	 */
	private String transferNumber;
	/**
	 * 分机号
	 */
	private String agentNumber;
	/**
	 * 用户姓名
	 */
	private String account;
	/**
	 * 转接状态
	 */
	private String status;
	/**
	 * 转接区域ID
	 */
	private String transferAreaId;
	/**
	 * 转接区域
	 */
	private String transferArea;
	/**
	 * 转接batch
	 */
	private String transferBatch;
	public String getId()
	{
		return id;
	}
	public void setId(String id)
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
	public String getSubmitTime()
	{
		return submitTime;
	}
	public void setSubmitTime(String submitTime)
	{
		this.submitTime = submitTime;
	}
	public String getTransferNumber()
	{
		return transferNumber;
	}
	public void setTransferNumber(String transferNumber)
	{
		this.transferNumber = transferNumber;
	}
	public String getAgentNumber()
	{
		return agentNumber;
	}
	public void setAgentNumber(String agentNumber)
	{
		this.agentNumber = agentNumber;
	}
	public String getAccount()
	{
		return account;
	}
	public void setAccount(String account)
	{
		this.account = account;
	}
	public String getStatus()
	{
		return status;
	}
	public void setStatus(String status)
	{
		this.status = status;
	}
	public String getTransferAreaId()
	{
		return transferAreaId;
	}
	public void setTransferAreaId(String transferAreaId)
	{
		this.transferAreaId = transferAreaId;
	}
	public String getTransferBatch()
	{
		return transferBatch;
	}
	public void setTransferBatch(String transferBatch)
	{
		this.transferBatch = transferBatch;
	}
	public String getTransferArea()
	{
		return transferArea;
	}
	public void setTransferArea(String transferArea)
	{
		this.transferArea = transferArea;
	}
	
	
}
