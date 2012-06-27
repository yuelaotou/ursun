package cn.ursun.platform.ps.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;

import cn.ursun.platform.core.tag.BaseTagSupport;
import cn.ursun.platform.ps.security.WeeSecurityInfo;

public class IsAnonymousUserTag  extends BaseTagSupport {

	public int doStartTag() throws JspException {

		if (WeeSecurityInfo.getInstance().isAnonymousUser())
			return Tag.EVAL_PAGE;
		else
			return Tag.SKIP_BODY;

	}
}
