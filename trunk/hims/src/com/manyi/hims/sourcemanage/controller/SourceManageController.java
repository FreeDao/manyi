package com.manyi.hims.sourcemanage.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.request.async.DeferredResult;

import com.leo.common.util.DateUtil;
import com.manyi.hims.Global;
import com.manyi.hims.Response;
import com.manyi.hims.contact.service.HouseResourceContactService;
import com.manyi.hims.entity.BdTask;
import com.manyi.hims.entity.HouseImageFile;
import com.manyi.hims.house.service.HouseService;
import com.manyi.hims.sourcemanage.model.SourceManageRequest;
import com.manyi.hims.sourcemanage.model.SourceManageResponse;
import com.manyi.hims.sourcemanage.model.SourceManageResponse.BDtaskDateAndLBS;
import com.manyi.hims.sourcemanage.model.SourceManageResponse.HouseImage;
import com.manyi.hims.sourcemanage.model.SourceManageResponse.HouseSupporting;
import com.manyi.hims.sourcemanage.service.SourceManageService;
import com.manyi.hims.util.CommonUtils;
import com.manyi.hims.util.EntityUtils;

/**
 * @date 2014年4月17日 下午12:49:52
 * @author Tom 
 * @description  
 * 房源管理详情页，控制类
 */
@Controller
@SessionAttributes(Global.SESSION_UID_KEY)
@RequestMapping("sourcemanage")
public class SourceManageController {
	
	@Autowired
	private HouseService houseService;
	
	@Autowired
	private SourceManageService sourceManageService;
	
	@Autowired
	private HouseResourceContactService houseResourceContactService;
	
	
	/**
	 * @date 2014年4月17日 下午12:54:52
	 * @author Tom  
	 * @description  
	 * 房源基础信息  和  租售信息
	 */
	@RequestMapping(value = "/getSourceBaseInfo.rest", produces="application/json")
	@ResponseBody
	private DeferredResult<Response> getSourceBaseInfo(@RequestBody SourceManageRequest sourceManageRequest, HttpSession session) {
//		房源基础信息
		SourceManageResponse sourceManageResponse = sourceManageService.getSourceBaseInfo(sourceManageRequest.getHouseId());
		
//		租售信息
		sourceManageService.getSourceRentAndSellList(sourceManageResponse, sourceManageRequest.getHouseId());
				
		return CommonUtils.deferredResult(sourceManageResponse);
	}
	

	/**
	 * @date 2014年4月18日 上午9:52:39
	 * @author Tom  
	 * @description  
	 * 业主信息
	 */
	@RequestMapping(value = "/getSourceHostInfo.rest", produces="application/json")
	@ResponseBody
	public DeferredResult<Response> getSourceHostInfo(@RequestBody SourceManageRequest sourceManageRequest) {
		SourceManageResponse sourceManageResponse = new SourceManageResponse();
		//当前
		sourceManageResponse.setHouseContactTrue(houseResourceContactService.getContactList4HouseId(sourceManageRequest.getHouseId(), true));
		//历史
		sourceManageResponse.setHouseContactFalse(houseResourceContactService.getContactList4HouseId(sourceManageRequest.getHouseId(), false));
		
		return CommonUtils.deferredResult(sourceManageResponse);
	}
	
	
	/**
	 * @date 2014年4月18日 上午9:52:39
	 * @author Tom  
	 * @description  
	 * 审核记录
	 */
	@RequestMapping(value = "/getAuditMessageInfo.rest", produces="application/json")
	@ResponseBody
	public DeferredResult<Response> getAuditMessageInfo(@RequestBody SourceManageRequest sourceManageRequest) {
		
		return CommonUtils.deferredResult(sourceManageService.getAuditMessageInfo(sourceManageRequest.getHouseId()));
	}
	
	/**
	 * @date 2014年4月20日 下午4:45:22
	 * @author Tom  
	 * @description  
	 * 修改房源
	 */
	@RequestMapping(value = "/updateSourceManage.rest", produces="application/json")
	@ResponseBody
	public DeferredResult<Response> updateSourceManage(@RequestBody SourceManageRequest sourceManageReques) {
		
		return CommonUtils.deferredResult(sourceManageService.updateSourceManage(sourceManageReques));

	}
	
	/**
	 * @date 2014年4月18日 上午9:52:39
	 * @author Tom  
	 * @description  
	 * 房源修改记录
	 */
	@RequestMapping(value = "/getSourceEditInfo.rest", produces="application/json")
	@ResponseBody
	public DeferredResult<Response> getSourceEditInfo(@RequestBody SourceManageRequest sourceManageRequest) {
		
		return CommonUtils.deferredResult(sourceManageService.getSourceEditInfo(sourceManageRequest.getHouseId()));
	}

	/**
	 * @date 2014年4月20日 下午4:45:22
	 * @author Tom  
	 * @description  
	 * 删除房源信息，假删除
	 */
	@RequestMapping(value = "/deleteSourceInfo.rest", produces="application/json")
	@ResponseBody
	public DeferredResult<Response> deleteSourceInfo(@RequestBody SourceManageRequest sourceManageReques) {
		sourceManageService.deleteSourceInfo(sourceManageReques.getHouseId(), sourceManageReques.getOperatorId());
		return CommonUtils.deferredResult(new SourceManageResponse());

	}
	
