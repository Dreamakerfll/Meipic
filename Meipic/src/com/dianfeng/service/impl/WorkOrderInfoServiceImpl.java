package com.dianfeng.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dianfeng.dao.WorkOrderInfoDao;
import com.dianfeng.entity.WorkOrderDetailInfo;
import com.dianfeng.entity.WorkOrderInfo;
import com.dianfeng.service.WorkOrderInfoService;
@Service("WorkOrderInfoService")
public class WorkOrderInfoServiceImpl implements WorkOrderInfoService
{
	@Autowired
	private WorkOrderInfoDao workOrderInfoDao;

	@Override
	public int insertWorkOrderInfo(WorkOrderInfo workOrderInfo)
	{
		// TODO Auto-generated method stub
		return workOrderInfoDao.insertWorkOrderInfo(workOrderInfo);
	}

	@Override
	public int updateWorkOrderInfo(WorkOrderInfo workOrderInfo)
	{
		// TODO Auto-generated method stub
		return workOrderInfoDao.updateWorkOrderInfo(workOrderInfo);
	}

	@Override
	public List<WorkOrderDetailInfo> getDetailWorkOrder(WorkOrderDetailInfo workOrderDetailInfo)
	{
		// TODO Auto-generated method stub
		return workOrderInfoDao.getDetailWorkOrder(workOrderDetailInfo);
	}

	@Override
	public int getDetailWorkOrderCount(WorkOrderDetailInfo workOrderDetailInfo)
	{
		// TODO Auto-generated method stub
		return workOrderInfoDao.getDetailWorkOrderCount(workOrderDetailInfo);
	}

	@Override
	public int deleteWorkOrderInfo(String id)
	{
		// TODO Auto-generated method stub
		return workOrderInfoDao.deleteWorkOrderInfo(id);
	}

}
