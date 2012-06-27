/**
 * 文件名：MenuComponent.java
 * 
 * 创建人：兰硕 - lans@neusoft.com
 * 
 * 创建时间：Sep 22, 2008 9:53:32 PM
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
 * @version 1.0 Created on Sep 22, 2008 9:53:32 PM
 */
public class Menu extends WeeDomain {

	public static enum TargetType {
		DEFAULT, _BLANK, _SELF, _PARENT
	}

	private String resourceId = null;

	private String resourceName = null;

	private String name = null;

	private TargetType targetType = TargetType.DEFAULT;

	private String description = null;

	private String image = null;

	private String imageOver = null;

	private int position = 0;

	private String parentId = null;

	private boolean folder = false;

	private boolean root = false;

	private String url = null;

	private String jsId = null;
	
	private String sequence=null;

	private List<Menu> childMenus = null;

	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public TargetType getTargetType() {
		return targetType;
	}

	public void setTargetType(TargetType targetType) {
		this.targetType = targetType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public boolean isRoot() {
		return root;
	}

	public void setRoot(boolean root) {
		this.root = root;
	}

	public List<Menu> getChildMenus() {
		return childMenus;
	}

	public void setChildMenus(List<Menu> childMenus) {
		this.childMenus = childMenus;
	}

	/**
	 *
	 * @return 
	 */
	public String getUrl() {
		return url;
	}

	/**
	 *
	 * @param url
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 *
	 * @return 
	 */
	public String getImageOver() {
		return imageOver;
	}

	/**
	 *
	 * @param imageOver
	 */
	public void setImageOver(String imageOver) {
		this.imageOver = imageOver;
	}

	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	public String getJsId() {
		return jsId;
	}

	public void setJsId(String jsId) {
		this.jsId = jsId;
	}

	
	public String getSequence() {
		return sequence;
	}

	
	public void setSequence(String sequence) {
		this.sequence = sequence;
	}

	
	public boolean isFolder() {
		return folder;
	}

	
	public void setFolder(boolean folder) {
		this.folder = folder;
	}

}
