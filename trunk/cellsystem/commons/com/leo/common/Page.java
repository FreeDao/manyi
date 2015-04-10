package com.leo.common;

import java.io.Serializable;
import java.util.List;

public class Page<T> implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final int MIN_PAGE_SIZE = 5;
	public static final int MAX_PAGE_SIZE = 50;

	private int firstPage = 1;

	private int totalSize;//数据总笔数

	private int pageSize;//每页数据数

	private int totalPage;//总共页数

	private int currentPage;//当前页
	private int prePage;//上一页

	private int nextPage;//下一页

	private int lastPage;//最后一页
	
	private int firstResultIndex;//当前页的第一笔交易在所有交易中的索引值
	
	private int maxResultLenght;
	
	private List<T> data;
	
	
	/**
	 * @return the result
	 */
	public List<T> getData() {
		return data;
	}

	/**
	 * @param result the result to set
	 */
	public void setData(List<T> data) {
		this.data = data;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
		updateData();
	}
	
	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
		updateData();
	}

	public int getTotalSize() {
		return totalSize;
	}

	public void setTotalSize(int totalSize) {
		this.totalSize = totalSize;
		updateData();
	}
	
	public Page(int currentPage, int pageSize, int totalSize) {
		this.currentPage = currentPage;
		this.totalSize = totalSize;
		this.pageSize = pageSize;
		updateData();
	}
	
	public Page(int currentPage, int pageSize) {
		this.currentPage = currentPage;
		this.pageSize = pageSize;
		updateData();
	}
	
	public Page(int currentPage) {
		this.currentPage = currentPage;
		this.pageSize = MIN_PAGE_SIZE;
		updateData();
	}
	
	public Page() {
		currentPage = 1;
		pageSize = MIN_PAGE_SIZE;
		updateData();
	}
	
	protected void updateData() {
		currentPage = currentPage <= 0 ? 1 : currentPage;
		totalSize = totalSize<0?0:totalSize;
		

		pageSize = pageSize < MIN_PAGE_SIZE ? MIN_PAGE_SIZE : (pageSize>MAX_PAGE_SIZE?MAX_PAGE_SIZE:pageSize);
		
		
		//setTotalPage();
		if(totalSize==0)
		    totalPage = 0;
		else
		    totalPage = totalSize % pageSize == 0 ? totalSize / pageSize : totalSize / pageSize + 1;
		
		//setFirstPage();
		firstPage = totalPage>0?1:0;
		
		//setLastPage();
		lastPage = totalPage;
		
		//setPrePage();
		if (currentPage > 1)
			prePage = currentPage - 1;
		else
			prePage = 0;

		//setNextPage();
		if (currentPage >= totalPage)
			nextPage = totalPage;
		else
			nextPage = currentPage + 1;
		
		//setFirstResult
		firstResultIndex = prePage * pageSize;
		
		int mid = currentPage*pageSize-totalSize;
		
		maxResultLenght = mid >0?pageSize-mid:pageSize;
		
	}


	
	public int getNextPage() {
		return this.nextPage;
	}

	public int getPrePage() {
		return this.prePage;
	}
	
	public int getLastPage() {
		return this.lastPage;
	}
	
	public int getFirstPage() {
		return firstPage;
	}

	public int getTotalPage() {
		return this.totalPage;
	}
	
	public int getFirstResultIndex(){
		return firstResultIndex;
	}
	
	public int getMaxResultLenght(){
		return maxResultLenght;
	}
	
	public static void main(String[] args) {
		//Page<Object> p = new Page<Object>(2, 4, 30);
		//System.out.println("--last page-->" + p.getLastPage() + "      total page->" + p.getTotalPage()
		//		+ "    first result-->" + p.getFirstResult());

	}
}
