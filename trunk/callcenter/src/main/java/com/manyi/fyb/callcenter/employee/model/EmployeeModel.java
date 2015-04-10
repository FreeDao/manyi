package com.manyi.fyb.callcenter.employee.model;

import lombok.Data;
@Data
public class EmployeeModel {
	
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
	
	private String areaName;
	
	
	/**
	 * 城市id
	 */
	private int cityId;//12217
	
	/**
	 * 城市名称
	 */
	private String cityName;

}
