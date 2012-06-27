package cn.ursun.console.app.console.unit.bizservice;

import cn.ursun.console.app.domain.Unit;
import cn.ursun.platform.core.exception.BizException;
import cn.ursun.platform.ps.bizlog.aop.annotation.BizExecution;
import cn.ursun.platform.ps.domain.TreeNode;

public interface UnitBS {

	/**
	 * <p>查询组织机构树</p>
	 * @return
	 * @throws BizException
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-23 下午08:25:55
	 */
	@BizExecution
	public TreeNode queryUnitTree() throws BizException;

	/**
	 * <p>查询指定节点组织机构及其下的各级组织机构</p>
	 * @return
	 * @throws BizException
	 * @author: 李志伟 - li.zhw@neusoft.com
	 * @data: Create on Jan 4, 2010 10:29:55 AM
	 */
	@BizExecution
	public TreeNode queryUnitTreeByUnitId(String unitId) throws BizException;
	
	/**
	 * <p>查询指定节点的组织机构详细信息</p>
	 * @param unitId
	 * @return
	 * @throws BizException
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-23 下午08:32:23
	 */
	@BizExecution
	public Unit queryUnitById(String unitId) throws BizException;

	/**
	 * <p>添加一个组织单元</p>
	 * @param unit
	 * @throws BizException
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-23 下午08:32:03
	 */
	@BizExecution
	public String addUnit(Unit unit) throws BizException;

	/**
	 * <p>修改组织单元</p>
	 * @param unit
	 * @throws BizException
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-23 下午08:31:28
	 */
	@BizExecution
	public void updateUnit(Unit unit) throws BizException;


	/**
	 * <p>删除组织单元,并删除该组织单元下与用户的关联</p>
	 * @param unitId
	 * @throws BizException
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-23 下午08:28:43
	 */
	@BizExecution
	public void deleteUnitUser(String unitId) throws BizException;

	/**
	 * <p>同一节点下是否存在相同名称的节点</p>
	 * @param unitName
	 * @param parentId
	 * @return
	 * @throws BizException
	 * @author: 李志伟 - li.zhw@neusoft.com
	 * @data: Create on Feb 24, 2010 9:25:09 AM
	 */
	@BizExecution
	public boolean isUnitExistSameParent(Unit unit) throws BizException;
	
	/**
	 * <p>查询除需要移动的节点及其子节点的组织机构树</p>
	 * @param unitId
	 * @return
	 * @throws BizException
	 * @author: 李志伟 - li.zhw@neusoft.com
	 * @data: Create on May 15, 2010 12:37:36 PM
	 */
	@BizExecution
	public TreeNode queryUnitTreeForUpdateParent(String unitId) throws BizException;
	
	/**
	 * <p>修改组织机构父子关系</p>
	 * @param unitId
	 * @param newParentId
	 * @throws BizException
	 * @author: 李志伟 - li.zhw@neusoft.com
	 * @data: Create on May 15, 2010 12:37:45 PM
	 */
	@BizExecution
	public void updateUnitParent(String unitId,String newParentId) throws BizException;
}
