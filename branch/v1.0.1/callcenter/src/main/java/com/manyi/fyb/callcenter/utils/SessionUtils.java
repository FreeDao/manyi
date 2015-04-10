package com.manyi.fyb.callcenter.utils;

import javax.servlet.http.HttpServletRequest;

import com.manyi.fyb.callcenter.employee.model.EmployeeModel;

public class SessionUtils {
	
	public static EmployeeModel getSession(HttpServletRequest request) {
		Object obj = request.getSession().getAttribute(Constants.LOGIN_SESSION);
		return  null == obj ? null : (EmployeeModel) obj;
	}

}
