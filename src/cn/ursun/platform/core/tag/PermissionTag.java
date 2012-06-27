package cn.ursun.platform.core.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;

import cn.ursun.platform.core.exception.BizException;
import cn.ursun.platform.ps.security.WeeSecurityInfo;

/**
 * <p>路径标签</p>
 *
 * @author 兰硕 - lans@neusoft.com
 * @version 1.0 Created on Aug 20, 2008 2:48:49 PM
 */
public class PermissionTag extends BaseTagSupport {

	private String resource = null;

	public int doStartTag() throws JspException {
		try {
			if (WeeSecurityInfo.getInstance().hasPermission(resource))
				return Tag.EVAL_PAGE;
			else
				return Tag.SKIP_BODY;
		} catch (BizException e) {
			throw new JspException(e);
		}
	}

	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}

}
