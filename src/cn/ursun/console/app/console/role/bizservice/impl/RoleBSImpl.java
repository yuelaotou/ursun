package cn.ursun.console.app.console.role.bizservice.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import cn.ursun.console.app.console.role.bizservice.RoleBS;
import cn.ursun.console.app.console.role.dto.RoleQC;
import cn.ursun.console.app.console.user.dto.UserQC;
import cn.ursun.console.app.dao.PrivilegeDAO;
import cn.ursun.console.app.dao.RoleDAO;
import cn.ursun.console.app.dao.UserDAO;
import cn.ursun.console.app.domain.Role;
import cn.ursun.console.app.domain.UserAccount;
import cn.ursun.platform.core.dto.Pagination;
import cn.ursun.platform.core.exception.BizException;
import cn.ursun.platform.ps.bizlog.aop.annotation.BizExecution;
import cn.ursun.platform.ps.bizservice.WeeBizService;
import cn.ursun.platform.ps.domain.TreeNode;
import cn.ursun.platform.ps.security.WeeSecurityInfo;
import cn.ursun.platform.ps.util.TreeUtil;

public class RoleBSImpl extends WeeBizService implements RoleBS {

	private RoleDAO roleDAO;

	private UserDAO userDAO;

	private PrivilegeDAO privilegeDAO;

	public void setPrivilegeDAO(PrivilegeDAO privilegeDAO) {
		this.privilegeDAO = privilegeDAO;
	}

	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	public void setRoleDAO(RoleDAO roleDAO) {
		this.roleDAO = roleDAO;
	}

	public RoleBSImpl() {
		super();
	}

	/**
	 * 
	 * <p>新增角色</p>
	 * @param role 角色对象
	 * @throws BizException
	 * @author: 徐冉 - xu_r@neusoft.com
	 * @data: Create on Dec 2, 2009 5:20:13 PM
	 */
	@BizExecution
	public void addRole(Role role) throws BizException {
		try {
			roleDAO.createRole(role);
		} catch (Exception e) {
			throw new BizException("0400202A", e);
		}
	}

	/**
	 * 
	 * <p>为角色添加用户,调用UserDAO.createUserRoleMapping() 为角色添加用户</p>
	 * @param roleId 角色Id
	 * @param userIds 用户Id数组
	 * @throws BizException
	 * @author: 徐冉 - xu_r@neusoft.com
	 * @data: Create on Dec 2, 2009 5:21:37 PM
	 */
	@BizExecution
	public void addUserForRole(String roleId, String[] userIds) throws BizException {
		try {
			userDAO.createUserRoleMapping(userIds, roleId);
		} catch (Exception e) {
			throw new BizException("0400206A", e);
		}
	}

	/**
	 * 
	 * <p>删除角色
	 * 1.删除角色与资源的关系 调用privilegeDAO.deleteRoleToResourceMaping
	 * 2.删除角色与人员的关系 调用userDAO.deleteUserRoleMapping
	 * 3.删除角色信息 调用RoleDAO.deleteRole() (删除时要级联删除子角色)
	 * </p>
	 * @param roleId 角色Id
	 * @throws BizException
	 * @author: 徐冉 - xu_r@neusoft.com
	 * @data: Create on Dec 2, 2009 5:22:52 PM
	 */
	@BizExecution
	public void deleteRole(String roleId) throws BizException {
		try {
			userDAO.deleteUserRoleMappingCascade(roleId);
			privilegeDAO.deleteResourceRoleMapping(roleId);
			roleDAO.deleteRole(roleId);
		} catch (Exception e) {
			throw new BizException("0400204A", e);
		}

	}

	/**
	 * 
	 * <p>删除角色中的用户,调用UserDAO.deleteUserRoleMapping() 为角色删除用户</p>
	 * @param roleId 角色Id
	 * @param userIds 用户Id数组
	 * @throws BizException
	 * @author: 徐冉 - xu_r@neusoft.com
	 * @data: Create on Dec 2, 2009 5:24:10 PM
	 */
	@BizExecution
	public void deleteUserForRole(String roleId, String[] userIds) throws BizException {
		try {
			userDAO.deleteUserRoleMapping(userIds, roleId);
		} catch (Exception e) {
			throw new BizException("0400207A", e);
		}
	}

	/**
	 * 
	 * <p>查询当前用户是否为超级管理员</p>
	 * @return 是否是超级管理员角色，如果是返回true，不是返回false。
	 * @throws BizException
	 * @author: 徐冉 - xu_r@neusoft.com
	 * @data: Create on Dec 2, 2009 5:25:19 PM
	 */
	@BizExecution
	public boolean isAdmin(String userId) throws BizException {
		try {
			WeeSecurityInfo wsi = WeeSecurityInfo.getInstance();
			return wsi.isAdminRole(userId);
		} catch (Exception e) {
			throw new BizException("0400208A", e);
		}
	}

