package cn.ursun.platform.core.tag.grid;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.ComponentTagSupport;

import cn.ursun.platform.core.tag.grid.component.ColumnModelComponent;
import com.opensymphony.xwork2.util.ValueStack;

public class ColumnModelTag extends ComponentTagSupport {

	private String cssClass = null;

	private String header;

	private String key;

	private int width;

	private int height;

	private int border;

	private String bgColor;

	private String foreColor;

	private String hAlign;

	private String vAlign;

	private String imgAsc;

	private String imgDes;

	private boolean sortable;

	private String sortColumn;

	public Component getBean(ValueStack stack, HttpServletRequest req, HttpServletResponse res) {
		return new ColumnModelComponent(stack);
	}

	protected void populateParams() {
		super.populateParams();
		ColumnModelComponent tag = (ColumnModelComponent) getComponent();
		tag.setKey(key);
		tag.setWidth(width);
		tag.setHeight(height);
		tag.setBorder(border);
		tag.setBgColor(bgColor);
		tag.setForeColor(foreColor);
		tag.setCssClass(cssClass);
		tag.setHAlign(hAlign);
		tag.setVAlign(vAlign);
		tag.setHeader(header);
		tag.setSortColumn(sortColumn);
		tag.setSortable(sortable);
		tag.setImgAsc(imgAsc);
		tag.setImgDes(imgDes);
		
	}

	public String getCssClass() {
		return cssClass;
	}

	public void setCssClass(String cssClass) {
		this.cssClass = cssClass;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String title) {
		this.header = title;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
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

	public String getImgAsc() {
		return imgAsc;
	}

	public void setImgAsc(String imgAsc) {
		this.imgAsc = imgAsc;
	}

	public String getImgDes() {
		return imgDes;
	}

	public void setImgDes(String imgDes) {
		this.imgDes = imgDes;
	}

	public boolean isSortable() {
		return sortable;
	}

	public void setSortable(boolean sortable) {
		this.sortable = sortable;
	}

	public String getSortColumn() {
		return sortColumn;
	}

	public void setSortColumn(String sortColumn) {
		this.sortColumn = sortColumn;
	}

}
