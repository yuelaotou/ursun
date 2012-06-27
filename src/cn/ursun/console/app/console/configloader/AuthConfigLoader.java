package cn.ursun.console.app.console.configloader;

import org.apache.commons.digester.Digester;
import org.apache.commons.digester.RuleSet;
import org.apache.commons.digester.RuleSetBase;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import cn.ursun.console.app.domain.AuthConfig;
import cn.ursun.console.app.domain.UserExtendColumn;
import cn.ursun.platform.core.component.AbstractComponent;

/**
 * <p>权限管理配置加载器</p>
 * @author 宋成山 - songchengshan@neusoft.com
 * @version 1.0
 */
public class AuthConfigLoader extends AbstractComponent {

	private Resource authConfigResource;

	public void execute() {
		AuthConfig authConfig = parse(authConfigResource);
		if (authConfig != null)
			AuthConfigHolder.getInstance().setAuthConfig(authConfig);
	}

	private AuthConfig parse(Resource resource) {
		AuthConfig authConf = null;
		Digester d = new Digester();
		d.setNamespaceAware(false);
		d.setUseContextClassLoader(false);
		d.setValidating(false);
		d.addRuleSet(createRuleSet());
		try {
			logger.info("Loading Auth Config [" + resource.getURL() + "] ... ");
			authConf = (AuthConfig) d.parse(resource.getInputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return authConf;
	}

	private static RuleSet createRuleSet() {
		return new RuleSetBase() {

			public void addRuleInstances(final Digester d) {
				d.addObjectCreate("auth-config", AuthConfig.class);
				d.addObjectCreate("auth-config/user-extend-info/column", UserExtendColumn.class);
				d.addSetProperties("auth-config/user-extend-info/column", "index", "index");
				d.addSetProperties("auth-config/user-extend-info/column", "name", "name");
				d.addSetProperties("auth-config/user-extend-info/column", "label", "label");
				d.addSetProperties("auth-config/user-extend-info/column", "type", "type");
				d.addSetProperties("auth-config/user-extend-info/column", "minlength", "minlength");
				d.addSetProperties("auth-config/user-extend-info/column", "maxlength", "maxlength");
				d.addSetProperties("auth-config/user-extend-info/column", "format", "format");
				d.addSetProperties("auth-config/user-extend-info/column", "require", "require");
				d.addSetProperties("auth-config/user-extend-info/column", "tableName", "tableName");
				d.addSetProperties("auth-config/user-extend-info/column", "codeName", "codeName");
				d.addSetProperties("auth-config/user-extend-info/column", "codeValue", "codeValue");
				d.addSetProperties("auth-config/user-extend-info/column", "relateColumn", "relateColumn");
				d.addSetProperties("auth-config/user-extend-info/column", "filterName", "filterName");
				d.addSetProperties("auth-config/user-extend-info/column", "filterValue", "filterValue");
				d.addSetProperties("auth-config/user-extend-info/column", "multiple", "multiple");
				d.addSetProperties("auth-config/user-extend-info/column", "isquery", "isquery");
				d.addSetProperties("auth-config/user-extend-info/column", "operate", "operate");
				d.addSetNext("auth-config/user-extend-info/column", "addUserExpandInfo");
			}
		};
	}

	public void setAuthConfigResource(Resource authConfigResource) {
		this.authConfigResource = authConfigResource;
	}

	public static void main(String[] args) {
		AuthConfigLoader ss = new AuthConfigLoader();
		ss
				.setAuthConfigResource(new FileSystemResource(
						"D:\\work\\workspace1.5\\sipo.pubsearch\\sysmgr\\com\\neusoft\\sipo\\pubsearch\\sysmgr\\config\\auth-config.xml"));
		ss.execute();

	}
}