	/**
	 * 
	 * <p>查询角色详细信息</p>
	 * @param roleId 角色Id
	 * @return 角色对象
	 * @throws BizException
	 * @author: 徐冉 - xu_r@neusoft.com
	 * @data: Create on Dec 2, 2009 5:26:04 PM
	 */
	@BizExecution
	public Role queryRoleById(String roleId) throws BizException {
		try {
			Role role = roleDAO.queryRoleById(roleId);
			return role;
		} catch (Exception e) {
			throw new BizException("0400209A", e);
		}
	}

	/**
	 * 
	 * <p>查询当前用户可管理的角色树
	 * 判断当前用户是否为超级管理员，超级管理员返回所有角色，否则查询当前用户拥有的角色级子角色</p>
	 * @return 返回登陆用户可见的角色节点集合。
	 * @throws BizException
	 * @author: 徐冉 - xu_r@neusoft.com
	 * @data: Create on Dec 2, 2009 5:26:41 PM
	 */
	@BizExecution
	public TreeNode queryRoleTree() throws BizException {
		try {
			WeeSecurityInfo wsi = WeeSecurityInfo.getInstance();
			String userId = wsi.getUserId();
			List<TreeNode> nodes = null;
//			TreeNode root = null;
			TreeNode root = new TreeNode();
			nodes = roleDAO.queryAllRoleTree();
			for (TreeNode n : nodes) {
				String key = n.getKey();
				if (key.equals("root")) {
					root = n;
					root.setIsFolder(true);
				}
			}
			TreeNode r = null;
			if (wsi.isAdminRole(userId)) {
				r = TreeUtil.createTreeRelation(nodes);
				r.setIsFolder(true);
				r.setKey(root.getKey());
				r.setTitle(root.getTitle());
				root = r;
			} else {
				nodes = roleDAO.queryRoleTree(userId);
				r = TreeUtil.createTreeRelation(nodes);
				List<TreeNode> tmp = r.getChildren();
				for (TreeNode t : tmp) {
					t.setParentId("root");
				}
				r.setIsFolder(true);
				r.setKey(root.getKey());
				r.setTitle(root.getTitle());
				root = r;
			}
			return r;
		} catch (Exception e) {
			throw new BizException("0400201A", e);
		}
	}

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
	public TreeNode queryRoleTreeWithoutAnoRole() throws BizException {
		try {
			WeeSecurityInfo wsi = WeeSecurityInfo.getInstance();
			String userId = wsi.getUserId();
			String anonymousRoleIds[] = wsi.getAnonymousRoleId();
			List<TreeNode> nodes = null;
			TreeNode root = new TreeNode();
			nodes = roleDAO.queryAllRoleTree();
			Collection<TreeNode> removedNodes = new ArrayList<TreeNode>();
			for (TreeNode n : nodes) {
				String key = n.getKey();
				if (key.equals("root")) {
					root = n;
					root.setIsFolder(true);
				}
				for (String anonymousRoleId : anonymousRoleIds) {
					if (anonymousRoleId.equals(key)) {
						removedNodes.add(n);
					}
				}
			}
			nodes.removeAll(removedNodes);
			TreeNode r = null;
			if (wsi.isAdminRole(userId)) {
				r = TreeUtil.createTreeRelation(nodes);
				r.setIsFolder(true);
				r.setKey(root.getKey());
				r.setTitle(root.getTitle());
				root = r;
			} else {
				nodes = roleDAO.queryRoleTree(userId);
				for (TreeNode n : nodes) {
					String key = n.getKey();
					for (String anonymousRoleId : anonymousRoleIds) {
						if (anonymousRoleId.equals(key)) {
							removedNodes.add(n);
						}
					}
				}
				nodes.removeAll(removedNodes);
				r = TreeUtil.createTreeRelation(nodes);
				List<TreeNode> tmp = r.getChildren();
				for (TreeNode t : tmp) {
					t.setParentId("root");
				}
				r.setIsFolder(true);
				r.setKey(root.getKey());
				r.setTitle(root.getTitle());
				root = r;
			}
			return r;
		} catch (Exception e) {
			throw new BizException("0400201A", e);
		}
	}

	/**
	 * 
	 * <p>修改角色</p>
	 * @param role 角色对象
	 * @throws BizException
	 * @author: 徐冉 - xu_r@neusoft.com
	 * @data: Create on Dec 2, 2009 5:27:17 PM
	 */
	@BizExecution
	public void updateRole(Role role) throws BizException {
		try {
			roleDAO.updateRole(role);
		} catch (Exception e) {
			throw new BizException("0400203A", e);
		}
	}

