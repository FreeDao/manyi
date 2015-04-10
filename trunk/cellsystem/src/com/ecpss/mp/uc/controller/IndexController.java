package com.ecpss.mp.uc.controller;



import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.ecpss.mp.BaseController;
import com.ecpss.mp.Global;
import com.ecpss.mp.entity.City;

@Controller
@RequestMapping("/index")
@SessionAttributes(Global.SESSION_UID_KEY)
public class IndexController extends BaseController {
	
	@RequestMapping(value = "/index_page")
	public String index(){
//		return "base.definition";
		return "base.page";
	}
	
}
