package com.dianfeng.entity;

public class QueueLog {
	
	private String id; 
	private String time; 
	private String callid;
	private String queuename;
	private String agent;
	private String event;
	private String data;
	public String getId()
	{
		return id;
	}
	public void setId(String id)
	{
		this.id = id;
	}
	public String getTime()
	{
		return time;
	}
	public void setTime(String time)
	{
		this.time = time;
	}
	public String getCallid()
	{
		return callid;
	}
	public void setCallid(String callid)
	{
		this.callid = callid;
	}
	public String getQueuename()
	{
		return queuename;
	}
	public void setQueuename(String queuename)
	{
		this.queuename = queuename;
	}
	public String getAgent()
	{
		return agent;
	}
	public void setAgent(String agent)
	{
		this.agent = agent;
	}
	public String getEvent()
	{
		return event;
	}
	public void setEvent(String event)
	{
		this.event = event;
	}
	public String getData()
	{
		return data;
	}
	public void setData(String data)
	{
		this.data = data;
	}
	
	
}
