package com.manyi.hims.bd.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import lombok.Data;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.DeferredResult;

import com.leo.common.Page;
import com.leo.jaxrs.fault.LeoFault;
import com.manyi.hims.Response;
import com.manyi.hims.RestController;
import com.manyi.hims.bd.BdConst;
import com.manyi.hims.bd.service.EmployeeService.TaskDetailsResponse;
import com.manyi.hims.bd.service.TaskService;

/**
 * 
 * 
 * @author linto
 * 
 */
@Controller
@RequestMapping("/rest/bd")
public class TaskController extends RestController {

	 
	private Logger log = LoggerFactory.getLogger(TaskController.class);
	@Autowired
	private TaskService taskService;

	public void setCommonService(TaskService taskService) {
		this.taskService = taskService;
	}

	

	
	/**
	 * 周任务提示
	 * 
	 * @param session
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/taskWeek.rest", produces = "application/json")
	@ResponseBody
	public DeferredResult<Response> taskPrompt(HttpSession session, @RequestBody TaskPromptRequest req) {
		
		log.info(JSONObject.fromObject(req).toString());
		
		TaskPromptResponse p = this.taskService.countWeekTask(req.getEmployeeId());
		
		log.info(JSONObject.fromObject(p).toString());
		DeferredResult<Response> dr = new DeferredResult<Response>();
		dr.setResult(p);
		
		return dr;
	}
	/**
	 * 查询地推的任务
	 * 
	 * @param session
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/taskList.rest", produces = "application/json")
	@ResponseBody
	public DeferredResult<Response> taskList(HttpSession session, @RequestBody TaskRequest req) {
		
		log.info(JSONObject.fromObject(req).toString());
		final Page<TaskResponse> page = this.taskService.findTaskByUserId(
				req.getEmployeeId(), req.getTaskStatus(), req.getBeginDate(),
				req.getEndDate(), req.getStart(), req.getMax());

		log.info(JSONArray.fromObject(page.getRows()).toString());

		Response result = new Response() {
			private List<TaskResponse> taskList = new ArrayList<TaskResponse>();
			{
				this.taskList = page.getRows();
			}

			public List<TaskResponse> getTaskList() {
				return taskList;
			}

			public void setTaskList(List<TaskResponse> taskList) {
				this.taskList = taskList;
			}
		};
		DeferredResult<Response> dr = new DeferredResult<Response>();
		dr.setResult(result);

		return dr;
	}
	
	/**
	 * 历史任务详情
	 * 
	 * @param session
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/hisTaskDetail.rest", produces = "application/json")
	@ResponseBody
	public DeferredResult<Response> hisTaskDetail(HttpSession session, @RequestBody HisTaskDetailRequest req) {
		
		log.info(JSONObject.fromObject(req).toString());
		if (req.getTaskId() == 0) {
			throw new LeoFault(BdConst.BD_ERROR140001);
		} else if (req.getTaskStatus() == 0) {
			throw new LeoFault(BdConst.BD_ERROR140001);
		}
		
		HisTaskDetailResponse p = this.taskService.hisTaskDetail(req.getTaskId(),req.getTaskStatus());

		log.info(JSONObject.fromObject(p).toString());
		DeferredResult<Response> dr = new DeferredResult<Response>();
		dr.setResult(p);

		return dr;
	}

	@Data
	public static class TaskRequest {
		/*编号*/
		private Integer employeeId; 
		/*开始时间*/
		private Date beginDate;
		/*结束时间*/
		private Date endDate;
		/*开始*/
		private int start;
		/*条数*/
		private int max;
		/* ，4未完成，5任务失败, 6完成*/
		private Integer  taskStatus;  
	}
	@Data
	public static class TaskResponse {
		/*taskID*/
		private long id;
		/*编号*/
		private Long employeeId;
		/*时间*/
		private Date date; 
		/*房屋编号*/
	    private int houseId;
	    /*任务状态*/
	    private Integer taskStatus;
	    /*任务状态中文*/
	    private String taskStatusCn;
	    
	    /*说明*/
	    private String remark;
	    /*最后更新时间*/
	    private Date lastUpdateTime;
	    
	    /*房屋名*/
	    private String houseName;
	    /*栋数*/
	    private String building;
	    /*室号*/
	    private String room;
	    /*地址*/
	    private String address;
	}
	
	@Data
	public static class TaskPromptRequest {
		private int employeeId; 
	}
	@Data
	public static class TaskPromptResponse extends Response{

		private long employeeId;
		/*本周任务数*/
		private Integer weekTaskCount;
		/*已完成任务数*/
		private Integer finishCount;
		/*失败任务数*/
	    private Integer failCount;
	    /*未完成任务数*/
	    private Integer unfinishCount;
	    
	}
	
	@Data
	public static class HisTaskDetailRequest{
		/*编号*/
		private Integer taskId;
		/*任务状态*/
		private Integer taskStatus;
	}
	@Data
	public static class HisTaskDetailResponse extends Response{

		/*任务明细信息*/
		private TaskDetailsResponse taskdetail;
        /*房屋历史基本信息*/
		private boolean isSuccess = false;
		
		private String taskImgStr; // 任务图片统计字段
		private String houseTypeStr; // 房型变化字段
		private String rentPriceStr; // 出租金额变化字段
		private String sellPriceStr; // 出售金额变化字段
		private String spaceAreaStr; // 面积变化字段
		private String houseSupport;/*配套信息*/
	    
	}
	
}
