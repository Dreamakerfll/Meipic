package com.dianfeng.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dianfeng.entity.PhoneModel;
import com.dianfeng.service.PhoneModelService;
import com.dianfeng.utils.JsonUtil;

@Component
@RequestMapping("/phone_model")
public class PhoneModelAction
{
	@Resource
	private PhoneModelService phoneTypeService;
	
	public PhoneModelService getPhoneTypeService()
	{
		return phoneTypeService;
	}

	public void setPhoneTypeService(PhoneModelService phoneTypeService)
	{
		this.phoneTypeService = phoneTypeService;
	}

	@RequestMapping(params="method=findAll")
	public void getAllPhoneType(String type,HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		List<PhoneModel> phoneModelList = phoneTypeService.getAllPhoneModel();
		if("0".equals(type))
		{
			PhoneModel phoneModel = new PhoneModel();
			phoneModel.setPhoneModel("");
			phoneModel.setPhoneModelDisplay("　");
			phoneModelList.add(0,phoneModel);
		}
		String returnJson =JsonUtil.toJson(phoneModelList);
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
