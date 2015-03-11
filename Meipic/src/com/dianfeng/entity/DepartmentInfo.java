package com.dianfeng.entity;

public class DepartmentInfo
{
	/**
	 * 部门ID
	 */
	private String id;
	/**
	 * 部门名称
	 */
	private String name;
	/**
	 * 部门名称在页面显示
	 */
	private String nameDisplay;
	/**
	 * 部门人数
	 */
	private String headcount;
	/**
	 * 部门负责人
	 */
	private String manager;
	public String getId()
	{
		return id;
	}
	public void setId(String id)
	{
		this.id = id;
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
	public String getHeadcount()
	{
		return headcount;
	}
	public void setHeadcount(String headcount)
	{
		this.headcount = headcount;
	}
	public String getManager()
	{
		return manager;
	}
	public void setManager(String manager)
	{
		this.manager = manager;
	}
	
}
