
/**
 * 文件名：Role.java
 * 
 * 创建人：兰硕 - lans@neusoft.com
 * 
 * 创建时间：Aug 19, 2008 5:16:30 PM
 * 
 * 版权所有：东软集团股份有限公司
 */
package cn.ursun.console.app.domain;

import cn.ursun.platform.core.domain.WeeDomain;

/**
 * <p>[描述信息：说明类的基本功能]</p>
 *
 * @author 兰硕 - lans@neusoft.com
 * @version 1.0 Created on Aug 19, 2008 5:16:30 PM
 */
public class Role extends WeeDomain {
    private String roleId=null;
    
	private String roleName = null;

	private String parentId = null;

	private boolean leaf = true;

	private String description = null;
	
	public String getRoleId() {
		return roleId;
	}

	
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public boolean isLeaf() {
		return leaf;
	}

	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}
}
