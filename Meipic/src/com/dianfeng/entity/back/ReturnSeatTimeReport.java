package com.dianfeng.entity.back;

public class ReturnSeatTimeReport {
	String selectDay;			//日期
	String seatNumber;			//坐席号
	String sumOnLineTime;		//在线时长
	String sumBusyTime;			//示忙时长
	String sumOutLineTime;		//休息时长
	String sumAllTime;			//总时长
	
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

	public String getSumOnLineTime() {
		return sumOnLineTime;
	}

	public void setSumOnLineTime(String sumOnLineTime) {
		this.sumOnLineTime = sumOnLineTime;
	}

	public String getSumBusyTime() {
		return sumBusyTime;
	}

	public void setSumBusyTime(String sumBusyTime) {
		this.sumBusyTime = sumBusyTime;
	}

	public String getSumOutLineTime() {
		return sumOutLineTime;
	}

	public void setSumOutLineTime(String sumOutLineTime) {
		this.sumOutLineTime = sumOutLineTime;
	}

	public String getSumAllTime() {
		return sumAllTime;
	}

	public void setSumAllTime(String sumAllTime) {
		this.sumAllTime = sumAllTime;
	}

}
