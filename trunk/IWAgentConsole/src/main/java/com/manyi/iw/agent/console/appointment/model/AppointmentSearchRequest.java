package com.manyi.iw.agent.console.appointment.model;

import java.util.Date;

import lombok.Data;

import com.manyi.iw.agent.console.entity.Entity;

@Data
public class AppointmentSearchRequest extends Entity {
    private Date startTime;
    
    private Date endTime;
    
    private Byte appointmentState;
    
    private Long agentId;
    
    private Long userId;
}
