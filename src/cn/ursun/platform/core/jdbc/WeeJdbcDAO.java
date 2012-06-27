/**
 * 文件名：WeeDAO.java
 * 
 * 创建人：段鹏 - duanp@neusoft.com
 * 
 * 创建时间：Aug 16, 2008 11:00:55 AM
 * 
 * 版权所有：东软软件股份有限公司
 */
package cn.ursun.platform.core.jdbc;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.support.AbstractLobCreatingPreparedStatementCallback;
import org.springframework.jdbc.support.lob.LobHandler;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import cn.ursun.platform.core.dto.Pagination;
import cn.ursun.platform.core.exception.DBException;

/**
 * <p>Wee框架提供的DAO支持基类。该类提供了DAO中使用的通用方法。</p>
 *
 * @author 段鹏 - danp@neusoft.com
 * @version 1.0 Created on Aug 16, 2008 2:27:24 PM
 */
public class WeeJdbcDAO extends NamedParameterJdbcDaoSupport {

	protected static final Log log = LogFactory.getLog(WeeJdbcDAO.class);

	/**
	 * <p>Wee批量更新语句参数设置器。</p>
	 *
	 * @author 兰硕 - lans@neusoft.com
	 * @version 1.0 Created on Sep 6, 2008 11:30:17 AM
	 */
	private class WeeBatchPSSetter implements BatchPreparedStatementSetter {

		private List<Object[]> params = null;

		public WeeBatchPSSetter(List<Object[]> params) {
			this.params = params;
		}

		/**
		 * <p>匿名类的实现方法，取得批量记录总数</p>
		 * 
		 * @see org.springframework.jdbc.core.BatchPreparedStatementSetter#getBatchSize()
		 * @author: 段鹏 - duanp@neusoft.com 
		 * @date: Created on Aug 28, 2008 10:23:06 AM
		 */
		public int getBatchSize() {
			return params.size();
		}

		/**
		 * <p>设值方法</p>
		 * 
		 * @see org.springframework.jdbc.core.BatchPreparedStatementSetter#setValues(java.sql.PreparedStatement, int)
		 * @author: 段鹏 - duanp@neusoft.com 
		 * @date: Created on Aug 28, 2008 10:23:09 AM
		 */
		public void setValues(PreparedStatement ps, int i) throws SQLException {
			Object[] param = params.get(i);
			for (int j = 0; j < param.length; j++) {
				if (param[j] instanceof java.util.Date) {
					ps.setObject(j + 1, new java.sql.Date(((java.util.Date) param[j]).getTime()));
				} else {
					ps.setObject(j + 1, param[j]);
				}
			}
		}
	}

	/**
	 * <p>操作LOB字段的回调基类</p>
	 *
	 * @author 段鹏- duanp@neusoft.com
	 * @version 1.0 Created on Sep 5, 2008 1:34:50 PM
	 */
	protected abstract class WeeLobCreator extends AbstractLobCreatingPreparedStatementCallback {

		public WeeLobCreator() {
			super(getLobHandler());
		}
	}

	/**
	 * Lob类型数据操作器。
	 */
	protected LobHandler lobHandler = null;

	public void setLobHandler(LobHandler lobHandler) {
		this.lobHandler = lobHandler;
	}

	/**
	 * <p>批量更新</p>
	 * 
	 * @param sql SQL语句
	 * @param params 参数的集合，按SQL语句中 ？的顺序进行匹配
	 * @throws DBException
	 * @author: 段鹏 - duanp@neusoft.com 
	 * @date: Created on Aug 28, 2008 10:31:57 AM
	 */
	protected void batchUpdate(String sql, final List<Object[]> params) throws DBException {
		try {
			getJdbcTemplate().batchUpdate(sql, new WeeBatchPSSetter(params));
		} catch (DataAccessException e) {
			throw new DBException(e);
		}
	}

	/**
	 * <p>批量更新</p>
	 * 
	 * @param sql SQL语句
	 * @param params 参数的集合，按SQL语句中 ？的顺序进行匹配
	 * @throws DBException
	 * @author: 段鹏 - duanp@neusoft.com 
	 * @date: Created on Aug 28, 2008 10:31:57 AM
	 */
	protected void batchUpdate(List<String> sqls, final List<Object[]> params) throws DBException {
		try {
			if (sqls.size() != params.size())
				throw new DBException("SQL语句与参数不匹配！");
			Iterator<String> sqlIterator = sqls.iterator();
			Iterator<Object[]> paramsIterator = params.iterator();
			while (sqlIterator.hasNext() && paramsIterator.hasNext()) {
				this.update(sqlIterator.next(), paramsIterator.next());
			}
		} catch (DataAccessException e) {
			throw new DBException(e);
		}
	}

	/**
	 * <p>执行更新操作，字段中带有LOB字段 </p>
	 * 
	 * @param sql
	 * @param psp
	 * @throws DBException
	 * @author: 段鹏 - duanp@neusoft.com 
	 * @date: Created on Sep 5, 2008 1:12:59 PM
	 */
	protected void execute(String sql, PreparedStatementCallback psp) throws DBException {
		try {
			getJdbcTemplate().execute(sql, psp);
		} catch (DataAccessException e) {
			throw new DBException(e);
		}
	}

