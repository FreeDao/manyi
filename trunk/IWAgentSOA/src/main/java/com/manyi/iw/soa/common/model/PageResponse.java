package com.manyi.iw.soa.common.model;

import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import lombok.Data;

@Data
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class PageResponse<T> extends Response {
    private int total;// 数据总笔数
    private List<T> data;
    public PageResponse(int total, List<T> data) {
        super();
        this.total = total;
        this.data = data;
    }
    public PageResponse() {
    }
   
    
    
    
}
