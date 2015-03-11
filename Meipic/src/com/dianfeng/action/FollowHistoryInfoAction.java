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

import com.dianfeng.entity.FollowHistoryInfo;
import com.dianfeng.service.FollowHistoryInfoService;
import com.dianfeng.utils.JsonUtil;
import com.dianfeng.utils.PageData;

@Component
@RequestMapping("/follow_history_info")
public class FollowHistoryInfoAction
{

	@Resource
	private FollowHistoryInfoService followHistoryInfoService;
	
	public FollowHistoryInfoService getFollowHistoryInfoService()
	{
		return followHistoryInfoService;
	}

	public void setFollowHistoryInfoService(FollowHistoryInfoService followHistoryInfoService)
	{
		this.followHistoryInfoService = followHistoryInfoService;
	}

	@RequestMapping(params="method=findFollowHistory")
	public void getFollowHistory(int page,int rows,String workOrderId,HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		List<FollowHistoryInfo> followHistoryInfoList = followHistoryInfoService.getFollowHistoryInfo(workOrderId);
		
		//设置分页
		List<FollowHistoryInfo> displyData = new ArrayList<FollowHistoryInfo>();
		int resultMaxCount = followHistoryInfoList.size() ;
	    int startIndex = (page-1)*rows<0?0:(page-1)*rows;
	    int endIndex = page*rows<resultMaxCount?page*rows:resultMaxCount;
	    for (int i = startIndex; i < endIndex ; i++) {
	    	displyData.add(followHistoryInfoList.get(i));
	    }
	    
		PageData<FollowHistoryInfo> t = new PageData<FollowHistoryInfo>();
		t.setRows(displyData);
		t.setTotal(resultMaxCount);
		
		String returnJson =JsonUtil.toJson(t);
		if(returnJson==null){
			returnJson="[]";
		}
		
		response.setCharacterEncoding("utf-8");
	    PrintWriter out = response.getWriter();
		out.print(returnJson);
		out.flush();
		out.close();
	}
}
