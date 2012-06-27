package cn.ursun.console.app.console.user.bizservice.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import cn.ursun.console.app.console.user.bizservice.UserBS;
import cn.ursun.console.app.console.user.dto.UserQC;
import cn.ursun.console.app.dao.AccountDAO;
import cn.ursun.console.app.dao.RoleDAO;
import cn.ursun.console.app.dao.UnitDAO;
import cn.ursun.console.app.dao.UserDAO;
import cn.ursun.console.app.domain.Role;
import cn.ursun.console.app.domain.Unit;
import cn.ursun.console.app.domain.UserAccount;
import cn.ursun.console.app.domain.UserExtendColumn;
import cn.ursun.platform.core.dto.Pagination;
import cn.ursun.platform.core.exception.BizException;
import cn.ursun.platform.ps.bizlog.aop.annotation.BizExecution;
import cn.ursun.platform.ps.bizservice.WeeBizService;
import cn.ursun.platform.ps.domain.Code;
import cn.ursun.platform.ps.domain.TreeNode;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.LocaleProvider;
import com.opensymphony.xwork2.TextProvider;
import com.opensymphony.xwork2.TextProviderFactory;

/**
 * <p>用户管理业务操作</p>
 * @author 李志伟 - li.zhw@neusoft.com
 * @version 1.0
 */
public class UserBSImpl extends WeeBizService implements UserBS,LocaleProvider {

	private final transient TextProvider textProvider = new TextProviderFactory().createInstance(getClass(),this);

	/**
	 * 用户数据操作对象
	 */
	private UserDAO userDAO;

	/**
	 * 帐户数据操作对象
	 */
	private AccountDAO accountDAO;

	/**
	 * 组织机构数据操作对象
	 */
	private UnitDAO unitDAO;

	/**
	 * 角色数据操作对象
	 */
	private RoleDAO roleDAO;

	public Locale getLocale() {
		return ActionContext.getContext().getLocale();
	}

	/**
	 * <p>根据用户名、用户真实姓名查询用户列表，并且是在当前用户所属角色下的用户
	 * 	即通过当前用户的所属角色过滤用户信息</p>
	 * @param userQC 用户查询条件
	 * @param pagination
	 * @return
	 * @throws BizException
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-23 下午05:06:42
	 */
	@BizExecution
	public List<UserAccount> queryUserList(UserQC userQC, Pagination pagination) throws BizException {
		try {
			return userDAO.queryUserList(userQC, pagination);
		} catch (Exception e) {
			throw new BizException("0400110A", e);
		}
	}

	/**
	 * <p>查询组织机构下的用户列表</p>
	 * @param unitId 组织机构ID
	 * @param queryUserCOND
	 * @param pagination
	 * @return
	 * @throws BizException
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-23 下午08:00:57
	 */
	@BizExecution
	public List<UserAccount> queryUserListOfUnit(String unitId, UserQC queryUserCOND, Pagination pagination)
			throws BizException {
		try {
			return userDAO.queryUserListOfUnit(unitId, queryUserCOND, pagination);
		} catch (Exception e) {
			throw new BizException("0400110A", e);
		}
	}

	/**
	 * <p>查询角色下的用户列表</p>
	 * @param roleId 角色ID
	 * @return
	 * @throws BizException
	 * @author: 徐冉 - xu_r@neusoft.com
	 * @data: Create on 2009-11-23 下午05:25:26
	 */
	@BizExecution
	public List<UserAccount> queryUserListOfRole(String roleId, String loginRId, String userId, String unitId,
			Pagination pagination) throws BizException {
		try {
			return userDAO.queryUserListOfRole(roleId, loginRId, userId, unitId, pagination);
		} catch (Exception e) {
			throw new BizException("0400110A", e);
		}
	}

	/**
	 * <p>根据userId查询用户账户信息</p>
	 * @param userId 用户ID
	 * @return 
	 * @throws BizException
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-23 下午05:26:46
	 */
	@BizExecution
	public UserAccount queryAccountByUserId(String userId) throws BizException {
		try {
			UserAccount account = accountDAO.queryUserAccountByUserId(userId);
			return account;
		} catch (Exception e) {
			throw new BizException("0400111A", e);
		}
	}

