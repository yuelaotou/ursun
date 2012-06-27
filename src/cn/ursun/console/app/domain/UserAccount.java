/**
 * 文件名：UserAccount.java
 * 
 * 创建人：兰硕 - lans@neusoft.com
 * 
 * 创建时间：Sep 4, 2008 7:04:30 PM
 * 
 * 版权所有：东软集团股份有限公司
 */
package cn.ursun.console.app.domain;

import cn.ursun.platform.core.domain.WeeDomain;

/**
 * <p>[描述信息：说明类的基本功能]</p>
 *
 * @author 兰硕 - lans@neusoft.com
 * @version 1.0 Created on Sep 4, 2008 7:04:30 PM
 */
public class UserAccount extends WeeDomain {

	private User user = new User();

	private Account account = new Account();

	private String[] roleIds = null;

	private String[] unitIds = null;
	
	

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	/**
	 *
	 * @return 
	 */
	public String[] getRoleIds() {
		return roleIds;
	}

	/**
	 *
	 * @param roleIds
	 */
	public void setRoleIds(String[] roleIds) {
		this.roleIds = roleIds;
	}

	/**
	 *
	 * @return 
	 */
	public String[] getUnitIds() {
		return unitIds;
	}

	/**
	 *
	 * @param unitIds
	 */
	public void setUnitIds(String[] unitIds) {
		this.unitIds = unitIds;
	}
}