	/**
	 * <p>执行存储过程方法</p>
	 * 
	 * @param callStatement 存储过程名称
	 * @param inParams		入参值
	 * @param outParamsType	出参类型
	 * @return
	 * @throws DBException
	 * @author: 段鹏 - duanp@neusoft.com 
	 * @date: Created on 2009-2-23 下午09:25:37
	 */
	protected Object execute(String callStatement, final Object[] inParams, final int[] outParamsType)
			throws DBException {
		try {
			String sql = this.generateCallStatement(callStatement, inParams, outParamsType);
			Object ret = getJdbcTemplate().execute(sql, new CallableStatementCallback() {

				public Object doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException {
					int inCnt = 0;
					if (inParams != null)
						inCnt = inParams.length;
					for (int i = 0; i < inCnt; i++) {
						cs.setObject(i + 1, inParams[i]);
					}
					if (outParamsType != null && outParamsType.length > 0)
						for (int j = 0; j < outParamsType.length; j++) {
							cs.registerOutParameter(inCnt + j + 1, outParamsType[j]);
						}
					cs.execute();
					Object[] result = null;
					if (outParamsType != null && outParamsType.length > 0) {
						result = new Object[outParamsType.length];
						for (int n = 0; n < outParamsType.length; n++) {
							result[n] = cs.getObject(inCnt + n + 1);
						}
					}
					return result;
				}
			});
			return ret;
		} catch (DataAccessException e) {
			throw new DBException(e);
		}

	}

	/**
	 * <p>生成调用存储过程SQL语句</p>
	 * 
	 * @param callStatement
	 * @param 
	 * @param ouinamsOarams
	 * @return
	 * @author: 段鹏 - duanp@neusoft.com 
	 * @date: Created on 2009-2-23 下午08:18:51
	 */
	private String generateCallStatement(String callStatement, Object[] inParams, int[] ouinamsOarams) {
		StringBuffer sb = new StringBuffer();
		sb.append("{ CALL ").append(callStatement);
		int paramsCnt = 0;
		if (inParams != null) {
			paramsCnt += inParams.length;
		}
		if (ouinamsOarams != null) {
			paramsCnt += ouinamsOarams.length;
		}
		if (paramsCnt > 0) {
			sb.append(" ( ");
			for (int i = 0; i < paramsCnt; i++) {
				sb.append("?,");
			}
			return sb.substring(0, sb.length() - 1) + " )}";
		} else {
			sb.append(" }");
		}
		return sb.toString();
	}

	// public static void main(String[] args) {
	// WeeDAO dao = new WeeDAO();
	// System.out.println(dao.generateCallStatement("PA", null, null));
	// }

	/**
	 * <p>翻页操作方法</p>
	 * 
	 * @param sql
	 * @param pagination
	 * @return
	 * @author: 段鹏 - duanp@neusoft.com 
	 * @date: Created on Sep 5, 2008 1:01:18 PM
	 */
	private String generatePagingSql(String sql, int realCnt, Pagination pagination) {
		if(pagination == null)
			pagination = new Pagination();
		pagination = getLastPage(realCnt, pagination);
		if (StringUtils.isNotEmpty(pagination.getSortColumn())) {
			String sortStr = StringUtils.defaultString(pagination.getSortColumn()) + " "
					+ StringUtils.defaultString(pagination.getSortMode());
			if (StringUtils.isNotEmpty(pagination.getGroupField())) {
				sortStr = StringUtils.defaultString(pagination.getGroupField()) + " "
						+ StringUtils.defaultString(pagination.getGroupSort()) + "," + sortStr;
			}
			return new StringBuffer("SELECT * FROM ( SELECT TT.*, ROWNUM NUM FROM (SELECT T.* FROM (").append(sql)
					.append(") T ").append(" ORDER BY ").append(sortStr).append(") TT WHERE ROWNUM <= ").append(
							pagination.getStart() + pagination.getLimit()).append(" ) TTT  WHERE TTT.NUM > ").append(
							pagination.getStart()).toString();
		} else {
			return new StringBuffer("SELECT * FROM ( SELECT T.*, ROWNUM NUM FROM (").append(sql).append(
					") T WHERE ROWNUM <= ").append(pagination.getStart() + pagination.getLimit()).append(
					" ) TT  WHERE TT.NUM > ").append(pagination.getStart()).toString();
		}

	}

	// /**
	// * <p>翻页操作方法</p>
	// *
	// * @param sql
	// * @param pagination
	// * @return
	// * @author: 段鹏 - duanp@neusoft.com
	// * @date: Created on Sep 5, 2008 1:01:18 PM
	// */
	// private String generatePagingSql(String sql, Pagination pagination) {
	// // pagination = getLastPage(realCnt, pagination);
	// return new StringBuffer("SELECT * FROM ( SELECT T.*, ROWNUM NUM FROM (").append(sql).append(
	// ") T WHERE ROWNUM <= ").append(pagination.getStart() + pagination.getLimit()).append(
	// " ) TT WHERE TT.NUM > ").append(pagination.getStart()).toString();
	// }

	/**
	 *
	 * @return 
	 */
	protected LobHandler getLobHandler() {
		return lobHandler;
	}

