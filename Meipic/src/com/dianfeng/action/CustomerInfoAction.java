package com.dianfeng.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dianfeng.entity.WorkOrderInfo;
import com.dianfeng.service.CustomerInfoService;
import com.dianfeng.service.CustomerPhoneLinkService;
import com.dianfeng.service.CustomerWorkOrderLinkService;
import com.dianfeng.service.PhoneInfoService;
import com.dianfeng.service.QuestionInfoService;
import com.dianfeng.service.WorkOrderInfoService;
import com.dianfeng.service.WorkOrderQuestionLinkService;
import com.dianfeng.utils.Constant;
import com.dianfeng.utils.JsonReturn;
import com.dianfeng.utils.JsonUtil;

@Component
@RequestMapping("/customer_info")
public class CustomerInfoAction
{
	@Resource
	private CustomerInfoService customerInfoService;

	public CustomerInfoService getCustomerInfoService()
	{
		return customerInfoService;
	}

	public void setCustomerInfoService(CustomerInfoService customerInfoService)
	{
		this.customerInfoService = customerInfoService;
	}
	
	@Resource
	private WorkOrderInfoService workOrderInfoService;
	
	public WorkOrderInfoService getWorkOrderInfoService()
	{
		return workOrderInfoService;
	}

	public void setWorkOrderInfoService(WorkOrderInfoService workOrderInfoService)
	{
		this.workOrderInfoService = workOrderInfoService;
	}
	
	@Resource
	private QuestionInfoService questionInfoService;

	public QuestionInfoService getQuestionInfoService()
	{
		return questionInfoService;
	}

	public void setQuestionInfoService(QuestionInfoService questionInfoService)
	{
		this.questionInfoService = questionInfoService;
	}
	
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
	
	@Resource
	private WorkOrderQuestionLinkService workOrderQuestionLinkService;

	public WorkOrderQuestionLinkService getWorkOrderQuestionLinkService()
	{
		return workOrderQuestionLinkService;
	}

	public void setWorkOrderQuestionLinkService(WorkOrderQuestionLinkService workOrderQuestionLinkService)
	{
		this.workOrderQuestionLinkService = workOrderQuestionLinkService;
	}
	
	@Resource
	private CustomerPhoneLinkService customerPhoneLinkService;

	public CustomerPhoneLinkService getCustomerPhoneLinkService()
	{
		return customerPhoneLinkService;
	}

	public void setCustomerPhoneLinkService(CustomerPhoneLinkService customerPhoneLinkService)
	{
		this.customerPhoneLinkService = customerPhoneLinkService;
	}
	
	@Resource
	private PhoneInfoService phoneInfoService;

	public PhoneInfoService getPhoneInfoService()
	{
		return phoneInfoService;
	}

	public void setPhoneInfoService(PhoneInfoService phoneInfoService)
	{
		this.phoneInfoService = phoneInfoService;
	}

	@RequestMapping(params="method=pageToTra")
	public String pageToTra(HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		return "customer";
	}
	
	@RequestMapping(params="method=deleteCustomer")
	public void deleteCustomer(String customerId,HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		//删除客户信息
		int ii = customerInfoService.deleteCustomerInfo(customerId);
		
		//删除手机信息
		int jj = phoneInfoService.deletePhoneInfoByCustomerId(customerId);
		
		//删除客户手机关联信息
		int xx = customerPhoneLinkService.deleteCustomerPhoneLinkByCustomerId(customerId);
		
		//查找客户关联的工单
		List<WorkOrderInfo> customerWorkOrderLinkList = customerWorkOrderLinkService.getWorkOrderByCustomerId(customerId);
		
		for(int zz = 0;zz<customerWorkOrderLinkList.size();zz++)
		{
			//删除工单信息
			int j = workOrderInfoService.deleteWorkOrderInfo(customerWorkOrderLinkList.get(zz).getId());
			
			//删除问题信息
			int x = questionInfoService.deleteQuestionInfoByWorkOrderId(customerWorkOrderLinkList.get(zz).getId());
			
			//删除客户工单关联信息
			int i = customerWorkOrderLinkService.deleteCustomerWorkOrderLinkByWorkOrderId(customerWorkOrderLinkList.get(zz).getId());
			
			//删除工单问题关联信息
			int k = workOrderQuestionLinkService.deleteWorkOrderQuestionLinkByWorkOrderId(customerWorkOrderLinkList.get(zz).getId());
		}
		JsonReturn jsonReturn=new JsonReturn();
		if(ii>0){
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
