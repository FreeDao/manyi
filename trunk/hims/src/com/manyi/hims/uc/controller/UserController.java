package com.manyi.hims.uc.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.leo.jaxrs.fault.LeoFault;
import com.manyi.hims.BaseController;
import com.manyi.hims.Global;
import com.manyi.hims.entity.User;
import com.manyi.hims.uc.service.UserService;

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
	
	

		 
	public static void main(String[] args) {
		/*
		 * Date date = new Date();
		long time = date.getTime();// 得到 当前时间的毫秒数
		time = ((1000 * 60 * 60) * 24 * 7) - 1;
		long hours = time / 1000 / 60 / 60;// 小时
		if ((hours / 24) >= 7) {
			System.out.println(date);
		} else if (hours < 1) {
			System.out.println(time / 1000 / 60 + "分");
		} else if (1 <= hours && hours < 24) {
			System.out.println(hours + "小时");
		} else if ((hours / 24) >= 1 && (hours / 24) < 7) {
			System.out.println((hours / 24) + "天");
		}

		 final  String[] strs = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P", "A",
				"S", "D", "F", "G", "H", "J", "K", "L", "Z", "X", "C", "V", "B", "N", "M" };

		for (int j = 0; j < 100; j++) {
			String s = "";
			for (int i = 0; i < 6; i++) {
				int a = (int) (Math.random() * 36);
				s += strs[a];
			}

			System.out.println(s);
		}
		String s= " ";
		System.out.println(StringUtils.isBlank(s));
		
		
		Date tdate = new Date();
		tdate.setTime(tdate.getTime()-1000*60*8-1000*60*85*2*60);
		String sss =DateUtil.fattrDate(tdate);
		System.out.println(sss);
		*/
		
	}
	


}
