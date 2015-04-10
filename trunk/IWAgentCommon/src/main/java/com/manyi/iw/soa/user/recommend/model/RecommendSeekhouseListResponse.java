package com.manyi.iw.soa.user.recommend.model;

import java.util.Date;

import lombok.Data;

@Data
public class RecommendSeekhouseListResponse {
    private Long id;
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
    
    private Byte houseStatus;
   
    
    

}
