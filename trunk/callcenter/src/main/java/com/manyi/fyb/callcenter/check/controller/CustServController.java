package com.manyi.fyb.callcenter.check.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import lombok.Getter;
import lombok.Setter;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.manyi.fyb.callcenter.base.controller.BaseController;
import com.manyi.fyb.callcenter.base.model.Response;
import com.manyi.fyb.callcenter.check.model.AntiCheatRequest;
import com.manyi.fyb.callcenter.check.model.AntiCheatResponse;
import com.manyi.fyb.callcenter.check.model.CSSingleRequest;
import com.manyi.fyb.callcenter.check.model.CSSingleResponse;
import com.manyi.fyb.callcenter.check.model.CommitCheckAllRequest;
import com.manyi.fyb.callcenter.check.model.DistributeResponse;
import com.manyi.fyb.callcenter.check.model.IsShanghaiResponse;
import com.manyi.fyb.callcenter.employee.model.EmployeeModel;
import com.manyi.fyb.callcenter.index.model.TokenModel;
import com.manyi.fyb.callcenter.utils.Constants;
import com.manyi.fyb.callcenter.utils.HttpClientHelper;
import com.manyi.fyb.callcenter.utils.SessionUtils;

@Controller
@RequestMapping("/check")
public class CustServController extends BaseController{
	
	
	@Getter @Setter
	private Map<String ,TokenModel<CommitCheckAllRequest>> submitCustCheckMap = new HashMap<String ,TokenModel<CommitCheckAllRequest>>();
	
	
	
	@RequestMapping("/cs/single")
	public ModelAndView custServ(CSSingleRequest csc ,@RequestParam(defaultValue="0",required=false) int custServWorkFlowFlag,HttpServletRequest request, ModelAndView mav){
		
		if (csc.getId() == -1) {
			mav.setViewName("check/noData");
			return mav;
		}
		
		if (csc.getId() == 0) {
			mav.setViewName("check/errorData");
			return mav;
		}
		csc.setEmployeeId(SessionUtils.getSession(request).getId());
		CSSingleResponse css = HttpClientHelper.sendRestJsonShortObj2Obj("/check/custServ/single.rest", csc, CSSingleResponse.class);
		mav.addObject("css" , css);
		mav.addObject("custServWorkFlowFlag",custServWorkFlowFlag);
		if (css == null || css.getHouseResource() == null ) {
			logger.error("can not comming into the if case...");
			mav.setViewName("/check/checkSell"); 
			return mav;
		}
		if (css.getHouseResource().getActionType() == 1) {
			mav.setViewName("/check/checkSell");
		}else if (css.getHouseResource().getActionType() == 2) {
			mav.setViewName("/check/checkSell");
		}else if (css.getHouseResource().getActionType() == 3) {
			mav.setViewName("/check/checkSell");
		}else if (css.getHouseResource().getActionType() == 5) {
			mav.setViewName("/check/checkSell");
		}else {
			logger.error("no actionType into the if case...");
			mav.setViewName("/check/checkSell");
		}
		
		return mav;
	}
	
	
	
	@RequestMapping("/cs/startWork")
	public String startWork(HttpServletRequest request){
		JSONObject json = null;
		synchronized (this) {
			json = HttpClientHelper.sendRestInterShortObject("/distribute/autoDistribute.rest",SessionUtils.getSession(request));
		}
		if (json == null) {
			return "redirect:/check/cs/single?id=" + 0 + "&custServWorkFlowFlag=" + 1 ;
		}
		//做完了 没有 其他任务了
		Object dis = JSONObject.toBean(json, DistributeResponse.class);
		if (dis != null && ((Response)dis).getErrorCode() == -1 ) {
			return "redirect:/check/cs/single?id=" + -1 + "&custServWorkFlowFlag=" + 1 ;
		}
		
		if (dis == null || ((Response)dis).getErrorCode() != 0 ) {
			return "redirect:/check/cs/single?id=" + 0 + "&custServWorkFlowFlag=" + 1 ;
		}
		int id = ((DistributeResponse)dis).getId();
		logger.info("houseResourceId:={}" ,id);
		return "redirect:/check/cs/single?id=" + id + "&custServWorkFlowFlag=" + 1 ;
		
	}
	
	
	
	
	
