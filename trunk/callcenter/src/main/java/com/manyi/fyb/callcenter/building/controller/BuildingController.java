package com.manyi.fyb.callcenter.building.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.manyi.fyb.callcenter.base.controller.BaseController;
import com.manyi.fyb.callcenter.building.model.BuildingImage;
import com.manyi.fyb.callcenter.building.model.BuildingReq;
import com.manyi.fyb.callcenter.building.model.BuildingRes;
import com.manyi.fyb.callcenter.estate.model.EstateDetailRes;
import com.manyi.fyb.callcenter.estate.model.EstateImage;
import com.manyi.fyb.callcenter.estate.model.EstateRequest;
import com.manyi.fyb.callcenter.utils.HttpClientHelper;
import com.manyi.fyb.callcenter.utils.OSSObjectUtil;

/**
 * 栋座 管理模块
 * 
 * @author tiger
 * 
 */
@Controller
@RequestMapping("/building")
public class BuildingController extends BaseController {
	Logger log =LoggerFactory.getLogger(this.getClass());
	
	/**
	 * 跳转到 对应的 列表页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/buildingGrid")
	private String index(HttpServletRequest request) {
		return "building/buildingGrid";
	}
	
	/**
	 * 楼栋  编辑
	 * @param session
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/editBuilding")
	@ResponseBody
	public String editBuilding(BuildingRes req, HttpServletRequest request) {
		return HttpClientHelper.sendRestJsonShortObj2Str("/rest/building/editBuilding.rest", req);
	}

	
	
	/**
	 * 栋座  编辑页面
	 * 
	 * @param session
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/editBuildingShow")
	public ModelAndView editBuildingShow(BuildingReq req, ModelAndView mav ,HttpServletRequest request) {
		BuildingRes res =HttpClientHelper.sendRestJsonShortObj2Obj("/rest/building/findBuildingById.rest", req,BuildingRes.class);
		if(res != null){
			List<BuildingImage> imgs=	res.getImages();
			if(imgs != null && imgs.size()>0){
				for (int i = 0; i < imgs.size(); i++) {
					String key = imgs.get(i).getImgKey();
					imgs.get(i).setImgKeyStr(OSSObjectUtil.getUrl(key));
				}
			}
		}
		String str = ReflectionToStringBuilder.toString(res);
		log.info ("editBuildingShow 返回的数据 : {} ",str);
		mav.addObject("building", res);
		mav.setViewName("building/buildingEdit");
		return mav;
	}
	
	/**
	 * 栋座 列表页面
	 * 
	 * @param session
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/buildingList")
	@ResponseBody
	public String buildingList(BuildingReq req, HttpServletRequest request) {
		return HttpClientHelper.sendRestInterShortObject("/rest/building/buildingList.rest", req).toString();// 添加参数
	}
	
}
