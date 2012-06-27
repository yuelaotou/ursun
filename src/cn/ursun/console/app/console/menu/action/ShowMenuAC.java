/**
 * 文件名：ShowMenuMgrAC.java
 * 
 * 创建人：[郭铜彬] - [guotb@neusoft.com]
 * 
 * 创建时间：Nov 27, 2009 10:20:06 AM
 * 
 * 版权所有：东软软件股份有限公司
 */
package cn.ursun.console.app.console.menu.action;

import java.util.List;

import cn.ursun.console.app.console.menu.bizservice.MenuBS;
import cn.ursun.platform.core.action.WeeAction;
import cn.ursun.platform.ps.domain.TreeNode;

/**
 * <p>
 * 菜单浏览 应用场景：前台系统菜单导航
 * </p>
 * 
 * @author 宋成山 - songchengshan@neusoft.com
 * @version 1.0
 */
public class ShowMenuAC extends WeeAction {

	/**
	 * 业务操作对象
	 */
	private MenuBS menuBS = null;

	/**
	 * 菜单树信息对象
	 */
	private TreeNode rootMenu = null;

	private List<TreeNode> mainMenuList = null;

	private List<TreeNode> childMenuList = null;

	private String menu_id;

	/**
	 * <p>
	 * 查询当前用户可以访问的MENU树
	 * </p>
	 * 
	 * @return
	 * @throws Exception
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-23 下午09:54:50
	 */
	public String queryMenuTree() throws Exception {
		return null;
	}

	public String queryConsoleMenu() {
		rootMenu = menuBS.queryMenuItem();
		return JSON;
	}

	public String queryMainMenuList() {
		mainMenuList = menuBS.queryMainMenuList();
		return JSON;
	}

	public String queryChildMenuListWithMenuID() {
		childMenuList = menuBS.queryChildMenuListWithMenuID(menu_id);
		return JSON;
	}

	/*
	 * public MenuBS getMenuBS() { return menuBS; }
	 */

	public void setMenuBS(MenuBS menuBS) {
		this.menuBS = menuBS;
	}

	public TreeNode getRootMenu() {
		return rootMenu;
	}

	public void setRootMenu(TreeNode rootMenu) {
		this.rootMenu = rootMenu;
	}

	public List<TreeNode> getMainMenuList() {
		return mainMenuList;
	}

	public void setMainMenuList(List<TreeNode> mainMenuList) {
		this.mainMenuList = mainMenuList;
	}

	public List<TreeNode> getChildMenuList() {
		return childMenuList;
	}

	public void setChildMenuList(List<TreeNode> childMenuList) {
		this.childMenuList = childMenuList;
	}

	public String getMenu_id() {
		return menu_id;
	}

	public void setMenu_id(String menu_id) {
		this.menu_id = menu_id;
	}

}
