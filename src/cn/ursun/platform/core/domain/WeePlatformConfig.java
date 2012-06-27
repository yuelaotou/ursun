package cn.ursun.platform.core.domain;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.springframework.core.io.Resource;


/**
 * <p> Title: wee配置文件</p>
 * <p> Description: [描述]</p>
 * <p> Created on 2009-9-1</p>
 * <p> Copyright: Copyright (c) 2008</p>
 * <p> Company: 东软软件股份有限公司</p>
 * @author 宋成山 songchengshan@neusoft.com
 * @version 1.0
 */
public class WeePlatformConfig implements Serializable {

	private Map<String,ComponentConfig> components = null;

	private String strutsConfig = null;

	private Resource[] springConfigResources = null;

	private Resource[] hibernateConfigResources = null;

	public WeePlatformConfig() {
	}

	/**
	 * <p>Discription:获取struts资源文件</p>
	 * Created on 2009-8-28
	 * @author: 宋成山 songchengshan@neusoft.com
	 * @update: [日期YYYY-MM-DD] [更改人姓名]
	 */
	public String getStrutsConfig() {
		return this.strutsConfig;
	}

	public void setStrutsConfig(String strutsConfig) {
		this.strutsConfig = strutsConfig;
	}

	public void addComponent(ComponentConfig component) {
		if (components == null) {
			components = new HashMap<String,ComponentConfig>();
		}
		components.put(component.getName(),component);
	}

	public Map<String,ComponentConfig> getComponents() {
		return components;
	}

	public void setComponents(Map<String,ComponentConfig> components) {
		this.components = components;
	}

	
	public Resource[] getSpringConfigResources() {
		return springConfigResources;
	}

	
	public void setSpringConfigResources(Resource[] springConfigResources) {
		this.springConfigResources = springConfigResources;
	}

	
	public Resource[] getHibernateConfigResources() {
		return hibernateConfigResources;
	}

	
	public void setHibernateConfigResources(Resource[] hibernateConfigResources) {
		this.hibernateConfigResources = hibernateConfigResources;
	}

}
