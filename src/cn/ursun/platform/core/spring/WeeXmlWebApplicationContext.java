package cn.ursun.platform.core.spring;

import java.io.IOException;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.core.io.Resource;
import org.springframework.web.context.support.ServletContextResource;
import org.springframework.web.context.support.XmlWebApplicationContext;

/**
 * <p>
 * Title: [名称]
 * </p>
 * <p>
 * Description: [描述]
 * </p>
 * <p>
 * Created on 2009-8-28
 * </p>
 * <p>
 * Copyright: Copyright (c) 2008
 * </p>
 * <p>
 * Company: 东软软件股份有限公司
 * </p>
 * 
 * @author 宋成山 songchengshan@neusoft.com
 * @version 1.0
 */
public class WeeXmlWebApplicationContext extends XmlWebApplicationContext
		implements WeeWebApplicationContext {

	/** Paths to XML configuration files */
	private String[] configLocations;

	/** Paths to XML configuration files */
	private Resource[] configResources;
	/**
	 * Load the bean definitions with the given XmlBeanDefinitionReader.
	 * <p>
	 * The lifecycle of the bean factory is handled by the refreshBeanFactory
	 * method; therefore this method is just supposed to load and/or register
	 * bean definitions.
	 * <p>
	 * Delegates to a ResourcePatternResolver for resolving location patterns
	 * into Resource instances.
	 * 
	 * @throws org.springframework.beans.BeansException
	 *             in case of bean registration errors
	 * @throws java.io.IOException
	 *             if the required XML document isn't found
	 * @see #refreshBeanFactory
	 * @see #getConfigLocations
	 * @see #getResources
	 * @see #getResourcePatternResolver
	 */
	@Override
	protected void loadBeanDefinitions(XmlBeanDefinitionReader reader)
			throws BeansException, IOException {
		Resource configResources[] = getConfigResources();
		if (configResources != null) {
			reader.loadBeanDefinitions(configResources);
		}
	}

	public void setConfigLocations(String[] locations) {
		this.configLocations = new String[locations.length];
		for (int i = 0; i < locations.length; i++) {
			this.configLocations[i] = resolvePath(locations[i]);
		}
	}

	public String[] getConfigLocations() {
		return (this.configLocations != null ? this.configLocations
				: getDefaultConfigLocations());
	}

	public void setConfigResources(Resource[] resources) {
		this.configResources = resources;
	}

	public Resource[] getConfigResources() {
		if (this.configResources != null) {
			return this.configResources;
		} else {
			if (getNamespace() != null) {
				return (new Resource[] { new ServletContextResource(this
						.getServletContext(), "/WEB-INF/" + getNamespace()
						+ ".xml") });
			} else {
				return (new Resource[] { new ServletContextResource(this
						.getServletContext(), "/WEB-INF/applicationContext.xml") });
			}
		}
	}

}
