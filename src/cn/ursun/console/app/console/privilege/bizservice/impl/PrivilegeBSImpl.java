/**
 * 文件名：PrivilegeBSImpl.java
 * 
 * 创建人：于宝淇 - yu-bq@neusoft.com
 * 
 * 创建时间：Dec 7, 2009 3:35:41 PM
 * 
 * 版权所有：东软软件股份有限公司
 */
package cn.ursun.console.app.console.privilege.bizservice.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import cn.ursun.console.app.console.privilege.bizservice.PrivilegeBS;
import cn.ursun.console.app.dao.PrivilegeDAO;
import cn.ursun.console.app.dao.ResourceDAO;
import cn.ursun.console.app.dao.RoleDAO;
import cn.ursun.platform.core.exception.BizException;
import cn.ursun.platform.ps.bizlog.aop.annotation.BizExecution;
import cn.ursun.platform.ps.bizservice.WeeBizService;
import cn.ursun.platform.ps.domain.TreeNode;
import cn.ursun.platform.ps.security.WeeSecurityInfo;
import cn.ursun.platform.ps.util.TreeUtil;

/**
 * <p>维护角色权限BS实现类</p>
 * @author 于宝淇 - yu-bq@neusoft.com
 * @version 1.0
 */
public class PrivilegeBSImpl extends WeeBizService implements PrivilegeBS {

	/**
	 * 角色管理DAO类
	 */
	private RoleDAO roleDAO = null;

	/**
	 * 资源管理DAO类
	 */
	private ResourceDAO resourceDAO = null;

	/**
	 * 权限管理DAO类
	 */
	private PrivilegeDAO privilegeDAO = null;

	/**
	 * <p>根据某个角色id查询相应的角色树</p>
	 * @param roleId
	 * @return
	 * @throws BizException
	 * @author: 于宝淇 - yu-bq@neusoft.com
	 * @data: Create on Dec 7, 2009 3:35:42 PM
	 */
	@BizExecution
	public List<TreeNode> queryResourceOfRole() throws BizException {
		try{
			List<TreeNode> treeList = resourceDAO.queryResourceTree();
			return treeList;
		}catch(Exception e){
			throw new BizException("0400501A", e);
		}		
	}

	/**
	 * <p>查询全部角色</p>
	 * @return
	 * @throws BizException
	 * @author: 于宝淇 - yu-bq@neusoft.com
	 * @data: Create on Dec 7, 2009 3:35:42 PM
	 */
	@BizExecution
	public List<TreeNode> queryRoleList() throws BizException {
		try{
			List<TreeNode> treeList = roleDAO.queryAllRoleTree();
			return treeList;
		}catch(Exception e){
			throw new BizException("0400501B", e);
		}
	}

	/**
	 * <p>更新角色和对应资源的关系</p>
	 * @param roleId
	 * @param resIds
	 * @throws BizException
	 * @author: 于宝淇 - yu-bq@neusoft.com
	 * @data: Create on Dec 7, 2009 3:35:42 PM
	 */
	@BizExecution
	public void updatePrivilege(String roleId, String[] resIds) throws BizException {		
		try{
			privilegeDAO.updatePrivilege(roleId, resIds);
		}catch(Exception e){
			throw new BizException("0400501C", e);
		}

	}

	/**
	 * 
	 * <p>得到父节点信息</p>
	 * @param parentId
	 * @return
	 * @throws BizException
	 * @author: 于宝淇 - yu-bq@neusoft.com
	 * @data: Create on Jan 5, 2010 2:38:57 PM
	 */
	@BizExecution
	public TreeNode getParentTreeNode(String parentId) throws BizException {		
		try{
			return privilegeDAO.getParentTreeNode(parentId);
		}catch(Exception e){
			throw new BizException("0400501D", e);
		}
	}
	
	/**
	 * 
	 * <p>取所有父亲节点</p>
	 * @param parentId
	 * @return
	 * @throws BizException
	 * @author: 于宝淇 - yu-bq@neusoft.com
	 * @data: Create on Jan 5, 2010 2:38:57 PM
	 */
	@BizExecution
	public List<TreeNode> getAllParentTreeNode() throws BizException {		
		try{
			return privilegeDAO.getAllParentTreeNode();
		}catch(Exception e){
			throw new BizException("0400501D", e);
		}
	}

