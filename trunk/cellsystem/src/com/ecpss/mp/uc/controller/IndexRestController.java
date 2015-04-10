package com.ecpss.mp.uc.controller;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.ecpss.mp.Global;
import com.ecpss.mp.Response;
import com.ecpss.mp.RestController;
import com.ecpss.mp.entity.TblOperator;
import com.ecpss.mp.uc.service.IndexService;
import com.ecpss.mp.uc.service.Sysmenu;
import com.leo.common.beanutil.BeanUtils;

@Controller
@RequestMapping("/index")
@SessionAttributes(Global.SESSION_UID_KEY)
public class IndexRestController extends RestController {
	private IndexService indexService;

	public IndexService getIndexService() {
		return indexService;
	}

	@Autowired
	@Qualifier("indexService")
	public void setIndexService(IndexService indexService) {
		this.indexService = indexService;
	}

	
	@RequestMapping(value = "/loadtree.rest",produces="application/json")
	@ResponseBody
	public List<Response> loadtree(HttpServletRequest request) {
		TblOperator operator = new TblOperator();
		operator.setDid(1l);
		operator.setDloginname("admin");
		operator.setDpassword("123456");
		operator.setDisplatoperator(1);
		operator.setDoperatortype(1);
		final Map<String , Object> maps = this.getIndexService().index(operator);
		
		@SuppressWarnings({"unused"})
		List<Response> result = new ArrayList<Response>(){
			private String id;
			private String menuid;
			private String menuno;
			private String text;
			private String menuname;
			private String menuparentno;
			private String menuurl;
			private String icon;
			private String menuicon;
			private Integer permissions;
			private String permissionobject;
			private List<Sysmenu> children;

			{
				//maps.get("menus");
				//BeanUtils.copyProperties(maps.get("menus"), this);
				List<Sysmenu> list = (List) maps.get("menus");
				for(int i =0 ; i < list.size(); i++){
					
				}
				System.out.println(this.getId());
			}

			public String getId() {
				return id;
			}

			public void setId(String id) {
				this.id = id;
			}

			public String getMenuid() {
				return menuid;
			}

			public void setMenuid(String menuid) {
				this.menuid = menuid;
			}

			public String getMenuno() {
				return menuno;
			}

			public void setMenuno(String menuno) {
				this.menuno = menuno;
			}

			public String getText() {
				return text;
			}

			public void setText(String text) {
				this.text = text;
			}

			public String getMenuname() {
				return menuname;
			}

			public void setMenuname(String menuname) {
				this.menuname = menuname;
			}

			public String getMenuparentno() {
				return menuparentno;
			}

			public void setMenuparentno(String menuparentno) {
				this.menuparentno = menuparentno;
			}

			public String getMenuurl() {
				return menuurl;
			}

			public void setMenuurl(String menuurl) {
				this.menuurl = menuurl;
			}

			public String getIcon() {
				return icon;
			}

			public void setIcon(String icon) {
				this.icon = icon;
			}

			public String getMenuicon() {
				return menuicon;
			}

			public void setMenuicon(String menuicon) {
				this.menuicon = menuicon;
			}

			public Integer getPermissions() {
				return permissions;
			}

			public void setPermissions(Integer permissions) {
				this.permissions = permissions;
			}

			public String getPermissionobject() {
				return permissionobject;
			}

			public void setPermissionobject(String permissionobject) {
				this.permissionobject = permissionobject;
			}

			public List<Sysmenu> getChildren() {
				return children;
			}

			public void setChildren(List<Sysmenu> children) {
				this.children = children;
			}
			
			
		};
		return result;
		
		 
	}
	public static class SystemMuen{
		
	}
	
	/**
	 * 返回主頁面
	 * @return
	 */
	@RequestMapping(value = "/loaddata.rest",produces="application/json")
	@ResponseBody
	public Response loaddata(HttpServletRequest request) {
		TblOperator operator = new TblOperator();
		operator.setDid(1l);
		operator.setDloginname("admin");
		operator.setDpassword("123456");
		operator.setDisplatoperator(1);
		operator.setDoperatortype(1);
		final Map<String , Object> maps = this.getIndexService().index(operator);
		
		@SuppressWarnings({"rawtypes","unused"})
		Response result = new Response(){
			private List menus = new ArrayList();

			private Map permission =new HashMap();
			
			{
				menus = (List) maps.get("menus");
				permission = (Map) maps.get("permission");
			}
			public List getMenus() {
				return menus;
			}

			public void setMenus(List menus) {
				this.menus = menus;
			}

			public Map getPermission() {
				return permission;
			}

			public void setPermission(Map permission) {
				this.permission = permission;
			}

			
		};
		return result;
	}

	
	/*
	
	@RequestMapping(value = "/index_list{suffix}")
	public String list(@PathVariable String suffix,HttpServletRequest request ) {
		if (assertSubmit(suffix)) {
			try{
				List<Index> indexs = getIndexService().getIndexList();
				request.setAttribute("indexs", indexs);
			}catch(LeoFault e){
				//result.reject(e.getKey());
				return "uc.index";
			}
		}
		return "uc.index";
	}
	

	
	
	@RequestMapping(value = "/show{suffix}")
	public String show(@PathVariable String suffix,HttpServletRequest request ) {
		if (assertSubmit(suffix)) {
			try{
				Index index = new Index();
				index.setIndexName("实验");
				index.setIndexNo("1000");
				
				List<Index> indexs =new ArrayList<Index>();
				indexs.add(index);
				//map.put("index", indexs);
				request.setAttribute("indexs", indexs);
			}catch(LeoFault e){
				//result.reject(e.getKey());
				return "uc.index";
			}
		}
		return "uc.index";
	}
	
	*/
	
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
