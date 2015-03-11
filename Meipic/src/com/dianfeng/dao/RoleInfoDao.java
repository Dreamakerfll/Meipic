package com.dianfeng.dao;

import java.util.List;

import com.dianfeng.entity.RoleInfo;

public interface RoleInfoDao
{
	/**
	 * 获取所有的 角色
	 * @return 所有的角色
	 */
	List<RoleInfo> getAllRole();
	
	/**
	 * 新增角色
	 * @param roleInfo 角色信息
	 * @return
	 * 新增的条数
	 */
	int insertRoleInfo(RoleInfo roleInfo);
	
	/**
	 * 修改角色
	 * @param roleInfo 角色信息
	 * @return
	 * 修改的条数
	 */
	int updateRoleInfo(RoleInfo roleInfo);
	
	/**
	 * 删除角色
	 * @param roleId 角色ID
	 * @return
	 * 删除的条数
	 */
	int deleteRoleInfo(String roleId);
}
