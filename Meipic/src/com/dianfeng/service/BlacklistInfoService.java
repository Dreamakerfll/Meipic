package com.dianfeng.service;

import java.util.List;

import com.dianfeng.entity.BlacklistInfo;

public interface BlacklistInfoService
{
	/**
	 * 获取所有的 黑名单
	 * @return 所有的黑名单
	 */
	List<BlacklistInfo> getAllBlacklist();
	
	/**
	 * 根据条件获取黑名单
	 * @param beginTime 开始时间
	 * @param endTime 结束时间
	 * @param phoneNumber 电话号码
	 * @param submitPerson 提交人
	 * @param agentComment 提交人备注
	 * @param status 状态
	 * @return 符合条件的黑名单
	 */
	List<BlacklistInfo> getBlacklistByCondition(String beginTime,String endTime,String phoneNumber,String submitPerson,String agentComment,String status);
	
	/**
	 * 通过ID删除黑名单
	 * @param id
	 * @return 删除的个数
	 */
	int deleteBlacklistById(String id);
	
	/**
	 * 添加黑名单
	 * @param beginTime 开始时间
	 * @param endTime 结束时间
	 * @param phoneNumber 电话号码
	 * @param submitPerson 提交人
	 * @param agentComment 提交人备注
	 * @param agentComment 提交时间
	 * @return 添加的个数
	 */
	int addBlacklist(String beginTime,String endTime,String phoneNumber,String submitPerson,String agentComment,String submitTime);
	
	/**
	 * 编辑黑名单
	 * @param beginTime 开始时间
	 * @param endTime 结束时间
	 * @param phoneNumber 电话号码
	 * @param agentComment 提交人备注
	 * @param id id
	 * @return 编辑的条数
	 */
	int editBlacklist(String beginTime,String endTime,String phoneNumber,String agentComment,String id);
	
	/**
	 * 审核黑名单
	 * @param id ID
	 * @param account 用户名
	 * @param monitorComment 班长备注
	 * @param status 审核状态
	 * @return 更新的条数
	 */
	int updateBlacklist(String id,String account,String monitorComment,String status);
}
