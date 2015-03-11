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

import com.dianfeng.entity.WorkOrderInfo;
import com.dianfeng.service.CustomerWorkOrderLinkService;
import com.dianfeng.utils.JsonUtil;
import com.dianfeng.utils.PageData;

@Component
@RequestMapping("/customer_work_order_link")
public class CustomerWorkOrderLinkAction
{
	@Resource
	private CustomerWorkOrderLinkService customerWorkOrderLinkService;
	
	public CustomerWorkOrderLinkService getCustomerWorkOrderLinkService()
	{
		return customerWorkOrderLinkService;
	}

	public void setCustomerWorkOrderLinkService(CustomerWorkOrderLinkService customerWorkOrderLinkService)
	{
		this.customerWorkOrderLinkService = customerWorkOrderLinkService;
	}

	@RequestMapping(params="method=findWorkOrderByCustomerId")
	public void getWorkOrderByCustomerId(int page,int rows,String customerId,HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		//通过客户ID来查找客户的工单信息
		List<WorkOrderInfo> workOrderInfoList = customerWorkOrderLinkService.getWorkOrderByCustomerId(customerId);
		
		//设置分页
		List<WorkOrderInfo> displyData = new ArrayList<WorkOrderInfo>();
		int resultMaxCount = workOrderInfoList.size() ;
	    int startIndex = (page-1)*rows<0?0:(page-1)*rows;
	    int endIndex = page*rows<resultMaxCount?page*rows:resultMaxCount;
	    for (int i = startIndex; i < endIndex ; i++) {
	    	displyData.add(workOrderInfoList.get(i));
	    }
	    
		PageData<WorkOrderInfo> t = new PageData<WorkOrderInfo>();
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
