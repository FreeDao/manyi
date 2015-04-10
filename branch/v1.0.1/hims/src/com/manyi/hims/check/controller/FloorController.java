package com.manyi.hims.check.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.request.async.DeferredResult;

import com.manyi.hims.Global;
import com.manyi.hims.Response;
import com.manyi.hims.RestController;
import com.manyi.hims.check.model.FloorRequest;
import com.manyi.hims.check.model.FloorResponse;
import com.manyi.hims.check.service.CheckService;
import com.manyi.hims.check.service.ReportService;
import com.manyi.hims.contact.service.HouseResourceContactService;
import com.manyi.hims.entity.HouseResourceTemp;
import com.manyi.hims.house.service.HouseService;
import com.manyi.hims.houseresource.service.HouseResourceService;
import com.manyi.hims.sourcemanage.service.SourceManageService;
import com.manyi.hims.util.CommonUtils;
import com.manyi.hims.util.EntityUtils.HouseStateEnum;

@Controller
@SessionAttributes(Global.SESSION_UID_KEY)
@RequestMapping("/check")
public class FloorController extends RestController{
	
	@Autowired
	@Qualifier("checkService")
	private CheckService checkService;
	
	@Autowired
	private HouseResourceContactService houseResourceContactService;
	
	@Autowired
	private SourceManageService sourceManageService;
	
	@Autowired
	private HouseService houseService;
	
	@Autowired
	private ReportService reportService;
	
	@Autowired
	private HouseResourceService houseResourceService;
	
	@RequestMapping(value = "/floorServ/single.rest", produces = "application/json")
	@ResponseBody
	public DeferredResult<Response> floorServ(HttpServletRequest request ){
		
		return CommonUtils.deferredResult(new Response());
	}
	
	/**
	 * @date 2014年4月22日 下午10:24:39
	 * @author Tom  
	 * @description  
	 * 地推人员审核操作
	 */
	@RequestMapping(value = "/floorServ/audit4operFloor.rest", produces = "application/json")
	public DeferredResult<Response> audit4operFloor(@RequestBody FloorRequest floorRequest){
		checkService.audit4operFloor(floorRequest);
		return CommonUtils.deferredResult(new Response());
	}
	
	/**
	 * @date 2014年4月26日 下午3:42:51
	 * @author Tom  
	 * @description  
	 * 构造理由信息
	 */
	private String getReportHouseResourceMsg(HouseResourceTemp houseResourceTemp) {
//		1出租，2出售，3即租又售，4即不租也不售
		return HouseStateEnum.getByValue(houseResourceTemp.getHouseState()).getDesc();
	}
	
	
	/**
	 * @date 2014年4月23日 下午3:10:16
	 * @author Tom  
	 * @description  
	 * 加载举报信息审核页面数据
	 */
	@RequestMapping(value = "/floorServ/loadAuditData.rest", produces = "application/json")
	@ResponseBody
	public DeferredResult<Response> loadAuditData(@RequestBody FloorRequest floorRequest) {
		
		HouseResourceTemp houseResourceTemp = houseResourceService.getHouseResourceTemp(floorRequest.getHouseId());
		FloorResponse floorResponse = new FloorResponse();
		
//		加载举报信息  
		floorResponse.setReportMsg(this.getReportHouseResourceMsg(houseResourceTemp));
		
//		加载举报理由 
		floorResponse.setRemark(houseResourceService.getHouseResourceByHouseId(floorRequest.getHouseId()).getRemark());
		
//		房屋信息
		floorResponse.setHouseInfoMsg(houseService.getHouseMsg(floorRequest.getHouseId()));
		
//		原发布出售/出租的经纪人信息
		floorResponse.setUserStr(new ArrayList<String>());
		floorResponse.getUserStr().add(checkService.getUser4UserId(houseResourceTemp.getUserId()));
		
//		加载当前业主信息
		floorResponse.setHouseResourceContact(houseResourceContactService.getContactList4HouseId(floorRequest.getHouseId(), true));
		
//		举报记录
		floorResponse.setAuditList(sourceManageService.getAuditMessageInfo(floorRequest.getHouseId()).getAuditList());
		
		return CommonUtils.deferredResult(floorResponse);
	}
	
	/**
	 * @date 2014年4月30日 下午5:30:15
	 * @author Tom  
	 * @description  
	 * 举报:	审核通过
	 * 
	 * 操作步骤如下：
	 * 1、更新houseResourceHistory原有对象的状态为通过status 2
	 * 2、将houseResourceTemp数据写入 houseResourceHistory
	 * 3、更新houseResource对象
	 * 4、删除houseResourceTemp对象
	 * 5、调用支付接口
	 */
	@RequestMapping(value = "/floorServ/auditSuccess.rest", produces = "application/json")
	@ResponseBody
	public DeferredResult<Response> auditSuccess(@RequestBody FloorRequest floorRequest) {
		
		return CommonUtils.deferredResult(reportService.auditSuccess(floorRequest));
		
	}
	
	
	/**
	 * @date 2014年4月30日 下午5:30:15
	 * @author Tom  
	 * @description  
	 * 举报:	审核失败
	 * 
	 * 操作步骤如下：
	 * 1、更新houseResourceHistory原有对象的状态为失败status 3
	 * 2、将houseResource数据写入 houseResourceHistory
	 * 3、将houseResourceTemp对象还原，到houseResource对象
	 * 4、删除houseResourceTemp对象
	 */
	@RequestMapping(value = "/floorServ/auditFail.rest", produces = "application/json")
	@ResponseBody
	public DeferredResult<Response> auditFail(@RequestBody FloorRequest floorRequest) {
		
		return CommonUtils.deferredResult(reportService.auditFail(floorRequest));
	}

}
