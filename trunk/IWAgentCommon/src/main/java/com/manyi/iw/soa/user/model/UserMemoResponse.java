package com.manyi.iw.soa.user.model;

import java.sql.Timestamp;

import lombok.Data;

import com.manyi.iw.soa.common.model.Entity;

@Data
public class UserMemoResponse {
    private String memo;
    
    private Timestamp createTime;
}
