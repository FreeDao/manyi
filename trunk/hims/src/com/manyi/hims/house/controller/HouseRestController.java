package com.manyi.hims.house.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.request.async.DeferredResult;

import com.manyi.hims.Global;
import com.manyi.hims.PageResponse;
import com.manyi.hims.Response;
import com.manyi.hims.RestController;
import com.manyi.hims.house.model.HouseDetailRes;
import com.manyi.hims.house.model.HouseReq;
import com.manyi.hims.house.model.HouseRes;
import com.manyi.hims.house.service.HouseService;
import com.manyi.hims.util.CommonUtils;


/**
 * 任务审核模块
 * @author tiger
 *
 */
@Controller
@RequestMapping("/rest/house")
@SessionAttributes(Global.SESSION_UID_KEY)
public class HouseRestController extends RestController{
	
	@Autowired
	@Qualifier("houseService")
	private HouseService houseService;
	
	public void setHouseService(HouseService houseService) {
		this.houseService = houseService;
	}

	/**
	 * 房源信息  列表
	 * @param session
	 * @param req
	 * @return
	 */
	@SuppressWarnings({ "rawtypes" })
	@RequestMapping(value = "/houseList.rest", produces="application/json")
	@ResponseBody
	public DeferredResult<PageResponse> checktaskList(HttpSession session, @RequestBody HouseReq req) {
		final PageResponse<HouseRes> lists = this.houseService.houseList(req);
		DeferredResult<PageResponse> dr = new DeferredResult<PageResponse>();
		dr.setResult(lists);
		return dr;
	}
	
	/**
	 * 编辑 房屋
	 * 
	 * @param session
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/editHouse.rest", produces = "application/json")
	@ResponseBody
	public DeferredResult<Response> editHouse(HttpSession session, @RequestBody HouseDetailRes req) {
		Response response = houseService.editHouse(req);
		return CommonUtils.deferredResult(response);
	}
	
	/**
	 * 编辑 时 , 加载 房屋 页面
	 * 
	 * @param session
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/findHouseById.rest", produces = "application/json")
	@ResponseBody
	public DeferredResult<Response> findHouseById(HttpSession session, @RequestBody HouseReq req) {
		HouseDetailRes response = houseService.findHouseById(req.getHouseId());//房屋Id
		DeferredResult<Response> dr = new DeferredResult<Response>();
		dr.setResult(response);
		return dr;
	}
	
}
