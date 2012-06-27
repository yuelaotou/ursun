package cn.ursun.console.app.console.menu.bizservice;

import java.util.List;

import cn.ursun.console.app.domain.Menu;
import cn.ursun.platform.core.exception.BizException;
import cn.ursun.platform.ps.bizlog.aop.annotation.BizExecution;
import cn.ursun.platform.ps.domain.TreeNode;

public interface MenuBS {

	/**
	 * <p>
	 * 查询当前用户可以访问的MENU树
	 * </p>
	 * 
	 * @return
	 * @throws BizException
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-23 下午09:56:39
	 */
	@BizExecution
	public TreeNode queryMenuTreeByUserId() throws BizException;

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
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-23 下午09:56:39
	 */
	@BizExecution
	public TreeNode queryMenuTree() throws BizException;

	/**
	 * <p>
	 * 获取所有资源树信息
	 * </p>
	 * 
	 * @return
	 * @throws BizException
	 * @author: 李志伟 - li.zhw@neusoft.com
	 * @data: Create on Dec 9, 2009 3:05:28 PM
	 */
	@BizExecution
	public TreeNode queryResourceTree() throws BizException;

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
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-23 下午09:57:00
	 */
	@BizExecution
	public TreeNode queryMenuTreeById(String menuId) throws BizException;

	/**
	 * <p>
	 * 查询子节点
	 * </p>
	 * 
	 * @param menuId
	 * @return
	 * @throws BizException
	 * @author: 李志伟 - li.zhw@neusoft.com
	 * @data: Create on Dec 14, 2009 11:22:06 AM
	 */
	@BizExecution
	public List<TreeNode> queryChildrenById(String menuId) throws BizException;

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
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-23 下午09:57:31
	 */
	@BizExecution
	public Menu queryMenuById(String menuId) throws BizException;

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
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-23 下午09:57:47
	 */
	@BizExecution
	public String addMenu(Menu menu) throws BizException;

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
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-23 下午09:57:47
	 */
	@BizExecution
	public void updateMenu(Menu menu) throws BizException;

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
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-23 下午09:57:47
	 */
	@BizExecution
	public void saveChangedMenu(Menu menu) throws BizException;

	/**
	 * <p>
	 * 删除指定菜单信息
	 * </p>
	 * <p>
	 * 调用步骤： 1、调用MenuDAO.deleteMenuResourceMapping(String menuId)删除菜单与资源绑定关系 2、调用MenuDAO.deleteMenuById(String
	 * menuId)删除菜单信息
	 * </p>
	 * 
	 * @param menuid
	 * @throws BizException
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-23 下午09:57:47
	 */
	@BizExecution
	public void deleteMenuById(String menuId) throws BizException;

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
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-23 下午09:58:29
	 */
	@BizExecution
	public void bindMenuResourceMapping(String menuId, String resId) throws BizException;

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
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-23 下午09:58:39
	 */
	@BizExecution
	public void unbindMenuResourceMapping(String menuId) throws BizException;

	public TreeNode queryMenuItem();

	public List<TreeNode> queryMainMenuList();

	public List<TreeNode> queryChildMenuListWithMenuID(String menu_id);
}
