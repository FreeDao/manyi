package com.manyi.hims.usertask.controller;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
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
import com.manyi.hims.common.CommonConst;
import com.manyi.hims.common.service.CommonService;
import com.manyi.hims.estate.service.EstateService;
import com.manyi.hims.sell.SellConst;
import com.manyi.hims.uc.service.UserService;
import com.manyi.hims.usertask.service.UserTaskService;
import com.manyi.hims.usertask.service.UserTaskService.UserTaskDetailResponse;
import com.manyi.hims.util.EntityUtils.AwardTypeEnum;
@Controller
@RequestMapping("/rest/userTask")
public class UserTaskController extends RestController{
	private Logger log = LoggerFactory.getLogger(UserTaskController.class);
	
	@Autowired
	private UserTaskService userTaskService;
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private EstateService estateService;
	
	@RequestMapping(value = "/userTaskCount.rest", produces="application/json")
	@ResponseBody
	public DeferredResult<Response> userTaskCount(@RequestBody UserTaskCountRequest pars) {
		//log.info(JSONObject.fromObject(pars).toString());

		final int result = (int)userTaskService.userTaskCount(pars.getUid());
		final int createLogCount = userService.myAccount(pars.uid).getCreateCount();
		log.info(JSONObject.fromObject(result).toString(),JSONObject.fromObject(createLogCount).toString());

		DeferredResult<Response> dr = new DeferredResult<Response>();
		
		Response response = new Response(){
			@Getter
			@Setter
			private int count;
			@Getter
			@Setter
			private int sellPrice;
			@Getter
			@Setter
			private int rentPrice;
			@Getter
			@Setter
			private int updateDisc;
			@Getter
			@Setter
			private int invitation;
			@Getter
			@Setter
			private int lookHouse;
			@Getter
			@Setter
			private int releaseCount;
			{
				count = result;
				releaseCount = createLogCount;
				sellPrice = AwardTypeEnum.SELL.getMoney();
				rentPrice = AwardTypeEnum.RENT.getMoney();
				updateDisc = AwardTypeEnum.CHANGE.getMoney();
				invitation = AwardTypeEnum.INVITE.getMoney();
				lookHouse = AwardTypeEnum.LOOKHOUSE.getMoney();
			}
		};
		dr.setResult(response);
		return dr;
	}
	
	
	@RequestMapping(value = "/addUserTask.rest", produces="application/json")
	@ResponseBody
	public DeferredResult<Response> addUserTask(@RequestBody AddUserTaskRequest pars) {

		DeferredResult<Response> dr = new DeferredResult<Response>();
		
		dr.setResult(userTaskService.addUserTask(pars.getUid(), pars.getHouseId()));
		
		return dr;
	}
	
	
	@RequestMapping(value = "/userTaskDetail.rest", produces="application/json")
	@ResponseBody
	public DeferredResult<Response> userTaskDetail(@RequestBody UserTaskDetailRequest pars) {

		final UserTaskDetailResponse result =  userTaskService.userTaskDetail(pars.getUserTaskId());

		DeferredResult<Response> dr = new DeferredResult<Response>();
		Response response = new Response() {
			@Getter
			@Setter
			private UserTaskDetailResponse userTaskDetail = new UserTaskDetailResponse();

			{
				BeanUtils.copyProperties(result, userTaskDetail);
			}
		};
		dr.setResult(response);
		return dr;
	}
	
	@RequestMapping(value = "/userTaskIndex.rest", produces="application/json")
	@ResponseBody
	public DeferredResult<Response> userTaskIndex(@RequestBody UserTaskCountRequest pars) {

		final List<UserTaskDetailResponse> result =  userTaskService.userTaskIndex(pars.getUid());
		DeferredResult<Response> dr = new DeferredResult<Response>();
		Response response = new Response(){
			@Getter
			@Setter
			private List<UserTaskDetailResponse> userTaskList = new ArrayList<UserTaskDetailResponse>();
			
			{
				userTaskList = result;
			}
			
		};
		dr.setResult(response);
		return dr;
	}
	
