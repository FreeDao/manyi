package com.ecpss.mp.uc.service;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import com.ecpss.mp.controller.ResidenceRestController.ResidenceInfo;
import com.ecpss.mp.entity.Area;


public interface MainService  {
	@SuppressWarnings("rawtypes")
	public Map<String, List> initData();
	
	public List<Area> loadArae (String type , String parentId);
	
	public List<ResidenceInfo> sqlServertest(ServletContext servletContext);
	
	public String exportToExcel(ServletContext servletContext,String txt);
}
