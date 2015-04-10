package com.manyi.hims.rollpoling.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.DeferredResult;

import com.manyi.hims.Response;
import com.manyi.hims.rollpoling.service.RollPolingService;

@Controller
@RequestMapping ("/rollpoling")
public class RollPolingController {
	
	@Autowired
	@Qualifier("rollPolingService")
	private RollPolingService rollPolingService;
	
	@RequestMapping(value="/check.rest", produces="application/json")
	@ResponseBody
	public DeferredResult<Response> check(){
		Response response =  rollPolingService.check();
		DeferredResult<Response> dr = new DeferredResult<Response>();
		dr.setResult(response);
		return dr;
	}
	
	@RequestMapping (value="/overdue.reset")
	@ResponseBody
	public DeferredResult<Response> overdue(){
		Response response =  rollPolingService.overdue();
		DeferredResult<Response> dr = new DeferredResult<Response>();
		dr.setResult(response);
		return dr;
	}
}
