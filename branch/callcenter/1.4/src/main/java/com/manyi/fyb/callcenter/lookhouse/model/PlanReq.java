package com.manyi.fyb.callcenter.lookhouse.model;

import lombok.Data;

@Data
public class PlanReq {
	/**
	 * +下一周,0 本周,-上一周
	 */
	private int action ;
	/**
	 * BD人员ID
	 */
	private int bdId;
	/**
	 * 查询的起始时间
	 */
	private String start;
}
