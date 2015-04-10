package com.manyi.iw.agent.console.base.model;

import java.util.List;

import lombok.Data;

@Data
public class PageResponse<T> extends Response {
    private int total;// 数据总笔数
    private List<T> data;
}
