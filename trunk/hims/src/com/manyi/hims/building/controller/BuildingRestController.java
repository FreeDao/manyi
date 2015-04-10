package com.manyi.hims.building.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.DeferredResult;

import com.manyi.hims.PageResponse;
import com.manyi.hims.Response;
import com.manyi.hims.RestController;
import com.manyi.hims.building.model.BuildingReq;
import com.manyi.hims.building.model.BuildingRes;
import com.manyi.hims.building.service.BuildingService;
import com.manyi.hims.util.CommonUtils;

/**
 * 小区 管理模块
 * 
 * @author tiger
 * 
 */
@Controller
@RequestMapping("/rest/building")
public class BuildingRestController extends RestController {

	@Autowired
	@Qualifier("buildingService")
	private BuildingService buildingService;

	/**
	 * 楼栋 信息 列表
	 * 
	 * @param session
	 * @param req
	 * @return
	 */
	@SuppressWarnings({ "rawtypes" })
	@RequestMapping(value = "/buildingList.rest", produces = "application/json")
	@ResponseBody
	public DeferredResult<PageResponse> buildingList(HttpSession session, @RequestBody BuildingReq req) {
		final PageResponse<BuildingRes> lists = this.buildingService.buildingList(req);
		DeferredResult<PageResponse> dr = new DeferredResult<PageResponse>();
		dr.setResult(lists);
		return dr;
	}
	
	/**
	 * 编辑楼栋
	 * 
	 * @param session
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/editBuilding.rest", produces = "application/json")
	@ResponseBody
	public DeferredResult<Response> editBuilding(HttpSession session, @RequestBody BuildingRes req) {
		Response response = buildingService.editBuilding(req);
		return CommonUtils.deferredResult(response);
	}
	
	/**
	 * 编辑楼栋 时 , 加载 楼栋 页面
	 * 
	 * @param session
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/findBuildingById.rest", produces = "application/json")
	@ResponseBody
	public DeferredResult<Response> findBuildingById(HttpSession session, @RequestBody BuildingReq req) {
		BuildingRes response = buildingService.findBuildingById(req.getId());//楼栋Id
		DeferredResult<Response> dr = new DeferredResult<Response>();
		dr.setResult(response);
		return dr;
	}
	
}
