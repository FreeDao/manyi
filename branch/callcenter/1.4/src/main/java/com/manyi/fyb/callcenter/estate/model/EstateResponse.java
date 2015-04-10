package com.manyi.fyb.callcenter.estate.model;

import java.util.List;

import com.manyi.fyb.callcenter.base.model.Response;

/**
 * @date 2014年4月16日 下午1:48:09
 * @author Tom
 * @description
 * 
 */
public class EstateResponse extends Response{

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
	private String estateId;

	/**
	 * 小区名称
	 */
	private String estateName;
	/**
	 * 小区地址
	 */
	private String estateRoad;

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
	
	
	private List<EstateMsgRes> EstateMsgResList;
	
	
	

	public List<EstateMsgRes> getEstateMsgResList() {
		return EstateMsgResList;
	}

	public void setEstateMsgResList(List<EstateMsgRes> estateMsgResList) {
		EstateMsgResList = estateMsgResList;
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

	public String getEstateId() {
		return estateId;
	}

	public void setEstateId(String estateId) {
		this.estateId = estateId;
	}

	public String getEstateName() {
		return estateName;
	}

	public void setEstateName(String estateName) {
		this.estateName = estateName;
	}

	public String getEstateRoad() {
		return estateRoad;
	}

	public void setEstateRoad(String estateRoad) {
		this.estateRoad = estateRoad;
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

	public static class EstateMsgRes {

		private String query;
		private List<String> suggestions;

		public List<String> getSuggestions() {
			return suggestions;
		}

		public void setSuggestions(List<String> suggestions) {
			this.suggestions = suggestions;
		}

		public String getQuery() {
			return this.query;
		}

		public void setQuery(String query) {
			this.query = query;
		}

	}

}
