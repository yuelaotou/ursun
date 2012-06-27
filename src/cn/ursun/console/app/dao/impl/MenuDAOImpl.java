/**
 * 文件名：MenuDAOImpl.java
 * 
 * 创建人：【李志伟】 - 【li.zhw@neusoft.com】
 * 
 * 创建时间：Dec 7, 2009 3:25:36 PM
 * 
 * 版权所有：东软软件股份有限公司
 */
package cn.ursun.console.app.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.jdbc.core.RowMapper;

import cn.ursun.console.app.dao.MenuDAO;
import cn.ursun.console.app.domain.Menu;
import cn.ursun.platform.core.exception.BizException;
import cn.ursun.platform.core.jdbc.WeeJdbcDAO;
import cn.ursun.platform.core.util.IDGenerator;
import cn.ursun.platform.ps.domain.TreeNode;

import com.opensymphony.xwork2.ActionContext;

/**
 * <p>菜单DAO</p>
 * @author 李志伟 - li.zhw@neusoft.com
 * @version 1.0
 */
public class MenuDAOImpl extends WeeJdbcDAO implements MenuDAO {

	/**
	 * 查询菜单树
	 */
	private static final String QUERY_LIST_SQL = "SELECT MENU_ID,NAME_EN,NAME_ZH_CN,URL,PARENT_ID,ISFOLDER,DESCRIPTION FROM WEE_AUTH_MENU ORDER BY PARENT_ID,SEQUENCE";
	/**
	 * 查询菜单树
	 */
	private static final String QUERY_MENU_LIST_SQL = "SELECT MENU_ID,NAME_EN,NAME_ZH_CN,URL,PARENT_ID,ISFOLDER,DESCRIPTION FROM WEE_AUTH_MENU WHERE PARENT_ID = ? ORDER BY PARENT_ID,SEQUENCE";

	/**
	 * 查询子菜单树
	 */
	private static final String QUERY_CHILDREN_SQL = "SELECT MENU_ID,NAME_EN,NAME_ZH_CN,URL,PARENT_ID,ISFOLDER,DESCRIPTION FROM WEE_AUTH_MENU START WITH MENU_ID = ? CONNECT BY PRIOR MENU_ID = PARENT_ID ORDER BY PARENT_ID,SEQUENCE ";

	/**
	 * 查询用户可见的菜单树
	 */
	private static final String QUERY_MENU_BY_USERID_SQL = "SELECT DISTINCT * FROM WEE_AUTH_MENU START WITH MENU_ID IN (SELECT A.MENU_ID FROM WEE_AUTH_MENU  A,"
			+ " WEE_AUTH_M_USER_ROLE B,  WEE_AUTH_M_ROLE_RESOURCE C, WEE_AUTH_M_MENU_RESOURCE D"
			+ " WHERE B.USER_ID = ?   AND B.ROLE_ID = C.ROLE_ID AND C.RESOURCE_ID = D.RESOURCE_ID  AND D.MENU_ID = A.MENU_ID) CONNECT BY PRIOR PARENT_ID = MENU_ID ORDER BY PARENT_ID,SEQUENCE ASC";

	/**
	 * 查询用户可见的子菜单树
	 */
	private static final String QUERY_CHILDREN_BY_USERID_SQL = "SELECT MENU_ID, NAME_EN, NAME_ZH_CN, URL, PARENT_ID, ISFOLDER, DESCRIPTION"
			+ " FROM (SELECT DISTINCT * FROM WEE_AUTH_MENU START WITH MENU_ID IN (SELECT A.MENU_ID FROM WEE_AUTH_MENU  A,"
			+ " WEE_AUTH_M_USER_ROLE B,  WEE_AUTH_M_ROLE_RESOURCE C, WEE_AUTH_M_MENU_RESOURCE D"
			+ " WHERE B.USER_ID = ?   AND B.ROLE_ID = C.ROLE_ID AND C.RESOURCE_ID = D.RESOURCE_ID  AND D.MENU_ID = A.MENU_ID) CONNECT BY PRIOR PARENT_ID = MENU_ID ) START WITH MENU_ID = ?"
			+ " CONNECT BY PRIOR MENU_ID = PARENT_ID ORDER BY PARENT_ID,SEQUENCE ";

	/**
	 * 查询当前角色可见的菜单树
	 */
	private static final String QUERY_MENU_BY_ROLEID_SQL = "SELECT MENU_ID, NAME_EN, NAME_ZH_CN, URL, PARENT_ID, ISFOLDER, DESCRIPTION"
			+ " FROM (SELECT DISTINCT * FROM WEE_AUTH_MENU START WITH MENU_ID IN (SELECT A.MENU_ID FROM WEE_AUTH_MENU  A,"
			+ " WEE_AUTH_M_ROLE_RESOURCE C, WEE_AUTH_M_MENU_RESOURCE D" + " WHERE C.ROLE_ID IN ";

