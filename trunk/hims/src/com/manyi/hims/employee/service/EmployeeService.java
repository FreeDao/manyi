package com.manyi.hims.employee.service;

import java.util.List;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import com.leo.common.Page;
import com.manyi.hims.Response;
import com.manyi.hims.employee.controller.EmployeeController.Result;
import com.manyi.hims.employee.model.EmployeeListResponse;
import com.manyi.hims.employee.model.EmployeeModel;
import com.manyi.hims.employee.model.EmployeeResponse;

public interface EmployeeService {
	
	public EmployeeResponse login (EmployeeModel employee) ;

	public void addEmployee(EmployeeModel employee);
	
	public void updateEmployee(EmployeeModel employee);
	
	public void disableEmployee(EmployeeModel employee);
	
	public Page<EmpResponse> getEmployee(Integer page, Integer rows);
	
	public EmployeeListResponse getEmployeeList();
	
	/**
	 * @date 2014年5月4日 上午10:39:32
	 * @author Tom  
	 * @description  
	 * 根据roleId查询员工列表
	 */
	public EmployeeListResponse getEmployeeList(int roleId);
	
	public  Result getProvinde();
	
	public Result getCityByParentId(int parentId);
	
	@Data
	public static class AreaResponse{
		
		private int areaId;
		private String name;
	}
	@Data
	public static class EmpResponse{
		private int employeeId;
		private String userName;// 用户名
		private String email;
		private boolean disable;// 是否禁用
		private String realName;// 身份证姓名
		private int role;//角色 1管理员,2客服经理，3客服人员,4地推经理,5地推人员 6财务
		private String roleStr;
		private String disabledStr;
		private String ucServerName;
		private String ucServerPwd; 
		private String ucServerGroupId;
		private String cityName;
		private String areaName;
		/**
		 * 城市ID
		 */
		private int cityId;
		/**
		 * areaId
		 */
		private int areaId;
	}

}
