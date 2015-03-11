package com.dianfeng.utils;
/**
 * json格式传值到前台做判断
 * @author Administrator
 *
 */
public class ResponseFormat {
	private String statu;
	private String record;
	private String exception; 
	private String recordset;
	public String getException() {
		return exception;
	}
	public void setException(String exception) {
		this.exception = exception;
	}
	public String getRecordset() {
		return recordset;
	}
	public void setRecordset(String recordset) {
		this.recordset = recordset;
	}
	public String getStatu() {
		return statu;
	}
	public void setStatu(String statu) {
		this.statu = statu;
	}
	public String getRecord() {
		return record;
	}
	public void setRecord(String record) {
		this.record = record;
	}

}
