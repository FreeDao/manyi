package com.manyi.hims.user.conroller;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.manyi.hims.BaseController;
import com.manyi.hims.user.model.UserValidationModel;
import com.manyi.hims.user.service.UserManagerService;

@Controller
@RequestMapping("/userValidation")
public class UserValidationController extends BaseController {
	
	@Autowired
	@Qualifier("userManagerService")
	private UserManagerService userManagerService;
	
	@RequestMapping(value="/index" )
	@ResponseBody
	public ModelAndView getIndex(ModelAndView mav){
		mav.setViewName("/fyb/user/userValidationCode");
		return mav;
	}
	
	@RequestMapping(value="/userValidationCode"  )
	@ResponseBody
	public ModelAndView getUserValidation(UserValidationModel uv ,ModelAndView mav){
		mav.setViewName("/fyb/user/userValidationCode");
		if (StringUtils.isBlank(uv.getMobile())) {
			return mav ;
		}
		List<UserValidationModel> uvList = userManagerService.getUserModelList(uv);
		mav.addObject("list", uvList);
		return mav;
	}
	
}
