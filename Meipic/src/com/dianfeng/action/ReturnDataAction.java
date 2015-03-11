package com.dianfeng.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dianfeng.entity.CustomerPhoneLinkDetailInfo;
import com.dianfeng.entity.WorkOrderDetailInfo;
import com.dianfeng.entity.back.ReturnDepartment;
import com.dianfeng.entity.back.ReturnDepartmentReport;
import com.dianfeng.entity.back.ReturnDepartmentSeatReport;
import com.dianfeng.entity.back.ReturnMissCallReport;
import com.dianfeng.entity.back.ReturnOnLineDetailReport;
import com.dianfeng.entity.back.ReturnSatisfactionDetailReport;
import com.dianfeng.entity.back.ReturnSeat;
import com.dianfeng.entity.back.ReturnSeatTimeReport;
import com.dianfeng.entity.back.ReturnSeatWorkReport;
import com.dianfeng.entity.back.ReturnTrafficReport;
import com.dianfeng.entity.back.data.DepartmentData;
import com.dianfeng.entity.back.data.DepartmentSeatData;
import com.dianfeng.entity.back.data.SeatTimeData;
import com.dianfeng.entity.back.data.SeatWorkData;
import com.dianfeng.entity.back.data.TrafficData;
import com.dianfeng.service.CustomerPhoneLinkService;
import com.dianfeng.service.ReturnDataService;
import com.dianfeng.service.WorkOrderInfoService;
import com.dianfeng.utils.Help;
import com.dianfeng.utils.JsonHelp;
import com.dianfeng.utils.JsonUtil;

@Component
@RequestMapping("/returnData")
public class ReturnDataAction {
	@Resource
	private ReturnDataService returnDataService;
	@Resource
	private WorkOrderInfoService workOrderInfoService;
	@Resource
	private CustomerPhoneLinkService customerPhoneLinkService;

