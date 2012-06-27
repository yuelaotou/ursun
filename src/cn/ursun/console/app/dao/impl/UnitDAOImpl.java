package cn.ursun.console.app.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.apache.struts2.ServletActionContext;
import org.springframework.jdbc.core.RowMapper;

import cn.ursun.console.app.dao.UnitDAO;
import cn.ursun.console.app.domain.Unit;
import cn.ursun.platform.core.exception.BizException;
import cn.ursun.platform.core.jdbc.WeeJdbcDAO;
import cn.ursun.platform.core.util.IDGenerator;
import cn.ursun.platform.ps.domain.TreeNode;

import com.opensymphony.xwork2.ActionContext;

/**
 * <p>组织机构数据操作</p>
 * @author 李志伟 - li.zhw@neusoft.com
 * @version 1.0
 */
public class UnitDAOImpl extends WeeJdbcDAO implements UnitDAO {

	private static final String QUERY_LIST_SQL = " SELECT UNIT_ID , UNIT_NAME ,DESCRIPTION "
			+ " ,PARENT_ID ,IS_LEAF FROM WEE_ORG_UNIT order by unit_name";

	private static final String CREATE_UNIT_SQL = "INSERT INTO WEE_ORG_UNIT "
			+ " (UNIT_ID,UNIT_NAME,DESCRIPTION,PARENT_ID,IS_LEAF) VALUES (?,?,?,?,?)";

	private static final String QUERY_UPDATE_UNIT_SQL = " UPDATE WEE_ORG_UNIT"
			+ " SET UNIT_NAME=?,DESCRIPTION=? WHERE UNIT_ID=?";

	private static final String QUERY_DELETE_UNIT_SQL = " DELETE FROM WEE_ORG_UNIT WHERE UNIT_ID =?";

	private static final String QUERY_UNIT_LIST_BY_USER_ID = "SELECT U.UNIT_ID,  U.UNIT_NAME, U.PARENT_ID, U.DESCRIPTION  FROM WEE_ORG_UNIT U, WEE_ORG_M_USER_UNIT M  WHERE U.UNIT_ID = M.UNIT_ID AND M.USER_ID = ? ORDER BY UNIT_NAME";

	private static final String QUERY_UNIT_LIST_BY_UNIT_ID = "SELECT UNIT_ID,UNIT_NAME,PARENT_ID,IS_LEAF,DESCRIPTION FROM WEE_ORG_UNIT START WITH UNIT_ID=? CONNECT BY PRIOR UNIT_ID=PARENT_ID order by unit_name";

	private static final String QUERY_UNIT_BY_UNIT_ID = "SELECT UNIT_ID,UNIT_NAME,PARENT_ID,IS_LEAF,DESCRIPTION FROM WEE_ORG_UNIT WHERE UNIT_ID=?";

	private static final String QUERY_EXIST_FOR_INSERT = "SELECT COUNT(1) FROM WEE_ORG_UNIT WHERE UNIT_NAME=? AND PARENT_ID=?";

	private static final String QUERY_EXIST_FOR_UPDATE = "SELECT COUNT(1) FROM WEE_ORG_UNIT WHERE UNIT_NAME=? AND PARENT_ID=? AND UNIT_ID!=?";

	private static final String QUERY_UNIT_FOR_UPDATE_PARENT = "SELECT * FROM WEE_ORG_UNIT WHERE UNIT_ID NOT IN(SELECT UNIT_ID FROM WEE_ORG_UNIT START WITH UNIT_ID=? CONNECT BY PRIOR UNIT_ID=PARENT_ID) ORDER BY UNIT_NAME";

	private static final String QUERY_UNIT_FOR_UPDATE_PARENT_A = "SELECT * FROM ("
			+ QUERY_UNIT_BY_UNIT_ID
			+ ") WHERE UNIT_ID NOT IN(SELECT UNIT_ID FROM WEE_ORG_UNIT START WITH UNIT_ID=? CONNECT BY PRIOR UNIT_ID=PARENT_ID) ORDER BY UNIT_NAME";

