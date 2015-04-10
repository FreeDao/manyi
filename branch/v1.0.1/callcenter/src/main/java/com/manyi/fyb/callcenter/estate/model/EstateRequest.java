package com.manyi.fyb.callcenter.estate.model;

/**
 * @date 2014年4月15日 下午2:50:17
 * @author Tom
 * @description 小区请求相关
 */
public class EstateRequest {
	/**
	 * 区域
	 */
	private String cityId;
	
	/**
	 * 小区名称
	 */
	private String estateName;
	/**
	 * 板块
	 */
	private String townId;
	/**
	 * 主小区id
	 */
	private String parentAreaId;
	/**
	 * 小区id
	 */
	private String areaId;

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

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getTownId() {
		return townId;
	}

	public void setTownId(String townId) {
		this.townId = townId;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public String getParentAreaId() {
		return parentAreaId;
	}

	public void setParentAreaId(String parentAreaId) {
		this.parentAreaId = parentAreaId;
	}

	public String getAreaRoad() {
		return areaRoad;
	}

	public void setAreaRoad(String areaRoad) {
		this.areaRoad = areaRoad;
	}

}
