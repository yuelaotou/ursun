/**
 * 文件名：EntityNotExistedException.java
 * 
 * 创建人：兰硕 - lans@neusoft.com
 * 
 * 创建时间：Sep 3, 2008 2:38:05 PM
 * 
 * 版权所有：东软集团股份有限公司
 */
package cn.ursun.platform.core.exception;

/**
 * <p>[描述信息：说明类的基本功能]</p>
 *
 * @author 兰硕 - lans@neusoft.com
 * @version 1.0 Created on Sep 3, 2008 2:38:05 PM
 */
public class PermissionForbiddenException extends BizException {

	public PermissionForbiddenException(String errorCode) {
		super(errorCode);
	}

	public PermissionForbiddenException(String errorCode, Throwable ex) {
		super(errorCode, ex);
	}
}
