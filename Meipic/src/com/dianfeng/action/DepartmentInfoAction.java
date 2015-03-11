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

import com.dianfeng.entity.DepartmentInfo;
import com.dianfeng.entity.UserInfo;
import com.dianfeng.service.DepartmentInfoService;
import com.dianfeng.service.UserInfoService;
import com.dianfeng.utils.Constant;
import com.dianfeng.utils.JsonReturn;
import com.dianfeng.utils.JsonUtil;
import com.dianfeng.utils.PageData;

@Component
@RequestMapping("/department_info")
public class DepartmentInfoAction
{
	@Resource
	private DepartmentInfoService departmentInfoService;

	public DepartmentInfoService getDepartmentInfoService()
	{
		return departmentInfoService;
	}

	public void setDepartmentInfoService(DepartmentInfoService departmentInfoService)
	{
		this.departmentInfoService = departmentInfoService;
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
	 * 获取所有的部门带分页
	 * @throws IOException
	 */
	@RequestMapping(params="method=findAllWithPage")
	public void getAllDepartmentWithPage(int page,int rows,HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		List<DepartmentInfo> departmentInfoList = departmentInfoService.getAllDepartment();
		
		//设置分页
		List<DepartmentInfo> displyData = new ArrayList<DepartmentInfo>();
		int resultMaxCount = departmentInfoList.size() ;
	    int startIndex = (page-1)*rows<0?0:(page-1)*rows;
	    int endIndex = page*rows<resultMaxCount?page*rows:resultMaxCount;
	    for (int i = startIndex; i < endIndex ; i++) {
	    	displyData.add(departmentInfoList.get(i));
	    }
	    
		PageData<DepartmentInfo> t = new PageData<DepartmentInfo>();
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
	 * 获取所有的部门不带分页
	 * @throws IOException
	 */
	@RequestMapping(params="method=findAll")
	public void getAllDepartment(String type,HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		List<DepartmentInfo> departmentInfoList = departmentInfoService.getAllDepartment();
		if("0".equals(type))
		{
			DepartmentInfo departmentInfo = new DepartmentInfo();
			departmentInfo.setId("");
			departmentInfo.setNameDisplay("　");
			departmentInfoList.add(0,departmentInfo);
		}
		String returnJson =JsonUtil.toJson(departmentInfoList);
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
	 * 新部门色信息
	 * @param department_info 部门信息
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(params="method=insertDepartmentInfo")
	public void insertDepartmentInfo(String department_info,HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		DepartmentInfo departmentInfo = JsonUtil.fromJson(department_info, DepartmentInfo.class);
		
		int i = departmentInfoService.insertDepartmentInfo(departmentInfo);
		
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
	 * 修改部门信息
	 * @param department_info 部门信息
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(params="method=updateDepartmentInfo")
	public void updateDepartmentInfo(String department_info,HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		DepartmentInfo departmentInfo = JsonUtil.fromJson(department_info, DepartmentInfo.class);
		
		int i = departmentInfoService.updateDepartmentInfo(departmentInfo);
		
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
	 * 删除部门信息
	 * @param departmentId 部门ID
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(params="method=deleteDepartmentInfo")
	public void deleteDepartmentInfo(String departmentId,HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		//查看部门ID是否有用户指定
		List<UserInfo> userInfoList = userInfoService.getUserByDepartmentId(departmentId);
		JsonReturn jsonReturn =new JsonReturn();
		
		//没有用户的情况下可以删除
		if(userInfoList.size() == 0)
		{
			int i = departmentInfoService.deleteDepartmentInfo(departmentId);
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
