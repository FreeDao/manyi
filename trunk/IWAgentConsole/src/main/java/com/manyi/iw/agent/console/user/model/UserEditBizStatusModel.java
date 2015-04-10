package com.manyi.iw.agent.console.user.model;

import lombok.Data;

@Data
public class UserEditBizStatusModel {
    private Long userId;
    
    private Byte bizStatus;
    
    private String memo;
}