	/**
	 * 导出工单详情
	 * @param work_order_detail_info
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(params="method=exportWorkOrder")
	public void exportWorkOrder(String workOrderInfo,HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		WorkOrderDetailInfo workOrderDetailInfo = new WorkOrderDetailInfo();
		if(workOrderInfo != null)
		{
			workOrderDetailInfo = JsonUtil.fromJson(workOrderInfo, WorkOrderDetailInfo.class);
		}
		else
		{
			workOrderDetailInfo.setPage(0);
			workOrderDetailInfo.setRows(1000);
		}
		
		List<WorkOrderDetailInfo> workOrderDetailInfoList = workOrderInfoService.getDetailWorkOrder(workOrderDetailInfo);
		
		String returnJson =JsonHelp.listToJsonStr(workOrderDetailInfoList);
		
		if(returnJson==null){
			returnJson="[]";
		}
		response.setCharacterEncoding("utf-8");
	    PrintWriter out = response.getWriter();
		out.print(returnJson);
		out.flush();
		out.close();
	}
	
	/**
	 * 导出客户详情
	 * @param customer_phone_info_json
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(params="method=exportCustomer")
	public void exportCustomer(String customerInfo,HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		CustomerPhoneLinkDetailInfo customerPhoneLinkDetailInfo = new CustomerPhoneLinkDetailInfo();
		if(customerInfo != null)
		{
			customerPhoneLinkDetailInfo = JsonUtil.fromJson(customerInfo, CustomerPhoneLinkDetailInfo.class);
		}
		else
		{
			customerPhoneLinkDetailInfo.setPage(0);
			customerPhoneLinkDetailInfo.setRows(1000);
		}
		
		List<CustomerPhoneLinkDetailInfo> customerPhoneLinkDetailInfoList = customerPhoneLinkService.getCustomerPhoneLinkDetailInfoByCondition(customerPhoneLinkDetailInfo);
		
		String returnJson = JsonHelp.listToJsonStr(customerPhoneLinkDetailInfoList);
		
		if(returnJson==null){
			returnJson="[]";
		}
		
		response.setCharacterEncoding("utf-8");
	    PrintWriter out = response.getWriter();
		out.print(returnJson);
		out.flush();
		out.close();
	}
	
	@RequestMapping(params="method=findAllSeat")
	public void findAllSeat(HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		List<ReturnSeat> seatList = returnDataService.findAllSeat();
		
		String returnJson =JsonHelp.listToJsonStr(seatList);
		
		if(returnJson==null){
			returnJson="[]";
		}
		response.setCharacterEncoding("utf-8");
	    PrintWriter out = response.getWriter();
		out.print(returnJson);
		out.flush();
		out.close();
	}
	
	@RequestMapping(params="method=findAllSeatByAccount")
	public void findAllSeatByAccount(HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		List<ReturnSeat> seatList = returnDataService.findAllSeatByAccount();
		
		String returnJson =JsonHelp.listToJsonStr(seatList);
		
		if(returnJson==null){
			returnJson="[]";
		}
		response.setCharacterEncoding("utf-8");
	    PrintWriter out = response.getWriter();
		out.print(returnJson);
		out.flush();
		out.close();
	}
	
	@RequestMapping(params="method=findAllDepartment")
	public void findAllDepartment(HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		List<ReturnDepartment> departmentList = returnDataService.findAllDepartment();
		
		String returnJson =JsonHelp.listToJsonStr(departmentList);
		
		if(returnJson==null){
			returnJson="[]";
		}
		
		response.setCharacterEncoding("utf-8");
	    PrintWriter out = response.getWriter();
		out.print(returnJson);
		out.flush();
		out.close();
	}
	
	@RequestMapping(params="method=seatTimeReportData")
	public void seatTimeReportData(String startTime,String endTime,String seatNumber,HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		List<SeatTimeData> seatTimeDataList = returnDataService.seatTimeReport(startTime, endTime, seatNumber);
		
		Map<String,Map<String,List<SeatTimeData>>> day_seatNumber_list = new HashMap<String, Map<String,List<SeatTimeData>>>();
		
		//进行数据处理.
		for(SeatTimeData data : seatTimeDataList){
			Map<String,List<SeatTimeData>> day_map =  day_seatNumber_list.get(data.getSelectDay());
			
			if(day_map == null)
				day_map  = new HashMap<String,List<SeatTimeData>>();
			
			List<SeatTimeData> list = day_map.get(data.getSeatNumber());
			
			if(list == null)
				list  = new ArrayList<SeatTimeData>();
			
			list.add(data);
			
			day_map.put(data.getSeatNumber(), list);
			
			day_seatNumber_list.put(data.getSelectDay(),day_map);
		}
		
		//返回结果
		List<ReturnSeatTimeReport> returnList = new ArrayList<ReturnSeatTimeReport>();

		for(String selectDay : day_seatNumber_list.keySet()){
			for(String seatNumberKey : day_seatNumber_list.get(selectDay).keySet()){
				List<SeatTimeData> list =  day_seatNumber_list.get(selectDay).get(seatNumberKey);
				ReturnSeatTimeReport seatTimeReport = new ReturnSeatTimeReport();
				seatTimeReport.setSelectDay(selectDay);		//日期
				seatTimeReport.setSeatNumber(seatNumberKey);	//坐席
				Integer allTime = 0 ;	//总时长
				for(SeatTimeData seatTimeData : list){
					int  intervalTime = Integer.parseInt(seatTimeData.getIntervalTime());
					String intervalTimeToTime = Help.secToTime(intervalTime);
					allTime += intervalTime;
					if(seatTimeData.getStatus().equals("1")){
						seatTimeReport.setSumOnLineTime(intervalTimeToTime);//在线
					}else if(seatTimeData.getStatus().equals("2")){
						seatTimeReport.setSumBusyTime(intervalTimeToTime);	//示忙
					}else if(seatTimeData.getStatus().equals("3")){
						seatTimeReport.setSumOutLineTime(intervalTimeToTime);//下线
					}
				}
				seatTimeReport.setSumAllTime(Help.secToTime(allTime));
				returnList.add(seatTimeReport);
			}
		}
		//排序
		Collections.sort(returnList,new Comparator<ReturnSeatTimeReport>(){
	        public int compare(ReturnSeatTimeReport arg0, ReturnSeatTimeReport arg1) {
	        	if(arg0.getSelectDay().equals(arg1.getSelectDay()))
	        		return arg0.getSeatNumber().compareTo(arg1.getSeatNumber());
	        	else
	        		return arg1.getSelectDay().compareTo(arg0.getSelectDay());
	        }
	    });
		
		String returnJson =  JsonHelp.listToJsonStr(returnList);
		
		if(returnJson==null || returnJson.equals("") || returnJson.equals("null"))
			returnJson = "[]";
		
		response.setCharacterEncoding("utf-8");
	    PrintWriter out = response.getWriter();
		out.print(returnJson);
		out.flush();
		out.close();
	}
	
	@RequestMapping(params="method=onLineDetailReportData")
	public void onLineDetailReportData(String startTime,String endTime,String seatNumber,HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		List<ReturnOnLineDetailReport> onLineDetailReportList = returnDataService.onLineDetailReport(startTime, endTime, seatNumber);
		
		for(ReturnOnLineDetailReport report : onLineDetailReportList){
			int  intervalTime = Integer.parseInt(report.getBetweenTime().equals("")?"0":report.getBetweenTime());
			String intervalTimeToTime = Help.secToTime(intervalTime);
			report.setBetweenTime(intervalTimeToTime);
		}
		
		String returnJson =JsonHelp.listToJsonStr(onLineDetailReportList);
		
		if(returnJson==null){
			returnJson="[]";
		}
		
		response.setCharacterEncoding("utf-8");
	    PrintWriter out = response.getWriter();
		out.print(returnJson);
		out.flush();
		out.close();
	}
	
	@RequestMapping(params="method=satisfactionDetailReportData")
	public void satisfactionDetailReportData(String startTime,String endTime,String seatNumber,String evaluationLevel,HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		List<ReturnSatisfactionDetailReport> satisfactionDetailReportList;
		// = returnDataService.satisfactionDetailReport(startTime, endTime, seatNumber, evaluationLevel);
		
		if(evaluationLevel.equals("-1")){
			satisfactionDetailReportList = returnDataService.satisfactionDetailReportSeatNotClick(startTime, endTime, seatNumber);
			for(ReturnSatisfactionDetailReport returnSatisfactionDetailReport : satisfactionDetailReportList){
				returnSatisfactionDetailReport.setEvaluationLevel("坐席未点");
			}
		}else if(evaluationLevel.equals("-2")){
			satisfactionDetailReportList = returnDataService.satisfactionDetailReportUserNotClick(startTime, endTime, seatNumber);
			for(ReturnSatisfactionDetailReport returnSatisfactionDetailReport : satisfactionDetailReportList){
				returnSatisfactionDetailReport.setEvaluationLevel("用户未评");
			}
		}else{
			satisfactionDetailReportList = returnDataService.satisfactionDetailReport(startTime, endTime, seatNumber, evaluationLevel);
			for(ReturnSatisfactionDetailReport returnSatisfactionDetailReport : satisfactionDetailReportList){
				if(returnSatisfactionDetailReport.getEvaluationLevel() == null||returnSatisfactionDetailReport.getEvaluationLevel().equals("")||returnSatisfactionDetailReport.getEvaluationLevel().equals("null")){
					if(returnSatisfactionDetailReport.getRecordType() == null || returnSatisfactionDetailReport.getRecordType().equals("")){
						returnSatisfactionDetailReport.setEvaluationLevel("坐席未点");
		    		}else{
		    			returnSatisfactionDetailReport.setEvaluationLevel("用户未评");
		    		}
				}
			}
		}
		
		String returnJson = JsonHelp.listToJsonStr(satisfactionDetailReportList);
		
		if(returnJson==null){
			returnJson="[]";
		}
		
		response.setCharacterEncoding("utf-8");
	    PrintWriter out = response.getWriter();
		out.print(returnJson);
		out.flush();
		out.close();
	}
	
	@RequestMapping(params="method=missCallReportData")
	public void missCallReportData(String startTime,String endTime,String seatNumber,String callNumber,HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		List<ReturnMissCallReport> missCallReportList = returnDataService.missCallReport(startTime, endTime, seatNumber, callNumber);
		
		String returnJson =JsonHelp.listToJsonStr(missCallReportList);
		
		if(returnJson==null){
			returnJson="[]";
		}
		
		response.setCharacterEncoding("utf-8");
	    PrintWriter out = response.getWriter();
		out.print(returnJson);
		out.flush();
		out.close();
	}
	/** 第二层 */
	@RequestMapping(params="method=seatWorkDayReportData")
	public void seatWorkDayReportData(String seatNumber,String selectDay,String hour,HttpServletRequest request,HttpServletResponse response) throws IOException{
		List<SeatWorkData> seatWorkHourDataList = returnDataService.seatWorkDayReportData(seatNumber, selectDay, hour);
		response.setCharacterEncoding("utf-8");
	    PrintWriter out = response.getWriter();
		out.print(seatWorkDayData(seatWorkHourDataList,"hour"));
		out.flush();
		out.close();
	}
	@RequestMapping(params="method=seatWorkWeekReportData")
	public void seatWorkWeekReportData(String seatNumber,String selectDay,HttpServletRequest request,HttpServletResponse response) throws IOException{
		List<SeatWorkData> seatWorkDayDataList = returnDataService.seatWorkWeekReportData(seatNumber, selectDay);
		response.setCharacterEncoding("utf-8");
	    PrintWriter out = response.getWriter();
		out.print(seatWorkDayData(seatWorkDayDataList,"day"));
		out.flush();
		out.close();
	}
	@RequestMapping(params="method=seatWorkMonthReportData")
	public void seatWorkMonthReportData(String seatNumber,String selectDay,HttpServletRequest request,HttpServletResponse response) throws IOException{
		List<SeatWorkData> seatWorkDayDataList = returnDataService.seatWorkMonthReportData(seatNumber, selectDay);
		response.setCharacterEncoding("utf-8");
	    PrintWriter out = response.getWriter();
		out.print(seatWorkDayData(seatWorkDayDataList,"day"));
		out.flush();
		out.close();
	}
	
