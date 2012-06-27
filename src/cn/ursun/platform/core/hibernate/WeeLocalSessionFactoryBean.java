/**
 * 文件名: WeeLocalSessionFactoryBean.java 创建时间：Aug 15, 2009 9:50:09 AM 创建人：兰硕 版权：东软集团股份有限公司
 */
package cn.ursun.platform.core.hibernate;

import java.util.Iterator;

import org.apache.commons.lang.ArrayUtils;
import org.hibernate.SessionFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.orm.hibernate3.LocalSessionFactoryBean;

import cn.ursun.platform.core.domain.AppConfig;
import cn.ursun.platform.core.domain.WeeConfig;
import cn.ursun.platform.core.domain.WeePlatformConfig;
import cn.ursun.platform.core.keep.AppConfigHolderProxy;

/**
 * @author 兰硕
 */
public class WeeLocalSessionFactoryBean extends LocalSessionFactoryBean {

	/**
	 * <p>Discription:读取配置文件</p>
	 * Created on 2009-9-1
	 * @author:　宋成山 songchengshan@neusoft.com
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	@Override
	public void setMappingResources(String mappingResources[]) {
		Resource[] mappingLocations = new Resource[mappingResources.length];
		for (int i = 0; i < mappingResources.length; i++)
			mappingLocations[i] = new ClassPathResource(mappingResources[i].trim());
		this.setMappingLocations(mappingLocations);

	}

	/**
	 * <p>Discription:读取配置文件</p>
	 * Created on 2009-9-1
	 * @author:　宋成山 songchengshan@neusoft.com
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void setMappingLocations(Resource mappingLocations[]) {

		// 加载Wee平台的配置文件
		WeeConfig weeConfig = AppConfigHolderProxy.getInstance().getWeeConfig();
		if (weeConfig.getHibernateConfigResources() != null) {
			Resource[] configResources = weeConfig.getHibernateConfigResources();
			mappingLocations = (Resource[]) ArrayUtils.addAll(mappingLocations, configResources);
		}
		
		// 加载Wee平台的配置文件
		WeePlatformConfig weePlatformConfig = AppConfigHolderProxy.getInstance().getWeePlatformConfig();
		if (weePlatformConfig.getHibernateConfigResources() != null) {
			Resource[] configResources = weePlatformConfig.getHibernateConfigResources();
			mappingLocations = (Resource[]) ArrayUtils.addAll(mappingLocations, configResources);
		}

		// 加载应用系统的配置文件
		Iterator<AppConfig> appConfigs = AppConfigHolderProxy.getInstance().getAppConfigs();
		while (appConfigs.hasNext()) {
			AppConfig ac = appConfigs.next();
			if (ac.getHibernateConfigResources() != null) {
				Resource[] configResources = ac.getHibernateConfigResources();
				mappingLocations = (Resource[]) ArrayUtils.addAll(mappingLocations, configResources);
			}
		}

		super.setMappingLocations(mappingLocations);
	}

	protected SessionFactory buildSessionFactory() throws Exception {
		return super.buildSessionFactory();
	}

}
