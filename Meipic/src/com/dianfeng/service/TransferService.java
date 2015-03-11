package com.dianfeng.service;

import java.util.List;

import com.dianfeng.entity.Transfer;

public interface TransferService
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
	List<Transfer> getTransferByCondition(String beginTime,String endTime,String phoneNumber,String area,String account);
	
	/**
	 * 添加转接信息
	 * @param transferList 转接实体List
	 * @return 插入的条数
	 */
	int addTransfer(List<Transfer> transferList);
	
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
	int updateStatusByBatchId(String batchId,String status);
	
	/**
	 * 判断转接区域是否已经存在
	 * @param areaList 转接区域
	 * @return
	 */
	String isAreaExist(List<String> areaList);
}
