package com.manyi.hims.sourcelog;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.manyi.hims.Response;

public class SourceLogResponse extends Response {
	
	private int historyId;
	private int houseId;
	private int estateId;// 小区id
	private String estateName;// 小区name
	private String building;// 楼座编号（例如：22栋，22坐，22号）
	private String room;// 房号（例如：1304室，1004－1008室等）
	private Date publishDate;// 发布时间
	private String publishStr;

	private int sourceState;// 审核状态(1 审核成功 2 审核中 3 审核失败)
	private String sourceStateStr;// 审核状态,文本 (1 审核成功 2 审核中 3 审核失败)

	private int typeId;// 发布记录 类型ID
	private String typeName;// 发布记录 类型(1.发布出售0.发布出租2.改盘3.举报6.新增小区)

	private Long markTime;// 时间戳
	private String returnMoney;// 返回10元

	public String getReturnMoney() {
		return returnMoney;
	}

	public void setPublishStr(String publishStr) {
		this.publishStr = publishStr;
	}

	public void setReturnMoney(String returnMoney) {
		this.returnMoney = returnMoney;
	}

	public int getHistoryId() {
		return historyId;
	}

	public void setHistoryId(int historyId) {
		this.historyId = historyId;
	}

	public int getHouseId() {
		return houseId;
	}

	public void setHouseId(int houseId) {
		this.houseId = houseId;
	}

	public Long getMarkTime() {
		return markTime;
	}

	public void setMarkTime(Long markTime) {
		this.markTime = markTime;
	}

	public String getSourceStateStr() {
		return sourceStateStr;
	}

	public void setSourceStateStr(String sourceStateStr) {
		this.sourceStateStr = sourceStateStr;
	}

	public int getTypeId() {
		return typeId;
	}

	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
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

	public String getBuilding() {
		return building;
	}

	public void setBuilding(String building) {
		this.building = building;
	}

	public String getRoom() {
		return room;
	}

	public void setRoom(String room) {
		this.room = room;
	}

	public Date getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
		this.publishStr = new SimpleDateFormat("yyyy-MM-dd").format(publishDate);
	}

	public String getPublishStr() {
		return publishStr;
	}

	public int getSourceState() {
		return sourceState;
	}

	public void setSourceState(int sourceState) {
		switch (sourceState) {
		case 2:
			this.sourceStateStr = "审核中";
			break;
		case 1:
			this.sourceStateStr = "审核成功";
			break;
		case 3:
			this.sourceStateStr = "审核失败";
			break;
		}
		this.sourceState = sourceState;
	}
}
