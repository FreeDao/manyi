package com.manyi.ihouse.user.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.DeferredResult;

import com.manyi.ihouse.base.BaseController;
import com.manyi.ihouse.base.PageResponse;
import com.manyi.ihouse.base.Response;
import com.manyi.ihouse.user.model.AppointmentDetailRequest;
import com.manyi.ihouse.user.model.AppointmentHouseRequest;
import com.manyi.ihouse.user.model.AppointmentOperateRequest;
import com.manyi.ihouse.user.model.AutoLoginRequest;
import com.manyi.ihouse.user.model.BindFeedbackRequest;
import com.manyi.ihouse.user.model.CollectionRequest;
import com.manyi.ihouse.user.model.CommentRequest;
import com.manyi.ihouse.user.model.DeleteCollectionRequest;
import com.manyi.ihouse.user.model.DeleteSeekHouseRequest;
import com.manyi.ihouse.user.model.FeedbackRequest;
import com.manyi.ihouse.user.model.HaveSeePageRequest;
import com.manyi.ihouse.user.model.HaveSeePageResponse;
import com.manyi.ihouse.user.model.HouseBaseModel;
import com.manyi.ihouse.user.model.IsHaveTripResponse;
import com.manyi.ihouse.user.model.MyAgentResponse;
import com.manyi.ihouse.user.model.MyRequest;
import com.manyi.ihouse.user.model.PageListRequest;
import com.manyi.ihouse.user.model.RreshDataResponse;
import com.manyi.ihouse.user.model.SeeDetailResponse;
import com.manyi.ihouse.user.model.SeeHouseSubmitResponse;
import com.manyi.ihouse.user.model.SeekHouseRequest;
import com.manyi.ihouse.user.model.SeekHouseResponse;
import com.manyi.ihouse.user.model.ToBeSeeDetailResponse;
import com.manyi.ihouse.user.model.ToBeSeePageRequest;
import com.manyi.ihouse.user.model.ToBeSeePageResponse;
import com.manyi.ihouse.user.model.UpdateCollectionRequest;
import com.manyi.ihouse.user.model.UpdateInfoRequest;
import com.manyi.ihouse.user.model.UpdateInfoResponse;
import com.manyi.ihouse.user.model.UserLoginRequest;
import com.manyi.ihouse.user.model.UserLoginResponse;
import com.manyi.ihouse.user.model.UserMyResponse;
import com.manyi.ihouse.user.service.UserService;

