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

import cn.ursun.console.app.console.menu.bizservice.MenuBS;
import cn.ursun.console.app.domain.Menu;
import cn.ursun.platform.core.action.WeeAction;
import cn.ursun.platform.core.exception.BizException;
import cn.ursun.platform.ps.domain.TreeNode;

/**
 * <p> ShowMenuMgrAC</p>
 * <p> 菜单、资源显示Action</p>
 * <p> Created on Nov 27, 2009</p>
 * <p> Copyright: Copyright (c) 2008</p>
 * <p> Company: 东软软件股份有限公司</p>
 * @author [郭铜彬] - [guotb@neusoft.com]
 * @version 1.0
 */
public class ShowMenuMgrAC extends WeeAction {

	/**
	 * 业务操作对象
	 */
	private MenuBS menuBS = null;

	/**
	 * 菜单树信息对象
	 */
	private TreeNode rootMenu = null;

	/**
	 * 资源树信息对象
	 */
	private TreeNode rootRes = null;
	
	/**
	 * 菜单信息对象
	 */
	private Menu menu=null;
	
	/**
	 * <p>初始化转向菜单树操作页面</p>
	 * @return
	 * @throws BizException
	 * @author: 李志伟 - li.zhw@neusoft.com
	 * @data: Create on Dec 14, 2009 9:37:39 AM
	 */
	public String init() throws BizException {
		return "menuMsg";
	}
	/**
	 * <p>获取全部节点的菜单树</p>
	 * <p>应用场景：系统菜单信息维护</p>
	 * <p>调用步骤：调用MenuBS.queryMenuTree()方法，返回TreeNode对象</p>
	 * Created on Nov 27, 2009
	 * @author: [郭铜彬] - [guotb@neusoft.com]
	 * @update: [日期YYYY-MM-DD] [郭铜彬]
	 */
	public String queryMenuTree() throws BizException {
		rootMenu=menuBS.queryMenuTree();
		return JSON;
	}

	/**
	 * <p>查指定节点的菜单树</p>
	 * <p>应用场景：系统菜单信息维护</p>
	 * <p>调用步骤：调用MenuBS.queryMenuTree()方法，返回TreeNode对象</p>
	 * Created on Nov 27, 2009
	 * @author: [郭铜彬] - [guotb@neusoft.com]
	 * @update: [日期YYYY-MM-DD] [郭铜彬]
	 */
	public String queryMenuTreeById() throws BizException {		
		return null;
	}
	
	/**
	 * <p>获取与指定菜单绑定有绑定关系的全部节点的资源树信息</p>
	 * <p>应用场景：系统菜单信息维护-资源绑定</p>
	 * <p>调用MenuBS.queryResourceTree(String menuId)方法，返回TreeNode对象</p>
	 * <p>说明：菜单与资源绑定的资源信息为资源中类型是菜单的资源</p>
	 * Created on Nov 27, 2009
	 * @author: [郭铜彬] - [guotb@neusoft.com]
	 * @update: [日期YYYY-MM-DD] [郭铜彬]
	 */
	public String queryResourceTree() throws BizException {
		rootRes=menuBS.queryResourceTree();
		return JSON;
	}

	public void setMenuBS(MenuBS menuBS) {
		this.menuBS = menuBS;
	}

	public TreeNode getRootMenu() {
		return rootMenu;
	}

	public void setRootMenu(TreeNode rootMenu) {
		this.rootMenu = rootMenu;
	}

	public TreeNode getRootRes() {
		return rootRes;
	}

	public void setRootRes(TreeNode rootRes) {
		this.rootRes = rootRes;
	}
	
	public Menu getMenu() {
		return menu;
	}
	
	public void setMenu(Menu menu) {
		this.menu = menu;
	}

}
