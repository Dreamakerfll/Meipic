package com.dianfeng.entity;

public class QuestionType
{
	/**
	 * 问题类型ID
	 */
	private String id;
	/**
	 * 问题大类
	 */
	private String mold;
	/**
	 * 问题类别
	 */
	private String type;
	/**
	 * 问题大类页面显示
	 */
	private String moldDisplay;
	/**
	 * 问题类别页面显示
	 */
	private String typeDisplay;
	public String getId()
	{
		return id;
	}
	public void setId(String id)
	{
		this.id = id;
	}
	public String getMold()
	{
		return mold;
	}
	public void setMold(String mold)
	{
		this.mold = mold;
	}
	public String getType()
	{
		return type;
	}
	public void setType(String type)
	{
		this.type = type;
	}
	public String getMoldDisplay()
	{
		return moldDisplay;
	}
	public void setMoldDisplay(String moldDisplay)
	{
		this.moldDisplay = moldDisplay;
	}
	public String getTypeDisplay()
	{
		return typeDisplay;
	}
	public void setTypeDisplay(String typeDisplay)
	{
		this.typeDisplay = typeDisplay;
	}
	
}
