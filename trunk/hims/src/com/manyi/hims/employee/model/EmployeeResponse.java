package com.manyi.hims.employee.model;


import lombok.Data;

import com.manyi.hims.Response;
@Data
public class EmployeeResponse extends Response {
	
	private int id;
	
	private String userName;
	
	private String password;
	
	private String realName;
	
	private int role;
	
	private int finishedTaskNum;//完成的工作数目


	private String ucServerName;

	private String ucServerPwd;

	private String ucServerGroupId;
	
	private int areaId;
	
	private int cityId;
	private String cityName;
	private String areaName;

}
