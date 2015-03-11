package com.dianfeng.service;

import java.util.List;

import com.dianfeng.entity.QuestionType;

public interface QuestionTypeService
{
	/**
	 * 获取问题大类
	 * @return
	 * 返回所有问题大类
	 */
	List<QuestionType> getAllQuestionMold();
	
	/**
	 * 根据问题大类获取问题类别
	 * @param questionMold
	 * @return
	 * 返回问题类别
	 */
	List<QuestionType> getQuestionTypeByQuestionMold(String questionMold);
}
