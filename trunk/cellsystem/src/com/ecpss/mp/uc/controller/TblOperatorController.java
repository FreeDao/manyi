package com.ecpss.mp.uc.controller;


import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.ecpss.mp.BaseController;
import com.ecpss.mp.Global;
import com.ecpss.mp.entity.TblOperator;
import com.ecpss.mp.uc.service.TblOperatorService;

@Controller
@RequestMapping("/tbloperator")
@SessionAttributes(Global.SESSION_UID_KEY)
public class TblOperatorController extends BaseController {
	private TblOperatorService tblTblOperatorService;

	public TblOperatorService getTblOperatorService() {
		return tblTblOperatorService;
	}

	@Autowired
	@Qualifier("tblOperatorService")
	public void setTblOperatorService(TblOperatorService operatorService) {
		this.tblTblOperatorService = operatorService;
	}

	/**
	 * 返回列表页面
	 * @return
	 */
	@RequestMapping(value = "/list")
	public String list(Map map) {
		map.put("home_path", "/operator/list");
		return "tbloperator.list";
	}

	/**
	 * 返回新增页面
	 * @return
	 */
	@RequestMapping(value = "/add")
	public String add(Map map) {
		map.put("home_path", "/operator/add");
		return "tbloperator.add";
	}
	
	/**
	 * 返回修改页面
	 * @return
	 */
	@RequestMapping(value = "/modify")
	public String modify(String id,HttpServletRequest request) {
		request.setAttribute("id", id);
		return "tbloperator.modify";
	}

	/**
	 * 返查看页面
	 * @return
	 */
	@RequestMapping(value = "/view")
	public String view(String id,HttpServletRequest request) {
		request.setAttribute("id", id);
		return "tbloperator.view";
	}

	@RequestMapping(value = "/save")
	public String save(TblOperator operator){
		this.getTblOperatorService().save(operator);
		return "tbloperator.list";
	}
	
	@RequestMapping(value = "/update")
	public String update(TblOperator operator){
		this.getTblOperatorService().update(operator);
		return "tbloperator.list";
	}
	
	@RequestMapping(value = "/delete")
	public String delete(String id){
		this.getTblOperatorService().delete(id);
		return "tbloperator.list";
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
