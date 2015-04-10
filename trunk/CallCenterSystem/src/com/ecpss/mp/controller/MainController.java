package com.ecpss.mp.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.ecpss.mp.BaseController;
import com.ecpss.mp.Global;
import com.ecpss.mp.entity.Employee;
import com.ecpss.mp.uc.service.MainService;

@Controller
@SessionAttributes(Global.SESSION_UID_KEY)
public class MainController extends BaseController {

	@Autowired
	@Qualifier("mainService")
	private MainService mainService;
	
	
	@RequestMapping(value = "/index")
	@SuppressWarnings("rawtypes")
	public String index(HttpServletRequest request) {
		//判断用户是否登录
		Employee loginEmployee = (Employee) request.getSession().getAttribute("loginEmployee");
		if(loginEmployee != null){
			return "index";
		}else{
			return "login";
		}
	}


	
	public MainService getMainService() {
		return mainService;
	}

	public void setMainService(MainService mainService) {
		this.mainService = mainService;
	}
}
