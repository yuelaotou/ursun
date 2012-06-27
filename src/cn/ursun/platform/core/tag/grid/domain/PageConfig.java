package cn.ursun.platform.core.tag.grid.domain;

public class PageConfig {

	private String imgFirst = null;

	private String imgPrevious = null;

	private String imgNext = null;

	private String imgLast = null;

	private String disableImgFirst = null;

	private String disableImgPrevious = null;

	private String disableImgNext = null;

	private String disableImgLast = null;

	private String pageSize;

	private String start;

	private String totalRecords;

	/**
	 * 分页样式
	 */
	private String theme;

	public static enum PAGE_THEME {
		TEXT, NUMBER;

		public boolean equals(String theme) {
			return this.toString().equals(theme);
		}

		public String toString() {
			if (this == TEXT)
				return "text";
			else
				return "number";

		}
	}

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	public String getImgFirst() {
		return imgFirst;
	}

	public void setImgFirst(String imgFirst) {
		this.imgFirst = imgFirst;
	}

	public String getImgPrevious() {
		return imgPrevious;
	}

	public void setImgPrevious(String imgPrevious) {
		this.imgPrevious = imgPrevious;
	}

	public String getImgNext() {
		return imgNext;
	}

	public void setImgNext(String imgNext) {
		this.imgNext = imgNext;
	}

	public String getImgLast() {
		return imgLast;
	}

	public void setImgLast(String imgLast) {
		this.imgLast = imgLast;
	}

	public String getDisableImgFirst() {
		return disableImgFirst;
	}

	public void setDisableImgFirst(String disableImgFirst) {
		this.disableImgFirst = disableImgFirst;
	}

	public String getDisableImgPrevious() {
		return disableImgPrevious;
	}

	public void setDisableImgPrevious(String disableImgPrevious) {
		this.disableImgPrevious = disableImgPrevious;
	}

	public String getDisableImgNext() {
		return disableImgNext;
	}

	public void setDisableImgNext(String disableImgNext) {
		this.disableImgNext = disableImgNext;
	}

	public String getDisableImgLast() {
		return disableImgLast;
	}

	public void setDisableImgLast(String disableImgLast) {
		this.disableImgLast = disableImgLast;
	}

	public String getPageSize() {
		return pageSize;
	}

	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(String totalRecords) {
		this.totalRecords = totalRecords;
	}

}
