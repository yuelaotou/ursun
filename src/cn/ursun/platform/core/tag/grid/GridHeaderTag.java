package cn.ursun.platform.core.tag.grid;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.ComponentTagSupport;

import cn.ursun.platform.core.tag.grid.component.GridHeaderComponent;
import com.opensymphony.xwork2.util.ValueStack;

public class GridHeaderTag extends ComponentTagSupport {

	private String cssClass = null;

	public Component getBean(ValueStack stack, HttpServletRequest req, HttpServletResponse res) {
		return new GridHeaderComponent(stack);
	}

	protected void populateParams() {
		super.populateParams();
		GridHeaderComponent tag = (GridHeaderComponent) getComponent();
		tag.setCssClass(cssClass);
	}

	public String getCssClass() {
		return cssClass;
	}

	public void setCssClass(String cssClass) {
		this.cssClass = cssClass;
	}

}
