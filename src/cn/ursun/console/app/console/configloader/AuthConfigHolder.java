package cn.ursun.console.app.console.configloader;

import cn.ursun.console.app.domain.AuthConfig;

/**
 * <p>权限管理配置</p>
 * @author 宋成山 - songchengshan@neusoft.com
 * @version 1.0
 */
public class AuthConfigHolder {

	private static AuthConfigHolder instance = null;

	private AuthConfig authConfig = null;

	private AuthConfigHolder() {

	}

	public static AuthConfigHolder getInstance() {
		if (instance == null)
			instance = new AuthConfigHolder();
		return instance;
	}

	public AuthConfig getAuthConfig() {
		return authConfig;
	}

	public void setAuthConfig(AuthConfig authConfig) {
		this.authConfig = authConfig;
	}

}