	/**
	 * <p>将多个数据组成对象数组列表。</p>
	 * 例如：
	 * <li>[A,B,C],X,[L,M,N] = > [A,X,L], [B,X,M], [C,X,N]
	 * <li>X,Y,Z = > [X,Y,Z]
	 * <li>[X,Y],A,[L,M,N] = > 出错，因为多个数组见长度不同。
	 * 
	 * @param data 参数需要为多个Object,如果需要传入 String[],请使用parseObjectArrayListForSingleParam方法。
	 * @return 对象数组列表
	 * @author: 兰硕 - lans@neusoft.com
	 * @date: Created on Sep 4, 2008 3:11:25 PM
	 */
	protected List<Object[]> parseObjectArrayListForMultiArrays(Object... data) {
		List<Object[]> list = new ArrayList<Object[]>();
		int temp = -1;
		boolean containArray = false;
		for (Object e : data) {
			if (e instanceof Object[]) {
				containArray = true;
				int length = ((Object[]) e).length;
				if (temp == -1)
					temp = length;
				Validate.isTrue(temp == length, "All of data which is type of Array has not the same size");
			}
		}

		if (!containArray) {
			Object[] objs = new Object[data.length];
			for (int i = 0; i < objs.length; i++)
				objs[i] = data[i];
			list.add(objs);
			return list;
		}

		for (int i = 0; i < temp; i++) {
			Object[] listE = new Object[data.length];
			for (int pos = 0; pos < data.length; pos++) {
				Object e = data[pos];
				Object ele = e;
				if (ele instanceof Object[]) {
					ele = ((Object[]) ele)[i];
				}
				listE[pos] = ele;
			}
			list.add(listE);
		}
		return list;
	}

	/**
	 * <p>将单个数据组成对象数组列表。</p>
	 * 
	 * @param data 单个数组数据
	 * @return 对象数组列表。
	 * @author: 兰硕 - lans@neusoft.com
	 * @date: Created on Sep 4, 2008 4:03:25 PM
	 */
	protected List<Object[]> parseObjectArrayListForSingleArray(Object[] data) {
		List<Object[]> list = new ArrayList<Object[]>();
		for (Object d : data) {
			list.add(new Object[] { d });
		}
		return list;
	}

	/**
	 * <p>Query given SQL to create a prepared statement from SQL and a list of arguments to bind to the query, mapping each row to a Java object via a RowMapper. </p>
	 * 
	 * @param sql - SQL query to execute
	 * @param paramMap - map of parameters to bind to the query (leaving it to the PreparedStatement to guess the corresponding SQL type)
	 * @param rowMapper - object that will map one object per row 
	 * @return
	 * @throws DBException
	 * @author: 蒲艳 - puy@neusoft.com 
	 * @date: Created on Aug 25, 2008 3:50:04 PM
	 */
	@Deprecated
	protected List query(String sql, Map paramMap, RowMapper rowMapper) throws DBException {
		try {
			return getNamedParameterJdbcTemplate().query(sql, paramMap, rowMapper);
		} catch (DataAccessException e) {
			throw new DBException(e);
		}
	}

	/**
	 * <p>Query given SQL to create a prepared statement from SQL and a list of arguments to bind to the query, mapping each row to a Java object via a RowMapper. </p>
	 * 
	 * @param sql - SQL query to execute
	 * @param paramMap - map of parameters to bind to the query (leaving it to the PreparedStatement to guess the corresponding SQL type)
	 * @param rowMapper - object that will map one object per row 
	 * @param pagination - object that will query result in a specified row range
	 * @return
	 * @throws DBException
	 * @author: 蒲艳 - puy@neusoft.com 
	 * @date: Created on Aug 25, 2008 3:51:12 PM
	 */
	@Deprecated
	protected List query(String sql, Map paramMap, RowMapper rowMapper, Pagination pagination) throws DBException {
		if(pagination == null)
			pagination = new Pagination();
		try {
			if (checkPagination(pagination))
			// sql = generatePagingSql(sql, pagination);
			{
				int totalCount = getCount(sql, paramMap);
				sql = generatePagingSql(sql, totalCount, pagination);
				pagination.setTotalCount(totalCount);
			}
			return getNamedParameterJdbcTemplate().query(sql, paramMap, rowMapper);
		} catch (DataAccessException e) {
			throw new DBException(e);
		}
	}

	/**
	 * <p>Query given SQL to create a prepared statement from SQL and a list of arguments to bind to the query, mapping each row to a Java object via a RowMapper</p>
	 * 
	 * @param sql - SQL query to execute
	 * @param args - arguments to bind to the query (leaving it to the PreparedStatement to guess the corresponding SQL type)
	 * @param rowMapper - object that will map one object per row 
	 * @return
	 * @throws DBException
	 * @author: 蒲艳 - puy@neusoft.com 
	 * @date: Created on Aug 25, 2008 6:38:12 PM
	 */
	protected List query(String sql, Object[] args, RowMapper rowMapper) throws DBException {
		try {
			return getJdbcTemplate().query(sql, args, rowMapper);
		} catch (DataAccessException e) {
			throw new DBException(e);
		}
	}

