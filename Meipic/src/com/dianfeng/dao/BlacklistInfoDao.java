package com.dianfeng.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dianfeng.entity.BlacklistInfo;

public interface BlacklistInfoDao
{
	/**
	 * 获取所有的 黑名单
	 * @return 所有的黑名单
	 */
	List<BlacklistInfo> getAllBlacklist();
	
	/**
	 * 通过条件获取黑名单
	 * @param beginTime 开始时间
	 * @param endTime 结束时间
	 * @param phoneNumber 电话号码
	 * @param submitPerson 提交人
	 * @param agentComment 提交人备注
	 * @param status 状态
	 * @return 符合条件的黑名单
	 */
	List<BlacklistInfo> getBlacklistByCondition(@Param("beginTime")String beginTime,@Param("endTime")String endTime,@Param("phoneNumber")String phoneNumber,@Param("submitPerson")String submitPerson,@Param("agentComment")String agentComment,@Param("status")String status);
	
	/**
	 * 通过ID删除黑名单
	 * @param id
	 * @return 删除的条数
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
	 * @return 添加的条数
	 */
	int addBlacklist(@Param("beginTime")String beginTime,@Param("endTime")String endTime,@Param("phoneNumber")String phoneNumber,@Param("submitPerson")String submitPerson,@Param("agentComment")String agentComment,@Param("submitTime")String submitTime);
	
	/**
	 * 编辑黑名单
	 * @param beginTime 开始时间
	 * @param endTime 结束时间
	 * @param phoneNumber 电话号码
	 * @param agentComment 提交人备注
	 * @param id id
	 * @return 编辑的条数
	 */
	int editBlacklist(@Param("beginTime")String beginTime,@Param("endTime")String endTime,@Param("phoneNumber")String phoneNumber,@Param("agentComment")String agentComment,@Param("id")String id);
	
	/**
	 * 审核黑名单
	 * @param id ID
	 * @param account 用户名
	 * @param monitorComment 班长备注
	 * @param status 审核状态
	 * @return 更新的条数
	 */
	int updateBlacklist(@Param("id")String id,@Param("account")String account,@Param("monitorComment")String monitorComment,@Param("status")String status);
}