	/**
	 * <p>查询当前用户信息</p>
	 * @return
	 * @throws BizException
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-23 下午05:27:00
	 */
	@BizExecution
	public UserAccount queryCurrentUserInfo() throws BizException {
		try {
			return null;
		} catch (Exception e) {
			throw new BizException("0400111A", e);
		}
	}

	/**
	 * <p>新增用户
	 * 1.添加用户信息----UserDAO.createUser()
	 * 2.添加用户账户信息---AccountDAO.createAccount()
	 * 3.添加用户的组织机构信息---UserDAO.createUserUnitMapping()
	 * 4.添加用户角色----UserDAO.createUserRoleMapping()
	 * </p>
	 * @param userAccount
	 * @return  返回false,用户名重复
	 * @throws BizException
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-23 下午05:28:21
	 */
	@BizExecution
	public boolean addUser(UserAccount userAccount) throws BizException {
		try {
			Map<String, String[]> map = userAccount.getUser().getExtendInfo();
			userAccount.getUser().setExtendInfo(map);
			userDAO.createUser(userAccount.getUser());
			accountDAO.createAccount(userAccount);
			userDAO.createUserUnitMapping(userAccount.getUser().getId(), userAccount.getUser().getUnitId());
			userDAO.createUserRoleMapping(userAccount.getUser().getId(), userAccount.getRoleIds());
			return true;
		} catch (Exception e) {
			throw new BizException("0400107A", e);
		}
	}

	/**
	 * <p>修改用户信息
	 * 1.添加用户信息----UserDAO.updateUser()
	 * 2.添加用户账户信息---AccountDAO.updateAccount()
	 * 3.添加用户的组织机构信息---UserDAO.deleteUserUnitMapping(),  UserDAO.createUserUnitMapping()
	 * 4.添加用户角色----UserDAO.deleteUserRoleMapping(),UserDAO.createUserRoleMapping()
	 * </p>
	 * @param userAccount
	 * @return 返回false,用户名重复
	 * @throws BizException
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-23 下午05:28:30
	 */
	@BizExecution
	public boolean updateUser(UserAccount userAccount, String enableRoleIds) throws BizException {
		try {
			Map<String, String[]> map = userAccount.getUser().getExtendInfo();
			userAccount.getUser().setExtendInfo(map);
			userDAO.updateUser(userAccount.getUser());
			accountDAO.updateAccount(userAccount);
			this.updateRoleForUser(userAccount.getUser().getId(), userAccount.getRoleIds(), enableRoleIds);
			userDAO.deleteUserUnitMapping(userAccount.getUser().getId());
			userDAO.createUserUnitMapping(userAccount.getUser().getId(), userAccount.getUser().getUnitId());
			return true;
		} catch (Exception e) {
			throw new BizException("0400108A", e);
		}
	}

	/**
	 * <p>修改用户密码</p>
	 * @param oldPwd
	 * @param newPwd
	 * @return 返回false，密码不正确
	 * @throws BizException
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-23 下午07:46:10
	 */
	@BizExecution
	public boolean updatePassword(String oldPwd, String newPwd) throws BizException {
		try {
			return true;
		} catch (Exception e) {
			throw new BizException("0400117A", e);
		}
	}

	/**
	 * <p>批量删除用户
	 * 1.删除用户与角色的关系
	 * 2.删除用户对应的账户信息
	 * 3.删除用户与组织机构的关系
	 * 4.删除用户信息
	 * </p>
	 * @param userId
	 * @throws BizException
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-23 下午05:28:46
	 */
	@BizExecution
	public void deleteUserById(String[] userIds) throws BizException {
		try {
			userDAO.deleteUserById(userIds);
		} catch (Exception e) {
			throw new BizException("0400109A", e);
		}
	}

