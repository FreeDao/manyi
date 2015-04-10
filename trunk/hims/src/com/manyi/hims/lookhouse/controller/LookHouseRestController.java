package com.manyi.hims.lookhouse.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.request.async.DeferredResult;

import com.manyi.hims.Global;
import com.manyi.hims.PageResponse;
import com.manyi.hims.Response;
import com.manyi.hims.RestController;
import com.manyi.hims.entity.HouseImageFile;
import com.manyi.hims.lookhouse.model.LookHouseReq;
import com.manyi.hims.lookhouse.model.LookHouseRes;
import com.manyi.hims.lookhouse.model.PlanReq;
import com.manyi.hims.lookhouse.model.PlanRes;
import com.manyi.hims.lookhouse.model.RandomReq;
import com.manyi.hims.lookhouse.model.SubmitRequest;
import com.manyi.hims.lookhouse.model.UserLookHouseReq;
import com.manyi.hims.lookhouse.model.UserLookHouseRes;
import com.manyi.hims.lookhouse.model.UserTaskSubmitRequest;
import com.manyi.hims.lookhouse.service.LookHouseService;
import com.manyi.hims.sourcemanage.model.SourceManageResponse;
import com.manyi.hims.sourcemanage.model.SourceManageResponse.HouseImage;
import com.manyi.hims.util.CommonUtils;
import com.manyi.hims.util.OSSObjectUtil;


/**
 * 任务审核模块
 * @author tiger
 *
 */
@Controller
@RequestMapping("/rest/lookHouse")
@SessionAttributes(Global.SESSION_UID_KEY)
public class LookHouseRestController extends RestController{
	
	@Autowired
	@Qualifier("lookHouseService")
	private LookHouseService lookHouseService;
	
	/**
	 *  通过 user task id 获得缩略图 图片
	 * @param session
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/getHouseImageByUserTaskId.rest", produces="application/json")
	@ResponseBody
	public DeferredResult<Response> getHouseImageByUserTaskId(HttpSession session, @RequestBody UserTaskSubmitRequest req) {
		SourceManageResponse sourceManageResponse = new SourceManageResponse();
		sourceManageResponse.setHouseImageList(new ArrayList<HouseImage>());
		
		List<HouseImageFile> list = lookHouseService.getHouseImageList(req.getId());
		HouseImage houseImage = null;
		for (HouseImageFile houseImageFile : list) {
			houseImage = new HouseImage();
			
			houseImage.setImgDes(houseImageFile.getDescription());
			//阿里云路径
//			houseImage.setThumbnailUrl(houseImageFile.getThumbnailKey());
			houseImage.setThumbnailUrl(OSSObjectUtil.getUrl(houseImageFile.getThumbnailKey()));
			sourceManageResponse.getHouseImageList().add(houseImage);
		}
		
		return CommonUtils.deferredResult(sourceManageResponse);
	}
	
	
	/**
	 * 通过 user task id 查询 user task 任务
	 * @param session
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/getUserTaskById.rest", produces="application/json")
	@ResponseBody
	public DeferredResult<Response> getUserTaskById(HttpSession session, @RequestBody SubmitRequest req) {
		final UserLookHouseRes lists = this.lookHouseService.getUserTaskById(req.getId());
		DeferredResult<Response> dr = new DeferredResult<Response>();
		dr.setResult(lists);
		return dr;
	}
	
	/**
	 * 查看BD某个时间段的排班情况
	 * @param session
	 * @param req
	 * @return
	 */
	@SuppressWarnings({ "rawtypes" })
	@RequestMapping(value = "/planList.rest", produces="application/json")
	@ResponseBody
	public DeferredResult<PageResponse> planList(HttpSession session, @RequestBody PlanReq req) {
		final PageResponse<PlanRes> lists = this.lookHouseService.planList(req);
		DeferredResult<PageResponse> dr = new DeferredResult<PageResponse>();
		dr.setResult(lists);
		return dr;
	}

	/**
	 * 房源任务信息  列表
	 * @param session
	 * @param req
	 * @return
	 */
	@SuppressWarnings({ "rawtypes" })
	@RequestMapping(value = "/lookHouseList.rest", produces="application/json")
	@ResponseBody
	public DeferredResult<PageResponse> checktaskList(HttpSession session, @RequestBody LookHouseReq req) {
		final PageResponse<LookHouseRes> lists = this.lookHouseService.lookHouseList(req);
		DeferredResult<PageResponse> dr = new DeferredResult<PageResponse>();
		dr.setResult(lists);
		return dr;
	}
	
	/**
	 * 选择房子,启动 看房任务
	 * @param session
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/addBdTask.rest", produces="application/json")
	@ResponseBody
	public DeferredResult<Response> addBdTask(HttpSession session, @RequestBody LookHouseReq req) {
		final Response lists = this.lookHouseService.addBdTask(req);
		DeferredResult<Response> dr = new DeferredResult<Response>();
		dr.setResult(lists);
		return dr;
	}
	
	/**
	 * 随机抽查看房 选择房子,启动 看房任务
	 * @param session
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/randomBdTask.rest", produces="application/json")
	@ResponseBody
	public DeferredResult<Response> randomBdTask(HttpSession session, @RequestBody RandomReq req) {
		final Response lists = this.lookHouseService.randomBdTask(req);
		DeferredResult<Response> dr = new DeferredResult<Response>();
		dr.setResult(lists);
		return dr;
	}
	
	
	/**
	 * 经纪人 看房 房源任务信息  列表
	 * @param session
	 * @param req
	 * @return
	 */
	@SuppressWarnings({ "rawtypes" })
	@RequestMapping(value = "/userLookHouseList.rest", produces="application/json")
	@ResponseBody
	public DeferredResult<PageResponse> userLookHouseList(HttpSession session, @RequestBody UserLookHouseReq req) {
		final PageResponse<UserLookHouseRes> lists = this.lookHouseService.userLookHouseList(req);
		DeferredResult<PageResponse> dr = new DeferredResult<PageResponse>();
		dr.setResult(lists);
		return dr;
	}
	
}
