package com.manyi.iw.soa.seekhouse.user.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.manyi.iw.soa.appointment.service.AppointmentServiceI;
import com.manyi.iw.soa.base.controller.BaseController;
import com.manyi.iw.soa.common.model.PageResponse;
import com.manyi.iw.soa.seekhouse.model.SeeHouseVo;
import com.manyi.iw.soa.seekhouse.user.service.UserSeekHouseServiceI;

@Controller
@RequestMapping(value = "/userAppointment", produces = { "application/json;charset=UTF-8" })
public class UserAppointmentController extends BaseController {

    @Autowired
    private UserSeekHouseServiceI seekHouseServiceI;

    @Autowired
    private AppointmentServiceI appointmentServiceI;

    /**
     * 
     * 功能描述: 用户点确认 ，约会状态变为待看房2，房源状态变为待看房3，不进历史表
     *
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     yufei:   2014年6月19日      新建S
     * </pre>
     *
     * @param request
     * @return
     */
    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/userConfirm", method = RequestMethod.POST)
    @ResponseBody
    public PageResponse userConfirm(@RequestBody Long appointmentId) {

        PageResponse pageResponse = new PageResponse();

        appointmentServiceI.confirmAppointment(appointmentId);

        return pageResponse;
    }

    /**
     * 
     * 功能描述: 约会经纪人签到 ，约会状态变为经纪人已签到3，进历史表，房源状态变为（历史表和月看表）待登记4 
     *    约会表更新签到时间 pc端没发提取地点
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     yufei:   2014年6月19日      新建S
     * </pre>
     *
     * @param request
     * @return
     */
    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/agentCheckIn", method = RequestMethod.POST)
    @ResponseBody
    public PageResponse agentCheckIn(@RequestBody String data) {
        PageResponse pageResponse = new PageResponse();
        JSONObject jsons = JSONObject.parseObject(data);
        if (jsons.getInteger("flag") > 1) {
            appointmentServiceI.agentCheckInUserNo(jsons.getLong("appointmentId"), jsons.getByte("state"));
        }
        else {
            appointmentServiceI.agentCheckIn(jsons.getLong("appointmentId"));

        }
        return pageResponse;
    }

    /**
     * 
     * 功能描述: 改为已看房操作
     *    约会表更新签到时间 pc端没发提取地点，
     *    @TODO 用户状态统计表  已看房源数字段加1 ，把这个用户的状态该为老用户
     *    @TODO 把约会状态变为6 已到已看房
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     yufei:   2014年6月19日      新建S
     * </pre>
     *
     * @param request
     * @return
     * @throws UnsupportedEncodingException 
     */
    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/finishState", method = RequestMethod.POST)
    @ResponseBody
    public PageResponse finishState(@RequestBody String data) throws UnsupportedEncodingException {

        PageResponse pageResponse = new PageResponse();
        JSONObject jsons = JSONObject.parseObject(data);
        String memo = URLDecoder.decode(jsons.getString("memo"), "utf-8");
        seekHouseServiceI.finishSeekhouse(jsons.getLong("id"), jsons.getLong("appointmentId"),
                jsons.getByte("noseeValue"), memo, jsons.getLong("customerId"));

        return pageResponse;
    }

    /**
     * 
     * 功能描述: 改为未看房后续操作
     *    约会表更新签到时间 pc端没发提取地点
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     yufei:   2014年6月19日      新建S
     * </pre>
     *
     * @param request
     * @return
     * @throws UnsupportedEncodingException 
     */
    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/afterNoFinishState", method = RequestMethod.POST)
    @ResponseBody
    public PageResponse afterNoFinishState(@RequestBody String data) throws UnsupportedEncodingException {

        PageResponse pageResponse = new PageResponse();
        JSONObject jsons = JSONObject.parseObject(data);
        String memo = URLDecoder.decode(jsons.getString("memo"), "utf-8");
        seekHouseServiceI.afterNofinishSeekhouse(jsons.getLong("id"), jsons.getByte("state"),
                jsons.getByte("noseeValue"), memo);
        return pageResponse;
    }

    /**
     * 
     * 功能描述: 改为未看房操作
     *    约会表更新签到时间 pc端没发提取地点
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     yufei:   2014年6月19日      新建S
     * </pre>
     *
     * @param request
     * @return
     */
    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/appointmetnNoSeeState", method = RequestMethod.POST)
    @ResponseBody
    public PageResponse appointmetnNoSeeState(@RequestBody Long appointmentId) {

        PageResponse pageResponse = new PageResponse();
        //type 字段分解 1，已看房操作 2，未看房操作 3，改期状态操作 4，取消状态操作
        appointmentServiceI.updateStateById(new Byte("5"), appointmentId);
        return pageResponse;
    }

    /**
     * 
     * 功能描述: 约会改期
     * 和取消逻辑很像，为了以后扩展放两个方法中。
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     yufei:   2014年6月19日      新建S
     * </pre>
     *
     * @param request
     * @return
     */
    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/changeTimeAppointment", method = RequestMethod.POST)
    @ResponseBody
    public PageResponse changeTimeAppointment(@RequestBody Long appointmentId) {

        PageResponse pageResponse = new PageResponse();
        appointmentServiceI.changeTimeAppointment(appointmentId);
        return pageResponse;
    }

    /**
     * 
     * 功能描述: 约会改期
     *    
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     yufei:   2014年6月19日      新建S
     * </pre>
     *
     * @param request
     * @return
     */
    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/cancelAppointment", method = RequestMethod.POST)
    @ResponseBody
    public PageResponse cancelAppointment(@RequestBody Long appointmentId) {

        PageResponse pageResponse = new PageResponse();
        appointmentServiceI.cancelAppointment(appointmentId);
        return pageResponse;
    }

    /**
     * 
     * 功能描述: 获取约会的所有约看
     *    
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     yufei:   2014年6月19日      新建S
     * </pre>
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/getAppointmentSeekhouseList", method = RequestMethod.POST)
    @ResponseBody
    public PageResponse<SeeHouseVo> getAppointmentSeekhouseList(@RequestBody String data) {

        JSONObject jsons = JSONObject.parseObject(data);
        PageResponse<SeeHouseVo> pageResponse = new PageResponse<SeeHouseVo>();
        List<SeeHouseVo> list = new ArrayList<SeeHouseVo>();
        if (jsons.getInteger("state") > 2) {//从房源历史表取数据
            list = seekHouseServiceI.getSeeHouseHistoryByAppointmentId(jsons.getLong("agentId"),
                    jsons.getLong("appointmentId"));
        }
        else {//从房源表取数据
            list = seekHouseServiceI.getSeeHouseByAppointmentId(jsons.getLong("agentId"),
                    jsons.getLong("appointmentId"));
        }
        pageResponse.setData(list);
        return pageResponse;
    }
}
