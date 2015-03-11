package com.dianfeng.entity;

public class MisscallInfo
{
	/**
	 * ID
	 */
	private String id;
	/**
	 * 用户名
	 */
	private String account;
	/**
	 * 座席号
	 */
	private String agentNumber;
	/**
	 * 来电时间
	 */
	private String answerTime;
	/**
	 * 电话类型（呼入或呼出）
	 */
	private String callType;
	/**
	 * 拨打者
	 */
	private String caller;
	/**
	 * 接听者
	 */
	private String answer;
	/**
	 * 响铃时长
	 */
	private String ringSec;
	/**
	 * 通话时长
	 */
	private String holdSec;
	/**
	 * 是否接听
	 */
	private String disposition;
	/**
	 * 录音地址
	 */
	private String recordPath;
	/**
	 * uniqueId
	 */
	private String uniqueId;
	/**
	 * 是否回拨
	 */
	private String isAnswer;
	/**
	 * 经办人
	 */
	private String operator;
	/**
	 * 未接来电是否在页面显示
	 */
	private String isDisplay;
	/**
	 * 页面显示的经办人
	 */
	private String personToDisplay;
	public String getId()
	{
		return id;
	}
	public void setId(String id)
	{
		this.id = id;
	}
	public String getAccount()
	{
		return account;
	}
	public void setAccount(String account)
	{
		this.account = account;
	}
	public String getAgentNumber()
	{
		return agentNumber;
	}
	public void setAgentNumber(String agentNumber)
	{
		this.agentNumber = agentNumber;
	}
	public String getAnswerTime()
	{
		return answerTime;
	}
	public void setAnswerTime(String answerTime)
	{
		this.answerTime = answerTime;
	}
	public String getCallType()
	{
		return callType;
	}
	public void setCallType(String callType)
	{
		this.callType = callType;
	}
	public String getCaller()
	{
		return caller;
	}
	public void setCaller(String caller)
	{
		this.caller = caller;
	}
	public String getAnswer()
	{
		return answer;
	}
	public void setAnswer(String answer)
	{
		this.answer = answer;
	}
	public String getRingSec()
	{
		return ringSec;
	}
	public void setRingSec(String ringSec)
	{
		this.ringSec = ringSec;
	}
	public String getHoldSec()
	{
		return holdSec;
	}
	public void setHoldSec(String holdSec)
	{
		this.holdSec = holdSec;
	}
	public String getDisposition()
	{
		return disposition;
	}
	public void setDisposition(String disposition)
	{
		this.disposition = disposition;
	}
	public String getRecordPath()
	{
		return recordPath;
	}
	public void setRecordPath(String recordPath)
	{
		this.recordPath = recordPath;
	}
	public String getUniqueId()
	{
		return uniqueId;
	}
	public void setUniqueId(String uniqueId)
	{
		this.uniqueId = uniqueId;
	}
	public String getIsAnswer()
	{
		return isAnswer;
	}
	public void setIsAnswer(String isAnswer)
	{
		this.isAnswer = isAnswer;
	}
	public String getOperator()
	{
		return operator;
	}
	public void setOperator(String operator)
	{
		this.operator = operator;
	}
	public String getPersonToDisplay()
	{
		return personToDisplay;
	}
	public void setPersonToDisplay(String personToDisplay)
	{
		this.personToDisplay = personToDisplay;
	}
	public String getIsDisplay()
	{
		return isDisplay;
	}
	public void setIsDisplay(String isDisplay)
	{
		this.isDisplay = isDisplay;
	}
	
	
}
