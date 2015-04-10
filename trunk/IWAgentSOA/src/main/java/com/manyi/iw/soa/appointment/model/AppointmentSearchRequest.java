package com.manyi.iw.soa.appointment.model;

import java.util.Date;

import lombok.Data;

import com.manyi.iw.soa.common.model.Entity;

@Data
public class AppointmentSearchRequest extends Entity {
    private Date startTime;
    
    private Date endTime;
    
    private Byte appointmentState;
    
    private Long agentId;
    
    private Long userId;
    
}
