package com.dianfeng.entity.back;


public class ReturnSatisfactionDetailReport {
	String department;		//部门
	String recordType;
	String seatName;		//姓名
	String agentNumber;		//分机号
	String callNumber;		//电话号码
	String callDay;			//日期
	String evaluationLevel;	//评价
	String playPath;		//播放地址
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getSeatName() {
		return seatName;
	}
	public void setSeatName(String seatName) {
		this.seatName = seatName;
	}
	public String getAgentNumber() {
		return agentNumber;
	}
	public void setAgentNumber(String agentNumber) {
		this.agentNumber = agentNumber;
	}
	public String getCallNumber() {
		return callNumber;
	}
	public void setCallNumber(String callNumber) {
		this.callNumber = callNumber;
	}
	public String getCallDay() {
		return callDay;
	}
	public void setCallDay(String callDay) {
		this.callDay = callDay;
	}
	public String getEvaluationLevel() {
		return evaluationLevel;
	}
	public void setEvaluationLevel(String evaluationLevel) {
		this.evaluationLevel = evaluationLevel;
	}
	public String getPlayPath() {
		return playPath;
	}
	public void setPlayPath(String playPath) {
		this.playPath = playPath;
	}
	public String getRecordType() {
		return recordType;
	}
	public void setRecordType(String recordType) {
		this.recordType = recordType;
	}
}
