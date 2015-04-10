package com.manyi.iw.soa.user.recommend.model;

import java.util.Date;

import lombok.Data;

@Data
public class RecommendListResponse {
    /**区域*/
    private String district;
    /**板块*/
    private String town;
    /**小区*/
    private String estate;
    /**houseId*/
    private Integer houseId;
    /**房号*/
    private String room;
    /**几室几厅几卫*/
    private String residence;
    /**面积*/
    private Integer coveredArea;
    /**装修*/
    private Integer decorateType; 
    /**出租价格*/
    private Double rentPrice;
    /**联系人*/
    private String contact;
    
    private Date createTime;
    
    
    private Byte houseStatus;
    /**1：已删除，2：已约看，3：已下架*/
    private Byte status;
    
    /**房源状态*/
    /**约看状态*/
    private Byte seekhouseStatus;

}
