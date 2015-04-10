package com.manyi.hims.actionexcel.service;


import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import com.manyi.hims.actionexcel.model.CustomerWorkInfo;
import com.manyi.hims.actionexcel.model.OperationsInfo;
import com.manyi.hims.actionexcel.model.ResidenceInfo;
import com.manyi.hims.entity.Area;


public interface MainService  {
	
	public List<Area> loadArae (String type , String parentId);
	
	public List<CustomerWorkInfo> customerWork(String publishDate);
	public List<OperationsInfo> bDOperations(HttpServletResponse response,OperationsInfo info);
	public List<ResidenceInfo> sqlServertest(ServletContext servletContext);
	
	public String exportToExcel(ServletContext servletContext,String txt);

	public int improtEstate(String filePath);

	public int improtSourceInfo(String filePath);

	public int improtSubEstate(String filePath);

	public int improtEstate2(String filePath);
	
	public int improtArea(String filePath);

	public int improtInitSourceInfo(String filePath);

	/**
	 * 
	 * @param start 开始时间
	 * @param end 结束时间
	 * @param state 状态(1审核中, 0审核通过,2审核失败,-1全部)
	 * @param type (1出售,2出租,-1全部)
	 * @return
	 */
	public int exportSourceInfo(Date start, Date end, int state,int type);

	public int checkUser(String mobile);

	public int improtSourceInfo(MultipartFile excel);

	public int improtInitSourceInfo(MultipartFile excel);

	public int improtEstate2(MultipartFile excel);

	public int exportSourceInfo(HttpServletResponse response, Date start, Date end, int state, int type);

	public int exportUserNum(HttpServletResponse response, Date start, Date end);
}
