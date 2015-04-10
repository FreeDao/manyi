package com.manyi.fyb.callcenter.lookhouse.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.manyi.fyb.callcenter.base.controller.BaseController;
import com.manyi.fyb.callcenter.base.model.Response;
import com.manyi.fyb.callcenter.check.model.CSSingleRequest;
import com.manyi.fyb.callcenter.lookhouse.model.LookHouseReq;
import com.manyi.fyb.callcenter.lookhouse.model.MakeCallResponse;
import com.manyi.fyb.callcenter.lookhouse.model.PlanReq;
import com.manyi.fyb.callcenter.lookhouse.model.RandomReq;
import com.manyi.fyb.callcenter.lookhouse.model.SubmitRequest;
import com.manyi.fyb.callcenter.lookhouse.model.UserLookHouseReq;
import com.manyi.fyb.callcenter.lookhouse.model.UserTaskRequest;
import com.manyi.fyb.callcenter.lookhouse.model.UserTaskSubmitRequest;
import com.manyi.fyb.callcenter.utils.HttpClientHelper;
import com.manyi.fyb.callcenter.utils.SessionUtils;



@Controller
@RequestMapping("/lookHouse")
public class LookHouseController extends BaseController{
	
	@RequestMapping("/single")
	public ModelAndView single(CSSingleRequest csc ,ModelAndView mav , HttpServletRequest request){
		
		csc.setEmployeeId(SessionUtils.getSession(request).getId());
		MakeCallResponse css = HttpClientHelper.sendRestJsonShortObj2Obj("/lookHouse/single.rest", csc, MakeCallResponse.class);
		mav.addObject("css" , css);
		
		mav.setViewName("lookhouse/makecall");
		return mav;
	}
	
	@RequestMapping("/userSingle")
	public ModelAndView userSingle(UserTaskRequest csc ,ModelAndView mav , HttpServletRequest request){
		
		mav.addObject("houseId",csc.getHouseId());//house id
		mav.addObject("id",csc.getId());// task Id
		
		mav.setViewName("lookhouse/checkUserLookHouse");
		return mav;
	}
	
	@RequestMapping("/submit")
	@ResponseBody
	public String submitMakingCall(SubmitRequest sr , HttpServletRequest request){
		sr.setOperatorId(SessionUtils.getSession(request).getId());
		synchronized (this) {
			Response response = HttpClientHelper.sendRestJsonShortObj2Obj("/lookHouse/submit.rest", sr, Response.class);
			if (response.getErrorCode() == 0) {
				return "success";
			}else {
				return response.getMessage();
			}
			
		}
		
		
	}
	
	@RequestMapping("/userTaskSubmit")
	@ResponseBody
	public Response submitCheckUser(UserTaskSubmitRequest ut, HttpServletRequest request){
		ut.setOperatorId(SessionUtils.getSession(request).getId());
		synchronized (this) {
			Response response = HttpClientHelper.sendRestJsonShortObj2Obj("/lookHouse/userTaskSubmit.rest", ut, Response.class);
			return response;
			
		}
		
		
	}

	/**
	 * 跳转到 对应的 审核 列表页面
	 * @param request
	 * @return
	 */
	@RequestMapping("/lookHouseGrid")
	private String index(HttpServletRequest request ){
		return "lookhouse/lookHouseGrid";
	}
	/**
	 * 跳转到 对应的 userlookHouseGrid.jsp
	 * @param request
	 * @return
	 */
	@RequestMapping("/userLookHouseGrid")
	private String userLookHouseListGrid(HttpServletRequest request ){
		return "lookhouse/userLookHouseGrid";
	}
	
	/**
	 * 跳转到 对应的 fullcalendar页面
	 * @param request
	 * @return
	 */
	@RequestMapping("/fullCal")
	private String fullCal(PlanReq req ,HttpServletRequest request ){
		request.getSession().setAttribute("bdId", req.getBdId());
		return "lookhouse/fullCal";
	}
	
