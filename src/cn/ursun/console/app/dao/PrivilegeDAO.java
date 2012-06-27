package cn.ursun.console.app.dao;

import java.util.List;
import java.util.Map;

import cn.ursun.platform.core.exception.BizException;
import cn.ursun.platform.ps.domain.TreeNode;

/**
 * <p>权限管理DAO</p>
 * @author 宋成山 - songchengshan@neusoft.com
 * @version 1.0
 */
public interface PrivilegeDAO {

	/**
	 * <p>查询资源树</p>
	 * @param roleId
	 * @return
	 * @throws BizException
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-23 下午09:51:19
	 */
	public List<TreeNode> queryResourceTreeOfRole(String roleId) throws BizException;
	
	/**
	 * <p>根据用户id查询资源树</p>
	 * @param userId
	 * @return
	 * @throws BizException
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-23 下午09:51:19
	 */
	public List<TreeNode> queryResourceTreeByUserId(String userId) throws BizException;

	/**
	 * <p>更新角色和对应资源的关系</p>
	 * @param roleId
	 * @param resIds
	 * @throws BizException
	 * @author: 于宝淇 - yu-bq@neusoft.com
	 * @data: Create on Dec 7, 2009 3:35:42 PM
	 */
	public void updatePrivilege(String roleId, String[] resIds) throws BizException;

	/**
	 * <p>查询URL与角色的对应关系</p>
	 * 
	 * @return
	 * @throws BizException
	 * @author: 段鹏 - duanp@neusoft.com 
	 * @date: Created on 2009-5-8 下午07:56:33
	 */
	public Map<String, List<String>> queryUrlRoleMapping() throws BizException;

	/**
	 * <p>判断是否有操作该资源的权限</p>
	 * 
	 * @param roleIds
	 * @param resourceId
	 * @return
	 * @throws BizException
	 * @author: 段鹏 - duanp@neusoft.com 
	 * @date: Created on 2009-4-26 下午03:44:12
	 */
	public boolean hasPrivilege(String[] roleIds, String resourceId) throws BizException;

	
	/**
	 * 
	 * <p>本方法用于删除角色之前级联删除角色与资源的映射关系</p>
	 * @param roleId
	 * @author: 【徐冉】 - 【xu_r@neusoft.com】
	 * @data: Create on 2010-1-6 下午01:33:28
	 */
	public void deleteResourceRoleMapping(String roleId) throws BizException;

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
	public boolean isMe(String userId,String roleId) throws BizException;
	
	/**
	 * 
	 * <p>得到父节点信息</p>
	 * @param parentId
	 * @return
	 * @throws BizException
	 * @author: 于宝淇 - yu-bq@neusoft.com
	 * @data: Create on Jan 5, 2010 2:38:57 PM
	 */
	public TreeNode getParentTreeNode(String parentId)throws BizException;
	
	
	/**
	 * 
	 * <p>取所有父亲节点</p>
	 * @param parentId
	 * @return
	 * @throws BizException
	 * @author: 于宝淇 - yu-bq@neusoft.com
	 * @data: Create on Jan 5, 2010 2:38:57 PM
	 */
	@SuppressWarnings("unchecked")
	public List<TreeNode> getAllParentTreeNode() throws BizException;
	
	/**
	 * 
	 * <p>级联删除子节点的资源映射</p>
	 * @param roleId
	 * @throws BizException
	 * @author: 于宝淇 - yu-bq@neusoft.com
	 * @data: Create on Jan 6, 2010 4:47:17 PM
	 */
	public void recDelResourceRoleMapping(String roleId) throws BizException;
	
	/**
	 * 
	 * <p>得到资源树的根节点</p>
	 * @return
	 * @throws BizException
	 * @author: 于宝淇 - yu-bq@neusoft.com
	 * @data: Create on Jan 6, 2010 7:32:49 PM
	 */
	public TreeNode getReourceRootNode()throws BizException;

}
