package com.manyi.ihouse.assignee.service;

import com.manyi.ihouse.assignee.model.AssigneeListResponse;
import com.manyi.ihouse.assignee.model.AssigneeModel;
import com.manyi.ihouse.assignee.model.AssigneeRequest;
import com.manyi.ihouse.assignee.model.AssigneeResponse;
import com.manyi.ihouse.base.PageResponse;
import com.manyi.ihouse.user.model.PageListRequest;

/**
 * 经济人信息管理接口
 * @author hubin
 *
 */
public interface AssigneeManagerService {

	/**
	 * 添加经纪人
	 * @param assigneeRequest 经纪人
	 */
	public void addAssignee(AssigneeRequest assigneeRequest);
	
	/**
	 * 更新经纪人信息
	 * @param assigneeRequest 经纪人
	 */
	public void updateAssignee(AssigneeRequest assigneeRequest);
	
	/**
	 * 根据经纪人ID获取经纪人信息
	 * @param id 经纪人ID
	 * @return
	 */
	public AssigneeResponse getAssignee(int id);
	
	/**
	 * 获取经纪人列表(无查询条件）
	 * @return
	 */
	public PageResponse<AssigneeModel> getAssigneeList(PageListRequest request);
	
}
