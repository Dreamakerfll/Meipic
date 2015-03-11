package com.dianfeng.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dianfeng.entity.AgentInfo;
import com.dianfeng.service.AgentInfoService;
import com.dianfeng.utils.JsonUtil;

@Component
@RequestMapping("/agent_info")
public class AgentInfoAction
{

	@Resource
	private AgentInfoService agentInfoService;

	public AgentInfoService getAgentInfoService()
	{
		return agentInfoService;
	}

	public void setAgentInfoService(AgentInfoService agentInfoService)
	{
		this.agentInfoService = agentInfoService;
	}
	
	@RequestMapping(params="method=findNoUseAgent")
	public void getNoUseAgent(String agent,HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		List<AgentInfo> agentInfoList = agentInfoService.getNoUseAgent(agent);
		
		AgentInfo agentInfo = new AgentInfo();
		agentInfo.setNumber("注销");
		agentInfo.setNumberDisplay("注销");
		agentInfoList.add(0,agentInfo);
		
		String returnJson =JsonUtil.toJson(agentInfoList);
		if(returnJson==null){
			returnJson="[]";
		}
		response.setCharacterEncoding("utf-8");
	    PrintWriter out = response.getWriter();
		out.print(returnJson);
		out.flush();
		out.close();
	}
	
	@RequestMapping(params="method=findAllAgent")
	public void getAllAgent(HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		List<AgentInfo> agentInfoList = agentInfoService.getAllAgent();
		
		String returnJson =JsonUtil.toJson(agentInfoList);
		if(returnJson==null){
			returnJson="[]";
		}
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		out.print(returnJson);
		out.flush();
		out.close();
	}
}
