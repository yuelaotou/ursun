package cn.ursun.platform.ps.tag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.ui.AbstractUITag;

import com.opensymphony.xwork2.util.ValueStack;

/**
 * <p> Title: 按钮标签</p>
 * <p> Description: [描述]</p>
 * <p> Created on Mar 10, 2010</p>
 * <p> Copyright: Copyright (c) 2008</p>
 * <p> Company: 东软软件股份有限公司</p>
 * @author 宋成山 - songchengshan@neusoft.com
 * @version 1.0
 */
public class ButtonTag extends AbstractUITag {

	private String buttonClass;

	@Override
	public Component getBean(ValueStack stack, HttpServletRequest request, HttpServletResponse response) {

		return new Button(stack, request, response);
	}

	protected void populateParams() {
		super.populateParams();
		Button button = (Button) component;
		button.setButtonClass(buttonClass);
	}

	public String getButtonClass() {
		return buttonClass;
	}

	public void setButtonClass(String buttonClass) {
		this.buttonClass = buttonClass;
	}

}
