package com.manyi.hims.estate.model;

import com.manyi.hims.Response;

/**
 * @date 2014年4月16日 下午1:48:09
 * @author Tom
 * @description
 * 
 */
public class EstateResponse extends Response {

	/**
	 * 区域名称
	 */
	private String cityName;
	/**
	 * 板块
	 */
	private String townName;
	/**
	 * 小区id
	 */
	private String areaId;

	/**
	 * 小区名称
	 */
	private String areaName;
	/**
	 * 小区地址
	 */
	private String areaRoad;

	/**
	 * 房源总数
	 */
	private String houseNum;

	/**
	 * 出售房源：
	 */
	private String sellHouseNum;

	/**
	 * 出租房源：
	 */
	private String rentNum;

	/**
	 * 审核状态
	 */
	private String sourceState;

	/**
	 * 发布人：
	 */
	private String createUser;

	private String estateNameStr;

	public EstateResponse() {
		super();
	}

	public EstateResponse(int i, String string) {
		super(i, string);
	}

	public String getEstateNameStr() {
		return estateNameStr;
	}

	public void setEstateNameStr(String estateNameStr) {
		this.estateNameStr = estateNameStr;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getTownName() {
		return townName;
	}

	public void setTownName(String townName) {
		this.townName = townName;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getAreaRoad() {
		return areaRoad;
	}

	public void setAreaRoad(String areaRoad) {
		this.areaRoad = areaRoad;
	}

	public String getHouseNum() {
		return houseNum;
	}

	public void setHouseNum(String houseNum) {
		this.houseNum = houseNum;
	}

	public String getSellHouseNum() {
		return sellHouseNum;
	}

	public void setSellHouseNum(String sellHouseNum) {
		this.sellHouseNum = sellHouseNum;
	}

	public String getRentNum() {
		return rentNum;
	}

	public void setRentNum(String rentNum) {
		this.rentNum = rentNum;
	}

	public String getSourceState() {
		return sourceState;
	}

	public void setSourceState(String sourceState) {
		this.sourceState = sourceState;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
}
