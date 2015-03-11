package com.dianfeng.utils;

import java.io.Serializable;

public class Constant implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final String STATU_SUCCESS = "200";
	public static final String STATU_ERROR = "500";
	
    public final static String ASTERISK_SERVER = "192.168.0.133";
	
	public final static String ASTERISK_PASSWORD = "techsum123";

	public final static String ASTERISK_USER = "techsum";
	
	public final static String SIP = "SIP/";
	public final static String CALL_OUT_CONTEXT = "internal";
	public final static String CALL_OUT_DIALPREFIX = "9";
	
	public static final String RECORD_TAKE ="录音调听";
	
	public static final String [] title = {"工单流水号","开始日期","开始时间","录音时长","通话类型","from","to","坐席满意度评价","录音地址"};
}
