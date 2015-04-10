package com.manyi.fyb.callcenter.index.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.manyi.fyb.callcenter.base.controller.BaseController;
import com.manyi.fyb.callcenter.check.model.CSCheckNumResponse;
import com.manyi.fyb.callcenter.employee.model.EmployeeModel;
import com.manyi.fyb.callcenter.utils.Constants;
import com.manyi.fyb.callcenter.utils.HttpClientHelper;
import com.manyi.fyb.callcenter.utils.SessionUtils;


@Controller
public class IndexController extends BaseController {
	
	@RequestMapping("/index")
	private String index(HttpServletRequest request ,ModelMap model){
		Object empObj = request.getSession().getAttribute(Constants.LOGIN_SESSION);
		if(null == empObj)
			return "index/welcome";
		if (((EmployeeModel)empObj).getRole() == 3) {
			//普通员工页面
			
			model.put("checkNum", HttpClientHelper.sendRestJsonShortObj2Obj("/check/custServ/getCheckNum.rest", SessionUtils.getSession(request), CSCheckNumResponse.class));
			return "index/cs_employee";
		}
		/*
		if (((EmployeeModel)empObj).getRole() == 5) {
			//地推普通员工页面
			return "index/fs_employee";
		}*/
		//TODO 根据权限进不同的页面....
		return "index/index";
	}
	
	@RequestMapping("/logout")
	private String loginout(HttpServletRequest request ){
		request.getSession().removeAttribute(Constants.LOGIN_SESSION);
		return "index/welcome";
	}
}
