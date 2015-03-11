package com.dianfeng.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dianfeng.entity.ActionRecordInfo;
import com.dianfeng.entity.Announcement;
import com.dianfeng.entity.LoginRecordInfo;
import com.dianfeng.entity.Notice;
import com.dianfeng.entity.ReturnCallLoss;
import com.dianfeng.entity.ReturnOnLineSeat;
import com.dianfeng.entity.ReturnSeat;
import com.dianfeng.entity.SimpleSeat;

public interface ReturnLoginService {
	
	int selectNameOrPwd(String number,String password);
	
	ReturnSeat getSeatByLoginName(@Param(value="number")String number);
	
	List<SimpleSeat> findAgentNumberByQueue(String queueStr);
	
	int seatLogin(LoginRecordInfo info);
	
	int updateOldQueueAction(@Param(value="agentNumber")String agentNumber,@Param(value="queueStr")String queueStr);
	
	int insertOldQueueAction(@Param(value="agentNumber")String agentNumber,@Param(value="queueStr")String queueStr);
	
	int insertAction(ActionRecordInfo recordInfo);
	
	int seatLogout(@Param(value="outTime")String outTime,@Param(value="agentNumber")String agentNumber,@Param(value="account")String account);
	
	int updateAction(@Param(value="endTime")String endTime,@Param(value="agentNumber")String agentNumber,@Param(value="account")String account,@Param(value="status")String status);
	
	int selectPwd(@Param(value="loginName")String loginName,@Param(value="oldPwd")String oldPwd);
	
	int updateNewPwd(@Param(value="loginName")String loginName,@Param(value="newPwd")String newPwd);

	//根据分机号查询全部未接来电
	List<ReturnCallLoss> findCallLossByAgentNumber(String agentNumber);
	
	//根据ID删除未接来电
	int deleteCallLossById(String callLossId);

	int selectCallOutByAgentNumber(@Param(value="agentNumber")String agentNumber, @Param(value="time")String time);

	Integer selectCallTimeByAgentNumber(@Param(value="agentNumber")String agentNumber, @Param(value="time")String time);

	List<Announcement> selectAllAnnouncement(@Param(value="time")String time);
	
	int addNotice(@Param(value="noticeId")String noticeId,@Param(value="title")String title,@Param(value="time")String time,@Param(value="agentNumber")String agentNumber, @Param(value="status")String status);
	
	List<Notice> selectAllNoticeByAgent(@Param(value="agentNumber")String agentNumber);
	
	List<Notice> selectAllNoticeByAgentByStatus(@Param(value="agentNumber")String agentNumber, @Param(value="time")String time);
	
	int updateNotice(@Param(value="id")String id);
	
	Announcement selectAnnouncementById(String id);
	
	List<ReturnOnLineSeat> findAllOnLineSeat();
	
	Integer selectCallTimeByUniqueId(String uniqueId);
	
	int addUniqueId(@Param(value="uniqueId")String uniqueId, @Param(value="count")String count);
	
}
