package com.dianfeng.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dianfeng.dao.AgentInfoDao;
import com.dianfeng.entity.AgentInfo;
import com.dianfeng.service.AgentInfoService;
@Service("AgentInfoService")
public class AgentInfoServiceImpl implements AgentInfoService
{
	@Autowired
	private AgentInfoDao agentInfoDao;
	
	@Override
	public List<AgentInfo> getNoUseAgent(String agent)
	{
		// TODO Auto-generated method stub
		return agentInfoDao.getNoUseAgent(agent);
	}

	@Override
	public List<AgentInfo> getAllAgent()
	{
		// TODO Auto-generated method stub
		return agentInfoDao.getAllAgent();
	}

}
