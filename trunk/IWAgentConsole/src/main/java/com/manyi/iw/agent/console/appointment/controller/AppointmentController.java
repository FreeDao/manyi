package com.manyi.iw.agent.console.appointment.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.manyi.iw.agent.console.appointment.model.AppointmentSearchRequest;
import com.manyi.iw.agent.console.base.controller.BaseController;
import com.manyi.iw.agent.console.entity.Agent;
import com.manyi.iw.agent.console.entity.Appointment;

@Controller
@RequestMapping(value="/appointment",produces={"application/json;charset=UTF-8"})
public class AppointmentController extends BaseController {
    
    

      @InitBinder  
      public void initBinder(WebDataBinder binder) {  
          SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");  
          dateFormat.setLenient(false);  
          binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));  
      } 

    
    /**
     * 功能描述:获取约会列表
     *
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     carvink:   2014年6月21日      新建
     * </pre>
     *
     * @param appointmentSearch
     * @param session
     * @return
     */
    @RequestMapping(value="/list")
    @ResponseBody
    public String getAppointmentList(AppointmentSearchRequest appointmentSearch,HttpSession session){
        Agent agent = getAgent(session);
        appointmentSearch.setAgentId(agent.getId());
        return postForObject("/appointment/list.do", appointmentSearch, String.class);
    }
    
    /**
     * 功能描述: 查询约会，并跳转约会详情页
     *
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     wangbiao:   2014年6月22日      新建
     * </pre>
     *
     * @param appointmentId
     * @param session
     * @param model
     * @return
     */
    @RequestMapping(value="/apmDetail")
    public String getApmDetail(Long appointmentId,HttpSession session,Model model){
        Appointment apm =   postForObject("/appointment/apmDetail.do", appointmentId, Appointment.class);
        model.addAttribute("appointment", apm);
        return "pages/user/apponitmentDetail";
    }
    
        
    @RequestMapping("/appointmentSearch")
    public String appointmentSearch(Long userId,Model model){
        model.addAttribute("userId", userId);
        return "/pages/appointmentSearch";
    }
}
