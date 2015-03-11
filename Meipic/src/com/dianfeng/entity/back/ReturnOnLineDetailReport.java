package com.dianfeng.entity.back;


public class ReturnOnLineDetailReport {
	String seatNumber;		//坐席号
	String startTime;		//开始时间
	String endTime;			//结束时间
	String betweenTime;		//时长
	String status;			//状态
	public String getSeatNumber() {
		return seatNumber;
	}
	public void setSeatNumber(String seatNumber) {
		this.seatNumber = seatNumber;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getBetweenTime() {
		return betweenTime;
	}
	public void setBetweenTime(String betweenTime) {
		this.betweenTime = betweenTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
