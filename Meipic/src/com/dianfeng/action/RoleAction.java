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

import com.dianfeng.entity.RoleInfo;
import com.dianfeng.entity.UserInfo;
import com.dianfeng.service.RoleInfoService;
import com.dianfeng.service.UserInfoService;
import com.dianfeng.utils.Constant;
import com.dianfeng.utils.JsonReturn;
import com.dianfeng.utils.JsonUtil;
import com.dianfeng.utils.PageData;

@Component
@RequestMapping("/role_info")
public class RoleAction
{
	@Resource
	private RoleInfoService roleInfoService;

	public RoleInfoService getRoleInfoService()
	{
		return roleInfoService;
	}

	public void setRoleInfoService(RoleInfoService roleInfoService)
	{
		this.roleInfoService = roleInfoService;
	}
	
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
	 * 检索全部的角色带分页
	 * @param page
	 * @param rows
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(params="method=findAllWithPage")
	public void getAllRole(int page,int rows,HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		List<RoleInfo> roleInfoList =  roleInfoService.getAllRole();
		
		//设置分页
		List<RoleInfo> displyData = new ArrayList<RoleInfo>();
		int resultMaxCount = roleInfoList.size() ;
	    int startIndex = (page-1)*rows<0?0:(page-1)*rows;
	    int endIndex = page*rows<resultMaxCount?page*rows:resultMaxCount;
	    for (int i = startIndex; i < endIndex ; i++) {
	    	displyData.add(roleInfoList.get(i));
	    }
	    
		PageData<RoleInfo> t = new PageData<RoleInfo>();
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
	 * 检索全部的角色不带分页
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(params="method=findAll")
	public void getAllRole(String type,HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		List<RoleInfo> roleInfoList = roleInfoService.getAllRole();
		if("0".equals(type))
		{
			RoleInfo roleInfo = new RoleInfo();
			roleInfo.setId("");
			roleInfo.setNameDisplay("　");
			roleInfoList.add(0,roleInfo);
		}
		String returnJson =JsonUtil.toJson(roleInfoList);
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
	 * 新增角色信息
	 * @param role_info 角色信息
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(params="method=insertRoleInfo")
	public void insertRoleInfo(String role_info,HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		RoleInfo roleInfo = JsonUtil.fromJson(role_info, RoleInfo.class);
		
		int i = roleInfoService.insertRoleInfo(roleInfo);
		
		JsonReturn jsonReturn = new JsonReturn();
		if(i>0){
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
	
	/**
	 * 修改角色信息
	 * @param role_info 角色信息
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(params="method=updateRoleInfo")
	public void updateRoleInfo(String role_info,HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		RoleInfo roleInfo = JsonUtil.fromJson(role_info, RoleInfo.class);
		
		int i = roleInfoService.updateRoleInfo(roleInfo);
		
		JsonReturn jsonReturn = new JsonReturn();
		if(i>0){
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

	/**
	 * 删除角色信息
	 * @param roleId 角色ID
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(params="method=deleteRoleInfo")
	public void deleteRoleInfo(String roleId,HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		//查看角色ID是否有用户指定
		List<UserInfo> userInfoList = userInfoService.getUserByRoleId(roleId);
		JsonReturn jsonReturn =new JsonReturn();
		
		//没有用户的情况下可以删除
		if(userInfoList.size() == 0)
		{
			int i = roleInfoService.deleteRoleInfo(roleId);
			if(i>0){
				jsonReturn.setStatus(Constant.STATU_SUCCESS);
			}else{
				
				jsonReturn.setStatus(Constant.STATU_ERROR);
			}
		}
		else
		{
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
