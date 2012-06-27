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

import net.sf.json.JSONObject;

import org.acegisecurity.AccessDeniedException;
import org.acegisecurity.AcegiSecurityException;
import org.acegisecurity.AuthenticationException;
import org.acegisecurity.AuthenticationTrustResolver;
import org.acegisecurity.AuthenticationTrustResolverImpl;
import org.acegisecurity.InsufficientAuthenticationException;
import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.ui.AbstractProcessingFilter;
import org.acegisecurity.ui.AccessDeniedHandler;
import org.acegisecurity.ui.AccessDeniedHandlerImpl;
import org.acegisecurity.ui.AuthenticationEntryPoint;
import org.acegisecurity.ui.savedrequest.SavedRequest;
import org.acegisecurity.util.PortResolver;
import org.acegisecurity.util.PortResolverImpl;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

public class WeeExceptionTranslationFilter implements Filter,InitializingBean {

	// ~ Static fields/initializers
	// =====================================================================================

	private static final Log logger = LogFactory.getLog(WeeExceptionTranslationFilter.class);

	// ~ Instance fields
	// ================================================================================================

	private AccessDeniedHandler accessDeniedHandler = new AccessDeniedHandlerImpl();

	private AuthenticationEntryPoint authenticationEntryPoint;

	private AuthenticationTrustResolver authenticationTrustResolver = new AuthenticationTrustResolverImpl();

	private PortResolver portResolver = new PortResolverImpl();

	private boolean createSessionAllowed = true;

	public static final String AUTHENTICATION_FAIL = "01";// Authentication exception occurred; redirecting to

	// authentication entry point

	public static final String AUTHENTICATION_EXPIRED = "02";// Expired - abort processing

	public static final String ACCESS_DENIED_ANONYMOUS = "03";// Access is denied (user is anonymous); redirecting to

	// authentication entry point

	public static final String ACCESS_DENIED_NOANONYMOUS = "04";// Access is denied (user is not anonymous); delegating

	// to AccessDeniedHandler

	private static final String ERROR_MSG_KEY = "error_msg";

	private static final String ERROR_TYPE_KEY = "error_type";

	// ~ Methods
	// ========================================================================================================

	public void afterPropertiesSet() throws Exception {
		Assert.notNull(authenticationEntryPoint, "authenticationEntryPoint must be specified");
		Assert.notNull(portResolver, "portResolver must be specified");
		Assert.notNull(authenticationTrustResolver, "authenticationTrustResolver must be specified");
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
			ServletException {
		if (!(request instanceof HttpServletRequest)) {
			throw new ServletException("HttpServletRequest required");
		}

		if (!(response instanceof HttpServletResponse)) {
			throw new ServletException("HttpServletResponse required");
		}

		try {
			chain.doFilter(request, response);
			if (logger.isDebugEnabled()) {
				logger.debug("Chain processed normally");
			}
		} catch (AuthenticationException ex) {
			handleException(request, response, chain, ex);
		} catch (AccessDeniedException ex) {
			handleException(request, response, chain, ex);
		} catch (ServletException ex) {
			if (ex.getRootCause() instanceof AuthenticationException
					|| ex.getRootCause() instanceof AccessDeniedException) {
				handleException(request, response, chain, (AcegiSecurityException) ex.getRootCause());
			} else {
				throw ex;
			}
		} catch (IOException ex) {
			throw ex;
		}
	}

	public AuthenticationEntryPoint getAuthenticationEntryPoint() {
		return authenticationEntryPoint;
	}

	public AuthenticationTrustResolver getAuthenticationTrustResolver() {
		return authenticationTrustResolver;
	}

	public PortResolver getPortResolver() {
		return portResolver;
	}

	private void handleException(ServletRequest request, ServletResponse response, FilterChain chain,
			AcegiSecurityException exception) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		boolean isAjax = isAjaxSumbit(req);
		if (exception instanceof AuthenticationException) {
			if (logger.isDebugEnabled()) {
				logger.debug("Authentication exception occurred; redirecting to authentication entry point", exception);
			}
			// 添加身份认证的失败原因
			res.addHeader("auth-state", AUTHENTICATION_FAIL);
			if (isAjax) {
				res.setContentType("text/html; charset=UTF-8");
				res.setStatus(HttpServletResponse.SC_FORBIDDEN);
				String errorMsg = new JSONObject().element(ERROR_MSG_KEY, "登录失败！").toString();
				PrintWriter pw = res.getWriter();
				pw.println(errorMsg);
				pw.flush();
				pw.close();
			} else {
				sendStartAuthentication(request, response, chain, (AuthenticationException) exception);
			}
		} else if (exception instanceof AccessDeniedException) {
			if (authenticationTrustResolver.isAnonymous(SecurityContextHolder.getContext().getAuthentication())) {
				if (logger.isDebugEnabled()) {
					logger.debug("Access is denied (user is anonymous); redirecting to authentication entry point",
							exception);
				}
				res.addHeader("auth-state", ACCESS_DENIED_ANONYMOUS);
				if (isAjax) {
					res.setContentType("text/html; charset=UTF-8");
					res.setStatus(HttpServletResponse.SC_FORBIDDEN);
					JSONObject jsonObject = new JSONObject();
					jsonObject.element(ERROR_MSG_KEY, "您的会话已超时，请重新登录！");
					jsonObject.element(ERROR_TYPE_KEY, "SessionTimeout");
					String errorMsg = jsonObject.toString();
					PrintWriter pw = res.getWriter();
					pw.println(errorMsg);
					pw.flush();
					pw.close();
				} else {
					sendStartAuthentication(request, response, chain, new InsufficientAuthenticationException(
							"Full authentication is required to access this resource"));
				}
			} else {
				if (logger.isDebugEnabled()) {
					logger.debug("Access is denied (user is not anonymous); delegating to AccessDeniedHandler",
							exception);
				}
				res.addHeader("auth-state", ACCESS_DENIED_NOANONYMOUS);
				accessDeniedHandler.handle(request, response, (AccessDeniedException) exception);
			}
		}
	}

