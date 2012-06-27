package cn.ursun.platform.core.configloader;

import org.apache.commons.digester.Digester;
import org.apache.commons.digester.RuleSet;
import org.apache.commons.digester.RuleSetBase;

import cn.ursun.platform.core.domain.AppConfig;
import cn.ursun.platform.core.domain.ComponentConfig;
import cn.ursun.platform.core.domain.WeeConfig;
import cn.ursun.platform.core.domain.WeePlatformConfig;

public class CreateRuleSet {

	public static RuleSet createAppConfigRuleSet() {
		return new RuleSetBase() {

			public void addRuleInstances(final Digester d) {
				d.addObjectCreate("app-config", AppConfig.class);
				d.addBeanPropertySetter("app-config/publish/name", "appName");
				d.addBeanPropertySetter("app-config/publish/root-path", "rootPath");
				d.addBeanPropertySetter("app-config/config/struts-config-file", "strutsConfig");
				d.addBeanPropertySetter("app-config/config/spring-config-package", "springConfig");
				d.addBeanPropertySetter("app-config/config/hibernate-config-package", "hibernateConfig");
			}
		};
	}

	public static RuleSet createPlatformRuleSet() {
		return new RuleSetBase() {

			public void addRuleInstances(final Digester d) {
				d.addObjectCreate("wee-config", WeePlatformConfig.class);
				d.addObjectCreate("wee-config/component", ComponentConfig.class);
				d.addSetProperties("wee-config/component", "name", "name");
				d.addSetProperties("wee-config/component", "isEnabled", "isEnabled");
				d.addSetProperties("wee-config/component", "description", "description");
				d.addSetProperties("wee-config/component", "depends-on", "depends");
				d.addBeanPropertySetter("wee-config/component/spring_file", "springFile");
				d.addBeanPropertySetter("wee-config/component/struts_file", "strutsConfigFile");
				d.addBeanPropertySetter("wee-config/component/hibernate_config_file", "hiberanteConfigFile");
				d.addSetNext("wee-config/component", "addComponent");
			}
		};
	}

	public static RuleSet createWeeConfigRuleSet() {
		return new RuleSetBase() {

			public void addRuleInstances(final Digester d) {
				d.addObjectCreate("wee-config", WeeConfig.class);
				d.addBeanPropertySetter("wee-config/publish/name", "appName");
				d.addBeanPropertySetter("wee-config/publish/root-path", "rootPath");
				d.addBeanPropertySetter("wee-config/config/struts-config-file", "strutsConfig");
				d.addBeanPropertySetter("wee-config/config/spring-config-package", "springConfig");
				d.addBeanPropertySetter("wee-config/config/hibernate-config-package", "hibernateConfig");
			}
		};
	}
}