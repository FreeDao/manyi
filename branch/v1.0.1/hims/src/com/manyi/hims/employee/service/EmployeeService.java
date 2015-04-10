package com.manyi.hims.employee.service;

import lombok.Getter;
import lombok.Setter;

import com.leo.common.Page;
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
		public int getEmployeeId() {
			return employeeId;
		}
		public void setEmployeeId(int employeeId) {
			this.employeeId = employeeId;
		}
		public String getUserName() {
			return userName;
		}
		public void setUserName(String userName) {
			this.userName = userName;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public boolean isDisable() {
			return disable;
		}
		public void setDisable(boolean disable) {
			this.disable = disable;
		}
		public String getRealName() {
			return realName;
		}
		public void setRealName(String realName) {
			this.realName = realName;
		}
		public int getRole() {
			return role;
		}
		public void setRole(int role) {
			this.role = role;
		}
		public String getRoleStr() {
			return roleStr;
		}
		public void setRoleStr(String roleStr) {
			this.roleStr = roleStr;
		}
		public String getDisabledStr() {
			return disabledStr;
		}
		public void setDisabledStr(String disabledStr) {
			this.disabledStr = disabledStr;
		}
		public String getUcServerName() {
			return ucServerName;
		}
		public void setUcServerName(String ucServerName) {
			this.ucServerName = ucServerName;
		}
		public String getUcServerPwd() {
			return ucServerPwd;
		}
		public void setUcServerPwd(String ucServerPwd) {
			this.ucServerPwd = ucServerPwd;
		}
		public String getUcServerGroupId() {
			return ucServerGroupId;
		}
		public void setUcServerGroupId(String ucServerGroupId) {
			this.ucServerGroupId = ucServerGroupId;
		}
		
		
	}

}
