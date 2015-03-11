package com.dianfeng.dao;

import java.util.List;

import com.dianfeng.entity.UserInfo;

public interface UserInfoDao
{
	/**
	 * 获取所有用户
	 * @return
	 * 返回所有用户信息
	 */
	List<UserInfo> getAllUser();
	
	/**
	 * 分页获取所有用户
	 * @return
	 * 返回所有用户信息
	 */
	List<UserInfo> getAllUserWithPage();
	
	/**
	 * 根据用户信息获取用户
	 * @param userInfo 用户信息
	 * @return
	 * 返回符合条件的用户
	 */
	List<UserInfo> getUserByCondition(UserInfo userInfo);
	
	/**
	 * 检查账号是否存在
	 * @param account 账号
	 * @return
	 * 返回检索的用户信息
	 */
	List<UserInfo> checkUserExist(String account);
	
	/**
	 * 通过角色ID查找用户
	 * @param roleId 角色ID
	 * @return
	 * 用户信息
	 */
	List<UserInfo> getUserByRoleId(String roleId);
	
	/**
	 * 通过部门ID查找用户
	 * @param departmentId 部门ID
	 * @return
	 * 用户信息
	 */
	List<UserInfo> getUserByDepartmentId(String departmentId);
	
	/**
	 * 新增用户
	 * @param userInfo 用户信息
	 * @return
	 * 新增的条数
	 */
	int insertUserInfo(UserInfo userInfo);
	
	/**
	 * 更新用户信息
	 * @param userInfo 用户信息
	 * @return
	 * 更新的条数
	 */
	int updateUserInfo(UserInfo userInfo);
	
	/**
	 * 删除用户
	 * @param userId 用户ID
	 * @return
	 * 删除的条数
	 */
	int deleteUserInfo(String userId);
}
