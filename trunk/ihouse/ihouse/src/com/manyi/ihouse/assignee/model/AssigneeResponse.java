package com.manyi.ihouse.assignee.model;

import com.manyi.ihouse.base.Response;

public class AssigneeResponse extends Response {

	/**
	 * ID
	 */
	private int id;
	
	/**
	 * 经纪人姓名
	 */
	private String name;
	
	/**
	 * 经纪人编号
	 */
	private String serialNumber;
	
	/**
	 * 手机号码
	 */
	private String mobile;
	
	/**
	 * 备注
	 */
	private String information;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getInformation() {
		return information;
	}

	public void setInformation(String information) {
		this.information = information;
	}

}
