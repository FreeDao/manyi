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
    private int currentPage=1;
    private int pageSize=10;
    private int totalSize=0;// 数据总笔数
    private int totalPage=0;// 总共页数
    // private int firstPage;//
    // private int lastPage;//最后一页
    private List<T> data;
    
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

    public int getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

}
