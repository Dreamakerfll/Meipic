package com.dianfeng.dao;

import java.util.List;

import com.dianfeng.entity.PhoneModel;

public interface PhoneModelDao
{
	/**
	 * 获取所有手机型号
	 * @return
	 */
	List<PhoneModel> getAllPhoneModel();
}
