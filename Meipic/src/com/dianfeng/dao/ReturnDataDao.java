package com.dianfeng.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

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

public interface ReturnDataDao {
	List<ReturnDepartment> findAllDepartment();
	List<ReturnSeat> findAllSeat();
	List<ReturnSeat> findAllSeatByAccount();
	List<SeatTimeData> seatTimeReport(@Param("startTime")String startTime,@Param("endTime")String endTime,@Param("seatNumber")String seatNumber);
	List<ReturnOnLineDetailReport> onLineDetailReport(@Param("startTime")String startTime,@Param("endTime")String endTime,@Param("seatNumber")String seatNumber);
	List<ReturnSatisfactionDetailReport> satisfactionDetailReport(@Param("startTime")String startTime,@Param("endTime")String endTime,@Param("seatNumber")String seatNumber,@Param("evaluationLevel")String evaluationLevel);
	List<ReturnSatisfactionDetailReport> satisfactionDetailReportUserNotClick(@Param("startTime")String startTime,@Param("endTime")String endTime,@Param("seatNumber")String seatNumber);
	List<ReturnSatisfactionDetailReport> satisfactionDetailReportSeatNotClick(@Param("startTime")String startTime,@Param("endTime")String endTime,@Param("seatNumber")String seatNumber);
	List<ReturnMissCallReport> missCallReport(@Param("startTime")String startTime,@Param("endTime")String endTime,@Param("seatNumber")String seatNumber,@Param("callNumber")String callNumber);
	List<SeatWorkData> seatWorkDayReportData(@Param("seatNumber")String seatNumber,@Param("selectDay")String selectDay,@Param("hour")String hour);
	List<SeatWorkData> seatWorkWeekReportData(@Param("seatNumber")String seatNumber,@Param("selectDay")String selectDay);
	List<SeatWorkData> seatWorkMonthReportData(@Param("seatNumber")String seatNumber,@Param("selectDay")String selectDay);
	List<DepartmentData> departmentDayReportData(@Param("departmentName")String departmentName,@Param("selectDay")String selectDay,@Param("hour")String hour);
	List<DepartmentData> departmentWeekReportData(@Param("departmentName")String departmentName,@Param("selectDay")String selectDay);
	List<DepartmentData> departmentMonthReportData(@Param("departmentName")String departmentName,@Param("selectDay")String selectDay);
	List<DepartmentData> departmentHourReportData(@Param("departmentName")String departmentName,@Param("startDay")String startDay,@Param("endDay")String endDay,@Param("hour")String hour);
	List<TrafficData> trafficDayReportData(@Param("selectDay")String selectDay);
	List<TrafficData> trafficWeekReportData(@Param("selectDay")String selectDay);
	List<TrafficData> trafficMonthReportData(@Param("selectDay")String selectDay);
	List<DepartmentSeatData> departmentSeatDayReportData(@Param("departmentName")String departmentName,@Param("selectDay")String selectDay);
	List<DepartmentSeatData> departmentSeatWeekReportData(@Param("departmentName")String departmentName,@Param("selectDay")String selectDay);
	List<DepartmentSeatData> departmentSeatMonthReportData(@Param("departmentName")String departmentName,@Param("selectDay")String selectDay);

}
