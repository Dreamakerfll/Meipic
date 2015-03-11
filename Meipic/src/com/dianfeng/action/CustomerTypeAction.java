package com.dianfeng.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dianfeng.entity.CustomerType;
import com.dianfeng.service.CustomerTypeService;
import com.dianfeng.utils.JsonUtil;

@Component
@RequestMapping("/customer_type")
public class CustomerTypeAction
{

	@Resource
	private CustomerTypeService customerTypeService;

	public CustomerTypeService getCustomerTypeService()
	{
		return customerTypeService;
	}

	public void setCustomerTypeService(CustomerTypeService customerTypeService)
	{
		this.customerTypeService = customerTypeService;
	}
	
	@RequestMapping(params="method=findAll")
		public void getAllCustomerType(String type,HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		List<CustomerType> customerTypeList = customerTypeService.getAllCustomerType();
		if("0".equals(type))
		{
			CustomerType customerType = new CustomerType();
			customerType.setCustomerType("");
			customerType.setCustomerTypeDisplay("　");
			customerTypeList.add(0,customerType);
		}
		String returnJson =JsonUtil.toJson(customerTypeList);
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
