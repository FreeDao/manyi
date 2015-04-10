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
import com.ecpss.mp.entity.City;
import com.ecpss.mp.uc.service.CityService;

@Controller
@RequestMapping("/city")
@SessionAttributes(Global.SESSION_UID_KEY)
public class CityController extends BaseController {
	private CityService cityService;

	public CityService getCityService() {
		return cityService;
	}

	@Autowired
	@Qualifier("cityService")
	public void setCityService(CityService cityService) {
		this.cityService = cityService;
	}

	/**
	 * 返回列表页面
	 * @return
	 */
	@RequestMapping(value = "/list")
	public String list() {
		return "city.list";
	}

	/**
	 * 返回新增页面
	 * @return
	 */
	@RequestMapping(value = "/add")
	public String add(Map map) {
		map.put("home_path", "/city/add");
		return "city.add";
	}
	
	/**
	 * 返回修改页面
	 * @return
	 */
	@RequestMapping(value = "/modify")
	public String modify(String id,HttpServletRequest request) {
		request.setAttribute("id", id);
		return "city.modify";
	}

	/**
	 * 返查看页面
	 * @return
	 */
	@RequestMapping(value = "/view")
	public String view(String id,HttpServletRequest request) {
		request.setAttribute("id", id);
		return "city.view";
	}

	@RequestMapping(value = "/save")
	public String save(City city){
		this.getCityService().save(city);
		return "city.list";
	}
	
	@RequestMapping(value = "/update")
	public String update(City city){
		this.getCityService().update(city);
		return "city.list";
	}
	
	@RequestMapping(value = "/delete")
	public String delete(String id){
		this.getCityService().delete(id);
		return "city.list";
	}
	
	/*
	
	@RequestMapping(value = "/city_list{suffix}")
	public String list(@PathVariable String suffix,HttpServletRequest request ) {
		if (assertSubmit(suffix)) {
			try{
				List<City> citys = getCityService().getCityList();
				request.setAttribute("citys", citys);
			}catch(LeoFault e){
				//result.reject(e.getKey());
				return "uc.city";
			}
		}
		return "uc.city";
	}
	

	
	
	@RequestMapping(value = "/show{suffix}")
	public String show(@PathVariable String suffix,HttpServletRequest request ) {
		if (assertSubmit(suffix)) {
			try{
				City city = new City();
				city.setCityName("实验");
				city.setCityNo("1000");
				
				List<City> citys =new ArrayList<City>();
				citys.add(city);
				//map.put("city", citys);
				request.setAttribute("citys", citys);
			}catch(LeoFault e){
				//result.reject(e.getKey());
				return "uc.city";
			}
		}
		return "uc.city";
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
