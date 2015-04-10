package com.manyi.hims.lookhouse.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.DeferredResult;

import com.manyi.hims.BaseController;
import com.manyi.hims.Response;
import com.manyi.hims.bd.service.HouseResourcePhotoService;
import com.manyi.hims.check.model.CSSingleRequest;
import com.manyi.hims.lookhouse.model.SubmitRequest;
import com.manyi.hims.lookhouse.model.UserTaskSubmitRequest;
import com.manyi.hims.lookhouse.service.LookHouseService;
import com.manyi.hims.usertask.service.UserTaskPhotoService;
import com.manyi.hims.util.CommonUtils;


/**
 * 看房任务模块
 * @author howard
 *
 */
@Controller
@RequestMapping("/lookHouse")
public class LookHouseController extends BaseController {
	
	private Logger logger = LoggerFactory.getLogger(LookHouseController.class);

	
	@Autowired
	@Qualifier("lookHouseService")
	private LookHouseService lookHouseService;
	
	@Autowired
	private HouseResourcePhotoService houseResourcePhotoService;
	
	@Autowired
	private UserTaskPhotoService userTaskPhotoService;

	
	@RequestMapping(value = "/single.rest", produces="application/json")
	@ResponseBody
	public DeferredResult<Response> single(@RequestBody CSSingleRequest css) {
		return CommonUtils.deferredResult(lookHouseService.single(css));
	}
	
	
	@RequestMapping(value = "/submit.rest", produces="application/json")
	@ResponseBody
	public DeferredResult<Response> submit(HttpSession session, @RequestBody SubmitRequest sr) {
		return CommonUtils.deferredResult(lookHouseService.submit(sr));
	}
	
	@RequestMapping(value = "/userTaskSubmit.rest", produces="application/json")
	@ResponseBody
	public DeferredResult<Response> submit(HttpSession session, @RequestBody UserTaskSubmitRequest ut) {
		return CommonUtils.deferredResult(lookHouseService.userTaskSubmit(ut));
	}
	
	 /**
     * @date 2014年6月3日 下午11:53:58
   	 * @author Tom
   	 * @description  
   	 * 当尝试3次上传阿里云失败后，不能再上传，图片都在服务器上；这是小概率事件
   	 * 
   	 * 弥补这种情况，我们可以单独对没有上传的图片进行再次上传操作。
   	 * 
   	 * 查询前一小时的数据
   	 * 
   	 */
	@RequestMapping(value = "/uploadOSSAgain.rest", produces= "application/json")
	@ResponseBody
	public DeferredResult<Response> uploadOSSAgain() {
		
		logger.info("手动触发再次上传没有成功的图片，开始。。。");
		
		logger.info("上传BD任务图片，开始。。。");
		houseResourcePhotoService.uploadFile2aliyunOSSAgain();
		logger.info("上传BD任务图片，结束。。。");
		
		logger.info("上传中介任务图片，开始。。。");
		userTaskPhotoService.uploadFile2aliyunOSSAgain();
		logger.info("上传中介任务图片，结束。。。");
		
		
		return CommonUtils.deferredResult(new Response());
	}
	
	
	
}
