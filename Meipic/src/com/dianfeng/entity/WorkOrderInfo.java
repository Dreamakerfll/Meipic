package com.dianfeng.entity;

public class WorkOrderInfo
{
	/**
	 * 工作任务单ID
	 */
	private String id;
	/**
	 * 工单流水
	 */
	private String assemblyLine;
	/**
	 * 来电号码
	 */
	private String tel;
	/**
	 * 来电时间
	 */
	private String telTime;
	/**
	 * 工单标题
	 */
	private String title;
	/**
	 * 工单优先级
	 */
	private String level;
	/**
	 * 反馈时间
	 */
	private String feedbackTime;
	/**
	 * 反馈类型
	 */
	private String feedbackType;
	/**
	 * 反馈渠道
	 */
	private String feedbackChannel;
	/**
	 * 处理过程
	 */
	private String treatmentScheme;
	/**
	 * 结案判定
	 */
	private String finalDecision;
	/**
	 * 处理座席
	 */
	private String userAgent;
	/**
	 * 用户账号
	 */
	private String userAccount;
	/**
	 * 技能组
	 */
	private String skillGroup;
	/**
	 * 工单uniqueId
	 */
	private String uniqueId;
	public String getId()
	{
		return id;
	}
	public void setId(String id)
	{
		this.id = id;
	}
	public String getAssemblyLine()
	{
		return assemblyLine;
	}
	public void setAssemblyLine(String assemblyLine)
	{
		this.assemblyLine = assemblyLine;
	}
	public String getTel()
	{
		return tel;
	}
	public void setTel(String tel)
	{
		this.tel = tel;
	}
	public String getTelTime()
	{
		return telTime;
	}
	public void setTelTime(String telTime)
	{
		this.telTime = telTime;
	}
	public String getTitle()
	{
		return title;
	}
	public void setTitle(String title)
	{
		this.title = title;
	}
	public String getLevel()
	{
		return level;
	}
	public void setLevel(String level)
	{
		this.level = level;
	}
	public String getFeedbackTime()
	{
		return feedbackTime;
	}
	public void setFeedbackTime(String feedbackTime)
	{
		this.feedbackTime = feedbackTime;
	}
	public String getFeedbackType()
	{
		return feedbackType;
	}
	public void setFeedbackType(String feedbackType)
	{
		this.feedbackType = feedbackType;
	}
	public String getFeedbackChannel()
	{
		return feedbackChannel;
	}
	public void setFeedbackChannel(String feedbackChannel)
	{
		this.feedbackChannel = feedbackChannel;
	}
	public String getTreatmentScheme()
	{
		return treatmentScheme;
	}
	public void setTreatmentScheme(String treatmentScheme)
	{
		this.treatmentScheme = treatmentScheme;
	}
	public String getFinalDecision()
	{
		return finalDecision;
	}
	public void setFinalDecision(String finalDecision)
	{
		this.finalDecision = finalDecision;
	}
	public String getUserAgent()
	{
		return userAgent;
	}
	public void setUserAgent(String userAgent)
	{
		this.userAgent = userAgent;
	}
	public String getUserAccount()
	{
		return userAccount;
	}
	public void setUserAccount(String userAccount)
	{
		this.userAccount = userAccount;
	}
	public String getSkillGroup()
	{
		return skillGroup;
	}
	public void setSkillGroup(String skillGroup)
	{
		this.skillGroup = skillGroup;
	}
	public String getUniqueId()
	{
		return uniqueId;
	}
	public void setUniqueId(String uniqueId)
	{
		this.uniqueId = uniqueId;
	}

}
