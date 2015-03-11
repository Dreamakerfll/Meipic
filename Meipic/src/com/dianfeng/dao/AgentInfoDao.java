package com.dianfeng.dao;

import java.util.List;

import com.dianfeng.entity.AgentInfo;

public interface AgentInfoDao
{
	/**
	 * 获取没在使用的分机
	 * @param agent 当前的分机
	 * @return
	 * 未在使用的分机
	 */
	List<AgentInfo> getNoUseAgent(String agent);
	
	/**
	 * 获取所有的坐席
	 * @return
	 * 返回所有的坐席
	 */
	List<AgentInfo> getAllAgent();
}
