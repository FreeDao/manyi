package com.manyi.iw.soa.appointment.model;

import java.util.Date;

import lombok.Data;

@Data
public class AppointmentSearchResponse {
    private Long id;
    
    private String meetAddress;
    
    private Byte appointmentState;
    
    private Date updateTime;
    
    private Date createTime;
    
    private Date appointmentTime;
    
    private String seehouseNumber;
    
    private Integer seeHouseCount;
}
