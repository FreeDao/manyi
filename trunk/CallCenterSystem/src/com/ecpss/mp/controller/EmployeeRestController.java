/**
 * 
 */
package com.ecpss.mp.controller;

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
import com.ecpss.mp.entity.Employee;
import com.ecpss.mp.uc.service.EmployeeService;

/**
 * @author lei
 *
 */
@Controller
@RequestMapping("/mp")
@SessionAttributes(Global.SESSION_UID_KEY)
public class EmployeeRestController extends RestController {
	private EmployeeService employeeService;

	public EmployeeService getEmployeeService() {
		return employeeService;
	}

	@Autowired
	@Qualifier("employeeService")
	public void setEmployeeService(EmployeeService employeeService) {
		this.employeeService = employeeService;
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
		
		final Employee employee = getEmployeeService().login(loginName, password);
		session.setAttribute("loginEmployee", employee);
		
		Response result = new Response(){
			private int eid;
			private String userName;// 用户名
			private String mobile;// 手机号
			private String email;
			private String number;// 工号
			private boolean disable;// 是否禁用
			private String password;
			private String realName;// 身份证姓名
			private int gender;// 姓别-1男人，0保密，1女人
			private String introduce;// 介绍
			
			{
				BeanUtils.copyProperties(this, employee);
			}

			public int getEid() {
				return eid;
			}

			public void setEid(int eid) {
				this.eid = eid;
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

			public String getNumber() {
				return number;
			}

			public void setNumber(String number) {
				this.number = number;
			}

			public boolean isDisable() {
				return disable;
			}

			public void setDisable(boolean disable) {
				this.disable = disable;
			}

			public String getPassword() {
				return password;
			}

			public void setPassword(String password) {
				this.password = password;
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

			public String getIntroduce() {
				return introduce;
			}

			public void setIntroduce(String introduce) {
				this.introduce = introduce;
			}
			
			
		};
		
		return result;
	}
	
}
