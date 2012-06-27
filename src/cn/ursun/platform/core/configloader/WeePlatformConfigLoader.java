/**
 * 文件名: AppConfigLoader.java 创建时间：Aug 15, 2009 2:59:57 PM 创建人：宋成山 版权：东软集团股份有限公司
 */
package cn.ursun.platform.core.configloader;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.commons.digester.Digester;
import org.apache.commons.digester.RuleSet;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.util.StringUtils;
import org.springframework.web.context.ConfigurableWebApplicationContext;
import org.springframework.web.context.support.ServletContextResource;

import cn.ursun.platform.core.domain.ComponentConfig;
import cn.ursun.platform.core.domain.WeePlatformConfig;
import cn.ursun.platform.core.util.ClassPathScanner;
import cn.ursun.platform.core.util.CreateRuleSet;
import cn.ursun.platform.core.util.FileFilter;

/**
 * 加载Wee配置文件，将其保存在AppConfigUtils中，供其他模块使用。
 * 
 * @author 宋成山
 */
public class WeePlatformConfigLoader {

	protected static final Log logger = LogFactory
			.getLog(WeePlatformConfigLoader.class);

	public static final String WEE_CONFIG_FILE_PARAM = "WeePlatformConfigFile";

	public static final String WEE_PLATFORM_CONFIG_FILE = "com/neusoft/wee/ps/config/wee-component-config.xml";

	public ServletContext servletContext = null;

	public void initWeeComponentConfig(ServletContext servletContext) {
		this.servletContext = servletContext;
		// 获取Wee配置文件
		String weePlatformConfigPath = servletContext
				.getInitParameter(WEE_CONFIG_FILE_PARAM);
		// 如果Wee配置文件不存在，使用默认值
		if (null == weePlatformConfigPath || "".equals(weePlatformConfigPath)) {
			weePlatformConfigPath = WEE_PLATFORM_CONFIG_FILE;
		}
		// 找到根路径中的配置文件
		Resource weeConfig = new ClassPathResource(weePlatformConfigPath);

		if (!weeConfig.exists()) {
			weeConfig = new ServletContextResource(servletContext,
					weePlatformConfigPath);
		}
		if (weeConfig.exists()) {
			AppConfigHolder u = AppConfigHolder.getInstance();
			WeePlatformConfig ac = parse(weeConfig);
			u.setWeePlatformConfig(ac);
		}
	}

