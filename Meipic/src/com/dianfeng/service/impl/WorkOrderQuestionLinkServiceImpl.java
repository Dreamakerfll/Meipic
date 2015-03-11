package com.dianfeng.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dianfeng.dao.WorkOrderQuestionLinkDao;
import com.dianfeng.entity.QuestionInfo;
import com.dianfeng.entity.WorkOrderQuestionLink;
import com.dianfeng.service.WorkOrderQuestionLinkService;
@Service("WorkOrderQuestionLinkService")
public class WorkOrderQuestionLinkServiceImpl implements WorkOrderQuestionLinkService
{

	@Autowired
	private WorkOrderQuestionLinkDao workOrderQuestionLinkDao;
	
	@Override
	public List<QuestionInfo> getQuestionByWorkOrderId(String workOrderId)
	{
		// TODO Auto-generated method stub
		return workOrderQuestionLinkDao.getQuestionByWorkOrderId(workOrderId);
	}

	@Override
	public int deleteWorkOrderQuestionLink(List<WorkOrderQuestionLink> workOrderQuestionLinkList)
	{
		// TODO Auto-generated method stub
		return workOrderQuestionLinkDao.deleteWorkOrderQuestionLink(workOrderQuestionLinkList);
	}

	@Override
	public int insertWorkOrderQuestionLink(List<WorkOrderQuestionLink> workOrderQuestionLinkList)
	{
		// TODO Auto-generated method stub
		return workOrderQuestionLinkDao.insertWorkOrderQuestionLink(workOrderQuestionLinkList);
	}

	@Override
	public int deleteWorkOrderQuestionLinkByWorkOrderId(String workOrderId)
	{
		// TODO Auto-generated method stub
		return workOrderQuestionLinkDao.deleteWorkOrderQuestionLinkByWorkOrderId(workOrderId);
	}

}
