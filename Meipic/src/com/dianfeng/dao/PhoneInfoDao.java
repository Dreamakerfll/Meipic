package com.dianfeng.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dianfeng.entity.PhoneInfo;

public interface PhoneInfoDao
{
	/**
	 * 根据手机ID查询手机详细信息
	 * @param phoneId 手机ID
	 * @return
	 * 手机详细信息
	 */
	PhoneInfo getPhoneInfoByPhoneId(String phoneId);
	
	/**
	 * 根据客户ID查询手机详细信息
	 * @param customerId 客户ID
	 * @return
	 * 手机详细信息
	 */
	List<PhoneInfo> getPhoneInfoByCustomerId(String customerId);
	
	/**
	 * 获取手机信息表最大的phoneId
	 * @return
	 * 最大的phoneId
	 */
	long getMaxPhoneId();
	
	/**
	 * 新增手机信息
	 * @param phoneInfoList 手机信息List
	 * @return
	 * 返回新增的条数
	 */
	int insertPhoneInfo(@Param(value="phoneInfoList")List<PhoneInfo> phoneInfoList);
	
	/**
	 * 新增手机信息
	 * @param phoneInfo 手机对象
	 * @return
	 * 返回新增的条数
	 */
	int insertOnePhoneInfo(PhoneInfo phoneInfo);
	
	/**
	 * 更新手机信息
	 * @param phoneInfoList 手机信息List
	 * @return
	 * 返回更新条数
	 */
	int updatePhoneInfo(@Param(value="phoneInfoList")List<PhoneInfo> phoneInfoList);
	
	/**
	 * 删除手机信息
	 * @param phoneInfoList 手机信息List
	 * @return
	 * 返回删除条数
	 */
	int deletePhoneInfo(@Param(value="phoneInfoList")List<PhoneInfo> phoneInfoList);
	
	/**
	 * 根据客户ID删除手机信息
	 * @param customerId 客户ID
	 * @return
	 * 删除的条数
	 */
	int deletePhoneInfoByCustomerId(String customerId);
}
