package com.manyi.fyb.callcenter.user.model;

import lombok.Data;

@Data
public class UserRequest {
	private String mobile;//手机号
	private String password;
	private String realName;//身份证姓名
	private int operator;
	private int areaId;
}
