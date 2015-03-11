package com.dianfeng.entity.back;


/** 坐席个人报表 */
public class ReturnTrafficReport {
	private String departmentName;	//技能组名称
	private String countACD;		//ACD来电量
	private String countSuccessACD;	//ACD成功接听量
	private String countFailureACD;	//ACD响铃放弃量
	private String sumCallInACD;	//ACD呼入总时长
	private String avgCallInACD;
	private String maxHoldACD;
	private String countCallOut;	//外拨呼叫量
	private String sumCallOut;		//外拨呼叫总时长
	private String avgCallOut;		//平均外拨时长
	private String sumAllCallTime;	//通话总时长
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	public String getCountACD() {
		return countACD;
	}
	public void setCountACD(String countACD) {
		this.countACD = countACD;
	}
	public String getCountSuccessACD() {
		return countSuccessACD;
	}
	public void setCountSuccessACD(String countSuccessACD) {
		this.countSuccessACD = countSuccessACD;
	}
	public String getCountFailureACD() {
		return countFailureACD;
	}
	public void setCountFailureACD(String countFailureACD) {
		this.countFailureACD = countFailureACD;
	}
	public String getSumCallInACD() {
		return sumCallInACD;
	}
	public void setSumCallInACD(String sumCallInACD) {
		this.sumCallInACD = sumCallInACD;
	}
	public String getAvgCallInACD() {
		return avgCallInACD;
	}
	public void setAvgCallInACD(String avgCallInACD) {
		this.avgCallInACD = avgCallInACD;
	}
	public String getMaxHoldACD() {
		return maxHoldACD;
	}
	public void setMaxHoldACD(String maxHoldACD) {
		this.maxHoldACD = maxHoldACD;
	}
	public String getCountCallOut() {
		return countCallOut;
	}
	public void setCountCallOut(String countCallOut) {
		this.countCallOut = countCallOut;
	}
	public String getSumCallOut() {
		return sumCallOut;
	}
	public void setSumCallOut(String sumCallOut) {
		this.sumCallOut = sumCallOut;
	}
	public String getAvgCallOut() {
		return avgCallOut;
	}
	public void setAvgCallOut(String avgCallOut) {
		this.avgCallOut = avgCallOut;
	}
	public String getSumAllCallTime() {
		return sumAllCallTime;
	}
	public void setSumAllCallTime(String sumAllCallTime) {
		this.sumAllCallTime = sumAllCallTime;
	}
}
