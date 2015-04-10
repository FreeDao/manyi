package com.manyi.fyb.callcenter.employee.model;

import lombok.Data;

import com.manyi.fyb.callcenter.base.model.Response;

@Data
public class EmployeeResponse extends Response {
	
	private int id;
	
	private String userName;

	private String password;

	private String realName;

	private int role;

	private int finishedTaskNum;// 完成的工作数目

	private String ucServerName;

	private String ucServerPwd;

	private String ucServerGroupId;

	private int areaId;
	private int cityId;
	/**
	 * 城市名称
	 */
	private String cityName;
	private String areaName;

	

}
