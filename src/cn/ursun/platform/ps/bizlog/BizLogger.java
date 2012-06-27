/**
 * 文件名：BizLog.java
 * 
 * 创建人：兰硕 - lans@neusoft.com
 * 
 * 创建时间：Nov 20, 2008 11:55:53 PM
 * 
 * 版权所有：东软集团股份有限公司
 */
package cn.ursun.platform.ps.bizlog;

import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;

/**
 * <p>[描述信息：说明类的基本功能]</p>
 *
 * @author 兰硕 - lans@neusoft.com
 * @version 1.0 Created on Nov 20, 2008 11:55:53 PM
 */
public class BizLogger {

	private static BizLogger instance = null;

	private ThreadLocal<Map<String, Map<String, String>>> logInfo = new ThreadLocal<Map<String, Map<String, String>>>();

	private ThreadLocal<IBizLog> bizLog = new ThreadLocal<IBizLog>();

	private BizLogger() {
	}

	public static BizLogger getInstance() {
		if (instance == null)
			instance = new BizLogger();
		return instance;
	}

	public BizLogger addProperty(String key, String value) {
		return addProperty("DEFAULT GROUP", key, value);
	}

	public BizLogger addProperty(String key, int value) {
		return addProperty("DEFAULT GROUP", key, value);
	}

	public BizLogger addProperty(String key, boolean value) {
		return addProperty("DEFAULT GROUP", key, value);
	}

	public BizLogger addProperty(String key, float value) {
		return addProperty("DEFAULT GROUP", key, value);
	}

	public BizLogger addProperty(String key, double value) {
		return addProperty("DEFAULT GROUP", key, value);
	}

	public BizLogger addProperty(String group, String key, String value) {
		if (!isGroupExisted(group)) {
			newGroup(group);
		}
		getGroup(group).put(key, value);
		return this;
	}

	public BizLogger addProperty(String group, String key, int value) {
		return addProperty(group, key, Integer.toString(value));
	}

	public BizLogger addProperty(String group, String key, boolean value) {
		return addProperty(group, key, Boolean.toString(value));
	}

	public BizLogger addProperty(String group, String key, float value) {
		return addProperty(group, key, Float.toString(value));
	}

	public BizLogger addProperty(String group, String key, double value) {
		return addProperty(group, key, Double.toString(value));
	}

	public void commit() {
		getLogInfo().clear();
		bizLog.set(null);
	}

	public IBizLog getBizLog() {
		return bizLog.get();
	}

	public boolean isGroupExisted(String group) {
		return getLogInfo().containsKey(group);
	}

	public void setAccessIP(String accessIP) {
		getBizLogImpl().setAccessIP(accessIP);
	}

	public void setDepartment(String department) {
		getBizLogImpl().setDepartment(department);
	}

	public void setDescription(String description) {
		getBizLogImpl().setDescription(description);
	}

	public void setModuleLevel(String[] moduleLevel) {
		getBizLogImpl().setModuleLevel(moduleLevel);
	}
	
	public void setOperationDate(Date operationDate) {
		getBizLogImpl().setOperationDate(operationDate);
	}
	

	public void setIsAnonymousUser(boolean isAnonymousUser) {
		getBizLogImpl().setIsAnonymousUser(isAnonymousUser);
	}

	
	public void setRoleName(String roleName) {
		getBizLogImpl().setRoleName(roleName);
	}

	// public void setOperationType(String operationType) {
	// getBizLogImpl().setOperationType(operationType);
	// }

	public void setOperator(String operator) {
		getBizLogImpl().setOperator(operator);
	}

	public void setOperatorName(String operatorName) {
		getBizLogImpl().setOperatorName(operatorName);
	}

	public void setSex(String sex) {
		getBizLogImpl().setSex(sex);
	}

	public void setBizCode(String bizCode) {
		getBizLogImpl().setBizCode(bizCode);
	}

	public void setFuncCode(String funcCode) {
		getBizLogImpl().setFuncCode(funcCode);
	}

	public void setLoginName(String loginName) {
		getBizLogImpl().setLoginName(loginName);
	}

	public void setXmlData() {
		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		sb.append("<log>");
		sb.append("<description>").append(
				StringUtils.defaultString(bizLog.get().getDescription(), "").replaceAll("<", "&lt;")).append(
				"</description>");
		Iterator<String> groupKeyItor = getLogInfo().keySet().iterator();
		while (groupKeyItor.hasNext()) {
			String group = groupKeyItor.next();
			sb.append("<group name=\"" + StringUtils.defaultString(group, "").replaceAll("<", "&lt;") + "\">");
			Iterator<String> propertyKeyItor = getGroup(group).keySet().iterator();
			while (propertyKeyItor.hasNext()) {
				String property = propertyKeyItor.next();
				String value = StringUtils.defaultString(getGroup(group).get(property), "").replaceAll("&", "&amp;")
						.replaceAll("<", "&lt;").replaceAll("\"", "&quot;");// .replaceAll("\\\"", "\\\\\\\"");
				sb.append("<property name=\"" + property.replaceAll("<", "&lt;") + "\" value=\"" + value + "\"/>");
			}
			sb.append("</group>");
		}
		sb.append("</log>");
		getBizLogImpl().setXmlData(sb.toString());
	}

	protected IBizLogImpl getBizLogImpl() {
		if (bizLog.get() == null) {
			bizLog.set(new IBizLogImpl());
		}
		return (IBizLogImpl) bizLog.get();
	}

	protected Map<String, String> getGroup(String group) {
		return getLogInfo().get(group);
	}

	protected void newGroup(String group) {
		Validate.notNull(group, "group required");
		Map<String, String> propertyMap = new LinkedHashMap<String, String>();
		getLogInfo().put(group, propertyMap);
	}

	protected Map<String, Map<String, String>> getLogInfo() {
		if (logInfo.get() == null) {
			logInfo.set(new LinkedHashMap<String, Map<String, String>>());
		}
		return logInfo.get();
	}
}
