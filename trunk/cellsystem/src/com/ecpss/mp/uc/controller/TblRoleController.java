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
import com.ecpss.mp.entity.TblRole;
import com.ecpss.mp.uc.service.TblRoleService;

@Controller
@RequestMapping("/tblrole")
@SessionAttributes(Global.SESSION_UID_KEY)
public class TblRoleController extends BaseController {
	private TblRoleService tblTblRoleService;

	public TblRoleService getTblRoleService() {
		return tblTblRoleService;
	}

	@Autowired
	@Qualifier("tblRoleService")
	public void setTblRoleService(TblRoleService tblroleService) {
		this.tblTblRoleService = tblroleService;
	}

	/**
	 * 返回列表页面
	 * @return
	 */
	@RequestMapping(value = "/list")
	public String list(Map map) {
		map.put("home_path", "/tblrole/list");
		return "tblrole.list";
	}

	/**
	 * 返回新增页面
	 * @return
	 */
	@RequestMapping(value = "/add")
	public String add(Map map) {
		map.put("home_path", "/tblrole/add");
		return "tblrole.add";
	}
	
	/**
	 * 返回修改页面
	 * @return
	 */
	@RequestMapping(value = "/modify")
	public String modify(String id,HttpServletRequest request) {
		request.setAttribute("id", id);
		return "tblrole.modify";
	}

	/**
	 * 返查看页面
	 * @return
	 */
	@RequestMapping(value = "/view")
	public String view(String id,HttpServletRequest request) {
		request.setAttribute("id", id);
		return "tblrole.view";
	}

	@RequestMapping(value = "/save")
	public String save(TblRole tblrole){
		this.getTblRoleService().save(tblrole);
		return "tblrole.list";
	}
	
	@RequestMapping(value = "/update")
	public String update(TblRole tblrole){
		this.getTblRoleService().update(tblrole);
		return "tblrole.list";
	}
	
	@RequestMapping(value = "/delete")
	public String delete(String id){
		this.getTblRoleService().delete(id);
		return "tblrole.list";
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
