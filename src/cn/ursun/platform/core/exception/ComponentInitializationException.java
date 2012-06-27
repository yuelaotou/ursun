/**
 * 文件名：ComponentInitializationException.java
 * 
 * 创建人：兰硕 - lans@neusoft.com
 * 
 * 创建时间：Sep 19, 2008 12:45:47 PM
 * 
 * 版权所有：东软软件股份有限公司
 */
package cn.ursun.platform.core.exception;

/**
 * @author 兰硕 - lans@neusoft.com
 */
public class ComponentInitializationException extends SystemException {

	public ComponentInitializationException() {
	}

	public ComponentInitializationException(String msg) {
		super(msg);
	}

	public ComponentInitializationException(Throwable ex) {
		super(ex);
	}

	public ComponentInitializationException(String msg, Throwable ex) {
		super(msg, ex);
	}
}
