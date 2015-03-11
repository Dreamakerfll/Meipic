package com.dianfeng.entity;

public class RoleInfo
{
	/**
	 * ID
	 */
	private String id;
	/**
	 * 用户角色名
	 */
	private String name;
	/**
	 * 用户角色名在页面显示
	 */
	private String nameDisplay;
	/**
	 * 权限ID
	 */
	private String rightId;
	/**
	 * 权限内容
	 */
	private String rightContent;
	public String getId()
	{
		return id;
	}
	public void setId(String id)
	{
		this.id = id;
	}
	public String getRightId()
	{
		return rightId;
	}
	public void setRightId(String rightId)
	{
		this.rightId = rightId;
	}
	public String getRightContent()
	{
		return rightContent;
	}
	public void setRightContent(String rightContent)
	{
		this.rightContent = rightContent;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public String getNameDisplay()
	{
		return nameDisplay;
	}
	public void setNameDisplay(String nameDisplay)
	{
		this.nameDisplay = nameDisplay;
	}
	
	
}
