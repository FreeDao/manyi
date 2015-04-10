package com.manyi.iw.agent.console.agent.model;


import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.manyi.iw.agent.console.entity.Entity;

import lombok.Data;

@Data
public class AgentSeekHouseRequest extends Entity{
    private Long agentId;
    private String realname;
    private Byte gender;
    private String mobile;
    private Byte state;
    private Byte userType;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTimeStart;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTimeEnd;
    private Integer district;
    private Integer town;
    private String estateName;
    private Integer livingRoomSum;
    private Integer bedroomSum;
    private Integer wcSum;
    private String floor;
    private Integer decorateType;
    private Double minPrice;
    private Double maxPrice;
}
