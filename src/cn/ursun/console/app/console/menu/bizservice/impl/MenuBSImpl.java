/**
 * 文件名：MenuBSImpl.java
 * 
 * 创建人：【李志伟】 - 【li.zhw@neusoft.com】
 * 
 * 创建时间：Dec 7, 2009 3:21:52 PM
 * 
 * 版权所有：东软软件股份有限公司
 */
package cn.ursun.console.app.console.menu.bizservice.impl;

import java.util.List;

import cn.ursun.console.app.console.menu.bizservice.MenuBS;
import cn.ursun.console.app.dao.MenuDAO;
import cn.ursun.console.app.dao.ResourceDAO;
import cn.ursun.console.app.domain.Menu;
import cn.ursun.platform.core.exception.BizException;
import cn.ursun.platform.ps.bizlog.aop.annotation.BizExecution;
import cn.ursun.platform.ps.bizservice.WeeBizService;
import cn.ursun.platform.ps.domain.TreeNode;
import cn.ursun.platform.ps.util.TreeUtil;

/**
 * <p>
 * 菜单管理业务操作
 * </p>
 * 
 * @author 李志伟 - li.zhw@neusoft.com
 * @version 1.0
 */
public class MenuBSImpl extends WeeBizService implements MenuBS {

	/**
	 * 菜单数据访问对象
	 */
	private MenuDAO menuDAO;

	/**
	 * 资源数据访问对象
	 */
	private ResourceDAO resourceDAO;

	/**
	 * <p>
	 * 保存添加的菜单信息
	 * </p>
	 * <p>
	 * 调用步骤：调用MenuDAO.createMenu()
	 * </p>
	 * 
	 * @param menu
	 *            菜单实体对象
	 * @throws BizException
	 * @author: 李志伟 - li.zhw@neusoft.com
	 * @data: Create on Dec 7, 2009 3:21:52 PM
	 */
	@BizExecution
	public String addMenu(Menu menu) throws BizException {
		try {
			return menuDAO.createMenu(menu);
		} catch (Exception e) {
			throw new BizException("0400402A", e);
		}
	}

	/**
	 * <p>
	 * 保存资源绑定
	 * </p>
	 * <p>
	 * 调用步骤： 1、调用MenuDAO.createMenuResourceMapping(String menuId, String resId)创建菜单与资源绑定关系
	 * </p>
	 * 
	 * @param menuId
	 * @param resId
	 * @throws BizException
	 * @author: 李志伟 - li.zhw@neusoft.com
	 * @data: Create on Dec 7, 2009 3:21:52 PM
	 */
	@BizExecution
	public void bindMenuResourceMapping(String menuId, String resId) throws BizException {
		try {
			menuDAO.deleteMenuResourceMapping(menuId);
			menuDAO.createMenuResourceMapping(menuId, resId);
		} catch (Exception e) {
			throw new BizException("0400405A", e);
		}
	}

	/**
	 * <p>
	 * 删除指定菜单信息
	 * </p>
	 * <p>
	 * 调用步骤： 1、调用MenuDAO.deleteMenuResourceMapping(String menuId)删除菜单与资源绑定关系 2、调用MenuDAO.deleteMenuById(String
	 * menuId)删除菜单信息
	 * </p>
	 * 
	 * @param menuId
	 * @throws BizException
	 * @author: 李志伟 - li.zhw@neusoft.com
	 * @data: Create on Dec 7, 2009 3:21:52 PM
	 */
	@BizExecution
	public void deleteMenuById(String menuId) throws BizException {
		try {
			menuDAO.deleteMenuResourceMapping(menuId);
			menuDAO.deleteMenuById(menuId);
		} catch (Exception e) {
			throw new BizException("0400404A", e);
		}
	}

	/**
	 * <p>
	 * 查询MENU详细信息
	 * </p>
	 * <p>
	 * 调用步骤：调用MenuDAO.queryMenuById(String menuId)方法获取菜单实体信息
	 * </p>
	 * 
	 * @param menuId
	 *            菜单ID
	 * @return Menu 菜单实体对象
	 * @throws BizException
	 * @author: 李志伟 - li.zhw@neusoft.com
	 * @data: Create on Dec 7, 2009 3:21:52 PM
	 */
	@BizExecution
	public Menu queryMenuById(String menuId) throws BizException {
		try {
			return menuDAO.queryMenuById(menuId);
		} catch (Exception e) {
			throw new BizException("0400407A", e);
		}
	}

	/**
	 * <p>
	 * 查询所有节点的MENU树
	 * </p>
	 * <p>
	 * 调用步骤：调用MenuDAO.queryMenuTree()方法获取树信息
	 * </p>
	 * 
	 * @return TreeNode 树节点信息
	 * @throws BizException
	 * @author: 李志伟 - li.zhw@neusoft.com
	 * @data: Create on Dec 7, 2009 3:21:52 PM
	 */
	@BizExecution
	public TreeNode queryMenuTree() throws BizException {
		try {
			return TreeUtil.createTreeRelation(menuDAO.queryMenuTree());
		} catch (Exception e) {
			throw new BizException("0400401A", e);
		}
	}