	@RequestMapping(value = "/userTaskIndexById.rest", produces="application/json")
	@ResponseBody
	public DeferredResult<Response> userTaskIndexById(@RequestBody UserTaskIndexByIdRequest pars) {

		final Page<UserTaskDetailResponse> result =  userTaskService.userTaskIndexById(pars.getUid(), pars.getFirst(), pars.getMax());
//		PageResponse<UserTaskDetailResponse> response = new PageResponse<UserTaskDetailResponse>();
//		BeanUtils.copyProperties(result, response);
		DeferredResult<Response> dr = new DeferredResult<Response>();
		Response response = new Response(){
			@Getter
			@Setter
			private List<UserTaskDetailResponse> userTaskList = new ArrayList<UserTaskDetailResponse>();
			{
				userTaskList = result.getRows();
			}
		};
		dr.setResult(response);
		return dr;
	}
	
	/**
	 * @date 2014年6月6日 下午2:48:51
	 * @author Tom
	 * @description  
	 * 查询房源是否满足拍照的条件
	 */
	@RequestMapping(value = "/findHouseResource4UserTaskPhoto.rest", produces="application/json")
	@ResponseBody
	public DeferredResult<Response> findHouseResource4UserTaskPhoto(@RequestBody FindHouseResourceRequest findHouseResourceRequest) {
		
		log.info(JSONObject.fromObject(findHouseResourceRequest).toString());
		int subEstateId = findHouseResourceRequest.getSubEstateId();
		String building = findHouseResourceRequest.getBuilding();
		String room = findHouseResourceRequest.getRoom();
		
		if (subEstateId == 0) {
			throw new LeoFault(SellConst.SELL_ERROR110003);
		}
		
		if (!commonService.checkIsBeiJing(estateService.getEstateIdBySubEstateId(subEstateId))) {
			//上海
			if (building.indexOf("-") != -1) {
				throw new LeoFault(CommonConst.CHECK_PUBLISH_1100053);
			}
		}
		
		if (StringUtils.isBlank(building)) {
			throw new LeoFault(SellConst.SELL_ERROR110004);
		}
		if (StringUtils.isBlank(room)) {
			throw new LeoFault(SellConst.SELL_ERROR110005);
		}
		 
		findHouseResourceRequest.setBuilding(commonService.checkBuilding(building));
		findHouseResourceRequest.setRoom(commonService.changeRoomStr(room));
		
		FindHouseResponse findHouseResponse = userTaskService.findHouseResource4UserTaskPhoto(findHouseResourceRequest);
	
		log.info(JSONObject.fromObject(findHouseResponse).toString());
		
		DeferredResult<Response> dr = new DeferredResult<Response>();
		dr.setResult(findHouseResponse);
		return dr;
	}
	
	/**
	 * @date 2014年6月9日 下午4:40:32
	 * @author Tom
	 * @description  
	 * 浏览图片 缩略图
	 */
	/*@RequestMapping(value = "/showPhoto/{encryptStr}")
	public void download(OutputStream os, @PathVariable String encryptStr) throws IOException {
		log.info("密文：encryptStr={}", encryptStr);
		
		byte[] decryptFrom = AESEncrypter.parseHexStr2Byte(encryptStr);
		byte[] decryptResult = AESEncrypter.decrypt(decryptFrom, Global.AES_PASSWORD);
		String key = new String(decryptResult);
		
		log.info("解密后：key={}", key);
		
		FileCopyUtils.copy(OSSObjectUtil.getObject(key), os);//将图片写到输出流中
	}*/
	
	@Data
	private static class UserTaskIndexByIdRequest{
		private int uid;
		private int first;
		private int max;
	}
	@Data
	public static class UserTaskDetailRequest{
		private int userTaskId;
	}
	@Data
	private static class UserTaskCountRequest{
		private int uid;
	}
	@Data
	public static class AddUserTaskRequest{
		private int uid;
		private int houseId;
	}
	
	@Data
	public static class FindHouseResourceRequest {
		private int subEstateId;
		private String building;// 楼座编号（例如：22栋 或者  1-1）  
		private String room;// 房号（例如：1304室，A1004） 
	}
	
	@Data
	public static class FindHouseResponse extends Response {
		private int houseId;
	    private int taskRepulsionCode = 0;
	    private String message;
	}
	
}
