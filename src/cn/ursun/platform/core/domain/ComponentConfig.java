package cn.ursun.platform.core.domain;

/**
 * <p> Title:组件配置</p>
 * <p> Description: [描述]</p>
 * <p> Created on 2009-9-15</p>
 * <p> Copyright: Copyright (c) 2008</p>
 * <p> Company: 东软软件股份有限公司</p>
 * @author 宋成山 songchengshan@neusoft.com
 * @version 1.0
 */
public class ComponentConfig {

	private String name = null;

	private String isEnabled = null;

	private String description = null;

	private String springFile = null;
	
	private String strutsConfigFile = null;

	private String hiberanteConfigFile = null;

	private String depends = null;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIsEnabled() {
		return isEnabled;
	}

	public void setIsEnabled(String isEnabled) {
		this.isEnabled = isEnabled;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStrutsConfigFile() {
		return strutsConfigFile;
	}

	public void setStrutsConfigFile(String strutsConfigFile) {
		this.strutsConfigFile = strutsConfigFile;
	}

	public String getHiberanteConfigFile() {
		return hiberanteConfigFile;
	}

	public void setHiberanteConfigFile(String hiberanteConfigFile) {
		this.hiberanteConfigFile = hiberanteConfigFile;
	}

	public String getDepends() {
		return depends;
	}

	public void setDepends(String depends) {
		this.depends = depends;
	}

	
	public String getSpringFile() {
		return springFile;
	}

	
	public void setSpringFile(String springFile) {
		this.springFile = springFile;
	}

}
