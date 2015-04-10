package com.manyi.iw.soa.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.manyi.iw.soa.appointment.model.AppointmentSearchRequest;
import com.manyi.iw.soa.appointment.model.AppointmentSearchResponse;
import com.manyi.iw.soa.entity.Appointment;
import com.manyi.iw.soa.seekhouse.model.WillAppointMentVo;

public interface AppointmentMapper {
    /**
     *
     */
    int deleteByPrimaryKey(Long id);

    /**
     *
     */
    int insert(Appointment record);

    /**
     *
     */
    int insertSelective(Appointment record);

    /**
     *
     */
    Appointment selectByPrimaryKey(Long id);

    /**
     *
     */
    int updateByPrimaryKeySelective(Appointment record);

    /**
     *
     */
    int updateByPrimaryKey(Appointment record);

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

    /***
     * 
     * 功能描述:根据id 更改约会状态
     *
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     yufei:   2014年6月20日      新建
     * </pre>
     *
     * @param state
     * @param id
     */
    public void updateStateById(@Param("appointmentState") Byte state, @Param("id") Long id);

    /**
     * 功能描述:根据id 更改约会状态  经纪人（包含用户签到）签到用 更新 经纪人签到时间（签到地址无法获取）
     *
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     yufei:   2014年6月21日      新建
     * </pre>
     *
     * @param state
     * @param id
     * @param agentCheckinTime 
     * @param userCheckinTime
     */
    public void updateCheckinStateById(@Param("appointmentState") Byte state, @Param("id") Long id,
            @Param("agentCheckinTime") Date agentCheckinTime, @Param("userCheckinTime") Date userCheckinTime);

    /**
     * 功能描述:查询约会列表
     *
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     carvink:   2014年6月21日      新建
     * </pre>
     *
     * @param appointmentSearch
     * @return
     */
    List<AppointmentSearchResponse> getAppointmentList(AppointmentSearchRequest appointmentSearch);

    /**
     * 功能描述:获取约会列表总数量
     *
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     carvink:   2014年6月21日      新建
     * </pre>
     *
     * @return
     */
    int getAppointmentListTotal(AppointmentSearchRequest appointmentSearch);

    /**
     * 功能描述:获取所有这个用户没取消和改期的约会
     *
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     yufei:   2014年6月24日      新建
     * </pre>
     *
     * @param customerId
     * @return
     */
    public List<Appointment> getAppointmentInUseByCustomerId(@Param("customerId") Long customerId);

}