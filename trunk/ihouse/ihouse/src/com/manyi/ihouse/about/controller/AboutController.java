package com.manyi.ihouse.about.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.DeferredResult;

import com.manyi.ihouse.about.model.MobilePropertyRequest;
import com.manyi.ihouse.about.service.AboutService;
import com.manyi.ihouse.base.Response;

@Controller
@RequestMapping("/About")
public class AboutController {
	
	@Autowired
	@Qualifier("aboutService")
	private AboutService aboutService;
	
	@RequestMapping(value = "/MobileInfo.rest",produces = "application/json")
	@ResponseBody
	public DeferredResult<Response> mobileInfo(@RequestBody MobilePropertyRequest request){
		//request.getLineNo() 
		DeferredResult<Response> dr = new DeferredResult<Response>();
		Response response = aboutService.mobilePropertyService(request);//.houseListBySubway(request);
		
//		SubwayResponse  resp = HttpClientHelper.sendRestJsonShortObj2Obj("/rest/Map//houseMarkBySubway.rest", request,SubwayResponse.class);
		dr.setResult(response);
		return dr;
	}
}
