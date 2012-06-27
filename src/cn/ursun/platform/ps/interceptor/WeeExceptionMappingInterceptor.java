/**
 * 文件名：WeeExceptionMappingInterceptor.java
 * 
 * 创建人：段鹏 - duanp@neusoft.com
 * 
 * 创建时间：Aug 16, 2008 12:27:15 AM
 * 
 * 版权所有：东软软件股份有限公司
 */
package cn.ursun.platform.ps.interceptor;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import cn.ursun.platform.core.exception.BizException;
import cn.ursun.platform.core.exception.SystemException;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.LocaleProvider;
import com.opensymphony.xwork2.TextProvider;
import com.opensymphony.xwork2.TextProviderFactory;
import com.opensymphony.xwork2.interceptor.ExceptionHolder;
import com.opensymphony.xwork2.interceptor.ExceptionMappingInterceptor;

/**
 * <p>[异常处理拦截器，处理各种异常信息]</p>
 *
 * @author 段鹏- duanp@neusoft.com
 * @version 1.0 Created on Aug 16, 2008 2:29:06 PM
 */
public class WeeExceptionMappingInterceptor extends ExceptionMappingInterceptor implements LocaleProvider {

	/**
	 * 异常错误信息字符串
	 */
	private static final String ERROR_MSG_KEY = "error_msg";

	private static final String ERROR_MSG_STACK = "error_stack";

	private static final String BIZ_ERROR_CODE_DEFAULT = "wee.error.biz_error_default_msg";

	private static final String SYSTEM_ERROR_CODE_DEFAULT = "wee.error.system_error_default_msg";

	/**
	 * 异常日志KEY
	 */
	private static final String WEE_EXCEPTION_LOGGER = "WeeException";

	/**
	 * 
	 */
	private final transient TextProvider textProvider = new TextProviderFactory().createInstance(getClass(), this);

	/**
	 * <p>重写拦截方法，增加处理机制</p>
	 * 
	 * @see com.opensymphony.xwork2.interceptor.ExceptionMappingInterceptor#intercept(com.opensymphony.xwork2.ActionInvocation)
	 * @author: 段鹏 - duanp@neusoft.com 
	 * @date: Created on Aug 16, 2008 2:29:51 PM
	 */
	public String intercept(ActionInvocation invocation) throws Exception {
		String result = null;
		try {
			result = invocation.invoke();
		} catch (Exception e) {
			if (logEnabled) {
				handleLogging(e);
			}

			Exception ex = null;
			// 如果为ajax提交，则将错误信息写到response中, 并设置状态值
			if (isAjaxSumbit()) {
				handleAjaxException(e);
				result = Action.NONE;
			} else if (e instanceof BizException) {
				ex = handleBizException((BizException) e);
				result = "global_error";
			} else if (e instanceof SystemException) {
				ex = handleSystemException((SystemException) e);
				result = "global_error";
			} else {
				ex = handleOtherException(e);
				result = "global_error";
			}
			publishException(invocation, new ExceptionHolder(ex == null ? e : ex));
			doLog(e);
		}
		return result;
	}

