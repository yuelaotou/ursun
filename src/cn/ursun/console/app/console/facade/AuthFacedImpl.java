package cn.ursun.console.app.console.facade;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;

import cn.ursun.console.app.console.role.bizservice.RoleBS;
import cn.ursun.console.app.console.unit.bizservice.UnitBS;
import cn.ursun.console.app.dao.AccountDAO;
import cn.ursun.console.app.dao.MenuDAO;
import cn.ursun.console.app.dao.PrivilegeDAO;
import cn.ursun.console.app.dao.ResourceDAO;
import cn.ursun.console.app.dao.RoleDAO;
import cn.ursun.console.app.dao.UnitDAO;
import cn.ursun.console.app.dao.UserDAO;
import cn.ursun.console.app.domain.Account;
import cn.ursun.console.app.domain.Resource;
import cn.ursun.console.app.domain.Role;
import cn.ursun.console.app.domain.Unit;
import cn.ursun.console.app.domain.User;
import cn.ursun.console.app.domain.UserAccount;
import cn.ursun.platform.core.exception.BizException;
import cn.ursun.platform.ps.domain.Code;
import cn.ursun.platform.ps.domain.TreeNode;
import cn.ursun.platform.ps.security.WeeSecurityInfo;

public class AuthFacedImpl implements AuthFacade {

	private AccountDAO accountDAO = null;

	private UserDAO userDAO = null;

	private PrivilegeDAO privilegeDAO = null;

	private ResourceDAO resourceDAO = null;

	private RoleDAO roleDAO = null;

	private UnitDAO unitDAO = null;

	private MenuDAO menuDAO = null;

	private static AuthFacedImpl instance = null;

	private RoleBS roleBS = null;

	private UnitBS unitBS = null;

	private AuthFacedImpl() {
	}

	public static AuthFacedImpl getInstance() {
		if (instance == null)
			instance = new AuthFacedImpl();
		return instance;
	}

	/**
	 * <p>查询用户的角色列表</p>
	 * @param userId 用户ID
	 * @return
	 * @throws BizException
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-24 上午11:15:15
	 */
	public List<Role> queryRoleListOfUser(String userId) throws BizException {
		Validate.notNull(userId, "userId required");
		return roleDAO.queryRoleListByUserId(userId);
	}

	/**
	 * <p>根据userId查询角色下人员</p>
	 * @param userIds
	 * @return
	 * @throws BizException
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @date: Create on 2010-1-7 下午01:08:43
	 */
	public List<Role> queryRoleListOfUser(String[] userIds) throws BizException {

		return roleDAO.queryRoleListByUserId(userIds);
	}

	/**
	 * <p>查询资源列表</p>
	 * @return
	 * @throws BizException
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-24 上午11:15:47
	 */
	public List<Resource> queryResourceList() throws BizException {
		return resourceDAO.queryResourceList();
	}

	/**
	 * <p>查询可访问该资源的角色列表</p>
	 * @param resourceId
	 * @return
	 * @throws BizException
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-24 上午11:16:09
	 */
	public List<Role> queryRoleListOfResource(String resourceId) throws BizException {

		return roleDAO.queryRoleListOfResource(resourceId);
	}

	/**
	 * <p>查询用户的组织机构</p>
	 * @param userId
	 * @return
	 * @throws BizException
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-24 上午11:24:32
	 */
	public List<Unit> queryUnitByUserId(String userId) throws BizException {
		return unitDAO.queryUnitListOfUser(userId);
	}

	/**
	 * <p>判断用户是否有权限</p>
	 * @param userId 用户ID
	 * @param resourceId 资源ID
	 * @return
	 * @throws BizException
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-24 上午11:24:50
	 */
	public boolean hasPermission(String userId, String resourceId) throws BizException {

		if (WeeSecurityInfo.getInstance().isAdminRole(userId))
			return true;

		List<Role> roleList = queryRoleListOfUser(userId);
		List<String> roleIds = new ArrayList<String>();
		for (Role r : roleList) {
			roleIds.add(r.getId());
		}
		return privilegeDAO.hasPrivilege(roleIds.toArray(new String[] {}), resourceId);
	}
	
