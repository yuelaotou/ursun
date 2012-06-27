/**
 * 文件名：Unit.java
 * 
 * 创建人：兰硕 - lans@neusoft.com
 * 
 * 创建时间：Aug 19, 2008 5:26:31 PM
 * 
 * 版权所有：东软集团股份有限公司
 */
package cn.ursun.console.app.domain;

import cn.ursun.platform.core.domain.WeeDomain;

/**
 * <p>[描述信息：说明类的基本功能]</p>
 *
 * @author 兰硕 - lans@neusoft.com
 * @version 1.0 Created on Aug 19, 2008 5:26:31 PM
 */
public class Unit extends WeeDomain {

	private String unitName = null;

	private String parentId = null;

	private String description = null;

	private boolean leaf = true;

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String name) {
		this.unitName = name;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isLeaf() {
		return leaf;
	}

	public void setLeaf(boolean isLeaf) {
		this.leaf = isLeaf;
	}
}
