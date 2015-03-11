package com.dianfeng.service;

import java.util.List;

import com.dianfeng.entity.DepartmentInfo;

public interface DepartmentInfoService
{
	/**
	 * 获取所有的部门
	 * @return
	 * 返回所有的部门
	 */
	List<DepartmentInfo> getAllDepartment();
	
	/**
	 * 新增部门信息
	 * @param departmentInfo 部门信息
	 * @return
	 * 新增的条数
	 */
	int insertDepartmentInfo(DepartmentInfo departmentInfo);
	
	/**
	 * 更新部门信息
	 * @param departmentInfo 部门信息
	 * @return
	 * 更新的条数
	 */
	int updateDepartmentInfo(DepartmentInfo departmentInfo);
	
	/**
	 * 删除部门信息
	 * @param departmentId 部门Id
	 * @return
	 * 删除的条数
	 */
	int deleteDepartmentInfo(String departmentId);
}
