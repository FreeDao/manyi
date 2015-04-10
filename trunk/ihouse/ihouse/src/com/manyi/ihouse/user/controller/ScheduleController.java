package com.manyi.ihouse.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.DeferredResult;

import com.manyi.ihouse.base.BaseController;
import com.manyi.ihouse.base.Response;
import com.manyi.ihouse.user.model.AppointmentDetailRequest;
import com.manyi.ihouse.user.model.AppointmentOperateRequest;
import com.manyi.ihouse.user.model.CommentRequest;
import com.manyi.ihouse.user.model.HaveSeePageRequest;
import com.manyi.ihouse.user.model.HaveSeePageResponse;
import com.manyi.ihouse.user.model.IsHaveTripResponse;
import com.manyi.ihouse.user.model.MyRequest;
import com.manyi.ihouse.user.model.SeeDetailResponse;
import com.manyi.ihouse.user.model.SeeHouseSubmitResponse;
import com.manyi.ihouse.user.model.ToBeSeeDetailResponse;
import com.manyi.ihouse.user.model.ToBeSeePageRequest;
import com.manyi.ihouse.user.model.ToBeSeePageResponse;
import com.manyi.ihouse.user.service.ScheduleService;
@Controller
@RequestMapping("/schedule")
public class ScheduleController extends BaseController{
    
    @Autowired
    @Qualifier("scheduleService")
    private ScheduleService scheduleService;

    public ScheduleService getScheduleService() {
        return scheduleService;
    }

    public void setScheduleService(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
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
        IsHaveTripResponse reponse = scheduleService.isHaveTrip(request);
        
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
        ToBeSeePageResponse toBeSeePageResponse = scheduleService.toBeSee(request);
//      AppointmentModel
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
        SeeHouseSubmitResponse response = scheduleService.seeHouseSubmit(request);
        
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
        ToBeSeeDetailResponse toBeSeeDetailResponse = scheduleService.toBeSeeDetail(request);
        
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
        Response response = scheduleService.changeAppointDate(request);
        
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
        Response response = scheduleService.cancelAppoint(request);
        
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
        HaveSeePageResponse haveSeeResponse = scheduleService.haveSee(request);
        
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
        Response response = scheduleService.comment(request);
        
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
        SeeDetailResponse response = scheduleService.commentDetail(request);
        
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
        SeeDetailResponse response = scheduleService.cancelDetail(request);
        
        DeferredResult<Response> dr = new DeferredResult<Response>();
        dr.setResult(response);
        return dr;
    }
}
