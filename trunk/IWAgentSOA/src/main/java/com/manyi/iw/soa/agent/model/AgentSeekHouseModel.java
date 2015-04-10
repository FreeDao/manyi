package com.manyi.iw.soa.agent.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class AgentSeekHouseModel {
    private String realname;
    private Byte gender;
    private String mobile;
    private Integer userType;
    private String district;
    private String town;
    private String estate;
    private String building;
    private String room;
    private Double rentPrice;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    private Byte state;
    private Long userId;
    
    private Integer bedroomSum;
    private Integer livingRoomSum;
    private Integer wcSum;
    private Integer floor;
}