	/**
	 * 查询当前角色可见的子菜单树
	 */
	private static final String QUERY_CHILDREN_BY_ROLEID_SQL = "SELECT MENU_ID, NAME_EN, NAME_ZH_CN, URL, PARENT_ID, ISFOLDER, DESCRIPTION"
			+ " FROM (SELECT DISTINCT * FROM WEE_AUTH_MENU START WITH MENU_ID IN (SELECT A.MENU_ID FROM WEE_AUTH_MENU  A,"
			+ " WEE_AUTH_M_ROLE_RESOURCE C, WEE_AUTH_M_MENU_RESOURCE D" + " WHERE C.ROLE_ID IN ";

	/**
	 * 查询父菜单树
	 */
	private static final String QUERY_PARENT_BY_MENUID_SQL = "SELECT MENU_ID, NAME_EN, NAME_ZH_CN, URL, PARENT_ID, ISFOLDER, DESCRIPTION"
			+ " FROM WEE_AUTH_MENU START WITH MENU_ID = ? CONNECT BY PRIOR PARENT_ID=MENU_ID ";

	/**
	 * 添加菜单
	 */
	private static final String INSERT_MENU_SQL = "INSERT INTO WEE_AUTH_MENU (MENU_ID,NAME_ZH_CN,URL,DESCRIPTION,PARENT_ID,ISFOLDER,SEQUENCE) VALUES (?,?,?,?,?,?,?)";

	/**
	 * 修改菜单
	 */
	private static final String UPDATE_MENU_SQL = "UPDATE WEE_AUTH_MENU SET NAME_ZH_CN=?,URL=?,DESCRIPTION=?,SEQUENCE=? WHERE MENU_ID=?";

	/**
	 * 删除菜单
	 */
	private static final String DELETE_MENU_SQL = "DELETE FROM WEE_AUTH_MENU WHERE MENU_ID=?";

	/**
	 * 查询菜单信息
	 */
	private static final String QUERY_MENU_SQL = "SELECT T1.MENU_ID,T1.NAME_ZH_CN,T1.URL,T1.DESCRIPTION,T1.PARENT_ID,T1.SEQUENCE,T2.RESOURCE_ID,T3.TITLE FROM WEE_AUTH_MENU T1,WEE_AUTH_M_MENU_RESOURCE T2,WEE_AUTH_RESOURCE T3 WHERE T1.MENU_ID=? AND T1.MENU_ID=T2.MENU_ID(+) AND T2.RESOURCE_ID=T3.RESOURCE_ID(+)";

	/**
	 * 绑定资源
	 */
	private static final String INSERT_M_MUNE_RESOURCE_SQL = "INSERT INTO WEE_AUTH_M_MENU_RESOURCE VALUES (?,?)";

	/**
	 * 解除资源绑定
	 */
	private static final String DELETE_M_MENU_RESOURCE_SQL = "DELETE FROM WEE_AUTH_M_MENU_RESOURCE WHERE MENU_ID=?";

