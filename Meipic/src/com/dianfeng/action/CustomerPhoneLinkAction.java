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

import com.dianfeng.entity.CustomerPhoneLinkDetailInfo;
import com.dianfeng.service.CustomerPhoneLinkService;
import com.dianfeng.utils.JsonUtil;
import com.dianfeng.utils.PageData;

@Component
@RequestMapping("/customer_phone_link")
public class CustomerPhoneLinkAction
{
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

	/**
	 * 根据条件查找客户和手机信息
	 * @param page 当前页
	 * @param rows 每页显示行数
	 * @param customer_phone_info_json json格式的客户和手机详细信息实体
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(params="method=findCustomerPhoneInfoByCondition")
	public void getCustomerPhoneInfoByCondition(int page,int rows,String customer_phone_info_json,HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		CustomerPhoneLinkDetailInfo customerPhoneLinkDetailInfo = new CustomerPhoneLinkDetailInfo();
		if(customer_phone_info_json != null)
		{
			customerPhoneLinkDetailInfo = JsonUtil.fromJson(customer_phone_info_json, CustomerPhoneLinkDetailInfo.class);
		}
		
		customerPhoneLinkDetailInfo.setPage((page-1)*rows);
		customerPhoneLinkDetailInfo.setRows(rows);
		
		List<CustomerPhoneLinkDetailInfo> customerPhoneLinkDetailInfoList = customerPhoneLinkService.getCustomerPhoneLinkDetailInfoByCondition(customerPhoneLinkDetailInfo);
		
		int count = customerPhoneLinkService.getCustomerPhoneLinkDetailInfoByConditionCount(customerPhoneLinkDetailInfo);
		
		//设置分页
		List<CustomerPhoneLinkDetailInfo> displyData = new ArrayList<CustomerPhoneLinkDetailInfo>();
		int resultMaxCount =count;
	    int startIndex = 0;
	    int endIndex = customerPhoneLinkDetailInfoList.size();
	    for (int i = startIndex; i < endIndex ; i++) {
	    	displyData.add(customerPhoneLinkDetailInfoList.get(i));
	    }
	    
		PageData<CustomerPhoneLinkDetailInfo> t = new PageData<CustomerPhoneLinkDetailInfo>();
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
