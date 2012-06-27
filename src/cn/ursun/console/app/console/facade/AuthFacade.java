package cn.ursun.console.app.console.facade;

import java.util.List;
import java.util.Map;

import cn.ursun.console.app.domain.Account;
import cn.ursun.console.app.domain.Resource;
import cn.ursun.console.app.domain.Role;
import cn.ursun.console.app.domain.Unit;
import cn.ursun.console.app.domain.User;
import cn.ursun.console.app.domain.UserAccount;
import cn.ursun.platform.core.exception.BizException;
import cn.ursun.platform.ps.domain.Code;
import cn.ursun.platform.ps.domain.TreeNode;

/**
 * <p>权限组件与外部的接口</p>
 * @author 宋成山 - songchengshan@neusoft.com
 * @version 1.0
 */
public interface AuthFacade {


	/**
	 * <p>查询用户的角色列表</p>
	 * @param userId 用户ID
	 * @return
	 * @throws BizException
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-24 上午11:15:15
	 */
	public List<Role> queryRoleListOfUser(String userId) throws BizException;

	/**
	 * <p>查询一批用户的角色列表</p>
	 * @param userId 用户ID
	 * @return
	 * @throws BizException
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-24 上午11:15:15
	 */
	public List<Role> queryRoleListOfUser(String[] userIds) throws BizException;

	/**
	 * <p>查询所有资源列表</p>
	 * @return
	 * @throws BizException
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-24 上午11:15:47
	 */
	public List<Resource> queryResourceList() throws BizException;

	/**
	 * <p>查询可访问该资源的角色列表</p>
	 * @param resourceId
	 * @return
	 * @throws BizException
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-24 上午11:16:09
	 */
	public List<Role> queryRoleListOfResource(String resourceId) throws BizException;

	/**
	 * <p>查询用户的组织机构</p>
	 * @param userId
	 * @return
	 * @throws BizException
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-24 上午11:24:32
	 */
	public List<Unit> queryUnitByUserId(String userId) throws BizException;

	/**
	 * <p>判断用户是否有权限</p>
	 * @param userId 用户ID
	 * @param resourceId 资源ID
	 * @return
	 * @throws BizException
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-24 上午11:24:50
	 */
	public boolean hasPermission(String userId, String resourceId) throws BizException;
	
	/**
	 * <p>判断角色是否有权限</p>
	 * @param roleIds 角色ID
	 * @param resourceId 资源ID
	 * @return
	 * @throws BizException
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-24 上午11:24:50
	 */
	public boolean hasPermission(String[] roleIds, String resourceId) throws BizException;

	/**
	 * <p>查询角色与可访问URL的关系映射</p>
	 * @return
	 * @throws BizException
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-24 上午11:25:35
	 */
	public Map<String, List<String>> queryUrlRoleMapping() throws BizException;

	/**
	 * <p>根据帐户名获取帐户信息</p>
	 * 
	 * @param username 需要获取信息的帐户名称。
	 * @return 帐户信息。如果帐户不存在，则返回null。
	 * @throws BizException 
	 * @author: 兰硕 - lans@neusoft.com
	 * @date: Created on Sep 2, 2008 7:35:00 PM
	 */
	public Account queryAccountByUsername(String username) throws BizException;

	/**
	 * <p>查询用户信息</p>
	 * @param userId
	 * @return
	 * @throws BizException
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-12-31 下午02:32:22
	 */
	public User queryUserById(String userId) throws BizException;

	/**
	 * <p>查询角色列表</p>
	 * @param excludeRoleId 被排除的角色ID，如果为null， 则查询所有角色
	 * @return 角色列表
	 * @throws BizException
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-12-31 下午02:32:50
	 */
	public List<Role> queryRoleListAll(List<String> excludeRoleId) throws BizException;

	/**
	 * <p>查询当前登录人可以看到的菜单</p>
	 * @return
	 * @throws BizException
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2010-1-4 下午01:29:58
	 */
	public List<TreeNode> queryMenuList() throws BizException;

	/**
	 * <p>查询当前登录人可以看到的指定菜单下的菜单</p>
	 * @param menuId
	 * @return
	 * @throws BizException
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2010-1-4 下午01:30:00
	 */
	public List<TreeNode> queryMenuList(String menuId) throws BizException;

	/**
	 * <p>查询当前登录人可以看到的菜单</p>
	 * @return
	 * @throws BizException
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2010-1-4 下午01:29:58
	 */
	public List<TreeNode> queryMenuListByUserId(String userid) throws BizException;

	/**
	 * <p>查询当前登录人可以看到的指定菜单下的菜单</p>
	 * @param menuId
	 * @return
	 * @throws BizException
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2010-1-4 下午01:30:00
	 */
	public List<TreeNode> queryMenuListByUserId(String userid, String menuId) throws BizException;

	/**
	 * <p>查询该角色可见菜单</p>
	 * @param menuId
	 * @return
	 * @throws BizException
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2010-1-4 下午01:30:00
	 */
	public List<TreeNode> queryMenuListByRoleId(String[] roleId) throws BizException;

