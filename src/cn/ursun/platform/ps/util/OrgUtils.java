/**
 * 文件名：MetadataUtils.java
 * 
 * 创建人：兰硕 - lans@neusoft.com
 * 
 * 创建时间：Sep 20, 2008 2:44:58 AM
 * 
 * 版权所有：东软集团股份有限公司
 */
package cn.ursun.platform.ps.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.Validate;

import cn.ursun.console.app.console.facade.AuthFacade;
import cn.ursun.console.app.dao.AccountDAO;
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

/**
 * <p>[描述信息：说明类的基本功能]</p>
 *
 * @author 兰硕 - lans@neusoft.com
 * @version 1.0 Created on Sep 20, 2008 2:44:58 AM
 */
public class OrgUtils implements AuthFacade {

	private static OrgUtils instance = null;

	private AccountDAO accountDAO = null;

	private UserDAO userDAO = null;

	private PrivilegeDAO privilegeDAO = null;

	private ResourceDAO resourceDAO = null;

	private RoleDAO roleDAO = null;

	private UnitDAO unitDAO = null;

	private String adminRoleId = null;

	public void setAdminRoleId(String adminRoleId) {
		this.adminRoleId = adminRoleId;
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

	private OrgUtils() {
	}

	public static OrgUtils getInstance() {
		if (instance == null)
			instance = new OrgUtils();
		return instance;
	}

	public boolean isUserOfAdminRole(String userId) throws BizException {
		Validate.notNull(userId, "userId required");
		List<Role> roleList = ((AuthFacade) roleDAO).queryRoleListOfUser(userId);
		for (Role r : roleList) {
			if (adminRoleId.equals(r.getId()))
				return true;
		}
		return false;
	}

	public List<Role> queryRoleListOfUser(String userId) throws BizException {
		Validate.notNull(userId, "userId required");
		return ((AuthFacade) roleDAO).queryRoleListOfUser(userId);
	}
	
	public List<Role> queryRoleListOfUser(String[] userIds) throws BizException {
		Validate.notNull(userIds, "userIds required");
		return null;
	}

	public List<Resource> queryResourceList() throws BizException {
		return resourceDAO.queryResourceList();
	}

	public List<Role> queryRoleListOfResource(String resourceId) throws BizException {
		return null;//roleDAO.queryRoleListOfResource(resourceId, null);
	}

	public List<Role> queryRoleListAll(List<String> excludeRoleId) throws BizException {
		return roleDAO.queryRoleList(excludeRoleId);
	}

	public List<Unit> queryUnitByUserId(String userId) throws BizException {
		return unitDAO.queryUnitListOfUser(userId);
	}

	public boolean hasPermission(String userId, String resourceId) throws BizException {
		List<Role> roleList = queryRoleListOfUser(userId);
		List<String> roleIds = new ArrayList<String>();
		for (Role r : roleList) {
			roleIds.add(r.getId());
		}
		return privilegeDAO.hasPrivilege(roleIds.toArray(new String[] {}), resourceId);
	}

	public Map<String, List<String>> queryUrlRoleMapping() throws BizException {
		return privilegeDAO.queryUrlRoleMapping();
	}

	/**
	 * <p>是否为超级管理员角色</p>
	 * @param userId 用户ID
	 * @return
	 * @throws BizException
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-24 上午11:14:10
	 */
	public boolean isAdminRole(String userId) throws BizException {
		return false;
	}

	/**
	 * <p>根据帐户名获取帐户信息</p>
	 * 
	 * @param username 需要获取信息的帐户名称。
	 * @return 帐户信息。如果帐户不存在，则返回null。
	 * @throws BizException 
	 * @author: 兰硕 - lans@neusoft.com
	 * @date: Created on Sep 2, 2008 7:35:00 PM
	 */
	public Account queryAccountByUsername(String username) throws BizException {
		return accountDAO.queryAccountByUsername(username);
	}

	public User queryUserById(String userId) throws BizException {

		return userDAO.queryUserById(userId);
	}

	public List<TreeNode> queryMenuList() throws BizException {
		return null;
	}

	public List<TreeNode> queryMenuList(String menuId) throws BizException {
		return null;
	}

	public User createUser(User user) throws BizException {
		return null;
	}

	public void createUserRoleMapping(String userId, String[] roleIds) throws BizException {
	}

	public void createUserUnitMapping(String userId, String unitIds) throws BizException {
	}

	public List<TreeNode> queryMenuListByRoleId(String roleId) throws BizException {
		return null;
	}

	public List<TreeNode> queryMenuListByRoleId(String roleId, String menuId) throws BizException {
		return null;
	}

	public List<TreeNode> queryMenuListByUserId(String userid) throws BizException {
		return null;
	}

	public List<TreeNode> queryMenuListByUserId(String userid, String menuId) throws BizException {
		return null;
	}

	public void createAccount(UserAccount useraccount) throws BizException {
	}

	public UserAccount queryUserAccountByUserId(String userId) throws BizException {
		return null;
	}

	public UserAccount queryUserAccountByUserName(String userName) throws BizException {
		return null;
	}

	public void updateAccount(UserAccount useraccount) throws BizException {
	}

	public void updateAccountPassword(String accountId, String newPwd) throws BizException {
	}

	public void updateUser(User user) throws BizException {
	}

	public List<Code> queryCodeList(String columnName, String relateValue) throws BizException {
		return null;
	}

	public String queryCodeName(String columnName, String codeValue) throws BizException {
		return null;
	}

	public Role[] queryRoleById(String[] roleIds) throws BizException {
		return null;
	}

	public TreeNode queryRoleTree() throws BizException {
		return null;
	}

	public TreeNode queryUnitTreeOfCurrentUser() throws BizException {
		return null;
	}

	public boolean hasPermission(String[] roleIds, String resourceId) throws BizException {
		return false;
	}

	public List<TreeNode> queryMenuListByRoleId(String[] roleId) throws BizException {
		return null;
	}

	public List<TreeNode> queryMenuListByRoleId(String[] roleId, String menuId) throws BizException {
		return null;
	}

	public List<TreeNode> queryMenuParentTree(String menuId) throws BizException {
		return null;
	}

	public void deleteUserUnitMapping(String userId) throws BizException {
	}

	public List<UserAccount> queryUserAccountListByUserName(String userName) throws BizException {
		return null;
	}

}
