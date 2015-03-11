package com.dianfeng.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dianfeng.dao.TransferDao;
import com.dianfeng.entity.Transfer;
import com.dianfeng.service.TransferService;

@Service("TransferService")
public class TransferServiceImpl implements TransferService
{

	@Autowired
	private TransferDao transferDao;
	
	public TransferDao getTransferDao()
	{
		return transferDao;
	}

	public void setTransferDao(TransferDao transferDao)
	{
		this.transferDao = transferDao;
	}

	public int addTransfer(List<Transfer> transferList)
	{
		// TODO Auto-generated method stub
		return transferDao.addTransfer(transferList);
	}

	public int deleteTransferByBatchId(String batchId)
	{
		// TODO Auto-generated method stub
		return transferDao.deleteTransferByBatchId(batchId);
	}

	public List<Transfer> getAllTransfer()
	{
		// TODO Auto-generated method stub
		return transferDao.getAllTransfer();
	}

	public List<Transfer> getTransferByAccount(String account)
	{
		// TODO Auto-generated method stub
		return transferDao.getTransferByAccount(account);
	}

	public int updateStatusByBatchId(String batchId, String status)
	{
		// TODO Auto-generated method stub
		return transferDao.updateStatusByBatchId(batchId, status);
	}

	public List<Transfer> getTransferByCondition(String beginTime, String endTime, String phoneNumber, String area,String account)
	{
		// TODO Auto-generated method stub
		return transferDao.getTransferByCondition(beginTime, endTime, phoneNumber, area,account);
	}

	public String isAreaExist(List<String> areaList)
	{
		// TODO Auto-generated method stub
		return transferDao.isAreaExist(areaList);
	}

}
