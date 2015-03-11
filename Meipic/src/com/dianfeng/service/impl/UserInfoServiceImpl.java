package com.dianfeng.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dianfeng.dao.UserInfoDao;
import com.dianfeng.entity.UserInfo;
import com.dianfeng.service.UserInfoService;
@Service("UserInfoService")
public class UserInfoServiceImpl implements UserInfoService
{

	@Autowired
	private UserInfoDao userInfoDao;
	
	@Override
	public List<UserInfo> getAllUser()
	{
		// TODO Auto-generated method stub
		return userInfoDao.getAllUser();
	}

	@Override
	public List<UserInfo> checkUserExist(String account)
	{
		// TODO Auto-generated method stub
		return userInfoDao.checkUserExist(account);
	}

	@Override
	public int deleteUserInfo(String userId)
	{
		// TODO Auto-generated method stub
		return userInfoDao.deleteUserInfo(userId);
	}

	@Override
	public int insertUserInfo(UserInfo userInfo)
	{
		// TODO Auto-generated method stub
		return userInfoDao.insertUserInfo(userInfo);
	}

	@Override
	public int updateUserInfo(UserInfo userInfo)
	{
		// TODO Auto-generated method stub
		return userInfoDao.updateUserInfo(userInfo);
	}

	@Override
	public List<UserInfo> getUserByRoleId(String roleId)
	{
		// TODO Auto-generated method stub
		return userInfoDao.getUserByRoleId(roleId);
	}

	@Override
	public List<UserInfo> getUserByDepartmentId(String departmentId)
	{
		// TODO Auto-generated method stub
		return userInfoDao.getUserByDepartmentId(departmentId);
	}

	@Override
	public List<UserInfo> getUserByCondition(UserInfo userInfo)
	{
		// TODO Auto-generated method stub
		return userInfoDao.getUserByCondition(userInfo);
	}

	@Override
	public List<UserInfo> getAllUserWithPage()
	{
		// TODO Auto-generated method stub
		return userInfoDao.getAllUserWithPage();
	}

}
