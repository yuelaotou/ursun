package cn.ursun.console.app.console.role.action;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import cn.ursun.console.app.console.role.bizservice.RoleBS;
import cn.ursun.console.app.console.role.dto.RoleQC;
import cn.ursun.console.app.console.user.bizservice.UserBS;
import cn.ursun.console.app.console.user.dto.UserQC;
import cn.ursun.console.app.domain.Role;
import cn.ursun.console.app.domain.UserAccount;
import cn.ursun.platform.core.action.WeePaginationSupportAction;
import cn.ursun.platform.core.dto.Pagination;
import cn.ursun.platform.core.exception.BizException;
import cn.ursun.platform.ps.domain.TreeNode;
import cn.ursun.platform.ps.security.WeeSecurityInfo;

public class ShowRoleAC extends WeePaginationSupportAction {

	private Pagination pagination;

	private String unitId;

	private RoleBS roleBS;

	private UserBS userBS;
	
	private String roleIds[];
	
	private String anonymousRoleIds[];
	
	private UserQC userQC;
	/**
	 * 角色树-根角色
	 */
	private TreeNode root;

	/**
	 * 是否是超级管理员
	 */
	private boolean isAdmin;

	private List<UserAccount> userList;

	private Role role;

	private TreeNode roleTree;
	/**
	 * 跳转类型标识
	 */
	private String ftype;
	/**
	 * 
	 * <p>初始化页面</p>
	 * @return
	 * @throws BizException
	 * @author: 【徐冉】 - 【xu_r@neusoft.com】
	 * @data: Create on 2010-1-6 下午05:14:52
	 */
	public String init()  throws BizException {
		WeeSecurityInfo wsi=WeeSecurityInfo.getInstance();
		roleIds=wsi.getUserRoleIds();
		return "init";
	}
	
	/**
	 * <p>【根据参数ftype 进行方法的跳转】</p>
	 * @return
	 * @throws Exception
	 * @author: 李志伟 - li.zhw@neusoft.com
	 * @data: Create on Mar 16, 2010 9:31:32 AM
	 */
	public String forward() throws Exception{
		if(StringUtils.isEmpty(ftype)){
			ftype="query";
		}
		if("query".equals(ftype)){
			return queryUsersOfRole();
		}
		if("add".equals(ftype)){
			return gotoAddList();
		}
		if("queryo".equals(ftype)){
			return queryUsersOfOtherRole();
		}
		return "";
	}
	/**
	 * 
	 * <p>跳转到为选中角色添加用户页面</p>
	 * @return
	 * @throws BizException
	 * @author: 【徐冉】 - 【xu_r@neusoft.com】
	 * @data: Create on 2010-1-6 下午05:15:11
	 */
	public String gotoAddList() throws BizException {
		return "addList";
	}
	
	/**
	 * <p>查询当前用户可管理的角色树</p>
	 * @return
	 * @author: 徐冉 - xu_r@neusoft.com
	 * @throws BizException 
	 * @data: Create on 2009-12-15 下午02:13:50
	 */
	public String queryRoleTree() throws Exception {
		root = roleBS.queryRoleTree();
		return JSON;
	}

	/**
	 * <p>查询角色详细信息</p>
	 * @return
	 * @author: 徐冉 - xu_r@neusoft.com
	 * @throws BizException 
	 * @data: Create on 2009-12-15 下午02:13:50
	 */
	public String queryRoleById() throws Exception {

		return null;
	}

