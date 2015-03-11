package com.dianfeng.entity.back.data;

public class TrafficData {
	private String departmentName;
	private String disposition;
	private String callType;
	private String count;
	private String sum;
	private String maxHoldTime;
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
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
	public String getMaxHoldTime() {
		return maxHoldTime;
	}
	public void setMaxHoldTime(String maxHoldTime) {
		this.maxHoldTime = maxHoldTime;
	}
}
