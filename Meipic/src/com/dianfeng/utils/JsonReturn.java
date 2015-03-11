package com.dianfeng.utils;

import java.util.List;

public class JsonReturn
{

	/**
	 * 状态
	 * 200：成功
	 * 500：失败
	 */
	private String status;
	
	/**
	 * 实体结果
	 */
	private Object result;
	
	/**
	 * List结果
	 */
	private List<Object> resultList;
	
	/**
	 * 错误信息
	 */
	private String exception;
	
	/**
	 * 预留位，用作标记
	 */
	private String sign;

	public String getStatus()
	{
		return status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

	public Object getResult()
	{
		return result;
	}

	public void setResult(Object result)
	{
		this.result = result;
	}

	public List<Object> getResultList()
	{
		return resultList;
	}

	public void setResultList(List<Object> resultList)
	{
		this.resultList = resultList;
	}

	public String getException()
	{
		return exception;
	}

	public void setException(String exception)
	{
		this.exception = exception;
	}

	public String getSign()
	{
		return sign;
	}

	public void setSign(String sign)
	{
		this.sign = sign;
	}
	
	
	
}
