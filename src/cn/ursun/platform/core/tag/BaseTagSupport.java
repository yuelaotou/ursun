/**
 * �ļ���SipoTagSupport.java
 * 
 * �����ˣ�<˶ - lans@neusoft.com
 * 
 * ����ʱ�䣺May 20, 2008 1:51:32 PM
 * 
 * ��Ȩ���У���������ɷ����޹�˾
 */
package cn.ursun.platform.core.tag;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * 
 * @author <˶ - lans@neusoft.com
 */
public abstract class BaseTagSupport extends TagSupport {

	protected HttpServletRequest getHttpServletRequest() {
		return (HttpServletRequest) pageContext.getRequest();
	}

	protected HttpServletResponse getHttpServletResponse() {
		return (HttpServletResponse) pageContext.getResponse();
	}

	protected String getContextPath() {
		return getHttpServletRequest().getContextPath();
	}

	protected void write(String text) throws JspException {
		JspWriter writer = pageContext.getOut();
		try {
			writer.print(text);
		} catch (IOException e) {
			throw new JspException(e.toString());
		}
	}
}
