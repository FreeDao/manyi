package com.manyi.iw.agent.console.entity;

import lombok.Data;

import com.manyi.iw.agent.console.util.StringUtils;

@Data
public class Entity {
    
    /**
     * 一页显示数量
     */
    protected Integer pageSize;
    
    /**
     * 第几页
     */
    protected Integer pageIndex;
    
    /**
     * 排序方向
     */
    protected String sortOrder;
    
    /**
     * 排序字段
     */
    protected String sortField;

}
