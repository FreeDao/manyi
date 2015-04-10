package com.manyi.fyb.callcenter.employee.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import lombok.Data;
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
@Controller
@RequestMapping("/employee")
public class EmployeeController extends BaseController{
	@Getter @Setter
	private Map<String ,TokenModel<EmployeeModel>> addEmployee = new HashMap<String ,TokenModel<EmployeeModel>>();
	
	@RequestMapping("/employeeIndex")
	public String employeeIndex(){
		return "employee/addEmployee";
	}
	
	@RequestMapping("/getEmployee")
	@ResponseBody
	public String getEmployee(HttpServletRequest request,GetEmployeeRequest employeeRequest){
		JSONObject json = HttpClientHelper.sendRestInterShortObject("/employee/getEmployee.rest", employeeRequest);
		return json.toString();
	}

	@RequestMapping("/login")
	public String login(EmployeeModel emp1,HttpServletRequest request){
		EmployeeResponse empResponse = HttpClientHelper.sendRestJsonShortObj2Obj("/employee/login.rest", emp1,EmployeeResponse.class);
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
		if (emp.getRole() == 2 || emp.getRole() == 3 || emp.getRole() == 4) {
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
		request.getSession().setAttribute("himsServicePath",Constants.HIMS_SERVICE_PATH);
		return "redirect:/index";
	}
	
	@RequestMapping("/addEmployee")
	@ResponseBody
	public JSONObject addEmployee(EmployeeModel empModel,HttpServletRequest request){
		Map<String, Object> map = new HashMap<String, Object>();
		EmployeeModel emp  = SessionUtils.getSession(request);
		empModel.setId(emp.getId());
		if(!submitProtectedUtils.sync(1, empModel, emp.getId())){
			JSONObject json = new JSONObject();
			map.put("errorCode", 111);
			map.put("message", "repeatSubmit");
			json.putAll(map);
			return json;
		}
		JSONObject json = HttpClientHelper.sendRestInterShortObject("/employee/addEmployee.rest", empModel);
		return json;
	}
	
	@RequestMapping("/updateEmployee")
	@ResponseBody
	 public JSONObject updateEmployee(EmployeeModel empModel,HttpServletRequest request){
		JSONObject json = HttpClientHelper.sendRestInterShortObject("/employee/updateEmployee.rest", empModel);
		return json;
	}
	
	@RequestMapping("/disabledEmployee")
	@ResponseBody
	 public JSONObject disabledEmployee(EmployeeModel empModel,HttpServletRequest request){
		JSONObject json = HttpClientHelper.sendRestInterShortObject("/employee/disableEmployee.rest", empModel);
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
	/**
	 * 获取城市
	 * @author fangyouhui
	 *
	 */
	@RequestMapping("/getProvince")
	@ResponseBody
	 public Result getProvince(HttpServletRequest request){
		Result response = HttpClientHelper.sendRestJsonShortObj2Obj("/employee/getProvince.rest", null,Result.class);
		System.out.println(response);
 		return response;
	}
	/**
	 * 获取行政区
	 * @author fangyouhui
	 *
	 */
	@RequestMapping("/getCity")
	@ResponseBody
	 public Response  getCity(CityRequest pars,HttpServletRequest request){
		Response json = HttpClientHelper.sendRestJsonShortObj2Obj("/employee/getCity.rest", pars,Result.class);
		System.out.println(json.toString());
		return json;
	}
	
	
	@Data
	public static class  Result extends Response{
		List<AreaResponse> list =new ArrayList<AreaResponse>();
	}
	@Data
	public static class AreaResponse{
		
		private int areaId;
		private String name;
	}
	@Data
	public static class CityRequest{
		private int parentId;
	}
	@Data
	public static class GetEmployeeRequest{
		private Integer page=0;
		private Integer rows=20;
		
	}
}