	/**
	 * <p>
	 * 查询指定节点的MENU树
	 * </p>
	 * <p>
	 * 调用步骤：调用MenuDAO.queryMenuTreeById(String menuID)方法获取树信息
	 * </p>
	 * 
	 * @param menuId
	 *            指定节点数ID
	 * @return TreeNode 树节点信息
	 * @throws BizException
	 * @author: 李志伟 - li.zhw@neusoft.com
	 * @data: Create on Dec 7, 2009 3:21:52 PM
	 */
	@BizExecution
	public TreeNode queryMenuTreeById(String menuId) throws BizException {
		try {
			return null;
		} catch (Exception e) {
			throw new BizException("0400401A", e);
		}
	}

	/**
	 * <p>
	 * 查询子节点
	 * </p>
	 * 
	 * @param menuId
	 * @return
	 * @throws BizException
	 * @author: 李志伟 - li.zhw@neusoft.com
	 * @data: Create on Dec 14, 2009 11:21:06 AM
	 */
	@BizExecution
	public List<TreeNode> queryChildrenById(String menuId) throws BizException {
		try {
			return menuDAO.queryMenuTreeById(menuId);
		} catch (Exception e) {
			throw new BizException("0400401A", e);
		}
	}

	/**
	 * <p>
	 * 查询当前用户可以访问的MENU树
	 * </p>
	 * 
	 * @return
	 * @throws BizException
	 * @author: 李志伟 - li.zhw@neusoft.com
	 * @data: Create on Dec 7, 2009 3:21:52 PM
	 */
	@BizExecution
	public TreeNode queryMenuTreeByUserId() throws BizException {
		try {
			return null;
		} catch (Exception e) {
			throw new BizException("0400401A", e);
		}
	}

	/**
	 * <p>
	 * 获取所有资源树信息
	 * </p>
	 * 
	 * @return
	 * @throws BizException
	 * @author: 李志伟 - li.zhw@neusoft.com
	 * @data: Create on Dec 9, 2009 3:06:31 PM
	 */
	@BizExecution
	public TreeNode queryResourceTree() throws BizException {
		try {
			List<TreeNode> list = resourceDAO.queryResourceTree();
			return TreeUtil.createTreeRelation(list);
		} catch (Exception e) {
			throw new BizException("0400408A", e);
		}
	}

	/**
	 * <p>
	 * 保存菜单节点关系的更改
	 * </p>
	 * <p>
	 * 调用步骤：调用MenuDAO.saveChangedMenu()
	 * </p>
	 * 
	 * @param menu
	 * @throws BizException
	 * @author: 李志伟 - li.zhw@neusoft.com
	 * @data: Create on Dec 7, 2009 3:21:52 PM
	 */
	@BizExecution
	public void saveChangedMenu(Menu menu) throws BizException {
		try {

		} catch (Exception e) {
			throw new BizException("", e);
		}
	}

	/**
	 * <p>
	 * 删除资源绑定
	 * </p>
	 * <p>
	 * 调用步骤： 1、调用MenuDAO.deleteMenuResourceMapping(String menuId)删除菜单与资源绑定关系
	 * </p>
	 * 
	 * @param menuId
	 * @throws BizException
	 * @author: 李志伟 - li.zhw@neusoft.com
	 * @data: Create on Dec 7, 2009 3:21:52 PM
	 */
	@BizExecution
	public void unbindMenuResourceMapping(String menuId) throws BizException {
		try {
			menuDAO.deleteMenuResourceMapping(menuId);
		} catch (Exception e) {
			throw new BizException("0400406A", e);
		}
	}

	/**
	 * <p>
	 * 保存添加的更新菜单信息
	 * </p>
	 * <p>
	 * 调用步骤：调用MenuDAO.updateMenu()
	 * </p>
	 * 
	 * @param menu
	 * @throws BizException
	 * @author: 李志伟 - li.zhw@neusoft.com
	 * @data: Create on Dec 7, 2009 3:21:52 PM
	 */
	@BizExecution
	public void updateMenu(Menu menu) throws BizException {
		try {
			menuDAO.updateMenu(menu);
		} catch (Exception e) {
			throw new BizException("0400402A", e);
		}
	}

	/**
	 * <p>
	 * 获取菜单数据访问对象
	 * </p>
	 * 
	 * @return
	 * @author: 李志伟 - li.zhw@neusoft.com
	 * @data: Create on Dec 7, 2009 5:15:28 PM
	 */
	public MenuDAO getMenuDAO() {
		return menuDAO;
	}

	public TreeNode queryMenuItem() {
		return TreeUtil.createTreeRelation(menuDAO.queryMenuItem());

	}

	public List<TreeNode> queryMainMenuList() {
		return menuDAO.queryMainMenuList();

	}

	public List<TreeNode> queryChildMenuListWithMenuID(String menu_id) {
		return menuDAO.queryChildMenuListWithMenuID(menu_id);

	}

	/**
	 * <p>
	 * 设置菜单数据访问对象
	 * </p>
	 * 
	 * @param menuDAO
	 * @author: 李志伟 - li.zhw@neusoft.com
	 * @data: Create on Dec 7, 2009 5:16:22 PM
	 */
	public void setMenuDAO(MenuDAO menuDAO) {
		this.menuDAO = menuDAO;
	}

	public ResourceDAO getResourceDAO() {
		return resourceDAO;
	}

	public void setResourceDAO(ResourceDAO resourceDAO) {
		this.resourceDAO = resourceDAO;
	}

}