	/**
	 * <p>为用户添加角色
	 * 1.先清除原有用户与角色关系UserDAO.deleteUserRoleMapping()
	 * 2.添加新的用户与角色关系UserDAO.createUserRoleMapping()
	 * </p>
	 * @param userId
	 * @param roleId
	 * @throws BizException
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-23 下午05:31:11
	 */
	@BizExecution
	public void updateRoleForUser(String userId, String[] roleIds, String enableRoleIds) throws BizException {
		try {
			List<Role> personRoles = roleDAO.queryRoleListByUserId(userId);
			List<String> personRoleId = new ArrayList<String>();
			// 被修改人员角色ID列表
			for (Role role : personRoles) {
				personRoleId.add(role.getId());
			}
			// 被修改人员多于登录人员可管理角色的部分
			String[] enableRoleIdArray = enableRoleIds.split(",");
			for (String id : enableRoleIdArray) {
				if (personRoleId.contains(id)) {
					personRoleId.remove(id);
				}
			}
			// 被修改人员经过修改应该具有的所有角色
			for (String id : roleIds) {
				if (StringUtils.isNotEmpty(id)) {
					personRoleId.add(id);
				}
			}
			// 删除原有的角色关联
			userDAO.deleteUserRoleMapping(userId);
			// 插入新的角色关联
			if (personRoleId.size() != 0) {
				userDAO.updateRoleForUser(userId, (String[]) personRoleId.toArray(new String[personRoleId.size()]));
			}
		} catch (Exception e) {
			throw new BizException("0400116A", e);
		}
	}

	/**
	 * <p>查询该用户对应的角色
	 * </p>
	 * @param userId
	 * @param roleId
	 * @throws BizException
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-23 下午05:31:11
	 */
	@BizExecution
	public List<TreeNode> queryRolesOfUser(String userId) throws BizException {
		try {
			return roleDAO.queryRoleTree(userId);
		} catch (Exception e) {
			throw new BizException("0400119A", e);
		}
	}

	/**
	 * <p>批量禁用帐户</p>
	 * @param userId
	 * @throws BizException
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-23 下午05:32:00
	 */
	@BizExecution
	public void disableUser(String[] userIds) throws BizException {
		try {
			accountDAO.disableAccount(userIds);
		} catch (Exception e) {
			throw new BizException("0400114A", e);
		}
	}

	/**
	 * <p>批量启用帐户</p>
	 * @param userIds
	 * @throws BizException
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-23 下午05:33:41
	 */
	@BizExecution
	public void enableUser(String[] userIds) throws BizException {
		try {
			accountDAO.enableAccount(userIds);
		} catch (Exception e) {
			throw new BizException("0400115A", e);
		}
	}

	/**
	 * <p>通过用户ID查询组织机构</p>
	 * @param userId
	 * @return
	 * @throws BizException
	 * @author: 李志伟 - li.zhw@neusoft.com
	 * @data: Create on Dec 24, 2009 9:42:46 AM
	 */
	@BizExecution
	public Unit queryUnitByUserId(String userId) throws BizException {
		try {
			List<Unit> list = unitDAO.queryUnitListOfUser(userId);
			if (list == null || list.size() == 0) {
				return null;
			}
			return list.get(0);
		} catch (Exception e) {
			throw new BizException("0400106A", e);
		}
	}

	/**
	 * <p>根据用户ID查询关联的角色</p>
	 * @param userId
	 * @return
	 * @throws BizException
	 * @author: 李志伟 - li.zhw@neusoft.com
	 * @data: Create on Dec 24, 2009 3:02:32 PM
	 */
	@BizExecution
	public Role[] queryRolesByUserId(String userId) throws BizException {
		try {
			List<Role> list = roleDAO.queryRoleListByUserId(userId);
			Role[] roles = new Role[list.size()];
			int i = 0;
			for (Iterator<Role> itr = list.iterator(); itr.hasNext();) {
				roles[i] = itr.next();
				i++;
			}
			return roles;
		} catch (Exception e) {
			throw new BizException("0400119A", e);
		}
	}

