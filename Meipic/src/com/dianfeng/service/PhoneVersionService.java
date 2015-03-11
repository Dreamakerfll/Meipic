package com.dianfeng.service;

import java.util.List;

import com.dianfeng.entity.PhoneVersion;

public interface PhoneVersionService
{
	
	/**
	 * 获取所有手机版本
	 * @return
	 */
	List<PhoneVersion> getAllPhoneVersion();
}
