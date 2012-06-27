package cn.ursun.console.app.dao;

import java.util.List;

import cn.ursun.console.app.console.role.dto.RoleQC;
import cn.ursun.console.app.console.user.dto.UserQC;
import cn.ursun.console.app.domain.User;
import cn.ursun.console.app.domain.UserAccount;
import cn.ursun.console.app.domain.UserExtendColumn;
import cn.ursun.platform.core.dto.Pagination;
import cn.ursun.platform.core.exception.BizException;
import cn.ursun.platform.ps.domain.Code;
import cn.ursun.platform.ps.domain.TreeNode;

public interface UserDAO {

	/**
	 * <p>根据用户名、用户真实姓名查询用户列表</p>
	 * @param queryUserCOND 用户查询条件
	 * @param pagination
	 * @return
	 * @throws BizException
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-23 下午07:51:43
	 */
	public List<UserAccount> queryUserList(UserQC queryUserCOND, Pagination pagination) throws BizException;

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
	public List<UserAccount> queryUserListOfUnit(String unitId, UserQC queryUserCOND, Pagination pagination)
			throws BizException;

	/**
	 * <p>查询角色下的用户列表</p>
	 * @param roleId 角色ID
	 * @param pagination
	 * @return
	 * @throws BizException
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-23 下午08:03:29
	 */
	public List<UserAccount> queryUserListOfRole(String roleId,String loginRId,String userId,String unitId ,Pagination pagination) throws BizException;

	public User queryUserById(String userId) throws BizException;

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
	 * <p>新增用户组织机构</p>
	 * @param userId 用户ID
	 * @param unitIds 组织机构ID
	 * @throws BizException
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-23 下午08:05:00
	 */

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
	 * <p>删除用户组织机构</p>
	 * @param userId
	 * @throws BizException
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-23 下午08:05:00
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
	 * <p>删除用户角色</p>
	 * @param userId
	 * @throws BizException
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-23 下午08:05:00
	 */

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
	public void updateRoleForUser(String userId, String[] roleIds) throws BizException;

	public void deleteUserRoleMapping(String userId) throws BizException;

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
	 * <p>删除用户
	 * </p>
	 * @param userId
	 * @throws BizException
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-23 下午08:06:40
	 */
	public void deleteUserById(String[] userIds) throws BizException;

	/**
	 * 
	 * <p>查询属于某个组织结构的不属于本角色的或者没有被授予角色的用户</p>
	 * @param roleId 角色ID
	 * @param unitId 组织机构ID
	 * @param pagination 分页对象
	 * @return 返回UserAccount对象集合
	 * @throws BizException
	 * @author: 【徐冉】 - 【xu_r@neusoft.com】
	 * @data: Create on 2009-12-16 下午02:05:29
	 */
	public List<UserAccount> queryUserListOfOtherRole(String roleId, String unitId, Pagination pagination)
			throws BizException;

	/**
	 * 
	 * <p>批量创建用户角色映射关系</p>
	 * @param userIds 用户ID数组
	 * @param roleId 角色ID
	 * @throws BizException
	 * @author: 【徐冉】 - 【xu_r@neusoft.com】
	 * @data: Create on 2009-12-15 下午07:13:07
	 */
	public void createUserRoleMapping(String userIds[], String roleId) throws BizException;

	/**
	 * 
	 * <p>批量删除用户与角色之间的映射关系</p>
	 * @param userIds 用户ID数组
	 * @param roleId 角色ID
	 * @throws BizException
	 * @author: 【徐冉】 - 【xu_r@neusoft.com】
	 * @data: Create on 2009-12-16 下午02:04:45
	 */
	public void deleteUserRoleMapping(String userId[], String roleId) throws BizException;
	
	/**
	 * 
	 * <p>本方法用于在删除角色之前级联删除用户与角色的映射关系</p>
	 * @param roleId 角色ID
	 * @author: 【徐冉】 - 【xu_r@neusoft.com】
	 * @data: Create on 2010-1-6 下午01:46:47
	 */
	public void deleteUserRoleMappingCascade(String roleId) throws BizException ;
	/**
	 * <p>查询该用户对应的角色
	 * </p>
	 * @param userId 用户ID
	 * @throws BizException
	 * @return 返回用户拥有的角色节点
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-23 下午05:31:11
	 */
	public List<TreeNode> queryRolesOfUser(String userId) throws BizException;
	
	/**
	 * 
	 * <p>根据条件查询角色下用户信息</p>
	 * @param roleQC 同角色相关的查询条件（
	 *  roleId 选中角色ID,
	 *  loginRId 登陆用户的角色ID,
	 *  userId 登陆用户ID,
	 *  unitId 登陆用户所属组织ID)
	 * @param queryUserCOND 查询条件
	 * @param pagination 分页对象
	 * @return 返回UserAccount对象集合
	 * @throws BizException
	 * @author: 【徐冉】 - 【xu_r@neusoft.com】
	 * @data: Create on 2010-1-7 上午09:49:00
	 */
	public List<UserAccount> queryUserListOfRoleByCQ(RoleQC roleQC,UserQC queryUserCOND,Pagination pagination) throws BizException;
	/**
	 * 
	 * <p>根据查询条件查询不在选中角色下，属于选中组织机构的用户信息</p>
	 * @param roleId 选中角色ID
	 * @param unitId 选中组织机构ID	
	 * @param queryUserCOND 查询条件
	 * @param pagination 分页对象
	 * @return 返回UserAccount对象集合
	 * @throws BizException
	 * @author: 【徐冉】 - 【xu_r@neusoft.com】
	 * @data: Create on 2010-1-7 上午09:52:42
	 */
	public List<UserAccount> queryUserListOfOtherRoleByCQ(String roleId, String unitId, UserQC queryUserCOND, Pagination pagination)throws BizException;
	
	/**
	 * <p>查询代码表</p>
	 * @param tableName
	 * @param codeName
	 * @param codeValue
	 * @return
	 * @author: 李志伟 - li.zhw@neusoft.com
	 * @data: Create on Jan 8, 2010 1:23:11 PM
	 */
	public List<Code> queryCodeList(UserExtendColumn column) throws BizException;
	
	/**
	 * <p>查询关联项的代码表</p>
	 * @param column
	 * @param relateValue
	 * @return
	 * @throws BizException
	 * @author: 李志伟 - li.zhw@neusoft.com
	 * @data: Create on Jan 9, 2010 4:09:53 PM
	 */
	public List<Code> queryCodeList(String columnName,String relateValue) throws BizException;
	
	/**
	 * <p>查询扩展字段对应字典表的代码值对应的代码名称</p>
	 * @param columnName 扩展字段name 
	 * @param relateValue  级联条件value (如果级联下拉框时，父下拉框的当前选中值)
	 * @return
	 * @throws BizException
	 * @author: 李志伟 - li.zhw@neusoft.com
	 * @data: Create on Jan 11, 2010 8:59:57 AM
	 */
	public String queryCodeName(String columnName, String codeValue) throws BizException;
}
