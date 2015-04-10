/**
 * 
 */
package com.ecpss.mp.controller;

import java.util.ArrayList;
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
import com.ecpss.mp.controller.ResidenceRestController.ResidenceInfo;
import com.ecpss.mp.uc.service.MainService;

/**
 * @author lei
 * 
 */
@Controller
@RequestMapping("/main")
@SessionAttributes(Global.SESSION_UID_KEY)
public class MainRestController extends RestController {

	@Autowired
	@Qualifier("mainService")
	private MainService mainService;
	
	public void setMainService(MainService mainService) {
		this.mainService = mainService;
	}
	
	/**
	 * 对应的 区域的数据导入到excel中
	 * @param request
	 * @param areaName
	 * @return
	 */
	@RequestMapping(value = "/export.rest", produces = "application/json")
	@ResponseBody
	public Response exportExcel(HttpServletRequest request,String areaName) {
		
		final String path = this.mainService.exportToExcel(request.getServletContext(),areaName);
		
		return new Response(){
			{
				if(path != ""){
					this.setMessage(path);
				}else{
					this.setMessage("导出失败!");
				}
			}
		};
	}
	
	@RequestMapping(value = "/test.rest", produces = "application/json")
	@ResponseBody
	public Response test(HttpServletRequest request) {
		final List<ResidenceInfo> obj =this.mainService.sqlServertest(request.getServletContext());
		
		return new Response(){
			List rows = new ArrayList();
			{
				//BeanUtils.copyProperties(this, obj);
				this.rows = obj;
			}
			public List getRows() {
				return rows;
			}
			public void setRows(List list) {
				this.rows = list;
			}
		};
	}

	
	
	/**
	 * 根据 类型 加载  不同的 数据字典 数据字典
	 * @param session
	 * @param pars
	 * @return
	 */
	@RequestMapping(value = "/loadlist.rest", produces = "application/json")
	@ResponseBody
	public Response load_list(HttpServletRequest request, ListPars pars) {
		Map<String,List>  map= (Map<String, List>) request.getServletContext().getAttribute("appconfigs");
		final List obj = 	map.get(pars.getListName());
		return new Response(){
			List rows = new ArrayList();
			{
				//BeanUtils.copyProperties(this, obj);
				this.rows = obj;
			}
			public List getRows() {
				return rows;
			}
			public void setRows(List list) {
				this.rows = list;
			}
			
		};
	}
	
	/**
	 * 根据 类型 加载  不同的 数据字典 数据字典
	 * @param session
	 * @param pars
	 * @return
	 */
	@RequestMapping(value = "/loadarealist.rest", produces = "application/json")
	@ResponseBody
	public Response load_list2(HttpServletRequest request, ListPars pars) {
		final List obj = 	this.mainService.loadArae(pars.listName, pars.parentId);
		return new Response(){
			List rows = new ArrayList();
			{
				//BeanUtils.copyProperties(this, obj);
				this.rows = obj;
			}
			public List getRows() {
				return rows;
			}
			public void setRows(List list) {
				this.rows = list;
			}
			
		};
	}
	
	
	public static class ListPars{
		private String listName ;
		private String parentId;
		public String getListName() {
			return listName;
		}
		public void setListName(String listName) {
			this.listName = listName;
		}
		public String getParentId() {
			return parentId;
		}
		public void setParentId(String parentId) {
			this.parentId = parentId;
		}
		
	}
	


}
