package com.manyi.hims.ihouse.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.DeferredResult;

import com.manyi.hims.PageResponse;
import com.manyi.hims.RestController;
import com.manyi.hims.ihouse.model.HouseSummaryResponse;
import com.manyi.hims.ihouse.model.MapRequest;
import com.manyi.hims.ihouse.service.MapService;

@Controller
@RequestMapping("/rest/Map")
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
	@SuppressWarnings({ "rawtypes" })
	@RequestMapping(value = "/houseSummaryList.rest", produces="application/json")
	@ResponseBody
	public DeferredResult<PageResponse> houseSummaryList(@RequestBody MapRequest request) {
		final PageResponse<HouseSummaryResponse> lists = this.mapService.houseSummaryList(request);
		DeferredResult<PageResponse> dr = new DeferredResult<PageResponse>();
		dr.setResult(lists);
		return dr;
	}
	
	
}
