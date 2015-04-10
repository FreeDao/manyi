package com.manyi.hims.sourcemanage.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.request.async.DeferredResult;

import com.manyi.hims.Global;
import com.manyi.hims.Response;
import com.manyi.hims.contact.service.HouseResourceContactService;
import com.manyi.hims.sourcemanage.model.SourceManageRequest;
import com.manyi.hims.sourcemanage.model.SourceManageResponse;
import com.manyi.hims.sourcemanage.service.SourceManageService;
import com.manyi.hims.util.CommonUtils;

/**
 * @date 2014年4月17日 下午12:49:52
 * @author Tom 
 * @description  
 * 房源管理详情页，控制类
 */
@Controller
@SessionAttributes(Global.SESSION_UID_KEY)
@RequestMapping("sourcemanage")
public class SourceManageController {
	
	@Autowired
	private SourceManageService sourceManageService;
	
	@Autowired
	private HouseResourceContactService houseResourceContactService;
	
	
	/**
	 * @date 2014年4月17日 下午12:54:52
	 * @author Tom  
	 * @description  
	 * 房源基础信息  和  租售信息
	 */
	@RequestMapping(value = "/getSourceBaseInfo.rest", produces="application/json")
	@ResponseBody
	private DeferredResult<Response> getSourceBaseInfo(@RequestBody SourceManageRequest sourceManageRequest, HttpSession session) {
//		房源基础信息
		SourceManageResponse sourceManageResponse = sourceManageService.getSourceBaseInfo(sourceManageRequest.getHouseId());
		
//		租售信息
		sourceManageService.getSourceRentAndSellList(sourceManageResponse, sourceManageRequest.getHouseId());
				
		return CommonUtils.deferredResult(sourceManageResponse);
	}
	

	/**
	 * @date 2014年4月18日 上午9:52:39
	 * @author Tom  
	 * @description  
	 * 业主信息
	 */
	@RequestMapping(value = "/getSourceHostInfo.rest", produces="application/json")
	@ResponseBody
	public DeferredResult<Response> getSourceHostInfo(@RequestBody SourceManageRequest sourceManageRequest) {
		SourceManageResponse sourceManageResponse = new SourceManageResponse();
		//当前
		sourceManageResponse.setHouseContactTrue(houseResourceContactService.getContactList4HouseId(sourceManageRequest.getHouseId(), true));
		//历史
		sourceManageResponse.setHouseContactFalse(houseResourceContactService.getContactList4HouseId(sourceManageRequest.getHouseId(), false));
		
		return CommonUtils.deferredResult(sourceManageResponse);
	}
	
	
	/**
	 * @date 2014年4月18日 上午9:52:39
	 * @author Tom  
	 * @description  
	 * 审核记录
	 */
	@RequestMapping(value = "/getAuditMessageInfo.rest", produces="application/json")
	@ResponseBody
	public DeferredResult<Response> getAuditMessageInfo(@RequestBody SourceManageRequest sourceManageRequest) {
		
		return CommonUtils.deferredResult(sourceManageService.getAuditMessageInfo(sourceManageRequest.getHouseId()));
	}
	
	/**
	 * @date 2014年4月20日 下午4:45:22
	 * @author Tom  
	 * @description  
	 * 修改房源
	 */
	@RequestMapping(value = "/updateSourceManage.rest", produces="application/json")
	@ResponseBody
	public DeferredResult<Response> updateSourceManage(@RequestBody SourceManageRequest sourceManageReques) {
		
		return CommonUtils.deferredResult(sourceManageService.updateSourceManage(sourceManageReques));

	}
	
	/**
	 * @date 2014年4月18日 上午9:52:39
	 * @author Tom  
	 * @description  
	 * 房源修改记录
	 */
	@RequestMapping(value = "/getSourceEditInfo.rest", produces="application/json")
	@ResponseBody
	public DeferredResult<Response> getSourceEditInfo(@RequestBody SourceManageRequest sourceManageRequest) {
		
		return CommonUtils.deferredResult(sourceManageService.getSourceEditInfo(sourceManageRequest.getHouseId()));
	}

	/**
	 * @date 2014年4月20日 下午4:45:22
	 * @author Tom  
	 * @description  
	 * 删除房源信息，假删除
	 */
	@RequestMapping(value = "/deleteSourceInfo.rest", produces="application/json")
	@ResponseBody
	public DeferredResult<Response> deleteSourceInfo(@RequestBody SourceManageRequest sourceManageReques) {
		sourceManageService.deleteSourceInfo(sourceManageReques.getHouseId(), sourceManageReques.getOperatorId());
		
		return CommonUtils.deferredResult(new SourceManageResponse());

	}
	
	
	
		
}