	/**
	 * <p>判断角色是否有权限</p>
	 * @param roleIds 角色ID
	 * @param resourceId 资源ID
	 * @return
	 * @throws BizException
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-24 上午11:24:50
	 */
	public boolean hasPermission(String[] roleIds, String resourceId) throws BizException{
		for(String roleId:roleIds){
			for(String adminRoleId:WeeSecurityInfo.getInstance().getAdminRoles()){
				if(StringUtils.equals(adminRoleId, roleId)){
					return true;
				}
			}
		}
		
		return privilegeDAO.hasPrivilege(roleIds, resourceId);
	}

	/**
	 * <p>新增用户</p>
	 * @param user 新增的用户
	 * @return 新增的用户
	 * @throws BizException
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-23 下午08:05:00
	 */
	public User createUser(User user) throws BizException {
		return userDAO.createUser(user);
	}

	/**
	 * <p>新增用户组织机构</p>
	 * @param userId 用户ID
	 * @param unitIds 组织机构ID
	 * @throws BizException
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-23 下午08:05:00
	 */

	public void createUserUnitMapping(String userId, String unitIds) throws BizException {
		userDAO.createUserUnitMapping(userId, unitIds);
	}

	/**
	 * <p>删除用户与组织机构关系</p>
	 * @param userId
	 * @throws BizException
	 * @author: 李志伟 - li.zhw@neusoft.com
	 * @data: Create on Jun 8, 2010 1:29:30 PM
	 */
	public void deleteUserUnitMapping(String userId) throws BizException {
		userDAO.deleteUserUnitMapping(userId);
	}
	/**
	 * <p>为用户添加角色</p>
	 * @param userId
	 * @param roleId
	 * @throws BizException
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-23 下午08:07:07
	 */
	public void createUserRoleMapping(String userId, String[] roleIds) throws BizException {
		userDAO.createUserRoleMapping(userId, roleIds);
	}

	/**
	 * <p>创建用户账户</p>
	 * @param userAccount
	 * @throws BizException
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @date: Create on 2010-1-7 下午01:07:19
	 */
	public void createAccount(UserAccount userAccount) throws BizException {
		accountDAO.createAccount(userAccount);
	}

	/**
	 * <p>修改帐户密码</p>
	 * @param accountId
	 * @param newPwd
	 * @throws BizException
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @date: Create on 2010-1-7 下午02:50:23
	 */
	public void updateAccountPassword(String accountId, String newPwd) throws BizException {
		accountDAO.updatePassword(accountId, newPwd);
	}

	/**
	 * <p>修改用户信息</p>
	 * @param user 修改的用户
	 * @return 修改的用户
	 * @throws BizException
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-23 下午08:05:31
	 */
	public void updateUser(User user) throws BizException {
		userDAO.updateUser(user);
	}

	/**
	 * <p>
	 * 修改用户账户
	 * </p>
	 * @param account  需要添加的帐户信息。
	 * @throws BizException
	 * @author: 张猛 - zhang_meng@neusoft.com
	 * @data: Create on 2009年12月11日10:54:31
	 */
	public void updateAccount(UserAccount useraccount) throws BizException {
		accountDAO.updateAccount(useraccount);
	}

	/**
	 * <p>查询扩展字段对应的字典表</p>
	 * @param columnName 扩展字段name 
	 * @param relateValue  级联条件value (如果级联下拉框时，父下拉框的当前选中值)
	 * @return
	 * @throws BizException
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @date: Create on 2010-1-9 下午04:47:59
	 */
	public List<Code> queryCodeList(String columnName, String relateValue) throws BizException {
		return userDAO.queryCodeList(columnName, relateValue);
	}

	/**
	 * <p>查询扩展字段对应字典表的代码值对应的代码名称</p>
	 * @param columnName 扩展字段name 
	 * @param relateValue  级联条件value (如果级联下拉框时，父下拉框的当前选中值)
	 * @return
	 * @throws BizException
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @date: Create on 2010-1-9 下午04:47:59
	 */
	public String queryCodeName(String columnName, String codeValue) throws BizException {
		return userDAO.queryCodeName(columnName, codeValue);
	}

	/**
	 * <p>查询角色与可访问URL的关系映射</p>
	 * @return
	 * @throws BizException
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-24 上午11:25:35
	 */
	public Map<String, List<String>> queryUrlRoleMapping() throws BizException {
		return privilegeDAO.queryUrlRoleMapping();
	}

