package com.manyi.fyb.callcenter.employee.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import lombok.Getter;
import lombok.Setter;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.manyi.fyb.callcenter.base.controller.BaseController;
import com.manyi.fyb.callcenter.base.model.Response;
import com.manyi.fyb.callcenter.employee.model.EmployeeModel;
import com.manyi.fyb.callcenter.employee.model.EmployeeResponse;
import com.manyi.fyb.callcenter.index.model.TokenModel;
import com.manyi.fyb.callcenter.utils.Constants;
import com.manyi.fyb.callcenter.utils.HttpClientHelper;
import com.manyi.fyb.callcenter.utils.SessionUtils;
@Controller@RequestMapping("/employee")
public class EmployeeController extends BaseController{
	@Getter @Setter
	private Map<String ,TokenModel<EmployeeModel>> addEmployee = new HashMap<String ,TokenModel<EmployeeModel>>();
	
	@RequestMapping("/employeeIndex")
	public String employeeIndex(){
		return "employee/addEmployee";
	}
	
	@RequestMapping("/getEmployee")
	@ResponseBody
	public String getEmployee(HttpServletRequest request,getEmployeeRequest employeeRequest){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("page", employeeRequest.getPage());
		map.put("rows", employeeRequest.getRows());
		JSONObject json = HttpClientHelper.sendRestInter(Constants.LINK_FRONT_PART+"/employee/getEmployee.rest", map);
		
		return json.toString();
	}

	@RequestMapping("/login")
	public String login(EmployeeModel emp1,HttpServletRequest request){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userName", emp1.getUserName());
		map.put("password", emp1.getPassword());
		JSONObject json = HttpClientHelper.sendRestInter(Constants.LINK_FRONT_PART+"/employee/login.rest", map);
		EmployeeResponse empResponse = (EmployeeResponse)JSONObject.toBean(json, EmployeeResponse.class);
		if (empResponse == null) {
			request.setAttribute("message", "系统异常");
			return "index/welcome";
		}
		logger.info(empResponse.getMessage());
		if(empResponse.getErrorCode()!=0){
			request.setAttribute("message", empResponse.getMessage());
			return "index/welcome";
		}
		final EmployeeModel emp = new EmployeeModel();
		BeanUtils.copyProperties(empResponse, emp);
		logger.info(emp.getUcServerName() +":"+ emp.getUcServerGroupId() + ":" + emp.getUcServerPwd() + ":");
		if (emp.getRole() == 2 || emp.getRole() == 3) {
			if (StringUtils.isNotBlank(emp.getUcServerName())) {
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						HttpClientHelper.HttpClientGetShort("?EVENT=QUEUE&type=1&usertype=account&user=" + emp.getUcServerName() + "&orgidentity=" + Constants.UC_ORG_IDENTITY + "&pwdtype=textplain&password=" + emp.getUcServerPwd() ) ;
					}
				}).start();
				
			}
			
		}
		request.getSession().setAttribute(Constants.LOGIN_SESSION, emp);
		request.getSession().setAttribute("himsServicePath", Constants.HIMS_SERVICE_PATH);
		return "redirect:/index";
	}
	
	@RequestMapping("/addEmployee")
	@ResponseBody
	public JSONObject addEmployee(EmployeeModel empModel,HttpServletRequest request){
		Map<String, Object> map = new HashMap<String, Object>();
		EmployeeModel emp  = SessionUtils.getSession(request);
		empModel.setId(emp.getId());
//		TokenModel<EmployeeModel> tm = new TokenModel<EmployeeModel>(1,1,empModel);
//		synchronized (this) {
//			TokenModel<EmployeeModel> tmLast = addEmployee.get(String.valueOf(emp.getId()));
//			if (tmLast != null ) {
//				if (tm.getTokenObject().equals(tmLast.getTokenObject())) {
//					JSONObject json = new JSONObject();
//					map.put("errorCode", 111);
//					map.put("message", "repeatSubmit");
//					json.putAll(map);
//					return json;
//				}
//			}
//			addEmployee.put(String.valueOf(empModel.getId()), tm);
//			
//		}
		
		if(!submitProtectedUtils.sync(1, empModel, emp.getId())){
			JSONObject json = new JSONObject();
			map.put("errorCode", 111);
			map.put("message", "repeatSubmit");
			json.putAll(map);
			return json;
		}
		map.put("userName", empModel.getUserName());
		map.put("password", empModel.getPassword());
		map.put("realName", empModel.getRealName());
		map.put("role", empModel.getRole());
		map.put("ucServerName", empModel.getUcServerName());
		map.put("ucServerPwd", empModel.getUcServerPwd());
		map.put("ucServerGroupId", empModel.getUcServerGroupId());
		JSONObject json = HttpClientHelper.sendRestInter(Constants.LINK_FRONT_PART+"/employee/addEmployee.rest", map);
		Response result= (Response)JSONObject.toBean(json,Response.class);
//		if(result.getErrorCode()!=0){
//			request.setAttribute("message", result.getMessage());
//			return json.toString();
//		}
		return json;
	}
	
	@RequestMapping("/updateEmployee")
	@ResponseBody
	 public JSONObject updateEmployee(EmployeeModel empModel,HttpServletRequest request){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("password", empModel.getPassword());
		map.put("role", empModel.getRole());
		map.put("id", empModel.getId());
		map.put("ucServerName", empModel.getUcServerName());
		map.put("ucServerPwd", empModel.getUcServerPwd());
		map.put("ucServerGroupId", empModel.getUcServerGroupId());
		JSONObject json = HttpClientHelper.sendRestInter(Constants.LINK_FRONT_PART+"/employee/updateEmployee.rest", map);
		Response result = (Response)JSONObject.toBean(json,Response.class);
//		if(result.getErrorCode()!=0){
//			request.setAttribute("message", result.getMessage());
//		}
		return json;
	}
	
	@RequestMapping("/disabledEmployee")
	@ResponseBody
	 public JSONObject disabledEmployee(EmployeeModel empModel,HttpServletRequest request){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", empModel.getId());
		JSONObject json = HttpClientHelper.sendRestInter(Constants.LINK_FRONT_PART+"/employee/disableEmployee.rest", map);
		Response result = (Response)JSONObject.toBean(json,Response.class);
//		if(result.getErrorCode()!=0){
//			request.setAttribute("message", result.getMessage());
//		}
		return json;
	}
	
	/**
	 * @date 2014年5月4日 上午10:42:42
	 * @author Tom  
	 * @description  
	 * 获取地推人员列表
	 */
	@RequestMapping("/getEmployeeList4Floor")
	@ResponseBody
	private String getEmployeeList4Floor() {
		Map<String, Object> map = new HashMap<String, Object>();
		return HttpClientHelper.sendRestJsonShortObj2Str("/employee/getEmployeeList4Floor.rest", map);
	}
	
	public static class getEmployeeRequest{
		private Integer page=0;
		private Integer rows=20;
		public Integer getPage() {
			return page;
		}
		public void setPage(Integer page) {
			this.page = page;
		}
		public Integer getRows() {
			return rows;
		}
		public void setRows(Integer rows) {
			this.rows = rows;
		}
	}
}
