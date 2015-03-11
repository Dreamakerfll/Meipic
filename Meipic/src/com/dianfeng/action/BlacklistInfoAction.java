package com.dianfeng.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dianfeng.entity.BlacklistInfo;
import com.dianfeng.service.BlacklistInfoService;
import com.dianfeng.utils.Constant;
import com.dianfeng.utils.JsonHelp;
import com.dianfeng.utils.JsonReturn;
import com.dianfeng.utils.JsonUtil;
import com.dianfeng.utils.PageData;

/**
 * 黑名单管理
 * @author Dreamaker
 *
 */
@Component
@RequestMapping("/blacklist")
public class BlacklistInfoAction
{
	@Resource
	private BlacklistInfoService blacklistInfoService;
	
	public BlacklistInfoService getBlacklistInfoService()
	{
		return blacklistInfoService;
	}

	public void setBlacklistInfoService(BlacklistInfoService blacklistInfoService)
	{
		this.blacklistInfoService = blacklistInfoService;
	}
	
	/**
	 * 跳转到黑名单页面
	 * @param request
	 * @param response
	 * @return 黑名单页面
	 * @throws IOException
	 */
	@RequestMapping(params="method=pageToTra")
	public String pageToTra(HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		return "blacklist";
	}

	@RequestMapping(params="method=findAll")
	public void getAllBlacklist(int page,int rows,HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		List<BlacklistInfo> blacklistList = blacklistInfoService.getAllBlacklist();
		
		//设置分页
		List<BlacklistInfo> displyData = new ArrayList<BlacklistInfo>();
		
		int resultMaxCount = blacklistList.size() ;
	    int startIndex = (page-1)*rows<0?0:(page-1)*rows;
	    int endIndex = page*rows<resultMaxCount?page*rows:resultMaxCount;
	    for (int i = startIndex; i < endIndex ; i++) {
	    	displyData.add(blacklistList.get(i));
	    }
	    
		PageData<BlacklistInfo> t = new PageData<BlacklistInfo>();
		t.setRows(displyData);
		t.setTotal(resultMaxCount);
		
		String returnJson =JsonHelp.objectToJsonStr(t);
		if(returnJson==null){
			returnJson="[]";
		}
		response.setCharacterEncoding("utf-8");
	    PrintWriter out = response.getWriter();
		out.print(returnJson);
		out.flush();
		out.close();
	}
	
	
	@RequestMapping(params="method=findBlacklistByCondition")
	public void getBlacklistByCondition(int page,int rows,String beginTime,String endTime,String phoneNumber,String submitPerson,String agentComment,String status,HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		List<BlacklistInfo> BlacklistList = blacklistInfoService.getBlacklistByCondition(beginTime,endTime,phoneNumber,submitPerson,agentComment,status);
		
		//设置分页
		List<BlacklistInfo> displyData = new ArrayList<BlacklistInfo>();
		int resultMaxCount = BlacklistList.size() ;
	    int startIndex = (page-1)*rows<0?0:(page-1)*rows;
	    int endIndex = page*rows<resultMaxCount?page*rows:resultMaxCount;
	    for (int i = startIndex; i < endIndex ; i++) {
	    	displyData.add(BlacklistList.get(i));
	    }
	    
		PageData<BlacklistInfo> t = new PageData<BlacklistInfo>();
		t.setRows(displyData);
		t.setTotal(resultMaxCount);
		
		String returnJson =JsonHelp.objectToJsonStr(t);
		if(returnJson==null){
			returnJson="[]";
		}
		response.setCharacterEncoding("utf-8");
	    PrintWriter out = response.getWriter();
		out.print(returnJson);
		out.flush();
		out.close();
	}
	@RequestMapping(params="method=deleteBlacklistById")
	public void deleteBlacklistById(String id,HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		int i = blacklistInfoService.deleteBlacklistById(id);
		JsonReturn jsonReturn = new JsonReturn();
		if(i==1){
			jsonReturn.setStatus(Constant.STATU_SUCCESS);
		}else{
			
			jsonReturn.setStatus(Constant.STATU_ERROR);
		}
		String returnJson =JsonUtil.toJson(jsonReturn);
		response.setCharacterEncoding("utf-8");
	    PrintWriter out = response.getWriter();
		out.print(returnJson);
		out.flush();
		out.close();
	}
	
	@RequestMapping(params="method=addBlacklist")
	public void addBlacklist(String beginTime,String endTime,String phoneNumber,String submitPerson,String agentComment,String submitTime,HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		int i = blacklistInfoService.addBlacklist(beginTime, endTime, phoneNumber, submitPerson, agentComment, submitTime);
		JsonReturn jsonReturn = new JsonReturn();
		if(i==1){
			jsonReturn.setStatus(Constant.STATU_SUCCESS);
		}else{
			
			jsonReturn.setStatus(Constant.STATU_ERROR);
		}
		String returnJson =JsonUtil.toJson(jsonReturn);
		response.setCharacterEncoding("utf-8");
	    PrintWriter out = response.getWriter();
		out.print(returnJson);
		out.flush();
		out.close();
	}
	
	@RequestMapping(params="method=editBlacklist")
	public void editBlacklist(String beginTime,String endTime,String phoneNumber,String agentComment,String id,HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		int i = blacklistInfoService.editBlacklist(beginTime, endTime, phoneNumber, agentComment, id);
		JsonReturn jsonReturn = new JsonReturn();
		if(i==1){
			jsonReturn.setStatus(Constant.STATU_SUCCESS);
		}else{
			
			jsonReturn.setStatus(Constant.STATU_ERROR);
		}
		String returnJson =JsonUtil.toJson(jsonReturn);
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		out.print(returnJson);
		out.flush();
		out.close();
	}
	
	@RequestMapping(params="method=checkBlacklistById")
	public void updateBlacklistById(String id,String account,String status,String monitorComment,HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		int i = blacklistInfoService.updateBlacklist(id, account, monitorComment, status);
		JsonReturn jsonReturn = new JsonReturn();
		if(i==1){
			jsonReturn.setStatus(Constant.STATU_SUCCESS);
		}else{
			
			jsonReturn.setStatus(Constant.STATU_ERROR);
		}
		String returnJson =JsonUtil.toJson(jsonReturn);
		response.setCharacterEncoding("utf-8");
	    PrintWriter out = response.getWriter();
		out.print(returnJson);
		out.flush();
		out.close();
	}
}