	/**
	 * @date 2014年4月20日 下午4:45:22
	 * @author Tom  
	 * @description  
	 * 获得装修情况
	 */ 		
	@RequestMapping(value = "/getHouseSuppoert.rest", produces="application/json")
	@ResponseBody
	public DeferredResult<Response> getHouseSuppoert(@RequestBody SourceManageRequest sourceManageReques) {
		HouseSupporting houseSupporting = new HouseSupporting();
		int decorateType = houseService.getDecorateTypeByHouseId(sourceManageReques.getHouseId());
		
		houseSupporting.setDecorateTypeStr(EntityUtils.HouseDecorateTypeEnum.getByValue(decorateType).getDesc());
			try {
				String baseSupport = houseService.getHouseSuppoert(sourceManageReques.getHouseId(), "1");
				String seniorSupport = houseService.getHouseSuppoert(sourceManageReques.getHouseId(), "2");
				houseSupporting.setBaseSupport(baseSupport);
				houseSupporting.setSeniorSupport(seniorSupport);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		SourceManageResponse sourceManageResponse = new SourceManageResponse();
		sourceManageResponse.setHouseSupporting(houseSupporting);
		return CommonUtils.deferredResult(sourceManageResponse);
		
	}
	
	/**
	 * getHouseSuppoertByUserTaskId
	 * 通过 usertaskid 获取房屋的装修情况
	 * @param sourceManageReques
	 * @return
	 */
	@RequestMapping(value = "/getHouseSuppoertByUserTaskId.rest", produces="application/json")
	@ResponseBody
	public DeferredResult<Response> getHouseSuppoertByUserTaskId(@RequestBody SourceManageRequest sourceManageReques) {
		HouseSupporting houseSupporting = new HouseSupporting();
		int decorateType = houseService.getDecorateTypeByUserTaskId(sourceManageReques.getHouseId());// usertaskid 
		
		houseSupporting.setDecorateTypeStr(EntityUtils.HouseDecorateTypeEnum.getByValue(decorateType).getDesc());
		
			try {
				String baseSupport = houseService.getHouseSuppoertByUserTaskId(sourceManageReques.getHouseId(), "1");
				String seniorSupport = houseService.getHouseSuppoertByUserTaskId(sourceManageReques.getHouseId(), "2");
				houseSupporting.setBaseSupport(baseSupport);
				houseSupporting.setSeniorSupport(seniorSupport);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		SourceManageResponse sourceManageResponse = new SourceManageResponse();
		sourceManageResponse.setHouseSupporting(houseSupporting);
		return CommonUtils.deferredResult(sourceManageResponse);

	}
	
	/**
	 * @date 2014年5月26日 下午2:49:34
	 * @author Tom
	 * @description  
	 * 返回访问图片列表
	 */
	@RequestMapping(value = "/getHouseImage.rest", produces="application/json")
	@ResponseBody
	public DeferredResult<Response> getHouseImage(@RequestBody SourceManageRequest sourceManageReques) {
		SourceManageResponse sourceManageResponse = new SourceManageResponse();
		sourceManageResponse.setHouseImageList(new ArrayList<HouseImage>());
		
		List<HouseImageFile> list = houseService.getHouseEnableImageList(sourceManageReques.getHouseId());
		HouseImage houseImage = null;
		for (HouseImageFile houseImageFile : list) {
			houseImage = new HouseImage();
			
			houseImage.setImgDes(houseImageFile.getDescription());
			//阿里云路径
			houseImage.setThumbnailUrl(houseImageFile.getThumbnailKey());
			sourceManageResponse.getHouseImageList().add(houseImage);
		}
		
		return CommonUtils.deferredResult(sourceManageResponse);

	}
	
	/**
	 * @date 2014年5月26日 下午2:49:34
	 * @author Tom
	 * @description  
	 * 返回任务完成时间和位置信息
	 */
	@RequestMapping(value = "/getBdtaskFinishAndLBS.rest", produces="application/json")
	@ResponseBody
	public DeferredResult<Response> getBdtaskFinishAndLBS(@RequestBody SourceManageRequest sourceManageReques) {
		SourceManageResponse sourceManageResponse = new SourceManageResponse();
		
		BdTask bdTask = houseService.getBDtaskFinishAndLBS(sourceManageReques.getHouseId());
		if (bdTask != null) {
			BDtaskDateAndLBS bDtaskDateAndLBS = new BDtaskDateAndLBS();
			bDtaskDateAndLBS.setFinishDate(DateUtil.formatDate("yyyy-MM-dd HH:mm:ss", bdTask.getFinishDate()));
			bDtaskDateAndLBS.setLatitude(bdTask.getLatitude());
			bDtaskDateAndLBS.setLongitude(bdTask.getLongitude());
			sourceManageResponse.setBDtaskDateAndLBS(bDtaskDateAndLBS);
		}
		
		return CommonUtils.deferredResult(sourceManageResponse);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
		
}
