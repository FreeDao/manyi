package com.manyi.hims.bd.controller;

import java.io.File;
import java.math.BigDecimal;

import lombok.Data;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.manyi.hims.bd.BdConst;
import com.manyi.hims.bd.service.HouseResourcePhotoService;

@Controller
@RequestMapping("/rest/bd")
public class HouseResourcePhotoController extends RestController{
	private Logger logger = LoggerFactory.getLogger(HouseResourcePhotoController.class);
    
	@Autowired
	private HouseResourcePhotoService houseResourcePhotoService;
	
	/**
	 * @date 2014年5月20日 下午7:51:23
	 * @author Tom
	 * @description  
	 * 列表页面提交按钮接口
	 */
	@RequestMapping(value = "/taskSubmit.rest", produces="application/json")
	@ResponseBody
	public DeferredResult<Response> taskSubmit(@RequestBody TaskRequest taskRequest) {
		logger.info(JSONObject.fromObject(taskRequest).toString());
		
		if (taskRequest.getHouseId() == 0) {
			throw new LeoFault(BdConst.BD_ERROR140001);
		} else if (taskRequest.getTaskId() == 0) {
			throw new LeoFault(BdConst.BD_ERROR140001);
		} else if (StringUtils.isBlank(taskRequest.getLatitude())) {
			throw new LeoFault(BdConst.BD_ERROR140001);
		} else if (StringUtils.isBlank(taskRequest.getLongitude())) {
			throw new LeoFault(BdConst.BD_ERROR140001);
		} else if (taskRequest.getPicNum() == 0) {
			throw new LeoFault(BdConst.BD_ERROR140001);
		}
		
		houseResourcePhotoService.taskSubmit(taskRequest);
		
		DeferredResult<Response> dr = new DeferredResult<Response>();
		dr.setResult(new Response());
		return dr;
	}
	
	@Data
	public static class TaskRequest {
		
		private int taskId;
		private int houseId;
		private BigDecimal spaceArea = new BigDecimal(0);// 内空面积
		private int bedroomSum;// 几房
		private int livingRoomSum;// 几厅
		private int wcSum;// 几卫
		private BigDecimal sellPrice = new BigDecimal(0);//出售价格
		private BigDecimal rentPrice = new BigDecimal(0);//出租价格
		
		private int picNum; //图片数目
		private String remark;
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
	
 
	
	/**
	 * @date 2014年5月20日 下午7:51:23
	 * @author Tom
	 * @description  
	 * 列表页面提交按钮接口
	 */
	@RequestMapping(value = "/taskLookFail.rest", produces="application/json")
	@ResponseBody
	public DeferredResult<Response> taskLookFail(@RequestBody TaskLookFailRequest taskLookFailRequest) {
		logger.info(JSONObject.fromObject(taskLookFailRequest).toString());

		houseResourcePhotoService.taskLookFail(taskLookFailRequest);
		
		DeferredResult<Response> dr = new DeferredResult<Response>();
		dr.setResult(new Response());
		return dr;
	}
	
	@Data
	public static class TaskLookFailRequest {
		private int taskId;
		private String remark;
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

		if (houseFileRequest.getFile() == null || houseFileRequest.getHouseId() == 0 || houseFileRequest.getPhotoFolder() == null) {
			throw new LeoFault(BdConst.BD_ERROR140001);
		}
		logger.info("单独上传房子图片：houseId={}", houseFileRequest.getHouseId());
		
		houseResourcePhotoService.taskUploadSingleFile(houseFileRequest);
		
		DeferredResult<Response> dr = new DeferredResult<Response>();
		dr.setResult(new Response());
		return dr;
	}
	
	@Data
	public static class HouseFileRequest {
		private int houseId;
		private File file;
		private int fileOrderFlag;// 1 代表第一张图片， 2 其他，3 表示最后一张图片
		private String photoFolder;// 照片所在目录的结构： HouseResourceType 的 key

		@JsonDeserialize(using = FileDeserializer.class)
		public File getFile() {
			return file;
		}

	}
}
