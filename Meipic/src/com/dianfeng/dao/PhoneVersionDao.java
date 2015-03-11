package com.dianfeng.dao;

import java.util.List;

import com.dianfeng.entity.PhoneVersion;

public interface PhoneVersionDao
{
	/**
	 * 获取所有手机版本
	 * @return
	 */
	List<PhoneVersion> getAllPhoneVersion();
}
