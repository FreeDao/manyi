package com.manyi.fyb.callcenter.aiwutools.estate.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.manyi.fyb.callcenter.base.controller.BaseController;

@Controller
@RequestMapping("/aiwuEstate")
public class AiwuEstateController extends BaseController {
	
	@RequestMapping("/index")
	public String Estate() {
		
		return "/aiwutools/demo/picUpload";
	}

}
