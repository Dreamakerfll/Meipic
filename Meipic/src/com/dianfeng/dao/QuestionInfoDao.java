package com.dianfeng.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dianfeng.entity.QuestionInfo;

public interface QuestionInfoDao
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
	int insertQuestionInfo(@Param(value="questionInfoList")List<QuestionInfo> questionInfoList);
	
	/**
	 * 更新问题信息
	 * @param questionInfoList 问题信息list
	 * @return
	 * 更新的条数
	 */
	int updateQuestionInfo(@Param(value="questionInfoList")List<QuestionInfo> questionInfoList);
	
	/**
	 * 删除问题信息
	 * @param questionInfoList 问题信息list
	 * @return
	 * 删除的条数
	 */
	int deleteQuestionInfo(@Param(value="questionInfoList")List<QuestionInfo> questionInfoList);
	
	/**
	 * 通过工单ID删除问题信息
	 * @param workOrderId 工单ID
	 * @return
	 * 删除的条数
	 */
	int deleteQuestionInfoByWorkOrderId(String workOrderId);
	
}
