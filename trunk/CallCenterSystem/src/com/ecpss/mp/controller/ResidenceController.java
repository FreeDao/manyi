package com.ecpss.mp.controller;


import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.ecpss.mp.BaseController;
import com.ecpss.mp.Global;
import com.ecpss.mp.entity.Residence;
import com.ecpss.mp.uc.service.ResidenceService;


@Controller
@RequestMapping("/residence")
@SessionAttributes(Global.SESSION_UID_KEY)
public class ResidenceController extends BaseController {
	@Autowired
	@Qualifier("residenceService")
	private ResidenceService residenceService;
	
	
	/**
	 * 返回列表页面
	 * @return
	 */
	@RequestMapping(value = "/list")
	public String list(String houseType,HttpSession session) {
		session.setAttribute("houseType", houseType);
		return "residence.list";
	}
	
	@RequestMapping(value = "/save")
	public String save(Residence residence){
		this.getResidenceService().save(residence);
		return "residence.list";
	}
	
	@RequestMapping(value = "/update")
	public String update(Residence residence){
		this.getResidenceService().update(residence);
		return "residence.list";
	}
	
	@RequestMapping(value = "/delete")
	public String delete(String id){
		this.getResidenceService().delete(id);
		return "residence.list";
	}
	

	
	public ResidenceService getResidenceService() {
		return residenceService;
	}

	public void setResidenceService(ResidenceService residenceService) {
		this.residenceService = residenceService;
	}
	
}
