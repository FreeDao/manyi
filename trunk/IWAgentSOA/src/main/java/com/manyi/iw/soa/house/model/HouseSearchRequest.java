package com.manyi.iw.soa.house.model;

import com.manyi.iw.soa.common.model.Entity;

import lombok.Data;


@Data
public class HouseSearchRequest extends Entity{
    /**区域编号*/
    private Integer district;
    /**板块编号*/
    private Integer town;
    /**小区名称*/
    private String estateName;
    /**
     * 装修类型
     */
    private Integer decorateType;
    
    private Integer livingRoomSum;
    
    private Integer bedroomSum;
    
    private Integer wcSum;
    
    private Double minPrice;
    
    private Double maxPrice;
    
    
}
