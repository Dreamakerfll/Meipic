package com.dianfeng.dao;

import org.apache.ibatis.annotations.Param;

import com.dianfeng.entity.QueueLog;



public interface QueueLogDao {
	int updateQueueLog(QueueLog queueLog);//更新
	int updateCdr(@Param(value="dialStatus")String dialStatus,@Param(value="UniqueId")String UniqueId,@Param(value="misscalltime")String misscalltime);//更新
	
}
