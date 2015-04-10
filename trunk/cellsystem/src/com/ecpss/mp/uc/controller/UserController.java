package com.ecpss.mp.uc.controller;

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
import com.ecpss.mp.entity.User;
import com.ecpss.mp.uc.service.UserService;
import com.leo.jaxrs.fault.LeoFault;

@Controller
@RequestMapping("/uc")
@SessionAttributes(Global.SESSION_UID_KEY)
public class UserController extends BaseController {
	private UserService userService;

	public UserService getUserService() {
		return userService;
	}

	@Autowired
	@Qualifier("userService")
	public void setUserService(UserService userService) {
		this.userService = userService;
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
	public String login(@PathVariable String suffix, HttpSession session, @ModelAttribute("command")LoginParams params,BindingResult result) {
		if (assertSubmit(suffix)) {
			try{
				User user = getUserService().login(params.getLoginName(), params.getPassword());
				session.setAttribute("uid", user.getUid());
			}catch(LeoFault e){
				result.reject(e.getKey());
				return "uc.login";
			}
		}
		return "uc.login";
	}

	public static class LoginParams {
		private String loginName;
		private String password;

		public String getLoginName() {
			return loginName;
		}

		public void setLoginName(String loginName) {
			this.loginName = loginName;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}
	}

}
