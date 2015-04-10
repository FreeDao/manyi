/**
 * 
 */
package com.manyi.hims.actionexcel.model;

import org.apache.commons.lang.StringUtils;

/**
 * @author zxc select
 * 
 *         re.houseId as houseId,re.hostName as hostName,re.hostMobile as hostMobile, max(re.createTime) as createTime,re.estateName as
 *         estateName,re.road as road,re.building as building,re.floor as floor,re.room as room
 */
public class ExportUserInfo {

	private String houseId;
	private String hostName;
	private String hostMobile;
	private String createTime;
	private String estateName;
	private String road;
	private String building;
	private String floor;
	private String room;

	public String getHouseId() {
		return format4Null(houseId);
	}

	public void setHouseId(String houseId) {
		this.houseId = houseId;
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
