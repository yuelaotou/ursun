package cn.ursun.console.app.dao;

import java.util.List;

import cn.ursun.console.app.domain.Unit;
import cn.ursun.platform.core.exception.BizException;
import cn.ursun.platform.ps.domain.TreeNode;

public interface UnitDAO {

	/**
	 * <p>查询组织机构树</p>
	 * @return
	 * @throws BizException
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-23 下午08:34:28
	 */
	public List<TreeNode> queryUnitTree() throws BizException;
	
	/**
	 * <p>查询指定节点及其下的组织机构</p>
	 * @return
	 * @throws BizException
	 * @author: 李志伟 - li.zhw@neusoft.com
	 * @data: Create on Jan 4, 2010 10:40:13 AM
	 */
	public List<TreeNode> queryUnitTreeByUnitId(String unitId) throws BizException;
	/**
	 * <p>查询指定节点的组织机构详细信息</p>
	 * @param unitId
	 * @return
	 * @throws BizException
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-23 下午08:35:02
	 */
	public Unit queryUnitById(String unitId) throws BizException;

	/**
	 * <p>查询用户的组织机构</p>
	 * @param userId
	 * @return
	 * @throws BizException
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-12-17 下午04:22:38
	 */
	public List<Unit> queryUnitListOfUser(String userId) throws BizException;

	/**
	 * <p>添加一个组织单元</p>
	 * @param unit
	 * @throws BizException
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-23 下午08:34:43
	 */
	public String createUnit(Unit unit) throws BizException;

	/**
	 * <p>修改组织单元</p>
	 * @param unit
	 * @throws BizException
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-23 下午08:31:28
	 */
	public void updateUnit(Unit unit) throws BizException;

	/**
	 * <p>删除组织单元,并删除该组织单元下与用户的关联</p>
	 * @param unitId
	 * @throws BizException
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-23 下午08:28:43
	 */
	public void deleteUnitUser(String unit) throws BizException;

	/**
	 * <p>同一节点下是否存在相同名称的节点</p>
	 * @param unitName
	 * @param parentId
	 * @return
	 * @throws BizException
	 * @author: 李志伟 - li.zhw@neusoft.com
	 * @data: Create on Feb 24, 2010 9:25:09 AM
	 */
	public boolean isUnitExistSameParent(Unit unit) throws BizException;
	
	/**
	 * <p>查询除需要移动的节点及其子节点的组织机构树</p>
	 * @param unit_id
	 * @return
	 * @throws BizException
	 * @author: 李志伟 - li.zhw@neusoft.com
	 * @data: Create on May 15, 2010 12:33:36 PM
	 */
	public List<TreeNode> queryUnitTreeForUpdateParent(String unitId) throws BizException;
	
	/**
	 * <p>查询除需要移动的节点及其子节点的组织机构树-普通用户角色</p>
	 * @param userUnitId
	 * @param updateUnitId
	 * @return
	 * @throws BizException
	 * @author: 李志伟 - li.zhw@neusoft.com
	 * @data: Create on May 15, 2010 12:47:47 PM
	 */
	public List<TreeNode> queryUnitTreeByUnitIdForUpdateParent(String userUnitId,String updateUnitId)throws BizException;
	
	/**
	 * <p>修改组织机构父子关系</p>
	 * @param unitId
	 * @param newParentId
	 * @throws BizException
	 * @author: 李志伟 - li.zhw@neusoft.com
	 * @data: Create on May 15, 2010 12:23:13 PM
	 */
	public void updateUnitParent(String unitId,String newParentId) throws BizException;
}
