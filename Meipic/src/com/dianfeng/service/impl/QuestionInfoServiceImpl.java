package com.dianfeng.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dianfeng.dao.QuestionInfoDao;
import com.dianfeng.entity.QuestionInfo;
import com.dianfeng.service.QuestionInfoService;
@Service("QuestionInfoService")
public class QuestionInfoServiceImpl implements QuestionInfoService
{
	@Autowired
	private QuestionInfoDao questionInfoDao;

	@Override
	public int deleteQuestionInfo(List<QuestionInfo> questionInfoList)
	{
		// TODO Auto-generated method stub
		return questionInfoDao.deleteQuestionInfo(questionInfoList);
	}

	@Override
	public long getMaxQuestionId()
	{
		// TODO Auto-generated method stub
		return questionInfoDao.getMaxQuestionId();
	}

	@Override
	public int insertQuestionInfo(List<QuestionInfo> questionInfoList)
	{
		// TODO Auto-generated method stub
		return questionInfoDao.insertQuestionInfo(questionInfoList);
	}

	@Override
	public int updateQuestionInfo(List<QuestionInfo> questionInfoList)
	{
		// TODO Auto-generated method stub
		return questionInfoDao.updateQuestionInfo(questionInfoList);
	}

	@Override
	public int deleteQuestionInfoByWorkOrderId(String workOrderId)
	{
		// TODO Auto-generated method stub
		return questionInfoDao.deleteQuestionInfoByWorkOrderId(workOrderId);
	}

}
