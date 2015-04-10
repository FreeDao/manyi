package com.manyi.hims.employee.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.request.async.DeferredResult;

import com.leo.common.Page;
import com.manyi.hims.Global;
import com.manyi.hims.PageResponse;
import com.manyi.hims.Response;
import com.manyi.hims.RestController;
import com.manyi.hims.employee.model.EmployeeListResponse;
import com.manyi.hims.employee.model.EmployeeModel;
import com.manyi.hims.employee.model.EmployeeResponse;
import com.manyi.hims.employee.service.EmployeeService;
import com.manyi.hims.employee.service.EmployeeService.EmpResponse;

@Controller
@RequestMapping("/employee")
public class EmployeeController extends RestController {
	
	@Autowired
	@Qualifier("employeeService")
	private EmployeeService employeeService;
	
	@RequestMapping(value = "/login.rest", produces="application/json")
	@ResponseBody
	public DeferredResult<Response> login(@RequestBody EmployeeModel emp) {
		final EmployeeResponse empBack = this.employeeService.login(emp);
		EmployeeResponse result = new EmployeeResponse() ;
		BeanUtils.copyProperties(empBack, result);
		DeferredResult<Response> dr = new DeferredResult<Response>();
		dr.setResult(result);
		return dr;
	}
	
	@RequestMapping(value = "/addEmployee.rest", produces="application/json")
	@ResponseBody
	public DeferredResult<Response> addEmployee(@RequestBody EmployeeModel emp) {
		this.employeeService.addEmployee(emp);
		Response result = new EmployeeResponse();
		 
		DeferredResult<Response> dr = new DeferredResult<Response>();
		dr.setResult(result);
		return dr;
	}
	
	@RequestMapping(value = "/disableEmployee.rest", produces="application/json")
	@ResponseBody
	public DeferredResult<Response> disableEmployee(@RequestBody EmployeeModel emp) {
		this.employeeService.disableEmployee(emp);
		//Response result = new EmployeeResponse() ;
		 
		DeferredResult<Response> dr = new DeferredResult<Response>();
		dr.setResult(new Response());
		return dr;
	}
	
	@RequestMapping(value = "/updateEmployee.rest", produces="application/json")
	@ResponseBody
	public DeferredResult<Response> updateEmployee(@RequestBody EmployeeModel emp) {
		this.employeeService.updateEmployee(emp);
		Response result = new EmployeeResponse() ;
		 
		DeferredResult<Response> dr = new DeferredResult<Response>();
		dr.setResult(result);
		return dr;
	}
	
	@RequestMapping(value = "/getEmployee.rest", produces="application/json")
	@ResponseBody
	public DeferredResult<Response> getEmployee(@RequestBody getEmployeeRequest employeeRequest) {
		Page<EmpResponse> page = this.employeeService.getEmployee(employeeRequest.getPage(), employeeRequest.getRows());
		PageResponse<EmpResponse> rows = new PageResponse<EmpResponse>();
		BeanUtils.copyProperties(page, rows);
		DeferredResult<Response> dr = new DeferredResult<Response>();
		dr.setResult(rows);
		return dr;
	}
	
	@RequestMapping(value = "/getEmployeeList.rest", produces="application/json")
	@ResponseBody
	public DeferredResult<Response> getEmployeeList() {
		EmployeeListResponse employeeList = this.employeeService.getEmployeeList();
		DeferredResult<Response> dr = new DeferredResult<Response>();
		dr.setResult(employeeList);
		return dr;
	}
	
	/**
	 * @date 2014年5月4日 上午10:41:11
	 * @author Tom  
	 * @description  
	 * 获取地推人员列表
	 */
	@RequestMapping(value = "/getEmployeeList4Floor.rest", produces="application/json")
	@ResponseBody
	public DeferredResult<Response> getEmployeeList4Floor() {
		EmployeeListResponse employeeList = this.employeeService.getEmployeeList(5);
		DeferredResult<Response> dr = new DeferredResult<Response>();
		dr.setResult(employeeList);
		return dr;
	}
	
	public static class getEmployeeRequest{
		private Integer page=0;
		private Integer rows=20;
		public Integer getPage() {
			return page;
		}
		public void setPage(Integer page) {
			this.page = page;
		}
		public Integer getRows() {
			return rows;
		}
		public void setRows(Integer rows) {
			this.rows = rows;
		}
		
	}
	

}
