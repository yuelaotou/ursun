package cn.ursun.console.app.domain;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.Validate;

/**
 * <p>权限管理配置信息</p>
 * @author 宋成山 - songchengshan@neusoft.com
 * @version 1.0
 */
public class AuthConfig {

	private Map<String, UserExtendColumn> userExtendInfo = null;

	public void put(UserExtendColumn expandInfo) {
		userExtendInfo.put(expandInfo.getName(), expandInfo);
	}

	public UserExtendColumn get(Object key) {
		return userExtendInfo.get(key);
	}

	public void addUserExpandInfo(UserExtendColumn column) {
		if (userExtendInfo == null) {
			userExtendInfo = new HashMap<String, UserExtendColumn>();
		}
		if (userExtendInfo.containsKey(column.getName())) {
			throw new RuntimeException("AuthConfig ExtendColumn :Name already exists!");
		} else
			userExtendInfo.put(column.getName(), column);
	}

	public Map<String, UserExtendColumn> getUserExpandInfo() {
		return userExtendInfo;
	}

	public void setUserExpandInfo(Map<String, UserExtendColumn> userExpandInfo) {
		this.userExtendInfo = userExpandInfo;
	}

}
