package com.manyi.hims.houseresource.controller;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.request.async.DeferredResult;

import com.leo.common.Page;
import com.manyi.hims.Global;
import com.manyi.hims.Response;
import com.manyi.hims.area.service.AreaService;
import com.manyi.hims.common.HouseEntity;
import com.manyi.hims.common.HouseSearchRequest;
import com.manyi.hims.houseresource.service.HouseResourceService;
import com.manyi.hims.util.CommonUtils;

@Controller
@RequestMapping("/rest/houseresource")
@SessionAttributes(Global.SESSION_UID_KEY)
public class HouseResourceController {
	
	Logger log = LoggerFactory.getLogger(HouseResourceController.class);

	@Autowired
	HouseResourceService houseResourceService;
	
	@Autowired
	AreaService areaService;
	
	/**
	 * @date 2014年4月29日 下午8:44:09
	 * @author Tom  
	 * @description  
	 * 返回数据集合
	 */
	@SuppressWarnings("unused")
	private Response queryHouseResourceByPage(final Page<HouseEntity> entitys) {
		log.info("---------------response：" + JSONArray.fromObject(entitys.getRows()).toString());
		Response result = new Response() {
			private List<HouseEntity> houseList = new ArrayList<HouseEntity>();
			{
				if (entitys != null && entitys.getRows() != null && entitys.getRows().size() > 0) {
					this.houseList=entitys.getRows();
				}
			}
			public List<HouseEntity> getHouseList() {
				return houseList;
			}
			public void setHouseList(List<HouseEntity> houseList) {
				this.houseList = houseList;
			}
		};
		return result;
	}

	/**
	 * @date 2014年4月29日 下午8:44:55
	 * @author Tom  
	 * @description  
	 * 出租 app 页面
	 * 页面列表 通过不同的参数搜索数据
	 */
	@RequestMapping(value = "/findRentByPage.rest", produces="application/json")
	@ResponseBody
	public DeferredResult<Response> findRentByPage(@RequestBody HouseSearchRequest req) {
		log.info("---------------/findRentByPage.rest---------------request：" + JSONObject.fromObject(req).toString());
		req.setSerialCode(areaService.getSerialCodeByAreaId(req.getAreaId()));
		return CommonUtils.deferredResult(queryHouseResourceByPage(this.houseResourceService.queryHouseResourceByPage(req, false)));
	}
	
	/**
	 * @date 2014年4月29日 下午8:44:55
	 * @author Tom  
	 * @description  
	 * 出售  app 页面
	 * 页面列表 通过不同的参数搜索数据
	 */
	@RequestMapping(value = "/findSellByPage.rest", produces="application/json")
	@ResponseBody
	public DeferredResult<Response> findSellByPage(@RequestBody HouseSearchRequest req) {
		log.info("---------------/findSellByPage.rest---------------request：" + JSONObject.fromObject(req).toString());
		req.setSerialCode(areaService.getSerialCodeByAreaId(req.getAreaId()));
		return CommonUtils.deferredResult(queryHouseResourceByPage(this.houseResourceService.queryHouseResourceByPage(req, true)));

	}

}
