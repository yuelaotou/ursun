/**
 * 文件名：DAOUtils.java
 * 
 * 创建人：兰硕 - lans@neusoft.com
 * 
 * 创建时间：Oct 7, 2008 11:21:16 AM
 * 
 * 版权所有：东软集团股份有限公司
 */
package cn.ursun.platform.core.jdbc;

/**
 * <p>[描述信息：说明类的基本功能]</p>
 *
 * @author 兰硕 - lans@neusoft.com
 * @version 1.0 Created on Oct 7, 2008 11:21:16 AM
 */
public class DAOUtils {

	public static String convertBoolean2String(Boolean b) {
		if (b == null)
			return null;
		return b.booleanValue() ? "1" : "0";
	}

	public static Boolean convertString2Boolean(String value, Boolean defaultValue) {
		if (value == null)
			return defaultValue;
		if ("0".equals(value))
			return Boolean.FALSE;
		else if ("1".equals(value))
			return Boolean.TRUE;
		return defaultValue;
	}

	public static boolean convertString2Boolean(String value) {
		return convertString2Boolean(value, Boolean.FALSE);
	}
}
