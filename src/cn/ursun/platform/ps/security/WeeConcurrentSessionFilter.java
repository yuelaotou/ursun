package cn.ursun.platform.ps.security;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.acegisecurity.concurrent.SessionInformation;
import org.acegisecurity.concurrent.SessionRegistry;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

public class WeeConcurrentSessionFilter implements Filter,InitializingBean {

	// ~ Instance fields
	// ================================================================================================

	private SessionRegistry sessionRegistry;

	private String expiredUrl;

	private static final String ERROR_MSG_KEY = "error_msg";
	private static final String ERROR_TYPE_KEY = "error_type";

	// ~ Methods
	// ========================================================================================================

	public void afterPropertiesSet() throws Exception {
		Assert.notNull(sessionRegistry, "SessionRegistry required");
		Assert.hasText(expiredUrl, "ExpiredUrl required");
	}

	/**
	 * Does nothing. We use IoC container lifecycle services instead.
	 */
	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
			ServletException {
		Assert.isInstanceOf(HttpServletRequest.class, request, "Can only process HttpServletRequest");
		Assert.isInstanceOf(HttpServletResponse.class, response, "Can only process HttpServletResponse");

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;

		HttpSession session = httpRequest.getSession(false);

		if (session != null) {
			SessionInformation info = sessionRegistry.getSessionInformation(session.getId());

			if (info != null) {
				if (info.isExpired()) {
					// Expired - abort processing
					session.invalidate();
					httpResponse.addHeader("auth-state", WeeExceptionTranslationFilter.AUTHENTICATION_EXPIRED);
					if (isAjaxSumbit(httpRequest)) {
						httpResponse.setContentType("text/html; charset=UTF-8");
						httpResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
						JSONObject	jsonObject=new JSONObject();
						jsonObject.element(ERROR_MSG_KEY, "您的会话已超时，请重新登录！");
						jsonObject.element(ERROR_TYPE_KEY, "SessionTimeout");
						String errorMsg = jsonObject.toString();
						PrintWriter pw = httpResponse.getWriter();
						pw.println(errorMsg);
						pw.flush();
						pw.close();
					} else {
						String targetUrl = httpRequest.getContextPath() + expiredUrl;
						httpResponse.sendRedirect(httpResponse.encodeRedirectURL(targetUrl));
					}
					return;
				} else {
					// Non-expired - update last request date/time
					info.refreshLastRequest();
				}
			}
		}

		chain.doFilter(request, response);
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
	 * Does nothing. We use IoC container lifecycle services instead.
	 *
	 * @param arg0 ignored
	 *
	 * @throws ServletException ignored
	 */
	public void init(FilterConfig arg0) throws ServletException {
	}

	public void setExpiredUrl(String expiredUrl) {
		this.expiredUrl = expiredUrl;
	}

	public void setSessionRegistry(SessionRegistry sessionRegistry) {
		this.sessionRegistry = sessionRegistry;
	}

}
