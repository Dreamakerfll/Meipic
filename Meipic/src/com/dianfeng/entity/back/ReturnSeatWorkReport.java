package com.dianfeng.entity.back;
/** 坐席个人报表 */
public class ReturnSeatWorkReport {
	private String selectDay;		//日期
	private String selectHour;		//时段
	private String countACD;		//ACD来电量
	private String countSuccessACD;	//ACD成功接听量
	private String countFailureACD;	//ACD响铃放弃量
	private String sumCallInACD;	//ACD呼入总时长
	private String countInternal;	//内部呼叫数量
	private String sumInternal;		//内部呼叫总时长
	private String countCallOut;	//外拨呼叫量
	private String sumCallOut;		//外拨呼叫总时长
	private String sumAllCallTime;	//通话总时长
	
	public String getSelectDay() {
		return selectDay;
	}
	public void setSelectDay(String selectDay) {
		this.selectDay = selectDay;
	}
	public String getSelectHour() {
		return selectHour;
	}
	public void setSelectHour(String selectHour) {
		this.selectHour = selectHour;
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
	public String getCountInternal() {
		return countInternal;
	}
	public void setCountInternal(String countInternal) {
		this.countInternal = countInternal;
	}
	public String getSumInternal() {
		return sumInternal;
	}
	public void setSumInternal(String sumInternal) {
		this.sumInternal = sumInternal;
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
	public String getSumAllCallTime() {
		return sumAllCallTime;
	}
	public void setSumAllCallTime(String sumAllCallTime) {
		this.sumAllCallTime = sumAllCallTime;
	}
	
}
