package com.dianfeng.socket;



import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.dianfeng.listener.MySocket;
import com.dianfeng.service.impl.ManagerServiceImpl;





public class MyListener implements ServletContextListener {
	private MyThread myThread;
	public void contextDestroyed(ServletContextEvent e) {
		MySocket.mc.logoff();
		if (myThread != null && myThread.isInterrupted()) {
			myThread.interrupt();
			
		}
	}

	public void contextInitialized(ServletContextEvent e) {
		String str = null;
		if (str == null && myThread == null) {
			MySocket.mc=MySocket.Login();
			myThread = new MyThread();
			myThread.start(); // servlet 上下文初始化时启动 socket
		}
	}
}




/**
 * 自定义一个 Class 线程类继承自线程类，重写 run() 方法，用于从后台获取并处理数据
 * 
 * 
 */
class MyThread extends Thread {
	public void run() {
		
		MySocket.Login();
		
		
		GeneralFactory gf = new GeneralFactory();
		ManagerServiceImpl managerService = gf.getManagerService();
//		while (!this.isInterrupted()) {// 线程未中断执行循环
//			try {
//				Thread.sleep(60000); //每隔2000ms执行一次
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//			
//		 
//		}
		
	}
	
	
}

