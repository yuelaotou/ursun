package cn.ursun.console.app.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.lob.LobCreator;

import cn.ursun.console.app.console.configloader.AuthConfigHolder;
import cn.ursun.console.app.console.role.dto.RoleQC;
import cn.ursun.console.app.console.user.dto.UserQC;
import cn.ursun.console.app.console.user.util.XmlUtils;
import cn.ursun.console.app.dao.UserDAO;
import cn.ursun.console.app.domain.User;
import cn.ursun.console.app.domain.UserAccount;
import cn.ursun.console.app.domain.UserExtendColumn;
import cn.ursun.platform.core.dto.Pagination;
import cn.ursun.platform.core.exception.BizException;
import cn.ursun.platform.core.jdbc.WeeJdbcDAO;
import cn.ursun.platform.ps.domain.Code;
import cn.ursun.platform.ps.domain.TreeNode;

/**
 * <p>用户管理数据操作</p>
 * @author 李志伟 - li.zhw@neusoft.com
 * @version 1.0
 */
public class UserDAOImpl extends WeeJdbcDAO implements UserDAO {

	/**
	 * 查询指定组织机构下的用户列表
	 */
	private static final String QUERY_USER_INFO = "SELECT U.USER_ID, U.FULL_NAME, U.SEX, U.TEL, U.EMAIL, U.DESCRIPTION "
			+ " ,A.USERNAME,A.REGISTE_DATE,A.ENABLED"
			+ " FROM WEE_ORG_USER U , WEE_ORG_UNIT T , WEE_ORG_M_USER_UNIT M ,  WEE_ORG_ACCOUNT A     "
			+ " WHERE U.USER_ID=M.USER_ID AND T.UNIT_ID=M.UNIT_ID  AND U.USER_ID=A.USER_ID AND T.UNIT_ID = ? AND U.USER_ID<>? ORDER BY A.REGISTE_DATE DESC";

	/**
	 * 查询未关联组织机构的人员列表
	 */
	private static final String QUERY_USER_NO_ORG = "SELECT U.USER_ID, U.FULL_NAME, U.SEX, U.TEL, U.EMAIL, U.DESCRIPTION "
			+ " ,M.USERNAME ,M.REGISTE_DATE,M.ENABLED"
			+ " FROM WEE_ORG_USER U , WEE_ORG_ACCOUNT M ,WEE_ORG_USER_EXTEND E"
			+ " WHERE U.USER_ID=M.USER_ID AND U.USER_ID NOT IN (SELECT T.USER_ID FROM WEE_ORG_M_USER_UNIT T) AND E.USER_ID(+)=U.USER_ID ";

	/**
	 * 根据输入的查询条件查询用户列表
	 */
	private static final String QUERY_USER_INFO_CONDITION = "SELECT U.USER_ID, U.FULL_NAME, U.SEX, U.TEL, U.EMAIL, U.DESCRIPTION "
			+ " ,M.USERNAME,M.REGISTE_DATE,M.ENABLED "
			+ " FROM WEE_ORG_USER U ,  WEE_ORG_ACCOUNT M  ,WEE_ORG_M_USER_UNIT T  ,WEE_ORG_USER_EXTEND E "
			+ " WHERE U.USER_ID=M.USER_ID AND  U.USER_ID=T.USER_ID(+) AND E.USER_ID(+)=U.USER_ID";

	/**
	 * 插入用户信息
	 */
	private static final String INSERT_USER_INFO = "INSERT INTO WEE_ORG_USER (USER_ID,FULL_NAME,SEX,TEL,EMAIL,DESCRIPTION) VALUES (?,?,?,?,?,?)";

	/**
	 * 删除帐户信息
	 */
	private static final String DELETE_USE_RACCOUNT_INFO = "DELETE  FROM WEE_ORG_USER WHERE USER_ID=?";

	/**
	 * 插入用户和组织机构关系
	 */
	private static final String CREATE_USER_UNIT_MAPPING = "INSERT  INTO WEE_ORG_M_USER_UNIT (USER_ID,UNIT_ID) VALUES (?,?)";

	/**
	 * 更新用户信息
	 */
	private static final String UPDATE_USER_INFO = "  UPDATE WEE_ORG_USER  SET   FULL_NAME=?,SEX=? ,TEL=?, "
			+ " EMAIL=?, DESCRIPTION=? " + " WHERE USER_ID=?";

	/**
	 * 删除用户与组织机构关系
	 */
	private static final String DELETE_USER_UNIT_MAPPING = "DELETE  FROM WEE_ORG_M_USER_UNIT WHERE USER_ID=?";

