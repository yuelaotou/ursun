package cn.ursun.console.app.console.user.bizservice;

import java.util.List;
import java.util.Map;

import cn.ursun.console.app.console.user.dto.UserQC;
import cn.ursun.console.app.domain.Role;
import cn.ursun.console.app.domain.Unit;
import cn.ursun.console.app.domain.UserAccount;
import cn.ursun.console.app.domain.UserExtendColumn;
import cn.ursun.platform.core.dto.Pagination;
import cn.ursun.platform.core.exception.BizException;
import cn.ursun.platform.ps.bizlog.aop.annotation.BizExecution;
import cn.ursun.platform.ps.domain.Code;
import cn.ursun.platform.ps.domain.TreeNode;


public interface UserBS {

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
	public List<UserAccount> queryUserList(UserQC userQC, Pagination pagination) throws BizException;


	
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
	public List<UserAccount> queryUserListOfUnit(String unitId,UserQC queryUserCOND,Pagination pagination) throws BizException;
		
	/**
	 * <p>查询角色下的用户列表</p>
	 * @param roleId 角色ID
	 * @return
	 * @throws BizException
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-23 下午05:25:26
	 */
	@BizExecution
	public List<UserAccount> queryUserListOfRole(String roleId,String loginRId,String userId,String unitId ,Pagination pagination) throws BizException;

	/**
	 * <p>根据userId查询用户账户信息</p>
	 * @param userId 用户ID
	 * @return 
	 * @throws BizException
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-23 下午05:26:46
	 */
	@BizExecution
	public UserAccount queryAccountByUserId(String userId) throws BizException;

	/**
	 * <p>查询当前用户信息</p>
	 * @return
	 * @throws BizException
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-23 下午05:27:00
	 */
	@BizExecution
	public UserAccount queryCurrentUserInfo() throws BizException;

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
	public boolean addUser(UserAccount userAccount) throws BizException;

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
	public boolean updateUser(UserAccount userAccount,String enableRoleIds) throws BizException;

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
	public boolean updatePassword(String oldPwd, String newPwd) throws BizException;

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
	public void deleteUserById(String[] userIds) throws BizException;

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
	public void updateRoleForUser(String userId, String[] roleIds,String enableRoleIds) throws BizException;

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
	public List<TreeNode> queryRolesOfUser(String userId) throws BizException;
	
	/**
	 * <p>批量禁用帐户</p>
	 * @param userId
	 * @throws BizException
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-23 下午05:32:00
	 */
	@BizExecution
	public void disableUser(String[] userIds) throws BizException;

	/**
	 * <p>批量启用帐户</p>
	 * @param userIds
	 * @throws BizException
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-23 下午05:33:41
	 */
	@BizExecution
	public void enableUser(String[] userIds) throws BizException;
	
	/**
	 * <p>通过用户ID查询组织机构</p>
	 * @param userId
	 * @return
	 * @throws BizException
	 * @author: 李志伟 - li.zhw@neusoft.com
	 * @data: Create on Dec 24, 2009 9:43:15 AM
	 */
	@BizExecution
	public Unit queryUnitByUserId(String userId) throws BizException;
	
	/**
	 * <p>根据用户ID查询关联的角色</p>
	 * @param userId
	 * @return
	 * @throws BizException
	 * @author: 李志伟 - li.zhw@neusoft.com
	 * @data: Create on Dec 24, 2009 3:02:32 PM
	 */
	@BizExecution
	public Role[] queryRolesByUserId(String userId)throws BizException;
	
	/**
	 * <p>检查账户是否存在</p>
	 * @param username
	 * @return
	 * @throws BizException
	 * @author: 李志伟 - li.zhw@neusoft.com
	 * @data: Create on Dec 25, 2009 12:52:56 PM
	 */
	@BizExecution
	public boolean isAccountExistedByUsername(String username,String accountId) throws BizException;
	
	/**
	 * <p>重置用户密码</p>
	 * @param userId
	 * @throws BizException
	 * @author: 李志伟 - li.zhw@neusoft.com
	 * @data: Create on Dec 28, 2009 10:32:58 AM
	 */
	@BizExecution
	public void resetPassword(String userId,String newPwd) throws BizException;
	
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
	public Map<String, List<Code>> queryCodeListForInit(Map<String, UserExtendColumn> columnMap) throws BizException;
	
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
	public List<Code> queryRelateCodeList(String columnName,String relateValue) throws BizException;
}
