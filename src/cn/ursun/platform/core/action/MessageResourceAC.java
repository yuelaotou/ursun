/**
 * 文件名：MessageResourceAC.java
 * 
 * 创建人：兰硕 - lans@neusoft.com
 * 
 * 创建时间：Aug 11, 2008 10:33:59 PM
 * 
 * 版权所有：东软集团股份有限公司
 */
package cn.ursun.platform.core.action;


/**
 * 获取国际化信息的Action，支持从前台传入国际化配置名称以及默认值，获取国际化配置名称对应的内容信息。
 * 
 * @author 兰硕 - lans@neusoft.com
 */
public class MessageResourceAC extends WeeAction {

	private String key = null;

	private String defaultValue = null;

	private String value = null;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public String getValue() {
		return value;
	}

	public String getValueThroughtGeneralMode() {
		return null;
	}

	public String getValueThroughtAjaxMode() {
		return null;
	}

	protected String get(String name, String dftValue) {
		return getText(name, dftValue);
	}

	protected String get(String name) {
		return getText(name, "");
	}
}
