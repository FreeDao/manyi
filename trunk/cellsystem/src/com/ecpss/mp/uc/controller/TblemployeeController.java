package com.ecpss.mp.uc.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.ecpss.mp.BaseController;
import com.ecpss.mp.Global;
import com.ecpss.mp.entity.Tblemployee;
import com.ecpss.mp.uc.service.TblemployeeService;
import com.leo.jaxrs.fault.LeoFault;

@Controller
@RequestMapping("/uc")
@SessionAttributes(Global.SESSION_UID_KEY)
public class TblemployeeController extends BaseController {
	private TblemployeeService tblemployeeService;

	public TblemployeeService getTblemployeeService() {
		return tblemployeeService;
	}

	@Autowired
	@Qualifier("tblemployeeService")
	public void setTblemployeeService(TblemployeeService tblemployeeService) {
		this.tblemployeeService = tblemployeeService;
	}
	
	@RequestMapping(value = "/tblemployee{suffix}")
	public String list(@PathVariable String suffix,HttpServletRequest request ) {
		if (assertSubmit(suffix)) {
			try{
				List<Tblemployee> tblemployees = getTblemployeeService().getList();
				request.setAttribute("tblemployees", tblemployees);
			}catch(LeoFault e){
				//result.reject(e.getKey());
				return "uc.tblemployee";
			}
		}
		return "uc.tblemployee";
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
