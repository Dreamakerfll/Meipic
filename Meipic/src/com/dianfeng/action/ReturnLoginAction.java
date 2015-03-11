package com.dianfeng.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dianfeng.entity.ActionRecordInfo;
import com.dianfeng.entity.Announcement;
import com.dianfeng.entity.LoginRecordInfo;
import com.dianfeng.entity.Notice;
import com.dianfeng.entity.ReturnCallLoss;
import com.dianfeng.entity.ReturnOnLineSeat;
import com.dianfeng.entity.ReturnSeat;
import com.dianfeng.entity.SimpleSeat;
import com.dianfeng.service.AnnouncementService;
import com.dianfeng.service.ReturnLoginService;
import com.dianfeng.utils.Constant;
import com.dianfeng.utils.JsonHelp;
import com.dianfeng.utils.ResponseFormat;

@Component
@RequestMapping("/login")
public class ReturnLoginAction {
	@Resource
	private ReturnLoginService service;
	@Resource
	private AnnouncementService anservice;
	public AnnouncementService getAnservice() {
		return anservice;
	}

	public void setAnservice(AnnouncementService anservice) {
		this.anservice = anservice;
	}

	public ReturnLoginService getService() {
		return service;
	}

	public void setService(ReturnLoginService service) {
		this.service = service;
	}
	
	/**
	 * 登陆验证
	 * @throws IOException 
	 */
	@RequestMapping(params="method=selectNameOrPwd")
	public void selectNameOrPwd(String loginName,String passWord,HttpServletResponse response) throws IOException{
		int i = service.selectNameOrPwd(loginName, passWord);
		String returnStr  = "";
		if(i>=1){
			returnStr = "true";
		}else{
			returnStr = "false";
		}
		response.setCharacterEncoding("UTF-8");
	    PrintWriter out = response.getWriter();
		out.print(returnStr);
		out.flush();
		out.close();
	}
	@RequestMapping(params="method=getSeatByLoginName")
	public void getSeatByLoginName(String loginName,HttpServletResponse response) throws IOException{
		ReturnSeat seat = service.getSeatByLoginName(loginName);
		String returnJson =JsonHelp.objectToJsonStr(seat);
		
		if(returnJson==null){
			returnJson="{}";
		}
		response.setCharacterEncoding("UTF-8");
	    PrintWriter out = response.getWriter();
		out.print(returnJson);
		out.flush();
		out.close();
	}
	
	@RequestMapping(params="method=regionalSeat")
	public void regionalSeat(String regional , HttpServletRequest request,HttpServletResponse response)throws IOException{
		List<SimpleSeat> seats = service.findAgentNumberByQueue(regional);
		
		String returnJson = JsonHelp.listToJsonStr(seats);
		
		response.setCharacterEncoding("UTF-8");
	    PrintWriter out = response.getWriter();
		out.print(returnJson);
		out.flush();
		out.close();
	}
	
	@RequestMapping(params="method=seatLogin")
	public void seatLogin(String agentNumber,String account,HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		//LoginData data = new LoginData();
		LoginRecordInfo data = new LoginRecordInfo();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = sdf.format(System.currentTimeMillis());
		
		data.setAgent(agentNumber);
		data.setAccount(account);
		data.setLoginTime(time);
		
		int result = service.seatLogin(data);
		
		ResponseFormat rf = new ResponseFormat();
		if(result == 1){
			//修改队列原始数据
			ReturnSeat seat = service.getSeatByLoginName(account);
			int i = service.updateOldQueueAction(agentNumber, seat.getQueue());
			//新增原始队列数据
			if(i == 0){
				service.insertOldQueueAction(agentNumber, seat.getQueue());
			}
			rf.setStatu("100");
			rf.setRecordset("登录成功!");
		}
		String returnJson =JsonHelp.objectToJsonStr(rf);
		response.setCharacterEncoding("UTF-8");
	    PrintWriter out = response.getWriter();
		out.print(returnJson);
		out.flush();
		out.close();
	}
	
	@RequestMapping(params="method=insertAction")
	public void insertAction(String agentNumber,String account,String status,HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		ActionRecordInfo recordInfo = new ActionRecordInfo();
	
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = sdf.format(System.currentTimeMillis());
		
		recordInfo.setBeginTime(time);					//开始时间
		recordInfo.setAccount(account);					//帐号
		recordInfo.setAgent(agentNumber);			//分机号
		recordInfo.setStatus(status);	//操作类型

		int result = service.insertAction(recordInfo);
		
		ResponseFormat rf = new ResponseFormat();
		
		if(result == 1){
			rf.setStatu("100");
		}

		String returnJson =JsonHelp.objectToJsonStr(rf);
		
		response.setCharacterEncoding("UTF-8");
	    PrintWriter out = response.getWriter();
		out.print(returnJson);
		out.flush();
		out.close();
	}
	
