package cn.ursun.platform.core.tag.grid;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.ComponentTagSupport;

import cn.ursun.platform.core.tag.grid.component.GridSelectAllComponent;
import com.opensymphony.xwork2.util.ValueStack;

public class GridSelectAllTag extends ComponentTagSupport {

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
	
	private boolean isSingle;

	public Component getBean(ValueStack stack, HttpServletRequest req, HttpServletResponse res) {
		return new GridSelectAllComponent(stack);
	}

	protected void populateParams() {
		super.populateParams();
		GridSelectAllComponent tag = (GridSelectAllComponent) getComponent();
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
		tag.setSingle(isSingle);
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

	
	public boolean isSingle() {
		return isSingle;
	}

	
	public void setIsSingle(boolean isSingle) {
		this.isSingle = isSingle;
	}

	
	
	
	

}
