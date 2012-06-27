/**
 * 文件名：WeblogicUtils.java
 * 
 * 创建人：段鹏 - duanp@neusoft.com
 * 
 * 创建时间：2009-3-26 下午07:07:05
 * 
 * 版权所有：东软集团股份有限公司
 */
package cn.ursun.platform.core.util;

/**
 * <p>[描述信息：说明类的基本功能]</p>
 *
 * @author 段鹏- duanp@neusoft.com
 * @version 1.0 Created on 2009-3-26 下午07:07:05
 */
public class ServerUtils {

	public static final String WEBLOGIC_SERVER_KEY = "weblogic.Name";

	/**
	 * <p>获得weblogic server name</p>
	 * 
	 * @return
	 * @author: 段鹏 - duanp@neusoft.com 
	 * @date: Created on 2009-3-26 下午07:08:54
	 */
	public static String getWeblogicServerName() {
		return System.getProperty(WEBLOGIC_SERVER_KEY);
	}
	
}
