package com.dianfeng.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dianfeng.dao.RecordDao;
import com.dianfeng.entity.Record;
import com.dianfeng.entity.WorkOrderRecordLink;
import com.dianfeng.service.RecordService;
@Service("RecordService")
public class RecordServiceImpl implements RecordService{
	@Autowired
	private RecordDao dao;
	@Override
	public List<Record> selectAllRecord(String seatNumber, String firstTime,
			String endTime, String inPhone, String score) {
		return dao.selectAllRecord(seatNumber, firstTime, endTime, inPhone, score);
	}

	public RecordDao getDao() {
		return dao;
	}

	public void setDao(RecordDao dao) {
		this.dao = dao;
	}

	@Override
	public List<WorkOrderRecordLink> getRecordByUniqueId(String uniqueId,String assemblyLine)
	{
		return dao.getRecordByUniqueId(uniqueId,assemblyLine);
	}

	@Override
	public List<Record> getRecordByRecordId(String recordId)
	{
		return dao.getRecordByRecordId(recordId);
	}

	@Override
	public int insertWordOrderRecordLink(String assemblyLine, String uniqueId)
	{
		return dao.insertWordOrderRecordLink(assemblyLine, uniqueId);
	}
	@Override
	public List<Record> selectAllRecordSeatNotClick(String seatNumber,
			String firstTime, String endTime, String inPhone) {
		return dao.selectAllRecordSeatNotClick(seatNumber, firstTime, endTime, inPhone);
	}
	@Override
	public List<Record> selectAllRecordUserNotClick(String seatNumber,
			String firstTime, String endTime, String inPhone) {
		return dao.selectAllRecordUserNotClick(seatNumber, firstTime, endTime, inPhone);
	}
}
