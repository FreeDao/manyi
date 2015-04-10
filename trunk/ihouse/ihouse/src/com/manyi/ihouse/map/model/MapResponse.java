package com.manyi.ihouse.map.model;

import java.util.List;
import java.util.Map;

import com.manyi.ihouse.base.Response;

/**
 * 地图Response
 * @author shenyamin
 *
 */
public class MapResponse extends Response{
	
	private int type; //区域类型
	
	private int allNum; //总房源数量
	
	private List<MapMarkModel> markList; //该区域内 子区域房源信息列表
	
	private Map<String,String> levelMap;

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

	public Map<String, String> getLevelMap() {
		return levelMap;
	}

	public void setLevelMap(Map<String, String> levelMap) {
		this.levelMap = levelMap;
	}
	
	
}