	private static final String UPDATE_PARENT = "UPDATE WEE_ORG_UNIT SET PARENT_ID=? WHERE UNIT_ID=?";

	/**
	 * <p>构造组织机构树</p>
	 * @return
	 * @throws BizException
	 * @author: 张猛 - zhangmeng@neusoft.com
	 * @data: Create on 2009-12-4 上午9:59:20
	 */
	private static class UnitTreeMapper implements RowMapper {

		String contextPath = ServletActionContext.getRequest().getContextPath();

		Locale locale = ActionContext.getContext().getLocale();

		private UnitTreeMapper() {
			Locale temp = (Locale) ServletActionContext.getRequest().getSession().getAttribute("WW_TRANS_I18N_LOCALE");
			if (temp != null) {
				locale = temp;
			}
		}

		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			TreeNode node = new TreeNode();
			node.setKey(rs.getString("UNIT_ID"));
			// *********modify by 王敏 begin 2010-01-27*************//
			String name = rs.getString("UNIT_NAME");
			if (name.length() > 9) {
				name = name.substring(0, 9) + "...";
				node.setTitle(name);
			} else {
				node.setTitle(rs.getString("UNIT_NAME"));
			}
			// *********modify by 王敏 end *************//
			if ("root".equals(rs.getString("UNIT_ID"))) {
				node.setUrl(new StringBuffer("showUserAC!queryUserListOfUnit?unit=").toString());
			} else {
				node.setUrl(new StringBuffer("showUserAC!queryUserListOfUnit.do?unit=").append(rs.getString("UNIT_ID"))
						.toString());
			}
			node.setParentId(rs.getString("PARENT_ID"));
			if ("1".equals(rs.getString("IS_LEAF"))) {
				node.setIsFolder(false);
			} else {
				node.setIsFolder(true);
			}
			// *********modify by 王敏 begin 2010-01-27*************//

			if (rs.getString("DESCRIPTION") == null) {
				node.setTooltip("");
			} else {
				node.setTooltip(rs.getString("DESCRIPTION"));
			}

