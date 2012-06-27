/**
 * 文件名：User.java
 * 
 * 创建人：兰硕 - lans@neusoft.com
 * 
 * 创建时间：Aug 19, 2008 5:13:56 PM
 * 
 * 版权所有：东软集团股份有限公司
 */
package cn.ursun.console.app.domain;

import java.util.Map;

import cn.ursun.platform.core.domain.WeeDomain;

/**
 * <p>
 * [描述信息：说明类的基本功能]
 * </p>
 * 
 * @author 兰硕 - lans@neusoft.com
 * @version 1.0 Created on Aug 19, 2008 5:13:56 PM
 */
public class User extends WeeDomain {

	private String fullName = null;

	private String sex = null;

	private String description = null;

	private String email = null;

	private String tel = null;

	private String unitId = null;

	/**
	 * 用户扩展信息
	 */
	private Map<String, String[]> extendInfo = null;

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Map<String, String[]> getExtendInfo() {
		return extendInfo;
	}

	public void setExtendInfo(Map<String, String[]> extendInfo) {
		this.extendInfo = extendInfo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

}
