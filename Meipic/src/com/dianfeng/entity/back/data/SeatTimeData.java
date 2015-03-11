package com.dianfeng.entity.back.data;

public class SeatTimeData {
	private String intervalTime;
	private String selectDay;
	private String seatNumber;
	private String status;
	
	public SeatTimeData(){}
	public SeatTimeData(String selectDay,String seatNumber,String status,String intervalTime){
		this.selectDay = selectDay;
		this.seatNumber = seatNumber;
		this.status = status;
		this.intervalTime = intervalTime;
	}
	public String getIntervalTime() {
		return intervalTime;
	}
	public void setIntervalTime(String intervalTime) {
		this.intervalTime = intervalTime;
	}
	public String getSelectDay() {
		return selectDay;
	}
	public void setSelectDay(String selectDay) {
		this.selectDay = selectDay;
	}
	public String getSeatNumber() {
		return seatNumber;
	}
	public void setSeatNumber(String seatNumber) {
		this.seatNumber = seatNumber;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