	/**
	 * <p>Query given SQL to create a prepared statement from SQL and a list of arguments to bind to the query, mapping each row to a Java object via a RowMapper</p>
	 * 
	 * @param sql - SQL query to execute
	 * @param args - arguments to bind to the query (leaving it to the PreparedStatement to guess the corresponding SQL type)
	 * @param rowMapper - object that will map one object per row 
	 * @param pagination - object that will query result in a specified row range
	 * @return
	 * @throws DBException
	 * @author: 蒲艳 - puy@neusoft.com 
	 * @date: Created on Aug 25, 2008 6:40:17 PM
	 */
	protected List query(String sql, Object[] args, RowMapper rowMapper, Pagination pagination) throws DBException {
		if(pagination == null)
			pagination = new Pagination();
		try {
			if (checkPagination(pagination)) {
				// sql = generatePagingSql(sql, pagination);
				int totalCount = getCount(sql, args);
				sql = generatePagingSql(sql, totalCount, pagination);
				pagination.setTotalCount(totalCount);
			}
			return getJdbcTemplate().query(sql, args, rowMapper);
		} catch (DataAccessException e) {
			throw new DBException(e);
		}
	}

	/**
	 * <p>校验pagination是否合法</p>
	 * 
	 * @param p
	 * @return
	 * @author: 段鹏 - duanp@neusoft.com 
	 * @date: Created on Sep 21, 2008 12:55:48 PM
	 */
	private boolean checkPagination(Pagination p) {
		return p != null && p.getStart() >= 0 && p.getLimit() >= 0;
	}

	/**
	 * <p>Execute a query given static SQL, mapping each row to a Java object via a RowMapper</p>
	 * 
	 * @param sql - SQL query to execute
	 * @param rowMapper - object that will map one object per row 
	 * @return
	 * @throws DBException
	 * @author: 蒲艳 - puy@neusoft.com 
	 * @date: Created on Aug 25, 2008 3:28:43 PM
	 */
	protected List query(String sql, RowMapper rowMapper) throws DBException {
		try {
			return getJdbcTemplate().query(sql, rowMapper);
		} catch (DataAccessException e) {
			throw new DBException(e);
		}
	}

	/**
	 * <p>Execute a query given static SQL, mapping each row to a Java object via a RowMapper</p>
	 * 
	 * @param sql - SQL query to execute
	 * @param rowMapper - object that will map one object per row 
	 * @param pagination - object that will query result in a specified row range
	 * @return
	 * @throws DBException
	 * @author: 蒲艳 - puy@neusoft.com 
	 * @date: Created on Aug 25, 2008 6:37:31 PM
	 */
	protected List query(String sql, RowMapper rowMapper, Pagination pagination) throws DBException {
		if(pagination == null)
			pagination = new Pagination();
		try {
			if (checkPagination(pagination)) { // sql = generatePagingSql(sql, pagination);
				int totalCount = getCount(sql);
				sql = generatePagingSql(sql, totalCount, pagination);
				pagination.setTotalCount(totalCount);
			}
			return getJdbcTemplate().query(sql, rowMapper);
		} catch (DataAccessException e) {
			throw new DBException(e);
		}

	}

	/**
	 * <p>Query given SQL to create a prepared statement from SQL and a list of arguments to bind to the query, mapping each row to a Java object via a RowMapper.  </p>
	 * 
	 * 
	 * @param sql - SQL query to execute
	 * @param paramSource - container of arguments to bind to the query
	 * @param rowMapper - object that will map one object per row 
	 * @return - the result List, containing mapped objects 
	 * @throws DBException
	 * @author: 蒲艳 - puy@neusoft.com 
	 * @date: Created on Aug 25, 2008 3:32:32 PM
	 * 
	 */
	@Deprecated
	protected List query(String sql, SqlParameterSource paramSource, RowMapper rowMapper) throws DBException {
		try {
			return getNamedParameterJdbcTemplate().query(sql, paramSource, rowMapper);
		} catch (DataAccessException e) {
			throw new DBException(e);
		}
	}

	/**
	 * <p>Query given SQL to create a prepared statement from SQL and a list of arguments to bind to the query, mapping each row to a Java object via a RowMapper.</p>
	 * 
	 * @param sql - SQL query to execute
	 * @param paramSource - container of arguments to bind to the query
	 * @param rowMapper - object that will map one object per row 
	 * @param pagination - object that will query result in a specified row range
	 * @return
	 * @throws DBException
	 * @author: 蒲艳 - puy@neusoft.com 
	 * @date: Created on Aug 25, 2008 3:42:58 PM
	 * 
	 */
	@Deprecated
	protected List query(String sql, SqlParameterSource paramSource, RowMapper rowMapper, Pagination pagination)
			throws DBException {
		if(pagination == null)
			pagination = new Pagination();
		try {
			if (checkPagination(pagination)) {
				int totalCount = getCount(sql, paramSource);
				sql = generatePagingSql(sql, totalCount, pagination);
				pagination.setTotalCount(totalCount);
			}

			return getNamedParameterJdbcTemplate().query(sql, paramSource, rowMapper);
		} catch (DataAccessException e) {
			throw new DBException(e);
		}
	}

	/**
	 * <p>Execute a query that results in an int value, given static SQL. </p>
	 * 
	 * @param sql - SQL query to execute
	 * @return
	 * @throws DBException
	 * @author: 蒲艳 - puy@neusoft.com 
	 * @date: Created on Aug 25, 2008 6:48:57 PM
	 */
	protected int queryForInt(String sql) throws DBException {
		try {
			return getJdbcTemplate().queryForInt(sql);
		} catch (DataAccessException e) {
			throw new DBException(e);
		}
	}

