package com.dianfeng.service;

import java.util.List;

import com.dianfeng.entity.Record;
import com.dianfeng.entity.WorkOrderRecordLink;

public interface RecordService {
	List<Record> selectAllRecord(String seatNumber,String firstTime,String endTime,String inPhone,String score);
	List<Record> selectAllRecordSeatNotClick(String seatNumber,String firstTime,String endTime,String inPhone);
	List<Record> selectAllRecordUserNotClick(String seatNumber,String firstTime,String endTime,String inPhone);
	/**
	 * 根据uniqueId查找录音
	 * @param uniqueId
	 * @return
	 */
	List<WorkOrderRecordLink> getRecordByUniqueId(String uniqueId,String assemblyLine);
	
	/**
	 * 根据recordId查找录音
	 * @param recordId
	 * @return
	 */
	List<Record> getRecordByRecordId(String recordId);
	
	/**
	 * 插入工单录音关联表信息
	 * @param assemblyLine
	 * @param uniqueId
	 * @return
	 */
	int insertWordOrderRecordLink(String assemblyLine,String uniqueId);
}
