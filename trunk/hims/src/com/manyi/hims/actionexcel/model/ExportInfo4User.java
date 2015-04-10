package com.manyi.hims.actionexcel.model;

import org.apache.commons.lang.StringUtils;

/**
 * @author zxc
 * 
 * update by Tom 2014-5-30 12:38
 * 
 */
public class ExportInfo4User {

	private String houseId;
	private String estateName;
	private String building;
	private String room;
	
	private String currentHouseState;
	private String hostMobile;
	private String agentName;
	private String agentMobile;
	private String publishDate;

	private String publishHouseState;
	private String state;

	public String getHouseId() {
		return format4Null(houseId);
	}

	public void setHouseId(String houseId) {
		this.houseId = houseId;
	}

	public String getAgentName() {
		return format4Null(agentName);
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public String getAgentMobile() {
		return format4Null(agentMobile);
	}

	public void setAgentMobile(String agentMobile) {
		this.agentMobile = agentMobile;
	}

	public String getHostMobile() {
		return format4Null(hostMobile);
	}

	public void setHostMobile(String hostMobile) {
		this.hostMobile = hostMobile;
	}

	public String getEstateName() {
		return format4Null(estateName);
	}

	public void setEstateName(String estateName) {
		this.estateName = estateName;
	}

	public String getBuilding() {
		return format4Null(building);
	}

	public void setBuilding(String building) {
		this.building = building;
	}

	public String getRoom() {
		return format4Null(room);
	}

	public void setRoom(String room) {
		this.room = room;
	}
	
	private String format4Null(Object obj) {
		return obj == null ? StringUtils.EMPTY : obj + StringUtils.EMPTY;
	}

	public String getCurrentHouseState() {
		return currentHouseState;
	}

	public void setCurrentHouseState(String currentHouseState) {
		this.currentHouseState = currentHouseState;
	}

	public String getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(String publishDate) {
		this.publishDate = publishDate;
	}

	public String getPublishHouseState() {
		return publishHouseState;
	}

	public void setPublishHouseState(String publishHouseState) {
		this.publishHouseState = publishHouseState;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
	
}
