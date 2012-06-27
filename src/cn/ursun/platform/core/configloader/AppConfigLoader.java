/**
 * 文件名: AppConfigLoader.java 创建时间：Aug 15, 2009 2:59:57 PM 创建人：兰硕 版权：东软集团股份有限公司
 */
package cn.ursun.platform.core.configloader;

import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.ServletContext;

import org.apache.commons.digester.Digester;
import org.apache.commons.digester.RuleSet;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.Resource;

import cn.ursun.platform.core.domain.AppConfig;
import cn.ursun.platform.core.util.ClassPathScanner;
import cn.ursun.platform.core.util.CreateRuleSet;
import cn.ursun.platform.core.util.FileFilter;

/**
 * 加载应用配置信息，将其保存在AppConfigUtils中，供其他模块使用。
 * 
 * @author 兰硕
 */
public class AppConfigLoader {

	protected static final Log logger = LogFactory.getLog(AppConfigLoader.class);

	public static final String APP_CONFIG_PATH_ROOT_PARAM = "appConfigPathRoot";

	public static final String APP_CONFIG_PATH_ROOT = "cn/ursun";

	public static final String APP_CONFIG_FILE_PATTERN_PARAM = "appConfigFilePattern";

	public static final String APP_CONFIG_FILE_PATTERN = "app-config.xml";

	public void initAppConfig(ServletContext servletContext) {

		// 获取应用配置信息的根路径
		String appConfigPathRoot = servletContext.getInitParameter(APP_CONFIG_PATH_ROOT_PARAM);
		// 获取根路径中应用配置信息文件的名称模式
		String appConfigPathPattern = servletContext.getInitParameter(APP_CONFIG_FILE_PATTERN_PARAM);
		// 如果根路径不存在，使用默认值
		if (null == appConfigPathRoot || "".equals(appConfigPathRoot)) {
			appConfigPathRoot = APP_CONFIG_PATH_ROOT;
		}

		if (null == appConfigPathPattern || "".equals(appConfigPathPattern)) {
			appConfigPathPattern = APP_CONFIG_FILE_PATTERN;
		}
		final Pattern p = Pattern.compile(appConfigPathPattern);

		// 找到根路径中的配置文件列表
		List<Resource> configResourceArray = new ClassPathScanner().scanFile(appConfigPathRoot, new FileFilter() {

			public boolean accept(String file) {
				return p.matcher(file).matches();
			}

		});
		// 读取配置文件列表，并对配置文件内容进行解析，保存在AppConfigUtils中，供其他内容加载时查询
		if (configResourceArray != null) {
			AppConfigHolder u = AppConfigHolder.getInstance();
			for (Resource appConfigResource : configResourceArray) {

				AppConfig ac = parse(appConfigResource);
				u.addAppConfig(ac);
			}
		}
	}

	/**
	 * 根据应用配置文件，解析并构造出AppConfig对象。
	 */
	public AppConfig parse(Resource resource) {
		AppConfig appConf = null;
		Digester d = new Digester();
		d.setNamespaceAware(false);
		d.setUseContextClassLoader(false);
		d.setValidating(false);
		d.addRuleSet(createRuleSet());
		try {
			logger.info("Loading Application Config [" + resource.getURL() + "] ... ");
			appConf = (AppConfig) d.parse(resource.getInputStream());
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
		return CreateRuleSet.createAppConfigRuleSet();
	}

}
