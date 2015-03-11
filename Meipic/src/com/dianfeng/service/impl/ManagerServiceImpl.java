package com.dianfeng.service.impl;



import org.springframework.beans.factory.annotation.Autowired;

import com.dianfeng.dao.QueueLogDao;
import com.dianfeng.entity.QueueLog;
import com.dianfeng.service.ManagerService;




public class ManagerServiceImpl implements ManagerService {
	
	@Autowired
	private QueueLogDao dao;

	
	public int updateQueueLog(QueueLog queueLog) {
		int count=dao.updateQueueLog(queueLog);
		return count ;
	}
	

}
