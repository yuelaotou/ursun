/**
 * 文件名：ESSAuthenticationProcessingFilter.java
 * 
 * 创建人：兰硕 - lans@neusoft.com
 * 
 * 创建时间：Aug 31, 2008 4:36:26 PM
 * 
 * 版权所有：东软集团股份有限公司
 */
package cn.ursun.platform.ps.security;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.acegisecurity.Authentication;
import org.acegisecurity.AuthenticationException;
import org.acegisecurity.ui.webapp.AuthenticationProcessingFilter;

/**
 * <p>[描述信息：说明类的基本功能]</p>
 *
 * @author 兰硕 - lans@neusoft.com
 * @version 1.0 Created on Aug 31, 2008 4:36:26 PM
 */
public class WeeAuthenticationProcessingFilter extends AuthenticationProcessingFilter {

	public static final String ACEGI_SECURITY_FORM_VALIDATION_CODE_KEY = "j_validation_code";

	private boolean isValidationCode = false;

	protected void onSuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			Authentication authResult) throws IOException {
		request.setAttribute("j_loginpoint_url", request.getParameter("j_loginpoint_url"));
		super.onSuccessfulAuthentication(request, response, authResult);
	}

	protected void onUnsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException {
		super.onUnsuccessfulAuthentication(request, response, failed);
	}
	
	protected String determineTargetUrl(HttpServletRequest request) {
		String targetUrl = isAlwaysUseDefaultTargetUrl() ? null : obtainFullRequestUrl(request);
		if (targetUrl == null) {
			String loginSuccessUrl = request.getParameter("j_loginsuccess_url");
			if (loginSuccessUrl != null) {
				if (getDefaultTargetUrl().lastIndexOf("?") != -1)
					targetUrl = getDefaultTargetUrl() + "&j_loginsuccess_url="+loginSuccessUrl;
				else
					targetUrl = getDefaultTargetUrl()+ "?j_loginsuccess_url="+loginSuccessUrl;
			} else
				targetUrl = getDefaultTargetUrl();

		}
		return targetUrl;
	}

	public Authentication attemptAuthentication(HttpServletRequest httpServletRequest) throws AuthenticationException {
		if (isValidationCode) {
			String inputValidationCode = httpServletRequest.getParameter("j_validation_code");
			// 从Session中取出验证码
			String ssnValidationCode = (String) httpServletRequest.getSession().getAttribute("j_validation_code");
			// 取出后就失效，
			httpServletRequest.getSession().setAttribute("j_validation_code", "Validation Code Invalidation");
			if (ssnValidationCode == null || ssnValidationCode.equalsIgnoreCase("Validation Code Invalidation")
					|| !ssnValidationCode.equals(inputValidationCode)) {

				// 用户输入的值与看到的不一致,抛出异常
				throw new WeeValidationCodeException("验证码输入错误!");
			}
		}
		return super.attemptAuthentication(httpServletRequest);
	}

	protected String obtainValidationCode(HttpServletRequest request) {
		return request.getParameter(ACEGI_SECURITY_FORM_VALIDATION_CODE_KEY);
	}

	public boolean isValidationCode() {
		return isValidationCode;
	}

	public void setIsValidationCode(boolean isValidationCode) {
		this.isValidationCode = isValidationCode;
	}

}
