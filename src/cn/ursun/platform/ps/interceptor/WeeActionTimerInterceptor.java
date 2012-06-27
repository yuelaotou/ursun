/**
 * 文件名：WEEActionTimerInterceptor.java
 * 
 * 创建人：兰硕 - lans@neusoft.com
 * 
 * 创建时间：Nov 12, 2008 10:18:59 AM
 * 
 * 版权所有：东软集团股份有限公司
 */
package cn.ursun.platform.ps.interceptor;

import cn.ursun.platform.core.aop.debug.ExecutionStack;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.TimerInterceptor;

/**
 * <p>[描述信息：说明类的基本功能]</p>
 *
 * @author 兰硕 - lans@neusoft.com
 * @version 1.0 Created on Nov 12, 2008 10:18:59 AM
 */
public class WeeActionTimerInterceptor extends TimerInterceptor {

	protected String invokeUnderTiming(ActionInvocation invocation) throws Exception {

		String methodName = invocation.getProxy().getMethod();
		String clazzName = invocation.getAction().getClass().getName();

		StringBuffer message = new StringBuffer(100);
		message.append("ACT[" + clazzName + "." + methodName + "(..)" + "],URL[");
		StringBuffer url = new StringBuffer();
		String namespace = invocation.getProxy().getNamespace();
		if ((namespace != null) && (namespace.trim().length() > 0)) {
			url.append(namespace + "/");
		}
		url.append(invocation.getProxy().getActionName());
		url.append("!");
		url.append(invocation.getProxy().getMethod());

		message.append(url);
		message.append("]");

		ExecutionStack.setActive(true);
		ExecutionStack.push(message.toString());
		String result = invocation.invoke();
		ExecutionStack.pop(message.toString());
		ExecutionStack.setActive(false);

		return result;
	}
}
