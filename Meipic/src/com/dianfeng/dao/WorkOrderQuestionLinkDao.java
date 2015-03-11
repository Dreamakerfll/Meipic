package com.dianfeng.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dianfeng.entity.QuestionInfo;
import com.dianfeng.entity.WorkOrderQuestionLink;

public interface WorkOrderQuestionLinkDao
{
	/**
	 * 通过工单ID获取问题信息
	 * @param workOrderId 工单ID
	 * @return
	 * 问题信息
	 */
	List<QuestionInfo> getQuestionByWorkOrderId(String workOrderId);
	
	/**
	 * 新增工单问题关联信息
	 * @param workOrderQuestionLinkList 工单问题关联信息
	 * @return
	 * 新增的条数
	 */
	int insertWorkOrderQuestionLink(@Param(value="workOrderQuestionLinkList")List<WorkOrderQuestionLink> workOrderQuestionLinkList);
	
	/**
	 * 删除工单问题关联信息
	 * @param workOrderQuestionLinkList 工单问题关联信息
	 * @return
	 * 删除的条数
	 */
	int deleteWorkOrderQuestionLink(@Param(value="workOrderQuestionLinkList")List<WorkOrderQuestionLink> workOrderQuestionLinkList);
	
	/**
	 * 根据工单ID删除工单问题关联信息
	 * @param workOrderId 工单ID
	 * @return
	 * 删除的条数
	 */
	int deleteWorkOrderQuestionLinkByWorkOrderId(String workOrderId);
}
