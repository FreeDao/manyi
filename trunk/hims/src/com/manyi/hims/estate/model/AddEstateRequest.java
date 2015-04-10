/**
 * 
 */
package com.manyi.hims.estate.model;

/**
 * @author zxc
 *
 */
public class AddEstateRequest {

	/**
	 * 小区名称
	 */
	private String estateName;
	/**
	 * 板块
	 */
	private int townId;
	/**
	 * 小区id
	 */
	private int areaId;
	/**
	 * 小区地址
	 */
	private String areaRoad;
	
	public String getEstateName() {
		return estateName;
	}
	
	public void setEstateName(String estateName) {
		this.estateName = estateName;
	}
	
	public int getTownId() {
		return townId;
	}
	
	public void setTownId(int townId) {
		this.townId = townId;
	}
	
	public int getAreaId() {
		return areaId;
	}
	
	public void setAreaId(int areaId) {
		this.areaId = areaId;
	}
	
	public String getAreaRoad() {
		return areaRoad;
	}
	
	public void setAreaRoad(String areaRoad) {
		this.areaRoad = areaRoad;
	}
}