	/** 第三层 */
	@RequestMapping(params="method=departmentDayReportData")
	public void departmentDayReportData(String departmentName,String selectDay,String hour,HttpServletRequest request,HttpServletResponse response) throws IOException{
		List<DepartmentData> departmentDayReportList = returnDataService.departmentDayReportData(departmentName, selectDay, hour);
		response.setCharacterEncoding("utf-8");
	    PrintWriter out = response.getWriter();
	    out.print(departmentData(departmentDayReportList,"hour"));
		out.flush();
		out.close();
	}
	@RequestMapping(params="method=departmentWeekReportData")
	public void departmentWeekReportData(String departmentName,String selectDay,HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		List<DepartmentData> departmentWeekReportList = returnDataService.departmentWeekReportData(departmentName, selectDay);
		response.setCharacterEncoding("utf-8");
	    PrintWriter out = response.getWriter();
		out.print(departmentData(departmentWeekReportList,"day"));
		out.flush();
		out.close();
	}
	@RequestMapping(params="method=departmentMonthReportData")
	public void departmentMonthReportData(String departmentName,String selectDay,HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		List<DepartmentData> departmentMonthReportList = returnDataService.departmentMonthReportData(departmentName, selectDay);
		response.setCharacterEncoding("utf-8");
	    PrintWriter out = response.getWriter();
		out.print(departmentData(departmentMonthReportList,"day"));
		out.flush();
		out.close();
	}
	@RequestMapping(params="method=departmentHourReportData")
	public void departmentHourReportData(String departmentName,String startDay,String endDay,String hour,HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		List<DepartmentData> departmentHourReportList = returnDataService.departmentHourReportData(departmentName, startDay, endDay, hour);
		response.setCharacterEncoding("utf-8");
	    PrintWriter out = response.getWriter();
	    out.print(departmentData(departmentHourReportList,"hour"));
		out.flush();
		out.close();
	}
	/**第四层*/
	@RequestMapping(params="method=trafficDayReportData")
	public void trafficDayReportData(String selectDay,HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		List<TrafficData> trafficDataList = returnDataService.trafficDayReportData(selectDay);
		response.setCharacterEncoding("utf-8");
	    PrintWriter out = response.getWriter();
		out.print(trafficData(trafficDataList));
		out.flush();
		out.close();
	}
	@RequestMapping(params="method=trafficWeekReportData")
	public void trafficWeekReportData(String selectDay,HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		List<TrafficData> trafficDataList = returnDataService.trafficWeekReportData(selectDay);
		response.setCharacterEncoding("utf-8");
	    PrintWriter out = response.getWriter();
		out.print(trafficData(trafficDataList));
		out.flush();
		out.close();
	}

