package cn.ursun.platform.ps.tag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.UIBean;
import org.apache.struts2.views.annotations.StrutsTag;
import org.apache.struts2.views.annotations.StrutsTagAttribute;

import com.opensymphony.xwork2.util.ValueStack;

/**
 * <p> Title:按钮组件</p>
 * <p> Description: [描述]</p>
 * <p> Created on Mar 10, 2010</p>
 * <p> Copyright: Copyright (c) 2008</p>
 * <p> Company: 东软软件股份有限公司</p>
 * @author 宋成山 - songchengshan@neusoft.com
 * @version 1.0
 */
@StrutsTag(name = "button", tldTagClass = "cn.ursun.platform.ps.tag.Button", description = "button tag")
public class Button extends UIBean {

	final public static String TEMPLATE = "button";

	private String buttonClass;

	public Button(ValueStack stack, HttpServletRequest request, HttpServletResponse response) {
		super(stack, request, response);
	}

	/**
	 * 用于返回模板的名字，Struts2会自动在后面加入.ftl扩展名以找到特定的模板文件。
	 */
	@Override
	protected String getDefaultTemplate() {
		super.setDefaultTemplateDir("cn/ursun/platform/ps/tag/template");
		return TEMPLATE;
	}

	public String getButtonClass() {
		return buttonClass;
	}

	@StrutsTagAttribute(description = "css style of button", type = "String")
	public void setButtonClass(String buttonClass) {
		this.buttonClass = buttonClass;
	}

	/**
	 * 重写evaluateExtraParams（）方法，添加button标签自定义属性
	 * 
	 */
	@Override
	protected void evaluateExtraParams() {
		super.evaluateExtraParams();
		addParameter("href", "javascript:void(0)");
		if (buttonClass != null) {
			addParameter("buttonClass", findString(buttonClass));
		}

	}

}