	/**
	 * 
	 * <p>getter方法</p>
	 * @return
	 * @author: 于宝淇 - yu-bq@neusoft.com
	 * @data: Create on Dec 7, 2009 5:11:57 PM
	 */
	public RoleDAO getRoleDAO() {
		return roleDAO;
	}

	/**
	 * 
	 * <p>setter方法</p>
	 * @param roleDAO
	 * @author: 于宝淇 - yu-bq@neusoft.com
	 * @data: Create on Dec 7, 2009 5:12:01 PM
	 */
	public void setRoleDAO(RoleDAO roleDAO) {
		this.roleDAO = roleDAO;
	}

	/**
	 * 
	 * <p>getter方法</p>
	 * @return
	 * @author: 于宝淇 - yu-bq@neusoft.com
	 * @data: Create on Dec 7, 2009 5:11:57 PM
	 */
	public ResourceDAO getResourceDAO() {
		return resourceDAO;
	}

	/**
	 * 
	 * <p>setter方法</p>
	 * @param resourceDAO
	 * @author: 于宝淇 - yu-bq@neusoft.com
	 * @data: Create on Dec 7, 2009 5:12:01 PM
	 */
	public void setResourceDAO(ResourceDAO resourceDAO) {
		this.resourceDAO = resourceDAO;
	}

	/**
	 * 
	 * <p>根据roleid查询该角色对应的资源id</p>
	 * @param roleId
	 * @return
	 * @throws BizException
	 * @author: 于宝淇 - yu-bq@neusoft.com
	 * @data: Create on Dec 8, 2009 5:02:15 PM
	 */
	@BizExecution
	public List<TreeNode> queryResourceTreeOfRole(String roleId) throws BizException {
		try{
			List<TreeNode> treeList = privilegeDAO.queryResourceTreeOfRole(roleId);
			return treeList;
		}catch(Exception e){
			throw new BizException("0400501E", e);
		}
	}

	/**
	 * <p>根据用户id查询资源树</p>
	 * @param userId
	 * @return
	 * @throws BizException
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-23 下午09:51:19
	 */
	@BizExecution
	public List<TreeNode> queryResourceTreeByUserId(String userId) throws BizException {
		try{
			List<TreeNode> treeList = privilegeDAO.queryResourceTreeByUserId(userId);
			return treeList;
		}catch(Exception e){
			throw new BizException("0400501F", e);
		}
	}

	/**
	 * <p>判断是否本角色点击的角色树节点</p>
	 * 
	 * @param userId
	 * @param roleId
	 * @return
	 * @throws BizException
	 * @author: 段鹏 - duanp@neusoft.com 
	 * @date: Created on 2009-4-26 下午03:44:12
	 */
	@BizExecution
	public boolean isMe(String userId, String roleId) throws BizException {
		try{
			return privilegeDAO.isMe(userId, roleId);
		}catch(Exception e){
			throw new BizException("0400501G", e);
		}
	}

	/**
	 *
	 * @return 
	 */
	public PrivilegeDAO getPrivilegeDAO() {
		return privilegeDAO;
	}

	/**
	 *
	 * @param privilegeDAO
	 */
	public void setPrivilegeDAO(PrivilegeDAO privilegeDAO) {
		this.privilegeDAO = privilegeDAO;
	}

	/**
	 * 
	 * <p>级联删除子节点的资源映射</p>
	 * @param roleId
	 * @throws BizException
	 * @author: 于宝淇 - yu-bq@neusoft.com
	 * @data: Create on Jan 6, 2010 4:47:17 PM
	 */
	@BizExecution
	public void recDelResourceRoleMapping(String roleId) throws BizException {		
		try{
			privilegeDAO.recDelResourceRoleMapping(roleId);
		}catch(Exception e){
			throw new BizException("0400501H", e);
		}
	}