	/**
	 * 插入用户与角色关系
	 */
	private static final String CREATE_USER_ROLE_MAPPING = "INSERT  INTO WEE_AUTH_M_USER_ROLE (USER_ID,ROLE_ID) VALUES (?,?)";

	/**
	 * 更新用户与角色关系
	 */
	private static final String UPDATE_USER_ROLE_MAPPING = "INSERT  INTO  WEE_AUTH_M_USER_ROLE "
			+ "  (ROLE_ID,USER_ID) VALUES (?,?)  ";

	/**
	 * 删除用户与角色关系
	 */
	private static final String DELETE_USER_ROLE_MAPPING = "DELETE FROM   WEE_AUTH_M_USER_ROLE "
			+ "  WHERE USER_ID=?  ";

	/**
	 * 删除帐户信息
	 */
	private static final String DELETE_ACCOUNT = "DELETE  FROM WEE_ORG_ACCOUNT WHERE USER_ID=?";

	/**
	 * 查询角色及用户与角色的关联关系
	 */
	private static final String QUERY_USERROLE_INFO = "SELECT R.ROLE_ID, R.ROLE_NAME, "
			+ "  R.DESCRIPTION,  R.PARENT_ID, (SELECT COUNT(1)" + "  FROM WEE_AUTH_M_USER_ROLE B "
			+ "  WHERE B.USER_ID = ? " + " AND B.ROLE_ID = R.ROLE_ID)  ISSELECT " + " FROM WEE_AUTH_ROLE R";

	private static final String QUERY_USERS_OF_OTHER_ROLE = " SELECT A.USERNAME USERNAME,U.USER_ID USER_ID,U.FULL_NAME FULL_NAME,U.TEL TEL,U.DESCRIPTION DESCRIPTION,A.REGISTE_DATE "
			+ "FROM WEE_ORG_M_USER_UNIT RUU,WEE_ORG_USER U,WEE_ORG_ACCOUNT A "
			+ "WHERE U.USER_ID =RUU.USER_ID AND A.USER_ID =U.USER_ID "
			+ "AND U.USER_ID NOT IN(SELECT R.USER_ID FROM WEE_AUTH_M_USER_ROLE R,WEE_ORG_USER Y WHERE R.USER_ID=Y.USER_ID AND R.ROLE_ID=?) "
			+ "AND RUU.UNIT_ID=?";

	private static final String QUERY_USERS_OF_ROLE_FOR_USER = "SELECT  A.USERNAME USERNAME,U.USER_ID USER_ID,U.FULL_NAME FULL_NAME,U.TEL TEL,A.REGISTE_DATE,"
			+ "U.DESCRIPTION DESCRIPTION FROM WEE_ORG_USER U,WEE_ORG_ACCOUNT A,WEE_AUTH_M_USER_ROLE R "
			+ "WHERE U.USER_ID=A.USER_ID AND A.USER_ID=R.USER_ID AND R.ROLE_ID=? AND U.USER_ID!=? "
			+ "AND U.USER_ID IN ( SELECT UUR.USER_ID FROM WEE_ORG_M_USER_UNIT UUR WHERE UUR.UNIT_ID IN (SELECT UNIT_ID FROM WEE_ORG_UNIT START WITH UNIT_ID=? CONNECT BY PRIOR UNIT_ID=PARENT_ID ))";

	private static final String QUERY_USERS_OF_ROLE_FOR_ADMIN = "SELECT  A.USERNAME USERNAME,U.USER_ID USER_ID,U.FULL_NAME FULL_NAME,U.TEL TEL,A.REGISTE_DATE,"
			+ "U.DESCRIPTION DESCRIPTION FROM WEE_ORG_USER U,WEE_ORG_ACCOUNT A,WEE_AUTH_M_USER_ROLE R "
			+ "WHERE U.USER_ID=A.USER_ID AND A.USER_ID=R.USER_ID AND R.ROLE_ID=? AND U.USER_ID!=?";

	private static final String DELETE_USER_ROLE_MAPPING_CASCADE = "DELETE FROM WEE_AUTH_M_USER_ROLE WHERE ROLE_ID=?";

	/**
	 * 查询指定ID的用户信息
	 */
	private static final String QUERY_USER_BY_ID = "SELECT USER_ID, FULL_NAME, SEX, DESCRIPTION FROM WEE_ORG_USER WHERE USER_ID = ?";

	/**
	 * 插入用户扩展信息
	 */
	private static final String CREATE_USER_EXTEND_INFO = "INSERT INTO WEE_ORG_USER_EXTEND(USER_ID,EXTENDINFO) VALUES(?,?)";

