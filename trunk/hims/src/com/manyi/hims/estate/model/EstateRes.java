package com.manyi.hims.estate.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class EstateRes {
		private int logId;// 记录id
		private int cityId;
		private String cityName;
		private int areaId;// 区域
		private String areaName;
		private int townId;// 片区
		private String townName;
		private int estateId;// 小区
		private String estateName;
		private String road;
		private Date publishDate;// 发布 出售时间
		private String publishDateStr;
		private int sourceState;// 审核状态
		private String sourceStateStr;
		private int publishId;// 发布人
		private String publishName;
		private int sellNum;// 在售数量
		private int rentNum;// 在租数量
		private int houseNum;// 房源数量

		public int getCityId() {
			return cityId;
		}

		public void setCityId(int cityId) {
			this.cityId = cityId;
		}

		public String getCityName() {
			return cityName;
		}

		public void setCityName(String cityName) {
			this.cityName = cityName;
		}

		public String getRoad() {
			return road;
		}

		public void setRoad(String road) {
			this.road = road;
		}

		public int getSellNum() {
			return sellNum;
		}

		public void setSellNum(int sellNum) {
			this.sellNum = sellNum;
		}

		public int getRentNum() {
			return rentNum;
		}

		public void setRentNum(int rentNum) {
			this.rentNum = rentNum;
		}

		public int getHouseNum() {
			return houseNum;
		}

		public void setHouseNum(int houseNum) {
			this.houseNum = houseNum;
		}

		public Date getPublishDate() {
			return publishDate;
		}

		public void setPublishDate(Date publishDate) {
			this.publishDate = publishDate;
			try {
				if(publishDate != null){
					this.publishDateStr = new SimpleDateFormat("yyyy-MM-dd").format(publishDate);
				}else{
					this.publishDateStr="";
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		public int getSourceState() {
			return sourceState;
		}

		public void setSourceState(int sourceState) {
			// 审核状态 1 审核通过 2 审核中 3 审核失败
			this.sourceState = sourceState;
			switch (sourceState) {
			case 1:
				this.sourceStateStr = "审核成功";
				break;
			case 2:
				this.sourceStateStr = "未审核";
				break;
			case 3:
				this.sourceStateStr = "审核失败";
				break;

			default:
				break;
			}
		}

		public int getPublishId() {
			return publishId;
		}

		public void setPublishId(int publishId) {
			this.publishId = publishId;
		}

		public String getPublishDateStr() {
			return publishDateStr;
		}

		public String getSourceStateStr() {
			return sourceStateStr;
		}

		public int getAreaId() {
			return areaId;
		}

		public void setAreaId(int areaId) {
			this.areaId = areaId;
		}

		public String getAreaName() {
			return areaName;
		}

		public void setAreaName(String areaName) {
			this.areaName = areaName;
		}

		public int getTownId() {
			return townId;
		}

		public void setTownId(int townId) {
			this.townId = townId;
		}

		public String getTownName() {
			return townName;
		}

		public void setTownName(String townName) {
			this.townName = townName;
		}

		public int getEstateId() {
			return estateId;
		}

		public void setEstateId(int estateId) {
			this.estateId = estateId;
		}

		public String getEstateName() {
			return estateName;
		}

		public void setEstateName(String estateName) {
			this.estateName = estateName;
		}

		public int getLogId() {
			return logId;
		}

		public void setLogId(int logId) {
			this.logId = logId;
		}

		public String getPublishName() {
			return publishName;
		}

		public void setPublishName(String publishName) {
			this.publishName = publishName;
		}

	}
