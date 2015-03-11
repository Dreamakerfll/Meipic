package com.dianfeng.dao;

import java.util.List;

import com.dianfeng.entity.WorkOrderDetailInfo;
import com.dianfeng.entity.WorkOrderInfo;

public interface WorkOrderInfoDao
{
	/**
	 * 新增工单信息
	 * @param workOrderInfo 工单信息
	 * @return
	 * 新增的条数
	 */
	int insertWorkOrderInfo(WorkOrderInfo workOrderInfo);
	
	/**
	 * 更新工单信息
	 * @param workOrderInfo 工单信息
	 * @return
	 * 更新的条数
	 */
	int updateWorkOrderInfo(WorkOrderInfo workOrderInfo);
	
	/**
	 * 删除工单信息
	 * @param id 工单ID
	 * @return
	 * 删除的条数
	 */
	int deleteWorkOrderInfo(String id);
	
	/**
	 * 获取工单的详细信息（包括客户信息，手机信息，问题信息）
	 * @param workOrderDetailInfo 工单详细信息
	 * @return
	 * 返回工单的详细信息
	 */
	List<WorkOrderDetailInfo> getDetailWorkOrder(WorkOrderDetailInfo workOrderDetailInfo);
	
	/**
	 * 获取工单详细信息的条数
	 * @param workOrderDetailInfo 工单详细信息
	 * @return
	 * 返回条数
	 */
	int getDetailWorkOrderCount(WorkOrderDetailInfo workOrderDetailInfo);
}
