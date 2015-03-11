package com.dianfeng.listener;

import org.asteriskjava.manager.ManagerEventListener;
import org.asteriskjava.manager.event.AgentCalledEvent;
import org.asteriskjava.manager.event.ManagerEvent;

import com.dianfeng.entity.QueueLog;
import com.dianfeng.service.impl.ManagerServiceImpl;
import com.dianfeng.socket.GeneralFactory;



public class ManagerListener implements ManagerEventListener
{  
	
	private GeneralFactory gf = new GeneralFactory();
	private ManagerServiceImpl managerService=gf.getManagerService();
	
	public void onManagerEvent(ManagerEvent event)
	{
        
		
		if (event.getClass().getName().equals("org.asteriskjava.manager.event.AgentCalledEvent"))
		{

			AgentCalledEvent agentCalledEvent = (AgentCalledEvent) event;

			QueueLog queueLog = new QueueLog();
			
			queueLog.setAgent(agentCalledEvent.getAgentCalled());
			queueLog.setCallid(agentCalledEvent.getUniqueId());
			System.out.println(agentCalledEvent.getAgentCalled());
			queueLog.setEvent("ENTERQUEUE");
//			int count=queueLogService.updateQueueLog(queueLog);
			managerService.updateQueueLog(queueLog);
		}
	}
}
