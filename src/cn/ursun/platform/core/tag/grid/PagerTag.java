package cn.ursun.platform.core.tag.grid;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;

import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.ComponentTagSupport;

import cn.ursun.platform.core.tag.grid.component.PagerComponent;
import com.opensymphony.xwork2.util.ValueStack;

/**
 * <p> Title: [名称]</p>
 * <p> Description: [描述]</p>
 * <p> Created on 2009-9-11</p>
 * <p> Copyright: Copyright (c) 2008</p>
 * <p> Company: 东软软件股份有限公司</p>
 * @author 宋成山 songchengshan@neusoft.com
 * @version 1.0
 */
public final class PagerTag extends ComponentTagSupport {

	private String imgFirst = null;

	private String imgPrevious = null;

	private String imgNext = null;

	private String imgLast = null;

	private String disableImgFirst = null;

	private String disableImgPrevious = null;

	private String disableImgNext = null;

	private String disableImgLast = null;

	/**
	 * 分页样式
	 */
	private String theme;

	public Component getBean(ValueStack stack, HttpServletRequest req, HttpServletResponse res) {
		return new PagerComponent(stack);
	}

	protected void populateParams() {
		super.populateParams();
		PagerComponent tag = (PagerComponent) getComponent();
		tag.setImgFirst(this.imgFirst);
		tag.setImgPrevious(this.imgPrevious);
		tag.setImgNext(this.imgNext);
		tag.setImgLast(this.imgLast);
		tag.setDisableImgFirst(this.disableImgFirst);
		tag.setDisableImgLast(this.disableImgLast);
		tag.setDisableImgNext(this.disableImgNext);
		tag.setDisableImgPrevious(this.disableImgPrevious);
		tag.setTheme(theme);
	}

	public int doStartTag() throws JspException {
		if (!(this.getParent() instanceof GridFooterTag))
			throw new JspException("Error: Column tag needs to be a child of GridFooterTag!");
		return super.doStartTag();
	}

	public int doEndTag() throws JspException {
		return super.doEndTag();
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

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

}
