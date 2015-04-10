package com.manyi.fyb.callcenter.lookhouse.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.manyi.fyb.callcenter.base.model.Response;

@Data
@EqualsAndHashCode(callSuper=true)
public class PlanRes extends Response{
	/**
	 * taskId
	 */
	private int id;
	/**
	 * 看房时间
	 */
	private long taskDate;
	/**
	 * 小区名字
	 */
	private String estateName;
	
	/**
	 * BD名称
	 */
	private String bdName;
	/**
	 * 行政区域名称
	 */
	private String areaName;
}
