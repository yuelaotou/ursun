/**
 * 文件名：Pagination.java
 * 
 * 创建人：段鹏 - duanp@neusoft.com
 * 
 * 创建时间：Aug 20, 2008 2:04:41 PM
 * 
 * 版权所有：东软集团股份有限公司
 */
package cn.ursun.platform.core.dto;

import java.io.Serializable;

/**
 * <p>[描述信息：说明类的基本功能]</p>
 *
 * @author 段鹏- duanp@neusoft.com
 * @version 1.0 Created on Aug 20, 2008 2:04:41 PM
 */
public class Pagination implements Serializable {

	private int start;

	private int limit = Pagination.PAGE_SIZE;

	private int totalCount;

	private String sortColumn = null;

	private String sortMode = null;

	private String groupField = null;

	private String groupSort = null;

	public final static int PAGE_SIZE = 10;

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

	public String getGroupField() {
		return groupField;
	}

	public void setGroupField(String groupField) {
		this.groupField = groupField;
	}

	public String getGroupSort() {
		return groupSort;
	}

	public void setGroupSort(String groupSort) {
		this.groupSort = groupSort;
	}

}
