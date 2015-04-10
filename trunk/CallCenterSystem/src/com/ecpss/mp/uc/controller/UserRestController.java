/**
 * 
 */
package com.ecpss.mp.uc.controller;

import javax.servlet.http.HttpSession;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.ecpss.mp.Global;
import com.ecpss.mp.Response;
import com.ecpss.mp.RestController;
import com.ecpss.mp.entity.User;
import com.ecpss.mp.uc.service.UserService;

/**
 * @author lei
 *
 */
@Controller
@RequestMapping("/uc")
@SessionAttributes(Global.SESSION_UID_KEY)
public class UserRestController extends RestController {
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
	@RequestMapping(value = "/login.rest", produces="application/json")
	@ResponseBody
	public Response login(HttpSession session,
			@NotEmpty(message="{ec_10100002}")String loginName,
			@NotEmpty(message="{ec_10100002}")String password) {
		
		final User user = getUserService().login(loginName, password);
		session.setAttribute("uid", user.getUid());
		
		Response result = new Response(){
			private int uid;
			private String userName;
			private String mobile;
			private String email;
			private int digitalAccount;
			private boolean disable;
			private String realName;
			private int gender;
			
			{
				BeanUtils.copyProperties(user, this);
			}
			
			public int getUid() {
				return uid;
			}
			public void setUid(int uid) {
				this.uid = uid;
			}
			public String getUserName() {
				return userName;
			}
			public void setUserName(String userName) {
				this.userName = userName;
			}
			public String getMobile() {
				return mobile;
			}
			public void setMobile(String mobile) {
				this.mobile = mobile;
			}
			public String getEmail() {
				return email;
			}
			public void setEmail(String email) {
				this.email = email;
			}
			public int getDigitalAccount() {
				return digitalAccount;
			}
			public void setDigitalAccount(int digitalAccount) {
				this.digitalAccount = digitalAccount;
			}
			public boolean isDisable() {
				return disable;
			}
			public void setDisable(boolean disable) {
				this.disable = disable;
			}
			public String getRealName() {
				return realName;
			}
			public void setRealName(String realName) {
				this.realName = realName;
			}
			public int getGender() {
				return gender;
			}
			public void setGender(int gender) {
				this.gender = gender;
			}
		};
		
		return result;
	}
	
}
