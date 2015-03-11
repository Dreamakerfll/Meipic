package com.dianfeng.listener;

import java.io.IOException;

import org.asteriskjava.manager.AuthenticationFailedException;
import org.asteriskjava.manager.ManagerConnection;
import org.asteriskjava.manager.ManagerConnectionFactory;
import org.asteriskjava.manager.TimeoutException;
import org.asteriskjava.manager.action.OriginateAction;
import org.asteriskjava.manager.response.ManagerResponse;

import com.dianfeng.utils.Constant;



public class MySocket {
	public static ManagerConnection mc;
	private static ManagerConnection managerConnection;
	
	public static ManagerConnection Login() {
		
		if(mc==null){
			
			 mc= getManagerConnection();
	      }else{
		     mc.logoff();
		     mc= getManagerConnection();
	      }    
		return mc;
    }
	
	
	
	private static ManagerConnection getManagerConnection(){
		  boolean success=true;
		  ManagerConnectionFactory mf = new ManagerConnectionFactory(Constant.ASTERISK_SERVER, Constant.ASTERISK_USER,
	                Constant.ASTERISK_PASSWORD);
	         mc= mf.createManagerConnection();
	         ManagerListener ml=new ManagerListener();
	         mc.addEventListener(ml);
//	         ManagerResponse response = new ManagerResponse();
	        try {
	             mc.login();
	                       
	        } catch (IllegalStateException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	            
	        } catch (IOException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	           ;
	        } catch (AuthenticationFailedException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	           
	        } catch (TimeoutException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	           
	        }
	        return mc;
	}
	
	/**
	 * 拨打电话
	 * @param mc
	 * @param telephone	来电号码
	 * @param seat		坐席
	 * @return	成功失败
	 */
	public static boolean callPhone(ManagerConnection mc,String tel,String agentNumber){
		if(agentNumber.equals("注销"))
		{
			return true;
		}
		ManagerResponse response = new ManagerResponse();
		OriginateAction or = new OriginateAction();
		or.setActionId(Constant.SIP + agentNumber + "-" + System.currentTimeMillis());
		or.setCallerId(agentNumber);
		or.setChannel(Constant.SIP + agentNumber);
		or.setContext(Constant.CALL_OUT_CONTEXT);
		or.setTimeout((long) 40000);
		or.setPriority(1);
		or.setAsync(true);
		or.setExten(Constant.CALL_OUT_DIALPREFIX + tel);
		
		try {
			mc.sendAction(or);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	

	
	
	
	 
	 
	

}
