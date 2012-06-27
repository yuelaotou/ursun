package cn.ursun.console.app.console.unit.action;

import cn.ursun.console.app.console.unit.bizservice.UnitBS;
import cn.ursun.console.app.domain.Unit;
import cn.ursun.platform.core.action.WeeAction;
import cn.ursun.platform.ps.domain.TreeNode;
import cn.ursun.platform.ps.security.WeeSecurityInfo;

/**
 * <p>组织机构查询展示</p>
 * @author 李志伟 - li.zhw@neusoft.com
 * @version 1.0
 */
public class ShowUnitAC extends WeeAction {

	private static final long serialVersionUID = 1L;

	/**
	 * 组织机构业务操作对象
	 */
	private UnitBS unitBS;

	/**
	 * 组织机构树
	 */
	private TreeNode rootUnit;
	
	private TreeNode unitRoot;

	/**
	 * 登录用户信息对象
	 */
	private WeeSecurityInfo wsi = WeeSecurityInfo.getInstance();

	/**
	 * 是否超级管理员
	 */
	private boolean admin = false;

	/**
	 * 登录用户的组织机构ID
	 */
	private String currentUnitId = null;
	
	/**
	 * 组织机构对象
	 */
	private Unit unit = null;
	
	/**
	 * 组织机构Id
	 */
	private String unitId = null;

	/**
	 * <p>初始化转向</p>
	 * @return
	 * @author: 李志伟 - li.zhw@neusoft.com
	 * @data: Create on Dec 21, 2009 3:53:29 PM
	 */
	public String init() throws Exception {
		admin = wsi.isAdminRole(wsi.getUserId());
		currentUnitId = wsi.getUserDeptId();
		return "unitTree";
	}

	/**
	 * <p>查询组织机构树
	 * 调用bs的queryUnitTree方法 赋值给rootUnit
	 * </p>
	 * @return 
	 * @throws Exception
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-23 下午08:19:36
	 */
	public String queryUnitTree() throws Exception {
		rootUnit = unitBS.queryUnitTree();
		return JSON;
	}

	/**
	 * <p>查询可作为移动目标的组织机构树</p>
	 * @return
	 * @throws Exception
	 * @author: 李志伟 - li.zhw@neusoft.com
	 * @data: Create on May 15, 2010 12:54:00 PM
	 */
	public String queryUnitTreeForUpdateParent() throws Exception {
		unitRoot = unitBS.queryUnitTreeForUpdateParent(unitId);
		return JSON;
	}
	
	/**
	 * <p>查询指定节点的组织机构详细信息</p>
	 * @return
	 * @throws Exception
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-23 下午08:19:50
	 */
	public String queryUnitByIdForModify() throws Exception {
		unit = unitBS.queryUnitById(unitId);
		return JSON;
	}

	public TreeNode getRootUnit() {
		return rootUnit;
	}

	public void setRootUnit(TreeNode rootUnit) {
		this.rootUnit = rootUnit;
	}

	public void setUnitBS(UnitBS unitBS) {
		this.unitBS = unitBS;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public String getCurrentUnitId() {
		return currentUnitId;
	}

	public void setCurrentUnitId(String currentUnitId) {
		this.currentUnitId = currentUnitId;
	}

	
	public Unit getUnit() {
		return unit;
	}

	
	public void setUnit(Unit unit) {
		this.unit = unit;
	}

	
	public String getUnitId() {
		return unitId;
	}

	
	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	
	public TreeNode getUnitRoot() {
		return unitRoot;
	}

	
	public void setUnitRoot(TreeNode unitRoot) {
		this.unitRoot = unitRoot;
	}
	
}
