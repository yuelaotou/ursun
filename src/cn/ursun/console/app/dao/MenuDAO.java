package cn.ursun.console.app.dao;

import java.util.List;

import cn.ursun.console.app.domain.Menu;
import cn.ursun.platform.core.exception.BizException;
import cn.ursun.platform.ps.domain.TreeNode;

/**
 * <p>
 * 菜单DAO
 * </p>
 * 
 * @author 宋成山 - songchengshan@neusoft.com
 * @version 1.0
 */
public interface MenuDAO {

	public List<TreeNode> queryMenuItem();

	public List<TreeNode> queryMainMenuList();

	public List<TreeNode> queryChildMenuListWithMenuID(String menu_id);

	/**
	 * <p>
	 * 查询当前用户可以访问的MENU树
	 * </p>
	 * 
	 * @param userId
	 *            用户ID
	 * @return
	 * @throws BizException
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-23 下午09:58:56
	 */
	public List<TreeNode> queryMenuTreeByUserId(String userId);

	/**
	 * <p>
	 * 查询当前用户可以访问指定节点的MENU树
	 * </p>
	 * 
	 * @param userId
	 *            用户ID
	 * @return
	 * @throws BizException
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-23 下午09:58:56
	 */
	public List<TreeNode> queryMenuTreeByUserId(String userId, String menuId);

	/**
	 * <p>
	 * 查询当前角色可以访问的MENU树
	 * </p>
	 * 
	 * @param userId
	 *            用户ID
	 * @return
	 * @throws BizException
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-23 下午09:58:56
	 */
	public List<TreeNode> queryMenuTreeByRoleId(String[] roleId);

	/**
	 * <p>
	 * 查询当前角色可以访问指定节点的MENU树
	 * </p>
	 * 
	 * @param userId
	 *            用户ID
	 * @return
	 * @throws BizException
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-23 下午09:58:56
	 */
	public List<TreeNode> queryMenuTreeByRoleId(String[] roleId, String menuId);

	/**
	 * <p>
	 * 查询所有节点的MENU树
	 * </p>
	 * <p>
	 * 查询表：WEE_AUTH_MENU
	 * </p>
	 * <p>
	 * 应用场景：系统菜单维护
	 * <p>
	 * 
	 * @return List<TreeNode> 树集合对象
	 * @throws BizException
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-23 下午09:36:14
	 * @update: [日期YYYY-MM-DD] [郭铜彬]
	 */
	public List<TreeNode> queryMenuTree();

	/**
	 * <p>
	 * 查询指定节点的MENU树
	 * </p>
	 * <p>
	 * 查询表：WEE_AUTH_MENU
	 * </p>
	 * <p>
	 * 应用场景：系统菜单维护
	 * <p>
	 * 
	 * @param menuId
	 *            指定节点ID
	 * @return List<TreeNode> 树集合对象
	 * @throws BizException
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-23 下午09:36:14
	 * @update: [日期YYYY-MM-DD] [郭铜彬]
	 */
	public List<TreeNode> queryMenuTreeById(String menuId);

	/**
	 * <p>
	 * 查询MENU详细信息
	 * </p>
	 * <p>
	 * 查询表：WEE_AUTH_MENU
	 * </p>
	 * <p>
	 * 应用场景：系统菜单维护
	 * <p>
	 * 
	 * @param menuId
	 *            指定节点ID
	 * @return Menu 菜单实体对象
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-23 下午09:36:14
	 * @update: [日期YYYY-MM-DD] [郭铜彬]
	 */
	public Menu queryMenuById(String menuId);

	/**
	 * <p>
	 * 增加菜单信息
	 * </p>
	 * <p>
	 * 操作表：WEE_AUTH_MENU
	 * </p>
	 * 
	 * @param menu
	 * @return String 菜单信息ID
	 * @throws BizException
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-23 下午09:58:56
	 */
	public String createMenu(Menu menu);

	/**
	 * <p>
	 * 保存添加的更新菜单信息
	 * </p>
	 * <p>
	 * 操作表：WEE_AUTH_MENU
	 * </p>
	 * 
	 * @param menu
	 * @return
	 * @throws BizException
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-23 下午09:58:56
	 */
	public void updateMenu(Menu menu);

	/**
	 * <p>
	 * 删除指定菜单信息
	 * </p>
	 * <p>
	 * 操作表：WEE_AUTH_MENU
	 * </p>
	 * 
	 * @param menuId
	 * @return
	 * @throws BizException
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-23 下午09:58:56
	 */
	public void deleteMenuById(String menuId);

	/**
	 * <p>
	 * 保存资源绑定
	 * </p>
	 * <p>
	 * 操作表：WEE_AUTH_M_MENU_RESOURCE
	 * </p>
	 * 
	 * @param menuId
	 * @param resId
	 * @return
	 * @throws BizException
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-23 下午09:58:56
	 */
	public void createMenuResourceMapping(String menuId, String resId);

	/**
	 * <p>
	 * 删除资源绑定
	 * </p>
	 * <p>
	 * 操作表：WEE_AUTH_M_MENU_RESOURCE
	 * </p>
	 * 
	 * @param menuId
	 * @return
	 * @throws BizException
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-23 下午09:58:56
	 */
	public void deleteMenuResourceMapping(String menuId);

	public List<TreeNode> queryMenuParentTree(String menuId);

}
