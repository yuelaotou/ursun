/**
 * 文件名：TimeDebugger.java
 * 
 * 创建人：兰硕 - lans@neusoft.com
 * 
 * 创建时间：Nov 11, 2008 9:14:35 PM
 * 
 * 版权所有：东软集团股份有限公司
 */
package cn.ursun.platform.core.aop.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>是否进行时间测试。</p>
 *
 * @author 兰硕 - lans@neusoft.com
 * @version 1.0 Created on Nov 11, 2008 9:14:35 PM
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface MethodTimeDebugger {

	/**
	 * <p>是否进行时间测试</p>
	 * 
	 * @author: 兰硕 - lans@neusoft.com
	 * @date: Created on Nov 11, 2008 9:18:00 PM
	 */
	public boolean value() default true;
	
	public String type() default "EXECUTION";
}
