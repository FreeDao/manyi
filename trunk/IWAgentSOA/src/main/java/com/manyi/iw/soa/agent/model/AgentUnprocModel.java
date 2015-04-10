package com.manyi.iw.soa.agent.model;

import java.util.Date;

import lombok.Data;

@Data
public class AgentUnprocModel {
	private String realname;
	private Byte gender;
	private Byte userType;
	private String mobile;
	private Date lastLoginTime;
	private String unprocCount;
	private Long userId;
}
