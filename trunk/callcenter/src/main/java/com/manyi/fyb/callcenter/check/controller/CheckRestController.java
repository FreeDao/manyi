package com.manyi.fyb.callcenter.check.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.DeferredResult;

import com.manyi.fyb.callcenter.base.controller.BaseController;
import com.manyi.fyb.callcenter.check.model.CheckReq;
import com.manyi.fyb.callcenter.check.model.CheckRes;
import com.manyi.fyb.callcenter.common.model.PageResponse;
import com.manyi.fyb.callcenter.utils.HttpClientHelper;

@Controller
@RequestMapping("/rest/check")
public class CheckRestController extends BaseController {
	
	@RequestMapping("/index2")
	private String index(HttpServletRequest request ){
		System.out.println("111");
		return null;
	}
	
	/**
	 * 
	 * @param session
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/checkList")
	@ResponseBody
	public DeferredResult<PageResponse<CheckRes>> checktaskList(HttpSession session, @RequestBody CheckReq req) {
//		JSONObject jobj = HttpClientHelper.sendRestInter("/rest/check/checkList.rest");
		JSONObject jobj =HttpClientHelper.sendRestInterShortObject("/rest/check/checkList.rest", req);
		System.out.println(jobj.toString());
		PageResponse<CheckRes> result = new PageResponse<CheckRes>() {
		};
		DeferredResult<PageResponse<CheckRes>> dr = new DeferredResult<PageResponse<CheckRes>>();
		dr.setResult(result);
		return dr;
	}
	

}
