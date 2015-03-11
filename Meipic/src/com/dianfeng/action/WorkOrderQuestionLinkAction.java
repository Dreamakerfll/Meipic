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

import com.dianfeng.entity.QuestionInfo;
import com.dianfeng.service.WorkOrderQuestionLinkService;
import com.dianfeng.utils.JsonUtil;
import com.dianfeng.utils.PageData;

@Component
@RequestMapping("/work_order_question_link")
public class WorkOrderQuestionLinkAction
{
	@Resource
	private WorkOrderQuestionLinkService workOrderQuestionService;
	
	public WorkOrderQuestionLinkService getWorkOrderQuestionService()
	{
		return workOrderQuestionService;
	}

	public void setWorkOrderQuestionService(WorkOrderQuestionLinkService workOrderQuestionService)
	{
		this.workOrderQuestionService = workOrderQuestionService;
	}

	@RequestMapping(params="method=findQuestionByWorkOrderId")
	public void getQuestionByWorkOrderId(int page,int rows,String workOrderId,HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		List<QuestionInfo> questionInfoList = workOrderQuestionService.getQuestionByWorkOrderId(workOrderId);
		
		//设置分页
		List<QuestionInfo> displyData = new ArrayList<QuestionInfo>();
		int resultMaxCount = questionInfoList.size() ;
	    int startIndex = (page-1)*rows<0?0:(page-1)*rows;
	    int endIndex = page*rows<resultMaxCount?page*rows:resultMaxCount;
	    
	    for (int i = startIndex; i < endIndex ; i++) {
	    	displyData.add(questionInfoList.get(i));
	    }
	    
		PageData<QuestionInfo> t = new PageData<QuestionInfo>();
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
