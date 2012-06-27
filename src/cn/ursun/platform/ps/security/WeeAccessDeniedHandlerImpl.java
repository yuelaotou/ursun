/**
 * 文件名：ESSAccessDeniedHandlerImpl.java
 * 
 * 创建人：段鹏 - duanp@neusoft.com
 * 
 * 创建时间：2009-3-3 下午09:25:27
 * 
 * 版权所有：东软集团股份有限公司
 */
package cn.ursun.platform.ps.security;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.acegisecurity.AccessDeniedException;
import org.acegisecurity.ui.AccessDeniedHandler;
import org.acegisecurity.ui.AccessDeniedHandlerImpl;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>访问受限处理类</p>
 *
 * @author 段鹏- duanp@neusoft.com
 * @version 1.0 Created on 2009-3-3 下午09:25:27
 */
public class WeeAccessDeniedHandlerImpl implements AccessDeniedHandler {

	public static final String ACEGI_SECURITY_ACCESS_DENIED_EXCEPTION_KEY = "ACEGI_SECURITY_403_EXCEPTION";

	protected static final Log logger = LogFactory.getLog(AccessDeniedHandlerImpl.class);

	/**
	 * 异常错误信息字符串
	 */
	private static final String ERROR_MSG_KEY = "error_msg";

	private static final String ACCESS_DENIED = "资源访问受限，如有需要请联系管理员";

	private String errorPage;

	/**
	 * <p>访问受限后的处理方法</p>
	 * 
	 * @see org.acegisecurity.ui.AccessDeniedHandler#handle(javax.servlet.ServletRequest, javax.servlet.ServletResponse, org.acegisecurity.AccessDeniedException)
	 * @author: 段鹏 - duanp@neusoft.com 
	 * @date: Created on 2009-3-3 下午09:35:43
	 */
	public void handle(ServletRequest request, ServletResponse response, AccessDeniedException accessDeniedException)
			throws IOException, ServletException {

		if (errorPage != null) {
			HttpServletRequest req = (HttpServletRequest) request;
			HttpServletResponse res = (HttpServletResponse) response;
			if (isAjaxSumbit(req)) {
				res.setContentType("text/html; charset=UTF-8");
				res.setStatus(HttpServletResponse.SC_FORBIDDEN);
				String errorMsg = new JSONObject().element(ERROR_MSG_KEY, ACCESS_DENIED).toString();
				PrintWriter pw = res.getWriter();
				pw.println(errorMsg);
				pw.flush();
				pw.close();
			} else {
				req.setAttribute(ACEGI_SECURITY_ACCESS_DENIED_EXCEPTION_KEY, accessDeniedException);
				res.sendRedirect(req.getContextPath() + errorPage);
			}
		}

		if (!response.isCommitted()) {
			// Send 403 (we do this after response has been written)

			((HttpServletResponse) response).sendError(HttpServletResponse.SC_FORBIDDEN, accessDeniedException
					.getMessage());
		}
	}

	/**
	 * <p>判断是否为ajax提交</p>
	 * @param request
	 * @return
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @date: Create on 2010-2-4 上午11:15:02
	 */
	private boolean isAjaxSumbit(HttpServletRequest request) {
		String submitType = request.getHeader("X-Requested-With");
		if (submitType == null || "".equals(submitType)) {
			submitType = request.getParameter("submit-type");
		}
		return "XMLHttpRequest".equals(submitType);
	}

	/**
	 * The error page to use. Must begin with a "/" and is interpreted relative to the current context root.
	 *
	 * @param errorPage the dispatcher path to display
	 *
	 * @throws IllegalArgumentException if the argument doesn't comply with the above limitations
	 */
	public void setErrorPage(String errorPage) {
		if ((errorPage != null) && !errorPage.startsWith("/")) {
			throw new IllegalArgumentException("ErrorPage must begin with '/'");
		}

		this.errorPage = errorPage;
	}

}
