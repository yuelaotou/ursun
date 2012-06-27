package cn.ursun.console.app.console.privilege.bizservice;

import java.util.List;

import cn.ursun.platform.core.exception.BizException;
import cn.ursun.platform.ps.bizlog.aop.annotation.BizExecution;
import cn.ursun.platform.ps.domain.TreeNode;

public interface PrivilegeBS {

	/**
	 * <p>查询当前用户可管理的角色树
	 * 判断当前用户是否为超级管理员，超级管理员返回所有角色，否则查询当前用户拥有的角色级子角色（用户不可修改自己本身角色的权限）
	 * </p>
	 * @return
	 * @throws BizException
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-23 下午09:43:10
	 */
	@BizExecution
	public List<TreeNode> queryRoleList() throws BizException;

	/**
	 * <p>查询资源树
	 * 1.查询当前选中角色的父角色ID.  调用RoleDAO.queryRoleById() 得到父角色ID
	 * 2.查询父角色ID下的所有资源。调用PrivilegeDAO.queryResourceListOfRole()
	 * 3.查询当前选中角色的所有资源。调用PrivilegeDAO.queryResourceListOfRole()
	 * 4.在第一步得到的资源树中挑选出第三步得到的资源树中的节点（通过roleID匹配）,将这些节点的初始状态设为被选中。
	 * 5.返回第一步得到的资源树。
	 * </p>
	 * @param roleId
	 * @return
	 * @throws BizException
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-27 上午11:04:18
	 */
	@BizExecution
	public List<TreeNode> queryResourceOfRole() throws BizException;

	/**
	 * <p>根据用户id查询资源树</p>
	 * @param userId
	 * @return
	 * @throws BizException
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-23 下午09:51:19
	 */
	@BizExecution
	public List<TreeNode> queryResourceTreeByUserId(String userId) throws BizException;

	/**
	 * <p>Discription:给角色赋权
	 * 建立角色与资源对应关系
	 * 调用PrivilegeDAO的deleteRoleToResourceMaping-> createRoleToResourceMaping
	 * </p>
	 * Created on 2009-11-17
	 * @author: 宋成山 songchengshan@neusoft.com
	 * @update: [日期YYYY-MM-DD] [更改人姓名]
	 */
	@BizExecution
	public void updatePrivilege(String roleId, String[] resIds) throws BizException;

	/**
	 * 
	 * <p>根据roleid查询该角色对应的资源id</p>
	 * @param roleId
	 * @return
	 * @throws BizException
	 * @author: 于宝淇 - yu-bq@neusoft.com
	 * @data: Create on Dec 8, 2009 5:01:17 PM
	 */
	@BizExecution
	public List<TreeNode> queryResourceTreeOfRole(String roleId) throws BizException;

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
	public boolean isMe(String userId, String roleId) throws BizException;

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
	public TreeNode getParentTreeNode(String parentId) throws BizException;

	/**
	 * 
	 * <p>级联删除子节点的资源映射</p>
	 * @param roleId
	 * @throws BizException
	 * @author: 于宝淇 - yu-bq@neusoft.com
	 * @data: Create on Jan 6, 2010 4:47:17 PM
	 */
	@BizExecution
	public void recDelResourceRoleMapping(String roleId) throws BizException;

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
	public TreeNode showResourceTree(String parentRoleId, String roleId) throws Exception;

}
