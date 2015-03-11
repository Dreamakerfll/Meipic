package com.dianfeng.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dianfeng.entity.Transfer;

public interface TransferDao
{
	/**
	 * 获取所有的 转接信息
	 */
	List<Transfer> getAllTransfer();
	
	/**
	 * 通过用户名获取转接信息
	 * @param account 用户名
	 * @return 转接信息
	 */
	List<Transfer> getTransferByAccount(String account);

	/**
	 * 通过多条件获取转接信息
	 * @param beginTime 开始时间
	 * @param endTime 结束时间
	 * @param phoneNumber 转接号码
	 * @param area 区域
	 * @param account 用户名
	 * @return 转接信息
	 */
	List<Transfer> getTransferByCondition(@Param("beginTime")String beginTime,@Param("endTime")String endTime,@Param("phoneNumber")String phoneNumber,@Param("area")String area,@Param("account")String account);
	
	/**
	 * 添加转接信息
	 * @param transferList 转接实体List
	 * @return 插入的条数
	 */
	int addTransfer(@Param(value="transferList")List<Transfer> transferList);
	
	/**
	 * 通过batchId删除转接信息
	 * @param batchId 转接batchId
	 * @return 删除的条数
	 */
	int deleteTransferByBatchId(String batchId);
	
	/**
	 * 通过batchId更新状态
	 * @param batchId 转接batchId
	 * @param status 状态
	 * @return 更新条数
	 */
	int updateStatusByBatchId(@Param("batchId")String batchId,@Param("status")String status);
	
	/**
	 * 判断转接区域是否已经存在
	 * @param areaList 转接区域
	 * @return 转接的区域
	 */
	String isAreaExist(@Param(value="areaList")List<String> areaList);
}
