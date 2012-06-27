package cn.ursun.console.app.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.springframework.jdbc.core.RowMapper;

import cn.ursun.console.app.dao.RoleDAO;
import cn.ursun.console.app.domain.Role;
import cn.ursun.platform.core.exception.BizException;
import cn.ursun.platform.core.jdbc.WeeJdbcDAO;
import cn.ursun.platform.ps.domain.TreeNode;

public class RoleDAOImpl extends WeeJdbcDAO implements RoleDAO {

	private static final String SQL_QUERY_ROLE_INFO = "SELECT ROLE_ID, ROLE_NAME, DESCRIPTION,  PARENT_ID, IS_LEAF FROM WEE_AUTH_ROLE ORDER BY ROLE_ID";

	private static final String SQL_QUERY_ROLE = "SELECT ROLE_ID, ROLE_NAME, DESCRIPTION,  PARENT_ID, IS_LEAF "
			+ " FROM WEE_AUTH_ROLE where ROLE_ID=? ORDER BY ROLE_ID";

	private static final String SQL_ADD_ROLE = "INSERT INTO WEE_AUTH_ROLE(ROLE_ID, ROLE_NAME, DESCRIPTION,  "
			+ "PARENT_ID) VALUES(?,?,?,?)";

	private static final String SQL_UPDATE_ROLE = "UPDATE WEE_AUTH_ROLE SET ROLE_NAME=?, DESCRIPTION=?"
			+ " WHERE ROLE_ID=?";

	private static final String SQL_DELETE_ROLE = "DELETE FROM WEE_AUTH_ROLE WHERE ROLE_ID=?";


	private static final String SQL_QUERY_ROLE_LIST_BY_USER_ID = "SELECT R.ROLE_ID, R.ROLE_NAME, PARENT_ID, R.DESCRIPTION FROM WEE_AUTH_ROLE R, WEE_AUTH_M_USER_ROLE M  WHERE R.ROLE_ID = M.ROLE_ID AND M.USER_ID = ? ORDER BY R.ROLE_NAME";
	
	private static final String SQL_QUERY_ROLE_LIST_BY_USER_IDS = "SELECT R.ROLE_ID, R.ROLE_NAME, PARENT_ID, R.DESCRIPTION FROM WEE_AUTH_ROLE R, WEE_AUTH_M_USER_ROLE M WHERE R.ROLE_ID = M.ROLE_ID ";

	private static final String SQL_QUERY_ROLE_LIST_OF_ALL = "SELECT R.ROLE_ID, R.ROLE_NAME, R.PARENT_ID, R.DESCRIPTION FROM WEE_AUTH_ROLE R ";

	private static final String SQL_QUERY_ROLE_LIST_OF_RESOURCE = "SELECT R.ROLE_ID, R.ROLE_NAME, R.PARENT_ID, R.DESCRIPTION FROM WEE_AUTH_ROLE R, WEE_AUTH_M_ROLE_RESOURCE RR WHERE R.ROLE_ID = RR.ROLE_ID AND RR.RESOURCE_ID = ? ORDER BY R.ROLE_NAME";

	private static final String SQL_QUERY_ROLE_BY_USER_ID = "SELECT ROLE_ID,USER_ID FROM WEE_AUTH_M_USER_ROLE WHERE USER_ID=?";

	private static final String SQL_QUERY_ROLE_BY_IDS="SELECT ROLE_ID, ROLE_NAME, DESCRIPTION,  PARENT_ID, IS_LEAF "
		+ " FROM WEE_AUTH_ROLE where ROLE_ID IN ";
	
	private static final String QUERY_EXIST_FOR_INSERT = "SELECT COUNT(1) FROM WEE_AUTH_ROLE WHERE ROLE_NAME=? AND PARENT_ID=?";

	private static final String QUERY_EXIST_FOR_UPDATE = "SELECT COUNT(1) FROM WEE_AUTH_ROLE WHERE ROLE_NAME=? AND PARENT_ID=? AND ROLE_ID!=?";
	
