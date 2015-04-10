package com.manyi.iw.soa.appointment.service;

import java.util.List;

import com.manyi.iw.soa.appointment.model.AppointmentSearchRequest;
import com.manyi.iw.soa.appointment.model.AppointmentSearchResponse;
import com.manyi.iw.soa.appointment.model.UnprocessApmResponse;
import com.manyi.iw.soa.common.model.PageResponse;
import com.manyi.iw.soa.entity.Appointment;
import com.manyi.iw.soa.seekhouse.model.WillAppointMentVo;

public interface AppointmentServiceI {
    public UnprocessApmResponse findUnprocessApm(Long agentId);

    /***
     * 
     * 功能描述:获取某个房源所有将来的约看
     *
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     yufei:   2014年6月18日      新建
     * </pre>
     *
     * @param houseId
     * @return
     */
    public List<WillAppointMentVo> getWillAppointment(Long houseId);

    /**
     * 
     * 功能描述:约会改期
     * 逻辑：约会表 状态字段改为7（已改期 ），约会对应约看表记录状态值改为 7（改期中）,本约会对应约看进入约看结果表
     * 改期中和取消的约会是不能再修改的，在页面上控制。
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     yufei:   2014年6月20日      新建
     * </pre>
     *
     * @param appointmentId
     */
    public void changeTimeAppointment(Long appointmentId);

    /**
     * 功能描述:取消约会
     * 逻辑：约会表 状态字段改为8（已改期 ），约会对应约看表记录状态值改为 8（改期中）,本约会对应约看进入约看结果表
     * 改期中和取消的约会是不能再修改的，在页面上控制。
     * 
     * <pre>其实和约会改期 逻辑很想，为了以后可扩展，暂时不合并</pre>
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     yufei:   2014年6月20日      新建
     * </pre>
     *
     * @param appointmentId
     */
    public void cancelAppointment(Long appointmentId);

    /**
     * 功能描述: 用户点确认 ，约会状态变为待看房2，房源状态变为待看房3，不进历史表
     *
     * <pre>
     *     Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     yufei:   2014年6月21日      新建
     * </pre>
     *
     * @param appointmentId
     */
    public void confirmAppointment(Long appointmentId);

    /**
    /***
     * 
     * 功能描述: 1，约会经纪人签到但用户未签到 ，约会状态变为经纪人已签到3，房源进历史表 状态为4，房源状态变为（历史表和月看表）待登记4 约会表更新签到时间 　pc端没发提取地点
     * 功能描述: 　２，约会经纪人签到并且用户也签到 了，约会状态变为已到未看房５，房源进历史表 状态为4，房源状态变为（历史表和月看表）待登记4 　　约会表更新签到时间 　和用户签到时间　pc端没发提取地点
     *    
     * <pre>
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     yufei:   2014年6月21日      新建
     * </pre>
     *
     * @param appointmentId
     */
    public void agentCheckIn(Long appointmentId);

    /**
     * 功能描述:存历史表，约会状态为未到未看房，房源状态在历史表中未未到未看房，在房源表表的的状态，更具具体后续state而定
     *
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     yufei:   2014年6月24日      新建
     * </pre>
     *
     * @param appointmentId
     * @param state
     */
    public void agentCheckInUserNo(Long appointmentId, Byte state);

    /**
     * 功能描述:获取约会列表
     * <pre>
     *  这里获取的约会列表首先都是针对经纪人的，其次，如果查询
     *  条件中包含了userId，就表示获取某个经纪人与某个用户对应起来的约会列表
     * </pre>
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     carvink:   2014年6月21日      新建
     * </pre>
     *
     * @param appointmentSearch
     * @return
     */
    PageResponse<AppointmentSearchResponse> getAppointmentList(AppointmentSearchRequest appointmentSearch);

    /**
     * 功能描述:获取约会
     *
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     wangbiao:   2014年6月22日      新建
     * </pre>
     *
     * @param id
     * @return
     */
    public Appointment getAppointment(Long id);

    /***
     * 
     * 功能描述:
     * 由于用户状态改变而取消用户所在的约会（ 根据约会状态分
     * 1，经纪人确认前取消, 调用约会取消逻辑
     * 2，经纪人确认后取消，  只删除约看关联记录
     * ），删除所有关联的约看。
     * 约会取消原因是用户已租好房/用户暂时不需要租房
     * 首先，然后删除与用户关联的约看约看表
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     yufei:   2014年6月23日      新建
     * </pre>
     * @param customerId
     */
    public void cancelAppointmentByCustomer(Long customerId);

    /**
     * 功能描述:更新制定id的状态。
     *
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     yufei:   2014年6月24日      新建
     * </pre>
     *
     * @param state
     * @param appointmentId
     */
    public void updateStateById(Byte state, Long appointmentId);
}
