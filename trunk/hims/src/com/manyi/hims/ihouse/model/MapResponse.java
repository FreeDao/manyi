package com.manyi.hims.ihouse.model;

import java.util.List;

import com.manyi.hims.Response;

/**
 * 地图Response
 * @author shenyamin
 *
 */
public class MapResponse extends Response{
	
	private int type; //区域类型
	
	private int allNum; //总房源数量
	
	private List<MapMarkModel> markList; //该区域内 子区域房源信息列表

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getAllNum() {
		return allNum;
	}

	public void setAllnum(int allNum) {
		this.allNum = allNum;
	}

	public List<MapMarkModel> getMarkList() {
		return markList;
	}

	public void setMarkList(List<MapMarkModel> markList) {
		this.markList = markList;
	}
	
	
}
