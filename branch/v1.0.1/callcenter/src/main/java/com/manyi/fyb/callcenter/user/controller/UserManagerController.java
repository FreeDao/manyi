package com.manyi.fyb.callcenter.user.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.JsonObject;
import com.manyi.fyb.callcenter.base.controller.BaseController;
import com.manyi.fyb.callcenter.base.model.Response;
import com.manyi.fyb.callcenter.check.model.CommitCheckAllRequest;
import com.manyi.fyb.callcenter.employee.model.EmployeeModel;
import com.manyi.fyb.callcenter.employee.model.EmployeeResponse;
import com.manyi.fyb.callcenter.index.model.TokenModel;
import com.manyi.fyb.callcenter.user.model.UserRequest;
import com.manyi.fyb.callcenter.user.model.UserResponse;
import com.manyi.fyb.callcenter.utils.Constants;
import com.manyi.fyb.callcenter.utils.HttpClientHelper;
import com.manyi.fyb.callcenter.utils.SessionUtils;
import com.manyi.fyb.callcenter.utils.SubmitProtectedUtils;
@Controller
@RequestMapping("/user")
public class UserManagerController extends BaseController{
//	@Getter @Setter
//	private Map<String ,TokenModel<UpdateUserStateRequest>> updateUserState = new HashMap<String ,TokenModel<UpdateUserStateRequest>>();
//	
//	@Getter @Setter
//	private Map<String ,TokenModel<UserRequest>> addUser = new HashMap<String ,TokenModel<UserRequest>>();
	
	
	@RequestMapping("/userIndex")
	public String employeeIndex(){
		return "user/userIndex";
	}
	
	@RequestMapping("/getUser")
	@ResponseBody
	public String getUser(HttpServletRequest request,GetUserRequest userRequest){
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("page", userRequest.getPage());
//		map.put("rows", userRequest.getRows());
//		map.put("type", userRequest.getType());
//		map.put("realName", userRequest.getRealName());
//		map.put("spreandName", userRequest.getSpreandName());
//		JSONObject json = HttpClientHelper.sendRestInter(Constants.LINK_FRONT_PART+"/userManager/getUser.rest", userRequest);
		JSONObject json = HttpClientHelper.sendRestInterShortObject("/userManager/getUser.rest", userRequest);
		return json.toString();
	}
	
	@SuppressWarnings("static-access")
	@RequestMapping("/getUserById")
	public String getUserById(HttpServletRequest request,GetUserByIdRequest userRequest){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("uid", userRequest.getUid());
		JSONObject json = HttpClientHelper.sendRestInter(Constants.LINK_FRONT_PART+"/userManager/getUserById.rest", map);
		UserResponse result = (UserResponse)json.toBean(json,UserResponse.class);
		if(result!=null){
			request.setAttribute("result", result);
			if(result.getState()==1){
				return "user/examineUesrSuccess";
			}
			if(result.getState()==2){
				return "user/examineUesr";
			}
			if(result.getState()==3){
				return "user/examineUesrFail";
			}
		}
		return "user/userIndex";
	}
	
	@RequestMapping("/updateUserState")
	public String updateUserState(HttpServletRequest request,UpdateUserStateRequest updateUserStateRequest){
		if (updateUserStateRequest == null) {
			return "error null";
		}
		
		EmployeeModel emp = SessionUtils.getSession(request);
		updateUserStateRequest.setOperator(emp.getId());
		TokenModel<UpdateUserStateRequest> tm = new TokenModel<UpdateUserStateRequest>(1,1,updateUserStateRequest);
//		synchronized (updateUserState) {
//			TokenModel<UpdateUserStateRequest> tmLast = updateUserState.get(String.valueOf(emp.getId()));
//			if (tmLast != null ) {
//				if (tm.getTokenObject().equals(tmLast.getTokenObject())) {
//					return "user/userIndex";
//				}
//			}
//			updateUserState.put(String.valueOf(emp.getId()), tm);
//			
//		}
		
		if(!submitProtectedUtils.sync(1, updateUserStateRequest, emp.getId())){
			return "user/userIndex";
		}
		HttpClientHelper.sendRestInterShortObject("/userManager/updateUserState.rest", updateUserStateRequest);
		return "user/userIndex";
	}
	
	@RequestMapping("/disabledUser")
	public String disabledUser(HttpServletRequest request,GetUserByIdRequest disabledRequest){
		EmployeeModel model = (EmployeeModel)request.getSession().getAttribute(Constants.LOGIN_SESSION);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("uid", disabledRequest.getUid());
		map.put("operator", model.getId());
		JSONObject json = HttpClientHelper.sendRestInter(Constants.LINK_FRONT_PART+"/userManager/disabledUser.rest", map);
		UserResponse result = (UserResponse)json.toBean(json,UserResponse.class);
		return "user/userIndex";
	}
	
	@RequestMapping("/addUser")
	@ResponseBody
	public JSONObject addUser(HttpServletRequest request,UserRequest userRequest){
		EmployeeModel emp = SessionUtils.getSession(request);
		userRequest.setOperator(emp.getId());
//		TokenModel<UserRequest> tm = new TokenModel<UserRequest>(1,1,userRequest);
//		synchronized (addUser) {
//			TokenModel<UpdateUserStateRequest> tmLast = updateUserState.get(String.valueOf(emp.getId()));
//			if (tmLast != null ) {
//				if (tm.getTokenObject().equals(tmLast.getTokenObject())) {
//					return null;
//				}
//			}
//			addUser.put(String.valueOf(emp.getId()), tm);
//			
//		}
		
		if(!submitProtectedUtils.sync(1, userRequest, emp.getId())){
			return null;
		}
		JSONObject json = HttpClientHelper.sendRestInterShortObject("/userManager/addUser.rest", userRequest);
		System.out.println(json);
		return json;
	}
	/**
	 * 发布记录
	 * @author fangyouhui
	 *
	 */
	@RequestMapping("/getUserSourceResource")
	@ResponseBody
	public JSONObject getUserSourceResource(HttpServletRequest request,SourceLogRquest sourceLogRquest){
		JSONObject json = HttpClientHelper.sendRestInterShortObject("/rest/sourceLog/findPageLog.rest", sourceLogRquest);
		System.out.println(json);
		return json;
	}
	
	/**
	 * 审核历史记录
	 * @author fangyouhui
	 *
	 */
	@RequestMapping("/getUserHistory")
	@ResponseBody
	public JSONObject getUserHistory(HttpServletRequest request,getUserHistoryRequest getUserHistoryRequest){
		JSONObject json = HttpClientHelper.sendRestInterShortObject("/userManager/getUserHistory.rest", getUserHistoryRequest);
		System.out.println(json);
		return json;
	}
	
	public static class getUserHistoryRequest{
		@Getter @Setter private Integer page;
		@Getter @Setter private Integer rows=20;
		@Getter @Setter private int uid;
	}
	@Data
	public static class SourceLogRquest{
		private int userId;
		private int start=0;
		private int end=20;
		private Long markTime;

		
	}

	@Data
	public static class GetUserRequest{
		private Integer page=0;
		private Integer rows=20;
		private int type;
		private String realName;
		private String spreandName;
		private String mobile;
		private int state;
	}
	@Data
	public static class GetUserByIdRequest{
		private int uid;
		private int operator;
		
	}
	@Data
	public static  class UpdateUserStateRequest{
		private int uid;
		private int state;
		private String doorName;
		private String doorNumber;
		private String remark;
		private int operator;
		
	}
	
}
