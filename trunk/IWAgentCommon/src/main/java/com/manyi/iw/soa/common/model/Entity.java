package com.manyi.iw.soa.common.model;

import com.manyi.iw.soa.common.StringUtils;

import lombok.Data;

@Data
public class Entity {
    
    /**
     * 一页显示数量
     */
    protected Integer pageSize = 10;
    
    /**
     * 第几页
     */
    protected Integer pageIndex = 0;
    
    /**
     * 排序方向
     */
    protected String sortOrder;
    
    /**
     * 排序字段
     */
    protected String sortField;
    /**
     * 总数量
     */
    protected Integer total;
    
    
    public String getAdditionalSql(){
        StringBuilder builder = new StringBuilder();
        if(StringUtils.hasValue(getOrderBySql())){
            builder.append(getOrderBySql());
        }
        
        builder.append(" limit ");
        if(getPageSize() <1){
            setPageSize(10);
        }
        
        if(getPageIndex()<0){
            setPageIndex(0);
        }
        
        if(getPageSize()*getPageIndex()>getTotal()){
            if(getTotal()%getPageSize() !=0){
                setPageIndex(getTotal()/getPageSize());
            }else{
                setPageIndex(getTotal()/getPageSize()-1);
            }
        }
        
        builder.append((getPageIndex())*getPageSize()).append(",").append(getPageSize());
        return builder.toString();
    }
    
    public String getOrderBySql(){
        if(StringUtils.hasValue(getSortField())){
            StringBuilder builder = new StringBuilder(" order by ");
            builder.append(getSortField());
            builder.append(" ");
            if("ASC".equalsIgnoreCase(getSortOrder())){
                builder.append(" ASC ");
            }else{
                builder.append(" DESC ");
            }
            return builder.toString();
        }
       return null;
    }
}
