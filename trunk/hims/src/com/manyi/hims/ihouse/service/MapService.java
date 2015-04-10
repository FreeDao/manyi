package com.manyi.hims.ihouse.service;

import com.manyi.hims.PageResponse;
import com.manyi.hims.ihouse.model.HouseSummaryResponse;
import com.manyi.hims.ihouse.model.MapRequest;
import com.manyi.hims.ihouse.model.MapResponse;

public interface MapService {
	/**
	 * 地图层级和当前经纬度获取房屋数量
	 * @param reqest
	 * @return
	 */
	public MapResponse houseMarkByLevel(MapRequest reqest);
	
	/**
	 * 小区经纬度得到列表
	 * @param request
	 * @return
	 */
	public PageResponse<MapResponse> houseMarkByZone(MapRequest request);
	
	/**
	 * 地铁沿线列表
	 * @param request
	 * @return
	 */
//	public SubwayResponse houseListBySubway(SubwayRequest request);
	
	/**
	 * Gets HouseSummaryResponse collection from houseId of MapRequest
	 * @param request
	 * @return
	 */
	public PageResponse<HouseSummaryResponse> houseSummaryList(MapRequest request);
	
}
