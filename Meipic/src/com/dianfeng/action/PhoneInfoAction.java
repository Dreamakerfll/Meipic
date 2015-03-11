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

import com.dianfeng.entity.PhoneInfo;
import com.dianfeng.service.CustomerPhoneLinkService;
import com.dianfeng.service.PhoneInfoService;
import com.dianfeng.utils.JsonUtil;
import com.dianfeng.utils.PageData;

@Component
@RequestMapping("/phone_info")
public class PhoneInfoAction
{

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
	 * 通过客户ID查询手机信息
	 * @param page
	 * @param rows
	 * @param customerId
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(params="method=findPhoneInfoByCustomerId")
	public void getPhoneInfoByPhoneId(int page,int rows,String customerId,HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		//通过客户ID来查找客户的手机信息
		List<PhoneInfo> phoneInfoList = phoneInfoService.getPhoneInfoByCustomerId(customerId);
		
		//设置分页
		List<PhoneInfo> displyData = new ArrayList<PhoneInfo>();
		int resultMaxCount = phoneInfoList.size() ;
	    int startIndex = (page-1)*rows<0?0:(page-1)*rows;
	    int endIndex = page*rows<resultMaxCount?page*rows:resultMaxCount;
	    for (int i = startIndex; i < endIndex ; i++) {
	    	displyData.add(phoneInfoList.get(i));
	    }
	    
		PageData<PhoneInfo> t = new PageData<PhoneInfo>();
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
	
	/**
	 * 通过客户ID查询手机信息
	 * @param page
	 * @param rows
	 * @param customerId
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(params="method=findPhoneInfoByCustomerIdNoPage")
	public void getPhoneInfoByPhoneIdNoPage(String customerId,HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		//通过客户ID来查找客户的手机信息
		List<PhoneInfo> phoneInfoList = phoneInfoService.getPhoneInfoByCustomerId(customerId);
		
		PhoneInfo phoneInfo = new PhoneInfo();
		phoneInfo.setImei("售前咨询");
		phoneInfo.setModel("售前咨询");
		phoneInfo.setVersion("售前咨询");
		phoneInfoList.add(phoneInfo);
		
		String returnJson =JsonUtil.toJson(phoneInfoList);
		if(returnJson==null){
			returnJson=JsonUtil.toJson(phoneInfo);
		}
		response.setCharacterEncoding("utf-8");
	    PrintWriter out = response.getWriter();
		out.print(returnJson);
		out.flush();
		out.close();
	}
}
