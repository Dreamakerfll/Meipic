package com.dianfeng.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dianfeng.dao.DepartmentInfoDao;
import com.dianfeng.entity.DepartmentInfo;
import com.dianfeng.service.DepartmentInfoService;
@Service("DepartmentInfoService")
public class DepartmentInfoServiceImpl implements DepartmentInfoService
{
	@Autowired
	private DepartmentInfoDao departmentInfoDao;
	
	@Override
	public List<DepartmentInfo> getAllDepartment()
	{
		// TODO Auto-generated method stub
		return departmentInfoDao.getAllDepartment();
	}

	@Override
	public int insertDepartmentInfo(DepartmentInfo departmentInfo)
	{
		// TODO Auto-generated method stub
		return departmentInfoDao.insertDepartmentInfo(departmentInfo);
	}

	@Override
	public int updateDepartmentInfo(DepartmentInfo departmentInfo)
	{
		// TODO Auto-generated method stub
		return departmentInfoDao.updateDepartmentInfo(departmentInfo);
	}

	@Override
	public int deleteDepartmentInfo(String departmentId)
	{
		// TODO Auto-generated method stub
		return departmentInfoDao.deleteDepartmentInfo(departmentId);
	}

}