	@RequestMapping(params="method=seatLogout")
	public void seatLogout(String agentNumber,String account,HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		String time = sdf.format(System.currentTimeMillis());
		
		int result = service.seatLogout(time, agentNumber, account);
		ResponseFormat rf = new ResponseFormat();
		if(result == 1){
			rf.setStatu("100");
			rf.setRecordset("退出成功!");
		}
		String returnJson =JsonHelp.objectToJsonStr(rf);
		
		response.setCharacterEncoding("UTF-8");
	    PrintWriter out = response.getWriter();
		out.print(returnJson);
		out.flush();
		out.close();
	}
	
	@RequestMapping(params="method=updateAction")
	public void updateAction(String agentNumber,String account,String status,HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		String time = sdf.format(System.currentTimeMillis());
		
		int result = service.updateAction(time, agentNumber, account, status);
		
		ResponseFormat rf = new ResponseFormat();
		
		if(result == 1){
			rf.setStatu("100");
		}

		String returnJson =JsonHelp.objectToJsonStr(rf);
		
		response.setCharacterEncoding("UTF-8");
	    PrintWriter out = response.getWriter();
		out.print(returnJson);
		out.flush();
		out.close();
	}
	
	@RequestMapping(params="method=selectPwd")
	public void selectPwd(String loginName,String oldPwd,HttpServletResponse response) throws IOException{
		int i = service.selectPwd(loginName,oldPwd);
		String returnStr  = "";
		if(i>=1){
			returnStr = "true";
		}else{
			returnStr = "false";
		}
		response.setCharacterEncoding("UTF-8");
	    PrintWriter out = response.getWriter();
		out.print(returnStr);
		out.flush();
		out.close();
	}
	
	@RequestMapping(params="method=updateNewPwd")
	public void updateNewPwd(String loginName,String newPwd,HttpServletResponse response) throws IOException{
		int i = service.updateNewPwd(loginName,newPwd);
		String returnStr  = "";
		if(i>=1){
			returnStr = "true";
		}else{
			returnStr = "false";
		}
		response.setCharacterEncoding("UTF-8");
	    PrintWriter out = response.getWriter();
		out.print(returnStr);
		out.flush();
		out.close();
	}
	
	@RequestMapping(params="method=findCallLossByAgentNumber")
	public void findCallLossByAgentNumber(String agentNumber,HttpServletRequest request,HttpServletResponse response)throws IOException{
		List<ReturnCallLoss> callLossList = service.findCallLossByAgentNumber(agentNumber);
		
		String returnJson = JsonHelp.listToJsonStr(callLossList);
		
		response.setCharacterEncoding("UTF-8");
	    PrintWriter out = response.getWriter();
		out.print(returnJson);
		out.flush();
		out.close();
	}
	
	@RequestMapping(params="method=deleteCallLossById")
	public void deleteCallLossById(String callLossId,HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		int result = service.deleteCallLossById(callLossId);
		ResponseFormat rf = new ResponseFormat();
		
		if(result == 1){
			rf.setStatu("100");
		}

		String returnJson =JsonHelp.objectToJsonStr(rf);
		
		response.setCharacterEncoding("UTF-8");
	    PrintWriter out = response.getWriter();
		out.print(returnJson);
		out.flush();
		out.close();
	}
	
	@RequestMapping(params="method=selectCallOutByAgentNumber")
	public void selectCallOutByAgentNumber(String agentNumber,HttpServletResponse response) throws IOException{
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String time = sdf.format(date);
		int count = service.selectCallOutByAgentNumber(agentNumber,time);
		String returnJson =JsonHelp.objectToJsonStr(count);
		response.setCharacterEncoding("UTF-8");
	    PrintWriter out = response.getWriter();
		out.print(returnJson);
		out.flush();
		out.close();
	}
	
