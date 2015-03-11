package com.dianfeng.service;

import java.util.List;

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

public interface ReturnDataService {
	List<ReturnDepartment> findAllDepartment();
	List<ReturnSeat> findAllSeat();
	List<ReturnSeat> findAllSeatByAccount();
	List<SeatTimeData> seatTimeReport(String startTime,String endTime,String seatNumber);
	List<ReturnOnLineDetailReport> onLineDetailReport(String startTime,String endTime,String seatNumber);
	List<ReturnSatisfactionDetailReport> satisfactionDetailReport(String startTime,String endTime,String seatNumber,String evaluationLevel);
	List<ReturnSatisfactionDetailReport> satisfactionDetailReportUserNotClick(String startTime,String endTime,String seatNumber);
	List<ReturnSatisfactionDetailReport> satisfactionDetailReportSeatNotClick(String startTime,String endTime,String seatNumber);
	List<ReturnMissCallReport> missCallReport(String startTime,String endTime,String seatNumber,String callNumber);
	List<SeatWorkData> seatWorkDayReportData(String seatNumber,String selectDay,String hour);
	List<SeatWorkData> seatWorkWeekReportData(String seatNumber,String selectDay);
	List<SeatWorkData> seatWorkMonthReportData(String seatNumber,String selectDay);
	List<DepartmentData> departmentDayReportData(String departmentName,String selectDay,String hour);
	List<DepartmentData> departmentWeekReportData(String departmentName,String selectDay);
	List<DepartmentData> departmentMonthReportData(String departmentName,String selectDay);
	List<DepartmentData> departmentHourReportData(String departmentName,String startDay,String endDay,String hour);
	List<TrafficData> trafficDayReportData(String selectDay);
	List<TrafficData> trafficWeekReportData(String selectDay);
	List<TrafficData> trafficMonthReportData(String selectDay);
	List<DepartmentSeatData> departmentSeatDayReportData(String departmentName,String selectDay);
	List<DepartmentSeatData> departmentSeatWeekReportData(String departmentName,String selectDay);
	List<DepartmentSeatData> departmentSeatMonthReportData(String departmentName,String selectDay);
}
