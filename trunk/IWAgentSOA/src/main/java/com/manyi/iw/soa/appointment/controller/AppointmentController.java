package com.manyi.iw.soa.appointment.controller;

import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.manyi.iw.soa.appointment.model.AppointmentSearchRequest;
import com.manyi.iw.soa.appointment.model.AppointmentSearchResponse;
import com.manyi.iw.soa.appointment.service.AppointmentServiceI;
import com.manyi.iw.soa.common.model.PageResponse;
import com.manyi.iw.soa.entity.Appointment;

@Controller
@RequestMapping(value="/appointment")
public class AppointmentController {
	   
    @Autowired
    private AppointmentServiceI appointmentServiceI; 
    
    @RequestMapping(value="/list")
    @ResponseBody
    public PageResponse<AppointmentSearchResponse> getAppointmentList(@RequestBody AppointmentSearchRequest appointmentSearch){
        return appointmentServiceI.getAppointmentList(appointmentSearch);
    }
    
    @RequestMapping(value="/apmDetail")
    @ResponseBody
    public Appointment getApmDetail(@RequestBody Long appointmentId,HttpSession session,Model model){
        
        return  appointmentServiceI.getAppointment(appointmentId);
    }
}