	@RequestMapping(params="method=selectCallTimeByAgentNumber")
	public void selectCallTimeByAgentNumber(String agentNumber,HttpServletResponse response) throws IOException{
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String time = sdf.format(date);
		Integer callTime = service.selectCallTimeByAgentNumber(agentNumber,time);
		String returnJson =JsonHelp.objectToJsonStr(callTime);
		if(returnJson==null||returnJson.equals("")){
			returnJson="[]";
		}
		response.setCharacterEncoding("UTF-8");
	    PrintWriter out = response.getWriter();
		out.print(returnJson);
		out.flush();
		out.close();
	}
	@RequestMapping(params="method=selectAllNotice")
	public void selectAllNotice(HttpServletResponse response) throws IOException{
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = sdf.format(date);
		
		List<Announcement> anlist = service.selectAllAnnouncement(time);
		String returnJson =JsonHelp.listCycleToJsonString(anlist);
		if(returnJson==null||returnJson.equals("")){
			returnJson="[]";
		}
		response.setCharacterEncoding("UTF-8");
	    PrintWriter out = response.getWriter();
		out.print(returnJson);
		out.flush();
		out.close();
	}
	
	@RequestMapping(params="method=addNotice")
	public void addNotice(String noticeId,String title,String time,String agentNumber,HttpServletResponse response) throws IOException{
		String status = "0";
		service.addNotice(noticeId,title,time,agentNumber,status);
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		out.flush();
		out.close();
	}
	
	@RequestMapping(params="method=selectAllNoticeByAgent")
	public void selectAllNoticeByAgent(String agentNumber,HttpServletResponse response) throws IOException{
		
		List<Notice> list = service.selectAllNoticeByAgent(agentNumber);
		String returnJson =JsonHelp.listCycleToJsonString(list);
		if(returnJson==null||returnJson.equals("")){
			returnJson="[]";
		}
		response.setCharacterEncoding("UTF-8");
	    PrintWriter out = response.getWriter();
		out.print(returnJson);
		out.flush();
		out.close();
	}
	@RequestMapping(params="method=selectAllNoticeByAgentByStatus")
	public void selectAllNoticeByAgentByStatus(String agentNumber,HttpServletResponse response) throws IOException{
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = sdf.format(date);
		
		List<Notice> list = service.selectAllNoticeByAgentByStatus(agentNumber,time);
		
		String returnJson =JsonHelp.listCycleToJsonString(list);
		if(returnJson==null||returnJson.equals("")){
			returnJson="[]";
		}
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		out.print(returnJson);
		out.flush();
		out.close();
	}
	
	@RequestMapping(params="method=findAllOnLineSeat")
	public void findAllOnLineSeat(HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		List<ReturnOnLineSeat> allOnLineSeatList = service.findAllOnLineSeat();
		
		String returnJson = JsonHelp.listToJsonStr(allOnLineSeatList);
		
		if(returnJson==null || returnJson.equals("null")){
			returnJson="[]";
		}
		response.setCharacterEncoding("UTF-8");
	    PrintWriter out = response.getWriter();
		out.print(returnJson);
		out.flush();
		out.close();
	}
	
	@RequestMapping(params="method=updateNotice")
	public void updateNotice(String id,HttpServletResponse response) throws IOException{
		int i = service.updateNotice(id);
		ResponseFormat rf = new ResponseFormat();
		
		if(i == 1){
			rf.setStatu("100");
		}
		String returnJson =JsonHelp.objectToJsonStr(rf);
		response.setCharacterEncoding("UTF-8");
	    PrintWriter out = response.getWriter();
		out.print(returnJson);
		out.flush();
		out.close();
	}
	@RequestMapping(params="method=selectAnnouncementById")
	public String selectAnnouncementById(String id,HttpServletRequest request){
		Announcement announcement = service.selectAnnouncementById(id);
		request.setAttribute("announcement", announcement);
		return "announcementEdit";
	}
	
	@RequestMapping(params="method=selectCallTimeByUniqueId")
	public void selectCallTimeByUniqueId(String uniqueId,HttpServletResponse response) throws IOException{
		Integer callTime = service.selectCallTimeByUniqueId(uniqueId);
		String returnJson =JsonHelp.objectToJsonStr(callTime);
		if(returnJson==null||returnJson.equals("")){
			returnJson="[]";
		}
		response.setCharacterEncoding("UTF-8");
	    PrintWriter out = response.getWriter();
		out.print(returnJson);
		out.flush();
		out.close();
	}
	
	@RequestMapping(params="method=addUniqueId")
	public void addUniqueId(String uniqueId,String count,HttpServletResponse response) throws IOException{
		int i = service.addUniqueId(uniqueId,count);
		ResponseFormat rf=new ResponseFormat();
		if(i==1){
			rf.setStatu(Constant.STATU_SUCCESS);
		}else{
			
			rf.setStatu(Constant.STATU_ERROR);
		}
		String returnJson =JsonHelp.objectToJsonStr(rf);
		response.setCharacterEncoding("utf-8");
	    PrintWriter out = response.getWriter();
		out.print(returnJson);
		out.flush();
		out.close();
	}
}
