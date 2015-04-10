package com.ecpss.mp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ecpss.mp.Response;
import com.ecpss.mp.RestController;
import com.ecpss.mp.controller.ResidenceRestController.ResidenceInfo;
import com.ecpss.mp.uc.service.UpdateResidenceService;
@Controller
@RequestMapping("/ur")
public class UpdateResidenceRestController extends RestController{
	private UpdateResidenceService updateResidenceService;

	public UpdateResidenceService getUpdateResidenceService() {
		return updateResidenceService;
	}
	@Autowired
	@Qualifier("updateResidenceService")
	public void setUpdateResidenceService(UpdateResidenceService updateResidenceService) {
		this.updateResidenceService = updateResidenceService;
	}
	@RequestMapping(value="/update",produces="application/json")
	@ResponseBody
	public Response uppdateResidence(int hid,ResidenceInfo resdienveInfo){
		
		getUpdateResidenceService().updateResdence(hid, resdienveInfo);
		return new Response();
	}
}
