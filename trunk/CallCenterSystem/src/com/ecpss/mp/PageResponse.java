/**
 * 
 */
package com.ecpss.mp;

import java.util.List;

/**
 * @author leo.li
 * 
 */
public class PageResponse<T> extends Response {
    private int currentPage;
    private int pageSize;
    private int total;// 数据总笔数
    private int totalPage;// 总共页数
    // private int firstPage;//
    // private int lastPage;//最后一页
    private List<T> rows;
    
    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

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

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> data) {
        this.rows = data;
    }

}
