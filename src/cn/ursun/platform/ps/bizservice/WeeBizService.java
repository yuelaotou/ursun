package cn.ursun.platform.ps.bizservice;

import cn.ursun.platform.core.bizservice.AbstractWeeBizService;
import cn.ursun.platform.ps.bizlog.BizLogger;

/**
 * <p>业务逻辑基类</p>
 *
 * @author 段鹏 - duanp@neusoft.com
 * @version 1.0 Created on Aug 16, 2008 3:12:08 PM
 */
public class WeeBizService extends AbstractWeeBizService {

	private BizLogger bizLogger = BizLogger.getInstance();

	public BizLogger log(String key, String value) {
		return bizLogger.addProperty(key, value);
	}

	public BizLogger log(String key, int value) {
		return bizLogger.addProperty(key, value);
	}

	public BizLogger log(String key, boolean value) {
		return bizLogger.addProperty(key, value);
	}

	public BizLogger log(String key, float value) {
		return bizLogger.addProperty(key, value);
	}

	public BizLogger log(String key, double value) {
		return bizLogger.addProperty(key, value);
	}

	public BizLogger log(String group, String key, String value) {
		return bizLogger.addProperty(group, key, value);

	}

	public BizLogger log(String group, String key, int value) {
		return bizLogger.addProperty(group, key, Integer.toString(value));
	}

	public BizLogger log(String group, String key, boolean value) {
		return bizLogger.addProperty(group, key, Boolean.toString(value));
	}

	public BizLogger log(String group, String key, float value) {
		return bizLogger.addProperty(group, key, Float.toString(value));
	}

	public BizLogger log(String group, String key, double value) {
		return bizLogger.addProperty(group, key, Double.toString(value));
	}

}