	/**
	 * <p>获取当前登录人员所能看到的角色树</p>
	 * @return
	 * @throws BizException
	 * @author: [郭铜彬] - [guotb@neusoft.com]
	 * @data: Create on Jan 13, 2010 9:49:01 AM
	 */
	public TreeNode queryRoleTree() throws BizException {
		return roleBS.queryRoleTree();
	}

	/**
	 * <p>根据角色ID查询角色</p>
	 * @param roleIds
	 * @return
	 * @throws BizException
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @date: Create on 2010-1-13 下午03:12:48
	 */
	public Role[] queryRoleById(String[] roleIds) throws BizException {
		return roleDAO.queryRoleById(roleIds);
	}

	public List<TreeNode> queryMenuList() throws BizException {
		return menuDAO.queryMenuTree();
	}

	public List<TreeNode> queryMenuList(String menuId) throws BizException {
		return menuDAO.queryMenuTreeById(menuId);
	}

	public List<TreeNode> queryMenuListByUserId(String userid) throws BizException {
		return menuDAO.queryMenuTreeByUserId(userid);
	}

	public List<TreeNode> queryMenuListByUserId(String userid, String menuId) throws BizException {
		return menuDAO.queryMenuTreeByUserId(userid, menuId);
	}

	/**
	 * <p>查询该角色可见菜单</p>
	 * @param menuId
	 * @return
	 * @throws BizException
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2010-1-4 下午01:30:00
	 */
	public List<TreeNode> queryMenuListByRoleId(String[] roleId) throws BizException {
		return menuDAO.queryMenuTreeByRoleId(roleId);
	}

	/**
	 * <p>查询该角色可见指定菜单下的子菜单</p>
	 * @param menuId
	 * @return
	 * @throws BizException
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2010-1-4 下午01:30:00
	 */
	public List<TreeNode> queryMenuListByRoleId(String roleId[], String menuId) throws BizException {
		return menuDAO.queryMenuTreeByRoleId(roleId, menuId);
	}

	/**
	 * <p>查询父菜单树</p>
	 * @param menuId
	 * @return
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @date: Create on 2010-1-16 下午02:14:28
	 */
	public List<TreeNode> queryMenuParentTree(String menuId) throws BizException {

		return menuDAO.queryMenuParentTree(menuId);
	}

	public Account queryAccountByUsername(String s) throws BizException {
		return accountDAO.queryAccountByUsername(s);
	}

	public User queryUserById(String s) throws BizException {
		return userDAO.queryUserById(s);
	}

	public List<Role> queryRoleListAll(List<String> excludeRoleId) throws BizException {
		return roleDAO.queryRoleList(excludeRoleId);
	}

	public UserAccount queryUserAccountByUserId(String userId) throws BizException {
		return accountDAO.queryUserAccountByUserId(userId);
	}

	public UserAccount queryUserAccountByUserName(String userName) throws BizException {
		return accountDAO.queryUserAccountByUserName(userName);
	}
	
	public List<UserAccount> queryUserAccountListByUserName(String userName) throws BizException {
		return accountDAO.queryUserAccountListByUserName(userName);
	}

	/**
	 * <p>根据当前用户查询组织机构树</p>
	 * @param userId 用户ID
	 * @return
	 * @throws BizException
	 * @author: 李志伟 - li.zhw@neusoft.com
	 * @data: Create on Jan 13, 2010 10:46:25 AM
	 */
	public TreeNode queryUnitTreeOfCurrentUser() throws BizException {
		return unitBS.queryUnitTree();
	}


	public void setAccountDAO(AccountDAO accountDAO) {
		this.accountDAO = accountDAO;
	}

	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	public void setPrivilegeDAO(PrivilegeDAO privilegeDAO) {
		this.privilegeDAO = privilegeDAO;
	}

	public void setResourceDAO(ResourceDAO resourceDAO) {
		this.resourceDAO = resourceDAO;
	}

	public void setRoleDAO(RoleDAO roleDAO) {
		this.roleDAO = roleDAO;
	}

	public void setUnitDAO(UnitDAO unitDAO) {
		this.unitDAO = unitDAO;
	}

	public void setMenuDAO(MenuDAO menuDAO) {
		this.menuDAO = menuDAO;
	}

	public void setRoleBS(RoleBS roleBS) {
		this.roleBS = roleBS;
	}

	public void setUnitBS(UnitBS unitBS) {
		this.unitBS = unitBS;
	}

}