	/**
	 * <p>查询该角色可见指定菜单下的子菜单</p>
	 * @param menuId
	 * @return
	 * @throws BizException
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2010-1-4 下午01:30:00
	 */
	public List<TreeNode> queryMenuListByRoleId(String[] roleId, String menuId) throws BizException;
	
	/**
	 * <p>查询父菜单树</p>
	 * @param menuId
	 * @return
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @date: Create on 2010-1-16 下午02:14:28
	 */
	public List<TreeNode> queryMenuParentTree(String menuId)throws BizException ;

	/**
	 * <p>新增用户</p>
	 * @param user 新增的用户
	 * @return 新增的用户
	 * @throws BizException
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-23 下午08:05:00
	 */
	public User createUser(User user) throws BizException;

	/**
	 * <p>新增用户账户</p>
	 * @param user 新增的用户
	 * @return 新增的用户
	 * @throws BizException
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-23 下午08:05:00
	 */
	public void createAccount(UserAccount useraccount) throws BizException;

	/**
	 * <p>新增用户组织机构</p>
	 * @param userId 用户ID
	 * @param unitIds 组织机构ID
	 * @throws BizException
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-23 下午08:05:00
	 */

	public void createUserUnitMapping(String userId, String unitIds) throws BizException;

	/**
	 * <p>删除用户与组织机构关系</p>
	 * @param userId
	 * @throws BizException
	 * @author: 李志伟 - li.zhw@neusoft.com
	 * @data: Create on Jun 8, 2010 1:29:30 PM
	 */
	public void deleteUserUnitMapping(String userId) throws BizException;
	
	/**
	 * <p>为用户添加角色</p>
	 * @param userId
	 * @param roleId
	 * @throws BizException
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-23 下午08:07:07
	 */
	public void createUserRoleMapping(String userId, String[] roleIds) throws BizException;

	/**
	 * <p>查询用户及帐户信息</p>
	 * @param UserName
	 * @return
	 * @throws BizException
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @date: Create on 2010-1-7 下午02:38:22
	 */
	public UserAccount queryUserAccountByUserName(String userName) throws BizException;

	/**
	 * <p>查询用户及帐户信息</p>
	 * @param userId
	 * @return
	 * @throws BizException
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @date: Create on 2010-1-7 下午02:38:24
	 */
	public UserAccount queryUserAccountByUserId(String userId) throws BizException;

	/**
	 * <p>修改帐户密码</p>
	 * @param accountId
	 * @param newPwd
	 * @throws BizException
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @date: Create on 2010-1-7 下午02:50:23
	 */
	public void updateAccountPassword(String accountId, String newPwd) throws BizException;

	/**
	 * <p>修改用户信息</p>
	 * @param user 修改的用户
	 * @return 修改的用户
	 * @throws BizException
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-23 下午08:05:31
	 */
	public void updateUser(User user) throws BizException;

	/**
	 * <p>
	 * 修改用户账户
	 * </p>
	 * @param account  需要添加的帐户信息。
	 * @throws BizException
	 * @author: 张猛 - zhang_meng@neusoft.com
	 * @data: Create on 2009年12月11日10:54:31
	 */
	public void updateAccount(UserAccount useraccount) throws BizException;

	/**
	 * <p>查询扩展字段对应的字典表</p>
	 * @param columnName 扩展字段name 
	 * @param relateValue  级联条件value (如果级联下拉框时，父下拉框的当前选中值)
	 * @return
	 * @throws BizException
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @date: Create on 2010-1-9 下午04:47:59
	 */
	public List<Code> queryCodeList(String columnName, String relateValue) throws BizException;

	/**
	 * <p>查询扩展字段对应字典表的代码值对应的代码名称</p>
	 * @param columnName 扩展字段name 
	 * @param relateValue  级联条件value (如果级联下拉框时，父下拉框的当前选中值)
	 * @return
	 * @throws BizException
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @date: Create on 2010-1-9 下午04:47:59
	 */
	public String queryCodeName(String columnName, String codeValue) throws BizException;

	/**
	 * <p>根据当前用户查询组织机构树</p>
	 * @param userId 用户ID
	 * @return
	 * @throws BizException
	 * @author: 李志伟 - li.zhw@neusoft.com
	 * @data: Create on Jan 13, 2010 10:46:25 AM
	 */
	public TreeNode queryUnitTreeOfCurrentUser() throws BizException;

	/**
	 * <p>获取当前登录人员所能看到的角色树</p>
	 * @return
	 * @throws BizException
	 * @author: [郭铜彬] - [guotb@neusoft.com]
	 * @data: Create on Jan 13, 2010 9:49:01 AM
	 */
	public TreeNode queryRoleTree() throws BizException;

	/**
	 * <p>根据角色ID查询角色</p>
	 * @param roleIds
	 * @return
	 * @throws BizException
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @date: Create on 2010-1-13 下午03:12:48
	 */
	public Role[] queryRoleById(String[] roleIds) throws BizException;
	
	/**
	 * 门户管理调用 根据用户名模糊查询用户信息
	 * @param userName
	 * @return
	 * @throws BizException
	 */
	public List<UserAccount> queryUserAccountListByUserName(String userName) throws BizException;

}
