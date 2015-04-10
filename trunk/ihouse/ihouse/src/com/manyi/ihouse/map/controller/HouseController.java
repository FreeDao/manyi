package com.manyi.ihouse.map.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.DeferredResult;

import com.manyi.ihouse.base.Response;
import com.manyi.ihouse.base.RestController;
import com.manyi.ihouse.map.model.HouseDetailResponse;
import com.manyi.ihouse.map.model.HouseRequest;
import com.manyi.ihouse.map.model.LookHouseRequest;
import com.manyi.ihouse.map.model.ZoneDetailResponse;
import com.manyi.ihouse.map.service.HouseService;

@Controller
@RequestMapping("/House")
public class HouseController extends RestController{

	@Autowired
	@Qualifier("houseInfoService")
	private HouseService houseService;
	
	@RequestMapping(value = "/houseDetailService.rest", produces="application/json")
	@ResponseBody
	public DeferredResult<HouseDetailResponse> houseDetailService(@RequestBody HouseRequest request){
		DeferredResult<HouseDetailResponse> dr = new DeferredResult<HouseDetailResponse>();
		HouseDetailResponse response = new HouseDetailResponse();
		if(!StringUtils.hasLength(request.getHouseId() + "")){
//			response.setErrorCode();
			response.setMessage("房屋ID为空");
			dr.setResult(response);
			return dr;
		}
		if(!StringUtils.hasLength(request.getEsateId() + "")){
//			response.setErrorCode();
			response.setMessage("小区ID为空");
			dr.setResult(response);
			return dr;
		}
//		if(!StringUtils.hasLength(request.getLat()) && StringUtils.hasLength(request.getLon()) ){
//			response.setErrorCode();
//			response.setMessage("当前经纬度为空");
//			dr.setResult(response);
//			return dr;
//		}
		response = this.houseService.houseDetailService(request);
		dr.setResult(response);
		return dr;
	}
	
	@RequestMapping(value = "/lookHouseService.rest", produces="application/json")
	@ResponseBody
	public DeferredResult<Response> lookHouseService(@RequestBody LookHouseRequest request){
		Response response = new Response();
		DeferredResult<Response> dr = new DeferredResult<Response>();
		if(Math.abs(request.getUserId()) <= 0){
			response.setMessage("用户ID为空");
			dr.setResult(response);
			return dr;
		}
		if(Math.abs(request.getHouseId()) <= 0){
			response.setMessage("房屋ID为空");
			dr.setResult(response);
			return dr;
		}
		response = this.houseService.lookHouseService(request);
		dr.setResult(response);
		return dr;
	}
	
	@RequestMapping(value = "/zoneDetailService.rest", produces="application/json")
	@ResponseBody
	public DeferredResult<ZoneDetailResponse> zoneDetailService(@RequestBody HouseRequest request){
		DeferredResult<ZoneDetailResponse> dr = new DeferredResult<ZoneDetailResponse>();
		ZoneDetailResponse response = new ZoneDetailResponse();
		if(Math.abs(request.getEsateId()) < 0){
			response.setMessage("小区ID不能为空");
			dr.setResult(response);
			return dr;
		}
		response = this.houseService.zoneDetailService(request);
		dr.setResult(response);
		return dr;
	}
	
}