	/**
	 * @date 2014年5月26日 下午6:04:05
	 * @description  
	 * 通过 user task id 获得缩略图 图片
	 */
	@RequestMapping("/getHouseImageByUserTaskId")
	@ResponseBody
	private String getHouseImageByUserTaskId(UserTaskSubmitRequest req) {
		//添加参数
		JSONObject jobj = HttpClientHelper.sendRestInterShortObject("/rest/lookHouse/getHouseImageByUserTaskId.rest",req);
		return jobj.toString();
		
	}
	
	/**
	 * 查看房源 列表页面
	 * @param session
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/lookHouseList")
	@ResponseBody
	public String lookHouseList(LookHouseReq req,HttpServletRequest request ) {
		
		//添加参数
		JSONObject jobj = HttpClientHelper.sendRestInterShortObject("/rest/lookHouse/lookHouseList.rest",req);
		return jobj.toString();
	}
	
	/**
	 * 通过 user task id 查询 user task 任务
	 * @param session
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/getUserTaskById")
	@ResponseBody
	public JSONObject getUserTaskById(SubmitRequest req,HttpServletRequest request ) {
		
		//添加参数
		JSONObject jobj = HttpClientHelper.sendRestInterShortObject("/rest/lookHouse/getUserTaskById.rest",req);
		return jobj;
	}
	
	/**
	 * 查看经纪人 看房  列表页面
	 * @param session
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/userLookHouseList")
	@ResponseBody
	public String userLookHouseList(UserLookHouseReq req,HttpServletRequest request ) {
		
		//添加参数
		JSONObject jobj = HttpClientHelper.sendRestInterShortObject("/rest/lookHouse/userLookHouseList.rest",req);
		return jobj.toString();
	}
	
	/**
	 * 查看BD某个时间段的排班情况
	 * @param session
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/planList", produces="application/json")
	@ResponseBody
	public JSONObject planList(HttpSession session,@RequestBody PlanReq req) {
		// 添加参数
		JSONObject jobj = HttpClientHelper.sendRestInterShortObject("/rest/lookHouse/planList.rest", req);
		return jobj;
	}
	
	/**	
	 * 选择房子,启动 看房任务
	 * addBdTask
	 * @param session
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/addBdTask" ,produces= "application/json")
	@ResponseBody
	public JSONObject addBdTask(@RequestBody  LookHouseReq req,HttpServletRequest request ) {
		
		req.setManageId(SessionUtils.getSession(request).getId());//写入当前登录人ID
		//添加参数
		JSONObject jobj = HttpClientHelper.sendRestInterShortObject("/rest/lookHouse/addBdTask.rest",req);
		return jobj;
	}
	
	/**
	 * 随机抽查看房 选择房子,启动 看房任务
	 * addBdTask
	 * @param session
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/randomBdTask" ,produces= "application/json")
	@ResponseBody
	public JSONObject randomBdTask(@RequestBody RandomReq req,HttpServletRequest request ) {
		
		req.setManageId(SessionUtils.getSession(request).getId());//写入当前登录人ID
		//添加参数
		JSONObject jobj = HttpClientHelper.sendRestInterShortObject("/rest/lookHouse/randomBdTask.rest",req);
		return jobj;
	}
	
	 /**
     * @date 2014年6月3日 下午11:53:58
   	 * @author Tom
   	 * @description  
   	 * 当尝试3次上传阿里云失败后，不能再上传，图片都在服务器上；这是小概率事件
   	 * 
   	 * 弥补这种情况，我们可以单独对没有上传的图片进行再次上传操作。
   	 * 
   	 * 查询前一天的数据
   	 * 
   	 */
	@RequestMapping(value = "/uploadOSSAgain")
	@ResponseBody
	public JSONObject uploadOSSAgain() {
		JSONObject jobj = HttpClientHelper.sendRestInterShort("/lookHouse/uploadOSSAgain.rest");
		return jobj;
	}
}
