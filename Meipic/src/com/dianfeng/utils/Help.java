package com.dianfeng.utils;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Help {
	
	/**
	 * 
	 * @param pattern			转换字符串格式 	例如:MM/dd/yyyy
	 * @param fomartPattern		要转换成的格式 	例如:yyyy-MM-dd
	 * @param dateString		需要被转换字符串 	 例如:10/27/2014
	 * @return	处理后的字符串
	 * @throws ParseException
	 */
	public static String fomateString(String pattern,String fomartPattern,String dateString) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		Date tempDate = (Date)sdf.parse(dateString);
		sdf = new SimpleDateFormat(fomartPattern);
		return sdf.format(tempDate);
	}
	/**小数转换成分数 */
	public static String decimalToPercent (int x,int total){  
		   double x_double= x*1.0;
		   if(total == 0) total = 1;
		   double tempresult=x_double/total;  
		   DecimalFormat df = new DecimalFormat("0.00%");    //##.00%   百分比格式，后面不足2位的用0补齐  
		   return df.format(tempresult);  
	}  
	
	/**整数转成时分秒;*/
	public static String secToTime(int time) {  
        String timeStr = null;  
        int hour = 0;  
        int minute = 0;  
        int second = 0;  
        if (time <= 0)  
            return "00:00:00";  
        else {  
            minute = time / 60;  
            if (minute < 60) {  
                second = time % 60;  
                timeStr = unitFormat(minute) + ":" + unitFormat(second);  
            } else {  
                hour = minute / 60;  
                minute = minute % 60;  
                second = time - hour * 3600 - minute * 60;  
                timeStr = unitFormat(hour) + ":" + unitFormat(minute) + ":" + unitFormat(second);  
            }  
        }  
        
        if(timeStr.length()<6)
        	return "00:"+timeStr;
        
        return timeStr;
    }  
  
    public static String unitFormat(int i) {  
        String retStr = null;  
        if (i >= 0 && i < 10)  
            retStr = "0" + Integer.toString(i);  
        else  
            retStr = "" + i;  
        return retStr;  
    }  

}
