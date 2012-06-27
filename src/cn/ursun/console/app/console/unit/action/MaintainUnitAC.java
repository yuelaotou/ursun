package cn.ursun.console.app.console.unit.action;

import cn.ursun.console.app.console.unit.bizservice.UnitBS;
import cn.ursun.console.app.domain.Unit;
import cn.ursun.platform.core.action.WeeAction;

/**
 * <p>组织机构-维护</p>
 * @author 李志伟 - li.zhw@neusoft.com
 * @version 1.0
 */
public class MaintainUnitAC extends WeeAction {

	private static final long serialVersionUID = 1L;

	/**
	 * 组织机构业务操作对象
	 */
	private UnitBS unitBS;

	/**
	 * 组织机构对象
	 */
	private Unit unit;

	/**
	 * 操作结果标识
	 */
	private int temp = 0;
	
	/**
	 * 新增加节点id
	 */
	private String newUnitId;
	
	/**
	 * <p>添加一个组织单元</p>
	 * @return
	 * @throws Exception
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-23 下午08:20:06
	 */
	public String addUnit() throws Exception {
		newUnitId=unitBS.addUnit(unit);
		setTemp(1);
		return JSON;
	}

	/**
	 * <p>修改组织单元</p>
	 * @return
	 * @throws Exception
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-23 下午08:20:23
	 */
	public String updateUnit() throws Exception {
		unitBS.updateUnit(unit);
		setTemp(1);
		return JSON;
	}

	/**
	 * 
	 * <p>删除组织单元，并删除组织单元下与用户关联</p>
	 * 
	 * @return
	 * @throws Exception
	 * @author: 宋成山 songchengshan@neusoft.com
	 * @date: Created on Sep 3, 2008 4:38:48 PM
	 */
	public String deleteUnit() throws Exception {
		unitBS.deleteUnitUser(unit.getId());
		setTemp(1);
		return JSON;
	}
	
	/**
	 * <p>同一节点下是否存在相同名称的节点</p>
	 * @return
	 * @throws Exception
	 * @author: 李志伟 - li.zhw@neusoft.com
	 * @data: Create on Feb 24, 2010 9:22:26 AM
	 */
	public String isExist() throws Exception {
		if(unitBS.isUnitExistSameParent(unit)){
			setTemp(1);
		}
		return JSON;
	}

	/**
	 * <p>修改组织机构父节点</p>
	 * @return
	 * @throws Exception
	 * @author: 李志伟 - li.zhw@neusoft.com
	 * @data: Create on May 17, 2010 1:57:00 PM
	 */
	public String updateUnitParent() throws Exception {
		unitBS.updateUnitParent(unit.getId(), unit.getParentId());
		setTemp(1);
		return JSON;
	}
	
	public void setUnitBS(UnitBS unitBS) {
		this.unitBS = unitBS;
	}

	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}

	public int getTemp() {
		return temp;
	}

	public void setTemp(int temp) {
		this.temp = temp;
	}

	
	public String getNewUnitId() {
		return newUnitId;
	}

	
	public void setNewUnitId(String newUnitId) {
		this.newUnitId = newUnitId;
	}

}
