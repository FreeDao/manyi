package com.ecpss.mp.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.ecpss.mp.BaseController;
import com.ecpss.mp.Global;
import com.ecpss.mp.entity.Employee;
import com.ecpss.mp.uc.service.EmployeeService;
import com.ecpss.mp.uc.service.MainService;
import com.leo.jaxrs.fault.LeoFault;

@Controller
@RequestMapping("/mp")
@SessionAttributes(Global.SESSION_UID_KEY)
public class EmployeeController extends BaseController {
	@Autowired
	@Qualifier("employeeService")
	private EmployeeService employeeService;
	
	@Autowired
	@Qualifier("mainService")
	private MainService mainService;

	public void setMainService(MainService mainService) {
		this.mainService = mainService;
	}

	public EmployeeService getEmployeeService() {
		return employeeService;
	}

	public void setEmployeeService(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	/**
	 * 如果访问/xxxxx转发到表单输入页面 如果访问/xxxxxSubmit则进行xxxxx处理
	 * 
	 * @param suffix
	 * @param session
	 * @param command
	 * @param result
	 * @return
	 */
	@RequestMapping(value = "/login{suffix}")
	public String login(@PathVariable String suffix, HttpSession session, @ModelAttribute("command")LoginParams params,HttpServletRequest request,BindingResult result) {
		if (assertSubmit(suffix)) {
			try{
				Employee employee = getEmployeeService().login(params.getUserName(), params.getPassword());
				//登录成功
				session.setAttribute("loginEmployee", employee);
				Map<String, List> appconfigs = init_web();
				request.getServletContext().setAttribute("appconfigs", appconfigs);
			}catch(LeoFault e){
				//result.reject(e.getKey());
				request.setAttribute("msg", e.getMessage());
				return "login";
			}
		}
		return "index";
	}
	
	
	private Map<String, List> init_web(){
		Map<String, List> appconfigs= this.mainService.initData();
		return appconfigs;
	}
	
	
	public static class LoginParams {
		private String userName;
		private String password;

		public String getUserName() {
			return userName;
		}

		public void setUserName(String userName) {
			this.userName = userName;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}
	}

}
