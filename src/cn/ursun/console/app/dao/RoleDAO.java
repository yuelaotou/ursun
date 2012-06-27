package cn.ursun.console.app.dao;

import java.util.List;

import cn.ursun.console.app.domain.Role;
import cn.ursun.platform.core.exception.BizException;
import cn.ursun.platform.ps.domain.TreeNode;

public interface RoleDAO {

	/**
	 * <p>查询所有角色
	 * </p>
	 * @return
	 * @throws Exception
	 * @author: 宋成山 songchengshan@neusoft.com
	 */
	public List<TreeNode> queryAllRoleTree() throws BizException;

	/**
	 * <p>查询当前用户可管理的角色树
	 * 
	 * </p>
	 * @param userId
	 * @return
	 * @throws Exception
	 * @author: 宋成山 songchengshan@neusoft.com
	 */
	public List<TreeNode> queryRoleTree(String userId) throws BizException;

	/**
	 * <p>查询当前用户可管理的角色列表
	 * 
	 * </p>
	 * @param userId
	 * @return
	 * @throws Exception
	 * @author: 宋成山 songchengshan@neusoft.com
	 */
	public List<Role> queryRoleListByUserId(String userId) throws BizException;
	
	/**
	 * <p>查询当前用户可管理的角色列表
	 * 
	 * </p>
	 * @param userId
	 * @return
	 * @throws Exception
	 * @author: 宋成山 songchengshan@neusoft.com
	 */
	public List<Role> queryRoleListByUserId(String[] userIds) throws BizException;

	/**
	 * <p>查询对应资源下的所有角色列表</p>
	 * @param resourceId
	 * @return
	 * @throws BizException
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-12-17 下午04:20:58
	 */
	public List<Role> queryRoleListOfResource(String resourceId) throws BizException;

	/**
	 * <p>除指定角色以外的角色列表</p>
	 * @param excludeRoleId
	 * @return
	 * @throws BizException
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-12-17 下午04:30:15
	 */
	public List<Role> queryRoleList(List<String> excludeRoleId) throws BizException;

	/**
	 * <p>精确查询角色信息</p>
	 * @param roleId
	 * @return
	 * @throws Exception
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-23 下午09:17:03
	 */
	public Role queryRoleById(String roleId) throws BizException;
	
	/**
	 * <p>精确查询角色信息</p>
	 * @param roleId
	 * @return
	 * @throws Exception
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-23 下午09:17:03
	 */
	public Role[] queryRoleById(String[] roleIds) throws BizException;

	/**
	 * <p>保存新增角色</p>
	 * @param role
	 * @throws Exception
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-23 下午09:16:47
	 */
	public String createRole(Role role) throws BizException;

	/**
	 * <p>保存修改角色</p>
	 * @param role
	 * @throws Exception
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-23 下午09:16:31
	 */
	public void updateRole(Role role) throws BizException;

	/**
	 * <p>删除角色 
	 * 1.删除角色与资源的关系
	 * 2.删除角色与人员的关系
	 * 3.删除角色信息
	 * </p>
	 * @param ids
	 * @return
	 * @throws BizException
	 * @author: 宋成山 songchengshan@neusoft.com
	 * @date: Created on Aug 21, 2008 1:49:55 PM
	 */
	public void deleteRole(String roleId) throws BizException;

	/**
	 * <p>同级下是否重名</p>
	 * @return
	 * @throws BizExcepton
	 * @author: 李志伟 - li.zhw@neusoft.com
	 * @data: Create on Apr 27, 2010 9:40:03 AM
	 */
	public boolean isExistSameParent(Role role) throws BizException;
	
	/**
	 * <p>查询除需要移动的节点及其子节点的角色树-管理员角色</p>
	 * @param unit_id
	 * @return
	 * @throws BizException
	 * @author: 李志伟 - li.zhw@neusoft.com
	 * @data: Create on May 15, 2010 12:33:36 PM
	 */
	@SuppressWarnings("unchecked")
	public List<TreeNode> queryUnitTreeForUpdateParent(String roleId) throws BizException;
	
	/**
	 * <p>查询除需要移动的节点及其子节点的角色树-普通用户角色</p>
	 * @param userUnitId
	 * @param updateUnitId
	 * @return
	 * @throws BizException
	 * @author: 李志伟 - li.zhw@neusoft.com
	 * @data: Create on May 15, 2010 12:47:47 PM
	 */
	@SuppressWarnings("unchecked")
	public List<TreeNode> queryUnitTreeByUnitIdForUpdateParent(String userId,String updateRoleId)throws BizException;
	
	/**
	 * <p>修改角色父子关系</p>
	 * @param unitId
	 * @param newParentId
	 * @throws BizException
	 * @author: 李志伟 - li.zhw@neusoft.com
	 * @data: Create on May 15, 2010 12:23:13 PM
	 */
	public void updateRoleParent(String roleId,String newParentId) throws BizException;
}
