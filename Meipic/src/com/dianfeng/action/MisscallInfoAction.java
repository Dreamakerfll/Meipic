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

import com.dianfeng.entity.MisscallInfo;
import com.dianfeng.service.MisscallInfoService;
import com.dianfeng.utils.Constant;
import com.dianfeng.utils.JsonHelp;
import com.dianfeng.utils.JsonReturn;
import com.dianfeng.utils.JsonUtil;
import com.dianfeng.utils.PageData;

/**
 * 未接来电管理
 * @author Dreamaker
 *
 */
@Component
@RequestMapping("/misscall")
public class MisscallInfoAction
{

	@Resource
	private MisscallInfoService misscallInfoService;

	public MisscallInfoService getMisscallInfoService()
	{
		return misscallInfoService;
	}

	public void setMisscallInfoService(MisscallInfoService misscallInfoService)
	{
		this.misscallInfoService = misscallInfoService;
	}

	@RequestMapping(params="method=findAll")
	public void getAllMisscall(int page,int rows,HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		List<MisscallInfo> misscallList = misscallInfoService.getAllMisscall();
		
		//设置分页
		List<MisscallInfo> displyData = new ArrayList<MisscallInfo>();
		int resultMaxCount = misscallList.size() ;
	    int startIndex = (page-1)*rows<0?0:(page-1)*rows;
	    int endIndex = page*rows<resultMaxCount?page*rows:resultMaxCount;
	    for (int i = startIndex; i < endIndex ; i++) {
	    	displyData.add(misscallList.get(i));
	    }
	    
		PageData<MisscallInfo> t = new PageData<MisscallInfo>();
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
	
	@RequestMapping(params="method=findMisscallByCondition")
	public void getMisscallByCondition(int page,int rows,String beginTime,String endTime,String phoneNumber,String phoneNumber_called,HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		List<MisscallInfo> misscallList = misscallInfoService.getMisscallByCondition(beginTime, endTime, phoneNumber,phoneNumber_called);
		
		//设置分页
		List<MisscallInfo> displyData = new ArrayList<MisscallInfo>();
		int resultMaxCount = misscallList.size() ;
		int startIndex = (page-1)*rows<0?0:(page-1)*rows;
		int endIndex = page*rows<resultMaxCount?page*rows:resultMaxCount;
		for (int i = startIndex; i < endIndex ; i++) {
			displyData.add(misscallList.get(i));
		}
		
		PageData<MisscallInfo> t = new PageData<MisscallInfo>();
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
	
	@RequestMapping(params="method=updateAccountById")
	public void updateAccountById(String id,String account,HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		int i = 0;
		
		if("0".equals(account))
		{
			i = misscallInfoService.updateMisscallStatus(id);
		}
		else
		{
			i = misscallInfoService.updateAccountById(id, account);
		}
		
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