	@RequestMapping(params="method=trafficMonthReportData")
	public void trafficMonthReportData(String selectDay,HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		List<TrafficData> trafficDataList = returnDataService.trafficMonthReportData(selectDay);
		response.setCharacterEncoding("utf-8");
	    PrintWriter out = response.getWriter();
		out.print(trafficData(trafficDataList));
		out.flush();
		out.close();
	}
	/**第五层*/
	@RequestMapping(params="method=departmentSeatDayReportData")
	public void departmentSeatDayReportData(String departmentName,String selectDay,HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		List<DepartmentSeatData> departmentSeatDataList = returnDataService.departmentSeatDayReportData(departmentName, selectDay);
		response.setCharacterEncoding("utf-8");
	    PrintWriter out = response.getWriter();
		out.print(departmentSeatData(departmentSeatDataList));
		out.flush();
		out.close();
	}

	@RequestMapping(params="method=departmentSeatWeekReportData")
	public void departmentSeatWeekReportData(String departmentName,String selectDay,HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		List<DepartmentSeatData> departmentSeatDataList = returnDataService.departmentSeatWeekReportData(departmentName, selectDay);
		response.setCharacterEncoding("utf-8");
	    PrintWriter out = response.getWriter();
		out.print(departmentSeatData(departmentSeatDataList));
		out.flush();
		out.close();
	}
	
