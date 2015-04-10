package com.manyi.fyb.callcenter.estate.controller;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.manyi.fyb.callcenter.base.controller.BaseController;
import com.manyi.fyb.callcenter.base.model.Response;
import com.manyi.fyb.callcenter.estate.model.AddEstateRequest;
import com.manyi.fyb.callcenter.estate.model.EstateDetailRes;
import com.manyi.fyb.callcenter.estate.model.EstateImage;
import com.manyi.fyb.callcenter.estate.model.EstateReq;
import com.manyi.fyb.callcenter.estate.model.EstateRequest;
import com.manyi.fyb.callcenter.estate.model.EstateResponse;
import com.manyi.fyb.callcenter.estate.model.EstateVerifyReq;
import com.manyi.fyb.callcenter.utils.HttpClientHelper;
import com.manyi.fyb.callcenter.utils.OSSObjectUtil;

@Controller
@RequestMapping("/estate")
@SuppressWarnings("deprecation")
public class EstateController extends BaseController {
	Logger log =LoggerFactory.getLogger(this.getClass());
	
	/**
	 * 小区 详情页面
	 * 
	 * @param session
	 * @param req
	 * @returnd
	 */
	@RequestMapping(value = "/detailEstate")
	@ResponseBody
	public EstateResponse detailEstate(EstateRequest req, HttpServletRequest request) {
		return HttpClientHelper.sendRestJsonShortObj2Obj("/rest/estate/detailEstate.rest", req ,EstateResponse.class);
	}
	
