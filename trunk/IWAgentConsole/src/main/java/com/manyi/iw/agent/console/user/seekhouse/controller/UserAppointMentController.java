package com.manyi.iw.agent.console.user.seekhouse.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.manyi.iw.agent.console.base.controller.BaseController;
import com.manyi.iw.agent.console.util.JsonBuilder;

/**
 * 
 * Function: 用户处理约会相关controller
 *
 * @author   yufei
 * @Date	 2014年6月21日		下午6:33:59
 *
 * @see
 */
@Controller
@RequestMapping(value = "/userAppointment", produces = { "application/json;charset=UTF-8" })
public class UserAppointMentController extends BaseController {

    /**
     * 功能描述: 用户点确认 ，约会状态变为待看房2，房源状态变为待看房3，不进历史表
     * <pre>
     *      Modify Reason:(修改原因,不需覆盖，直接追加.)
     *      yufei:   2014年6月19日      新建S
     * </pre>
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/userConfirm", method = RequestMethod.POST)
    @ResponseBody
    public String userConfirm(HttpServletRequest request) {

        Long apponitmentId = Long.valueOf(request.getParameter("apponitmentId"));

        String response = postForObject("/userAppointment/userConfirm.do", apponitmentId, String.class);

        return response;
    }

    /**
     * 
     * 功能描述: 约会经纪人签到 ，约会状态变为经纪人已签到3，进历史表，房源状态变为（历史表和月看表）待登记4 
     * 约会表更新签到时间 pc端没发提取地点
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     yufei:   2014年6月19日      新建S
     * </pre>
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/agentCheckIn", method = RequestMethod.POST)
    @ResponseBody
    public String agentCheckIn(HttpServletRequest request) {
        JsonBuilder jsonBuilder = JsonBuilder.start().put("appointmentId", request.getParameter("appointmentId"))
                .put("ifUserCheckIn", request.getParameter("ifUserCheckIn")).put("flag", request.getParameter("flag"))
                .put("state", request.getParameter("state"));
        String response = postForObject("/userAppointment/agentCheckIn.do", jsonBuilder.getJson(), String.class);

        return response;
    }

    /**
     * 
     * 功能描述: 改为已看房操作
     *    约会表更新签到时间 pc端没发提取地点
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     yufei:   2014年6月19日      新建S
     * </pre>
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/finishState", method = RequestMethod.POST)
    @ResponseBody
    public String finishState(HttpServletRequest request) {

        JsonBuilder jsonBuilder = JsonBuilder.start().put("id", request.getParameter("id"))
                .put("noseeValue", request.getParameter("noseeValue")).put("memo", request.getParameter("memo"))
                .put("customerId", request.getParameter("customerId"))
                .put("appointmentId", request.getParameter("appointmentId"));
        String response = postForObject("/userAppointment/finishState.do", jsonBuilder.getJson(), String.class);

        return response;
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
     */
    @RequestMapping(value = "/afterNoFinishState", method = RequestMethod.POST)
    @ResponseBody
    public String afterNoFinishState(HttpServletRequest request) {

        JsonBuilder jsonBuilder = JsonBuilder.start().put("id", request.getParameter("id"))
                .put("state", request.getParameter("state")).put("noseeValue", request.getParameter("noseeValue"))
                .put("memo", request.getParameter("memo"));

        String response = postForObject("/userAppointment/afterNoFinishState.do", jsonBuilder.getJson(), String.class);

        return response;
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
    @RequestMapping(value = "/changeTimeAppointment", method = RequestMethod.POST)
    @ResponseBody
    public String changeTimeAppointment(Long appoinmentId) {

        String response = postForObject("/userAppointment/changeTimeAppointment.do", appoinmentId, String.class);
        return response;
    }

    /**
     * 
     * 功能描述: 取消约会
     *    
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     yufei:   2014年6月19日      新建S
     * </pre>
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/cancelAppointment", method = RequestMethod.POST)
    @ResponseBody
    public String cancelAppointment(Long appoinmentId) {

        String response = postForObject("/userAppointment/cancelAppointment.do", appoinmentId, String.class);

        return response;
    }

    /**
     * 
     * 功能描述: 获取一个约会的所有房源
     * 如果是1,待确认 2,待看房  从房源表中查
     *  如果状态时  （3,经纪人已签到 4,未到未看房 5,已到未看房 6,已到已看房
     *  7,已改期 8,已取消 ）从历史表中查
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
    public String getAppointmentSeekhouseList(HttpServletRequest request, HttpSession session) {

        JsonBuilder jsonBuilder = JsonBuilder.start().put("agentId", getAgent(session).getId())
                .put("state", request.getParameter("state"))
                .put("appointmentId", request.getParameter("appointmentId"));

        String response = postForObject("/userAppointment/getAppointmentSeekhouseList.do", jsonBuilder.getJson(),
                String.class);

        return response;
    }

    /**
     * 
     * 功能描述: 改为约会状态为已到未看房
     *    约会表更新签到时间 pc端没发提取地点
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     yufei:   2014年6月19日      新建S
     * </pre>
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/appointmetnNoSeeState", method = RequestMethod.POST)
    @ResponseBody
    public String appointmetnNoSeeState(Long appointmentId) {

        String response = postForObject("/userAppointment/appointmetnNoSeeState.do", appointmentId, String.class);
        return response;
    }
}
