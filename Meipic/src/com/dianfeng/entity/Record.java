package com.dianfeng.entity;
/**
 * 录音调听
 * @author Administrator
 *
 */
public class Record {
	private String id;
	private String assemblyLine;//流水号
	private String recordDay;	//开始日期
	private String startTime;	//开始时间
	private String recordTime;	//时长
	private String callType;	//类型
	private String callers;		//拨打
	private String answers;		//接听
	private String score;		//录音评分
	private String recordPath;	//录音地址
	private String uniqueId;	//录音uniqueId
	private String recordType;	//评分类型

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAssemblyLine() {
		return assemblyLine;
	}
	public void setAssemblyLine(String assemblyLine) {
		this.assemblyLine = assemblyLine;
	}
	public String getRecordDay() {
		return recordDay;
	}
	public void setRecordDay(String recordDay) {
		this.recordDay = recordDay;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getRecordTime() {
		return recordTime;
	}
	public void setRecordTime(String recordTime) {
		this.recordTime = recordTime;
	}
	public String getCallType() {
		return callType;
	}
	public void setCallType(String callType) {
		this.callType = callType;
	}
	public String getCallers() {
		return callers;
	}
	public void setCallers(String callers) {
		this.callers = callers;
	}
	public String getAnswers() {
		return answers;
	}
	public void setAnswers(String answers) {
		this.answers = answers;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	public String getRecordPath() {
		return recordPath;
	}
	public void setRecordPath(String recordPath) {
		this.recordPath = recordPath;
	}
	public String getUniqueId()
	{
		return uniqueId;
	}
	public void setUniqueId(String uniqueId)
	{
		this.uniqueId = uniqueId;
	}
	public String getRecordType() {
		return recordType;
	}
	public void setRecordType(String recordType) {
		this.recordType = recordType;
	}
}
