package com.manyi.hims.user.conroller;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.DeferredResult;

import com.leo.common.Page;
import com.manyi.hims.PageResponse;
import com.manyi.hims.Response;
import com.manyi.hims.RestController;
import com.manyi.hims.user.model.UserRequest;
import com.manyi.hims.user.service.UserManagerService;
import com.manyi.hims.user.service.UserManagerService.UserHistoryList;
import com.manyi.hims.user.service.UserManagerService.UserResponse;
import com.manyi.hims.user.service.UserManagerService.UserUpdateRequest;
@Controller
@RequestMapping("/userManager")
public class UserManagerController extends RestController{
	@Autowired
	@Qualifier("userManagerService")
	private UserManagerService userManagerService;
	
	public UserManagerService getUserManagerService() {
		return userManagerService;
	}

	public void setUserManagerService(UserManagerService userManagerService) {
		this.userManagerService = userManagerService;
	}
	
	@RequestMapping("/getUser.rest")
	@ResponseBody
	public DeferredResult<Response> getUser(@RequestBody getUserRequest request){
		Page<UserResponse> result =  this.getUserManagerService().getUser(request,request.getPage(), request.getRows(),request.getRealName(),request.getSpreandName(),request.getType(),request.getMobile(),request.getState());
		PageResponse<UserResponse> rows = new PageResponse<UserResponse>(); 
		BeanUtils.copyProperties(result, rows);
		DeferredResult<Response> dr = new DeferredResult<Response>();
		dr.setResult(rows);
		return dr;
	}
	
	@RequestMapping("/getUserById.rest")
	@ResponseBody
	public DeferredResult<Response> getUserById(@RequestBody GetUserByIdRequest request){
		UserResponse result =  this.getUserManagerService().getUserById(request.getUid());
		DeferredResult<Response> dr = new DeferredResult<Response>();
		dr.setResult(result);
		return dr;
	}
	
	@RequestMapping("/updateUserState.rest")
	@ResponseBody
	public DeferredResult<Response> updateUserState(@RequestBody UpdateUserStateRequest request){
		
		this.getUserManagerService().updateUserState(request.getUid(), request.getState(),request.getDoorName(),request.getDoorNumber(),request.getRemark(),request.getOperator());
		 
		DeferredResult<Response> dr = new DeferredResult<Response>();
		dr.setResult(new Response());
		return dr;
	}
	
	@RequestMapping("/disabledUser.rest")
	@ResponseBody
	public DeferredResult<Response> disabledUser(@RequestBody GetUserByIdRequest request){
		
		this.getUserManagerService().disabledUser(request.getUid(),request.getOperator());
		 
		DeferredResult<Response> dr = new DeferredResult<Response>();
		dr.setResult(new Response());
		return dr;
	}
	
	@RequestMapping("/addUser.rest")
	@ResponseBody
	public DeferredResult<Response> addUser(@RequestBody UserRequest request){
		
		this.getUserManagerService().addUser(request);
		 
		DeferredResult<Response> dr = new DeferredResult<Response>();
		dr.setResult(new Response());
		return dr;
	}
	
	@RequestMapping("/getUserHistory.rest")
	@ResponseBody
	public DeferredResult<Response> getUserHistory(@RequestBody getUserHistoryRequest request){
		Page<UserHistoryList> result =  this.getUserManagerService().getUserHistory(request.getPage(), request.getRows(),request.getUid());
		PageResponse<UserHistoryList> rows = new PageResponse<UserHistoryList>(); 
		BeanUtils.copyProperties(result, rows);
		DeferredResult<Response> dr = new DeferredResult<Response>();
		dr.setResult(rows);
		return dr;
	}
	
	@RequestMapping("/updateUser.rest")
	@ResponseBody
	public DeferredResult<Response> updateUser(@RequestBody UserUpdateRequest request){
		
		this.getUserManagerService().updateUser(request);
		 
		DeferredResult<Response> dr = new DeferredResult<Response>();
		dr.setResult(new Response());
		return dr;
	}
	public static class getUserHistoryRequest{
		@Getter @Setter private Integer page;
		@Getter @Setter private Integer rows=20;
		@Getter @Setter private int uid;
		
	}
	
	@Data
	public static class getUserRequest{
		private Integer page=0;
		private Integer rows=20;
		private String realName;
		private String spreandName;
		private String mobile;
		private int state;
		private int type;
		private int cityId;
		private String startRegistDate;
		private String endRegistDate;
		private String recommendedMobile;
		
	}
	
	public static class UpdateUserStateRequest{
		private int uid;
		private int state;
		private String doorName;
		private String doorNumber;
		private String remark;
		private int operator;
		public int getUid() {
			return uid;
		}
		public void setUid(int uid) {
			this.uid = uid;
		}
		public int getState() {
			return state;
		}
		public void setState(int state) {
			this.state = state;
		}
		public String getDoorName() {
			return doorName;
		}
		public void setDoorName(String doorName) {
			this.doorName = doorName;
		}
		public String getDoorNumber() {
			return doorNumber;
		}
		public void setDoorNumber(String doorNumber) {
			this.doorNumber = doorNumber;
		}
		public String getRemark() {
			return remark;
		}
		public void setRemark(String remark) {
			this.remark = remark;
		}
		public int getOperator() {
			return operator;
		}
		public void setOperator(int operator) {
			this.operator = operator;
		}
	}
	
	public static class GetUserByIdRequest{
		private int uid;
		private int  operator;
		public int getUid() {
			return uid;
		}

		public void setUid(int uid) {
			this.uid = uid;
		}

		public int getOperator() {
			return operator;
		}

		public void setOperator(int operator) {
			this.operator = operator;
		}
	}

	
}

	
