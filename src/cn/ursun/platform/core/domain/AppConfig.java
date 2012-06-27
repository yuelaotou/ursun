/**
 * 文件名: AppConfig.java 创建时间：Aug 15, 2009 1:42:29 PM 创建人：兰硕 版权：东软集团股份有限公司
 */
package cn.ursun.platform.core.domain;

import java.io.Serializable;

import org.springframework.core.io.Resource;

/**
 * 存储应用配置的实体类。
 * 
 * @author 兰硕
 */
public class AppConfig implements Serializable {

	private String appName = null;

	private String rootPath = null;

	private String strutsConfig = null;

	private String springConfig = null;

	private String hibernateConfig = null;

	private Resource[] springConfigResources = null;

	private Resource[] hibernateConfigResources = null;

	public AppConfig() {
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getRootPath() {
		return rootPath;
	}

	public void setRootPath(String rootPath) {
		this.rootPath = rootPath;
	}

	public String getStrutsConfig() {
		return strutsConfig;
	}

	public void setStrutsConfig(String strutsConfig) {
		this.strutsConfig = strutsConfig;
	}

	public String getSpringConfig() {
		return springConfig;
	}

	public void setSpringConfig(String springConfig) {
		this.springConfig = springConfig;
		
	}

	public String getHibernateConfig() {
		return hibernateConfig;
	}

	public void setHibernateConfig(String hibernateConfig) {
		this.hibernateConfig = hibernateConfig;
	}

	
	/**
	 * <p>Discription:获取spring资源文件</p>
	 * Created on 2009-8-28
	 * @author: 宋成山 songchengshan@neusoft.com
	 * @update: [日期YYYY-MM-DD] [更改人姓名]
	 */
	public Resource[] getSpringConfigResources() {
		return this.springConfigResources;
	}

	/**
	 * <p>Discription:获取hibernate资源文件</p>
	 * Created on 2009-8-28
	 * @author: 宋成山 songchengshan@neusoft.com
	 * @update: [日期YYYY-MM-DD] [更改人姓名]
	 */
	public Resource[] getHibernateConfigResources() {
		return this.hibernateConfigResources;
	}

	
	public void setSpringConfigResources(Resource[] springConfigResources) {
		this.springConfigResources = springConfigResources;
	}

	
	public void setHibernateConfigResources(Resource[] hibernateConfigResources) {
		this.hibernateConfigResources = hibernateConfigResources;
	}

}