	/**
	 * If <code>true</code>, indicates that <code>SecurityEnforcementFilter</code> is permitted to store the target
	 * URL and exception information in the <code>HttpSession</code> (the default). In situations where you do not
	 * wish to unnecessarily create <code>HttpSession</code>s - because the user agent will know the failed URL, such
	 * as with BASIC or Digest authentication - you may wish to set this property to <code>false</code>. Remember to
	 * also set the {@link org.acegisecurity.context.HttpSessionContextIntegrationFilter#allowSessionCreation} to
	 * <code>false</code> if you set this property to <code>false</code>.
	 * 
	 * @return <code>true</code> if the <code>HttpSession</code> will be used to store information about the failed
	 *         request, <code>false</code> if the <code>HttpSession</code> will not be used
	 */
	public boolean isCreateSessionAllowed() {
		return createSessionAllowed;
	}

	protected void sendStartAuthentication(ServletRequest request, ServletResponse response, FilterChain chain,
			AuthenticationException reason) throws ServletException, IOException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;

		SavedRequest savedRequest = new SavedRequest(httpRequest, portResolver);

		if (logger.isDebugEnabled()) {
			logger.debug("Authentication entry point being called; SavedRequest added to Session: " + savedRequest);
		}

		if (createSessionAllowed) {
			// Store the HTTP request itself. Used by AbstractProcessingFilter
			// for redirection after successful authentication (SEC-29)
			httpRequest.getSession().setAttribute(AbstractProcessingFilter.ACEGI_SAVED_REQUEST_KEY, savedRequest);
		}

		// SEC-112: Clear the SecurityContextHolder's Authentication, as the
		// existing Authentication is no longer considered valid
		SecurityContextHolder.getContext().setAuthentication(null);

		authenticationEntryPoint.commence(httpRequest, (HttpServletResponse) response, reason);
	}

	/**
	 * <p>
	 * 判断是否为ajax提交
	 * </p>
	 * 
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

	public void setAccessDeniedHandler(AccessDeniedHandler accessDeniedHandler) {
		Assert.notNull(accessDeniedHandler, "AccessDeniedHandler required");
		this.accessDeniedHandler = accessDeniedHandler;
	}

	public void setAuthenticationEntryPoint(AuthenticationEntryPoint authenticationEntryPoint) {
		this.authenticationEntryPoint = authenticationEntryPoint;
	}

	public void setAuthenticationTrustResolver(AuthenticationTrustResolver authenticationTrustResolver) {
		this.authenticationTrustResolver = authenticationTrustResolver;
	}

	public void setCreateSessionAllowed(boolean createSessionAllowed) {
		this.createSessionAllowed = createSessionAllowed;
	}

	public void setPortResolver(PortResolver portResolver) {
		this.portResolver = portResolver;
	}

	public void init(FilterConfig filterConfig) throws ServletException {
	}

	public void destroy() {
	}

}