	/**
	 * 删除用户扩展信息
	 */
	private static final String DELETE_USER_EXTEND_INFO = "DELETE FROM WEE_ORG_USER_EXTEND WHERE USER_ID=?";

	/**
	 * 更新用户扩展信息
	 */
	private static final String UPDATE_USER_EXTEND_INFO = "UPDATE WEE_ORG_USER_EXTEND SET EXTENDINFO=? WHERE USER_ID=?";

	private static class AccountRowMapper implements RowMapper {

		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			UserAccount userAccount = new UserAccount();
			userAccount.getUser().setId(rs.getString("USER_ID"));
			userAccount.getUser().setFullName(rs.getString("FULL_NAME"));
			userAccount.getUser().setSex(rs.getString("SEX"));

			if (rs.getString("TEL") == null) {
				userAccount.getUser().setTel("");
			} else {
				userAccount.getUser().setTel(rs.getString("TEL"));
			}
			userAccount.getUser().setEmail(rs.getString("EMAIL"));
			userAccount.getUser().setDescription(rs.getString("DESCRIPTION"));
			userAccount.getAccount().setUsername(rs.getString("USERNAME"));

			userAccount.getAccount().setRegisterDate(rs.getDate("REGISTE_DATE"));

			if ("1".equals(rs.getString("ENABLED"))) {
				userAccount.getAccount().setEnabled(true);
			} else {
				userAccount.getAccount().setEnabled(false);
			}
			return userAccount;
		}
	}

	private static class RoleRowMapper implements RowMapper {

		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			TreeNode node = new TreeNode();
			node.setKey(rs.getString("ROLE_ID"));
			node.setTitle(rs.getString("ROLE_NAME"));
			node.setParentId(rs.getString("PARENT_ID"));
			node.setTooltip(rs.getString("DESCRIPTION"));
			if ("1".equals(rs.getString("isSelect"))) {
				node.setSelect(true);
			} else {

				node.setSelect(false);
			}
			return node;
		}
	}

	private static class UsersOfRoleRowMapper implements RowMapper {

		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			UserAccount userAccount = new UserAccount();
			userAccount.getAccount().setUsername(rs.getString("USERNAME"));
			userAccount.getAccount().setUserId(rs.getString("USER_ID"));
			userAccount.getUser().setFullName(rs.getString("FULL_NAME"));
			String tel = rs.getString("TEL");
			if (tel == null) {
				userAccount.getUser().setTel("");
			} else {
				userAccount.getUser().setTel(tel);
			}
			String description = rs.getString("DESCRIPTION");
			if (description == null) {
				userAccount.getUser().setDescription("");
			} else {
				userAccount.getUser().setDescription(description);
			}
			userAccount.getAccount().setRegisterDate(rs.getDate("REGISTE_DATE"));
			return userAccount;
		}

	}

	private static class UserRowMapper implements RowMapper {

		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			User user = new User();
			user.setId(rs.getString("USER_ID"));
			user.setFullName(rs.getString("FULL_NAME"));
			user.setDescription(rs.getString("DESCRIPTION"));
			user.setSex(rs.getString("SEX"));
			return user;
		}
	}

	private class CodeRowMapper implements RowMapper {

		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			Code code = new Code();
			code.setCode(rs.getString(1));
			code.setCodeName(rs.getString(2));
			return code;
		}
	}

	/**
	 * <p>根据用户名、用户真实姓名查询用户列表</p>
	 * @param queryUserCOND 用户查询条件
	 * @param pagination
	 * @return
	 * @throws BizException
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-23 下午07:51:43
	 */
	@SuppressWarnings("unchecked")
	public List<UserAccount> queryUserList(UserQC queryUserCOND, Pagination pagination) throws BizException {
		StringBuffer sqlBuffer = new StringBuffer();

		if (StringUtils.isNotEmpty(queryUserCOND.getUnit())) {
			sqlBuffer.append(QUERY_USER_INFO_CONDITION);
			sqlBuffer.append(" AND  T.UNIT_ID = '");
			sqlBuffer.append(convertSQLParameter(queryUserCOND.getUnit()));
			sqlBuffer.append("'");
		} else {
			sqlBuffer.append(QUERY_USER_NO_ORG);
		}
		if (StringUtils.isNotEmpty(StringUtils.trim(queryUserCOND.getUserName()))) {
			sqlBuffer.append(" AND LOWER(M.USERNAME) LIKE '%");
			sqlBuffer.append(convertSQLParameter(queryUserCOND.getUserName().trim().toLowerCase()));
			sqlBuffer.append("%' ESCAPE '\\'");
		}
		if (StringUtils.isNotEmpty(StringUtils.trim(queryUserCOND.getFullName()))) {
			sqlBuffer.append(" AND LOWER(U.FULL_NAME) LIKE '%");
			sqlBuffer.append(convertSQLParameter(queryUserCOND.getFullName().trim().toLowerCase()));
			sqlBuffer.append("%' ESCAPE '\\'");
		}
		if (StringUtils.isNotEmpty(queryUserCOND.getEnabled())) {
			sqlBuffer.append(" AND M.ENABLED = '");
			sqlBuffer.append(convertSQLParameter(queryUserCOND.getEnabled()));
			sqlBuffer.append("'");
		}
		if (StringUtils.isNotEmpty(queryUserCOND.getUserId())) {
			sqlBuffer.append(" AND U.USER_ID <>'");
			sqlBuffer.append(convertSQLParameter(queryUserCOND.getUserId()));
			sqlBuffer.append("'");
		}
		if (StringUtils.isNotEmpty(queryUserCOND.getRegisteDateBegin())) {
			sqlBuffer.append(" AND M.REGISTE_DATE >= to_date('");
			sqlBuffer.append(convertSQLParameter(queryUserCOND.getRegisteDateBegin()));
			sqlBuffer.append("','yyyy-MM-dd')");
		}
		if (StringUtils.isNotEmpty(queryUserCOND.getRegisteDateEnd())) {
			sqlBuffer.append(" AND M.REGISTE_DATE <= to_date('");
			sqlBuffer.append(convertSQLParameter(queryUserCOND.getRegisteDateEnd()));
			sqlBuffer.append("','yyyy-MM-dd')+1");
		}
		if (queryUserCOND.getExtendCondition() != null) {
			Map<String, String> map = queryUserCOND.getExtendCondition();
			for (String key : map.keySet()) {
				if (StringUtils.isEmpty(map.get(key).trim())) {
					continue;
				}
				Map<String, UserExtendColumn> columnMap = AuthConfigHolder.getInstance().getAuthConfig()
						.getUserExpandInfo();
				UserExtendColumn column = columnMap.get(key);
				if ("like".equals(column.getOperate())) {
					sqlBuffer
							.append(" and exists(select value(val) from wee_org_user_extend i,table(XMLSequence(extract(xmltype(i.extendinfo),'/columns[contains(");
					sqlBuffer.append(convertSQLParameter(key));
					sqlBuffer.append(",''");
					sqlBuffer.append(convertSQLParameter(map.get(key).trim()));
					sqlBuffer.append("'')]'))) val where i.user_id=e.user_id)");
				} else {
					sqlBuffer
							.append(" and exists(select value(val) from wee_org_user_extend i,table(XMLSequence(extract(xmltype(i.extendinfo),'/columns[");
					sqlBuffer.append(convertSQLParameter(key));
					sqlBuffer.append(column.getOperate());
					sqlBuffer.append("''");
					sqlBuffer.append(convertSQLParameter(map.get(key).trim()));
					sqlBuffer.append("'']'))) val where i.user_id=e.user_id)");
				}
			}
		}
		sqlBuffer.append(" ORDER BY M.REGISTE_DATE DESC");
		return this.query(sqlBuffer.toString(), new AccountRowMapper(), pagination);
	}

	/**
	 * <p>查询组织机构下的用户列表</p>
	 * @param unitId 组织机构ID
	 * @param queryUserCOND
	 * @param pagination
	 * @return
	 * @throws BizException
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-23 下午08:00:57
	 */
	@SuppressWarnings("unchecked")
	public List<UserAccount> queryUserListOfUnit(String unitId, UserQC queryUserCOND, Pagination pagination)
			throws BizException {
		if (StringUtils.isEmpty(unitId)) {
			return this.query(new StringBuffer(QUERY_USER_NO_ORG).append(
					" AND U.USER_ID<>? ORDER BY M.REGISTE_DATE DESC").toString(), new Object[] { queryUserCOND
					.getUserId() }, new AccountRowMapper(), pagination);
		} else {
			return this.query(QUERY_USER_INFO, new Object[] { unitId, queryUserCOND.getUserId() },
					new AccountRowMapper(), pagination);
		}
	}

	/**
	 * <p>新增用户</p>
	 * @param user 新增的用户
	 * @return 新增的用户
	 * @throws BizException
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-23 下午08:05:00
	 */
	public User createUser(User user) throws BizException {
		this.update(INSERT_USER_INFO, new Object[] { user.getId(), user.getFullName(), user.getSex().toString(),
				user.getTel(), user.getEmail(), user.getDescription() });
		final String extend = XmlUtils.parseMapToXml(user.getExtendInfo());
		final String userId = user.getId();
		WeeLobCreator params = new WeeLobCreator() {

			protected void setValues(PreparedStatement ps, LobCreator lobCreator) throws SQLException,
					DataAccessException {
				ps.setString(1, userId);
				lobCreator.setClobAsString(ps, 2, extend);
			}
		};
		execute(CREATE_USER_EXTEND_INFO, params);
		return null;
	}

	/**
	 * <p>新增用户组织机构</p>
	 * @param userId 用户ID
	 * @param unitIds 组织机构ID
	 * @throws BizException
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-23 下午08:05:00
	 */
	public void createUserUnitMapping(String userId, String unitIds) throws BizException {
		this.update(CREATE_USER_UNIT_MAPPING, new Object[] { userId, unitIds });
	}

	/**
	 * <p>删除用户组织机构</p>
	 * @param userId
	 * @throws BizException
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-23 下午08:05:00
	 */
	public void deleteUserUnitMapping(String userId) throws BizException {
		this.update(DELETE_USER_UNIT_MAPPING, new Object[] { userId });
	}

	/**
	 * <p>为用户添加角色</p>
	 * @param userId
	 * @param roleId
	 * @throws BizException
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-23 下午08:07:07
	 */
	public void createUserRoleMapping(String userId, String[] roleIds) throws BizException {
		ArrayList<Object[]> al = new ArrayList<Object[]>();
		int length = roleIds == null ? 0 : roleIds.length;
		for (int i = 0; i < length; i++) {
			String[] params = { userId, roleIds[i] };
			al.add(params);
		}
		this.batchUpdate(CREATE_USER_ROLE_MAPPING, al);
	}

	/**
	 * <p>为用户添加角色
	 * 1.先清除原有用户与角色关系UserDAO.deleteUserRoleMapping()
	 * 2.添加新的用户与角色关系UserDAO.createUserRoleMapping()
	 * </p>
	 * @param userId
	 * @param roleId
	 * @throws BizException
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-23 下午05:31:11
	 */
	public void updateRoleForUser(String userId, String[] roleIds) throws BizException {
		List<Object[]> al = new ArrayList<Object[]>();
		int length = roleIds == null ? 0 : roleIds.length;
		for (int i = 0; i < length; i++) {
			String[] params = { roleIds[i], userId };
			al.add(params);
		}
		this.batchUpdate(UPDATE_USER_ROLE_MAPPING, al);
	}

	/**
	 * <p>删除用户角色</p>
	 * @param userId
	 * @throws BizException
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-23 下午08:05:00
	 */
	public void deleteUserRoleMapping(String userId) throws BizException {
		this.update(DELETE_USER_ROLE_MAPPING, new Object[] { userId });
	}

	/**
	 * <p>修改用户信息</p>
	 * @param user 修改的用户
	 * @return 修改的用户
	 * @throws BizException
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-23 下午08:05:31
	 */
	public void updateUser(User user) throws BizException {
		this.update(UPDATE_USER_INFO, new Object[] { user.getFullName(), user.getSex().toString(), user.getTel(),
				user.getEmail(), user.getDescription(), user.getId() });
		final String extend = XmlUtils.parseMapToXml(user.getExtendInfo());
		final String userId = user.getId();
		WeeLobCreator params = new WeeLobCreator() {

			protected void setValues(PreparedStatement ps, LobCreator lobCreator) throws SQLException,
					DataAccessException {
				lobCreator.setClobAsString(ps, 1, extend);
				ps.setString(2, userId);
			}
		};
		this.execute(UPDATE_USER_EXTEND_INFO, params);
	}

	/**
	 * <p>删除用户
	 * </p>
	 * @param userId
	 * @throws BizException
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-23 下午08:06:40
	 */
	public void deleteUserById(String[] userIds) throws BizException {
		ArrayList<Object[]> al = new ArrayList<Object[]>();
		for (String id : userIds) {
			al.add(new Object[] { id });
		}
		this.batchUpdate(DELETE_USER_UNIT_MAPPING, al);
		this.batchUpdate(DELETE_USER_ROLE_MAPPING, al);
		this.batchUpdate(DELETE_ACCOUNT, al);
		this.batchUpdate(DELETE_USE_RACCOUNT_INFO, al);
		this.batchUpdate(DELETE_USER_EXTEND_INFO, al);
	}

	/**
	 * <p>查询登录用户可以管理的角色列表
	 * </p>
	 * @param userId
	 * @param roleId
	 * @throws BizException
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-23 下午05:31:11
	 */
	@SuppressWarnings("unchecked")
	public List<TreeNode> queryRolesOfUser(String userId) throws BizException {
		List<TreeNode> retList = query(QUERY_USERROLE_INFO, new Object[] { userId }, new RoleRowMapper());
		return retList;
	}

	/**
	 * 
	 * <p>批量删除用户与角色之间的映射关系</p>
	 * @param userIds 用户Id数组
	 * @param roleId 角色Id
	 * @throws BizException
	 * @author: 徐冉 - xu_r@neusoft.com
	 * @data: Create on 2009-12-16 下午02:04:45
	 */
	public void deleteUserRoleMapping(String[] userIds, String roleId) throws BizException {
		List<Object[]> params = new ArrayList<Object[]>();
		for (int i = 0; i < userIds.length; i++) {
			params.add(new Object[] { userIds[i], roleId });

		}
		this.batchUpdate("DELETE FROM WEE_AUTH_M_USER_ROLE WHERE USER_ID=? AND ROLE_ID=?", params);
	}

	/**
	 * 
	 * <p>本方法用于在删除角色之前级联删除用户与角色的映射关系</p>
	 * @param roleId 角色ID
	 * @author: 【徐冉】 - 【xu_r@neusoft.com】
	 * @data: Create on 2010-1-6 下午01:46:47
	 */
	public void deleteUserRoleMappingCascade(String roleId) throws BizException {
		this.update(DELETE_USER_ROLE_MAPPING_CASCADE, new Object[] { roleId });
	}

	/**
	 * 
	 * <p>查询属于某个组织结构的不属于本角色的或者没有被授予角色的用户</p>
	 * @param roleId 角色Id
	 * @param unitId 组织机构Id
	 * @param pagination 分页对象
	 * @return 返回UserAccount对象集合
	 * @throws BizException
	 * @author: 徐冉 - xu_r@neusoft.com
	 * @data: Create on 2009-12-16 下午02:05:29
	 */
	@SuppressWarnings("unchecked")
	public List<UserAccount> queryUserListOfOtherRole(String roleId, String unitId, Pagination pagination)
			throws BizException {
		if ("root".equals(unitId)) {// 超级管理员登陆系统
			return query(QUERY_USER_NO_ORG + " ORDER BY A.USERNAME", new UsersOfRoleRowMapper(), pagination);
		} else {
			return query(QUERY_USERS_OF_OTHER_ROLE + " ORDER BY A.USERNAME", new Object[] { roleId, unitId },
					new UsersOfRoleRowMapper(), pagination);
		}
	}

	/**
	 * 
	 * <p>批量创建用户角色映射关系</p>
	 * @param userIds 用户ID数组
	 * @param roleId 角色ID
	 * @throws BizException
	 * @author: 徐冉 - xu_r@neusoft.com
	 * @data: Create on 2009-12-15 下午07:13:07
	 */
	public void createUserRoleMapping(String userIds[], String roleId) throws BizException {
		List<Object[]> params = new ArrayList<Object[]>();
		if (userIds != null) {
			for (int i = 0; i < userIds.length; i++) {
				params.add(new Object[] { userIds[i], roleId });

			}
		}
		this.batchUpdate(CREATE_USER_ROLE_MAPPING, params);
	}

	/**
	 * 
	 * <p>查询角色下的用户列表</p>
	 * @param roleId 选中的角色ID
	 * @param loginRId 登陆用户的角色ID
	 * @param userId 登陆用户ID
	 * @param unitId 登陆用户所属组织机构ID
	 * @param pagination 分页对象
	 * @return 返回UserAccount对象集合
	 * @throws BizException
	 * @author: 【徐冉】 - 【xu_r@neusoft.com】
	 * @data: Create on 2010-1-6 上午11:31:58
	 */
	@SuppressWarnings("unchecked")
	public List<UserAccount> queryUserListOfRole(String roleId, String loginRId, String userId, String unitId,
			Pagination pagination) throws BizException {

		List<UserAccount> list = null;
		if (loginRId.equals("adminRole")) {
			list = query(QUERY_USERS_OF_ROLE_FOR_ADMIN + " ORDER BY A.USERNAME", new Object[] { roleId, userId },
					new UsersOfRoleRowMapper(), pagination);
		} else {
			list = query(QUERY_USERS_OF_ROLE_FOR_USER + " ORDER BY A.USERNAME",
					new Object[] { roleId, userId, unitId }, new UsersOfRoleRowMapper(), pagination);
		}
		return list;

	}

	/**
	 * 
	 * <p>根据条件查询角色下用户信息</p>
	 * @param roleQC 同角色相关的查询条件（
	 *  roleId 选中角色ID,
	 *  loginRId 登陆用户的角色ID,
	 *  userId 登陆用户ID,
	 *  unitId 登陆用户所属组织ID)
	 * @param queryUserCOND 查询条件
	 * @param pagination 分页对象
	 * @return 返回UserAccount对象集合
	 * @throws BizException
	 * @author: 【徐冉】 - 【xu_r@neusoft.com】
	 * @data: Create on 2010-1-7 上午09:49:00
	 */
	@SuppressWarnings("unchecked")
	public List<UserAccount> queryUserListOfRoleByCQ(RoleQC roleQC, UserQC queryUserCOND, Pagination pagination)
			throws BizException {
		StringBuffer sqlBuffer = new StringBuffer();
		String roleId = roleQC.getRoleId();
		String loginRId = roleQC.getLoginRId();
		String userId = roleQC.getUserId();
		String unitId = roleQC.getUnitId();
		List<UserAccount> list = null;
		if (loginRId.equals("adminRole")) {
			sqlBuffer.append(QUERY_USERS_OF_ROLE_FOR_ADMIN);
			if (StringUtils.isNotEmpty(StringUtils.trim(queryUserCOND.getUserName()))) {
				sqlBuffer.append(" AND LOWER(A.USERNAME) LIKE '%");
				sqlBuffer.append(convertSQLParameter(queryUserCOND.getUserName().trim().toLowerCase()));
				sqlBuffer.append("%' ESCAPE '\\'");
			}
			if (StringUtils.isNotEmpty(StringUtils.trim(queryUserCOND.getFullName()))) {
				sqlBuffer.append(" AND LOWER(U.FULL_NAME) LIKE '%");
				sqlBuffer.append(convertSQLParameter(queryUserCOND.getFullName().trim().toLowerCase()));
				sqlBuffer.append("%' ESCAPE '\\'");
			}
			sqlBuffer.append(" ORDER BY A.USERNAME");
			list = query(sqlBuffer.toString(), new Object[] { roleId, userId }, new UsersOfRoleRowMapper(), pagination);
		} else {
			sqlBuffer.append(QUERY_USERS_OF_ROLE_FOR_USER);
			if (StringUtils.isNotEmpty(StringUtils.trim(queryUserCOND.getUserName()))) {
				sqlBuffer.append(" AND LOWER(A.USERNAME) LIKE '%");
				sqlBuffer.append(convertSQLParameter(queryUserCOND.getUserName().trim().toLowerCase()));
				sqlBuffer.append("%' ESCAPE '\\'");
			}
			if (StringUtils.isNotEmpty(StringUtils.trim(queryUserCOND.getFullName()))) {
				sqlBuffer.append(" AND LOWER(U.FULL_NAME) LIKE '%");
				sqlBuffer.append(convertSQLParameter(queryUserCOND.getFullName().trim().toLowerCase()));
				sqlBuffer.append("%' ESCAPE '\\'");
			}
			sqlBuffer.append(" ORDER BY A.USERNAME");
			list = query(sqlBuffer.toString(), new Object[] { roleId, userId, unitId }, new UsersOfRoleRowMapper(),
					pagination);
		}
		return list;

	}

	/**
	 * 
	 * <p>根据查询条件查询不在选中角色下，属于选中组织机构的用户信息</p>
	 * @param roleId 选中角色ID
	 * @param unitId 选中组织机构ID	
	 * @param queryUserCOND 查询条件
	 * @param pagination 分页对象
	 * @return 返回UserAccount对象集合
	 * @throws BizException
	 * @author: 【徐冉】 - 【xu_r@neusoft.com】
	 * @data: Create on 2010-1-7 上午09:52:42
	 */
	@SuppressWarnings("unchecked")
	public List<UserAccount> queryUserListOfOtherRoleByCQ(String roleId, String unitId, UserQC queryUserCOND,
			Pagination pagination) throws BizException {
		StringBuffer sqlBuffer = new StringBuffer();
		List<UserAccount> list = null;
		sqlBuffer.append(QUERY_USERS_OF_OTHER_ROLE);
		if (StringUtils.isNotEmpty(StringUtils.trim(queryUserCOND.getUserName()))) {
			sqlBuffer.append(" AND LOWER(A.USERNAME) LIKE '%");
			sqlBuffer.append(convertSQLParameter(queryUserCOND.getUserName().trim().toLowerCase()));
			sqlBuffer.append("%' ESCAPE '\\'");
		}
		if (StringUtils.isNotEmpty(StringUtils.trim(queryUserCOND.getFullName()))) {
			sqlBuffer.append(" AND LOWER(U.FULL_NAME) LIKE '%");
			sqlBuffer.append(convertSQLParameter(queryUserCOND.getFullName().trim().toLowerCase()));
			sqlBuffer.append("%' ESCAPE '\\'");
		}
		sqlBuffer.append(" ORDER BY A.USERNAME");
		list = query(sqlBuffer.toString(), new Object[] { roleId, unitId }, new UsersOfRoleRowMapper(), pagination);
		return list;
	}

	public User queryUserById(String userId) throws BizException {
		Validate.notNull(userId, "userId required");
		return (User) queryForObject(QUERY_USER_BY_ID, new Object[] { userId }, new UserRowMapper());
	}

	/**
	 * <p>查询代码表</p>
	 * @param tableName
	 * @param codeName
	 * @param codeValue
	 * @return
	 * @author: 李志伟 - li.zhw@neusoft.com
	 * @data: Create on Jan 8, 2010 1:23:11 PM
	 */
	@SuppressWarnings("unchecked")
	public List<Code> queryCodeList(UserExtendColumn column) throws BizException {
		if (StringUtils.isEmpty(column.getTableName()) || StringUtils.isEmpty(column.getCodeName())
				|| StringUtils.isEmpty(column.getCodeValue())) {
			return null;
		}
		StringBuffer buffer = new StringBuffer().append("SELECT ").append(column.getCodeName()).append(",").append(
				column.getCodeValue()).append(" FROM ").append(column.getTableName());
		if (StringUtils.isNotEmpty(column.getFilterName())) {
			buffer.append(" WHERE ").append(column.getFilterName());
			if (StringUtils.isNotEmpty(column.getFilterValue())) {
				buffer.append("=").append("'").append(convertSQLParameter(column.getFilterValue())).append("'");
			} else {
				buffer.append(" IS NULL");
			}
		}
		buffer.append(" ORDER BY ").append(column.getCodeName());
		String sql = buffer.toString();
		return query(sql, new CodeRowMapper());
	}

	/**
	 * <p>查询关联项的代码表</p>
	 * @param column
	 * @param relateValue
	 * @return
	 * @throws BizException
	 * @author: 李志伟 - li.zhw@neusoft.com
	 * @data: Create on Jan 9, 2010 4:09:53 PM
	 */
	@SuppressWarnings("unchecked")
	public List<Code> queryCodeList(String columnName, String relateValue) throws BizException {
		UserExtendColumn column = AuthConfigHolder.getInstance().getAuthConfig().getUserExpandInfo().get(columnName);
		if (column == null || StringUtils.isEmpty(column.getTableName()) || StringUtils.isEmpty(column.getCodeName())
				|| StringUtils.isEmpty(column.getCodeValue())) {
			return null;
		}
		StringBuffer buffer = new StringBuffer().append("SELECT ").append(column.getCodeName()).append(",").append(
				column.getCodeValue()).append(" FROM ").append(column.getTableName());
		if (StringUtils.isNotEmpty(column.getFilterName())) {
			buffer.append(" WHERE ").append(column.getFilterName());
			if (StringUtils.isNotEmpty(relateValue)) {
				buffer.append("=").append("'").append(relateValue).append("'");
			} else {
				buffer.append(" IS NULL");
			}
		}
		buffer.append(" ORDER BY ").append(column.getCodeName());
		String sql = buffer.toString();
		return query(sql, new CodeRowMapper());
	}

	/**
	 * <p>查询扩展字段对应字典表的代码值对应的代码名称</p>
	 * @param columnName  
	 * @param relateValue 
	 * @return
	 * @throws BizException
	 * @author: 李志伟 - li.zhw@neusoft.com
	 * @data: Create on Jan 11, 2010 8:59:57 AM
	 */
	@SuppressWarnings("unchecked")
	public String queryCodeName(String columnName, String codeValue) throws BizException {
		UserExtendColumn column = AuthConfigHolder.getInstance().getAuthConfig().getUserExpandInfo().get(columnName);
		if (StringUtils.isEmpty(column.getTableName()) || StringUtils.isEmpty(column.getCodeName())
				|| StringUtils.isEmpty(column.getCodeValue())) {
			return null;
		}
		StringBuffer buffer = new StringBuffer().append("SELECT ").append(column.getCodeName())
				.append(",").append(column.getCodeValue()).append(" FROM ").append(
						column.getTableName()).append(" WHERE ").append(
						column.getCodeName()).append("='").append(codeValue)
				.append("'");
		String sql = buffer.toString();
		List<Code> list = query(sql, new CodeRowMapper());
		String result = null;
		if (list != null && list.size() != 0) {
			result = list.get(0).getCodeName();
		}
		return result;
	}
}
