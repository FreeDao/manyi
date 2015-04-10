package com.manyi.iw.soa.seekhouse.user.model;

import lombok.Data;

@Data
public class SeekhousePositionResponse {
    /**约看编号*/
    private Long id;
    
    /**房源编号*/
    private Long houseId;
    
    /**经度*/
    private Double longitude;
    
    /**经度*/
    private Double latitude;
    
    /**约看状态*/
    private Byte status;
    
    /**推荐来源*/
    private Byte recommendSource;
    
    
    
}
