package com.manyi.fyb.callcenter.house.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.manyi.fyb.callcenter.base.controller.BaseController;
import com.manyi.fyb.callcenter.estate.model.EstateDetailRes;
import com.manyi.fyb.callcenter.house.model.EstateReq;
import com.manyi.fyb.callcenter.house.model.HouseDetailRes;
import com.manyi.fyb.callcenter.house.model.HouseImageFile;
import com.manyi.fyb.callcenter.house.model.HouseReq;
import com.manyi.fyb.callcenter.house.model.HouseSupportingMeasures;
import com.manyi.fyb.callcenter.house.model.LoadAreaByParentIdReq;
import com.manyi.fyb.callcenter.utils.HttpClientHelper;
import com.manyi.fyb.callcenter.utils.OSSObjectUtil;

@Controller
@RequestMapping("/house")
public class HouseController extends BaseController {
	Logger log =LoggerFactory.getLogger(this.getClass());
	
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
	
	
	
	
	/**
	 * 房源  编辑
	 * @param session
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/editHouse")
	@ResponseBody
	public String editBuilding(HouseDetailRes req , HouseSupportingMeasures hsm, HttpServletRequest request) {
		hsm.setHouseId(req.getHouseId());
		req.setSupporing(hsm);
		return HttpClientHelper.sendRestJsonShortObj2Str("/rest/house/editHouse.rest", req);
	}

	
	
	/**
	 * 房源 编辑页面
	 * 
	 * @param session
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/editHouseShow")
	public ModelAndView editBuildingShow(HouseReq req, ModelAndView mav ,HttpServletRequest request) {
		HouseDetailRes res =HttpClientHelper.sendRestJsonShortObj2Obj("/rest/house/findHouseById.rest", req,HouseDetailRes.class);
		if(res != null){
			List<HouseImageFile> imgs=	res.getImages();
			if(imgs != null && imgs.size()>0){
				for (int i = 0; i < imgs.size(); i++) {
					String key = imgs.get(i).getImgKey();
					imgs.get(i).setImgKeyStr(OSSObjectUtil.getUrl(key));
				}
			}
		}
		String str = ReflectionToStringBuilder.toString(res);
		log.info ("editHouseShow 返回的数据 : {} ",str);
		mav.addObject("house", res);
		mav.setViewName("house/houseEdit");
		return mav;
	}

}
