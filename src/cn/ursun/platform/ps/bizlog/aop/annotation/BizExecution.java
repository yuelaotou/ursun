package cn.ursun.platform.ps.bizlog.aop.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import cn.ursun.platform.ps.bizlog.IBizLog.OperationType;

/**
 * <p>记录业务日志annonation</p>
 *
 * @author 段鹏- duanp@neusoft.com
 * @version 1.0 Created on Nov 20, 2008 11:34:41 PM
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface BizExecution {

	/**
	 * <p>是否进行业务处理</p>
	 * 
	 * @return
	 * @author: 段鹏 - duanp@neusoft.com 
	 * @date: Created on Nov 21, 2008 12:54:27 AM
	 */
	public boolean enable() default true;

	/**
	 * <p>操作类型</p>
	 * 
	 * @return
	 * @author: 段鹏 - duanp@neusoft.com 
	 * @date: Created on Nov 21, 2008 11:58:34 AM
	 */
	public OperationType type() default OperationType.USE;

}