	private static class MenuRowMapper implements RowMapper {
		Locale locale = ActionContext.getContext().getLocale();
		private MenuRowMapper(){
			Locale	temp = (Locale) ServletActionContext.getRequest().getSession().getAttribute("WW_TRANS_I18N_LOCALE");
			if(temp!=null){
				locale=temp;
			}
		}
		
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			TreeNode node = new TreeNode();
			node.setKey(rs.getString("MENU_ID"));
			if (locale != null && !locale.toString().equals("en")) {
				node.setTitle(rs.getString("NAME_ZH_CN"));
			}else{
				node.setTitle(rs.getString("NAME_EN"));
			}
			if(rs.getString("URL")!=null&&!rs.getString("URL").equals("")){
				node.setUrl(ServletActionContext.getRequest().getContextPath() +"/"+ rs.getString("URL"));
			}
			node.setParentId(rs.getString("PARENT_ID"));
			node.setIsFolder(rs.getBoolean("ISFOLDER"));
			return node;
		}
	}
	private static class MenuMapper implements RowMapper {

		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			Menu menu = new Menu();
			menu.setId(rs.getString("MENU_ID"));
			menu.setName(rs.getString("NAME_ZH_CN"));
			menu.setUrl(rs.getString("URL") == null ? "" : rs.getString("URL"));
			menu.setDescription(rs.getString("DESCRIPTION") == null ? "" : rs.getString("DESCRIPTION"));
			menu.setParentId(rs.getString("PARENT_ID"));
			menu.setSequence(rs.getString("SEQUENCE") == null ? "0" : rs.getString("SEQUENCE"));
			menu.setResourceId(rs.getString("RESOURCE_ID"));
			menu.setResourceName(rs.getString("TITLE") == null ? "" : rs.getString("TITLE"));
			return menu;
		}
	}

	private static class MenuTreeMapper implements RowMapper {

		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			TreeNode node = new TreeNode();
			node.setKey(rs.getString("MENU_ID"));
			//*********modify by 王敏 begin 2010-01-27*************//
			String name = rs.getString("NAME_ZH_CN");
			if (name.length() > 9){
				name = name.substring(0, 9) + "...";
				node.setTitle(name);
			}else{
				node.setTitle(rs.getString("NAME_ZH_CN"));
			}
			//*********modify by 王敏 end 2010-01-27*************//
			node.setUrl(rs.getString("URL") == null ? "" : rs.getString("URL"));
			//*********modify by 王敏 begin 2010-01-27*************//
			//node.setTooltip(rs.getString("NAME_ZH_CN"));
			node.setTooltip(rs.getString("DESCRIPTION") == null ? "" : rs.getString("DESCRIPTION"));
			//*********modify by 王敏 end 2010-01-27*************//
			node.setParentId(rs.getString("PARENT_ID"));
			node.setIsFolder(rs.getBoolean("ISFOLDER"));
			return node;
		}
	}

	public List<TreeNode> queryMenuItem() {
		return this.query(QUERY_LIST_SQL, new MenuRowMapper());
	}
	public List<TreeNode> queryMainMenuList() {
		return this.query(QUERY_MENU_LIST_SQL, new Object[]{"root"}, new MenuRowMapper());
	}
	public List<TreeNode> queryChildMenuListWithMenuID(String menu_id) {
		return this.query(QUERY_MENU_LIST_SQL, new Object[]{menu_id}, new MenuRowMapper());
	}
	/**
	 * <p>增加菜单信息,同时更新父菜单的isfolder属性</p>
	 * <p>操作表：WEE_AUTH_MENU</p>
	 * @param menu
	 * @return String 菜单信息ID
	 * @author: 李志伟 - li.zhw@neusoft.com
	 * @data: Create on Dec 7, 2009 3:25:36 PM
	 */
	public String createMenu(Menu menu) {
		String id=IDGenerator.generateId();
		this.update(INSERT_MENU_SQL, new Object[] { id, menu.getName(), menu.getUrl(),
				menu.getDescription(), menu.getParentId(), menu.isFolder() ? 1 : 0,
				StringUtils.isEmpty(menu.getSequence()) ? "" : Integer.parseInt(menu.getSequence()) });
		return id;
	}

	/**
	 * <p>保存资源绑定</p>
	 * <p>操作表：WEE_AUTH_M_MENU_RESOURCE</p>
	 * @param menuId
	 * @param resId
	 * @author: 李志伟 - li.zhw@neusoft.com
	 * @data: Create on Dec 7, 2009 3:25:36 PM
	 */
	public void createMenuResourceMapping(String menuId, String resId) {
		this.update(INSERT_M_MUNE_RESOURCE_SQL, new Object[] { menuId, resId });
	}

	/**
	 * <p>删除指定菜单信息</p>
	 * <p>操作表：WEE_AUTH_MENU</p>
	 * @param menuId
	 * @author: 李志伟 - li.zhw@neusoft.com
	 * @data: Create on Dec 7, 2009 3:25:36 PM
	 */
	public void deleteMenuById(String menuId) {
		this.update(DELETE_MENU_SQL, new Object[] { menuId });
	}

	/**
	 * <p>删除资源绑定</p>
	 * <p>操作表：WEE_AUTH_M_MENU_RESOURCE</p>
	 * @param menuId
	 * @author: 李志伟 - li.zhw@neusoft.com
	 * @data: Create on Dec 7, 2009 3:25:36 PM
	 */
	public void deleteMenuResourceMapping(String menuId) {
		this.update(DELETE_M_MENU_RESOURCE_SQL, new Object[] { menuId });
	}

	/**
	 * <p>查询MENU详细信息</p>
	 * <p>查询表：WEE_AUTH_MENU</p>
	 * <p>应用场景：系统菜单维护<p>
	 * @param menuId 指定节点ID
	 * @return Menu 菜单实体对象
	 * @author: 李志伟 - li.zhw@neusoft.com
	 * @data: Create on Dec 7, 2009 3:25:36 PM
	 */
	public Menu queryMenuById(String menuId) {
		return (Menu) this.queryForObject(QUERY_MENU_SQL, new Object[] { menuId }, new MenuMapper());
	}

	/**
	 * <p>查询所有节点的MENU树</p>
	 * <p>查询表：WEE_AUTH_MENU</p>
	 * <p>应用场景：系统菜单维护<p>
	 * @return List<TreeNode> 树集合对象
	 * @throws BizException
	 * @author: 李志伟 - li.zhw@neusoft.com
	 * @data: Create on Dec 7, 2009 3:25:36 PM
	 */
	@SuppressWarnings("unchecked")
	public List<TreeNode> queryMenuTree() {
		return this.query(QUERY_LIST_SQL, new MenuTreeMapper());
	}

	/**
	 * <p>查询指定节点的MENU树</p>
	 * <p>查询表：WEE_AUTH_MENU</p>
	 * <p>应用场景：系统菜单维护<p>
	 * @param menuId 指定节点ID
	 * @return List<TreeNode> 树集合对象
	 * @author: 李志伟 - li.zhw@neusoft.com
	 * @data: Create on Dec 7, 2009 3:25:36 PM
	 */
	@SuppressWarnings("unchecked")
	public List<TreeNode> queryMenuTreeById(String menuId) {		
		return this.query(QUERY_CHILDREN_SQL, new Object[] { menuId }, new MenuTreeMapper());
	}

	/**
	 * <p>查询当前用户可以访问的MENU树</p>
	 * @param userId
	 * @return
	 * @author: 李志伟 - li.zhw@neusoft.com
	 * @data: Create on Dec 7, 2009 3:25:36 PM
	 */
	@SuppressWarnings("unchecked")
	public List<TreeNode> queryMenuTreeByUserId(String userId) {

		return this.query(QUERY_MENU_BY_USERID_SQL, new Object[] { userId }, new MenuTreeMapper());
	}

	/**
	 * <p>查询当前用户可以访问指定节点的MENU树</p>
	 * @param userId
	 * @return
	 * @author: 李志伟 - li.zhw@neusoft.com
	 * @data: Create on Dec 7, 2009 3:25:36 PM
	 */
	@SuppressWarnings("unchecked")
	public List<TreeNode> queryMenuTreeByUserId(String userId, String menuId) {

		return this.query(QUERY_CHILDREN_BY_USERID_SQL, new Object[] { userId, menuId }, new MenuTreeMapper());
	}

	/**
	 * <p>查询当前角色可以访问的MENU树</p>
	 * @param userId 用户ID
	 * @return
	 * @throws BizException
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-23 下午09:58:56
	 */
	@SuppressWarnings("unchecked")
	public List<TreeNode> queryMenuTreeByRoleId(String[] roleIds) {
		StringBuffer roleStr = new StringBuffer();
		for (String roleid : roleIds) {
			roleStr.append("'").append(convertSQLParameter(roleid)).append("',");
		}
		roleStr.append("''");
		return this
				.query(
						QUERY_MENU_BY_ROLEID_SQL
								+ "("
								+ roleStr.toString()
								+ ") AND C.RESOURCE_ID = D.RESOURCE_ID  AND D.MENU_ID = A.MENU_ID) CONNECT BY PRIOR PARENT_ID = MENU_ID ) "
								+ " START WITH PARENT_ID IS NULL  CONNECT BY PRIOR MENU_ID = PARENT_ID ORDER BY PARENT_ID,SEQUENCE ",
						new MenuTreeMapper());
	}

	/**
	 * <p>查询当前角色可以访问指定节点的MENU树</p>
	 * @param userId 用户ID
	 * @return
	 * @throws BizException
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-23 下午09:58:56
	 */
	@SuppressWarnings("unchecked")
	public List<TreeNode> queryMenuTreeByRoleId(String[] roleIds, String menuId) {
		StringBuffer roleStr = new StringBuffer();
		for (String roleid : roleIds) {
			roleStr.append("'").append(convertSQLParameter(roleid)).append("',");
		}
		roleStr.append("''");
		return this
				.query(
						QUERY_CHILDREN_BY_ROLEID_SQL
								+ "("
								+ roleStr.toString()
								+ ") AND C.RESOURCE_ID = D.RESOURCE_ID  AND D.MENU_ID = A.MENU_ID) CONNECT BY PRIOR PARENT_ID = MENU_ID )  START WITH MENU_ID = ?"
								+ " CONNECT BY PRIOR MENU_ID = PARENT_ID ORDER BY PARENT_ID,SEQUENCE ",
						new Object[] { menuId }, new MenuTreeMapper());
	}

	@SuppressWarnings("unchecked")
	public List<TreeNode> queryMenuParentTree(String menuId) {
		return this.query(QUERY_PARENT_BY_MENUID_SQL, new Object[] { menuId }, new MenuTreeMapper());
	}

	/**
	 * <p>保存添加的更新菜单信息</p>
	 * <p>操作表：WEE_AUTH_MENU</p>
	 * @param menu
	 * @author: 李志伟 - li.zhw@neusoft.com
	 * @data: Create on Dec 7, 2009 3:25:36 PM
	 */
	public void updateMenu(Menu menu) {
		this.update(UPDATE_MENU_SQL, new Object[] { menu.getName(), menu.getUrl(), menu.getDescription(),
				Integer.parseInt(menu.getSequence()), menu.getId() });
	}

}
