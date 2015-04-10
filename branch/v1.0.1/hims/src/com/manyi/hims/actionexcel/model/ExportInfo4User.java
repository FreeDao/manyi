/**
 * 
 */
package com.manyi.hims.actionexcel.model;

import org.apache.commons.lang.StringUtils;

/**
 * @author zxc
 * 
 *         select agent.uid as uid,agent.realName as agentName,agent.mobile as agentMobile,hc.hostName as hostName,hc.hostMobile as
 *         hostMobile, hr.createTime as createTime,a.name estateName,a.road as road,h.building as building,h.floor as floor,h.room as room
 * 
 */
public class ExportInfo4User {

	private String uid;
	private String agentName;
	private String agentMobile;
	private String hostName;
	private String hostMobile;
	private String createTime;
	private String estateName;
	private String road;
	private String building;
	private String floor;
	private String room;

	public String getUid() {
		return format4Null(uid);
	}

	public void setUid(String uid) {
		this.uid = uid;
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

	public String getHostName() {
		return format4Null(hostName);
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public String getHostMobile() {
		return format4Null(hostMobile);
	}

	public void setHostMobile(String hostMobile) {
		this.hostMobile = hostMobile;
	}

	public String getCreateTime() {
		return format4Null(createTime);
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getEstateName() {
		return format4Null(estateName);
	}

	public void setEstateName(String estateName) {
		this.estateName = estateName;
	}

	public String getRoad() {
		return format4Null(road);
	}

	public void setRoad(String road) {
		this.road = road;
	}

	public String getBuilding() {
		return format4Null(building);
	}

	public void setBuilding(String building) {
		this.building = building;
	}

	public String getFloor() {
		return format4Null(floor);
	}

	public void setFloor(String floor) {
		this.floor = floor;
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
}
