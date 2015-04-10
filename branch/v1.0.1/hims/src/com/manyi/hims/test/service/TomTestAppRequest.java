package com.manyi.hims.test.service;

import java.util.HashMap;
import java.util.Map;

import com.manyi.hims.util.InfoUtils;

public class TomTestAppRequest {
	public static void main(String[] args) {

		TomTestAppRequest tomTestAppRequest = new TomTestAppRequest();
		
//		tomTestAppRequest.findAreaByAreaId();
//		tomTestAppRequest.findByPage();//success
//		tomTestAppRequest.findEstateByName();  //success
		
		tomTestAppRequest.automaticUpdates();
		
		
	}
	

	/**
	 * @date 2014年4月29日 下午9:13:56
	 * @author Tom  
	 * @description  
	 * 检测版本
	 * 
	 * 请求地址：
	 * /rest/common/automaticUpdates.rest
	 */
	public void automaticUpdates() {
		String url = "http://localhost:8080/rest/common/automaticUpdates.rest";
		Map<String, Object> pars = new HashMap<String, Object>();
		

		
		pars.put("version", "1.0");
		
		InfoUtils.sendRestInter(url, pars);

	}

	/**
	 * @date 2014年4月29日 下午9:13:56
	 * @author Tom  
	 * @description  
	 * app 端  选择小区名称 进行模糊查询
	 * 
	 * 请求地址：
	 * /rest/common/findEstateByName.rest
	 */
	public void findEstateByName() {
		String url = "http://localhost:8080/rest/common/findEstateByName.rest";
		Map<String, Object> pars = new HashMap<String, Object>();
		

		
		pars.put("name", "大");
		
		InfoUtils.sendRestInter(url, pars);

	}
	
	
	/**
	 * @date 2014年4月29日 下午9:13:56
	 * @author Tom  
	 * @description  
	 * app 端  选择区域、房型/面积/价格 查询
	 * 
	 * 请求地址：
	 * houseresource/findRentByPage.rest
	 * houseresource/findSellByPage.rest
	 */
	public void findByPage() {
//		String url = "http://localhost:8080/rest/houseresource/findRentByPage.rest";
		String url = "http://localhost:8080/rest/houseresource/findSellByPage.rest";
		Map<String, Object> pars = new HashMap<String, Object>();
		

		
		pars.put("areaId", "37398");
//		pars.put("bedroomSum", "0");//房型(几房)
		
//		pars.put("startPrice", "0");//起始价格
//		pars.put("endPrice", "600");//截止价格
		
		pars.put("startSpaceArea", "0");// 起始内空面积
		pars.put("endSpaceArea", "50");// 截止内空面积
//		
//		pars.put("start", "1");//数据起始下标
		pars.put("max", "12");//返回的数据量(返回几条记录)
		
		InfoUtils.sendRestInter(url, pars);

	}
	
	/**
	 * @date 2014年4月29日 下午9:13:56
	 * @author Tom  
	 * @description  
	 * app 端 级联查询 区域列表
	 */
	public void findAreaByAreaId() {
		String url = "http://localhost:8080/rest/common/findAreaByAreaId.rest";
		Map<String, Object> pars = new HashMap<String, Object>();
		pars.put("areaId", "1");
		pars.put("notHasAll", "true");
		InfoUtils.sendRestInter(url, pars);

	}
	
	 
}
