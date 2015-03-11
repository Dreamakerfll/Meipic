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

import com.dianfeng.entity.UserInfo;
import com.dianfeng.service.UserInfoService;
import com.dianfeng.utils.Constant;
import com.dianfeng.utils.JsonReturn;
import com.dianfeng.utils.JsonUtil;
import com.dianfeng.utils.PageData;

@Component
@RequestMapping("/user_info")
public class UserInfoAction
{

	@Resource
	private UserInfoService userInfoService;
	

	public UserInfoService getUserInfoService()
	{
		return userInfoService;
	}

	public void setUserInfoService(UserInfoService userInfoService)
	{
		this.userInfoService = userInfoService;
	}

	/**
	 * 获取所有用户的信息
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(params="method=findAll")
	public void getAllAccount(String type,HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		List<UserInfo> accountInfoList = userInfoService.getAllUser();
		if("0".equals(type))
		{
			UserInfo userInfo = new UserInfo();
			userInfo.setAccount("");
			userInfo.setAccountDisplay("　");
			userInfo.setAgent("");
			userInfo.setAgentDisplay("　");
			accountInfoList.add(0,userInfo);
		}
		String returnJson =JsonUtil.toJson(accountInfoList);
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
	 * 获取所有用户的信息带分页
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(params="method=findAllWithPage")
	public void getAllAccountWithPage(int page,int rows,HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		List<UserInfo> userInfoList = userInfoService.getAllUserWithPage();
		
		//设置分页
		List<UserInfo> displyData = new ArrayList<UserInfo>();
		int resultMaxCount = userInfoList.size() ;
	    int startIndex = (page-1)*rows<0?0:(page-1)*rows;
	    int endIndex = page*rows<resultMaxCount?page*rows:resultMaxCount;
	    for (int i = startIndex; i < endIndex ; i++) {
	    	displyData.add(userInfoList.get(i));
	    }
	    
		PageData<UserInfo> t = new PageData<UserInfo>();
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
	
	@RequestMapping(params="method=findUserByCondition")
	public void getUserByCondition(int page,int rows,String user_info,HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		UserInfo userInfo = JsonUtil.fromJson(user_info, UserInfo.class);
		
		List<UserInfo> userInfoList = userInfoService.getUserByCondition(userInfo);
		
		//设置分页
		List<UserInfo> displyData = new ArrayList<UserInfo>();
		int resultMaxCount = userInfoList.size() ;
	    int startIndex = (page-1)*rows<0?0:(page-1)*rows;
	    int endIndex = page*rows<resultMaxCount?page*rows:resultMaxCount;
	    for (int i = startIndex; i < endIndex ; i++) {
	    	displyData.add(userInfoList.get(i));
	    }
	    
		PageData<UserInfo> t = new PageData<UserInfo>();
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
	 * 验证账号是否合法
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(params="method=checkAccount")
	public void checkAccount(String user_account_add,HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		List<UserInfo> userInfoList = userInfoService.checkUserExist(user_account_add);
		JsonReturn jsonReturn = new JsonReturn();
		if(userInfoList.size()>0)
		{
			jsonReturn.setSign("isExist");
		}
		else
		{
			jsonReturn.setSign("unExist");
		}
		String returnJson = JsonUtil.toJson(jsonReturn);
		response.setCharacterEncoding("utf-8");
	    PrintWriter out = response.getWriter();
		out.print(returnJson);
		out.flush();
		out.close();
	}
	
	/**
	 * 新增用户
	 * @param user_info 用户信息
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(params="method=insertUserInfo")
	public void insertUserInfo(String user_info,HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		UserInfo userInfo = JsonUtil.fromJson(user_info, UserInfo.class);
		
		int i = userInfoService.insertUserInfo(userInfo);
		
		JsonReturn jsonReturn = new JsonReturn();
		if(i>0){
			jsonReturn.setStatus(Constant.STATU_SUCCESS);
		}
		else{
			jsonReturn.setStatus(Constant.STATU_ERROR);
		}
		String returnJson = JsonUtil.toJson(jsonReturn);
		response.setCharacterEncoding("utf-8");
	    PrintWriter out = response.getWriter();
		out.print(returnJson);
		out.flush();
		out.close();
	}
	
	/**
	 * 更新用户
	 * @param user_info 用户信息
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(params="method=updateUserInfo")
	public void updateUserInfo(String user_info,HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		UserInfo userInfo = JsonUtil.fromJson(user_info, UserInfo.class);
		
		int i = userInfoService.updateUserInfo(userInfo);
		
		JsonReturn jsonReturn = new JsonReturn();
		if(i>0){
			jsonReturn.setStatus(Constant.STATU_SUCCESS);
		}
		else{
			jsonReturn.setStatus(Constant.STATU_ERROR);
		}
		String returnJson = JsonUtil.toJson(jsonReturn);
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		out.print(returnJson);
		out.flush();
		out.close();
	}
	
	/**
	 * 删除用户
	 * @param user_id 用户ID
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(params="method=deleteUserInfo")
	public void deleteUserInfo(String user_id,HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		int i = userInfoService.deleteUserInfo(user_id);
		
		JsonReturn jsonReturn = new JsonReturn();
		if(i>0){
			jsonReturn.setStatus(Constant.STATU_SUCCESS);
		}
		else{
			jsonReturn.setStatus(Constant.STATU_ERROR);
		}
		String returnJson = JsonUtil.toJson(jsonReturn);
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		out.print(returnJson);
		out.flush();
		out.close();
	}
}
