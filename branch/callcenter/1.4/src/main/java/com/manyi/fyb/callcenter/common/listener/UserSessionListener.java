package com.manyi.fyb.callcenter.common.listener;

import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserSessionListener implements HttpSessionBindingListener {

	private int employeeId;
	
	private Logger logger = LoggerFactory.getLogger(UserSessionListener.class);

	public UserSessionListener(int employeeId) {
		this.employeeId = employeeId;
	}

	@Override
	public void valueBound(HttpSessionBindingEvent event) {
		logger.info("UserSessionListener valueBound invoke !!!!!");
		logger.info("UserSessionListener employeeId == " + employeeId);

		
	}

	@Override
	public void valueUnbound(HttpSessionBindingEvent event) {
		logger.info("UserSessionListener valueUnbound invoke !!!!!");
		logger.info("UserSessionListener employeeId == " + employeeId);
		
	}

}
