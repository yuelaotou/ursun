/**
 * 文件名：BizLogImpl.java
 * 
 * 创建人：兰硕 - lans@neusoft.com
 * 
 * 创建时间：Nov 21, 2008 1:35:51 AM
 * 
 * 版权所有：东软集团股份有限公司
 */
package cn.ursun.platform.ps.bizlog;

import java.util.Date;

/**
 * <p>[描述信息：说明类的基本功能]</p>
 *
 * @author 兰硕 - lans@neusoft.com
 * @version 1.0 Created on Nov 21, 2008 1:35:51 AM
 */
public class IBizLogImpl implements IBizLog {

	private String[] moduleLevel = null;

	private String operator = null;

	private String operatorName = null;

	private String sex = null;

	private String department = null;

	private Date operationDate = null;

	// private String operationType = null;

	private String accessIP = null;

	private String description = null;

	private String xmlData = null;

	private String bizCode = null;

	private String funcCode = null;

	private boolean isAnonymousUser = true;

	private String roleName = null;

	private String loginName = null;

	/**
	 *
	 * @return 
	 */
	public String[] getModuleLevel() {
		return moduleLevel;
	}

	/**
	 *
	 * @param loginName
	 */
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	/**
	 *
	 * @return 
	 */
	public String getModuleLevel(int i) {
		if (i >= moduleLevel.length)
			return null;
		return moduleLevel[i];
	}

	/**
	 *
	 * @param moduleLevel
	 */
	public void setModuleLevel(String[] moduleLevel) {
		this.moduleLevel = moduleLevel;
	}

	public String getAccessIP() {
		return accessIP;
	}

	public String getDepartment() {
		return department;
	}

	public String getDescription() {
		return description;
	}

	public Date getOperationDate() {
		return operationDate;
	}

	// public String getOperationType() {
	// return operationType;
	// }

	public String getOperator() {
		return operator;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public String getSex() {
		return sex;
	}

	public String getXmlData() {
		return xmlData;
	}

	public void setAccessIP(String accessIP) {
		this.accessIP = accessIP;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setOperationDate(Date operationDate) {
		this.operationDate = operationDate;
	}

	// public void setOperationType(String operationType) {
	// this.operationType = operationType;
	// }

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public void setXmlData(String xmlData) {
		this.xmlData = xmlData;
	}

	public String getBizCode() {
		return bizCode;
	}

	public void setBizCode(String bizCode) {
		this.bizCode = bizCode;
	}

	public String getFuncCode() {
		return funcCode;
	}

	public int getModuleLevelCount() {
		return moduleLevel == null ? 0 : moduleLevel.length;
	}

	/**
	 *
	 * @param funcCode
	 */
	public void setFuncCode(String funcCode) {
		this.funcCode = funcCode;
	}

	public String getLoginName() {
		return this.loginName;
	}

	public boolean isAnonymousUser() {
		return isAnonymousUser;
	}

	public void setIsAnonymousUser(boolean isAnonymousUser) {
		this.isAnonymousUser = isAnonymousUser;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleName() {
		return this.roleName;
	}

}
