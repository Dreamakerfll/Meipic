package com.dianfeng.service;

import java.util.List;

import com.dianfeng.entity.QuestionInfo;

public interface QuestionInfoService
{
	/**
	 * 获取最大的questionId
	 * @return
	 * 最大的questionId
	 */
	long getMaxQuestionId();
	
	/**
	 * 新增问题信息
	 * @param questionInfoList 问题信息list
	 * @return
	 * 新增的条数
	 */
	int insertQuestionInfo(List<QuestionInfo> questionInfoList);
	
	/**
	 * 更新问题信息
	 * @param questionInfoList 问题信息list
	 * @return
	 * 更新的条数
	 */
	int updateQuestionInfo(List<QuestionInfo> questionInfoList);
	
	/**
	 * 删除问题信息
	 * @param questionInfoList 问题信息list
	 * @return
	 * 删除的条数
	 */
	int deleteQuestionInfo(List<QuestionInfo> questionInfoList);
	
	/**
	 * 通过工单ID删除问题信息
	 * @param workOrderId 工单ID
	 * @return
	 * 删除的条数
	 */
	int deleteQuestionInfoByWorkOrderId(String workOrderId);
}
