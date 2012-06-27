package cn.ursun.console.app.console.unit.bizservice.impl;

import java.util.List;

import org.apache.struts2.ServletActionContext;

import cn.ursun.console.app.console.unit.bizservice.UnitBS;
import cn.ursun.console.app.dao.UnitDAO;
import cn.ursun.console.app.dao.UserDAO;
import cn.ursun.console.app.domain.Unit;
import cn.ursun.platform.core.exception.BizException;
import cn.ursun.platform.ps.bizlog.aop.annotation.BizExecution;
import cn.ursun.platform.ps.bizservice.WeeBizService;
import cn.ursun.platform.ps.domain.TreeNode;
import cn.ursun.platform.ps.security.WeeSecurityInfo;
import cn.ursun.platform.ps.util.TreeUtil;

/**
 * <p>组织机构业务操作</p>
 * @author 李志伟 - li.zhw@neusoft.com
 * @version 1.0
 */
public class UnitBSImpl extends WeeBizService implements UnitBS {

	/**
	 * 组织机构数据操作对象
	 */
	private UnitDAO unitDAO;

	/**
	 * 用户数据操作对象
	 */
	private UserDAO userDAO;

	/**
	 * <p>查询组织机构树</p>
	 * @return
	 * @throws BizException
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-23 下午08:25:55
	 */
	@BizExecution
	public TreeNode queryUnitTree() throws BizException {
		try {
			TreeNode unitTree = null;
			WeeSecurityInfo wsi = WeeSecurityInfo.getInstance();
			if (wsi.isAdminRole(wsi.getUserId())) {
				unitTree = TreeUtil.createTreeRelation(unitDAO.queryUnitTree());
			} else {
				Unit unit = unitDAO.queryUnitById("root");
				List<TreeNode> list = unitDAO.queryUnitTreeByUnitId(wsi.getUserDeptId());
				unitTree = TreeUtil.createTreeRelation(list);
				unitTree.setKey(unit.getId());
				unitTree.setUrl(ServletActionContext.getRequest().getContextPath()
						+ "/platform/auth/showUserAC!queryUserListOfUnit.do?unit=");
				unitTree.setTitle(unit.getUnitName());
				unitTree.setIsFolder(unit.isLeaf());
				unitTree.setTooltip(unit.getDescription());
			}
			return unitTree;
		} catch (Exception e) {
			throw new BizException("0400101A", e);
		}
	}

	/**
	 * <p>查询指定节点组织机构及其下的各级组织机构</p>
	 * @return
	 * @throws BizException
	 * @author: 李志伟 - li.zhw@neusoft.com
	 * @data: Create on Jan 4, 2010 10:29:55 AM
	 */
	@BizExecution
	public TreeNode queryUnitTreeByUnitId(String unitId) throws BizException {
		try {
			Unit unit = unitDAO.queryUnitById("root");
			List<TreeNode> list = unitDAO.queryUnitTreeByUnitId(unitId);
			TreeNode node = TreeUtil.createTreeRelation(list);
			node.setKey(unit.getId());
			node.setUrl(ServletActionContext.getRequest().getContextPath()
					+ "/sysmgr/showUserAC!queryUserListOfUnit.do?unit=");
			node.setTitle(unit.getUnitName());
			node.setIsFolder(unit.isLeaf());
			node.setTooltip(unit.getDescription());
			return node;
		} catch (Exception e) {
			throw new BizException("0400101A", e);
		}
	}

	/**
	 * <p>查询指定节点的组织机构详细信息</p>
	 * @param unitId
	 * @return
	 * @throws BizException
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-23 下午08:32:23
	 */
	@BizExecution
	public Unit queryUnitById(String unitId) throws BizException {
		try {
			return unitDAO.queryUnitById(unitId);
		} catch (Exception e) {
			throw new BizException("0400106A", e);
		}
	}

	/**
	 * <p>添加一个组织单元</p>
	 * @param unit
	 * @throws BizException
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-23 下午08:32:03
	 */
	@BizExecution
	public String addUnit(Unit unit) throws BizException {
		try {
			return unitDAO.createUnit(unit);
		} catch (Exception e) {
			throw new BizException("0400102A", e);
		}
	}

	/**
	 * <p>修改组织单元</p>
	 * @param unit
	 * @throws BizException
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-23 下午08:31:28
	 */
	@BizExecution
	public void updateUnit(Unit unit) throws BizException {
		try {
			unitDAO.updateUnit(unit);
		} catch (Exception e) {
			throw new BizException("0400103A", e);
		}
	}

	/**
	 * <p>删除组织单元,并删除该组织单元下与用户的关联</p>
	 * @param unitId
	 * @throws BizException
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-23 下午08:28:43
	 */
	@BizExecution
	public void deleteUnitUser(String unitId) throws BizException {
		try {
			unitDAO.deleteUnitUser(unitId);
		} catch (Exception e) {
			throw new BizException("0400104A", e);
		}
	}

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
	public boolean isUnitExistSameParent(Unit unit) throws BizException {
		try {
			return unitDAO.isUnitExistSameParent(unit);
		} catch (Exception e) {
			throw new BizException("0400105A", e);
		}
	}

	/**
	 * <p>查询除需要移动的节点及其子节点的组织机构树</p>
	 * @param unitId
	 * @return
	 * @throws BizException
	 * @author: 李志伟 - li.zhw@neusoft.com
	 * @data: Create on May 15, 2010 12:37:36 PM
	 */
	@BizExecution
	public TreeNode queryUnitTreeForUpdateParent(String unitId) throws BizException{
		try {
			TreeNode unitTree = null;
			WeeSecurityInfo wsi = WeeSecurityInfo.getInstance();
			if (wsi.isAdminRole(wsi.getUserId())) {
				unitTree = TreeUtil.createTreeRelation(unitDAO.queryUnitTreeForUpdateParent(unitId));
			} else {
				Unit unit = unitDAO.queryUnitById("root");
				List<TreeNode> list = unitDAO.queryUnitTreeByUnitIdForUpdateParent(wsi.getUserDeptId(),unitId);
				unitTree = TreeUtil.createTreeRelation(list);
				unitTree.setKey(unit.getId());
				unitTree.setUrl(ServletActionContext.getRequest().getContextPath()
						+ "/sysmgr/showUserAC!queryUserListOfUnit.do?unit=");
				unitTree.setTitle(unit.getUnitName());
				unitTree.setIsFolder(unit.isLeaf());
				unitTree.setTooltip(unit.getDescription());
			}
			return unitTree;
		} catch (Exception e) {
			throw new BizException("0400101A", e);
		}
	}
	
	/**
	 * <p>修改组织机构父子关系</p>
	 * @param unitId
	 * @param newParentId
	 * @throws BizException
	 * @author: 李志伟 - li.zhw@neusoft.com
	 * @data: Create on May 15, 2010 12:37:45 PM
	 */
	@BizExecution
	public void updateUnitParent(String unitId,String newParentId) throws BizException{
		try{
			unitDAO.updateUnitParent(unitId, newParentId);
		}catch(Exception e){
			throw new BizException("0400103A", e);
		}
	}
	
	
	public UnitDAO getUnitDAO() {
		return unitDAO;
	}

	public void setUnitDAO(UnitDAO unitDAO) {
		this.unitDAO = unitDAO;
	}

	public UserDAO getUserDAO() {
		return userDAO;
	}

	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

}