	/**
	 * 根据应用配置文件，解析并构造出AppConfig对象。
	 */
	public WeePlatformConfig parse(Resource resource) {
		WeePlatformConfig wc = null;
		Digester d = new Digester();
		d.setNamespaceAware(false);
		d.setUseContextClassLoader(false);
		d.setValidating(false);
		d.addRuleSet(createRuleSet());
		try {
			logger.info("Loading Wee Platform Config [" + resource.getURL()
					+ "] ... ");
			wc = (WeePlatformConfig) d.parse(resource.getInputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
		mergeConfig(wc);
		return wc;
	}

	/**
	 * 创建解析应用配置信息的规则。
	 */
	private RuleSet createRuleSet() {
		return CreateRuleSet.createPlatformRuleSet();
	}

	private void mergeConfig(WeePlatformConfig wc) {
		StringBuffer strutsConfig = new StringBuffer();
		List<Resource> springConfigResources = new ArrayList<Resource>();
		List<Resource> hibernateConfigResources = new ArrayList<Resource>();
		Map<String, ComponentConfig> components = wc.getComponents();
		Map<String, Boolean> alreadyReadCmp = new HashMap<String, Boolean>();
		for (Iterator<String> it = components.keySet().iterator(); it.hasNext();) {
			String cmpKey = (String) it.next();
			if (!alreadyReadCmp.containsKey(cmpKey)) {
				ComponentConfig cmpConfig = components.get(cmpKey);
				if ("true".equals(cmpConfig.getIsEnabled())) {
					try {
						readCmpConfig(components, strutsConfig,
								springConfigResources,
								hibernateConfigResources, cmpConfig,
								alreadyReadCmp);
					} catch (Exception e) {
						logger.error(e);
					}
					alreadyReadCmp.put(cmpKey, true);
				}
			}
		}
		if (strutsConfig.toString().length() > 1) {
			wc.setStrutsConfig(strutsConfig.toString().substring(1));
		}
		wc.setSpringConfigResources(springConfigResources
				.toArray(new Resource[] {}));
		wc.setHibernateConfigResources(hibernateConfigResources
				.toArray(new Resource[] {}));
	}

	private void readCmpConfig(Map<String, ComponentConfig> components,
			StringBuffer strutsConfig, List<Resource> springConfigResources,
			List<Resource> hibernateConfigResources, ComponentConfig cmpConfig,
			Map<String, Boolean> alreadyReadCmp) throws Exception {

		String[] depends = StringUtils.tokenizeToStringArray(cmpConfig
				.getDepends(),
				ConfigurableWebApplicationContext.CONFIG_LOCATION_DELIMITERS);
		if (depends != null) {
			for (String cmpKey : depends) {
				if (!components.containsKey(cmpKey)) {
					throw new Exception("加载组件失败,组件" + cmpKey + "不存在！");
				}
				if (!alreadyReadCmp.containsKey(cmpKey)) {
					if ("true".equals(components.get(cmpKey).getIsEnabled())) {
						readCmpConfig(components, strutsConfig,
								springConfigResources,
								hibernateConfigResources, components
										.get(cmpKey), alreadyReadCmp);
					} else {
						throw new Exception("加载组件【" + cmpConfig.getName()
								+ "】失败,它依赖的组件【" + cmpKey + "】已被禁用！");
					}
				}
			}
		}
		if (cmpConfig.getStrutsConfigFile() != null && !cmpConfig.equals("")) {
			strutsConfig.append(",").append(cmpConfig.getStrutsConfigFile());
		}

		String[] springFiles = StringUtils.tokenizeToStringArray(cmpConfig
				.getSpringFile(),
				ConfigurableWebApplicationContext.CONFIG_LOCATION_DELIMITERS);
		if (springFiles != null) {
			for (String springFile : springFiles) {
				// 加载ClassPath下的配置文件
				Resource resources = new ClassPathResource(springFile);
				// 加载WebRoot下的配置文件
				if (!resources.exists()) {
					resources = new ServletContextResource(servletContext,
							springFile);
				}
				// 加载jar 包中指定包中的配置文件
				if (!resources.exists()) {
					List<Resource> springConfigArray = new ClassPathScanner()
							.scanFile(springFile, new FileFilter() {

								public boolean accept(String file) {
									return file.endsWith(".xml");
								}
							});
					springConfigResources.addAll(springConfigArray);
					continue;
				}
				if (!resources.exists()) {
					throw new Exception("加载组件【" + cmpConfig.getName()
							+ "】失败,配置路径【" + springFile + "】不存在！");
				}

				if (resources instanceof ClassPathResource) {
					List<Resource> springConfigArray;
					try {
						springConfigArray = new ClassPathScanner().scanFile(
								springFile, new FileFilter() {

									public boolean accept(String file) {
										return file.endsWith(".xml");
									}
								});

						if (springConfigArray == null
								|| springConfigArray.size() == 0) {
							springConfigResources.add(resources);
						} else {
							springConfigResources.addAll(springConfigArray);
						}
					} catch (RuntimeException e) {
						e.printStackTrace();
					}
				} else {
					if (resources.getFile().isFile()) {
						springConfigResources.add(resources);
					} else {
						File confFile = resources.getFile();
						File[] confFiles = confFile
								.listFiles(new FilenameFilter() {

									public boolean accept(File file, String s) {

										return s.endsWith(".xml");
									}
								});
						for (File file : confFiles) {
							springConfigResources.add(new FileSystemResource(
									file));
						}
					}
				}
			}
		}
		String[] hiberanteConfigFiles = StringUtils.tokenizeToStringArray(
				cmpConfig.getHiberanteConfigFile(),
				ConfigurableWebApplicationContext.CONFIG_LOCATION_DELIMITERS);
		if (hiberanteConfigFiles != null) {
			for (String hiberanteConfigFile : hiberanteConfigFiles) {
				List<Resource> hibernateConfigArray = new ClassPathScanner()
						.scanFile(hiberanteConfigFile, new FileFilter() {

							public boolean accept(String file) {
								return file.endsWith(".hbm.xml");
							}
						});
				if (hibernateConfigArray.size() <= 0) {
					Resource res = new ServletContextResource(servletContext,
							hiberanteConfigFile);
					try {
						File confFile = res.getFile();
						if (confFile.isDirectory()) {
							File[] confFiles = confFile
									.listFiles(new FilenameFilter() {

										public boolean accept(File file,
												String s) {

											return s.endsWith(".hbm.xml");
										}
									});
							for (File file : confFiles) {
								hibernateConfigResources
										.add(new FileSystemResource(file));
							}

						} else {
							throw new Exception("加载组件【" + cmpConfig.getName()
									+ "】失败,配置路径【" + hiberanteConfigFile
									+ "】不是目录！");
						}

					} catch (IOException e) {
						throw new Exception("加载组件【" + cmpConfig.getName()
								+ "】失败,配置路径【" + hiberanteConfigFile + "】不存在！",
								e);
					}
				} else {
					hibernateConfigResources.addAll(hibernateConfigArray);
				}
			}
		}
		alreadyReadCmp.put(cmpConfig.getName(), true);
	}

}
