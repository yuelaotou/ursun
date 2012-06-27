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

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;

import org.apache.commons.lang.StringUtils;

import com.opensymphony.xwork2.ActionContext;

/**
 * <p> Title: 导入jQuery</p>
 * <p> Description: [描述]</p>
 * <p> Created on 2009-11-16</p>
 * <p> Copyright: Copyright (c) 2008</p>
 * <p> Company: 东软软件股份有限公司</p>
 * @author 宋成山 songchengshan@neusoft.com
 * @version 1.0
 */
public class JqueryTag extends BaseTagSupport {

	private String components = null;

	private Map<String, String[]> componentLinkMap = null;

	private Locale locale = null;

	public JqueryTag() {
		componentLinkMap = new HashMap<String, String[]>();
		componentLinkMap.put("accordion", new String[] {
				new StringBuffer().append("<script  src=\"{0}/platform/common/jquery/ui/ui.accordion.js\"></script>\n").toString(),
				"" });
		componentLinkMap
				.put("tab", new String[] {
						new StringBuffer().append("<script  src=\"{0}/platform/common/jquery/ui/ui.tabs.js\"></script>\n")
								.toString(), "" });
		componentLinkMap.put("dialog", new String[] {
				new StringBuffer(
						"<script  src=\"{0}/platform/common/jquery/ui/ui.dialog.js\"></script>\n").toString(),
				"resizable,effects,bgiframe" });
		componentLinkMap
				.put(
						"tree",
						new String[] {
								new StringBuffer()
										.append(
												"<script  src=\"{0}/platform/common/jquery/external/cookie/jquery.cookie.min.js\"></script>\n")
										.append(
												"<link href=\"{0}/platform/common/jquery/plugin/dynatree/skin/ui.dynatree.css\" rel=\"stylesheet\" type=\"text/css\">\n")
										.append(
												"<script  src=\"{0}/platform/common/jquery/plugin/dynatree/jquery.dynatree.min.js\"></script>\n")
										.toString(), "menu" });
		componentLinkMap
				.put(
						"menu",
						new String[] {
								new StringBuffer()
										.append(
												"<script  src=\"{0}/platform/common/jquery/plugin/contextmenu/jquery.contextMenu.min.js\"></script>\n")
										.append(
												"<link href=\"{0}/platform/common/jquery/plugin/contextmenu/jquery.contextMenu.css\" rel=\"stylesheet\" type=\"text/css\">\n")
										.toString(), "" });
		componentLinkMap.put("calendar",
				new String[] {
						new StringBuffer().append("<script  src=\"{0}/platform/common/jquery/ui/ui.datepicker.min.js\"></script>\n")
								.toString(), "" });
		componentLinkMap
				.put(
						"form",
						new String[] {
								new StringBuffer()
										.append("<script  src=\"{0}/platform/common/js/validation.min.js\"></script>\n")
										.append(
												"<link href=\"{0}/platform/common/css/validation.css\" rel=\"stylesheet\" type=\"text/css\">\n")
										.toString(), "" });
		componentLinkMap.put("progressbar", new String[] {
				new StringBuffer().append("<script  src=\"{0}/platform/common/jquery/ui/ui.progressbar.js\"></script>\n")
						.toString(), "" });
		componentLinkMap.put("slider",
				new String[] {
						new StringBuffer().append("<script  src=\"{0}/platform/common/jquery/ui/ui.slider.js\"></script>\n")
								.toString(), "" });
		componentLinkMap.put("xheditor", new String[] { "", "" });
		componentLinkMap
				.put(
						"autocomplete",
						new String[] {
								new StringBuffer()
										.append(
												"<script  src=\"{0}/platform/common/jquery/plugin/autocomplete/jquery.autocomplete.js\"></script>\n")
										.append(
												"<link rel=\"stylesheet\"  href=\"{0}/platform/common/jquery/plugin/autocomplete/jquery.autocomplete.css\" type=\"text/css\"></link>\n")
										.toString(), "bgiframe,thickbox,ajaxQueue" });

		componentLinkMap.put("bgiframe", new String[] {
				new StringBuffer().append(
						"<script  src=\"{0}/platform/common/jquery/external/bgiframe/jquery.bgiframe.js\"></script>\n")
						.toString(), "" });

		componentLinkMap.put("effects", new String[] {
				new StringBuffer().append("<script  src=\"{0}/platform/common/jquery/ui/effects.core.js\"></script>\n").append(
						"<script  src=\"{0}/platform/common/jquery/ui/effects.highlight.js\"></script>\n").toString(), "" });

		componentLinkMap.put("resizable", new String[] {
				new StringBuffer().append("<script  src=\"{0}/platform/common/jquery/ui/ui.resizable.js\"></script>\n").toString(),
				"" });

		componentLinkMap
				.put(
						"thickbox",
						new String[] {
								new StringBuffer()
										.append(
												"<script  src=\"{0}/platform/common/jquery/external/thickbox/thickbox-compressed.js\"></script>\n")
										.append(
												"<link rel=\"stylesheet\" href=\"{0}/platform/common/jquery/external/thickbox/thickbox.css\" type=\"text/css\"></link>\n")
										.toString(), "" });
		componentLinkMap.put("ajaxQueue", new String[] {
				new StringBuffer().append(
						"<script  src=\"{0}/platform/common/jquery/external/ajaxqueue/jquery.ajaxQueue.js\"></script>\n")
						.toString(), "" });

		componentLinkMap.put("blockUI", new String[] {
				new StringBuffer().append("<script  src=\"{0}/platform/common/js/jquery.blockUI.min.js\"></script>\n")
						.toString(), "" });
		locale = ActionContext.getContext().getLocale();
	}

