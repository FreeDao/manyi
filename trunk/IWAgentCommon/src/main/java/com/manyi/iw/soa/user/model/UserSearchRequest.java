package com.manyi.iw.soa.user.model;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

import lombok.Data;

import com.manyi.iw.soa.common.model.Entity;

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
    
    private Date minLastLoginTime;
    
    private Date maxLastLoginTime;
    
}
