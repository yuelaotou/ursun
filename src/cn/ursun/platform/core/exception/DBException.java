/**
 * 文件名：DBExcpetion.java
 * 
 * 创建人：段鹏 - duanp@neusoft.com
 * 
 * 创建时间：Aug 6, 2008 9:39:05 PM
 * 
 * 版权所有：东软软件股份有限公司
 */
package cn.ursun.platform.core.exception;

/**
 * <p> Title: 数据库操作异常　</p>
 * <p> Description: 描述数据库操作的异常情况　</p>
 * <p> Created on Aug 6, 2008</p>
 * <p> Copyright: Copyright (c) 2008</p>
 * <p> Company: 东软软件股份有限公司</p>
 * @author 段鹏 - duanp@neusoft.com
 * @version 1.0
 */
public class DBException extends SystemException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 构造方法
	 */
	public DBException() {
	}

	/**
	 * 构造方法
	 */
	public DBException(String msg) {
		super(msg);
	}

	/**
	 * 构造方法
	 */
	public DBException(Throwable ex) {
		super(ex);
	}

	/**
	 * 构造方法
	 */
	public DBException(String msg, Throwable ex) {
		super(msg, ex);
	}
}
