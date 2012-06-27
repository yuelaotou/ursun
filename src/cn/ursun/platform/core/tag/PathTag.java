package cn.ursun.platform.core.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;

/**
 * <p>路径标签</p>
 *
 * @author 兰硕 - lans@neusoft.com
 * @version 1.0 Created on Aug 20, 2008 2:48:49 PM
 */
public class PathTag extends BaseTagSupport {

	private String relative = null;

	private String path = null;

	public void setRelative(String relative) {
		this.relative = relative;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public int doStartTag() throws JspException {
		StringBuffer sb = new StringBuffer();
		String tempPath = null;

		if (relative == null || "".equals(relative)) {
			tempPath = getContextPath();
		} else {
			// specified path relative
			tempPath = relative;
		}
		sb.append(tempPath);
		if (path != null && !path.startsWith("/"))
			sb.append("/");
		sb.append(path);
		write(sb.toString());
		return Tag.EVAL_PAGE;
	}
}