	/**
	 * <p>查询选中角色下的属于登陆用户所属组织的用户信息</p>
	 * @return
	 * @author: 徐冉 - xu_r@neusoft.com
	 * @throws BizException 
	 * @data: Create on 2009-12-15 下午02:13:50
	 */
	public String queryUsersOfRole() throws Exception {
		String roleId=null;
		if(role==null){
			roleId="root";
		}else{
			roleId=role.getRoleId();
		}
		WeeSecurityInfo wsi=WeeSecurityInfo.getInstance();
		String loginRIds[]=wsi.getUserRoleIds();
		String loginRId="otherRole";
		for(String id:loginRIds){
			if(id.equals("adminRole")){
				loginRId=id;
			}
		}
		String userId=wsi.getUserId();
		unitId=wsi.getUserDeptId();
		if (pagination == null) {
			pagination = new Pagination();
		}
		RoleQC roleQC=new RoleQC();
		roleQC.setLoginRId(loginRId);
		roleQC.setRoleId(roleId);
		roleQC.setUnitId(unitId);
		roleQC.setUserId(userId);
		if(userQC==null){
			userList = userBS.queryUserListOfRole(roleId,loginRId,userId,unitId, pagination);
		}else{
			userList=roleBS.queryUserListOfRoleByCQ(roleQC, userQC, pagination);
		}
		
		return "list";
	}
	
	
	/**
	 * 
	 * <p>查询其他角色下的并属于登陆用户所属组织的用户</p>
	 * @return
	 * @author: 徐冉 - xu_r@neusoft.com
	 * @throws BizException 
	 * @data: Create on 2009-12-15 下午02:13:50
	 */
	public String queryUsersOfOtherRole() throws BizException {
		String roleId=role.getRoleId();
		if (pagination == null) {
			pagination = new Pagination();
		}
		if(userQC==null){
			userList = roleBS.queryUsersOfOtherRole(roleId, unitId, pagination);
		}else{
			userList=roleBS.queryUserListOfOtherRoleByCQ(roleId, unitId, userQC, pagination);
		}
		
		return "add";
	}
	
	/**
	 * 
	 * <p>查询其他角色下的并属于登陆用户所属组织的用户</p>
	 * @return
	 * @author: 徐冉 - xu_r@neusoft.com
	 * @throws BizException 
	 * @data: Create on 2009-12-15 下午02:13:50
	 */
	public String queryUsersOfOtherRoleJSON() throws BizException {
		String roleId=role.getRoleId();
		if (pagination == null) {
			pagination = new Pagination();
		}
		if(userQC==null){
			userList = roleBS.queryUsersOfOtherRole(roleId, unitId, pagination);
		}else{
			userList=roleBS.queryUserListOfOtherRoleByCQ(roleId, unitId, userQC, pagination);
		}
		
		return JSON;
	}

	/**
	 * <p>查询可作为移动目标的角色树</p>
	 * @return
	 * @throws Exception
	 * @author: 李志伟 - li.zhw@neusoft.com
	 * @data: Create on May 15, 2010 12:54:00 PM
	 */
	public String queryRoleTreeForUpdateParent() throws Exception {
		roleTree=roleBS.queryUnitTreeForUpdateParent(role.getId());
		return JSON;
	}
	
	/**
	 * 
	 * <p>查询匿名角色Id</p>
	 * @return 
	 * @throws BizException
	 * @author: 徐冉 - xu_r@neusoft.com
	 * @data: Create on 2010-1-26 下午03:31:16
	 */
	public String queryAnonymousRoleIds() throws BizException{
		WeeSecurityInfo wsi=WeeSecurityInfo.getInstance();
		anonymousRoleIds=wsi.getAnonymousRoleId();
		return JSON;
	}
	public boolean getIsAdmin() {
		return isAdmin;
	}

	public List<UserAccount> getUserList() {
		return userList;
	}

	public void setUserList(List<UserAccount> userList) {
		this.userList = userList;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public void setRoleBS(RoleBS roleBS) {

		this.roleBS = roleBS;

	}

	public TreeNode getRoot() {
		return root;
	}

	public void setUserBS(UserBS userBS) {
		this.userBS = userBS;
	}

	public Pagination getPagination() {
		return pagination;
	}

	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	
	public String[] getRoleIds() {
		return roleIds;
	}

	
	public void setRoleIds(String[] roleIds) {
		this.roleIds = roleIds;
	}
	
	public UserQC getUserQC() {
		return userQC;
	}
	
	public void setUserQC(UserQC userQC) {
		this.userQC = userQC;
	}
	
	public String[] getAnonymousRoleIds() {
		return anonymousRoleIds;
	}
	
	public void setAnonymousRoleIds(String[] anonymousRoleIds) {
		this.anonymousRoleIds = anonymousRoleIds;
	}

	
	public String getFtype() {
		return ftype;
	}

	
	public void setFtype(String ftype) {
		this.ftype = ftype;
	}

	
	public TreeNode getRoleTree() {
		return roleTree;
	}

	
	public void setRoleTree(TreeNode roleTree) {
		this.roleTree = roleTree;
	}
	
	
	
	
}