	/**
	 * <p>判断是否为ajax提交</p>
	 * 
	 * @return
	 * @author: 段鹏 - duanp@neusoft.com 
	 * @date: Created on Dec 17, 2008 7:22:23 PM
	 */
	private boolean isAjaxSumbit() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String submitType = request.getHeader("X-Requested-With");
		if (submitType == null || "".equals(submitType)) {
			submitType = request.getParameter("submit-type");
		}
		return "XMLHttpRequest".equals(submitType);
	}

	/**
	 * <p>处理AJAXException</p>
	 * 
	 * @param e
	 * @throws Exception
	 * @author: 段鹏 - duanp@neusoft.com 
	 * @date: Created on Aug 16, 2008 4:33:52 PM
	 */
	private void handleAjaxException(Exception e) throws Exception {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html; charset=UTF-8");
		response.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
		String errorMsg = null;
		if (e instanceof BizException) {
			errorMsg = getBizExceptionMessageStack((BizException) e);
		} else {
			errorMsg = new JSONObject().element(ERROR_MSG_KEY, getText(SYSTEM_ERROR_CODE_DEFAULT)).toString();
		}
		PrintWriter pw = response.getWriter();
		pw.println(errorMsg);
		pw.flush();
		pw.close();
	}

	/**
	 * <p>获得业务异常提示信息栈</p>
	 * 
	 * @param ex
	 * @return
	 * @author: 段鹏 - duanp@neusoft.com 
	 * @date: Created on Dec 17, 2008 7:21:06 PM
	 */
	private String getBizExceptionMessageStack(BizException ex) {
		String msg = ex.getMessage();
		String[] params = ex.getMessageParameters();
		JSONObject retValue = new JSONObject();
		retValue.element(ERROR_MSG_KEY, getText(StringUtils.defaultString(msg, BIZ_ERROR_CODE_DEFAULT), params));
		if (ex.getCause() != null && ex.getCause() instanceof BizException) {
			retValue.element(ERROR_MSG_STACK, getBizExceptionMessageStack((BizException) ex.getCause()));
		}
		return retValue.toString();
	}

	/**
	 * <p>根据错误代码获得错误信息</p>
	 * 
	 * @param textKey
	 * @param params
	 * @param defalutMsg
	 * @return
	 * @author: 段鹏 - duanp@neusoft.com 
	 * @date: Created on Dec 17, 2008 8:03:04 PM
	 */
	private String getText(String textKey, String[] params, String defalutMsg) {
		try {
			if (!"".equals(StringUtils.defaultString(textKey, "")) && params != null)
				return textProvider.getText(textKey, params);
			else if (!"".equals(StringUtils.defaultString(textKey, "")) && params == null)
				return textProvider.getText(textKey, textProvider.getText(defalutMsg));
			else
				return textProvider.getText(defalutMsg);
		} catch (Exception ex) {
			doLog(ex);
			Logger.getLogger(WEE_EXCEPTION_LOGGER).error("系统查询错误信息失败！");
			return defalutMsg;
		}
	}

	/**
	 * <p>根据错误代码获得错误信息</p>
	 * 
	 * @param textKey
	 * @param defalutMsg
	 * @return
	 * @author: 段鹏 - duanp@neusoft.com 
	 * @date: Created on Dec 17, 2008 8:03:23 PM
	 */
	private String getText(String textKey, String defalutMsg) {
		return textProvider.getText(textKey, defalutMsg);
	}

	/**
	 * <p>根据错误代码获得错误信息</p>
	 * 
	 * @param textKey
	 * @param defalutMsg
	 * @return
	 * @author: 段鹏 - duanp@neusoft.com 
	 * @date: Created on Dec 17, 2008 8:03:23 PM
	 */
	private String getText(String textKey, String[] params) {
		return textProvider.getText(textKey,textProvider.getText(BIZ_ERROR_CODE_DEFAULT), params);
	}

	/**
	 * <p>根据错误代码获得错误信息</p>
	 * 
	 * @param textKey
	 * @return
	 * @author: 段鹏 - duanp@neusoft.com 
	 * @date: Created on Dec 17, 2008 8:03:31 PM
	 */
	private String getText(String textKey) {
		return textProvider.getText(textKey,textProvider.getText(BIZ_ERROR_CODE_DEFAULT));
	}

	/**
	 * <p>处理其他类型异常</p>
	 * 
	 * @param e
	 * @return
	 * @throws Exception
	 * @author: 段鹏 - duanp@neusoft.com 
	 * @date: Created on Dec 17, 2008 8:03:36 PM
	 */
	private Exception handleOtherException(Exception e) throws Exception {
		return new SystemException(getText(SYSTEM_ERROR_CODE_DEFAULT), e);
	}

	/**
	 * <p>处理SystemException</p>
	 * 
	 * @param e
	 * @throws Exception
	 * @author: 段鹏 - duanp@neusoft.com 
	 * @date: Created on Aug 16, 2008 4:33:52 PM
	 */
	private Exception handleSystemException(SystemException e) throws Exception {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html; charset=UTF-8");
		response.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
		return new SystemException(getText(SYSTEM_ERROR_CODE_DEFAULT), e);
	}

	/**
	 * <p>处理BizException</p>
	 * 
	 * @param e
	 * @throws Exception
	 * @author: 段鹏 - duanp@neusoft.com 
	 * @date: Created on Aug 16, 2008 4:33:52 PM
	 */
	private Exception handleBizException(BizException e) throws Exception {
		List errorMessage = new ArrayList();
		getBizExceptionMessageStack(errorMessage, e);
		return new BizException(StringUtils.join(errorMessage.iterator(), "->"), e);
	}

	/**
	 * <p>获得业务异常提示信息栈</p>
	 * 
	 * @param ex
	 * @return
	 * @author: 段鹏 - duanp@neusoft.com 
	 * @date: Created on Dec 17, 2008 7:21:06 PM
	 */
	private void getBizExceptionMessageStack(List errorMessageList, BizException ex) {
		String msg = ex.getMessage();
		String[] params = ex.getMessageParameters();
		errorMessageList.add(getText(StringUtils.defaultString(msg, BIZ_ERROR_CODE_DEFAULT), params));
		if (ex.getCause() != null && ex.getCause() instanceof BizException) {
			getBizExceptionMessageStack(errorMessageList, (BizException) ex.getCause());
		}
	}

	/**
	 * <p>记录日志</p>
	 * 
	 * @param e
	 * @author: 段鹏 - duanp@neusoft.com 
	 * @date: Created on Aug 16, 2008 4:40:05 PM
	 */
	private void doLog(Exception e) {
		// log4j日志记录操作
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		Logger.getLogger(WEE_EXCEPTION_LOGGER).debug(sw.getBuffer().toString());
	}

	public Locale getLocale() {
		return ActionContext.getContext().getLocale();
	}
}
