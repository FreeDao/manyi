package com.manyi.ihouse.map.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.DeferredResult;

import com.manyi.ihouse.base.PageResponse;
import com.manyi.ihouse.base.Response;
import com.manyi.ihouse.base.RestController;
import com.manyi.ihouse.map.model.MapRequest;
import com.manyi.ihouse.map.model.MapResponse;
import com.manyi.ihouse.map.model.SubwayLinePoint;
import com.manyi.ihouse.map.model.SubwayLineResponse;
import com.manyi.ihouse.map.model.SubwayResponse;
import com.manyi.ihouse.map.service.MapService;
import com.manyi.ihouse.user.model.HouseBaseModel;

@Controller
@RequestMapping("/Map")
public class MapController extends RestController{
	@Autowired
	@Qualifier("mapService")
	private MapService mapService;
	
	
	/**
	 * 
	 * @param session
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/mapLevelDefine.rest", produces="application/json")
	@ResponseBody
	public DeferredResult<MapResponse> mapLevelDefine() {
		MapResponse response = new MapResponse();
		DeferredResult<MapResponse> dr = new DeferredResult<MapResponse>();
		Map<String,String> map = new HashMap<String,String>();
		map.put("12","1");
		map.put("13","1");
		map.put("14","1");
		map.put("16","2");
		map.put("17","2");
		map.put("18","3");
		map.put("19","3");
		response.setLevelMap(map);
		dr.setResult(response);
		return dr;
	}
	
	
	/**
	 * 
	 * 小区房源列表
	 * @param session
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/houseSummaryListByEstateId.rest", produces="application/json")
	@ResponseBody
	public DeferredResult<PageResponse> houseSummaryListByEstateId(@RequestBody MapRequest request) {
		DeferredResult<PageResponse> dr = new DeferredResult<PageResponse>();
		PageResponse<HouseBaseModel> lists = new PageResponse<HouseBaseModel>();
//		if(request.getLevel() <= 0 ){
//			lists.setMessage("地图层级参数为空 ");
//			dr.setResult(lists);
//			return dr;
//		}
//		if(Math.abs(request.getLat()) <= 0 || Math.abs(request.getLon()) <= 0){
//			lists.setMessage("经纬度参数为空 ");
//			dr.setResult(lists);
//			return dr;
//		}
		if(request.getAreaId() <= 0){
			lists.setMessage("区域ID参数为空");
			dr.setResult(lists);
			return dr;
		}
		
//		if(request.getSequence() <= 0){
//			lists.setMessage("排序参数为空");
//			dr.setResult(lists);
//			return dr;
//		}
		lists = this.mapService.houseSummaryList(request);
		dr.setResult(lists);
		return dr;
	}
	
	/**
	 * 地图可视范围内的房源列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/houseSummaryListByLevel.rest", produces="application/json")
	@ResponseBody
	public DeferredResult<PageResponse> houseSummaryListByLevel(@RequestBody MapRequest request){
		DeferredResult<PageResponse> dr = new DeferredResult<PageResponse>();
		PageResponse<HouseBaseModel> lists = new PageResponse<HouseBaseModel>();
		if(request.getLevel() < 15){
			if( StringUtils.isEmpty(request.getCity())){
				lists.setMessage("level=15，城市名称不可为空 ");
				dr.setResult(lists);
				return dr;
			}
		}
//		if(request.getLevel() > 14){
//			if(Math.abs(request.getLat()) <= 0 || Math.abs(request.getLon()) <= 0){
//				lists.setMessage("level>14，当前点经纬度不可为空");
//				dr.setResult(lists);
//				return dr;
//			}
//		}
		
//		if(request.getSequence() <= 0){
//			lists.setMessage("排序参数为空");
//			dr.setResult(lists);
//			return dr;
//		}
		lists = this.mapService.houseSummaryListByLevel(request);
		dr.setResult(lists);
		return dr;
	}
	
	@RequestMapping(value = "/houseMarkByLevel.rest",produces = "application/json")
	@ResponseBody
	public DeferredResult<Response> houseMarkByLevel(@RequestBody MapRequest request){
		DeferredResult<Response> dr = new DeferredResult<Response>();
		MapResponse response = new MapResponse();
		if(request.getLevel() < 12 || request.getLevel() > 19 ){
			response.setMessage("地图层级超过了界限；地图层级:12-19");
			dr.setResult(response);
			return dr;
		}
		if(request.getLevel() == 12 || request.getLevel() == 13 || request.getLevel() == 14 ){
			if(request.getCity() == null || request.getCity().length() <= 0){
				response.setMessage("当前定位的地区为空");
				dr.setResult(response);
				return dr;
			}
		} 
		if( request.getLevel() > 14){
			if(Math.abs(request.getLat()) <= 0 || Math.abs(request.getLon()) <= 0){
				response.setMessage("经纬度为空");
				dr.setResult(response);
				return dr;
			}
		}
		response = this.mapService.houseMarkByLevel(request);
		dr.setResult(response);
		return dr;
	}
	
	@RequestMapping(value = "/stationsCollectionById.rest",produces = "application/json")
	@ResponseBody
	public DeferredResult<Response> stationsCollectionById(@RequestBody MapRequest request){
		DeferredResult<Response> dr = new DeferredResult<Response>();
		Response r = new Response();
		if(request.getCity() == null || request.getCity().length() <= 0){
			r.setMessage("城市编码为空");
			dr.setResult(r);
			return dr;
		}
		try{
			int i = Integer.parseInt(request.getCity());
		}catch(Exception e){
			r.setMessage("城市编码为整形");
			dr.setResult(r);
			return dr;
		}
		if(request.getSubwayNo() <= 0){
			r.setMessage("地铁编号参数为空");
			dr.setResult(r);
			return dr;
		}
		final SubwayResponse response = this.mapService.stationsCollectionById(request);
		dr.setResult(response);
		return dr;
	}
	
	@RequestMapping(value = "/subwayLineListByCity.rest",produces = "application/json")
	@ResponseBody
	public DeferredResult<PageResponse> subwayLineListByCity(@RequestBody MapRequest request){
		DeferredResult<PageResponse> dr = new DeferredResult<PageResponse>();
		PageResponse<SubwayLineResponse> response = new PageResponse<SubwayLineResponse>();
		int citycode = 0;
		if(request.getCity() == null){
			response.setMessage("城市编号参数为空");
			dr.setResult(response);
			return dr;
		}
		try{
			citycode = Integer.parseInt(request.getCity());
		}catch(Exception e){
			response.setMessage("城市编号参数为空");
			dr.setResult(response);
			return dr;
		}
		dr.setResult(this.mapService.subwayLineListByCity(request));
		return dr;
	}
	
	@RequestMapping(value = "/subwayLinePoint.rest",produces = "application/json")
	@ResponseBody
	public DeferredResult<PageResponse> subwayLinePoint(@RequestBody MapRequest request){
		DeferredResult<PageResponse> dr = new DeferredResult<PageResponse>();
		PageResponse<SubwayLinePoint> response = new PageResponse<SubwayLinePoint>();
		int citycode = 0;
		if(request.getCity() == null){
			response.setMessage("城市编号参数为空");
			dr.setResult(response);
			return dr;
		}
		try{
			citycode = Integer.parseInt(request.getCity());
		}catch(Exception e){
			response.setMessage("城市编号参数错误，城市编号为INT型");
			dr.setResult(response);
			return dr;
		}
		if(request.getSubwayNo() <= 0){
			response.setMessage("地铁编号参数为空");
			dr.setResult(response);
			return dr;
		}
		dr.setResult(this.mapService.subwayLinePoint(request));
		return dr;
	}
	
	@RequestMapping(value = "/ArroundEstateMarkBySubwayLine.rest",produces = "application/json")
	@ResponseBody
	public DeferredResult<MapResponse> ArroundEstateMarkBySubwayLine(@RequestBody MapRequest request){
		int citycode = 0;
		MapResponse response = new MapResponse();
		DeferredResult<MapResponse> dr = new DeferredResult<MapResponse>();
		if(request.getCity() == null){
			response.setMessage("城市编号参数为空");
			dr.setResult(response);
			return dr;
		}
		try{
			citycode = Integer.parseInt(request.getCity());
		}catch(Exception e){
			response.setMessage("城市编号参数错误，城市编号为INT型");
			dr.setResult(response);
			return dr;
		}
		if(request.getSubwayNo() <= 0){
			response.setMessage("地铁编号参数为空");
			dr.setResult(response);
			return dr;
		}
		if(Math.abs(request.getLat()) <0 || Math.abs(request.getLon()) < 0){
			response.setMessage("当前点的经纬度为空");
			dr.setResult(response);
			return dr;
		}
		response = this.mapService.ArroundEstateMarkBySubwayLine(request);
		dr.setResult(response);
		return dr;
	}
	
	
}
