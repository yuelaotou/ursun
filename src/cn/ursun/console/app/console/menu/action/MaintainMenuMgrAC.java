/**
 * 文件名：MaintainMenuMgrAC.java
 * 
 * 创建人：[郭铜彬] - [guotb@neusoft.com]
 * 
 * 创建时间：Nov 27, 2009 10:20:47 AM
 * 
 * 版权所有：东软软件股份有限公司
 */
package cn.ursun.console.app.console.menu.action;

import cn.ursun.console.app.console.menu.bizservice.MenuBS;
import cn.ursun.console.app.domain.Menu;
import cn.ursun.platform.core.action.WeeAction;
import cn.ursun.platform.core.exception.BizException;

/**
 * <p> MaintainMenuMgrAC</p>
 * <p> 维护菜单信息Action</p>
 * <p> Created on Nov 27, 2009</p>
 * <p> Copyright: Copyright (c) 2008</p>
 * <p> Company: 东软软件股份有限公司</p>
 * @author [郭铜彬] - [guotb@neusoft.com]
 * @version 1.0
 */
public class MaintainMenuMgrAC extends WeeAction {

	/**
	 * 业务操作对象
	 */
	private MenuBS menuBS = null;

	/**
	 * 菜单实体作对象
	 */
	private Menu menu = null;

	/**
	 * 菜单id作对象
	 */
	private String menuId = null;
	
	/**
	 * 资源id
	 */
	private String resourceId=null;

	private String flag = null;

	private String newMenuId=null;
	/**
	 * <p>查询菜单详细信息</p>
	 * <p>应用场景：系统菜单信息维护</p>
	 * <p>调用步骤：调用MenuBS.queryMenuById(String menuId)方法，返回Menu对象</p>
	 * Created on Nov 26, 2009
	 * @author: [郭铜彬] - [guotb@neusoft.com]
	 * @update: [日期YYYY-MM-DD] [郭铜彬]
	 */
	public String queryMenuById() throws BizException {
		menu = menuBS.queryMenuById(menuId);
		return JSON;
	}

	/**
	 * <p>添加菜单</p>
	 * <p>应用场景：系统菜单信息维护</p>
	 * <p>调用步骤：调用MenuBS.addMenu()方法</p>
	 * Created on Nov 30, 2009
	 * @author: [郭铜彬] - [guotb@neusoft.com]
	 * @update: [日期YYYY-MM-DD] [郭铜彬]
	 */
	public String addMenu() throws BizException {
		newMenuId=menuBS.addMenu(menu);
		this.setFlag("1");
		return JSON;
	}

	/**
	 * <p>修改菜单</p>
	 * <p>应用场景：系统菜单信息维护</p>
	 * <p>调用步骤：调用MenuBS.updateMenu()方法</p>
	 * Created on Nov 30, 2009
	 * @author: [郭铜彬] - [guotb@neusoft.com]
	 * @update: [日期YYYY-MM-DD] [郭铜彬]
	 */
	public String updateMenu() throws BizException {
		menuBS.updateMenu(menu);
		this.setFlag("1");
		return JSON;
	}

	/**
	 * <p>保存菜单节点关系的更改</p>
	 * <p>应用场景：系统菜单信息维护</p>
	 * <p>调用步骤：调用MenuBS.saveChangedMenu()方法</p>
	 * @return
	 * @throws Exception
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-23 下午09:54:50
	 */
	public String saveChangedMenu() throws Exception {
		return null;
	}

	/**
	 * <p>删除指定菜单信息</p>
	 * <p>应用场景：系统菜单信息维护</p>
	 * <p>调用步骤：调用MenuBS.deleteMenuById(String menuId)方法</p>
	 * @return
	 * @throws Exception
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-23 下午09:54:50
	 */
	public String deleteMenuById() throws Exception {
		menuBS.deleteMenuById(menuId);
		this.setFlag("1");
		return JSON;
	}

	/**
	 * <p>保存资源绑定</p>
	 * <p>应用场景：系统菜单信息维护</p>
	 * <p>调用步骤：调用MenuBS.saveMenuResourceMapping(String menuId, String resId)方法</p>
	 * @return
	 * @throws Exception
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-23 下午09:54:50
	 */
	public String bindMenuResourceMapping() throws Exception {
		menuBS.bindMenuResourceMapping(menuId, resourceId);
		this.setFlag("1");
		return JSON;
	}

	/**
	 * <p>解除资源绑定</p>
	 * <p>应用场景：系统菜单信息维护</p>
	 * <p>调用步骤：调用MenuBS.deleteMenuResourceMapping(String menuId)方法</p>
	 * @returns
	 * @throws Exception
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-23 下午09:54:50
	 */
	public String unbindMenuResourceMapping() throws Exception {
		menuBS.unbindMenuResourceMapping(menuId);
		this.setFlag("1");
		return JSON;
	}

	// public MenuBS getMenuBS() {
	// return menuBS;
	// }

	public void setMenuBS(MenuBS menuBS) {
		this.menuBS = menuBS;
	}

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	
	public String getFlag() {
		return flag;
	}

	
	public void setFlag(String flag) {
		this.flag = flag;
	}

	
	public String getResourceId() {
		return resourceId;
	}

	
	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	
	public String getNewMenuId() {
		return newMenuId;
	}

	
	public void setNewMenuId(String newMenuId) {
		this.newMenuId = newMenuId;
	}

}
