/**
 * 文件名：ExecutionTimeHandler.java
 * 
 * 创建人：兰硕 - lans@neusoft.com
 * 
 * 创建时间：Nov 11, 2008 5:24:16 PM
 * 
 * 版权所有：东软集团股份有限公司
 */
package cn.ursun.platform.core.aop.debug;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import cn.ursun.platform.core.aop.annotation.MethodTimeDebugger;
import cn.ursun.platform.core.aop.aspect.WeeBaseAspect;

/**
 * <p>[描述信息：说明类的基本功能]</p>
 *
 * @author 兰硕 - lans@neusoft.com
 * @version 1.0 Created on Nov 11, 2008 5:24:16 PM
 */
@Aspect
public class ExecutionTimeDebuggerAspect extends WeeBaseAspect {

	protected final static Log logger = LogFactory.getLog(ExecutionTimeDebuggerAspect.class);

	@Around(value = "@annotation(cn.ursun.platform.core.aop.annotation.MethodTimeDebugger)")
	public Object debugExecutionTime(ProceedingJoinPoint pjp) throws Throwable {
		MethodTimeDebugger timeDebugger = getAnnotation(pjp.getSignature(), MethodTimeDebugger.class);
		return debug(pjp, timeDebugger);
	}

	private Object debug(ProceedingJoinPoint pjp, MethodTimeDebugger timeDebugger) throws Throwable {
		if (!timeDebugger.value()) {
			return pjp.proceed();
		}
		String type = timeDebugger.type();
		StringBuffer msg = new StringBuffer();
		msg.append(type + "[").append(pjp.getTarget().getClass().getName()).append(".").append(
				getMethod(pjp.getSignature()).getName()).append("(..)");
		ExecutionStack.push(msg.toString());
		Object result = pjp.proceed();
		ExecutionStack.pop(msg.toString());
		return result;
	}
}
