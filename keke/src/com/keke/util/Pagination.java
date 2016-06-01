package com.keke.util;


public class Pagination
{
	
	
	/** 当前页码 */
	private int currentPage;
	/** 总数 */
	private int totalCount;
	/** 每页的数量 */
	private int pageCount;
	
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public int getPageCount() {
		return pageCount;
	}
	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
	
	@Override
	public String toString() {
		return JsonUtil.toJsonString(this);
	}
}
