package com.manyi.ihouse.test.controller;

import java.util.HashMap;
import java.util.Map;

import com.manyi.ihouse.util.InfoUtils;

public class TestAssigneeController {
	
	public static void main(String[] args) {
		
		TestAssigneeController testAssigneeController = new TestAssigneeController();
		testAssigneeController.addAssignee();
		
	}
	
	public void addAssignee() {
		String url = "http://localhost:8080/assigneeManager/addAssignee.rest";
		Map<String, Object> pars = new HashMap<String, Object>();
		
		pars.put("serialNumber", "10003");
		pars.put("name", "测试3");
		pars.put("mobile", "15912345673");
		pars.put("information", "测试用户3");
		
		InfoUtils.sendRestInter(url, pars);

	}

}
