package cn.ursun.console.app.console.role.action;

import cn.ursun.console.app.console.role.bizservice.RoleBS;
import cn.ursun.console.app.domain.Role;
import cn.ursun.platform.core.action.WeeAction;
import cn.ursun.platform.core.exception.BizException;
import cn.ursun.platform.ps.domain.TreeNode;

public class MaintainRoleAC extends WeeAction {

	private RoleBS roleBS;

	/**
	 * 角色树-根角色
	 */
	private TreeNode root;
	/**
	 * 角色信息
	 */
	private Role role;
	
	private String[] userIds;
	
	private boolean success;
	
	private boolean flag;
	
	private String newRoleId;
	/**
	 * <p>保存新增角色</p>
	 * 
	 * @return
	 * @author: 徐冉 - xu_r@neusoft.com
	 * @throws BizException 
	 * @data: Create on 2009-12-15 下午02:13:50
	 */
	public String addRole() throws Exception {
		success=false;
		String roleName=role.getRoleName();
		String description=role.getDescription();
		String parentId=role.getParentId();
		if(roleName!=null&&description!=null&&parentId!=null){
			if(!roleName.equals("")){
				Role role=new Role();
				String gId=cn.ursun.platform.core.util.IDGenerator.generateId();
				role.setRoleName(roleName);
				role.setDescription(description);
				role.setParentId(parentId);
				role.setRoleId(gId);
				roleBS.addRole(role);
				success=true;
				newRoleId=gId;
			}
		}
		return JSON;
	}

	/**
	 * <p>保存修改角色</p>
	 * 
	 * @return
	 * @author: 徐冉 - xu_r@neusoft.com
	 * @throws BizException 
	 * @data: Create on 2009-12-15 下午02:13:50
	 */
	public String updateRole() throws Exception {
		success=false;
		String roleName=role.getRoleName();
		String description=role.getDescription();
		String roleId=role.getRoleId();
		if(roleName!=null&&description!=null&&roleId!=null){
			Role role=new Role();
			role.setRoleName(roleName);
			role.setDescription(description);
			role.setRoleId(roleId);
			roleBS.updateRole(role);
			success=true;
		}
		return JSON;
	}

	/**
	 * <p>删除角色</p>
	 * 
	 * @param ids
	 * @return
	 * @author: 徐冉 - xu_r@neusoft.com
	 * @throws BizException 
	 * @data: Create on 2009-12-15 下午02:13:50
	 */
	public String deleteRole() throws Exception {
		success=false;
		String roleId=role.getRoleId();
		if(roleId!=null){
			roleBS.deleteRole(roleId);
			success=true;
		}
		return JSON;
	}

	/**
	 * <p>为角色添加用户</p>
	 * @return
	 * @author: 徐冉 - xu_r@neusoft.com
	 * @throws BizException 
	 * @data: Create on 2009-12-15 下午02:13:50
	 */
	public String addUsersForRole() throws Exception {
		String roleId=role.getRoleId();
		if(roleId!=null&&userIds!=null&&userIds.length>0){
			roleBS.addUserForRole(roleId, userIds);
		}	
		return JSON;
	}

	/**
	 * 
	 * <p>为角色删除用户</p>
	 * 
	 * @return
	 * @author: 徐冉 - xu_r@neusoft.com
	 * @throws BizException 
	 * @data: Create on 2009-12-15 下午02:13:50
	 */
	public String deleteUsersForRole() throws Exception {
		String roleId=role.getRoleId();
		if(roleId!=null&&userIds!=null&&userIds.length>0){
			roleBS.deleteUserForRole(roleId, userIds);
		}	
		return JSON;
	}

	/**
	 * <p>修改角色父节点</p>
	 * @return
	 * @throws Exception
	 * @author: 李志伟 - li.zhw@neusoft.com
	 * @data: Create on May 17, 2010 1:56:42 PM
	 */
	public String updateRoleParent() throws Exception {
		roleBS.updateRoleParent(role.getId(), role.getParentId());
		return JSON;
	}
	
	/**
	 * 
	 * <p>查询当前用户是否为超级管理员</p>
	 * @return
	 * @throws Exception
	 * @author: 【徐冉】 - 【xu_r@neusoft.com】
	 * @data: Create on 2010-1-4 下午01:43:29
	 */
	public String isAdmin() throws Exception {

		return null;
	}

	public String isExist() throws Exception {
		flag=roleBS.isExistSameParent(role);
		return JSON;
	}
	
	public void setRoleBS(RoleBS roleBS) {
		this.roleBS = roleBS;
	}

		
	
	public TreeNode getRoot() {
		return root;
	}

	
	public void setRoot(TreeNode root) {
		this.root = root;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	
	public String[] getUserIds() {
		return userIds;
	}

	
	public void setUserIds(String[] userIds) {
		this.userIds = userIds;
	}

	
	

	public Boolean getSuccess() {
		return success;
	}

	
	public boolean isFlag() {
		return flag;
	}

	
	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	
	public String getNewRoleId() {
		return newRoleId;
	}

	
	public void setNewRoleId(String newRoleId) {
		this.newRoleId = newRoleId;
	}

}
