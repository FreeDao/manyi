package com.manyi.fyb.callcenter.employee.model;

import java.util.List;

import com.manyi.fyb.callcenter.base.model.Response;

public class EmployeeListResponse extends Response {
	List<EmployeeModel> custServices;
	List<EmployeeModel> floorServices;
	
	public List<EmployeeModel> getCustServices() {
		return custServices;
	}
	public void setCustServices(List<EmployeeModel> custServices) {
		this.custServices = custServices;
	}
	public List<EmployeeModel> getFloorServices() {
		return floorServices;
	}
	public void setFloorServices(List<EmployeeModel> floorServices) {
		this.floorServices = floorServices;
	}	
}