	private static final String QUERY_ROLE_FOR_UPDATE_PARENT="SELECT * FROM WEE_AUTH_ROLE WHERE ROLE_ID NOT IN(SELECT ROLE_ID FROM WEE_AUTH_ROLE START WITH ROLE_ID=? CONNECT BY PRIOR ROLE_ID=PARENT_ID) ORDER BY ROLE_NAME";
	
	private static final String UPDATE_PARENT="UPDATE WEE_AUTH_ROLE SET PARENT_ID=? WHERE ROLE_ID=?";
	
	private static class RoleTreeRowMapper implements RowMapper {

		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			TreeNode node = new TreeNode();
			node.setKey(rs.getString("ROLE_ID"));
			node.setTitle(rs.getString("ROLE_NAME"));
			node.setParentId(rs.getString("PARENT_ID"));
			String des=rs.getString("DESCRIPTION");
			if(des!=null){
				node.setTooltip(des);
			}else{
				node.setTooltip("");
			}
			return node;
		}
	}

	private static class RoleRowMapper implements RowMapper {

		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			Role role = new Role();
			role.setId(rs.getString("ROLE_ID"));
			role.setRoleName(rs.getString("ROLE_NAME"));
			role.setParentId(rs.getString("PARENT_ID"));
			String des=rs.getString("DESCRIPTION");
			if(des!=null){
				role.setDescription(des);
			}else{
				role.setDescription("");
			}
			
			return role;
		}
	}

	private static class UserRoleRowMapper implements RowMapper {

		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			Map map = new HashMap();
			map.put(rs.getString("USER_ID"), rs.getString("ROLE_ID"));
			return map;
		}
	}

	/**
	 * 
	 * <p>创建新角色</p>
	 * @param role 角色对象
	 * @return 角色Id
	 * @throws BizException
	 * @author: 徐冉 - xu_r@neusoft.com
	 * @data: Create on Dec 2, 2009 11:20:10 AM
	 */
	public String createRole(Role role) throws BizException {
		update(SQL_ADD_ROLE, new String[] { role.getRoleId(), role.getRoleName(), role.getDescription(),
				role.getParentId() });
		return role.getRoleId();
	}

	/**
	 * 
	 * <p>删除角色</p>
	 * @param roleId 角色Id
	 * @throws Exception
	 * @author: 徐冉 - xu_r@neusoft.com
	 * @data: Create on Dec 2, 2009 3:18:54 PM
	 */
	public void deleteRole(String roleId) throws BizException {

		update(SQL_DELETE_ROLE, new String[] { roleId });

	}


	/**
	 * 
	 * <p>查询所有角色</p>
	 * @return 所有角色节点的集合
	 * @throws Exception
	 * @author: 徐冉 - xu_r@neusoft.com
	 * @data: Create on Dec 2, 2009 4:32:58 PM
	 */
	
	public List<TreeNode> queryAllRoleTree() throws BizException {
		return query(SQL_QUERY_ROLE_INFO, new RoleTreeRowMapper());
	}

	/**
	 * 
	 * <p>精确查询角色信息</p>
	 * @param roleId 角色Id
	 * @return 角色对象
	 * @throws Exception
	 * @author: 徐冉 - xu_r@neusoft.com
	 * @data: Create on Dec 2, 2009 4:34:44 PMF
	 */
	
	public Role queryRoleById(String roleId) throws BizException {
		List<Role> list = query(SQL_QUERY_ROLE, new Object[] { roleId }, new RoleTreeRowMapper());
		Role role = list.get(0);
		return role;
	}

	/**
	 * 
	 * <p>查询当前用户可管理的角色树</p>
	 * @param userId 当前登陆用户Id
	 * @return	根据登陆用户Id返回可见角色节点集合
	 * @throws Exception
	 * @author: 徐冉 - xu_r@neusoft.com
	 * @data: Create on Dec 2, 2009 4:35:17 PM
	 */
	
	public List<TreeNode> queryRoleTree(String userId) throws BizException {
		StringBuffer sql = new StringBuffer("SELECT DISTINCT ROLE_ID, ROLE_NAME, DESCRIPTION,  PARENT_ID, "
				+ "IS_LEAF FROM WEE_AUTH_ROLE START WITH ROLE_ID IN(");
		// 生成user与role的映射表
		List<Map> userRoleMappings = query(SQL_QUERY_ROLE_BY_USER_ID, new Object[] { userId }, new UserRoleRowMapper());
		// 结果列表
		List<TreeNode> list = new ArrayList<TreeNode>();

		for (Map<String, String> map : userRoleMappings) {
			String roleId = map.get(userId);
			sql.append("'" + this.convertSQLParameter(roleId)+ "',");
		}
		sql.deleteCharAt(sql.length() - 1);
		sql.append(") CONNECT BY PRIOR ROLE_ID=PARENT_ID");
		list = query(sql.toString(), new RoleTreeRowMapper());
		return list;
	}

	/**
	 * 
	 * <p>修改角色</p>
	 * @param role 角色对象
	 * @throws Exception
	 * @author: 徐冉 - xu_r@neusoft.com
	 * @data: Create on Dec 2, 2009 4:39:38 PM
	 */
	public void updateRole(Role role) throws BizException {
		update(SQL_UPDATE_ROLE, new String[] { role.getRoleName(), role.getDescription(), role.getRoleId() });
	}

	
	public List<Role> queryRoleListByUserId(String userId) throws BizException {

		return query(SQL_QUERY_ROLE_LIST_BY_USER_ID, new Object[] { userId }, new RoleRowMapper());
	}

	
	public List<Role> queryRoleListByUserId(String[] userIds) throws BizException {
		Validate.notNull(userIds, "resourceId required");
		if (userIds != null && userIds.length > 0) {
			StringBuffer userIdstr = new StringBuffer();
			for (String userId : userIds) {
				userIdstr.append("'").append(convertSQLParameter(userId)).append("',");
			}
			return query(SQL_QUERY_ROLE_LIST_BY_USER_IDS +"  AND M.USER_ID IN ( "+ userIdstr.substring(0, userIdstr.length() - 1) + ") ORDER BY R.ROLE_NAME",
					new RoleRowMapper());

		} else {
			return null;
		}
	}

	public List<Role> queryRoleList(List<String> excludeRoleId) throws BizException {

		if (excludeRoleId != null && excludeRoleId.size() > 0) {
			StringBuffer roleIds = new StringBuffer();
			for (String roleId : excludeRoleId) {
				roleIds.append("'").append(convertSQLParameter(roleId)).append("',");
			}
			return query(SQL_QUERY_ROLE_LIST_OF_ALL + " WHERE ROLE_ID NOT IN ("
					+ roleIds.substring(0, roleIds.length() - 1) + ") ORDER BY R.ROLE_NAME", new RoleRowMapper());

		} else {
			return query(SQL_QUERY_ROLE_LIST_OF_ALL + " ORDER BY R.ROLE_NAME", new RoleRowMapper());
		}
	}

	
	public List<Role> queryRoleListOfResource(String resourceId) throws BizException {
		Validate.notNull(resourceId, "resourceId required");
		return query(SQL_QUERY_ROLE_LIST_OF_RESOURCE, new String[] { resourceId }, new RoleRowMapper());
	}
	/**
	 * 
	 * <p>批量精确查询角色信息</p>
	 * @param roleIds 角色Id数组
	 * @return 角色数组
	 * @throws BizException
	 * @author: 【徐冉】 - 【xu_r@neusoft.com】
	 * @data: Create on 2010-1-13 下午03:41:13
	 */
	
	public Role[] queryRoleById(String[] roleIds) throws BizException {
		Role []roles=new Role[]{};
		StringBuffer sqlBuffer=new StringBuffer();
		sqlBuffer.append(SQL_QUERY_ROLE_BY_IDS);
		sqlBuffer.append("(");
		for(int i=0;i<roleIds.length;i++){
			sqlBuffer.append("'");
			sqlBuffer.append(this.convertSQLParameter(roleIds[i]));
			sqlBuffer.append("',");
		}
		sqlBuffer.deleteCharAt(sqlBuffer.length()-1);
		sqlBuffer.append(")");
		List<Role> list = this.query(sqlBuffer.toString(), new RoleRowMapper());
		roles=list.toArray(roles);
		return roles;
	}

	/**
	 * <p>同级下是否重名</p>
	 * @return
	 * @throws BizExcepton
	 * @author: 李志伟 - li.zhw@neusoft.com
	 * @data: Create on Apr 27, 2010 9:40:03 AM
	 */
	public boolean isExistSameParent(Role role) throws BizException{
		if (StringUtils.isEmpty(role.getRoleName()) || StringUtils.isEmpty(role.getParentId())) {
			return false;
		}
		if (StringUtils.isEmpty(role.getId())) {
			return 0 != queryForInt(QUERY_EXIST_FOR_INSERT, new Object[] { role.getRoleName(), role.getParentId() });
		}
		return 0 != queryForInt(QUERY_EXIST_FOR_UPDATE, new Object[] { role.getRoleName(), role.getParentId(),
				role.getId() });
	}
	
	/**
	 * <p>查询除需要移动的节点及其子节点的角色树-管理员角色</p>
	 * @param unit_id
	 * @return
	 * @throws BizException
	 * @author: 李志伟 - li.zhw@neusoft.com
	 * @data: Create on May 15, 2010 12:33:36 PM
	 */
	@SuppressWarnings("unchecked")
	public List<TreeNode> queryUnitTreeForUpdateParent(String roleId) throws BizException {
		return this.query(QUERY_ROLE_FOR_UPDATE_PARENT,new Object[]{roleId}, new RoleTreeRowMapper());
	}
	
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
	public List<TreeNode> queryUnitTreeByUnitIdForUpdateParent(String userId,String updateRoleId)throws BizException{
		StringBuffer sql = new StringBuffer("SELECT * FROM (SELECT DISTINCT ROLE_ID, ROLE_NAME, DESCRIPTION,  PARENT_ID, "
				+ "IS_LEAF FROM WEE_AUTH_ROLE START WITH ROLE_ID IN(");
		// 生成user与role的映射表
		List<Map> userRoleMappings = query(SQL_QUERY_ROLE_BY_USER_ID, new Object[] { userId }, new UserRoleRowMapper());
		// 结果列表
		List<TreeNode> list = new ArrayList<TreeNode>();

		for (Map<String, String> map : userRoleMappings) {
			String roleId = map.get(userId);
			sql.append("'" + this.convertSQLParameter(roleId)+ "',");
		}
		sql.deleteCharAt(sql.length() - 1);
		sql.append(") CONNECT BY PRIOR ROLE_ID=PARENT_ID) WHERE UNIT_ID NOT IN(SELECT UNIT_ID FROM WEE_AUTH_ROLE START WITH ROLE_ID=? CONNECT BY PRIOR ROLE_ID=PARENT_ID)");
		list = query(sql.toString(),new Object[]{updateRoleId}, new RoleTreeRowMapper());
		return list;
	}
	
	/**
	 * <p>修改角色父子关系</p>
	 * @param unitId
	 * @param newParentId
	 * @throws BizException
	 * @author: 李志伟 - li.zhw@neusoft.com
	 * @data: Create on May 15, 2010 12:23:13 PM
	 */
	public void updateRoleParent(String roleId,String newParentId) throws BizException {
		this.update(UPDATE_PARENT, new Object[]{newParentId,roleId});
	}
}
