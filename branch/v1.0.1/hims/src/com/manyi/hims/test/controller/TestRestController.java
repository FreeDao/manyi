/**
 * 
 */
package com.manyi.hims.test.controller;

import java.io.File;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.request.async.DeferredResult;

import com.leo.jackson.FileDeserializer;
import com.manyi.hims.Global;
import com.manyi.hims.Response;
import com.manyi.hims.RestController;
import com.manyi.hims.entity.Residence;
import com.manyi.hims.entity.User;
import com.manyi.hims.test.service.TestService;
import com.manyi.hims.uc.service.UserService;
import com.manyi.hims.uc.service.UserService.MyAccountResponse;
import com.manyi.hims.uc.service.UserService.ReportResponse;
import com.manyi.hims.uc.service.UserService.UserInfo;

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
	
	
	@RequestMapping(value = "/test.rest", produces="application/json")
	@ResponseBody
	public DeferredResult<Response> test(@RequestBody SellExamineRequest params) {
		
		testService.addArea();
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