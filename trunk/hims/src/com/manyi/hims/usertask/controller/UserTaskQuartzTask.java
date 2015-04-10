package com.manyi.hims.usertask.controller;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.manyi.hims.usertask.service.UserTaskService;

public class UserTaskQuartzTask {
	
	@Autowired
	private UserTaskService userTaskService;

	private Logger logger = LoggerFactory.getLogger(UserTaskQuartzTask.class);

	
	public void setUserTask48HoursExpired() {

		logger.info("start setUserTask48HoursExpired UserTaskQuartzTask.... at {}", new Date());
		try {
			userTaskService.setUserTask48HoursExpired();
			logger.info("将48小时前中介任务设置为过期 完成... ");
		} catch (Exception e) {
			logger.info("将48小时前中介任务设置为过期 错误！{}" + e.getMessage());
			e.printStackTrace();
		}

	}



}
