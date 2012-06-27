package cn.ursun.platform.ps.util;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

public class I18nUtil {

	private static I18nUtil instance = null;

	String[] defaultBundle;

	HttpServletRequest request;

	private I18nUtil(HttpServletRequest request) {
		this.request = request;
		this.setDefaultBundle("resource,errorcodes-resource");
	}

	public static I18nUtil getInstance(HttpServletRequest request) {
		if (instance == null) {
			instance = new I18nUtil(request);
		}
		return instance;
	}

	public String getMessage(ResourceBundle resourceBundle, String key, Object[] parameters) {
		List<ResourceBundle> resourceBundleList = new ArrayList<ResourceBundle>();
		if (resourceBundle == null)
			resourceBundleList = getDefaultBundle();
		else
			resourceBundleList.add(resourceBundle);
		if (key != null) {
			for (ResourceBundle rb : resourceBundleList) {
				String message;
				try {
					message = rb.getString(key);
				} catch (RuntimeException e) {
					continue;
				}
				if (parameters != null && parameters.length > 0)
					message = MessageFormat.format(message, parameters);
				return message;
			}
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
		return getMessage("", key, parameters);
	}

	private ResourceBundle getBundle(String resourceBundleName) {
		if (resourceBundleName != null && !"".equals(resourceBundleName))
			return ResourceBundle.getBundle(resourceBundleName, this.request.getLocale());
		return null;
	}

	private List<ResourceBundle> getDefaultBundle() {
		List<ResourceBundle> defaultBundlelist = new ArrayList<ResourceBundle>();
		if (defaultBundle != null)
			for (String bundleName : defaultBundle)
				if (StringUtils.isNotEmpty(bundleName))
					defaultBundlelist.add(ResourceBundle.getBundle(bundleName, this.request.getLocale(), Thread.currentThread().getContextClassLoader()));
		return defaultBundlelist;
	}

	public void setDefaultBundle(String defaultBundles) {
		this.defaultBundle = defaultBundles.split(",");
	}

}
