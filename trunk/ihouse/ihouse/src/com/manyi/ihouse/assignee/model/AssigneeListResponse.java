package com.manyi.ihouse.assignee.model;

import java.util.List;

import com.manyi.ihouse.base.Response;

public class AssigneeListResponse extends Response {
	
	private List<AssigneeModel> assigneeList;

	public List<AssigneeModel> getAssigneeList() {
		return assigneeList;
	}

	public void setAssigneeList(List<AssigneeModel> assigneeList) {
		this.assigneeList = assigneeList;
	}
	
}
