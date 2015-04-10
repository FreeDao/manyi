package com.manyi.fyb.callcenter.check.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.manyi.fyb.callcenter.base.controller.BaseController;
import com.manyi.fyb.callcenter.check.model.CheckReq;
import com.manyi.fyb.callcenter.check.model.DistributeRequest;
import com.manyi.fyb.callcenter.check.model.FloorRequest;
import com.manyi.fyb.callcenter.employee.model.EmployeeModel;
import com.manyi.fyb.callcenter.utils.Constants;
import com.manyi.fyb.callcenter.utils.HttpClientHelper;
import com.manyi.fyb.callcenter.utils.SessionUtils;

@Controller
@RequestMapping("/check")
public class CheckController extends BaseController {
	
	/**
	 * 跳转到 对应的 审核 列表页面
	 * @param request
	 * @return
	 */
	@RequestMapping("/checkGrid")
	private String index(HttpServletRequest request ){
		return "check/checkGrid";
	}
	
	/**
	 * 审核列表页面
	 * @param session
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/checkList")
	@ResponseBody
	public String checktaskList(CheckReq req,HttpServletRequest request ) {
//		JSONObject jobj = HttpClientHelper.sendRestInterShort("/rest/check/checkList.rest");
		req.setEmployeeId(SessionUtils.getSession(request).getId());
		//添加参数
		JSONObject jobj = HttpClientHelper.sendRestInterShortObject("/rest/check/checkList.rest",req);
		return jobj.toString();
	}
	
	
	
	@RequestMapping("/floorServ")
	private String floorServ( int id,HttpServletRequest request ){
		
		return "check/checkFloor";
	}
	
	/**
	 * @date 2014年4月22日 下午10:45:11
	 * @author Tom  
	 * @description  
	 * 地推人员审核操作
	 */
	@RequestMapping("/audit4operFloor") 
	@ResponseBody
	private String audit4operFloor(FloorRequest floorRequest) {
		HttpClientHelper.sendRestJsonShortObj2Str("/check/floorServ/audit4operFloor.rest", floorRequest);
		return "success";
	}
	
	/**
	 * @date 2014年4月23日 上午10:28:29
	 * @author Tom  
	 * @description  
	 * 去举报信息审核页面
	 */
	@RequestMapping("/toAudit4operFloor")
	private String toAudit4operFloor(@RequestParam int houseId, Model model) {
		model.addAttribute("houseId", houseId);
		return "/check/checkReport";
	}
	
	/**
	 * @date 2014年4月30日 下午7:11:57
	 * @author Tom  
	 * @description  
	 * 获得iframejsp
	 */
	@RequestMapping("/getIframeJsp")
	private String getIframeJsp() {
		return "/check/checkReportIframe";
	}
	
	
	/**
	 * @date 2014年4月23日 上午11:14:16
	 * @author Tom  
	 * @description  
	 * 加载举报信息审核页面数据
	 */
	@RequestMapping("/loadAuditData")
	@ResponseBody
	private String loadAuditData(FloorRequest floorRequest) {

		
		return HttpClientHelper.sendRestJsonShortObj2Str("/check/floorServ/loadAuditData.rest", floorRequest);
	}
	
	@RequestMapping("/auditSuccess")
	private String auditSuccess(FloorRequest floorRequest, Model model, HttpSession session) {

		EmployeeModel employeeModel = (EmployeeModel)session.getAttribute(Constants.LOGIN_SESSION);
		floorRequest.setEmployeeId(employeeModel.getId());
		
		if (!submitProtectedUtils.sync(1, (Object)floorRequest, employeeModel.getId())) {
			model.addAttribute("operFlag", "error");
		} else {
			HttpClientHelper.sendRestJsonShortObj2Str("/check/floorServ/auditSuccess.rest", floorRequest);
			model.addAttribute("operFlag", "success");
		}

		return "/check/checkReportIframe";
	}
	
	
	
	
	@RequestMapping("/auditFail")
	private String auditFail(FloorRequest floorRequest, Model model, HttpSession session) {

		EmployeeModel employeeModel = (EmployeeModel)session.getAttribute(Constants.LOGIN_SESSION);
		floorRequest.setEmployeeId(employeeModel.getId());
		
		if (!submitProtectedUtils.sync(1, (Object)floorRequest, employeeModel.getId())) {
			model.addAttribute("operFlag", "error");
		} else {
			HttpClientHelper.sendRestJsonShortObj2Str("/check/floorServ/auditFail.rest", floorRequest);
			model.addAttribute("operFlag", "success");
		}
		
		return "/check/checkReportIframe";
	}
	
	/**
	 * @date 2014年5月4日 上午11:06:56
	 * @author Tom  
	 * @description  
	 * 将房源信息分配地推人员
	 */
	@RequestMapping("/distribute2Floor")
	@ResponseBody
	private String distribute2Floor(FloorRequest floorRequest, HttpSession session) {
		
		EmployeeModel employeeModel = (EmployeeModel)session.getAttribute(Constants.LOGIN_SESSION);

		String[] stringHouseId =  floorRequest.getHouseIds().split(",");

		int[] intHouseId = new int[stringHouseId.length];
		for (int i = 0; i < stringHouseId.length; i++) {
			intHouseId[i] = Integer.parseInt(stringHouseId[i]);

		}
		
		DistributeRequest distributeRequest = new DistributeRequest();
		distributeRequest.setManagerId(employeeModel.getId());
		distributeRequest.setEmployeeId(floorRequest.getEmployeeId());
		distributeRequest.setLookStatus(1);
		distributeRequest.setHouseIds(intHouseId);
		
		return HttpClientHelper.sendRestJsonShortObj2Str("/distribute/distribute4Report.rest", distributeRequest);

	}
	
	
	
		
		
	
	

}
