/**
 * 文件名: WeeContextLoader.java 创建时间：Aug 15, 2009 3:03:31 PM 创建人：兰硕 版权：东软集团股份有限公司
 */
package cn.ursun.platform.core.spring;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.ServletContext;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextException;
import org.springframework.core.io.Resource;
import org.springframework.util.StringUtils;
import org.springframework.web.context.ConfigurableWebApplicationContext;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.ServletContextResource;

import cn.ursun.platform.core.domain.AppConfig;
import cn.ursun.platform.core.domain.WeeConfig;
import cn.ursun.platform.core.domain.WeePlatformConfig;
import cn.ursun.platform.core.keep.AppConfigHolderProxy;

/**
 * @author 兰硕
 */
public class WeeContextLoader extends ContextLoader {

	protected WebApplicationContext createWebApplicationContext(ServletContext servletContext, ApplicationContext parent)
			throws BeansException {
		Class<?> contextClass = determineContextClass(servletContext);
		if (!WeeWebApplicationContext.class.isAssignableFrom(contextClass)) {
			throw new ApplicationContextException("Custom context class [" + contextClass.getName()
					+ "] is not of type [" + WeeWebApplicationContext.class.getName() + "]");
		}

		WeeWebApplicationContext wac = (WeeWebApplicationContext) BeanUtils.instantiateClass(contextClass);
		wac.setParent(parent);
		wac.setServletContext(servletContext);

		Resource[] configResources = getConfigResources(servletContext);
		if (configResources != null && configResources.length != 0)
			wac.setConfigResources(configResources);

		wac.refresh();
		WeeApplicationContextUtils.getInstance().setApplicationContext(wac);
		return wac;
	}

	@SuppressWarnings("unchecked")
	protected Resource[] getConfigResources(ServletContext servletContext) {
		String configLocation = servletContext.getInitParameter(CONFIG_LOCATION_PARAM);
		Set<Resource> configResourcesSet = new HashSet<Resource>();
		
		// 添加web.xml中的spring配置文件
		if (configLocation != null) {
			String[] configLocations = StringUtils.tokenizeToStringArray(configLocation,
					ConfigurableWebApplicationContext.CONFIG_LOCATION_DELIMITERS);
			for (String configLocationtmp : configLocations) {
				configResourcesSet.add(new ServletContextResource(servletContext, configLocationtmp));
			}
		}

		// 添加Wee组件配置文件
		WeeConfig weeConfig = AppConfigHolderProxy.getInstance().getWeeConfig();
		if (weeConfig!=null&&weeConfig.getSpringConfigResources() != null && weeConfig.getSpringConfigResources().length > 0) {
			CollectionUtils.addAll(configResourcesSet, weeConfig.getSpringConfigResources());
		}
		
		// 添加Wee组件配置文件
		WeePlatformConfig weePlatformConfig = AppConfigHolderProxy.getInstance().getWeePlatformConfig();
		if (weePlatformConfig!=null&&weePlatformConfig.getSpringConfigResources() != null && weePlatformConfig.getSpringConfigResources().length > 0) {
			CollectionUtils.addAll(configResourcesSet, weePlatformConfig.getSpringConfigResources());
		}

		// 添加应用级的Spring配置文件
		Iterator<AppConfig> appConfigs = AppConfigHolderProxy.getInstance().getAppConfigs();
		while (appConfigs.hasNext()) {
			AppConfig ac = appConfigs.next();
			Resource[] configResources = ac.getSpringConfigResources();
			if (configResources != null && configResources.length > 0) {
				CollectionUtils.addAll(configResourcesSet, configResources);
			}
		}
		if (configResourcesSet.size() == 0)
			return null;
		return (Resource[]) configResourcesSet.toArray(new Resource[configResourcesSet.size()]);
	}
}
