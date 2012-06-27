/**
 * 文件名：MessageResourceAC.java
 * 
 * 创建人：兰硕 - lans@neusoft.com
 * 
 * 创建时间：Aug 11, 2008 10:33:59 PM
 * 
 * 版权所有：东软集团股份有限公司
 */
package cn.ursun.platform.core.action;

import cn.ursun.platform.core.dto.Pagination;

/**
 * <p>翻页使用的Action基类</p>
 *
 * @author 段鹏- duanp@neusoft.com
 * @version 1.0 Created on Sep 1, 2008 11:40:34 AM
 */
public class WeePaginationSupportAction extends WeeAction {

	/**
	 * 记录总数
	 */
	private int totalCount;

	/**
	 * 开始记录
	 */
	private int start;

	/**
	 * 查询条数
	 */
	private int limit;

	/**
	 * 排序字段
	 */
	private String sortColumn = null;

	/**
	 * 排序方式
	 */
	private String sortMode = null;

	/**
	 *
	 * @return 
	 */
	public int getTotalCount() {
		return totalCount;
	}

	/**
	 *
	 * @param totalCount
	 */
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	/**
	 *
	 * @return 
	 */
	public int getStart() {
		return start;
	}

	/**
	 *
	 * @param start
	 */
	public void setStart(int start) {
		this.start = start;
	}

	/**
	 *
	 * @return 
	 */
	public int getLimit() {
		return limit;
	}

	/**
	 *
	 * @param limit
	 */
	public void setLimit(int limit) {
		this.limit = limit;
	}

	/**
	 *
	 * @param pagination
	 */
	protected void setPagination(Pagination pagination) {
		this.limit = pagination.getLimit();
		this.start = pagination.getStart();
		this.totalCount = pagination.getTotalCount();
		this.sortColumn = pagination.getSortColumn();
		this.sortMode = pagination.getSortMode();
	}

	/**
	 *
	 * @param pagination
	 */
	protected Pagination getPagination() {
		Pagination pagination = new Pagination();
		if(this.limit<=0){
			this.limit=Pagination.PAGE_SIZE;
		}
		pagination.setLimit(this.limit);
		pagination.setStart(this.start);
		pagination.setTotalCount(this.totalCount);
		pagination.setSortColumn(this.sortColumn);
		pagination.setSortMode(this.sortMode);
		return pagination;
	}

	public String getSortColumn() {
		return sortColumn;
	}

	public void setSortColumn(String sortColumn) {
		this.sortColumn = sortColumn;
	}

	public String getSortMode() {
		return sortMode;
	}

	public void setSortMode(String sortMode) {
		this.sortMode = sortMode;
	}
}
