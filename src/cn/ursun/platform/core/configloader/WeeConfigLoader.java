/**
 * 文件名: WeeConfigLoader.java 创建时间：Aug 15, 2009 2:59:57 PM 创建人：兰硕 版权：东软集团股份有限公司
 */
package cn.ursun.platform.core.configloader;

import java.util.List;

import javax.servlet.ServletContext;

import org.apache.commons.digester.Digester;
import org.apache.commons.digester.RuleSet;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.context.support.ServletContextResource;

import cn.ursun.platform.core.domain.WeeConfig;
import cn.ursun.platform.core.util.ClassPathScanner;
import cn.ursun.platform.core.util.CreateRuleSet;
import cn.ursun.platform.core.util.FileFilter;

/**
 * 加载应用配置信息，将其保存在WeeConfigUtils中，供其他模块使用。
 * 
 * @author 兰硕
 */
public class WeeConfigLoader {

	protected static final Log logger = LogFactory.getLog(WeeConfigLoader.class);

	public static final String WEE_CONFIG_FILE_PARAM = "WeeConfigFile";

	public static final String WEE_CONFIG_FILE = "cn/ursun/platform/core/config/wee-config.xml";

	public void initWeeConfig(ServletContext servletContext) {

		// 获取应用配置信息的根路径
		String weeConfigPath = servletContext.getInitParameter(WEE_CONFIG_FILE_PARAM);
		// 如果Wee配置文件不存在，使用默认值
		if (null == weeConfigPath || "".equals(weeConfigPath)) {
			weeConfigPath = WEE_CONFIG_FILE;
		}
		// 找到根路径中的配置文件
		Resource weeConfig = new ClassPathResource(weeConfigPath);

		if (!weeConfig.exists()) {
			weeConfig = new ServletContextResource(servletContext, weeConfigPath);
		}
		if (weeConfig.exists()) {
			AppConfigHolder u = new AppConfigHolder();
			WeeConfig ac = parse(weeConfig);
			u.setWeeConfig(ac);
		} else {
			logger.error("ConfigFile [" + weeConfigPath + "] not exis! ");
		}
	}

	/**
	 * 根据应用配置文件，解析并构造出WeeConfig对象。
	 */
	public WeeConfig parse(Resource resource) {
		WeeConfig appConf = null;
		Digester d = new Digester();
		d.setNamespaceAware(false);
		d.setUseContextClassLoader(false);
		d.setValidating(false);
		d.addRuleSet(createRuleSet());
		try {
			logger.info("Loading Wee Config [" + resource.getURL() + "] ... ");
			appConf = (WeeConfig) d.parse(resource.getInputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (appConf != null) {
			if (StringUtils.isNotEmpty(appConf.getSpringConfig())) {
				// 找到根路径中的配置文件列表
				List<Resource> configResourceArray = new ClassPathScanner().scanFile(appConf.getSpringConfig(),
						new FileFilter() {

							public boolean accept(String file) {
								return file.endsWith(".xml");
							}
						});
				appConf.setSpringConfigResources((Resource[]) configResourceArray
						.toArray(new Resource[configResourceArray.size()]));
			}
			if (StringUtils.isNotEmpty(appConf.getHibernateConfig())) {
				// 找到根路径中的配置文件列表
				List<Resource> configResourceArray = new ClassPathScanner().scanFile(appConf.getHibernateConfig(),
						new FileFilter() {

							public boolean accept(String file) {
								return file.endsWith(".hbm.xml");
							}
						});
				appConf.setHibernateConfigResources((Resource[]) configResourceArray
						.toArray(new Resource[configResourceArray.size()]));
			}
		}
		return appConf;
	}

	/**
	 * 创建解析应用配置信息的规则。TODO：可以将规则独立出来，做的更加灵活、可控一些。
	 */
	private static RuleSet createRuleSet() {
		return CreateRuleSet.createWeeConfigRuleSet();
	}

}