	@RequestMapping("/cs/submitCheckAll")
	@ResponseBody
	public String submitCheck(@Valid  CommitCheckAllRequest ccar, BindingResult result, HttpServletRequest request,HttpServletResponse response) throws IOException{
		if (result.hasErrors()) {
			return "error";
		}
		if (ccar == null) {
			return "error null";
		}
		
		//设置response编码
		
		
		logger.info("submit houseId=:{}",ccar.getHouseId());
		EmployeeModel emp = SessionUtils.getSession(request);
		if(!submitProtectedUtils.sync(1, (Object)ccar, emp.getId())){
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write("请勿连续重复性提交表单!");
			return null;
		}
		
		/*synchronized(this) {
			
			for (TokenModel<CommitCheckAllRequest> sub : submitCustCheckQueue) {
				if () {
					logger.info("submit error return houseId=:{}",ccar.getHouseId());
					return "请勿连续重复性提交表单!";
				}
			}
			
			submitCustCheckQueue.add(tm);
		}*/
		
		logger.info("submit start  houseId=:{}",ccar.getHouseId());
		
		Response response1 = new Response(15860004,"请勿连续重复性提交表单!");
		try {
			response1 = HttpClientHelper.sendRestJsonShortObj2Obj("/check/custServ/submitCheckAll.rest", ccar, Response.class);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.info("submit exception:{}", e);
		}
		//submitCustCheckQueue.remove(tm);
		
		if(response1.getErrorCode() == 0) {
			emp.setFinishedTaskNum(emp.getFinishedTaskNum() + 1);
			return "success";
		}
		else {
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(response1.getMessage());
			return null;
		}
	}
	
	@RequestMapping("/cs/makeCall")
	@ResponseBody
	public String makeCall(String mobile,HttpServletRequest request){
		if (StringUtils.isBlank(mobile) ) {
			return "error mobile";
		}
		final EmployeeModel emp= SessionUtils.getSession(request);
		if (StringUtils.isNotBlank(emp.getUcServerName())) {
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					HttpClientHelper.HttpClientGetShort("?EVENT=QUEUE&type=1&usertype=account&user=" + emp.getUcServerName() + "&orgidentity=" + Constants.UC_ORG_IDENTITY + "&pwdtype=textplain&password=" + emp.getUcServerPwd() ) ;
				}
			}).start();
			
		}
		if (mobile.startsWith("021")) {
			///TODO 上海的 直接打
		}else if (mobile.startsWith("0")) {
			//座机 0打头的 直接打
		}else {
			IsShanghaiResponse response = HttpClientHelper.sendRestJsonShortStr2Obj("/check/custServ/isShanghai.rest", mobile, IsShanghaiResponse.class);
			if (response.getIsShanghai() == 1) {
				//上海本地手机号
			}else {
				mobile = "0" + mobile;
			}
		}
		
		//HttpClientHelper.HttpClientGet("?EVENT=QUEUE&type=1&usertype=account&user=" + employeeModel.getUcServerName() + "&orgidentity=" + Constants.UC_ORG_IDENTITY + "&pwdtype=textplain&password=" + employeeModel.getPassword() ) ;
		return HttpClientHelper.HttpClientGetShort("?EVENT=MAKECALL&targetdn=" +  mobile + "&targettype=exter&agentgroupid=" + emp.getUcServerGroupId() + "&usertype=account&user=" + emp.getUcServerName() + "&orgidentity=" + Constants.UC_ORG_IDENTITY + "&pwdtype=textplain&password=" + emp.getUcServerPwd() + "&modeltype=Campaign&model_id=1&userdata=userdata") ;
		
		
	}
	
	
	@RequestMapping("/cs/antiCheat")
	@ResponseBody
	public ModelAndView antiCheat(AntiCheatRequest anti ,HttpServletRequest request,ModelAndView mav){
		AntiCheatResponse response = HttpClientHelper.sendRestJsonShortObj2Obj("/check/custServ/antiCheat.rest", anti, AntiCheatResponse.class);
		
		if (response == null || response.getList() == null  || response.getList().size() == 0) {
			return null;
		}
		mav.addObject("anti", response);
		mav.setViewName("/check/checkAntiCheat");
		return mav;
	}

}
