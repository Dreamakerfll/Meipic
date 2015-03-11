package com.dianfeng.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dianfeng.dao.RoleInfoDao;
import com.dianfeng.entity.RoleInfo;
import com.dianfeng.service.RoleInfoService;
@Service("RoleInfoService")
public class RoleInfoServiceImpl implements RoleInfoService
{
	@Autowired
	private RoleInfoDao roleInfoDao;

	public List<RoleInfo> getAllRole()
	{
		// TODO Auto-generated method stub
		return roleInfoDao.getAllRole();
	}

	@Override
	public int deleteRoleInfo(String roleId)
	{
		// TODO Auto-generated method stub
		return roleInfoDao.deleteRoleInfo(roleId);
	}

	@Override
	public int insertRoleInfo(RoleInfo roleInfo)
	{
		// TODO Auto-generated method stub
		return roleInfoDao.insertRoleInfo(roleInfo);
	}

	@Override
	public int updateRoleInfo(RoleInfo roleInfo)
	{
		// TODO Auto-generated method stub
		return roleInfoDao.updateRoleInfo(roleInfo);
	}
	

}