	/**
	 * <p>Query given SQL to create a prepared statement from SQL and a list of arguments to bind to the query, resulting in an int value. </p>
	 * 
	 * @param sql - SQL query to execute
	 * @param paramMap - map of parameters to bind to the query (leaving it to the PreparedStatement to guess the corresponding SQL type) 
	 * @return
	 * @throws DBException
	 * @author: 蒲艳 - puy@neusoft.com 
	 * @date: Created on Aug 25, 2008 4:09:30 PM
	 */
	@Deprecated
	protected int queryForInt(String sql, Map paramMap) throws DBException {
		try {
			return getNamedParameterJdbcTemplate().queryForInt(sql, paramMap);
		} catch (DataAccessException e) {
			throw new DBException(e);
		}
	}

	/**
	 * <p>Query given SQL to create a prepared statement from SQL and a list of arguments to bind to the query, resulting in an int value. </p>
	 * 
	 * @param sql - SQL query to execute
	 * @param args  - arguments to bind to the query (leaving it to the PreparedStatement to guess the corresponding SQL type) 
	 * @return
	 * @throws DBException
	 * @author: 蒲艳 - puy@neusoft.com 
	 * @date: Created on Aug 25, 2008 6:49:49 PM
	 */
	protected int queryForInt(String sql, Object[] args) throws DBException {
		try {
			return getJdbcTemplate().queryForInt(sql, args);
		} catch (DataAccessException e) {
			throw new DBException(e);
		}
	}

	/**
	 * <p>Query given SQL to create a prepared statement from SQL and a list of arguments to bind to the query, resulting in an int value. </p>
	 * 
	 * @param sql - SQL query to execute
	 * @param paramSource - container of arguments to bind to the query 
	 * @return
	 * @throws DBException
	 * @author: 蒲艳 - puy@neusoft.com 
	 * @date: Created on Aug 25, 2008 3:57:40 PM
	 * 
	 */
	@Deprecated
	protected int queryForInt(String sql, SqlParameterSource paramSource) throws DBException {
		try {
			return getNamedParameterJdbcTemplate().queryForInt(sql, paramSource);
		} catch (DataAccessException e) {
			throw new DBException(e);
		}
	}

	/**
	 * <p>Execute a query for a result list, given static SQL. </p>
	 * 
	 * @param sql - SQL query to execute
	 * @return
	 * @throws DBException
	 * @author: 蒲艳 - puy@neusoft.com 
	 * @date: Created on Aug 25, 2008 6:50:41 PM
	 */
	protected List<Map<String, Object>> queryForList(String sql) throws DBException {
		try {
			return getJdbcTemplate().queryForList(sql);
		} catch (DataAccessException e) {
			throw new DBException(e);
		}
	}

	/**
	 * <p>Query given SQL to create a prepared statement from SQL and a list of arguments to bind to the query, expecting a result list. </p>
	 * 
	 * @param sql - SQL query to execute
	 * @param paramMap - map of parameters to bind to the query (leaving it to the PreparedStatement to guess the corresponding SQL type) 
	 * @return
	 * @throws DBException
	 * @author: 蒲艳 - puy@neusoft.com 
	 * @date: Created on Aug 25, 2008 4:10:42 PM
	 */
	@Deprecated
	protected List<Map<String, Object>> queryForList(String sql, Map paramMap) throws DBException {
		try {
			return getNamedParameterJdbcTemplate().queryForList(sql, paramMap);
		} catch (DataAccessException e) {
			throw new DBException(e);
		}
	}

	/**
	 * <p>Query given SQL to create a prepared statement from SQL and a list of arguments to bind to the query, expecting a result list. </p>
	 * 
	 * @param sql - SQL query to execute
	 * @param args - arguments to bind to the query (leaving it to the PreparedStatement to guess the corresponding SQL type) 
	 * @return
	 * @throws DBException
	 * @author: 蒲艳 - puy@neusoft.com 
	 * @date: Created on Aug 25, 2008 6:51:26 PM
	 */
	protected List<Map<String, Object>> queryForList(String sql, Object[] args) throws DBException {
		try {
			return getJdbcTemplate().queryForList(sql, args);
		} catch (DataAccessException e) {
			throw new DBException(e);
		}
	}

	/**
	 * <p>带翻页参数的queryForList</p>
	 * 
	 * @param sql
	 * @param args
	 * @param pagination
	 * @return
	 * @throws DBException
	 * @author: 郭伟 - guowei@neusoft.com
	 * @date: Created on Feb 25, 2009 11:50:36 AM
	 */
	protected List<Map<String, Object>> queryForList(String sql, Object[] args, Pagination pagination)
			throws DBException {
		if(pagination == null)
			pagination = new Pagination();
		try {
			if (checkPagination(pagination)) {
				int totalCount = getCount(sql, args);
				sql = generatePagingSql(sql, totalCount, pagination);
				pagination.setTotalCount(totalCount);
			}
			return getJdbcTemplate().queryForList(sql, args);
		} catch (DataAccessException e) {
			throw new DBException(e);
		}
	}

	/**
	 * <p>Query given SQL to create a prepared statement from SQL and a list of arguments to bind to the query, expecting a result list. </p>
	 * 
	 * @param sql - SQL query to execute
	 * @param paramSource - container of arguments to bind to the query 
	 * @return
	 * @throws DBException
	 * @author: 蒲艳 - puy@neusoft.com 
	 * @date: Created on Aug 25, 2008 4:09:57 PM
	 * 
	 */
	@Deprecated
	protected List<Map<String, Object>> queryForList(String sql, SqlParameterSource paramSource) throws DBException {
		try {
			return getNamedParameterJdbcTemplate().queryForList(sql, paramSource);
		} catch (DataAccessException e) {
			throw new DBException(e);
		}
	}