/**
 * 用户的操作Controller
 * @author hubin
 *
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseController {

	@Autowired
	@Qualifier("userService")
	private UserService userService;
	
	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	/**
	 * 发送验证码
	 * @param session
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/sendVerifyCode.rest", produces="application/json")
	@ResponseBody
	public DeferredResult<Response> sendVerifyCode(HttpSession session, @RequestBody UserLoginRequest request) {
		
		Response result = userService.sendVerifyCode(session,request);
		DeferredResult<Response> dr = new DeferredResult<Response>();
		dr.setResult(result);
		return dr;
	}

	/**
	 * 手机应用登陆验证
	 * @param session
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/userLogin.rest", produces="application/json")
	@ResponseBody
	public DeferredResult<Response> userLogin(HttpSession session, @RequestBody UserLoginRequest request) {
		UserLoginResponse result = userService.login(session,request);
		DeferredResult<Response> dr = new DeferredResult<Response>();
		dr.setResult(result);
		return dr;
	}
	
    /**
     * 手机应用登出操作
     * @param session
     * @param request
     * @return
     */
    @RequestMapping(value = "/userLogout.rest", produces="application/json")
    @ResponseBody
    public DeferredResult<Response> userLogout(HttpSession session, @RequestBody MyRequest request) {
        Response result = userService.userLogout(session,request);
        DeferredResult<Response> dr = new DeferredResult<Response>();
        dr.setResult(result);
        return dr;
    }

	/**
	 * 手机应用自动登陆
	 * @param session
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/autoLogin.rest", produces="application/json")
	@ResponseBody
	public DeferredResult<Response> autoLogin(HttpSession session, @RequestBody AutoLoginRequest request) {
		UserLoginResponse result = userService.autoLogin(session,request);
		DeferredResult<Response> dr = new DeferredResult<Response>();
		dr.setResult(result);
		return dr;
	}
	
	
	
	/**
	 * 获取‘我的’页面信息
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/my.rest", produces="application/json")
	@ResponseBody
	public  DeferredResult<Response> my(@RequestBody MyRequest request){
		UserMyResponse userMyResponse = userService.my(request);
		
		DeferredResult<Response> dr = new DeferredResult<Response>();
		dr.setResult(userMyResponse);
		return dr;
	}

    /**
     * 功能描述:获取我的经纪人
     *
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     hubin:   2014年6月16日      新建
     * </pre>
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/myAgent.rest", produces="application/json")
    @ResponseBody
    public  DeferredResult<Response> myAgent(@RequestBody MyRequest request){
        MyAgentResponse userMyResponse = userService.myAgent(request);
        
        DeferredResult<Response> dr = new DeferredResult<Response>();
        dr.setResult(userMyResponse);
        return dr;
    }
	
    
    /**
     * 
     * 功能描述:获取最新全局数据
     *
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     hubin:   2014年6月16日      新建
     * </pre>
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/freshData.rest", produces="application/json")
    @ResponseBody
    public  DeferredResult<Response> freshData(@RequestBody MyRequest request){
        RreshDataResponse reponse = userService.freshData(request);
        
        DeferredResult<Response> dr = new DeferredResult<Response>();
        dr.setResult(reponse);
        return dr;
    }
    
    /**
     * 功能描述:获取更新信息
     *
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     hubin:   2014年6月16日      新建
     *     
     * </pre>
     *
     * @return
     */
    @RequestMapping(value = "/updateInfo.rest", produces="application/json")
    @ResponseBody
	public DeferredResult<Response> updateInfo(@RequestBody UpdateInfoRequest request){
       UpdateInfoResponse res = userService.updateInfo(request);
	   DeferredResult<Response> dr = new DeferredResult<Response>();
       dr.setResult(res);
	   return dr;
	   
	    
	}
	
	/**
	 * 意见反馈(Feedback)
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/feedback.rest", produces="application/json")
	@ResponseBody
	public  DeferredResult<Response> feedback(@RequestBody FeedbackRequest request){
	    Response response = userService.feedback(request);
		
		DeferredResult<Response> dr = new DeferredResult<Response>();
		dr.setResult(response);
		return dr;
	}
	
	
	/**
	 * 登陆后将登陆前提交的反馈信息绑定到该账号
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/bindFeedback.rest", produces="application/json")
	@ResponseBody
	public  DeferredResult<Response> bindFeedback(HttpSession session, @RequestBody BindFeedbackRequest request){
		Response response = userService.bindFeedback(session, request);
		
		DeferredResult<Response> dr = new DeferredResult<Response>();
		dr.setResult(response);
		return dr;
	}
	
    /**
     * 添加收藏房源
     * @param request
     * @return
     */
    @RequestMapping(value = "/collect.rest", produces="application/json")
    @ResponseBody
    public  DeferredResult<Response> collect(@RequestBody CollectionRequest request){
        Response response = userService.collect(request);
        
        DeferredResult<Response> dr = new DeferredResult<Response>();
        dr.setResult(response);
        return dr;
    }
	
	/**
	 * 我的收藏
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/myCollection.rest", produces="application/json")
	@ResponseBody
	public  DeferredResult<Response> myCollection(HttpSession session, @RequestBody PageListRequest request){
		PageResponse<HouseBaseModel> collectionResponse =  userService.myCollection(session, request);
		DeferredResult<Response> dr = new DeferredResult<Response>();
		dr.setResult(collectionResponse);
		return dr;
	}
	
	/**
	 * 删除我的收藏中的房源
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/deleteCollection.rest", produces="application/json")
	@ResponseBody
	public  DeferredResult<Response> deleteCollection(@RequestBody DeleteCollectionRequest request){
		Response response = userService.deleteCollection(request);
		
		DeferredResult<Response> dr = new DeferredResult<Response>();
		dr.setResult(response);
		return dr;
	}
	
	/**
     * 同步收藏中的房源
     * @param request
     * @return
     */
    @RequestMapping(value = "/updateCollection.rest", produces="application/json")
    @ResponseBody
    public  DeferredResult<Response> updateCollection(@RequestBody UpdateCollectionRequest request){
        Response response = userService.updateCollection(request);
        
        DeferredResult<Response> dr = new DeferredResult<Response>();
        dr.setResult(response);
        return dr;
    }
	
	/**
	 * 看房单页面
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/seekHouse.rest", produces="application/json")
	@ResponseBody
	public  DeferredResult<Response> seekHouse(@RequestBody SeekHouseRequest request){
		SeekHouseResponse seekHouseResponse = userService.seekHouse(request);
		
		DeferredResult<Response> dr = new DeferredResult<Response>();
		dr.setResult(seekHouseResponse);
		return dr;
	}
	
	/**
	 * 删除看房单中的房源
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/deleteSeekHouse.rest", produces="application/json")
	@ResponseBody
	public  DeferredResult<Response> deleteSeekHouse(@RequestBody DeleteSeekHouseRequest request){
		Response response = userService.deleteSeekHouse(request);
		
		DeferredResult<Response> dr = new DeferredResult<Response>();
		dr.setResult(response);
		return dr;
	}
	
	/**
	 * 将看房单页中选中的房源提交约看
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/appointmentHouse.rest", produces="application/json")
	@ResponseBody
	public  DeferredResult<Response> appointmentHouse(@RequestBody AppointmentHouseRequest request){
		Response response = userService.appointmentHouse(request);
		
		DeferredResult<Response> dr = new DeferredResult<Response>();
		dr.setResult(response);
		return dr;
	}

    /**
     * 
     * 功能描述:用户是否有行程
     *
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     hubin:   2014年6月16日      新建
     * </pre>
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/isHaveTrip.rest", produces="application/json")
    @ResponseBody
    public  DeferredResult<Response> isHaveTrip(@RequestBody MyRequest request){
        IsHaveTripResponse reponse = userService.isHaveTrip(request);
        
        DeferredResult<Response> dr = new DeferredResult<Response>();
        dr.setResult(reponse);
        return dr;
    }
	
	/**
	 * 功能描述: 行程-待看的列表
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     hubin:   2014年6月13日      新建
	 * </pre>
	 *
	 * @param request 
	 * @return
	 */
	@RequestMapping(value = "/toBeSee.rest", produces="application/json")
	@ResponseBody
	public  DeferredResult<Response> toBeSee(@RequestBody ToBeSeePageRequest request){
		ToBeSeePageResponse toBeSeePageResponse = userService.toBeSee(request);
//		AppointmentModel
		DeferredResult<Response> dr = new DeferredResult<Response>();
		dr.setResult(toBeSeePageResponse);
		return dr;
	}
	
	
	/**
	 * 确认看房
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/seeHouseSubmit.rest", produces="application/json")
	@ResponseBody
	public  DeferredResult<Response> seeHouseSubmit(@RequestBody AppointmentOperateRequest request){
	    SeeHouseSubmitResponse response = userService.seeHouseSubmit(request);
		
		DeferredResult<Response> dr = new DeferredResult<Response>();
		dr.setResult(response);
		return dr;
	}
	
	
	/**
	 * 已确认待看房-详情页
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/toBeSeeDetail.rest", produces="application/json")
	@ResponseBody
	public  DeferredResult<Response> toBeSeeDetail(@RequestBody AppointmentDetailRequest request){
		ToBeSeeDetailResponse toBeSeeDetailResponse = userService.toBeSeeDetail(request);
		
		DeferredResult<Response> dr = new DeferredResult<Response>();
		dr.setResult(toBeSeeDetailResponse);
		return dr;
	}
	
	
	/**
	 * 申请改期
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/changeAppointDate.rest", produces="application/json")
	@ResponseBody
	public  DeferredResult<Response> changeAppointDate(@RequestBody AppointmentOperateRequest request){
		Response response = userService.changeAppointDate(request);
		
		DeferredResult<Response> dr = new DeferredResult<Response>();
		dr.setResult(response);
		return dr;
	}
	
	/**
	 * 取消看房
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/cancelAppoint.rest", produces="application/json")
	@ResponseBody
	public  DeferredResult<Response> cancelAppoint(@RequestBody AppointmentOperateRequest request){
		Response response = userService.cancelAppoint(request);
		
		DeferredResult<Response> dr = new DeferredResult<Response>();
		dr.setResult(response);
		return dr;
	}
	
	/**
	 * 行程-已看的列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/haveSee.rest", produces="application/json")
	@ResponseBody
	public  DeferredResult<Response> haveSee(@RequestBody HaveSeePageRequest request){
		HaveSeePageResponse haveSeeResponse = userService.haveSee(request);
		
		DeferredResult<Response> dr = new DeferredResult<Response>();
		dr.setResult(haveSeeResponse);
		return dr;
	}
	
	/**
	 * 评价提交
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/comment.rest", produces="application/json")
	@ResponseBody
	public  DeferredResult<Response> comment(@RequestBody CommentRequest request){
		Response response = userService.comment(request);
		
		DeferredResult<Response> dr = new DeferredResult<Response>();
		dr.setResult(response);
		return dr;
	}
	
	/**
	 * 已看的已评价-详情页commentDetail
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/commentDetail.rest", produces="application/json")
	@ResponseBody
	public  DeferredResult<Response> commentDetail(@RequestBody AppointmentDetailRequest request){
		SeeDetailResponse response = userService.commentDetail(request);
		
		DeferredResult<Response> dr = new DeferredResult<Response>();
		dr.setResult(response);
		return dr;
	}
	
	/**
	 * 已取消-详情页cancelDetail
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/cancelDetail.rest", produces="application/json")
	@ResponseBody
	public  DeferredResult<Response> cancelDetail(@RequestBody AppointmentDetailRequest request){
		SeeDetailResponse response = userService.cancelDetail(request);
		
		DeferredResult<Response> dr = new DeferredResult<Response>();
		dr.setResult(response);
		return dr;
	}
	
	
	
}
