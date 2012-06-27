/**
 * 文件名: AppConfigLoaderListener.java 创建时间：Aug 15, 2009 4:06:42 PM 创建人：兰硕 版权：东软集团股份有限公司
 */
package cn.ursun.platform.core.configloader;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @author 兰硕
 */
public class WeeConfigLoaderListener implements ServletContextListener {

	public void contextInitialized(ServletContextEvent servletContextEvent) {
		// 装载应用程序
		LoadAppProgram LoadAppProgram = new LoadAppProgram();
		LoadAppProgram.setUpAppProgram(servletContextEvent.getServletContext());

		// 装载Wee核心配置
		WeeConfigLoader weeConfigLoader = new WeeConfigLoader();
		weeConfigLoader.initWeeConfig(servletContextEvent.getServletContext());

		// 装载Wee平台配置
		WeePlatformConfigLoader weePlatformConfigLoader = new WeePlatformConfigLoader();
		weePlatformConfigLoader.initWeeComponentConfig(servletContextEvent.getServletContext());

		// 装载应用程序配置
		AppConfigLoader appConfigLoader = new AppConfigLoader();
		appConfigLoader.initAppConfig(servletContextEvent.getServletContext());
	}

	public void contextDestroyed(ServletContextEvent servletContextEvent) {
	}
}
