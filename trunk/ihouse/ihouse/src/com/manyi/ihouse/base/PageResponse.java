/**
 * 
 */
package com.manyi.ihouse.base;

import java.util.List;

/**
 * @author leo.li
 * 
 */
public class PageResponse<T> extends Response {
//    private int currentPage; //当前页码
    private int pageSize; //每页数据量
    private int total;// 数据总笔数
//    private int totalPage;// 总共页数
    // private int firstPage;//
    // private int lastPage;//最后一页
    private List<T> rows;
    
    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int totalSize) {
        this.total = totalSize;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> data) {
        this.rows = data;
    }

}
