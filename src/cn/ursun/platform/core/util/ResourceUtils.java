/**
 * 文件名：ResourceUtils.java
 * 
 * 创建人：段鹏 - duanp@neusoft.com
 * 
 * 创建时间：2008-12-31 下午02:29:37
 * 
 * 版权所有：东软集团股份有限公司
 */
package cn.ursun.platform.core.util;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * <p>[描述信息：说明类的基本功能]</p>
 *
 * @author 段鹏- duanp@neusoft.com
 * @version 1.0 Created on 2008-12-31 下午02:29:37
 */
public class ResourceUtils {

	private static ResourceUtils instance = null;

	ResourceBundle errorResourceBundle = null;

	ResourceBundle configBundle = null;

	private ResourceUtils() {

	}

	public static ResourceUtils getInstance() {
		if (instance == null) {
			instance = new ResourceUtils();
		}
		return instance;
	}

	/**
	 * <p>错误信息句柄</p>
	 * 
	 * @param errorResourceBundle
	 * @author: 段鹏 - duanp@neusoft.com 
	 * @date: Created on 2009-3-27 上午09:49:13
	 */
	public void setErrorResourceBundle(String errorResourceBundle) {
		this.errorResourceBundle = ResourceBundle.getBundle(errorResourceBundle, Locale.getDefault());
	}

	public String getErrorMessage(String key, Object[] parameters) {
		if (key != null) {
			String message = errorResourceBundle.getString(key);
			message = MessageFormat.format(message, parameters);
			return message;
		}
		return null;
	}

	/**
	 * <p>获得错误提示信息</p>
	 * 
	 * @param key
	 * @return
	 * @author: 段鹏 - duanp@neusoft.com 
	 * @date: Created on 2009-3-27 上午09:49:40
	 */
	public String getErrorMessage(String key) {
		return getErrorMessage(key, null);
	}

	/**
	 * <p>查询信息</p>
	 * 
	 * @param resourceBundle
	 * @param key
	 * @param parameters
	 * @return
	 * @author: 段鹏 - duanp@neusoft.com 
	 * @date: Created on 2009-3-27 上午09:50:05
	 */
	public String getMessage(ResourceBundle resourceBundle, String key, Object[] parameters) {
		if (resourceBundle == null)
			resourceBundle = getBundle(null);
		if (key != null) {
			String message = resourceBundle.getString(key);
			if (parameters != null && parameters.length > 0)
				message = MessageFormat.format(message, parameters);
			return message;
		}
		return null;
	}

	public String getMessage(ResourceBundle resourceBundle, String key) {
		return getMessage(resourceBundle, key, null);
	}

	public String getMessage(String resourceBundle, String key) {
		return getMessage(getBundle(resourceBundle), key, null);
	}

	public String getMessage(String resourceBundle, String key, Object[] parameters) {
		return getMessage(getBundle(resourceBundle), key, parameters);
	}

	public String getMessage(String key) {
		return getMessage("", key);
	}

	public String getMessage(String key, Object[] parameters) {
		return getMessage("", key, null);
	}

	private ResourceBundle getBundle(String resourceBundle) {
		ResourceBundle rb = null;
		if (resourceBundle == null || "".equals(resourceBundle))
			rb = configBundle;
		else
			rb = ResourceBundle.getBundle(resourceBundle, Locale.getDefault());
		return rb;
	}

	/**
	 *
	 * @param configBundle
	 */
	public void setConfigBundle(String configBundle) {
		this.configBundle = ResourceBundle.getBundle(configBundle, Locale.getDefault());
	}

}