	@RequestMapping(params="method=departmentSeatMonthReportData")
	public void departmentSeatMonthReportData(String departmentName,String selectDay,HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		List<DepartmentSeatData> departmentSeatDataList = returnDataService.departmentSeatMonthReportData(departmentName, selectDay);
		response.setCharacterEncoding("utf-8");
	    PrintWriter out = response.getWriter();
		out.print(departmentSeatData(departmentSeatDataList));
		out.flush();
		out.close();
	}
	
	
	/** 坐席个人报表 分时  ok*/
	private String seatWorkDayData(List<SeatWorkData> seatWorkDayDataList,final String type){
		Map<String,List<SeatWorkData>> day_or_hour_list = new HashMap<String,List<SeatWorkData>>();
		
		for(SeatWorkData seatWorkData : seatWorkDayDataList){
			List<SeatWorkData> list =  null;
			if(type.equals("day"))
				list = day_or_hour_list.get(seatWorkData.getSelectDay());
			else if(type.equals("hour"))
				list = day_or_hour_list.get(seatWorkData.getHour());
			
			if(list == null)
				list = new ArrayList<SeatWorkData>();
			
			list.add(seatWorkData);
			if(type.equals("day"))
				day_or_hour_list.put(seatWorkData.getSelectDay(), list);
			else if(type.equals("hour"))
				day_or_hour_list.put(seatWorkData.getHour(), list);
		}
		
		List<ReturnSeatWorkReport> returnList = new ArrayList<ReturnSeatWorkReport>();
		
		for(String dayOrHour : day_or_hour_list.keySet()){
			List<SeatWorkData> list =  day_or_hour_list.get(dayOrHour);
			ReturnSeatWorkReport  returnSeatWorkReport =  new ReturnSeatWorkReport();
			
			Integer allSum = 0 ;	//总时长
			Integer inSum = 0 ;		//ACD呼入总时长
			Integer inCount = 0;	//ACD来电量
			for(SeatWorkData seatWorkData : list){
				Integer sum = Integer.parseInt(seatWorkData.getSum());
				Integer count = Integer.parseInt(seatWorkData.getCount());
				allSum += sum;
				returnSeatWorkReport.setSelectDay(seatWorkData.getSelectDay());
				returnSeatWorkReport.setSelectHour(seatWorkData.getHour());
				if(seatWorkData.getCallType().equals("in")){
					inSum += sum;
					inCount += count;
					if(seatWorkData.getDisposition().equals("ANSWERED")){
						returnSeatWorkReport.setCountSuccessACD(count.toString());	//接通
					}else{
						returnSeatWorkReport.setCountFailureACD(count.toString());	//未接
					}
				}else if(seatWorkData.getCallType().equals("out")){
					returnSeatWorkReport.setCountCallOut(count.toString());	//外拨呼叫量
					returnSeatWorkReport.setSumCallOut(sum.toString());		//外拨呼叫总时长
				}else if(seatWorkData.getCallType().equals("internal")){
					returnSeatWorkReport.setCountInternal(count.toString());	//内部呼叫量
					returnSeatWorkReport.setSumInternal(sum.toString());		//内部呼叫总时长
				}
			}
			returnSeatWorkReport.setCountACD(inCount.toString());
			returnSeatWorkReport.setSumCallInACD(inSum.toString());
			returnSeatWorkReport.setSumAllCallTime(allSum.toString());
			returnList.add(returnSeatWorkReport);
		}
		//排序
		Collections.sort(returnList,new Comparator<ReturnSeatWorkReport>(){
	        public int compare(ReturnSeatWorkReport arg0, ReturnSeatWorkReport arg1) {
	        	if(type.equals("day"))
	        		return arg0.getSelectDay().compareTo(arg1.getSelectDay());
	        	else
	        		return arg0.getSelectHour().compareTo(arg1.getSelectHour());
	        }
	    });
		
		String returnJson =  JsonHelp.listToJsonStr(returnList);
		
		if(returnJson==null || returnJson.equals("") || returnJson.equals("null"))
			returnJson = "[]";
		
		return returnJson;
	}
	/**技能组报表数据 */
	private String departmentData(List<DepartmentData> departmentDataList,final String type){
		Map<String,List<DepartmentData>> day_or_hour_list = new HashMap<String,List<DepartmentData>>();
		
		for(DepartmentData departmentData : departmentDataList){
			List<DepartmentData> list = null;
			if(type.equals("day")){
				list = day_or_hour_list.get(departmentData.getSelectDay());
			}else{
				list = day_or_hour_list.get(departmentData.getHour());
			}
			
			if(list == null)
				list = new ArrayList<DepartmentData>();
			
			list.add(departmentData);
			if(type.equals("day")){
				day_or_hour_list.put(departmentData.getSelectDay(), list);
			}else{
				day_or_hour_list.put(departmentData.getHour(), list);
			}
		}
		
		List<ReturnDepartmentReport> returnList = new ArrayList<ReturnDepartmentReport>();
		
		for(String dayOrHourKey : day_or_hour_list.keySet()){
			List<DepartmentData> list = day_or_hour_list.get(dayOrHourKey);
			ReturnDepartmentReport  returnDepartmentReport =  new ReturnDepartmentReport();
			if(type.equals("day")){
				returnDepartmentReport.setSelectDay(dayOrHourKey);
			}else{
				returnDepartmentReport.setSelectHour(dayOrHourKey);
			}
			
			Integer allSum = 0 ;	//总时长
			Integer inSum = 0 ;		//ACD呼入总时长
			Integer inCount = 0;	//ACD来电量
			
			for(DepartmentData seatWorkData : list){
				Integer sum = Integer.parseInt(seatWorkData.getSum());
				Integer count = Integer.parseInt(seatWorkData.getCount());
				allSum += sum;
				if(seatWorkData.getCallType().equals("in")){
					inSum += sum;
					inCount += count;
					if(seatWorkData.getDisposition().equals("ANSWERED")){
						returnDepartmentReport.setCountSuccessACD(count.toString());	//接通
					}else{
						returnDepartmentReport.setCountFailureACD(count.toString());	//未接
					}
				}else if(seatWorkData.getCallType().equals("out")){
					returnDepartmentReport.setCountCallOut(count.toString());	//外拨呼叫量
					returnDepartmentReport.setSumCallOut(sum.toString());		//外拨呼叫总时长
				}else if(seatWorkData.getCallType().equals("internal")){
					returnDepartmentReport.setCountInternal(count.toString());	//内部呼叫量
					returnDepartmentReport.setSumInternal(sum.toString());	//内部呼叫总时长
				}
			}
			returnDepartmentReport.setCountACD(inCount.toString());
			returnDepartmentReport.setSumCallInACD(inSum.toString());
			returnDepartmentReport.setSumAllCallTime(allSum.toString());
			returnList.add(returnDepartmentReport);
		}
		//排序
		Collections.sort(returnList,new Comparator<ReturnDepartmentReport>(){
	        public int compare(ReturnDepartmentReport arg0, ReturnDepartmentReport arg1) {
	        	if(type.equals("day"))
	        		return arg0.getSelectDay().compareTo(arg1.getSelectDay());
	        	else
	        		return arg0.getSelectHour().compareTo(arg1.getSelectHour());
	        }
	    });

		String returnJson =  JsonHelp.listToJsonStr(returnList);
		
		if(returnJson==null || returnJson.equals("") || returnJson.equals("null"))
			returnJson = "[]";
		
		return returnJson;
	}
	/**话务量报表数据 */
	private String trafficData(List<TrafficData> trafficDataList){
		Map<String,List<TrafficData>> department_list = new HashMap<String,List<TrafficData>>();
		
		for(TrafficData trafficData : trafficDataList){
			List<TrafficData> list = department_list.get(trafficData.getDepartmentName());
			
			if(list == null)
				list = new ArrayList<TrafficData>();
			
			list.add(trafficData);
			
			department_list.put(trafficData.getDepartmentName(), list);
		}
		
		List<ReturnTrafficReport> returnList = new ArrayList<ReturnTrafficReport>();
		
		for(String departmentName : department_list.keySet()){
			List<TrafficData> list =  department_list.get(departmentName);
			ReturnTrafficReport  returnTrafficReport =  new ReturnTrafficReport();
			returnTrafficReport.setDepartmentName(departmentName);
			Integer allSum = 0 ;	//总时长
			Integer inSum = 0 ;		//ACD呼入总时长
			Integer inCount = 0;	//ACD来电量
			Integer successIn = 0;	//外呼成功
			
			Integer outSum = 0 ;	//外呼总时长
			Integer outCount = 0;	//外呼量
			Integer successOut = 0;	//外呼成功
			for(TrafficData trafficData : list){
				Integer sum = Integer.parseInt(trafficData.getSum());
				Integer count = Integer.parseInt(trafficData.getCount());
				allSum += sum;
				if(trafficData.getCallType().equals("in")){
					inSum += sum;
					inCount += count;
					if(trafficData.getDisposition().equals("ANSWERED")){
						successIn = count;			//成功接听次数
						returnTrafficReport.setCountSuccessACD(count.toString());	//接通
					}else{
						returnTrafficReport.setCountFailureACD(count.toString());	//未接
						returnTrafficReport.setMaxHoldACD(trafficData.getMaxHoldTime());//最大延迟
					}
				}else if(trafficData.getCallType().equals("out")){
					outSum += sum;
					outCount += count;
					if(trafficData.getDisposition().equals("ANSWERED")){		
						successOut = count;
					}
				}
			}
			returnTrafficReport.setCountACD(inCount.toString());
			returnTrafficReport.setSumCallInACD(inSum.toString());
			if(successIn != 0){
				Integer avgCallIn = inSum/successIn;
				returnTrafficReport.setAvgCallInACD(avgCallIn.toString());
			}else{
				returnTrafficReport.setAvgCallOut("0");
			}
			returnTrafficReport.setCountCallOut(outCount.toString());
			returnTrafficReport.setSumCallOut(outSum.toString());
			if(successOut != 0){
				Integer avgCallOut = outSum/successOut;
				returnTrafficReport.setAvgCallOut(avgCallOut.toString());
			}else{
				returnTrafficReport.setAvgCallOut("0");
			}

			returnTrafficReport.setSumAllCallTime(allSum.toString());
			returnList.add(returnTrafficReport);
		}
		//排序
		String returnJson =  JsonHelp.listToJsonStr(returnList);
		if(returnJson==null || returnJson.equals("") || returnJson.equals("null"))
			returnJson = "[]";
		
		return returnJson;
	}
	/** 技能组 坐席报表 */
	private String departmentSeatData(List<DepartmentSeatData> departmentSeatDataList){
		Map<String,List<DepartmentSeatData>> department_seat_list = new HashMap<String,List<DepartmentSeatData>>();
		
		for(DepartmentSeatData departmentSeatData : departmentSeatDataList){
			List<DepartmentSeatData> list = department_seat_list.get(departmentSeatData.getSeatName());
			
			if(list == null)
				list = new ArrayList<DepartmentSeatData>();
			
			list.add(departmentSeatData);
			
			department_seat_list.put(departmentSeatData.getSeatName(), list);
		}
		
		List<ReturnDepartmentSeatReport> returnList = new ArrayList<ReturnDepartmentSeatReport>();
		
		for(String seatName : department_seat_list.keySet()){
			List<DepartmentSeatData> list =  department_seat_list.get(seatName);
			ReturnDepartmentSeatReport  returnDepartmentSeatReport =  new ReturnDepartmentSeatReport();
			returnDepartmentSeatReport.setSeatName(seatName);	//姓名
			Integer allSum = 0 ;	//总时长
			Integer inSum = 0 ;		//ACD呼入总时长
			Integer inCount = 0;	//ACD来电量
			Integer successIn = 0;	//外呼成功
			
			Integer outSum = 0 ;	//外呼总时长
			Integer outCount = 0;	//外呼量
			Integer successOut = 0;	//外呼成功
			
			
			for(DepartmentSeatData departmentSeatData : list){
				Integer sum = Integer.parseInt(departmentSeatData.getSum());		//总数
				Integer count = Integer.parseInt(departmentSeatData.getCount());	//评价
				allSum += sum;	//总时长
				
				if(departmentSeatData.getCallType().equals("in")){
					inSum += sum;
					inCount += count;
					if(departmentSeatData.getDisposition().equals("ANSWERED")){
						successIn = count;	//成功接听次数
						returnDepartmentSeatReport.setCountSuccessACD(count.toString());	//ACD成功接听量
					}else{
						returnDepartmentSeatReport.setCountFailureACD(count.toString());	//ACD响铃放弃量
					}
				}else if(departmentSeatData.getCallType().equals("out")){
					outSum += sum;
					outCount += count;
					if(departmentSeatData.getDisposition().equals("ANSWERED")){		
						successOut = count;
					}
				}else if (departmentSeatData.getCallType().equals("internal")){
					returnDepartmentSeatReport.setCountInternal(count.toString());	//内部呼叫数量
					returnDepartmentSeatReport.setSumInternal(sum.toString());		//内部呼叫总时长
				}
			}
			returnDepartmentSeatReport.setCountACD(inCount.toString());			//ACD来电量
			returnDepartmentSeatReport.setSumCallInACD(inSum.toString());		//ACD呼入总时长
			if(successIn != 0){
				Integer avgCallIn = inSum/successIn;
				returnDepartmentSeatReport.setAvgCallInACD(avgCallIn.toString());//ACD呼入平均时长
			}else{
				returnDepartmentSeatReport.setAvgCallOut("0");
			}
			returnDepartmentSeatReport.setCountCallOut(outCount.toString());	//外拨呼叫量
			returnDepartmentSeatReport.setSumCallOut(outSum.toString());		//外拨总时长
			if(successOut != 0){
				Integer avgCallOut = outSum/successOut;
				returnDepartmentSeatReport.setAvgCallOut(avgCallOut.toString());//外呼评价时长
			}else{
				returnDepartmentSeatReport.setAvgCallOut("0");
			}
			
			returnDepartmentSeatReport.setSumAllCallTime(allSum.toString());	//总时长
			returnList.add(returnDepartmentSeatReport);
		}
		//排序
		String returnJson =  JsonHelp.listToJsonStr(returnList);
		if(returnJson==null || returnJson.equals("") || returnJson.equals("null"))
			returnJson = "[]";
		
		return returnJson;
	}
	
	
	public ReturnDataService getReturnDataService() {
		return returnDataService;
	}

	public void setReturnDataService(ReturnDataService returnDataService) {
		this.returnDataService = returnDataService;
	}

	public WorkOrderInfoService getWorkOrderInfoService() {
		return workOrderInfoService;
	}

	public void setWorkOrderInfoService(WorkOrderInfoService workOrderInfoService) {
		this.workOrderInfoService = workOrderInfoService;
	}

	public CustomerPhoneLinkService getCustomerPhoneLinkService() {
		return customerPhoneLinkService;
	}

	public void setCustomerPhoneLinkService(
			CustomerPhoneLinkService customerPhoneLinkService) {
		this.customerPhoneLinkService = customerPhoneLinkService;
	}
}
