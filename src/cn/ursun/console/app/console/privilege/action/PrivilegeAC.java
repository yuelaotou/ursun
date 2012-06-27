package cn.ursun.console.app.console.privilege.action;

import java.util.Iterator;
import java.util.List;

import cn.ursun.console.app.console.privilege.bizservice.PrivilegeBS;
import cn.ursun.console.app.console.role.bizservice.RoleBS;
import cn.ursun.platform.core.action.WeeAction;
import cn.ursun.platform.ps.domain.TreeNode;
import cn.ursun.platform.ps.security.WeeSecurityInfo;

/**
 * <p> Title: 角色授权管理</p>
 * <p> Description: [角色授权管理]</p>
 * <p> Created on 2009-11-17</p>
 * <p> Copyright: Copyright (c) 2008</p>
 * <p> Company: 东软软件股份有限公司</p>
 * @author 宋成山 songchengshan@neusoft.com
 * @version 1.0
 */
public class PrivilegeAC extends WeeAction {

	private static final long serialVersionUID = -4683363018762354070L;

	/**
	 * 权限bs类
	 */
	private PrivilegeBS privilegeBS;

	/**
	 * 资源ID
	 */
	private String[] resourceIds = null;

	/**
	 * 角色ID
	 */
	private String roleId = null;

	/**
	 * 角色树-根角色
	 */
	private TreeNode rootRole;

	/**
	 * 资源树-根角色
	 */
	private TreeNode rootRes;

	/**
	 * 角色BS
	 */
	private RoleBS roleBS;

	/**
	 * 是否本角色标志位
	 */
	private boolean flag = true;

	/**
	 * 是否管理员标志位
	 */
	private boolean adminFlag = true;

	/**
	 * 父节点角色id
	 */
	private String parentRoleId = "";

	/**
	 * 
	 * <p>跳转权限管理页面</p>
	 * @return
	 * @throws Exception
	 * @author: 于宝淇 - yu-bq@neusoft.com
	 * @data: Create on Dec 7, 2009 3:57:48 PM
	 */
	public String forwardListPage() throws Exception {
		return "list";
	}

	/**
	 * 查询当前用户可管理的角色树
	 * @return
	 * @throws Exception
	 * @author: 宋成山 songchengshan@neusoft.com
	 */
	public String queryRoleList() throws Exception {
		// 判断当前用户是否为超级管理员，超级管理员返回所有角色，
		// 否则查询当前用户拥有的角色级子角色（用户不可修改自己本身角色的权限）
		rootRole = roleBS.queryRoleTree();
		return JSON;
	}

	/**
	 * 
	 * <p>查询资源树</p>
	 * @return
	 * @throws Exception
	 * @author: 于宝淇 - yu-bq@neusoft.com
	 * @data: Create on Dec 10, 2009 10:38:10 AM
	 */
	public String queryResourceListOfRole() throws Exception {
		rootRes = privilegeBS.showResourceTree(parentRoleId, roleId);
		return JSON;
	}

	/**
	 * <p>Discription:判断是否本角色点击的角色树节点</p>
	 * Created on 2009-11-17
	 * @author: 宋成山 songchengshan@neusoft.com
	 * @update: [日期YYYY-MM-DD] [更改人姓名]
	 */
	public String isMe() throws Exception {
		String userID = WeeSecurityInfo.getInstance().getUserId();
		flag = privilegeBS.isMe(userID, roleId);

		return JSON;
	}

	/**
	 * <p>Discription:判断是否是管理员</p>
	 * Created on 2009-11-17
	 * @author: 宋成山 songchengshan@neusoft.com
	 * @update: [日期YYYY-MM-DD] [更改人姓名]
	 */
	public String isAdmin() throws Exception {
		String userID = WeeSecurityInfo.getInstance().getUserId();
		adminFlag = WeeSecurityInfo.getInstance().isAdminRole(userID);

		return JSON;
	}

	/**
	 * <p>Discription:给角色赋权</p>
	 * Created on 2009-11-17
	 * @author: 宋成山 songchengshan@neusoft.com
	 * @update: [日期YYYY-MM-DD] [更改人姓名]
	 */
	public String updatePrivilege() throws Exception {
		// 建立角色与资源对应关系
		privilegeBS.updatePrivilege(roleId, resourceIds);

		// 刷新内存里的资源绑定信息
		WeeSecurityInfo.getInstance().refresh();
		return JSON;
	}

	/**
	 * <p>Discription:[根据角色ID绑定资源]</p>
	 * Created on 2010-6-2
	 * @author: [杨博] - [yangb@neusoft.com]
	 *
	 */
	public String queryResourceListTreeOfRole() throws Exception {
		List<TreeNode> list = privilegeBS.queryResourceTreeOfRole(roleId);
		TreeNode node = null;
		int i = 0;
		if (list.size() > 0) {
			resourceIds = new String[list.size()];
			for (Iterator<TreeNode> iterator = list.iterator(); iterator.hasNext();) {
				node = iterator.next();
				resourceIds[i] = node.getKey();
				i++;
			}
		}
		return JSON;
	}

	public String[] getResourceIds() {
		return resourceIds;
	}

	public void setResourceIds(String[] resourceIds) {
		this.resourceIds = resourceIds;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public TreeNode getRootRole() {
		return rootRole;
	}

	public void setRootRole(TreeNode rootRole) {
		this.rootRole = rootRole;
	}

	public TreeNode getRootRes() {
		return rootRes;
	}

	public void setRootRes(TreeNode rootRes) {
		this.rootRes = rootRes;
	}

	public void setPrivilegeBS(PrivilegeBS privilegeBS) {
		this.privilegeBS = privilegeBS;
	}

	/**
	 *
	 * @param roleBS
	 */
	public void setRoleBS(RoleBS roleBS) {
		this.roleBS = roleBS;
	}

	/**
	 *
	 * @return 
	 */
	public boolean getFlag() {
		return flag;
	}

	/**
	 *
	 * @param flag
	 */
	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	/**
	 *
	 * @return 
	 */
	public boolean getAdminFlag() {
		return adminFlag;
	}

	/**
	 *
	 * @param adminFlag
	 */
	public void setAdminFlag(boolean adminFlag) {
		this.adminFlag = adminFlag;
	}

	/**
	 *
	 * @return 
	 */
	public String getParentRoleId() {
		return parentRoleId;
	}

	/**
	 *
	 * @param parentRoleId
	 */
	public void setParentRoleId(String parentRoleId) {
		this.parentRoleId = parentRoleId;
	}

}
