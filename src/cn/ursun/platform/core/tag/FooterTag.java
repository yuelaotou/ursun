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

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;

/**
 * <p>
 * 导入基本信息
 * </p>
 * 
 * @author 兰硕 - lans@neusoft.com
 * @version 1.0 Created on Aug 20, 2008 3:45:37 PM
 */
public class FooterTag extends BaseTagSupport {

	public FooterTag() {
	}

	public int doStartTag() throws JspException {
		StringBuffer sb = new StringBuffer();

		sb.append("\t\t<script type=\"text/javascript\" src=\"" + getContextPath()
				+ "/platform/common/js/weeGrid.min.js\"></script>\n");

		// 加载表单JS
		sb.append("\t\t<script type=\"text/javascript\" src=\"" + getContextPath()
				+ "/platform/common/jquery/plugin/form/jquery.form.js\"></script>\n");

		// 加载弹出窗口组件
		sb.append("\t\t<link rel=\"stylesheet\" href=\"" + getContextPath()
				+ "/platform/common/css/window.css\" type=\"text/css\"></link>\n");
		sb.append("\t\t<script type=\"text/javascript\" src=\"" + getContextPath()
				+ "/platform/common/jquery/ui/ui.draggable.min.js\"></script>\n");
		sb.append("\t\t<script type=\"text/javascript\" src=\"" + getContextPath()
				+ "/platform/common/jquery/plugin/block/jquery.blockUI.min.js\"></script>\n");
		sb.append("\t\t<script type=\"text/javascript\" src=\"" + getContextPath()
				+ "/platform/common/js/window.min.js\"></script>\n");

		// 定义JS上下文变量
		sb.append("\t\t<script type=\"text/javascript\"> var contextPath=\"" + getContextPath() + "\";</script>\n");
		write(sb.toString());

		return Tag.EVAL_PAGE;
	}
}
