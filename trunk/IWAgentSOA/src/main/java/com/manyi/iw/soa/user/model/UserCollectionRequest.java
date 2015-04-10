package com.manyi.iw.soa.user.model;

import lombok.Data;

@Data
public class UserCollectionRequest {
    private Long houseId;
    
    private Long userId;
    
    private String memo;
}