			// node.setTooltip(rs.getString("UNIT_NAME"));
			// *********modify by 王敏 end *************//
			return node;
		}
	}

	private static class UnitMaper implements RowMapper {

		private String unitId;

		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			unitId = rs.getString("UNIT_ID");
			Unit unit = new Unit();
			unit.setId(unitId);
			unit.setUnitName(rs.getString("UNIT_NAME"));
			unit.setParentId(rs.getString("PARENT_ID"));
			unit.setDescription(rs.getString("DESCRIPTION"));
			return unit;
		}
	}

	/**
	 * <p>查询组织机构树</p>
	 * @return
	 * @throws BizException
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-23 下午08:34:28
	 */
	@SuppressWarnings("unchecked")
	public List<TreeNode> queryUnitTree() throws BizException {
		return this.query(QUERY_LIST_SQL, new UnitTreeMapper());
	}

	/**
	 * <p>查询指定节点及其下的组织机构</p>
	 * @return
	 * @throws BizException
	 * @author: 李志伟 - li.zhw@neusoft.com
	 * @data: Create on Jan 4, 2010 10:40:13 AM
	 */
	@SuppressWarnings("unchecked")
	public List<TreeNode> queryUnitTreeByUnitId(String unitId) throws BizException {
		return this.query(QUERY_UNIT_LIST_BY_UNIT_ID, new Object[] { unitId }, new UnitTreeMapper());
	}

	/**
	 * <p>查询指定节点的组织机构详细信息</p>
	 * @param unitId
	 * @return
	 * @throws BizException
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-23 下午08:35:02
	 */
	public Unit queryUnitById(String unitId) throws BizException {
		return (Unit) this.queryForObject(QUERY_UNIT_BY_UNIT_ID, new Object[] { unitId }, new UnitMaper());
	}

	/**
	 * <p>添加一个组织单元</p>
	 * @param unit
	 * @throws BizException
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-23 下午08:34:43
	 */
	public String createUnit(Unit unit) throws BizException {
		String id = IDGenerator.generateId();
		this.update(CREATE_UNIT_SQL, new Object[] { id, unit.getUnitName(), unit.getDescription(), unit.getParentId(),
				1 });
		return id;
	}

	/**
	 * <p>修改组织单元</p>
	 * @param unit
	 * @throws BizException
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-23 下午08:31:28
	 */
	public void updateUnit(Unit unit) throws BizException {
		this.update(QUERY_UPDATE_UNIT_SQL, new Object[] { unit.getUnitName(), unit.getDescription(), unit.getId() });
	}

	/**
	 * <p>删除组织单元,并删除该组织单元下与用户的关联</p>
	 * @param unitId
	 * @throws BizException
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-23 下午08:28:43
	 */
	public void deleteUnitUser(String unit) throws BizException {
		List<Object[]> params = new ArrayList<Object[]>();
		params.add(unit.split(","));
		this.batchUpdate(QUERY_DELETE_UNIT_SQL, params);
	}

	/**
	 * <p>通过用户ID查询组织机构列表</p>
	 * @param userId
	 * @return
	 * @throws BizException
	 * @author: 李志伟 - li.zhw@neusoft.com
	 * @data: Create on Dec 21, 2009 4:11:33 PM
	 */
	@SuppressWarnings("unchecked")
	public List<Unit> queryUnitListOfUser(String userId) throws BizException {
		Validate.notNull(userId, "userId required");
		return query(QUERY_UNIT_LIST_BY_USER_ID, new Object[] { userId }, new UnitMaper());
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
	public boolean isUnitExistSameParent(Unit unit) throws BizException {
		if (StringUtils.isEmpty(unit.getUnitName()) || StringUtils.isEmpty(unit.getParentId())) {
			return false;
		}
		if (StringUtils.isEmpty(unit.getId())) {
			return 0 != queryForInt(QUERY_EXIST_FOR_INSERT, new Object[] { unit.getUnitName(), unit.getParentId() });
		}
		return 0 != queryForInt(QUERY_EXIST_FOR_UPDATE, new Object[] { unit.getUnitName(), unit.getParentId(),
				unit.getId() });
	}

	/**
	 * <p>查询除需要移动的节点及其子节点的组织机构树-管理员角色</p>
	 * @param unit_id
	 * @return
	 * @throws BizException
	 * @author: 李志伟 - li.zhw@neusoft.com
	 * @data: Create on May 15, 2010 12:33:36 PM
	 */
	@SuppressWarnings("unchecked")
	public List<TreeNode> queryUnitTreeForUpdateParent(String unitId) throws BizException {
		return this.query(QUERY_UNIT_FOR_UPDATE_PARENT, new Object[] { unitId }, new UnitTreeMapper());
	}

	/**
	 * <p>查询除需要移动的节点及其子节点的组织机构树-普通用户角色</p>
	 * @param userUnitId
	 * @param updateUnitId
	 * @return
	 * @throws BizException
	 * @author: 李志伟 - li.zhw@neusoft.com
	 * @data: Create on May 15, 2010 12:47:47 PM
	 */
	@SuppressWarnings("unchecked")
	public List<TreeNode> queryUnitTreeByUnitIdForUpdateParent(String userUnitId, String updateUnitId)
			throws BizException {
		return this.query(QUERY_UNIT_FOR_UPDATE_PARENT_A, new Object[] { userUnitId, updateUnitId },
				new UnitTreeMapper());
	}

	/**
	 * <p>修改组织机构父子关系</p>
	 * @param unitId
	 * @param newParentId
	 * @throws BizException
	 * @author: 李志伟 - li.zhw@neusoft.com
	 * @data: Create on May 15, 2010 12:23:13 PM
	 */
	public void updateUnitParent(String unitId, String newParentId) throws BizException {
		this.update(UPDATE_PARENT, new Object[] { newParentId, unitId });
	}
}
