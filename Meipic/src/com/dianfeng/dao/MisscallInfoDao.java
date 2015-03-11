package com.dianfeng.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dianfeng.entity.MisscallInfo;

public interface MisscallInfoDao
{
	/**
	 * 获取所有的未接来电
	 * @return 未接来电list
	 */
	List<MisscallInfo> getAllMisscall();
	
	/**
	 * 根据ID修改经办人
	 * @param id
	 * @param account
	 * @return 修改的条数
	 */
	int updateAccountById(@Param("id")String id,@Param("account")String account);
	
	/**
	 * 通过条件查找未接来电
	 * @param beginTime 开始时间
	 * @param endTime 结束时间
	 * @param phoneNumber 主叫号码
	 * @param phoneNumber_called 被叫号码
	 * @return 未接来电list
	 */
	List<MisscallInfo> getMisscallByCondition(@Param("beginTime")String beginTime,@Param("endTime")String endTime,@Param("phoneNumber")String phoneNumber,@Param("phoneNumber_called")String phoneNumber_called);
	
	/**
	 * 关闭未接来电
	 * @param id
	 * @return
	 * 更新的条数
	 */
	int updateMisscallStatus(String id);
}
