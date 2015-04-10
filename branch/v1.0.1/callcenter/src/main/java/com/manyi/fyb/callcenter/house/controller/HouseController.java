package com.manyi.fyb.callcenter.house.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.manyi.fyb.callcenter.base.controller.BaseController;
import com.manyi.fyb.callcenter.house.model.EstateReq;
import com.manyi.fyb.callcenter.house.model.HouseReq;
import com.manyi.fyb.callcenter.house.model.LoadAreaByParentIdReq;
import com.manyi.fyb.callcenter.utils.HttpClientHelper;

@Controller
@RequestMapping("/house")
public class HouseController extends BaseController {
	
	/**
	 * 跳转到 对应的 审核 列表页面
	 * @param request
	 * @return
	 */
	@RequestMapping("/houseGrid")
	private String index(HttpServletRequest request ){
		return "house/houseGrid";
	}
	
	/**
	 * 房源 列表页面
	 * @param session
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/houseList")
	@ResponseBody
	public String houseList(HouseReq req,HttpServletRequest request ) {
//		JSONObject jobj = HttpClientHelper.sendRestInterShort("/rest/check/checkList.rest");
		
		//添加参数
		JSONObject jobj = HttpClientHelper.sendRestInterShortObject("/rest/house/houseList.rest",req);
		return jobj.toString();
	}
	
	/**
	 * 通过不同的ID加载 不同的 区域板块
	 * @param session
	 * @param req
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/loadAreaByParentId")
	@ResponseBody
	public JSONObject loadAreaByParentId(LoadAreaByParentIdReq req,HttpServletRequest request,HttpServletResponse response ){
		//添加参数
		JSONObject jobj = HttpClientHelper.sendRestInterShortObject("/rest/common/findAreaByParentId.rest",req);
		return jobj;
	}
	/**
	 * 通过名字模糊查询出来小区列表
	 * @param session
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/findEstateByName", produces="application/json")
	@ResponseBody
	public String findEstateByName(EstateReq req,HttpServletRequest request ) {
		//添加参数
		JSONObject jobj = HttpClientHelper.sendRestInterShortObject("/rest/common/findEstateByName.rest",req);
		return jobj.toString();
	}
	

}
