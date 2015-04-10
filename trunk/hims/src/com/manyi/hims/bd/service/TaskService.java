package com.manyi.hims.bd.service;

import java.util.Date;

import com.leo.common.Page;
import com.manyi.hims.bd.controller.TaskController.HisTaskDetailResponse;
import com.manyi.hims.bd.controller.TaskController.TaskPromptResponse;
import com.manyi.hims.bd.controller.TaskController.TaskResponse;

public interface TaskService {
	/*任务列表*/
	public Page<TaskResponse> findTaskByUserId(int userId, Integer taskStatus,
			Date beginDate, Date endDate, int start, int max);
	/*统计周任务*/
	public TaskPromptResponse countWeekTask(int employeeId);

	/*历史任务详情*/
	public HisTaskDetailResponse hisTaskDetail(int taskId,int taskStatus);

}
