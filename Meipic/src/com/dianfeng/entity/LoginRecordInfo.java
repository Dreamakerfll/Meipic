package com.dianfeng.entity;

public class LoginRecordInfo
{
	/**
	 * 登录ID
	 */
	private String id;
	/**
	 * 登录时间
	 */
	private String loginTime;
	/**
	 * 登出时间
	 */
	private String logoutTime;
	/**
	 * 座席号
	 */
	private String agent;
	/**
	 * 账号
	 */
	private String account;

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getLoginTime()
	{
		return loginTime;
	}

	public void setLoginTime(String loginTime)
	{
		this.loginTime = loginTime;
	}

	public String getLogoutTime()
	{
		return logoutTime;
	}

	public void setLogoutTime(String logoutTime)
	{
		this.logoutTime = logoutTime;
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
	
}
