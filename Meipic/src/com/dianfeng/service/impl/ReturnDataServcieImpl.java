package com.dianfeng.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dianfeng.dao.ReturnDataDao;
import com.dianfeng.entity.back.ReturnDepartment;
import com.dianfeng.entity.back.ReturnMissCallReport;
import com.dianfeng.entity.back.ReturnOnLineDetailReport;
import com.dianfeng.entity.back.ReturnSatisfactionDetailReport;
import com.dianfeng.entity.back.ReturnSeat;
import com.dianfeng.entity.back.data.DepartmentData;
import com.dianfeng.entity.back.data.DepartmentSeatData;
import com.dianfeng.entity.back.data.SeatTimeData;
import com.dianfeng.entity.back.data.SeatWorkData;
import com.dianfeng.entity.back.data.TrafficData;
import com.dianfeng.service.ReturnDataService;

@Service("ReturnDataServcie")
public class ReturnDataServcieImpl implements ReturnDataService{
	@Autowired
	private ReturnDataDao returnDataDao;
	
	@Override
	public List<ReturnDepartment> findAllDepartment() {
		return returnDataDao.findAllDepartment();
	}

	@Override
	public List<ReturnSeat> findAllSeat() {
		return returnDataDao.findAllSeat();
	}
	@Override
	public List<ReturnSeat> findAllSeatByAccount() {
		return returnDataDao.findAllSeatByAccount();
	}
	public ReturnDataDao getReturnDataDao() {
		return returnDataDao;
	}

	public void setReturnDataDao(ReturnDataDao returnDataDao) {
		this.returnDataDao = returnDataDao;
	}

	@Override
	public List<DepartmentData> departmentDayReportData(String departmentName, String selectDay, String hour) {
		return returnDataDao.departmentDayReportData(departmentName, selectDay, hour);
	}
	
	@Override
	public List<DepartmentData> departmentWeekReportData(String departmentName, String selectDay) {
		return returnDataDao.departmentWeekReportData(departmentName, selectDay);
	}

	@Override
	public List<DepartmentData> departmentMonthReportData(String departmentName, String selectDay) {
		return returnDataDao.departmentMonthReportData(departmentName, selectDay);
	}
	
	@Override
	public List<DepartmentData> departmentHourReportData(String departmentName, String startDay, String endDay, String hour) {
		return returnDataDao.departmentHourReportData(departmentName, startDay, endDay, hour);
	}
	
	@Override
	public List<DepartmentSeatData> departmentSeatDayReportData(String departmentName, String selectDay) {
		return returnDataDao.departmentSeatDayReportData(departmentName, selectDay);
	}

	@Override
	public List<DepartmentSeatData> departmentSeatMonthReportData(String departmentName, String selectDay) {
		return returnDataDao.departmentSeatMonthReportData(departmentName, selectDay);
	}

	@Override
	public List<DepartmentSeatData> departmentSeatWeekReportData(String departmentName, String selectDay) {
		return returnDataDao.departmentSeatWeekReportData(departmentName, selectDay);
	}


	@Override
	public List<ReturnMissCallReport> missCallReport(String startTime,String endTime, String seatNumber, String callNumber) {
		return returnDataDao.missCallReport(startTime, endTime, seatNumber, callNumber);
	}

	@Override
	public List<ReturnOnLineDetailReport> onLineDetailReport(String startTime,String endTime, String seatNumber) {
		return returnDataDao.onLineDetailReport(startTime, endTime, seatNumber);
	}

	@Override
	public List<ReturnSatisfactionDetailReport> satisfactionDetailReport(String startTime, String endTime, String seatNumber,String evaluationLevel) {
		return returnDataDao.satisfactionDetailReport(startTime, endTime, seatNumber, evaluationLevel);
	}

	@Override
	public List<SeatTimeData> seatTimeReport(String startTime,String endTime, String seatNumber) {
		return returnDataDao.seatTimeReport(startTime, endTime, seatNumber);
	}

	@Override
	public List<SeatWorkData> seatWorkDayReportData(String seatNumber,String selectDay, String hour) {
		return returnDataDao.seatWorkDayReportData(seatNumber, selectDay, hour);
	}

	@Override
	public List<SeatWorkData> seatWorkMonthReportData(String seatNumber, String selectDay) {
		return returnDataDao.seatWorkMonthReportData(seatNumber, selectDay);
	}

	@Override
	public List<SeatWorkData> seatWorkWeekReportData(String seatNumber,String selectDay) {
		return returnDataDao.seatWorkWeekReportData(seatNumber, selectDay);
	}

	@Override
	public List<TrafficData> trafficDayReportData(String selectDay) {
		return returnDataDao.trafficDayReportData(selectDay);
	}

	@Override
	public List<TrafficData> trafficMonthReportData(String selectDay) {
		return returnDataDao.trafficMonthReportData(selectDay);
	}

	@Override
	public List<TrafficData> trafficWeekReportData(String selectDay) {
		return returnDataDao.trafficWeekReportData(selectDay);
	}
	
	@Override
	public List<ReturnSatisfactionDetailReport> satisfactionDetailReportSeatNotClick(
			String startTime, String endTime, String seatNumber) {
		return returnDataDao.satisfactionDetailReportSeatNotClick(startTime,endTime,seatNumber);
	}
	@Override
	public List<ReturnSatisfactionDetailReport> satisfactionDetailReportUserNotClick(
			String startTime, String endTime, String seatNumber) {
		return returnDataDao.satisfactionDetailReportUserNotClick(startTime,endTime,seatNumber);
	}

}
