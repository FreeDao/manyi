package com.manyi.ihouse.user.model;

public class AutoLoginRequest {
	
	private long userId; //用户ID
	
	private String mobile; //手机号码

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
}
