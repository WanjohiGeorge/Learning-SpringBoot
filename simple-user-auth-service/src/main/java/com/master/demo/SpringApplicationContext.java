package com.master.demo;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContextAware;


public class SpringApplicationContext implements ApplicationContextAware
{
	private static org.springframework.context.ApplicationContext CONTEXT;

	@Override
	public void setApplicationContext(org.springframework.context.ApplicationContext applicationContext)
			throws BeansException {
	CONTEXT =   applicationContext;	
	}
	
	public static Object getBean(String beanName) {
		return CONTEXT.getBean(beanName);
	}	

}