	/**
	 * <p>Execute a query that results in a long value, given static SQL.</p>
	 * 
	 * @param sql - SQL query to execute
	 * @return
	 * @throws DBException
	 * @author: 蒲艳 - puy@neusoft.com 
	 * @date: Created on Aug 25, 2008 6:45:49 PM
	 */
	protected long queryForLong(String sql) throws DBException {
		try {
			return getJdbcTemplate().queryForLong(sql);
		} catch (DataAccessException e) {
			throw new DBException(e);
		}
	}

	/**
	 * <p>Query given SQL to create a prepared statement from SQL and a list of arguments to bind to the query, resulting in a long value.</p>
	 * 
	 * @param sql - SQL query to execute
	 * @param paramMap - map of parameters to bind to the query (leaving it to the PreparedStatement to guess the corresponding SQL type)
	 * @return
	 * @throws DBException
	 * @author: 蒲艳 - puy@neusoft.com 
	 * @date: Created on Aug 25, 2008 3:57:08 PM
	 */
	@Deprecated
	protected long queryForLong(String sql, Map paramMap) throws DBException {
		try {
			return getNamedParameterJdbcTemplate().queryForLong(sql, paramMap);
		} catch (DataAccessException e) {
			throw new DBException(e);
		}
	}

	/**
	 * <p>Query given SQL to create a prepared statement from SQL and a list of arguments to bind to the query, resulting in a long value. </p>
	 * 
	 * @param sql - SQL query to execute
	 * @param args - arguments to bind to the query (leaving it to the PreparedStatement to guess the corresponding SQL type) 
	 * @return
	 * @throws DBException
	 * @author: 蒲艳 - puy@neusoft.com 
	 * @date: Created on Aug 25, 2008 6:47:48 PM
	 */
	protected long queryForLong(String sql, Object[] args) throws DBException {
		try {
			return getJdbcTemplate().queryForLong(sql, args);
		} catch (DataAccessException e) {
			throw new DBException(e);
		}
	}

	/**
	 * <p>Query given SQL to create a prepared statement from SQL and a list of arguments to bind to the query, resulting in a long value.</p>
	 * 
	 * @param sql - SQL query to execute
	 * @param paramSource - container of arguments to bind to the query 
	 * @return
	 * @throws DBException
	 * @author: 蒲艳 - puy@neusoft.com 
	 * @date: Created on Aug 25, 2008 3:56:21 PM
	 * 
	 */
	@Deprecated
	protected long queryForLong(String sql, SqlParameterSource paramSource) throws DBException {
		try {
			return getNamedParameterJdbcTemplate().queryForLong(sql, paramSource);
		} catch (DataAccessException e) {
			throw new DBException(e);
		}
	}

	/**
	 * <p>Execute a query for a result Map, given static SQL. </p>
	 * 
	 * @param sql - SQL query to execute 
	 * @return
	 * @throws DBException
	 * @author: 蒲艳 - puy@neusoft.com 
	 * @date: Created on Aug 25, 2008 6:43:45 PM
	 */
	protected Map<String, Object> queryForMap(String sql) throws DBException {
		try {
			return getJdbcTemplate().queryForMap(sql);
		} catch (DataAccessException e) {
			throw new DBException(e);
		}
	}

	/**
	 * <p>Query given SQL to create a prepared statement from SQL and a list of arguments to bind to the query, expecting a result Map.</p>
	 * 
	 * @param sql - SQL query to execute
	 * @param paramMap - map of parameters to bind to the query (leaving it to the PreparedStatement to guess the corresponding SQL type) 
	 * @return
	 * @throws DBException
	 * @author: 蒲艳 - puy@neusoft.com 
	 * @date: Created on Aug 25, 2008 3:55:25 PM
	 */
	@Deprecated
	protected Map<String, Object> queryForMap(String sql, Map paramMap) throws DBException {
		try {
			return getNamedParameterJdbcTemplate().queryForMap(sql, paramMap);
		} catch (DataAccessException e) {
			throw new DBException(e);
		}
	}

	/**
	 * <p>Query given SQL to create a prepared statement from SQL and a list of arguments to bind to the query, expecting a result Map. </p>
	 * 
	 * @param sql - SQL query to execute
	 * @param args - arguments to bind to the query (leaving it to the PreparedStatement to guess the corresponding SQL type) 
	 * @return
	 * @throws DBException
	 * @author: 蒲艳 - puy@neusoft.com 
	 * @date: Created on Aug 25, 2008 6:44:35 PM
	 */
	protected Map<String, Object> queryForMap(String sql, Object[] args) throws DBException {
		try {
			return getJdbcTemplate().queryForMap(sql, args);
		} catch (DataAccessException e) {
			throw new DBException(e);
		}
	}

