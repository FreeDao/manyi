package com.manyi.fyb.callcenter.sourcemanage.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.manyi.fyb.callcenter.base.controller.BaseController;
import com.manyi.fyb.callcenter.employee.model.EmployeeModel;
import com.manyi.fyb.callcenter.sourcemanage.model.SourceManageRequest;
import com.manyi.fyb.callcenter.utils.Constants;
import com.manyi.fyb.callcenter.utils.HttpClientHelper;

/**
 * @date 2014年4月17日 下午12:11:17
 * @author Tom 
 * @description  
 * 房源管理详情页面
 */
@Controller
@RequestMapping("sourcemanage")
public class SourceManageController extends BaseController {
	
	
	/**
	 * @date 2014年4月18日 上午10:36:33
	 * @author Tom  
	 * @description  
	 * 
	 */
	@RequestMapping("/sourceManageDetail")
	private String sourceManageDetail(@RequestParam String houseId, Model model) {
		model.addAttribute("houseId", houseId);
	
		return "sourcemanage/sourceManageDetail";
	}
	/**
	 * @date 2014年4月17日 下午12:11:58
	 * @author Tom  
	 * @description  
	 * 房源基础信息
	 */
	@RequestMapping("/getSourceBaseInfo")
	@ResponseBody
	private String detail(SourceManageRequest sourceManageRequest) {

		return HttpClientHelper.sendRestJsonShortObj2Str("/sourcemanage/getSourceBaseInfo.rest", sourceManageRequest);
	}
	
	/**
	 * @date 2014年4月18日 上午10:03:01
	 * @author Tom  
	 * @description  
	 * 业主信息
	 */
	@RequestMapping("/getSourceHostInfo")
	@ResponseBody
	private String getSourceHostInfo(SourceManageRequest sourceManageRequest) {
		
		return HttpClientHelper.sendRestJsonShortObj2Str("/sourcemanage/getSourceHostInfo.rest", sourceManageRequest);
	}
	
	/**
	 * @date 2014年4月18日 上午10:02:52
	 * @author Tom  
	 * @description  
	 * 审核记录
	 */
	@RequestMapping("/getAuditMessageInfo")
	@ResponseBody
	private String getAuditMessageInfo(SourceManageRequest sourceManageRequest) {
		
		return HttpClientHelper.sendRestJsonShortObj2Str("/sourcemanage/getAuditMessageInfo.rest", sourceManageRequest);
	}
	
	/**
	 * @date 2014年4月18日 上午10:02:45
	 * @author Tom  
	 * @description  
	 * 房源修改记录
	 */
	@RequestMapping("/getSourceEditInfo")
	@ResponseBody
	private String getSourceEditInfo(SourceManageRequest sourceManageRequest, HttpSession session) {
		
		EmployeeModel employeeModel = (EmployeeModel)session.getAttribute(Constants.LOGIN_SESSION);
		sourceManageRequest.setOperatorId(employeeModel.getId());

		return HttpClientHelper.sendRestJsonShortObj2Str("/sourcemanage/getSourceEditInfo.rest", sourceManageRequest);
	}
	
	
	 
	/**
	 * @date 2014年4月20日 下午4:45:22
	 * @author Tom  
	 * @description  
	 * 修改房源
	 */
	@RequestMapping("/updateSourceManage")
	@ResponseBody
	private String updateSourceManage(SourceManageRequest sourceManageRequest, HttpServletRequest request) {
		EmployeeModel employeeResponse = (EmployeeModel)request.getSession().getAttribute(Constants.LOGIN_SESSION);
		sourceManageRequest.setOperatorId(employeeResponse.getId());
		
		return HttpClientHelper.sendRestJsonShortObj2Str("/sourcemanage/updateSourceManage.rest", sourceManageRequest);
	}
	/**
	 * @date 2014年4月20日 下午4:45:22
	 * @author Tom  
	 * @description  
	 * 修改房源
	 */
	@RequestMapping("/deleteSourceInfo")
	@ResponseBody
	private String deleteSourceInfo(SourceManageRequest sourceManageRequest, HttpServletRequest request) {
		
		EmployeeModel employeeResponse = (EmployeeModel)request.getSession().getAttribute(Constants.LOGIN_SESSION);
		sourceManageRequest.setOperatorId(employeeResponse.getId());
		
		return HttpClientHelper.sendRestJsonShortObj2Str("/sourcemanage/deleteSourceInfo.rest", sourceManageRequest);
	}
	
	
	
	
	
}
