package com.manyi.ihouse.user.service;

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

public interface ScheduleService {
    
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
    public IsHaveTripResponse isHaveTrip(MyRequest request);
    
    /**
     * 行程-待看的列表
     * @param request
     * @return
     */
    public ToBeSeePageResponse toBeSee(ToBeSeePageRequest request);

    /**
     * 确认看房
     * @param request
     * @return
     */
    public SeeHouseSubmitResponse seeHouseSubmit(AppointmentOperateRequest request);

    /**
     * 已确认待看房-详情页
     * @param request
     * @return
     */
    public ToBeSeeDetailResponse toBeSeeDetail(AppointmentDetailRequest request);

    /**
     * 申请改期
     * @param request
     * @return
     */
    public Response changeAppointDate(AppointmentOperateRequest request);

    /**
     * 取消看房
     * @param request
     * @return
     */
    public Response cancelAppoint(AppointmentOperateRequest request);

    /**
     * 行程-已看的列表
     * @param request
     * @return
     */
    public HaveSeePageResponse haveSee(HaveSeePageRequest request);

    /**
     * 评价提交
     * @param request
     * @return
     */
    public Response comment(CommentRequest request);

    /**
     * 已看的已评价-详情页
     * @param request
     * @return
     */
    public SeeDetailResponse commentDetail(AppointmentDetailRequest request);

    /**
     * 已取消-详情页
     * @param request
     * @return
     */
    public SeeDetailResponse cancelDetail(AppointmentDetailRequest request);
}
