package com.dianfeng.entity.back.data;

public class SeatWorkData {
	private String selectDay;
	private String hour;
	private String disposition;
	private String callType;
	private String count;
	private String sum;
	public SeatWorkData(){}
	public SeatWorkData(String selectDay,String hour,String disposition,String callType,String count,String sum){
		this.selectDay = selectDay;
		this.hour = hour;
		this.disposition = disposition;
		this.callType = callType;
		this.count = count;
		this.sum = sum;
	}
	
	public String getSelectDay() {
		return selectDay;
	}
	public void setSelectDay(String selectDay) {
		this.selectDay = selectDay;
	}
	public String getHour() {
		return hour;
	}
	public void setHour(String hour) {
		this.hour = hour;
	}
	public String getDisposition() {
		return disposition;
	}
	public void setDisposition(String disposition) {
		this.disposition = disposition;
	}
	public String getCallType() {
		return callType;
	}
	public void setCallType(String callType) {
		this.callType = callType;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	public String getSum() {
		return sum;
	}
	public void setSum(String sum) {
		this.sum = sum;
	}
}
