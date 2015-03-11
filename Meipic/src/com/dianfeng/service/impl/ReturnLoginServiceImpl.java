package com.dianfeng.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dianfeng.dao.ReturnLoginDao;
import com.dianfeng.entity.ActionRecordInfo;
import com.dianfeng.entity.Announcement;
import com.dianfeng.entity.LoginRecordInfo;
import com.dianfeng.entity.Notice;
import com.dianfeng.entity.ReturnCallLoss;
import com.dianfeng.entity.ReturnOnLineSeat;
import com.dianfeng.entity.ReturnSeat;
import com.dianfeng.entity.SimpleSeat;
import com.dianfeng.service.ReturnLoginService;
@Service("ReturnLoginService")
public class ReturnLoginServiceImpl implements ReturnLoginService {
	@Autowired
	private ReturnLoginDao dao;

	public int selectNameOrPwd(String number, String password) {
		
		return dao.selectNameOrPwd(number, password);
	}

	public ReturnSeat getSeatByLoginName(String number) {
		
		return dao.getSeatByLoginName(number);
	}

	
	public List<SimpleSeat> findAgentNumberByQueue(String queueStr) {
		
		return dao.findAgentNumberByQueue(queueStr);
	}

	public int seatLogin(LoginRecordInfo info) {
	
		return dao.seatLogin(info);
	}

	
	public int insertOldQueueAction(String agentNumber, String queueStr) {
	
		return dao.insertOldQueueAction(agentNumber, queueStr);
	}

	public int updateOldQueueAction(String agentNumber, String queueStr) {
		
		return dao.updateOldQueueAction(agentNumber, queueStr);
	}

	public int insertAction(ActionRecordInfo recordInfo) {
	
		return dao.insertAction(recordInfo);
	}

	public int seatLogout(String outTime, String agentNumber, String account) {
		
		return dao.seatLogout(outTime, agentNumber, account);
	}

	public int updateAction(String endTime, String agentNumber, String account,
			String status) {
		
		return dao.updateAction(endTime, agentNumber, account, status);
	}

	public int selectPwd(String loginName, String oldPwd) {
		
		return dao.selectPwd(loginName, oldPwd);
	}

	public int updateNewPwd(String loginName, String newPwd) {
		
		return dao.updateNewPwd(loginName, newPwd);
	}

	public List<ReturnCallLoss> findCallLossByAgentNumber(String agentNumber) {
		
		return dao.findCallLossByAgentNumber(agentNumber);
	}

	public int deleteCallLossById(String callLossId) {
		
		return dao.deleteCallLossById(callLossId);
	}

	public int selectCallOutByAgentNumber(String agentNumber, String time) {
	
		return dao.selectCallOutByAgentNumber(agentNumber,time);
	}

	public Integer selectCallTimeByAgentNumber(String agentNumber, String time) {
		
		return dao.selectCallTimeByAgentNumber(agentNumber, time);
	}

	public int addNotice(String noticeId,String title,String time, String agentNumber, String status) {
		
		return dao.addNotice(noticeId,title, time,agentNumber, status);
	}

	public List<Notice> selectAllNoticeByAgent(String agentNumber) {
		
		return dao.selectAllNoticeByAgent(agentNumber);
	}

	public List<Announcement> selectAllAnnouncement(String time) {
		
		return dao.selectAllAnnouncement(time);
	}

	
	public List<ReturnOnLineSeat> findAllOnLineSeat() {
		
		return dao.findAllOnLineSeat();
	}

	
	

	public int updateNotice(String id) {
		
		return dao.updateNotice(id);
	}

	public Announcement selectAnnouncementById(String id) {
		
		return dao.selectAnnouncementById(id);
	}

	public Integer selectCallTimeByUniqueId(String uniqueId) {
		
		return dao.selectCallTimeByUniqueId(uniqueId);
	}

	public List<Notice> selectAllNoticeByAgentByStatus(String agentNumber,
			String time) {
		
		return dao.selectAllNoticeByAgentByStatus(agentNumber, time);
	}

	public int addUniqueId(String uniqueId, String count) {
		
		return dao.addUniqueId(uniqueId, count);
	}



	

}
