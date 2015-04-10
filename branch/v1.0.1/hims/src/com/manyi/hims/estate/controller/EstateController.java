package com.manyi.hims.estate.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.request.async.DeferredResult;

import com.manyi.hims.Global;
import com.manyi.hims.Response;
import com.manyi.hims.RestController;
import com.manyi.hims.estate.model.EstateRequest;
import com.manyi.hims.estate.model.EstateResponse;
import com.manyi.hims.estate.service.EstateService;
import com.manyi.hims.util.CommonUtils;

/**
 * @date 2014年4月15日 下午2:20:45
 * @author Tom
 * @description
 * 
 */
@Controller
@SessionAttributes(Global.SESSION_UID_KEY)
@RequestMapping("/estate")
public class EstateController extends RestController {

	@Autowired
	private EstateService estateService;

	/**
	 * @date 2014年4月15日 下午4:00:39
	 * @author Tom
	 * @description 根据areaId返回EstateResponse
	 */
	@RequestMapping(value = "/findNotPassEstateById.rest", produces = "application/json")
	@ResponseBody
	public DeferredResult<Response> findNotPassEstateById(@RequestBody EstateRequest estateRequest) {
		EstateResponse estateResponse = estateService.findNotPassEstateById(Integer.parseInt(estateRequest.getAreaId()));
		return CommonUtils.deferredResult(estateResponse);

	}

	/**
	 * @date 2014年4月22日 下午5:24:48
	 * @author Tom
	 * @description 根据小区名称查询小区
	 */
	@RequestMapping(value = "/getEstateByName.rest", produces = "application/json")
	@ResponseBody
	public DeferredResult<Response> getEstateByName(@RequestBody EstateRequest estateRequest) {
		List<Object> list = estateService.getEstateByName(estateRequest.getEstateName());
		StringBuffer sb = new StringBuffer("[");
		boolean isFirst = true;
		for (Object obj : list) {
			if (isFirst) {
				sb.append("\"");
				isFirst = false;
			} else {
				sb.append(", \"");
			}
			sb.append(obj.toString());
			sb.append("\"");
		}
		sb.append("]");
		EstateResponse estateResponse = new EstateResponse();
		estateResponse.setEstateNameStr(sb.toString());
		return CommonUtils.deferredResult(estateResponse);
	}
}
