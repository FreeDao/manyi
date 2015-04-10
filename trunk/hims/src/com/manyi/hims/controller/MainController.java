package com.manyi.hims.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.manyi.hims.BaseController;
import com.manyi.hims.Global;

@Controller
@SessionAttributes(Global.SESSION_UID_KEY)
public class MainController extends BaseController {

	@RequestMapping(value = "/index2")
	public String index() {
		return "index";
	}
	
	@RequestMapping(value = "/rpTips")
	public String rpTips() {
		return "rpTips";
	}
}
