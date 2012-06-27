/**
 * 文件名：BizLogInterceptor.java
 * 
 * 创建人：兰硕 - lans@neusoft.com
 * 
 * 创建时间：Nov 20, 2008 11:15:24 PM
 * 
 * 版权所有：东软集团股份有限公司
 */
package cn.ursun.platform.ps.interceptor;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.StrutsStatics;

import cn.ursun.platform.core.context.RequestContext;
import cn.ursun.platform.ps.security.WeeSecurityInfo;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * <p>客户端信息拦截器，向RequestContext中添加Module-Level和Client-IP信息。</p>
 *
 * @author 宋成山 - songchengshan@neusoft.com
 * @version 1.0 Created on Nov 20, 2008 11:15:24 PM
 */
public class ClientInfoContextInterceptor extends AbstractInterceptor {

	public final static String MODULE_LEVEL = "Module_Level";

	public final static String CLIENT_IP = "Client_IP";

	public String intercept(ActionInvocation invocation) throws Exception {
		String moduleLevel = ServletActionContext.getRequest().getParameter("wee.bizlog.modulelevel");
		RequestContext.getContext().put(MODULE_LEVEL, moduleLevel);
		HttpServletRequest request = (HttpServletRequest) ActionContext.getContext().get(StrutsStatics.HTTP_REQUEST);
		String accessIP = getAccessIp(request);
		RequestContext.getContext().put(CLIENT_IP, accessIP);
		ServletActionContext.getResponse().addCookie(new Cookie("WEE_SID",ServletActionContext.getRequest().getSession().getId()));
		ServletActionContext.getResponse().addCookie(new Cookie("IS_LOGIN",String.valueOf(!WeeSecurityInfo.getInstance().isAnonymousUser())));
		return invocation.invoke();
	}

	/**
	 * <p>取得访问的真实IP</p>
	 * 
	 * @param request
	 * @return
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @throws UnknownHostException 
	 * @date: Created on 2009-2-10 上午11:01:46
	 */
	private String getAccessIp(HttpServletRequest request) throws UnknownHostException {
		String accessIP = request.getHeader("X-Forwarded-For");
		if (accessIP == null || accessIP.length() == 0 || "unknown".equalsIgnoreCase(accessIP)) {
			accessIP = request.getHeader("Proxy-Client-IP");
		}
		if (accessIP == null || accessIP.length() == 0 || "unknown".equalsIgnoreCase(accessIP)) {
			accessIP = request.getHeader("WL-Proxy-Client-IP");
		}
		if (accessIP == null || accessIP.length() == 0 || "unknown".equalsIgnoreCase(accessIP)) {
			accessIP = request.getRemoteAddr();
		}
		if (accessIP == null || accessIP.length() == 0 || "127.0.0.1".equalsIgnoreCase(accessIP)) {
			accessIP = InetAddress.getLocalHost().getHostAddress();
		}
		return accessIP;
	}
}
