package com.dianfeng.service;

import java.util.List;

import com.dianfeng.entity.AreaInfo;

public interface AreaInfoService
{
	/**
	 * 获取所有的区域
	 */
	List<AreaInfo> getAllArea();
	
	/**
	 * 根据区号查询归属地
	 * @param tel
	 * @return
	 */
	List<AreaInfo> getAreaByCode(String tel);
	
	/**
	 * 根据手机号查询归属地
	 * @param tel
	 * @return
	 */
	List<AreaInfo> getAreaByPhone(String tel);
}
