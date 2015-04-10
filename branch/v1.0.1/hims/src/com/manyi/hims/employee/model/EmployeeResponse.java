package com.manyi.hims.employee.model;

import lombok.Getter;
import lombok.Setter;

import com.manyi.hims.Response;

public class EmployeeResponse extends Response {
	
private int id;
	
	private String userName;
	
	private String password;
	
	private String realName;
	
	private int role;
	
	@Getter @Setter
	private int finishedTaskNum;//完成的工作数目

	@Getter @Setter private String ucServerName;
	
	@Getter @Setter private String ucServerPwd;

	@Getter @Setter private String ucServerGroupId;
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}
	

}
