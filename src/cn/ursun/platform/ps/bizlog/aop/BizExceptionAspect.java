/**
 * 文件名：BizExceptionAspect.java
 * 
 * 创建人：兰硕 - lans@neusoft.com
 * 
 * 创建时间：Nov 25, 2008 6:42:39 PM
 * 
 * 版权所有：东软集团股份有限公司
 */
package cn.ursun.platform.ps.bizlog.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;

import cn.ursun.platform.core.aop.aspect.WeeBaseAspect;
import cn.ursun.platform.core.exception.BizException;
import cn.ursun.platform.core.exception.SystemException;

/**
 * <p>
 * 异常处理类
 * </p>
 * 
 * @author 兰硕 - lans@neusoft.com
 * @version 1.0 Created on Nov 25, 2008 6:42:39 PM
 */
@Aspect
public class BizExceptionAspect extends WeeBaseAspect {

	@AfterThrowing(value = "@annotation(cn.ursun.platform.ps.bizlog.aop.annotation.BizExecution)", throwing = "ex")
	public void handleException(ProceedingJoinPoint pjp, Throwable ex) throws BizException {
		if (ex instanceof BizException) {
			throw (BizException) ex;
		} else if (ex instanceof SystemException) {
			throw (SystemException) ex;
		} else {
			throw new SystemException(ex.getMessage(), ex);
		}
	}

}
