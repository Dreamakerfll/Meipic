package com.dianfeng.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dianfeng.entity.PhoneVersion;
import com.dianfeng.service.PhoneVersionService;
import com.dianfeng.utils.JsonUtil;

@Component
@RequestMapping("/phone_version")
public class PhoneVersionAction
{
	@Resource
	private PhoneVersionService phoneVersionService;

	public PhoneVersionService getPhoneVersionService()
	{
		return phoneVersionService;
	}

	public void setPhoneVersionService(PhoneVersionService phoneVersionService)
	{
		this.phoneVersionService = phoneVersionService;
	}

	@RequestMapping(params="method=findAll")
	public void getAllPhoneType(String type,HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		List<PhoneVersion> phoneVersionList = phoneVersionService.getAllPhoneVersion();
		if("0".equals(type))
		{
			PhoneVersion phoneVersion = new PhoneVersion();
			phoneVersion.setPhoneVersion("");
			phoneVersion.setPhoneVersionDisplay("　");
			phoneVersionList.add(0,phoneVersion);
		}
		String returnJson =JsonUtil.toJson(phoneVersionList);
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
