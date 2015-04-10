package com.manyi.ihouse.assignee.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.DeferredResult;

import com.manyi.ihouse.assignee.model.AssigneeListResponse;
import com.manyi.ihouse.assignee.model.AssigneeModel;
import com.manyi.ihouse.assignee.model.AssigneeRequest;
import com.manyi.ihouse.assignee.model.AssigneeResponse;
import com.manyi.ihouse.assignee.service.AssigneeManagerService;
import com.manyi.ihouse.base.BaseController;
import com.manyi.ihouse.base.PageResponse;
import com.manyi.ihouse.base.Response;
import com.manyi.ihouse.user.model.PageListRequest;
import com.manyi.ihouse.util.CommonUtils;

@Controller
@RequestMapping("/assigneeManager")
public class AssigneeManagerController extends BaseController{

	@Autowired
	@Qualifier("assigneeManagerService")
	private AssigneeManagerService assigneeManagerService;

	public AssigneeManagerService getAssigneeManagerService() {
		return assigneeManagerService;
	}

	public void setAssigneeManagerService(
			AssigneeManagerService assigneeManagerService) {
		this.assigneeManagerService = assigneeManagerService;
	}
	
	@RequestMapping(value = "/addAssignee.rest", produces = "application/json")
	@ResponseBody
	public  DeferredResult<Response> addAssignee(HttpSession session, @RequestBody AssigneeRequest assigneeRequest){
		System.out.println("----------------------");
		System.out.println("request value name="+assigneeRequest.getName());
		assigneeManagerService.addAssignee(assigneeRequest);
		return CommonUtils.deferredResult(new Response());
	}
	
	@RequestMapping(value = "/updateAssignee.rest", produces = "application/json")
	@ResponseBody
	public DeferredResult<Response> updateAssignee(HttpSession session, @RequestBody AssigneeRequest assigneeRequest){
		assigneeManagerService.updateAssignee(assigneeRequest);
		return CommonUtils.deferredResult(new Response());
	}
	
	@RequestMapping(value = "/getAssigneeById.rest", produces = "application/json")
	@ResponseBody
	public DeferredResult<Response> getAssigneeById(HttpSession session, @RequestBody AssigneeRequest assigneeRequest){
		AssigneeResponse result = assigneeManagerService.getAssignee(assigneeRequest.getId());
		DeferredResult<Response> dr = new DeferredResult<Response>();
		dr.setResult(result);
		
		return dr;
	}
	
	@RequestMapping(value = "/assigneeList.rest", produces = "application/json")
	@ResponseBody
	public DeferredResult<Response> assigneeList(@RequestBody PageListRequest request){
		PageResponse<AssigneeModel> assigneeListResponse = assigneeManagerService.getAssigneeList(request);
		DeferredResult<Response> dr = new DeferredResult<Response>();
		dr.setResult(assigneeListResponse);
		return dr;
	}
}