	/**
	 * <p>检查账户是否存在</p>
	 * @param username
	 * @return
	 * @throws BizException
	 * @author: 李志伟 - li.zhw@neusoft.com
	 * @data: Create on Dec 25, 2009 12:52:56 PM
	 */
	@BizExecution
	public boolean isAccountExistedByUsername(String username, String accountId) throws BizException {
		try {
			return accountDAO.isAccountExistedByUsername(username, accountId);
		} catch (Exception e) {
			throw new BizException("0400112A", e);
		}
	}

	/**
	 * <p>重置用户密码</p>
	 * @param userId
	 * @throws BizException
	 * @author: 李志伟 - li.zhw@neusoft.com
	 * @data: Create on Dec 28, 2009 10:32:58 AM
	 */
	@BizExecution
	public void resetPassword(String userId, String newPwd) throws BizException {
		try {
			String accountId = accountDAO.queryUserAccountByUserId(userId).getAccount().getId();
			accountDAO.updatePassword(accountId, newPwd);
		} catch (Exception e) {
			throw new BizException("0400117A", e);
		}
	}

	/**
	 * <p>查询代码表</p>
	 * @param tableName
	 * @param codeName
	 * @param codeValue
	 * @return
	 * @throws BizException
	 * @author: 李志伟 - li.zhw@neusoft.com
	 * @data: Create on Jan 8, 2010 1:24:51 PM
	 */
	@BizExecution
	public Map<String, List<Code>> queryCodeListForInit(Map<String, UserExtendColumn> columnMap) throws BizException {
		try {
			Map<String, List<Code>> dictMap = new HashMap<String, List<Code>>();
			Code code = new Code();
			code.setCode("");
			code.setCodeName(textProvider.getText("userAccount.pleaseselect"));
			List<Code> temp = null;
			UserExtendColumn column = null;
			List<Code> codeList = null;
			for (String key : columnMap.keySet()) {
				codeList = new ArrayList<Code>();
				temp = new ArrayList<Code>();
				column = columnMap.get(key);
				if ("dict".equalsIgnoreCase(column.getType()) || "radio".equalsIgnoreCase(column.getType())
						|| "checkbox".equals(column.getType())) {
					if ("dict".equalsIgnoreCase(column.getType())) {
						codeList.add(code);
					}
					if (StringUtils.isEmpty(column.getRelateColumn())) {
						temp = userDAO.queryCodeList(column);
					}
					if (temp != null) {
						codeList.addAll(temp);
					}
					dictMap.put(key, codeList);
				}
			}
			return dictMap;
		} catch (Exception e) {
			throw new BizException("0400118A", e);
		}
	}

	/**
	 * <p>查询关联项的代码表</p>
	 * @param column
	 * @param relateValue
	 * @return
	 * @throws BizException
	 * @author: 李志伟 - li.zhw@neusoft.com
	 * @data: Create on Jan 9, 2010 4:15:11 PM
	 */
	@BizExecution
	public List<Code> queryRelateCodeList(String columnName, String relateValue) throws BizException {
		try {
			List<Code> list = new ArrayList<Code>();
			Code code = new Code();
			code.setCode("");
			code.setCodeName("--请选择--");
			list.add(code);
			List<Code> temp = userDAO.queryCodeList(columnName, relateValue);
			if (temp != null) {
				list.addAll(temp);
			}
			return list;
		} catch (Exception e) {
			throw new BizException("0400118A", e);
		}
	}

	public UserDAO getUserDAO() {
		return userDAO;
	}

	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	public AccountDAO getAccountDAO() {
		return accountDAO;
	}

	public void setAccountDAO(AccountDAO accountDAO) {
		this.accountDAO = accountDAO;
	}

	public UnitDAO getUnitDAO() {
		return unitDAO;
	}

	public void setUnitDAO(UnitDAO unitDAO) {
		this.unitDAO = unitDAO;
	}

	public RoleDAO getRoleDAO() {
		return roleDAO;
	}

	public void setRoleDAO(RoleDAO roleDAO) {
		this.roleDAO = roleDAO;
	}

}
