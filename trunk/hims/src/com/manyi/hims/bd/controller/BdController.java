package com.manyi.hims.bd.controller;

import java.math.BigDecimal;

import lombok.Data;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.DeferredResult;

import com.manyi.hims.Response;
import com.manyi.hims.RestController;
import com.manyi.hims.bd.service.EmployeeService;
import com.manyi.hims.bd.service.EmployeeService.BdLoginResponse;
import com.manyi.hims.bd.service.EmployeeService.TaskDetailsResponse;
@Controller
@RequestMapping("/rest/bd")
public class BdController extends RestController{
	
	private Logger log = LoggerFactory.getLogger(BdController.class);
	
	@Autowired
	private EmployeeService employeeService;
	public EmployeeService getEmployeeService() {
		return employeeService;
	}

	public void setEmployeeService(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}
	

	@RequestMapping(value = "/bdLogin.rest", produces="application/json")
	@ResponseBody
	public DeferredResult<Response> bdLogin(@RequestBody DbLoginRequest pars) {
		log.info(JSONObject.fromObject(pars).toString());

		BdLoginResponse result = this.employeeService.login(pars.getUserName(), pars.getPasswrod());
		
		log.info(JSONObject.fromObject(result).toString());

		DeferredResult<Response> dr = new DeferredResult<Response>();
		dr.setResult(result);
		return dr;
	}
	
	@RequestMapping(value = "/taskDetails.rest", produces="application/json")
	@ResponseBody
	public DeferredResult<Response> taskDetails(@RequestBody TaskDetailsRequest pars) {
		log.info(JSONObject.fromObject(pars).toString());
		TaskDetailsResponse result = this.employeeService.taskDetails(pars.getTaskId());
		log.info(JSONObject.fromObject(result).toString());

		DeferredResult<Response> dr = new DeferredResult<Response>();
		dr.setResult(result);
		return dr;
	}
	@Data
	public static class TaskDetailsRequest{
		private int taskId;
	}
	@Data
	public static class DbLoginRequest{
		private String userName;
		private String passwrod;
	}
	@Data
	public static class UpdateHouseRequest{
		private int houseId;
		private int bedroomSum;// 几房
		private int livingRoomSum;// 几厅
		private int wcSum;// 几卫
		private BigDecimal spaceArea = new BigDecimal(0);// 内空面积
		private BigDecimal sellPrice;//出售价格
		private BigDecimal rentPrice;//出租价格
	}
	
	
}
