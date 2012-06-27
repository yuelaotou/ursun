/**
 * 文件名：Resource.java
 * 
 * 创建人：兰硕 - lans@neusoft.com
 * 
 * 创建时间：Aug 19, 2008 5:21:29 PM
 * 
 * 版权所有：东软集团股份有限公司
 */
package cn.ursun.console.app.domain;

import java.util.List;

import cn.ursun.platform.core.domain.WeeDomain;

/**
 * <p>[描述信息：说明类的基本功能]</p>
 *
 * @author 兰硕 - lans@neusoft.com
 * @version 1.0 Created on Aug 19, 2008 5:21:29 PM
 */
public class Resource extends WeeDomain {

	private String resourceId = null;
	
	private String title = null;
	
	private String parentId = null;
	
	private String isLeaf = null;
	
	//private String resourceType = null;

	private String description = null;
	
	private String type = null;

	private List<String> url = null;
	
	private String rid = null;

	
	public String getTitle() {
		return title;
	}

	
	public void setTitle(String title) {
		this.title = title;
	}

	
	
	public String getResourceId() {
		return resourceId;
	}


	
	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}


	
	public String getParentId() {
		return parentId;
	}


	
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}


	
	public String getIsLeaf() {
		return isLeaf;
	}


	
	public void setIsLeaf(String isLeaf) {
		this.isLeaf = isLeaf;
	}


	
/*	public String getResourceType() {
		return resourceType;
	}


	
	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}*/


	public String getDescription() {
		return description;
	}

	
	public void setDescription(String description) {
		this.description = description;
	}


	
	public List<String> getUrl() {
		return url;
	}


	
	public void setUrl(List<String> url) {
		this.url = url;
	}


	
	public String getType() {
		return type;
	}


	
	public void setType(String type) {
		this.type = type;
	}


	
	public String getRid() {
		return rid;
	}


	
	public void setRid(String rid) {
		this.rid = rid;
	}

	
}
