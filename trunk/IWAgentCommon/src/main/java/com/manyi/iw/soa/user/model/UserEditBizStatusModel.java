package com.manyi.iw.soa.user.model;

import lombok.Data;

@Data
public class UserEditBizStatusModel {
    private Long userId;
    
    private Byte bizStatus;
    
    private String memo;
}
