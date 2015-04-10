package com.manyi.ihouse.research.model;

import java.util.Date;

public class SearchRequest {
	// 选择的城市
		private String city;
		// 搜索关键字
		private String key;
		// 最低价
		private long lowPrice;
		// 最高价
		private long highPrice;
		// 卧室
		private int room;
		// 装修
		private int decorate;
		// 入住时间
		private Date inhouse;
		//搜索类型     地图搜索：1   列表搜索：2
//		private int searchType;
		
		private int offset;
		
		private int pageSize = 20;
		
		private long userId;

		private int sequence = 1;
		
		public String getCity() {
			return city;
		}


		public void setCity(String city) {
			this.city = city;
		}


		public String getKey() {
			return key;
		}


		public void setKey(String key) {
			this.key = key;
		}


		public long getLowPrice() {
			return lowPrice;
		}


		public void setLowPrice(long lowPrice) {
			this.lowPrice = lowPrice;
		}


		public long getHighPrice() {
			return highPrice;
		}


		public void setHighPrice(long highPrice) {
			this.highPrice = highPrice;
		}


		public int getRoom() {
			return room;
		}


		public void setRoom(int room) {
			this.room = room;
		}


		public int getDecorate() {
			return decorate;
		}


		public void setDecorate(int decorate) {
			this.decorate = decorate;
		}


		public Date getInhouse() {
			return inhouse;
		}


		public void setInhouse(Date inhouse) {
			this.inhouse = inhouse;
		}


//		public int getSearchType() {
//			return searchType;
//		}
//
//
//		public void setSearchType(int searchType) {
//			this.searchType = searchType;
//		}


		public int getOffset() {
			return offset;
		}


		public void setOffset(int offset) {
			this.offset = offset;
		}


		public int getPageSize() {
			return pageSize;
		}


		public void setPageSize(int pageSize) {
			this.pageSize = pageSize;
		}


		public long getUserId() {
			return userId;
		}


		public void setUserId(long userId) {
			this.userId = userId;
		}


		public int getSequence() {
			return sequence;
		}


		public void setSequence(int sequence) {
			this.sequence = sequence;
		}
}
