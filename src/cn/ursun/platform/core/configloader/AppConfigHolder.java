/**
 * 文件名: AppConfig.java 创建时间：Aug 15, 2009 1:38:34 PM 创建人：兰硕 版权：东软集团股份有限公司
 */
package cn.ursun.platform.core.configloader;

import java.util.ArrayList;
import java.util.Iterator;

import cn.ursun.platform.core.domain.AppConfig;
import cn.ursun.platform.core.domain.WeeConfig;
import cn.ursun.platform.core.domain.WeePlatformConfig;

/**
 * 用于持有AppConfig实体，并提供一个读取配置信息的Iterator对象。 <br>
 * 这些AppConfig实体，可能由AppConfigLoader调用AppConfig.parse后放入本实例。
 * 
 * @author 兰硕
 */
public class AppConfigHolder {

	private static AppConfigHolder instance = null;

	private ArrayList<AppConfig> appConfigList = new ArrayList<AppConfig>();
	
	private WeeConfig weeConfig=null;

	private WeePlatformConfig weePlatformConfig = null;

	public static AppConfigHolder getInstance() {
		if (instance == null)
			throw new IllegalStateException(
					"AppConfigHolder has not load yet, please initialize it via AppConfigLoader or AppConfigLoaderListener");
		return instance;
	}

	protected AppConfigHolder() {
		instance = this;
	}

	/**
	 * 添加Wee配置类
	 */
	protected void setWeePlatformConfig(WeePlatformConfig weePlatformConfig) {
		this.weePlatformConfig = weePlatformConfig;
	}
	
	public WeePlatformConfig getWeePlatformConfig() {
		return this.weePlatformConfig;
	}

	/**
	 * 提供给同package的应用程序AppConfigLoader调用，加入已经解析过多AppConfig实体。
	 */
	protected void addAppConfig(AppConfig appConfig) {
		appConfigList.add(appConfig);
	}

	public AppConfig getAppConfig(String appName) {
		for (AppConfig ac : appConfigList) {
			if (appName != null && appName.equals(ac.getAppName()))
				return ac;
		}
		return null;
	}

	/**
	 * 获取
	 */
	public synchronized Iterator<AppConfig> getAppConfigs() {
		return appConfigList.iterator();
	}

	
	public void setWeeConfig(WeeConfig weeConfig) {
		this.weeConfig = weeConfig;
	}

	
	public WeeConfig getWeeConfig() {
		return weeConfig;
	}
}
