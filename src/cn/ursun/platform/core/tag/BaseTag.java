/**
 * 文件名：ImportTag.java
 * 
 * 创建人：兰硕 - lans@neusoft.com
 * 
 * 创建时间：Aug 20, 2008 3:45:37 PM
 * 
 * 版权所有：东软集团股份有限公司
 */
package cn.ursun.platform.core.tag;

import java.util.Locale;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;

import com.opensymphony.xwork2.ActionContext;

/**
 * <p>
 * 导入基本信息
 * </p>
 * 
 * @author 兰硕 - lans@neusoft.com
 * @version 1.0 Created on Aug 20, 2008 3:45:37 PM
 */
public class BaseTag extends BaseTagSupport {

	private Locale locale = null;

	public BaseTag() {
		locale = ActionContext.getContext().getLocale();
	}

	public int doStartTag() throws JspException {
		StringBuffer sb = new StringBuffer();
		// 加载国际化资源
		Locale locale1 = (Locale) getHttpServletRequest().getSession().getAttribute("WW_TRANS_I18N_LOCALE");
		if (locale1 != null) {
			locale = locale1;
		}
		if (locale != null && !locale.toString().equals("en")) {
			sb.append("<script type=\"text/javascript\" src=\"" + getContextPath()
					+ "/platform/common/js/local/resource_" + locale.toString() + ".js\"></script>\n");
		} else {
			sb.append("<script type=\"text/javascript\" src=\"" + getContextPath()
					+ "/platform/common/js/local/resource.js\"></script>\n");
		}
		// 加载Jquery
		sb.append("\t\t<script type=\"text/javascript\" src=\"" + getContextPath()
				+ "/platform/common/jquery/jquery-1.3.2.min.js\"></script>\n");

		// 加载公共组件
		sb.append("\t\t<link rel=\"stylesheet\" type=\"text/css\" href=\"" + getContextPath()
				+ "/platform/common/css/base.css\"/>\n");
		sb.append("\t\t<script type=\"text/javascript\" src=\"" + getContextPath()
				+ "/platform/common/js/common.min.js\"></script>\n");
		// 加载Grid组件
		sb.append("\t\t<link rel=\"stylesheet\" type=\"text/css\" href=\"" + getContextPath()
				+ "/platform/common/css/grid_style.css\"/>\n");
		// 加载JqueryUI组件
		sb.append(
				"\t\t<link rel=\"stylesheet\" type=\"text/css\" href=\"" + getContextPath()
						+ "/platform/common/jquery/themes/cupertino/jquery-ui-1.7.2.custom.css\">\n").append(
				"\t\t<script type=\"text/javascript\" src=\"" + getContextPath()
						+ "/platform/common/jquery/ui/ui.core.min.js\"></script>\n");
		// 定义JS上下文变量
		sb.append("\t\t<script type=\"text/javascript\"> var contextPath=\"" + getContextPath() + "\";</script>\n");
		write(sb.toString());
		return Tag.EVAL_PAGE;
	}
}