	public int doStartTag() throws JspException {
		if (components == null || "".equals(components)) {
			throw new JspException("components 不能为空");
		}
		String[] componentIDs = components.split(",");
		StringBuffer sb = new StringBuffer();
		Map<String, String> completeCmp = new HashMap<String, String>();
		Locale locale1 = (Locale) getHttpServletRequest().getSession().getAttribute("WW_TRANS_I18N_LOCALE");
		if (locale1 != null) {
			locale = locale1;
		}
		loadComponent(componentIDs, sb, completeCmp);
		this.write(sb.toString());
		return Tag.EVAL_PAGE;
	}

	private void loadComponent(String[] componentIDs, StringBuffer sb, Map<String, String> completeCmp)
			throws JspException {

		for (String componentID : componentIDs) {
			if (completeCmp.containsKey(componentID)) {
				return;
			}
			if (!componentLinkMap.containsKey(componentID)) {
				throw new JspException("组件" + componentID + "不存在！");
			}
			if (componentLinkMap.get(componentID).length > 1) {
				if (StringUtils.isNotEmpty(componentLinkMap.get(componentID)[1])) {
					String[] dependComponent = componentLinkMap.get(componentID)[1].split(",");
					this.loadComponent(dependComponent, sb, completeCmp);
				}
			}
			completeCmp.put(componentID, "");

			// 添加国际化资源
			if ("calendar".equals(componentID)) {
				if (locale != null && !locale.toString().equals("en")) {
					sb.append("<script  src=\"" + getContextPath() + "/platform/common/jquery/ui/i118n/ui.datepicker_"
							+ locale.toString() + ".js\"></script>\n");
				}
			}
			if ("form".equals(componentID)) {
				if (locale != null && !locale.toString().equals("en")) {
					sb.append("<script  src=\"" + getContextPath()
							+ "/platform/common/jquery/plugin/formvalidator/i18n/jquery.validationEngine_" + locale.toString()
							+ ".js\"></script>\n");
				}
			}
			if ("xheditor".equals(componentID)) {
				if (locale != null && !locale.toString().equals("en")) {
					sb.append("<script  src=\"" + getContextPath() + "/platform/common/jquery/plugin/xheditor/xheditor_"
							+ locale.toString() + ".js\"></script>\n");
				} else {
					sb.append("<script  src=\"" + getContextPath()
							+ "/platform/common/jquery/plugin/xheditor/xheditor.js\"></script>\n");
				}
			}
			sb.append(MessageFormat.format(componentLinkMap.get(componentID)[0], getContextPath()));
		}
	}

	public String getComponents() {
		return components;
	}

	public void setComponents(String components) {
		this.components = components;
	}
}
