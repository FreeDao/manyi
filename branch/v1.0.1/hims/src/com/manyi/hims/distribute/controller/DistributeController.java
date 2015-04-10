package com.manyi.hims.distribute.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.DeferredResult;

import com.manyi.hims.Response;
import com.manyi.hims.distribute.model.DistributeRequest;
import com.manyi.hims.distribute.service.DistributeService;
import com.manyi.hims.employee.model.EmployeeModel;

@Controller
@RequestMapping ("/distribute")
public class DistributeController {
	
	@Autowired
	@Qualifier("distributeService")
	private DistributeService distributeService;
	
	@RequestMapping (value="/check.rest", produces="application/json")
	@ResponseBody
	public DeferredResult<Response> check() {
		Response response = distributeService.distribute();
		DeferredResult<Response> dr = new DeferredResult<Response>();
		dr.setResult(response);
		return dr;
	}

	@RequestMapping (value="/distribute.rest")
	@ResponseBody
	public DeferredResult<Response> distribute(@RequestBody DistributeRequest distributeRequest){
		Response response = distributeService.distribute(distributeRequest);
		DeferredResult<Response> dr = new DeferredResult<Response>();
		dr.setResult(response);
		return dr;
	}
	
	/**
	 * @date 2014年5月6日 下午20:50:38
	 * @author Tom  
	 * @description  
	 * 分配举报任务
	 */
	@RequestMapping (value="/distribute4Report.rest")
	@ResponseBody
	public DeferredResult<Response> distribute4Report(@RequestBody DistributeRequest distributeRequest){
		Response response = distributeService.distribute4Report(distributeRequest);
		DeferredResult<Response> dr = new DeferredResult<Response>();
		dr.setResult(response);
		return dr;
	}
	
	@RequestMapping (value="/autoDistribute.rest")
	@ResponseBody
	public DeferredResult<Response> autoDistribute(@RequestBody EmployeeModel emp){
		Response response = null;
		
		System.out.println("hims empId " + emp.getId());
		DeferredResult<Response> dr = new DeferredResult<Response>();
		if (emp .getId() <= 0) {
			response = new Response(1586004,"error employeeId");
			dr.setResult(response);
			return dr;
		}
		response = distributeService.getDistributedHouseResource(emp.getId());
		if(response == null){
			response = distributeService.autoDistribute(emp.getId());
		}
		
		dr.setResult(response);
		return dr;
	}
	
	@RequestMapping (value="/pendingRecovery.rest")
	@ResponseBody
	public DeferredResult<Response> pending(){
		Response response = distributeService.pendingRecovery();
		DeferredResult<Response> dr = new DeferredResult<Response>();
		dr.setResult(response);
		return dr;
	}	
}