	/**
	 * 
	 * <p>根据角色id查询相应的资源树结构</p>
	 * @param roleId
	 * @return
	 * @throws Exception
	 * @author: 于宝淇 - yu-bq@neusoft.com
	 * @data: Create on Jan 7, 2010 8:33:40 AM
	 */
	@BizExecution
	private List<TreeNode> queryResourceForRole(String roleId) throws Exception {
		try{
			String userID = WeeSecurityInfo.getInstance().getUserId();
			if (WeeSecurityInfo.getInstance().isAdminRole(userID)) {
				if (this.isMe(userID, roleId)) {// 如果是管理员选中自己的角色节点，则可以看到整个资源树
					return this.queryResourceOfRole();
				} else {// 管理员选中其他的角色节点，可以看到选择节点的父节点的资源树
					return this.queryResourceTreeOfRole(roleId);
				}
			} else {// 非管理员可以看到选择节点的父节点的资源树
				return this.queryResourceTreeOfRole(roleId);
			}
		}catch(Exception e){
			throw new BizException("0400501I", e);
		}
	}

	/**
	 * 
	 * <p>展示资源树</p>
	 * @param parentRoleId
	 * @param roleId
	 * @return
	 * @throws Exception
	 * @author: 于宝淇 - yu-bq@neusoft.com
	 * @data: Create on Jan 7, 2010 8:46:11 AM
	 */
	@BizExecution
	public TreeNode showResourceTree(String parentRoleId, String roleId) throws Exception {				
		try{
			List<TreeNode> newlist = new ArrayList<TreeNode>();
			TreeNode root = new TreeNode();
			// 查询父节点的树结构 查询父亲角色可访问的资源信息
			List<TreeNode> resourceList = queryResourceForRole(parentRoleId);
			if (resourceList.size() > 0) {//当前选中角色所能访问的资源只能是父亲角色所能访问资源的子集
				// 递归查询父角色树
				List<TreeNode> treeNodelist = this.getAllParentTreeNode();								
				newlist = resourceList;
				for(int j=0; j<resourceList.size(); j++){
					TreeNode parttreeNode = resourceList.get(j);				
					if(parttreeNode.getParentId()!=null){
						if (!StringUtils.isEmpty(parttreeNode.getParentId())) {								
							for(int i=0; i<treeNodelist.size();i++){
								TreeNode treeNode = treeNodelist.get(i);
								if(treeNode.getKey().equals(parttreeNode.getParentId())){
									if (!isExists(treeNode, newlist)) {
										newlist.add(treeNode);
										treeNodelist.remove(treeNode);
										//parttreeNode = treeNode;
										//跳出循环
										break;
									}
								}
							}
						}
					}
					
				}
				
				// 得到资源树的根节点
				TreeNode resourceRoot = privilegeDAO.getReourceRootNode();

				root = TreeUtil.createTreeRelation(newlist);
				root.setTitle(resourceRoot.getTitle());
				root.setTooltip(resourceRoot.getTooltip());
				root.setIsFolder(true);

				// 查询选择节点与资源的关系
				List<TreeNode> checkedNodeList = queryResourceTreeOfRole(roleId);

				//List<TreeNode> listNode = filterParentLeaf(checkedNodeList);
				
				for (TreeNode checkTreeNode : checkedNodeList) {
					for (TreeNode resourceTreeNode : newlist) {
						if (checkTreeNode.getKey().equals(resourceTreeNode.getKey())) {
							resourceTreeNode.setSelect(true);
							// 如果某个节点的子节点全部选中，则该父节点也需要选中
							setParentsTreeChecked(newlist, resourceTreeNode.getParentId());
						}
					}
				}

			} else {
				// 级联删除子节点的资源映射
				this.recDelResourceRoleMapping(roleId);
				root.setKey("nores");
				root.setTitle("该角色没有相关的资源");

			}

			WeeSecurityInfo.getInstance().refresh();
			return root;
		}catch(Exception e){
			throw new BizException("0400501J", e);
		}
	}
	
