package com.dianfeng.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dianfeng.dao.QuestionTypeDao;
import com.dianfeng.entity.QuestionType;
import com.dianfeng.service.QuestionTypeService;
@Service("QuestionTypeService")
public class QuestionTypeServiceImpl implements QuestionTypeService
{

	@Autowired
	private QuestionTypeDao questionTypeDao;
	
	@Override
	public List<QuestionType> getAllQuestionMold()
	{
		// TODO Auto-generated method stub
		return questionTypeDao.getAllQuestionMold();
	}

	@Override
	public List<QuestionType> getQuestionTypeByQuestionMold(String questionMold)
	{
		// TODO Auto-generated method stub
		return questionTypeDao.getQuestionTypeByQuestionMold(questionMold);
	}

}
