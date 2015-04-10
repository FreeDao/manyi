/**
 * 
 */
package com.manyi.hims.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.DeferredResult;

import com.manyi.hims.Response;
import com.manyi.hims.RestController;
import com.manyi.hims.test.service.TestService;

/**
 * @author lei
 *
 */
@Controller
@RequestMapping("/rest/test")
public class TestRestController extends RestController {
	
	private TestService testService;
	public TestService getTestService() {
		return testService;
	}

	@Autowired
	@Qualifier("testService")
	public void setTestService(TestService testService) {
		this.testService = testService;
	}

	
	
	
	
	@RequestMapping(value = "/sellExamineThrough.rest", produces="application/json")
	@ResponseBody
	public DeferredResult<Response> sellExamineThrough(@RequestBody SellExamineRequest params) {
		
		testService.sellExamineThrough(params.getHouseId());
		DeferredResult<Response> dr = new DeferredResult<Response>();
		dr.setResult(new Response());
		return dr;
	}
	
	@RequestMapping(value = "/rentExamineThrough.rest", produces="application/json")
	@ResponseBody
	public DeferredResult<Response> rentExamineThrough(@RequestBody SellExamineRequest params) {
		
		testService.rentExamineThrough(params.getHouseId());
		DeferredResult<Response> dr = new DeferredResult<Response>();
		dr.setResult(new Response());
		return dr;
	}
	
	
	@RequestMapping(value = "/addArea.rest", produces="application/json")
	@ResponseBody
	public DeferredResult<Response> addArea() {
		
		testService.addArea();
		DeferredResult<Response> dr = new DeferredResult<Response>();
		dr.setResult(new Response());
		return dr;
	}
	
	@RequestMapping(value = "/snedSMS.rest", produces="application/json")
	@ResponseBody
	public DeferredResult<Response> test() {
		
		testService.sendSMS();
		DeferredResult<Response> dr = new DeferredResult<Response>();
		dr.setResult(new Response());
		return dr;
	}
	public static class SellExamineRequest{
		private String[] houseId;

		public String[] getHouseId() {
			return houseId;
		}

		public void setHouseId(String[] houseId) {
			this.houseId = houseId;
		}


	}


	
	
	
	
}