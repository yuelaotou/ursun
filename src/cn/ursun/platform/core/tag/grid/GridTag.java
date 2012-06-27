/*------------------------------------------------------------------------------
 * PACKAGE: com.freeware.gridtag
 * FILE   : DBGrid.java
 * CREATED: Jul 20, 2004
 * AUTHOR : Prasad P. Khandekar
 *------------------------------------------------------------------------------
 * Change Log:
 *-----------------------------------------------------------------------------*/
package cn.ursun.platform.core.tag.grid;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;

import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.ComponentTagSupport;

import cn.ursun.platform.core.tag.grid.component.GridComponent;
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
public class GridTag extends ComponentTagSupport {

	private int border;

	private int cellPadding;

	private int cellSpacing;

	private int width;

	private String cssClass = null;

	private String bgColor = null;

	private String foreColor = null;

	private String id = null;

	private String name = null;

	private String dataSource = null;

	private String action = null;

	private String pagination;

	private String theme;

	public GridTag() {
		super();
		this.border = 0;
		this.cellPadding = 0;
		this.cellSpacing = 1;
		this.width = 100;
	}

	public Component getBean(ValueStack stack, HttpServletRequest req, HttpServletResponse res) {
		return new GridComponent(stack);
	}

	protected void populateParams() {
		super.populateParams();
		GridComponent tag = (GridComponent) getComponent();
		tag.setBgColor(bgColor);
		tag.setBorder(border);
		tag.setCellPadding(cellPadding);
		tag.setCellSpacing(cellSpacing);
		tag.setCssClass(cssClass);
		tag.setDataSource(dataSource);
		tag.setForeColor(foreColor);
		tag.setId(id);
		tag.setName(name);
		tag.setWidth(width);
		tag.setAction(action);
		tag.setPagination(pagination);
		tag.setTheme(theme);
	}

	public int doEndTag() throws JspException {
		component = null;
		return EVAL_PAGE;
	}

	public int doAfterBody() throws JspException {
		boolean again = component.end(pageContext.getOut(), getBody());

		if (again) {
			return EVAL_BODY_AGAIN;
		} else {
			if (bodyContent != null) {
				try {
					bodyContent.writeOut(bodyContent.getEnclosingWriter());
				} catch (Exception e) {
					throw new JspException(e.getMessage());
				}
			}
			return SKIP_BODY;
		}
	}

	public int getBorder() {
		return border;
	}

	public void setBorder(int border) {
		this.border = border;
	}

	public int getCellPadding() {
		return cellPadding;
	}

	public void setCellPadding(int cellPadding) {
		this.cellPadding = cellPadding;
	}

	public int getCellSpacing() {
		return cellSpacing;
	}

	public void setCellSpacing(int cellSpacing) {
		this.cellSpacing = cellSpacing;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public String getCssClass() {
		return cssClass;
	}

	public void setCssClass(String cssClass) {
		this.cssClass = cssClass;
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDataSource() {
		return dataSource;
	}

	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getPagination() {
		return pagination;
	}

	public void setPagination(String pagination) {
		this.pagination = pagination;
	}

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

}
