package com.dianfeng.dao;

import java.util.List;

import com.dianfeng.entity.FollowHistoryInfo;

public interface FollowHistoryInfoDao
{
	/**
	 * 根据工单ID搜索工单的跟进历史记录
	 * @param workOrderId
	 * @return
	 */
	List<FollowHistoryInfo> getFollowHistoryInfo(String workOrderId);
	
	/**
	 * 新增工单跟进历史记录
	 * @param followHistoryInfo
	 * @return
	 * 新增的条数
	 */
	int insertFollowHistoryInfo(FollowHistoryInfo followHistoryInfo);
}
