package com.manyi.ihouse.map.service;

import com.manyi.ihouse.base.PageResponse;
import com.manyi.ihouse.map.model.MapRequest;
import com.manyi.ihouse.map.model.MapResponse;
import com.manyi.ihouse.map.model.SubwayLinePoint;
import com.manyi.ihouse.map.model.SubwayLineResponse;
import com.manyi.ihouse.map.model.SubwayResponse;
import com.manyi.ihouse.user.model.HouseBaseModel;

public interface MapService {
	/**
	 * 地图层级和当前经纬度获取房屋数量
	 * @param reqest
	 * @return
	 */
	public MapResponse houseMarkByLevel(MapRequest request);
	
	public PageResponse<HouseBaseModel> houseSummaryListByLevel(MapRequest request);
	
	/**
	 * 小区经纬度得到列表
	 * @param request
	 * @return
	 */
	public PageResponse<MapResponse> houseMarkByZone(MapRequest request);
	
	
	/**
	 * Gets HouseSummaryResponse collection from houseId of MapRequest
	 * @param request
	 * @return
	 */
	public PageResponse<HouseBaseModel> houseSummaryList(MapRequest request);
	
	/**
	 * 城市地铁列表
	 * @param request
	 * @return
	 */
	public PageResponse<SubwayLineResponse> subwayLineListByCity(MapRequest request);
	
	/**
	 * 地铁站台列表
	 * @param request
	 * @return
	 */
	public SubwayResponse stationsCollectionById(MapRequest request);
	
	/**
	 * 地铁站点 描点
	 * @param request
	 * @return
	 */
	public PageResponse<SubwayLinePoint> subwayLinePoint(MapRequest request);
	
	/**
	 * 地铁附近房源
	 * @param request
	 * @return
	 */
	public MapResponse ArroundEstateMarkBySubwayLine(MapRequest request);  
	
}
