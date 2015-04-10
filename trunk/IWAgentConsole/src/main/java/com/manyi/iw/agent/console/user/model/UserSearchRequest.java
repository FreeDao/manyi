package com.manyi.iw.agent.console.user.model;

import lombok.Data;

import com.manyi.iw.agent.console.entity.Entity;

@Data
public class UserSearchRequest extends Entity{
    private Integer collectionNum;
    
    private Integer sawNum;
    
    private Integer inchartNum;
    
    private Integer recommendNum;
    
    private Integer waitdealNum;
    
    private Integer lastLoginTimeSearch;
    
    private String mobile;
    
    private String realName;
    
    /** 业务状态 （0 找房中，1 已租房） */
    private Byte bizStatus;
    
    private Byte gender;
}
