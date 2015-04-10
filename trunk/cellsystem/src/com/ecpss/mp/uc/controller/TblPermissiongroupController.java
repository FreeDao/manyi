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
import com.ecpss.mp.entity.TblPermissiongroup;
import com.ecpss.mp.uc.service.TblPermissiongroupService;

@Controller
@RequestMapping("/tblpermissiongroup")
@SessionAttributes(Global.SESSION_UID_KEY)
public class TblPermissiongroupController extends BaseController {
	@Autowired
	@Qualifier("tblPermissiongroupService")
	private TblPermissiongroupService tblPermissiongroupService;


	public TblPermissiongroupService getTblPermissiongroupService() {
		return tblPermissiongroupService;
	}

	public void setTblPermissiongroupService(TblPermissiongroupService tblPermissiongroupService) {
		this.tblPermissiongroupService = tblPermissiongroupService;
	}

	/**
	 * 返回列表页面
	 * @return
	 */
	@RequestMapping(value = "/list")
	public String list(Map map) {
		map.put("home_path", "/tblpermissiongroup/list");
		return "tblpermissiongroup.list";
	}

	/**
	 * 返回新增页面
	 * @return
	 */
	@RequestMapping(value = "/add")
	public String add(Map map) {
		map.put("home_path", "/tblpermissiongroup/add");
		return "tblpermissiongroup.add";
	}
	
	/**
	 * 返回修改页面
	 * @return
	 */
	@RequestMapping(value = "/modify")
	public String modify(String id,HttpServletRequest request) {
		request.setAttribute("id", id);
		return "tblpermissiongroup.modify";
	}

	/**
	 * 返查看页面
	 * @return
	 */
	@RequestMapping(value = "/view")
	public String view(String id,HttpServletRequest request) {
		request.setAttribute("id", id);
		return "tblpermissiongroup.view";
	}

	@RequestMapping(value = "/save")
	public String save(TblPermissiongroup tblpermissiongroup){
		this.getTblPermissiongroupService().save(tblpermissiongroup);
		return "tblpermissiongroup.list";
	}
	
	@RequestMapping(value = "/update")
	public String update(TblPermissiongroup tblpermissiongroup){
		this.getTblPermissiongroupService().update(tblpermissiongroup);
		return "tblpermissiongroup.list";
	}
	
	@RequestMapping(value = "/delete")
	public String delete(String id){
		this.getTblPermissiongroupService().delete(id);
		return "tblpermissiongroup.list";
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
