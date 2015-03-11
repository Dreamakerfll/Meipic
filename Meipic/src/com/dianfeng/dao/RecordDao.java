package com.dianfeng.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dianfeng.entity.Record;
import com.dianfeng.entity.WorkOrderRecordLink;

public interface RecordDao {
	List<Record> selectAllRecord(@Param("seatNumber")String seatNumber,@Param("startTime")String startTime,
			@Param("endTime")String endTime,@Param("inPhone")String inPhone,@Param("score")String score);
	List<Record> selectAllRecordSeatNotClick(@Param("seatNumber")String seatNumber,@Param("startTime")String startTime,
			@Param("endTime")String endTime,@Param("inPhone")String inPhone);
	List<Record> selectAllRecordUserNotClick(@Param("seatNumber")String seatNumber,@Param("startTime")String startTime,
			@Param("endTime")String endTime,@Param("inPhone")String inPhone);
	/**
	 * 根据uniqueId,assemblyLine查找录音
	 * @param uniqueId
	 * @param assemblyLine
	 * @return
	 */
	List<WorkOrderRecordLink> getRecordByUniqueId(@Param("uniqueId")String uniqueId,@Param("assemblyLine")String assemblyLine);
	
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
	int insertWordOrderRecordLink(@Param("assemblyLine")String assemblyLine,@Param("uniqueId")String uniqueId);
}
