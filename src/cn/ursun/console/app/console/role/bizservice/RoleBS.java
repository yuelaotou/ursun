package cn.ursun.console.app.console.role.bizservice;

import java.util.List;

import cn.ursun.console.app.console.role.dto.RoleQC;
import cn.ursun.console.app.console.user.dto.UserQC;
import cn.ursun.console.app.domain.Role;
import cn.ursun.console.app.domain.UserAccount;
import cn.ursun.platform.core.dto.Pagination;
import cn.ursun.platform.core.exception.BizException;
import cn.ursun.platform.ps.bizlog.aop.annotation.BizExecution;
import cn.ursun.platform.ps.domain.TreeNode;

public interface RoleBS {

	/**
	 * <p> 查询当前用户可管理的角色树
	 * 判断当前用户是否为超级管理员，超级管理员返回所有角色，否则查询当前用户拥有的角色级子角色
	 * </p>
	 * @return 返回当前用户可见的角色树节点
	 * @throws BizException
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-23 下午09:08:57
	 */
	@BizExecution
	public TreeNode queryRoleTree() throws BizException;

	/**
	 * <p>查询角色详细信息</p>
	 * @param roleId 角色ID
	 * @return 根据角色Id返回角色对象
	 * @throws BizException
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-23 下午09:09:50
	 */
	@BizExecution
	public Role queryRoleById(String roleId) throws BizException;

	/**
	 * <p>保存新增角色</p>
	 * @param role 角色对象
	 * @throws BizException
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-23 下午09:10:21
	 */
	@BizExecution
	public void addRole(Role role) throws BizException;

	/**
	 * <p>保存修改角色</p>
	 * @param role 角色
	 * @throws BizBizException
	 * @author: 宋成山 songchengshan@neusoft.com
	 * @date: Created on Aug 21, 2008 1:49:52 PM
	 */
	@BizExecution
	public void updateRole(Role role) throws BizException;

	/**
	 * <p>删除角色
	 * 1.删除角色与资源的关系 调用PrivilegeDAO.deleteRoleToResourceMaping
	 * 2.删除角色与人员的关系 调用PrivilegeDAO.deleteUserRoleMapping
	 * 3.删除角色信息 调用RoleDAO.deleteRole() (删除时要级联删除子角色)
	 * </p>
	 * @param roleId  角色ID
	 * @throws BizBizException
	 * @author: 宋成山 songchengshan@neusoft.com
	 * @date: Created on Aug 21, 2008 1:49:55 PM
	 */
	@BizExecution
	public void deleteRole(String roleId) throws BizException;

	/**
	 * <p>为角色添加用户
	 * 调用UserDAO.createUserRoleMapping() 为角色添加用户
	 * </p>
	 * @param roleId 角色ID
	 * @param userIds 用户ID数组
	 * @author: 宋成山 songchengshan@neusoft.com
	 * @date: Created on Aug 25, 2008 3:46:43 PM
	 */
	@BizExecution
	public void addUserForRole(String roleId, String[] userIds) throws BizException;

	/**
	 * <p>删除角色中的用户
	 *   调用UserDAO.deleteUserRoleMapping() 为角色删除用户
	 * </p>
	 * @param roleId 角色ID
	 * @param userId 用户ID数组
	 * @throws BizException
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-23 下午09:11:46
	 */
	@BizExecution
	public void deleteUserForRole(String roleId, String[] userIds) throws BizException;

	/**
	 * <p>Discription:查询当前用户是否为超级管理员</p>
	 * @param userId 用户ID 
	 * @return 返回boolean值，判断是否为管理员
	 * @throws BizException
	 * @author: 宋成山 songchengshan@neusoft.com
	 * @date: Created on Sep 4, 2008 6:25:16 PM
	 */
	@BizExecution
	public boolean isAdmin(String userId) throws BizException;
	 /**
     * 
     * <p>查询非本角色下的用户</p>
     * @param roleId 角色ID
     * @param unitId 组织机构ID
     * @param pagination 分页对象
     * @return 返回UserAccount对象集合
     * @throws BizException
     * @author: 徐冉 - xu_r@neusoft.com
     * @data: Create on Dec 2, 2009 5:27:17 PM
     */
	@BizExecution
	public List<UserAccount> queryUsersOfOtherRole(String roleId,String unitId,Pagination pagination)throws BizException;
	
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
	@BizExecution
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
	@BizExecution
	public List<UserAccount> queryUserListOfOtherRoleByCQ(String roleId, String unitId, UserQC queryUserCOND, Pagination pagination)throws BizException;
	/**
	 * 
	 * <p>查询当前用户可管理的角色树(不包含匿名角色)
	 * 判断当前用户是否为超级管理员，超级管理员返回所有角色，否则查询当前用户拥有的角色级子角色</p>
	 * @return 返回登陆用户可见的角色节点集合。
	 * @throws BizException
	 * @author: 徐冉 - xu_r@neusoft.com
	 * @data: Create on Dec 2, 2009 5:26:41 PM
	 */
	@BizExecution
	public TreeNode queryRoleTreeWithoutAnoRole() throws BizException;
	
	/**
	 * <p>同级下是否重名</p>
	 * @return
	 * @throws BizExcepton
	 * @author: 李志伟 - li.zhw@neusoft.com
	 * @data: Create on Apr 27, 2010 9:40:03 AM
	 */
	@BizExecution
	public boolean isExistSameParent(Role role) throws BizException;
	
	/**
	 * <p>修改角色父子关系</p>
	 * @param unitId
	 * @param newParentId
	 * @throws BizException
	 * @author: 李志伟 - li.zhw@neusoft.com
	 * @data: Create on May 15, 2010 12:37:45 PM
	 */
	@BizExecution
	public void updateRoleParent(String roleId,String newParentId) throws BizException;
	
	/**
	 * <p>查询除需要移动的节点及其子节点的角色树</p>
	 * @param unitId
	 * @return
	 * @throws BizException
	 * @author: 李志伟 - li.zhw@neusoft.com
	 * @data: Create on May 15, 2010 12:37:36 PM
	 */
	@BizExecution
	public TreeNode queryUnitTreeForUpdateParent(String roleId) throws BizException;
}
