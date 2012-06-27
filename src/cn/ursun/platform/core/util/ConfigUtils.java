/**
 * 文件名：ConfigUtils.java
 * 
 * 创建人：段鹏 - duanp@neusoft.com
 * 
 * 创建时间：2009-3-27 上午09:40:13
 * 
 * 版权所有：东软集团股份有限公司
 */
package cn.ursun.platform.core.util;

import java.util.ResourceBundle;

import cn.ursun.platform.core.util.ResourceUtils;


/**
 * <p>查询参数参数配置</p>
 *
 * @author 段鹏- duanp@neusoft.com
 * @version 1.0 Created on 2009-3-27 上午09:40:13
 */
public class ConfigUtils {

	private static ConfigUtils instance = null;

	public static final String DEFAULT_SERVER = "default";

	private String concatServerInfo(String key) {
		String serverName = ServerUtils.getWeblogicServerName();
		if (serverName != null)
			return serverName + "." + key;

		return DEFAULT_SERVER + "." + key;
	}

	private ConfigUtils() {

	}

	public static ConfigUtils getInstance() {
		if (instance == null)
			instance = new ConfigUtils();
		return instance;
	}

	/**
	 * @param resourceBundle
	 * @param key
	 * @param parameters
	 * @return
	 * @see cn.ursun.platform.util.ResourceUtils#getMessage(java.util.ResourceBundle, java.lang.String, java.lang.Object[])
	 */
	public String getMessage(ResourceBundle resourceBundle, String key, Object[] parameters) {

		return ResourceUtils.getInstance().getMessage(resourceBundle, concatServerInfo(key), parameters);
	}

	/**
	 * @param resourceBundle
	 * @param key
	 * @return
	 * @see cn.ursun.platform.util.ResourceUtils#getMessage(java.util.ResourceBundle, java.lang.String)
	 */
	public String getMessage(ResourceBundle resourceBundle, String key) {
		return ResourceUtils.getInstance().getMessage(resourceBundle, concatServerInfo(key));
	}

	/**
	 * @param key
	 * @param parameters
	 * @return
	 * @see cn.ursun.platform.util.ResourceUtils#getMessage(java.lang.String, java.lang.Object[])
	 */
	public String getMessage(String key, Object[] parameters) {
		return ResourceUtils.getInstance().getMessage(concatServerInfo(key), parameters);
	}

	/**
	 * @param resourceBundle
	 * @param key
	 * @param parameters
	 * @return
	 * @see cn.ursun.platform.util.ResourceUtils#getMessage(java.lang.String, java.lang.String, java.lang.Object[])
	 */
	public String getMessage(String resourceBundle, String key, Object[] parameters) {
		return ResourceUtils.getInstance().getMessage(resourceBundle, concatServerInfo(key), parameters);
	}

	/**
	 * @param resourceBundle
	 * @param key
	 * @return
	 * @see cn.ursun.platform.util.ResourceUtils#getMessage(java.lang.String, java.lang.String)
	 */
	public String getMessage(String resourceBundle, String key) {
		return ResourceUtils.getInstance().getMessage(resourceBundle, concatServerInfo(key));
	}

	/**
	 * @param key
	 * @return
	 * @see cn.ursun.platform.util.ResourceUtils#getMessage(java.lang.String)
	 */
	public String getMessage(String key) {
		return ResourceUtils.getInstance().getMessage(concatServerInfo(key));
	}
}
