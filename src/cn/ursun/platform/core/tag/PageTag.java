package cn.ursun.platform.core.tag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.ComponentTagSupport;

import com.opensymphony.xwork2.util.ValueStack;

/**
 * <p> Title:分页标签  </p>
 * <p> Description: [描述]</p>
 * <p> Created on 2009-9-8</p>
 * <p> Copyright: Copyright (c) 2008</p>
 * <p> Company: 东软软件股份有限公司</p>
 * @author 宋成山 songchengshan@neusoft.com
 * @version 1.0
 */
public class PageTag extends ComponentTagSupport {

	private String styleClass; // 新增的样式属性

	private String theme; // 新增的分页样式属性

	private String id;

	private String action;

	private String queryParamName;

	private boolean ajax;

	private String target;

	/**
	 * 对应的Action的pagination对象
	 */
	private String pagination;

	/**
	 * 是否显示总页数标签
	 */
	private boolean showTotalRecord = false;

	public boolean getShowTotalRecord() {
		return showTotalRecord;
	}

	public void setShowTotalRecord(boolean showTotalRecord) {
		this.showTotalRecord = showTotalRecord;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	public void setStyleClass(String styleClass) {
		this.styleClass = styleClass;
	}

	@Override
	public Component getBean(ValueStack valueStack, HttpServletRequest req, HttpServletResponse res) {
		return new Pages(valueStack);
	}

	protected void populateParams() {
		super.populateParams();
		Pages pages = (Pages) component;
		;
		pages.setStyleClass(styleClass);
		pages.setTheme(theme);
		pages.setPagination(pagination);
		pages.setShowTotalRecord(showTotalRecord);
		pages.setAction(this.action);
		pages.setId(this.id);
		pages.setQueryParamName(this.queryParamName);
		pages.setAjax(ajax);
		pages.setTarget(target);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getQueryParamName() {
		return queryParamName;
	}

	public void setQueryParamName(String queryParamName) {
		this.queryParamName = queryParamName;
	}

	public boolean getAjax() {
		return ajax;
	}

	public void setAjax(boolean ajax) {
		this.ajax = ajax;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getPagination() {
		return pagination;
	}

	public void setPagination(String pagination) {
		this.pagination = pagination;
	}

}
