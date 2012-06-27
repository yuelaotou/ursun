package cn.ursun.platform.core.tag.grid;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;

import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.ComponentTagSupport;

import cn.ursun.platform.core.tag.grid.component.ColumnComponentSupport;
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
public abstract class ColumnTagSupport extends ComponentTagSupport {

	protected int width;

	protected int height;

	protected int border;

	protected String name;

	protected String bgColor;

	protected String foreColor;

	protected String cssClass;

	protected String hAlign;

	protected String vAlign;

	protected String headerText;

	protected String dataField;

	protected boolean sortable;

	protected String key;

	public ColumnTagSupport() {
		super();
		this.width = -1;
		this.height = -1;
		this.border = -1;
	}

	public abstract Component getBean(ValueStack valuestack, HttpServletRequest httpservletrequest,
			HttpServletResponse httpservletresponse);

	protected void populateParams() {
		super.populateParams();
		ColumnComponentSupport tag = (ColumnComponentSupport) getComponent();
		tag.setWidth(width);
		tag.setHeight(height);
		tag.setBorder(border);
		tag.setName(name);
		tag.setBgColor(bgColor);
		tag.setForeColor(foreColor);
		tag.setCssClass(cssClass);
		tag.setHAlign(hAlign);
		tag.setVAlign(vAlign);
		tag.setDataField(dataField);
	}

	public int doStartTag() throws JspException {
		if (!(this.getParent() instanceof GridTbodyTag))
			throw new JspException("Error: Column tag needs to be a child of GridTbodyTag!");
		return super.doStartTag();
	}

	public int doEndTag() throws JspException {

		return super.doEndTag();
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getBorder() {
		return border;
	}

	public void setBorder(int border) {
		this.border = border;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBgColor() {
		return bgColor;
	}

	public void setBgColor(String bgColor) {
		this.bgColor = bgColor;
	}

	public String getForeColor() {
		return foreColor;
	}

	public void setForeColor(String foreColor) {
		this.foreColor = foreColor;
	}

	public String getCssClass() {
		return cssClass;
	}

	public void setCssClass(String cssClass) {
		this.cssClass = cssClass;
	}

	public String getHAlign() {
		return hAlign;
	}

	public void setHAlign(String align) {
		hAlign = align;
	}

	public String getVAlign() {
		return vAlign;
	}

	public void setVAlign(String align) {
		vAlign = align;
	}

	public String getHeaderText() {
		return headerText;
	}

	public void setHeaderText(String headerText) {
		this.headerText = headerText;
	}

	public String getDataField() {
		return dataField;
	}

	public void setDataField(String dataField) {
		this.dataField = dataField;
	}

	public boolean isSortable() {
		return sortable;
	}

	public void setSortable(boolean sortable) {
		this.sortable = sortable;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

}