	/**
	 * <p>Query given SQL to create a prepared statement from SQL and a list of arguments to bind to the query, expecting a result Map.</p>
	 * 
	 * @param sql - SQL query to execute
	 * @param paramSource  - container of arguments to bind to the query 
	 * @return
	 * @throws DBException
	 * @author: 蒲艳 - puy@neusoft.com 
	 * @date: Created on Aug 25, 2008 3:54:56 PM
	 * 
	 */
	@Deprecated
	protected Map<String, Object> queryForMap(String sql, SqlParameterSource paramSource) throws DBException {
		try {
			return getNamedParameterJdbcTemplate().queryForMap(sql, paramSource);
		} catch (DataAccessException e) {
			throw new DBException(e);
		}
	}

	/**
	 * <p>Query given SQL to create a prepared statement from SQL and a list of arguments to bind to the query, mapping a single result row to a Java object via a RowMapper. </p>
	 * 
	 * @param sql - SQL query to execute
	 * @param paramMap - map of parameters to bind to the query (leaving it to the PreparedStatement to guess the corresponding SQL type)
	 * @param rowMapper - object that will map one object per row 
	 * @return
	 * @throws DBException
	 * @author: 蒲艳 - puy@neusoft.com 
	 * @date: Created on Aug 25, 2008 3:53:25 PM
	 */
	protected Object queryForObject(String sql, Map paramMap, RowMapper rowMapper) throws DBException {
		try {
			List list = query(sql, paramMap, rowMapper);
			if (list.size() == 0)
				return null;
			return list.get(0);
		} catch (DataAccessException e) {
			throw new DBException(e);
		}
	}

	/**
	 * <p>Query given SQL to create a prepared statement from SQL and a list of arguments to bind to the query, mapping a single result row to a Java object via a RowMapper. </p>
	 * 
	 * @param sql - SQL query to execute
	 * @param args  - arguments to bind to the query (leaving it to the PreparedStatement to guess the corresponding SQL type)
	 * @param rowMapper - object that will map one object per row 
	 * @return
	 * @throws DBException
	 * @author: 蒲艳 - puy@neusoft.com 
	 * @date: Created on Aug 25, 2008 6:41:52 PM
	 */
	protected Object queryForObject(String sql, Object[] args, RowMapper rowMapper) throws DBException {
		try {
			List list = query(sql, args, rowMapper);
			if (list.size() == 0)
				return null;
			return list.get(0);
		} catch (DataAccessException e) {
			throw new DBException(e);
		}
	}

	/**
	 * <p>Execute a query given static SQL, mapping a single result row to a Java object via a RowMapper. </p>
	 * 
	 * @param sql - SQL query to execute
	 * @param rowMapper - object that will map one object per row 
	 * @return
	 * @throws DBException
	 * @author: 蒲艳 - puy@neusoft.com 
	 * @date: Created on Aug 25, 2008 6:41:21 PM
	 */
	protected Object queryForObject(String sql, RowMapper rowMapper) throws DBException {
		try {
			List list = query(sql, rowMapper);
			if (list.size() == 0)
				return null;
			return list.get(0);
		} catch (DataAccessException e) {
			throw new DBException(e);
		}
	}

	/**
	 * <p>Query given SQL to create a prepared statement from SQL and a list of arguments to bind to the query, mapping a single result row to a Java object via a RowMapper. </p>
	 * 
	 * @param sql - SQL query to execute
	 * @param paramSource - container of arguments to bind to the query
	 * @param rowMapper  - object that will map one object per row 
	 * @return
	 * @throws DBException
	 * @author: 蒲艳 - puy@neusoft.com 
	 * @date: Created on Aug 25, 2008 3:52:48 PM
	 *
	 */
	@Deprecated
	protected Object queryForObject(String sql, SqlParameterSource paramSource, RowMapper rowMapper) throws DBException {
		try {
			List list = query(sql, paramSource, rowMapper);
			if (list.size() == 0)
				return null;
			return list.get(0);
		} catch (DataAccessException e) {
			throw new DBException(e);
		}
	}

	/**
	 * <p>Execute a query for a SqlRowSet, given static SQL. </p>
	 * 
	 * @param sql - SQL query to execute 
	 * @return
	 * @throws DBException
	 * @author: 蒲艳 - puy@neusoft.com 
	 * @date: Created on Aug 25, 2008 8:00:14 PM
	 */
	protected SqlRowSet queryForRowSet(String sql) throws DBException {
		try {
			return getJdbcTemplate().queryForRowSet(sql);
		} catch (DataAccessException e) {
			throw new DBException(e);
		}
	}

	/**
	 * <p>Query given SQL to create a prepared statement from SQL and a list of arguments to bind to the query, expecting a SqlRowSet. </p>
	 * 
	 * @param sql  - SQL query to execute
	 * @param paramMap - map of parameters to bind to the query (leaving it to the PreparedStatement to guess the corresponding SQL type)
	 * @return
	 * @throws DBException
	 * @author: 蒲艳 - puy@neusoft.com 
	 * @date: Created on Aug 25, 2008 4:12:12 PM
	 */
	@Deprecated
	protected SqlRowSet queryForRowSet(String sql, Map paramMap) throws DBException {
		try {
			return getNamedParameterJdbcTemplate().queryForRowSet(sql, paramMap);
		} catch (DataAccessException e) {
			throw new DBException(e);
		}
	}

