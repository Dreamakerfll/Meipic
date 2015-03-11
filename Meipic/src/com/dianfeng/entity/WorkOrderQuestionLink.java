package com.dianfeng.entity;

public class WorkOrderQuestionLink
{
	/**
	 * 工单和问题关联ID
	 */
	private String id;
	/**
	 * 工单ID
	 */
	private String workOrderId;
	/**
	 * 问题ID
	 */
	private String questionId;
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
	public String getQuestionId()
	{
		return questionId;
	}
	public void setQuestionId(String questionId)
	{
		this.questionId = questionId;
	}
}
