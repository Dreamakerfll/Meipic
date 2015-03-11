package com.dianfeng.socket;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.dianfeng.service.impl.ManagerServiceImpl;


public class GeneralFactory implements ApplicationContextAware{
	private static ApplicationContext applicationContext;

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        GeneralFactory.applicationContext = applicationContext;
    }
    public ManagerServiceImpl getManagerService() {
        return (ManagerServiceImpl) applicationContext.getBean("ManagerService");
    }

}