	/**
	 * <p>Query given SQL to create a prepared statement from SQL and a list of arguments to bind to the query, expecting a SqlRowSet</p>
	 * 
	 * @param sql - SQL query to execute 
	 * @param args - arguments to bind to the query (leaving it to the PreparedStatement to guess the corresponding SQL type) 
	 * @return
	 * @throws DBException
	 * @author: 蒲艳 - puy@neusoft.com 
	 * @date: Created on Aug 25, 2008 8:00:47 PM
	 */
	protected SqlRowSet queryForRowSet(String sql, Object[] args) throws DBException {
		try {
			return getJdbcTemplate().queryForRowSet(sql, args);
		} catch (DataAccessException e) {
			throw new DBException(e);
		}
	}

	/**
	 * <p>Query given SQL to create a prepared statement from SQL and a list of arguments to bind to the query, expecting a SqlRowSet. </p>
	 * 
	 * @param sql - SQL query to execute
	 * @param paramSource  - container of arguments to bind to the query 
	 * @return
	 * @throws DBException
	 * @author: 蒲艳 - puy@neusoft.com 
	 * @date: Created on Aug 25, 2008 4:11:29 PM
	 * 
	 */
	@Deprecated
	protected SqlRowSet queryForRowSet(String sql, SqlParameterSource paramSource) throws DBException {
		try {
			return getNamedParameterJdbcTemplate().queryForRowSet(sql, paramSource);
		} catch (DataAccessException e) {
			throw new DBException(e);
		}
	}

	/**
	 * <p>Issue a single SQL update.</p>
	 * 
	 * @param sql - SQL query to execute
	 * @return
	 * @throws DBException
	 * @author: 蒲艳 - puy@neusoft.com 
	 * @date: Created on Aug 25, 2008 8:01:26 PM
	 */
	protected int update(final String sql) throws DBException {
		try {
			return getJdbcTemplate().update(sql);
		} catch (DataAccessException e) {
			throw new DBException(e);
		}
	}

	/**
	 * <p>Issue an update via a prepared statement, binding the given arguments.</p>
	 * 
	 * @param sql - SQL containing named parameters
	 * @param paramMap - map of parameters to bind to the query (leaving it to the PreparedStatement to guess the corresponding SQL type) 
	 * @return
	 * @throws DBException
	 * @author: 蒲艳 - puy@neusoft.com 
	 * @date: Created on Aug 25, 2008 4:14:00 PM
	 */
	@Deprecated
	protected int update(String sql, Map paramMap) throws DBException {
		try {
			return getNamedParameterJdbcTemplate().update(sql, paramMap);
		} catch (DataAccessException e) {
			throw new DBException(e);
		}
	}

	/**
	 * <p>Issue an update via a prepared statement, binding the given arguments. </p>
	 * 
	 * @param sql - SQL query to execute
	 * @param args - arguments to bind to the query (leaving it to the PreparedStatement to guess the corresponding SQL type) 
	 * @return
	 * @throws DBException
	 * @author: 蒲艳 - puy@neusoft.com 
	 * @date: Created on Aug 25, 2008 8:01:55 PM
	 */
	protected int update(String sql, Object[] args) throws DBException {
		try {
			return getJdbcTemplate().update(sql, args);
		} catch (DataAccessException e) {
			throw new DBException(e);
		}
	}

	/**
	 * <p>Issue an update via a prepared statement, binding the given arguments. </p>
	 * 
	 * @param sql - SQL containing named parameters
	 * @param paramMap - container of arguments and SQL types to bind to the query 
	 * @return
	 * @throws DBException
	 * @author: 蒲艳 - puy@neusoft.com 
	 * @date: Created on Aug 25, 2008 4:13:06 PM
	 * 
	 */
	@Deprecated
	protected int update(String sql, SqlParameterSource paramSource) throws DBException {
		try {
			return getNamedParameterJdbcTemplate().update(sql, paramSource);
		} catch (DataAccessException e) {
			throw new DBException(e);
		}
	}

	/**
	 * <p>当前数据出现不同步的时候，需要查询到最后一页</p>
	 * 
	 * @param realCnt
	 * @param p
	 * @return
	 * @author: 段鹏 - duanp@neusoft.com 
	 * @date: Created on 2009-4-2 上午11:04:14
	 */
	private Pagination getLastPage(int realCnt, Pagination p) {
		if (p.getStart() < realCnt || (p.getStart() == 0 && realCnt == 0)) // 当数据为0时，不作处理
			return p;
		if (p.getLimit() > 0) {
			if (realCnt > 0 && realCnt % p.getLimit() == 0)
				p.setStart((realCnt - 1) / p.getLimit() * p.getLimit() + 1);
			else
				p.setStart(realCnt / p.getLimit() * p.getLimit() + 1);
		}
		return p;
	}

	private int getCount(String sql, Object[] args) {
		return this.queryForInt(generateCntSql(sql), args);
	}

	private int getCount(String sql, Map args) {
		return this.queryForInt(generateCntSql(sql), args);
	}

	private int getCount(String sql) {
		return this.queryForInt(generateCntSql(sql));
	}

	private int getCount(String sql, SqlParameterSource paramSource) {
		return this.queryForInt(generateCntSql(sql), paramSource);
	}

	private String generateCntSql(String sql) {
		return "SELECT COUNT(1) FROM ( " + sql + " )";
	}
	
	protected String convertSQLParameter(String srcStr) {
		srcStr = srcStr == null ? "" : srcStr.toString();
		return srcStr.replaceAll("'", "''").replaceAll("\\\\", "\\\\\\\\").replaceAll("_", "\\\\_").replaceAll("%",
				"\\\\%");
	}
}
