package com.manyi.hims.usertask.controller;

import java.io.File;

import lombok.Data;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.DeferredResult;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.leo.jackson.FileDeserializer;
import com.leo.jaxrs.fault.LeoFault;
import com.manyi.hims.Response;
import com.manyi.hims.RestController;
import com.manyi.hims.usertask.UserTaskConst;
import com.manyi.hims.usertask.service.UserTaskPhotoService;

@Controller
@RequestMapping("/rest/userTask")
public class UserTaskPhotoController extends RestController {
	private Logger logger = LoggerFactory.getLogger(UserTaskPhotoController.class);
    
	@Autowired
    private UserTaskPhotoService userTaskPhotoService;
    
    @Autowired
    private ThreadPoolTaskExecutor executor;
	
	/**
	 * @date 2014年6月5日 下午3:34:36
	 * @author Tom
	 * @description  
	 * 列表页面提交按钮接口
	 */
	@RequestMapping(value = "/taskSubmit.rest", produces="application/json")
	@ResponseBody
	public DeferredResult<Response> taskSubmit(@RequestBody TaskRequest taskRequest) {
		logger.info(JSONObject.fromObject(taskRequest).toString());
		
		if (taskRequest.getHouseId() == 0) {
			throw new LeoFault(UserTaskConst.UserTask_ERROR150001);
		} else if (taskRequest.getTaskId() == 0) {
			throw new LeoFault(UserTaskConst.UserTask_ERROR150001);
		} else if (StringUtils.isBlank(taskRequest.getLatitude())) {
			throw new LeoFault(UserTaskConst.UserTask_ERROR150001);
		} else if (StringUtils.isBlank(taskRequest.getLongitude())) {
			throw new LeoFault(UserTaskConst.UserTask_ERROR150001);
		} else if (taskRequest.getPicNum() == 0) {
			throw new LeoFault(UserTaskConst.UserTask_ERROR150001);
		}
		
		userTaskPhotoService.taskSubmit(taskRequest);
		
		DeferredResult<Response> dr = new DeferredResult<Response>();
		dr.setResult(new Response());
		return dr;
	}
	
	
	
	/**
	 * @date 2014年5月20日 下午7:51:23
	 * @author Tom
	 * @description  
	 * 列表页面提交按钮接口
	 */
	@RequestMapping(value = "/taskUploadSingleFile.rest", produces="application/json")
	@ResponseBody
	public DeferredResult<Response> taskUploadSingleFile(@RequestBody HouseFileRequest houseFileRequest) {

		if (houseFileRequest.getFile() == null || houseFileRequest.getTaskId() == 0 || houseFileRequest.getHouseId() == 0 || houseFileRequest.getPhotoFolder() == null) {
			throw new LeoFault(UserTaskConst.UserTask_ERROR150001);
		}
		logger.info("单独上传房子图片：houseId={}, userTaskId={}", houseFileRequest.getHouseId(), houseFileRequest.getTaskId());
		
		userTaskPhotoService.taskUploadSingleFile(houseFileRequest);
		
		DeferredResult<Response> dr = new DeferredResult<Response>();
		dr.setResult(new Response());
		return dr;
	}
	
	@Data
	public static class TaskRequest {
		
		private int taskId;
		private int houseId;
		private int bedroomSum;// 几房
		private int livingRoomSum;// 几厅
		private int wcSum;// 几卫
		
		private int picNum; //图片数目
		private String latitude;// 坐标 纬度
		private String longitude;// 坐标 经度
		
		//装修类型
		private int decorateType; //1 毛坯，2 装修
		//装修
		private HouseSupportingMeasures houseSupportingMeasures;
		
	}
	
	@Data
	public static class HouseSupportingMeasures {

		//基础配置  //是否含有  true 1 是； false 0 否
		private boolean hasTV; //  电视 
		private boolean hasRefrigerator;//  冰箱 
		private boolean hasWashingMachine;//  洗衣机 
		private boolean hasAirConditioner;//  空调 
		private boolean hasWaterHeater;//  热水器 
		private boolean hasBed;//  床 
		private boolean hasSofa;//  沙发 
		private boolean hasBathtub;//  浴缸/淋浴 
		
		//高端配置 //是否含有  true 1 是； false 0 否
		private boolean hasCentralHeating;//  暖气/地暖 
		private boolean hasCentralAirConditioning;//  中央空调 
		private boolean hasCloakroom;//  衣帽间 
		private boolean hasReservedParking;//  车位 
		private boolean hasCourtyard;//  院落 
		private boolean hasGazebo;//  露台 
		private boolean hasPenthouse;//  阁楼 
	}
 
	@Data
	public static class HouseFileRequest {
		private int houseId;
		private int taskId;
		private File file;
		private int fileOrderFlag;// 1 代表第一张图片， 2 其他，3 表示最后一张图片
		private String photoFolder;// 照片所在目录的结构： HouseResourceType 的 key

		@JsonDeserialize(using = FileDeserializer.class)
		public File getFile() {
			return file;
		}
	}
    
}
