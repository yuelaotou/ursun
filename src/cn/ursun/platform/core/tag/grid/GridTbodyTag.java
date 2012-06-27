package cn.ursun.platform.core.tag.grid;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.ComponentTagSupport;

import cn.ursun.platform.core.tag.grid.component.GridTbodyComponent;
import com.opensymphony.xwork2.util.ValueStack;

public class GridTbodyTag extends ComponentTagSupport {

	private String cssClass = null;

	private String onClick = null;

	public Component getBean(ValueStack stack, HttpServletRequest req, HttpServletResponse res) {
		return new GridTbodyComponent(stack);
	}

	protected void populateParams() {
		super.populateParams();
		GridTbodyComponent tag = (GridTbodyComponent) getComponent();
		tag.setCssClass(cssClass);
		tag.setOnClick(onClick);
	}

	public String getCssClass() {
		return cssClass;
	}

	public void setCssClass(String cssClass) {
		this.cssClass = cssClass;
	}

	public String getOnClick() {
		return onClick;
	}

	public void setOnClick(String onClick) {
		this.onClick = onClick;
	}

}
