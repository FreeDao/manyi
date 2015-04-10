package com.manyi.ihouse.map.service;

import com.manyi.ihouse.base.Response;
import com.manyi.ihouse.map.model.HouseDetailResponse;
import com.manyi.ihouse.map.model.HouseRequest;
import com.manyi.ihouse.map.model.LookHouseRequest;
import com.manyi.ihouse.map.model.ZoneDetailResponse;

public interface HouseService {
	//房源详情
	public HouseDetailResponse houseDetailService(HouseRequest request);
	//"我要看房"service
	public Response lookHouseService(LookHouseRequest request);
	//小区详情
	public ZoneDetailResponse zoneDetailService(HouseRequest request);
}