	/**
	 * 
	 * <p>过滤父节点是叶子类型的节点</p>
	 * @param list  数据库里保存的角色对应的资源id
	 * @return 返回的list里过滤了
	 * @throws BizException
	 * @author: 于宝淇 - yu-bq@neusoft.com
	 * @data: Create on Jan 29, 2010 2:43:31 PM
	 */
//	private List<TreeNode> filterParentLeaf(List<TreeNode> list)throws BizException{
//		List<TreeNode> newlist = new ArrayList<TreeNode>();
//		List<TreeNode> newlist2 = new ArrayList<TreeNode>();
//		List<String> strlist = new ArrayList<String>();
//		for(TreeNode node:list){
//			strlist.add(node.getKey());
//			newlist.add(node);
//			newlist2.add(node);
//		}
//		
//		for(TreeNode node:list){
//			if(strlist.contains(node.getParentId())){
//				TreeNode parentNode = getParentNode(newlist,node.getParentId());
//				if(parentNode!=null)
//					newlist2.remove(parentNode);					
//			}
//		}
//		return newlist2;
//	}
	
	/**
	 * 
	 * <p>返回父节点对象</p>
	 * @param nodelist
	 * @param parentId
	 * @return
	 * @author: 于宝淇 - yu-bq@neusoft.com
	 * @data: Create on Jan 29, 2010 2:56:12 PM
	 */
//	private TreeNode getParentNode(List<TreeNode> nodelist,String parentId){
//		for(TreeNode node:nodelist){
//			if(parentId.equals(node.getKey())){
//				return node;
//			}
//		}
//		
//		return null;
//	}

	/**
	 * <p>递归选中父节点</p>
	 * <p>如果某个节点的子节点全部选中，则该父节点也需要选中</p>
	 * @param treeList
	 * @param parentId
	 * @throws BizException
	 * @author: 于宝淇 - yu-bq@neusoft.com
	 * @data: Create on Jan 12, 2010 10:36:51 AM
	 */
	@BizExecution
	private void setParentsTreeChecked(List<TreeNode> treeList, String parentId) throws BizException {
		try{
			TreeNode treeNode = null;
			for (TreeNode loopNode : treeList) {
				if (loopNode.getKey().equals(parentId)) {
					treeNode = loopNode;
					break;
				}
			}

			if (treeNode != null) {
				List<TreeNode> childrenNodes = treeNode.getChildren();
				if (childrenNodes != null) {
					boolean checkFlag = true;
					for (TreeNode childrenNode : childrenNodes) {
						if (!childrenNode.getSelect()) {
							checkFlag = false;
							break;
						}
					}

					if (checkFlag) {
						treeNode.setSelect(true);
						if (!StringUtils.isEmpty(treeNode.getParentId())) {
							// 递归调用
							setParentsTreeChecked(treeList, treeNode.getParentId());
						}
					}
				}
			}			
		}catch(Exception e){
			throw new BizException("0400501K", e);
		}
	}

	/**
	 * 
	 * <p>递归查询父角色树</p>
	 * @param newList
	 * @param parentId
	 * @return
	 * @throws Exception
	 * @author: 于宝淇 - yu-bq@neusoft.com
	 * @data: Create on Jan 6, 2010 5:06:18 PM
	 */
	@BizExecution
	private List<TreeNode> getParentsTree(List<TreeNode> newList, String parentId) throws Exception {		
		try{
			TreeNode treeNode = this.getParentTreeNode(parentId);
			if (!isExists(treeNode, newList)) {
				newList.add(treeNode);
			}

			if (!StringUtils.isEmpty(treeNode.getParentId())) {
				getParentsTree(newList, treeNode.getParentId());
			}
			return newList;
		}catch(Exception e){
			throw new BizException("0400501K", e);
		}
	}

	/**
	 * 
	 * <p>该节点是否已经存在</p>
	 * @param treeNode
	 * @param newList
	 * @return
	 * @author: 于宝淇 - yu-bq@neusoft.com
	 * @data: Create on Jan 6, 2010 5:06:47 PM
	 */
	private boolean isExists(TreeNode treeNode, List<TreeNode> newList) {
		for (TreeNode tmpNode : newList) {
			if (tmpNode.getKey().equals(treeNode.getKey())) {
				return true;
			}
		}
		return false;
	}
}
