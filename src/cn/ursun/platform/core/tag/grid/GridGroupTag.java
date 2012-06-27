/*------------------------------------------------------------------------------
 * PACKAGE: com.freeware.gridtag
 * FILE   : GridSorter.java
 * CREATED: Jul 27, 2004
 * AUTHOR : Prasad P. Khandekar
 *------------------------------------------------------------------------------
 * Change Log:
 *-----------------------------------------------------------------------------*/
package cn.ursun.platform.core.tag.grid;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.ComponentTagSupport;

import cn.ursun.platform.core.tag.grid.component.GridGroupComponent;
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
public final class GridGroupTag extends ComponentTagSupport {

	private String dataField = null;

	private String lable = null;

	private String expandImg = null;

	private String closeImg = null;

	private String cssClass = null;

	private String sortMode = null;

	public Component getBean(ValueStack stack, HttpServletRequest req, HttpServletResponse res) {
		return new GridGroupComponent(stack);
	}

	protected void populateParams() {
		super.populateParams();
		GridGroupComponent tag = (GridGroupComponent) getComponent();
		tag.setCssClass(cssClass);
		tag.setDataField(dataField);
		tag.setLable(lable);
		tag.setSortMode(sortMode);
	}

	public String getDataField() {
		return dataField;
	}

	public void setDataField(String dataField) {
		this.dataField = dataField;
	}

	public String getLable() {
		return lable;
	}

	public void setLable(String lable) {
		this.lable = lable;
	}

	public String getExpandImg() {
		return expandImg;
	}

	public void setExpandImg(String expandImg) {
		this.expandImg = expandImg;
	}

	public String getCloseImg() {
		return closeImg;
	}

	public void setCloseImg(String closeImg) {
		this.closeImg = closeImg;
	}

	public String getCssClass() {
		return cssClass;
	}

	public void setCssClass(String cssClass) {
		this.cssClass = cssClass;
	}

	public String getSortMode() {
		return sortMode;
	}

	public void setSortMode(String sortMode) {
		this.sortMode = sortMode;
	}

}
