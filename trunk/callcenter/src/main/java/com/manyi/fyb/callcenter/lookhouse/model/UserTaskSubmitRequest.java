package com.manyi.fyb.callcenter.lookhouse.model;

import lombok.Data;

@Data
public class UserTaskSubmitRequest {
	/**
	 * user task Id
	 */
	private int id;
	/**
	 * UserTaskStatusEnum
	 * 审核状态(4,审核失败;3,审核成功)
	 */
	private int status;
	private int operatorId;
	/**
	 * 审核失败的  理由 /成功的简述
	 */
	private String note;
	/**
	 * 总楼层高
	 */
	private int layers;
	/**
	 * 装修类型
	 */
	private String decorateType;
}
