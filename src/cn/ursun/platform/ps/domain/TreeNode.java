package cn.ursun.platform.ps.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * <p> Title: [名称]</p>
 * <p> Description: [描述]</p>
 * <p> Created on 2009-10-15</p>
 * <p> Copyright: Copyright (c) 2008</p>
 * <p> Company: 东软软件股份有限公司</p>
 * @author 宋成山 songchengshan@neusoft.com
 * @version 1.0
 */
public class TreeNode {

	/** NOTE= we can also add custom attributes here.
	 This may then also be used in the onActivate(); onSelect(); or onLazyRead() callbacks.*/
	private String title = null; // (required) Displayed name of the node (html is allowed here)

	private String key = null; // May be used with activate(); select(); find(); ...

	private boolean isFolder = false; // Use a folder icon. Also the node is expandable but not selectable.

	/**
	 * Call onLazyRead(); when the node is expanded for the first time to allow for
	 * delayed creation of children.
	 * */
	private boolean isLazy = false;

	private String tooltip = null; // Show this popup text.

	private String addClass = null; // Class name added to the node's span tag. ?

	/**
	 *  (deprecated= consider using addClass) Use a custom image (filename relative to
	 *  tree.options.imagePath). 'null' for default icon; 'false' for no icon.
	 * */
	private String icon = null;

	private boolean activate = false; // Initial active status.

	private boolean focus = false; // Initial focused status.

	private boolean expand = false; // Initial expanded status.

	private boolean select = false; // Initial selected status.

	private boolean hideCheckbox = false; // Suppress checkbox for this node.

	private boolean unselectable = false; // Prevent selection.

	private String url = null;
	
	private String parentId=null;

	/** The following attributes are only valid if passed to some functions=
	 *  Array of child nodes.
	 * */
	private List<TreeNode> children = null;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public boolean getIsFolder() {
		return isFolder;
	}

	public void setIsFolder(boolean isFolder) {
		this.isFolder = isFolder;
	}

	public boolean getIsLazy() {
		return isLazy;
	}

	public void setIsLazy(boolean isLazy) {
		this.isLazy = isLazy;
	}

	public String getTooltip() {
		return tooltip;
	}

	public void setTooltip(String tooltip) {
		this.tooltip = tooltip;
	}

	public String getAddClass() {
		return addClass;
	}

	public void setAddClass(String addClass) {
		this.addClass = addClass;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public boolean getActivate() {
		return activate;
	}

	public void setActivate(boolean activate) {
		this.activate = activate;
	}

	public boolean getFocus() {
		return focus;
	}

	public void setFocus(boolean focus) {
		this.focus = focus;
	}

	public boolean getExpand() {
		return expand;
	}

	public void setExpand(boolean expand) {
		this.expand = expand;
	}

	public boolean getSelect() {
		return select;
	}

	public void setSelect(boolean select) {
		this.select = select;
	}

	public boolean getHideCheckbox() {
		return hideCheckbox;
	}

	public void setHideCheckbox(boolean hideCheckbox) {
		this.hideCheckbox = hideCheckbox;
	}

	public boolean getUnselectable() {
		return unselectable;
	}

	public void setUnselectable(boolean unselectable) {
		this.unselectable = unselectable;
	}

	public List<TreeNode> getChildren() {
		return children;
	}

	public void setChildren(List<TreeNode> children) {
		this.children = children;
	}

	public void addChildren(TreeNode childrenNode) {
		if (this.children == null) {
			this.children = new ArrayList<TreeNode>();
		}
		this.children.add(childrenNode);
	}

	
	public String getUrl() {
		return url;
	}

	
	public void setUrl(String url) {
		this.url = url;
	}

	
	public String getParentId() {
		return parentId;
	}

	
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

}