	/**
	* 
	* <p>查询非本角色下的用户</p>
	* @param role 角色对象
	* @throws BizException
	* @author: 徐冉 - xu_r@neusoft.com
	* @data: Create on Dec 2, 2009 5:27:17 PM
	*/
	@BizExecution
	public List<UserAccount> queryUsersOfOtherRole(String roleId, String unitId, Pagination pagination)
			throws BizException {
		try {
			List<UserAccount> list = userDAO.queryUserListOfOtherRole(roleId, unitId, pagination);
			return list;
		} catch (Exception e) {
			throw new BizException("0400205A", e);
		}
	}

	/**
	 * 
	 * <p>根据条件查询角色下用户信息</p>
	 * @param roleQC 同角色相关的查询条件（
	 *  roleId 选中角色ID,
	 *  loginRId 登陆用户的角色ID,
	 *  userId 登陆用户ID,
	 *  unitId 登陆用户所属组织ID)
	 * @param queryUserCOND 同用户相关的查询条件
	 * @param pagination 分页对象
	 * @return 返回UserAccount对象集合
	 * @throws BizException
	 * @author: 【徐冉】 - 【xu_r@neusoft.com】
	 * @data: Create on 2010-1-7 上午09:49:00
	 */
	@BizExecution
	public List<UserAccount> queryUserListOfRoleByCQ(RoleQC roleQC, UserQC queryUserCOND, Pagination pagination)
			throws BizException {
		try {
			List<UserAccount> list = userDAO.queryUserListOfRoleByCQ(roleQC, queryUserCOND, pagination);
			return list;
		} catch (Exception e) {
			throw new BizException("0400205A", e);
		}
	}

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
	public List<UserAccount> queryUserListOfOtherRoleByCQ(String roleId, String unitId, UserQC queryUserCOND,
			Pagination pagination) throws BizException {
		try {
			List<UserAccount> list = userDAO.queryUserListOfOtherRoleByCQ(roleId, unitId, queryUserCOND, pagination);
			return list;
		} catch (Exception e) {
			throw new BizException("0400205A", e);
		}
	}
	
	/**
	 * <p>同级下是否重名</p>
	 * @return
	 * @throws BizExcepton
	 * @author: 李志伟 - li.zhw@neusoft.com
	 * @data: Create on Apr 27, 2010 9:40:03 AM
	 */
	@BizExecution
	public boolean isExistSameParent(Role role) throws BizException{
		try{
			return roleDAO.isExistSameParent(role);
		}catch (Exception e) {
			throw new BizException("0400209A", e);
		}
	}
	
	/**
	 * <p>查询除需要移动的节点及其子节点的角色树</p>
	 * @param unitId
	 * @return
	 * @throws BizException
	 * @author: 李志伟 - li.zhw@neusoft.com
	 * @data: Create on May 15, 2010 12:37:36 PM
	 */
	@BizExecution
	public TreeNode queryUnitTreeForUpdateParent(String roleId) throws BizException{
		try {
			WeeSecurityInfo wsi = WeeSecurityInfo.getInstance();
			String userId = wsi.getUserId();
			List<TreeNode> nodes = null;
			TreeNode root = null;
			nodes = roleDAO.queryUnitTreeForUpdateParent(roleId);
			for (TreeNode n : nodes) {
				String key = n.getKey();
				if (key.equals("root")) {
					root = n;
					root.setIsFolder(true);
				}
			}
			TreeNode r = null;
			if (wsi.isAdminRole(userId)) {
				r = TreeUtil.createTreeRelation(nodes);
				r.setIsFolder(true);
				r.setKey(root.getKey());
				r.setTitle(root.getTitle());
				root = r;
			} else {
				nodes = roleDAO.queryUnitTreeByUnitIdForUpdateParent(userId, roleId);
				r = TreeUtil.createTreeRelation(nodes);
				List<TreeNode> tmp = r.getChildren();
				for (TreeNode t : tmp) {
					t.setParentId("root");
				}
				r.setIsFolder(true);
				r.setKey(root.getKey());
				r.setTitle(root.getTitle());
				root = r;
			}
			return r;
		} catch (Exception e) {
			throw new BizException("0400201A", e);
		}
	}
	
	/**
	 * <p>修改角色父子关系</p>
	 * @param unitId
	 * @param newParentId
	 * @throws BizException
	 * @author: 李志伟 - li.zhw@neusoft.com
	 * @data: Create on May 15, 2010 12:37:45 PM
	 */
	@BizExecution
	public void updateRoleParent(String roleId,String newParentId) throws BizException{
		try{
			roleDAO.updateRoleParent(roleId, newParentId);
		}catch(Exception e){
			throw new BizException("0400203A", e);
		}
	}
}