	/**
	 * 小区 编辑页面
	 * 
	 * @param session
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/editEstateShow")
	public ModelAndView editEstateShow(EstateRequest req, ModelAndView mav ,HttpServletRequest request) {
		EstateDetailRes res =HttpClientHelper.sendRestJsonShortObj2Obj("/rest/estate/findEstateById.rest", req,EstateDetailRes.class);
		if(res != null){
			List<EstateImage> imgs=	res.getImages();
			if(imgs != null && imgs.size()>0){
				for (int i = 0; i < imgs.size(); i++) {
					String key = imgs.get(i).getImgKey();
					imgs.get(i).setImgKeyStr(OSSObjectUtil.getUrl(key));
				}
			}
			if(res.getEstateRangeImgKey() != null){
				res.setEstateRangeImgKeyStr(OSSObjectUtil.getUrl(res.getEstateRangeImgKey()));
			}
		}
		String str = ReflectionToStringBuilder.toString(res);
		log.info ("editEstateShow 返回的数据 : {} ",str);
		mav.addObject("estate", res);
		mav.setViewName("estate/estateaEdit");
		return mav;
	}
	
//	/**
//	 * 小区 详情页面
//	 * 
//	 * @param session
//	 * @param req
//	 * @returnd
//	 */
//	@RequestMapping(value = "/detailEstate")
//	@ResponseBody
//	public String detailEstate(EstateRequest req, HttpServletRequest request) {
//		return HttpClientHelper.sendRestJsonShortObj2Str("/rest/estate/detailEstate.rest", req);
//	}
//	
	/**
	 * 小区 编辑
	 * @param session
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/editEstate")
	@ResponseBody
	public String editEstate(EstateDetailRes req, HttpServletRequest request) {
		return HttpClientHelper.sendRestJsonShortObj2Str("/rest/estate/editEstate.rest", req);
	}

	/**
	 * 后台直接新增加小区
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/addEstate")
	@ResponseBody
	private Response addEstate(AddEstateRequest addEstateRequest, HttpServletRequest request) {
		return HttpClientHelper.sendRestJsonShortObj2Obj("/rest/estate/addEstate.rest", addEstateRequest,Response.class);
	}

	/**
	 * 跳转到 对应的 审核 列表页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/estateGrid")
	private String index(HttpServletRequest request) {
		return "estate/estateGrid";
	}

	/**
	 * 跳转到 对应的 列表页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/estateLogGrid")
	private String indexLog(HttpServletRequest request) {
		return "estate/estateLogGrid";
	}

	/**
	 * 小区 列表页面
	 * 
	 * @param session
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/estateList")
	@ResponseBody
	public String estateList(EstateReq req, HttpServletRequest request) {
		return HttpClientHelper.sendRestInterShortObject("/rest/estate/estateList.rest", req).toString();// 添加参数
	}

	/**
	 * 小区 列表页面
	 * 
	 * @param session
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/estateLogList")
	@ResponseBody
	public String estateLogList(EstateReq req, HttpServletRequest request) {
		return HttpClientHelper.sendRestInterShortObject("/rest/estate/estateLogList.rest", req).toString();// 添加参数
	}

	/**
	 * 小区审核 操作完成获得最新的 estateLogList 列表
	 * 
	 * @param req
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/estateVerify")
	@ResponseBody
	public Response estateVerify(EstateVerifyReq verifyModel, HttpServletRequest request) {
		return HttpClientHelper.sendRestJsonShortObj2Obj("/rest/estate/estateVerify.rest", verifyModel, Response.class);
	}
	
	@RequestMapping("/floorServ")
	private String floorServ(int id, HttpServletRequest request) {
		return "check/checkFloor";
	}

	/**
	 * @date 2014年4月21日 下午3:13:04
	 * @author Tom
	 * @description 进入增加小区页面
	 */
	@RequestMapping("/toAddEstate")
	private String toAddEstate(EstateRequest estateRequest) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("areaId", estateRequest.getAreaId());
		// JSONObject json = HttpClientHelper.sendRestInterShort("/estate/findEstateById.rest", map);
		return "/estate/estateadd";
	}

	/**
	 * @date 2014年4月21日 下午3:32:48
	 * @author Tom
	 * @description 动态获取小区名称
	 */
	@RequestMapping("/getEstateByName")
	@ResponseBody
	private String getEstateByName(@RequestParam String term) {
		Map<String, Object> map = new HashMap<String, Object>();

		String estateName = null;
		try {
			estateName = new String(term.getBytes("ISO-8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		map.put("estateName", estateName);
		// 数组格式
		// String str = "[\"玉兰香苑\", \"Ada\", \"Adamsville\", \"Addyston\", \"Adelphi\", \"Adena\", \"Adrian\", \"Akron\"]";
		JSONObject json = HttpClientHelper.sendRestInterShort("/estate/getEstateByName.rest", map);

		if (json == null || json.getInt("errorCode") != 0) {
			return "error";
		}
		return json.getString("estateNameStr");
	}

	/**
	 * @date 2014年4月21日 下午3:32:48
	 * @author Tom
	 * @description 动态获取小区名称
	 */
	@RequestMapping("/getSubEstateList4EstateName")
	@ResponseBody
	private String getSubEstateList4EstateName(@RequestBody EstateRequest estateRequest) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("estateName", estateRequest.getEstateName());
		// JSONObject json = HttpClientHelper.sendRestInterShort("/estate/getEstateByName.rest", map);
		//
		// if (json == null || json.getInt("errorCode") != 0) {
		// return "error";
		// }
		return null;
	}

	/**
	 * @date 2014年4月15日 下午3:21:25
	 * @author Tom
	 * @description 查看小区详情: 当审核通过、未审核 跳转到不同的页面
	 */
	@RequestMapping("/detail4auditpass")
	private String auditPass(EstateRequest estateRequest) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("areaId", estateRequest.getAreaId());

		// JSONObject json = HttpClientHelper.sendRestInterShort("/estate/findEstateById.rest", map);

		/*
		 * if (true) { // 小区管理详情页（未审核） return "/estate/notaudit";
		 * 
		 * } else { // 小区管理详情（审核成功） return "/estate/auditpass"; }
		 */
		return "/estate/auditpass";

	}

	/**
	 * @date 2014年4月15日 下午3:21:25
	 * @author Tom
	 * @description 查看小区详情: 当审核通过、未审核 跳转到不同的页面
	 */
	@RequestMapping("/detail4notpass")
	private String auditNotPass(EstateRequest estateRequest, Model model) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("areaId", estateRequest.getAreaId());
		/*
		 * JSONObject json = HttpClientHelper.sendRestInterShort("/estate/findNotPassEstateById.rest", map);
		 * 
		 * EstateResponse estateResponse = (EstateResponse)JSONObject.toBean(json, EstateResponse.class);
		 * 
		 * model.addAttribute(estateResponse);
		 */

		return "/estate/notaudit";

	}

	/**
	 * @date 2014年4月15日 下午3:21:25
	 * @author Tom
	 * @description 查看小区详情: 当审核通过、未审核 跳转到不同的页面
	 */
	@RequestMapping("/auditpass")
	private String auditpass(EstateRequest estateRequest) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("areaId", estateRequest.getAreaId());
		return "/estate/notaudit";
	}
}
