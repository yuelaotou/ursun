/**
 * 文件名：BizLog.java
 * 
 * 创建人：兰硕 - lans@neusoft.com
 * 
 * 创建时间：Nov 21, 2008 12:36:01 AM
 * 
 * 版权所有：东软集团股份有限公司
 */
package cn.ursun.platform.ps.bizlog;

import java.util.Date;

/**
 * <p>[描述信息：说明类的基本功能]</p>
 *
 * @author 兰硕 - lans@neusoft.com
 * @version 1.0 Created on Nov 21, 2008 12:36:01 AM
 */
public interface IBizLog {

	public int getModuleLevelCount();

	public String getModuleLevel(int i);

	public String getOperator();

	public String getOperatorName();

	public String getSex();

	public String getDepartment();

	public Date getOperationDate();

	// public String getOperationType();

	public String getAccessIP();

	public String getDescription();

	public String getXmlData();

	public String getBizCode();

	public String getFuncCode();

	public String getLoginName();

	public boolean isAnonymousUser();

	public String getRoleName();

	 public static enum OperationType {

		USE, SEARCH, MAINTAIN, LOGIN, LOGOUT, IMPORT, EXPORT
	}
}
