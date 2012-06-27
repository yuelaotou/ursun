/**
 * 文件名：IncrementDatabaseSynchronizer.java
 * 
 * 创建人：段鹏 - duanp@neusoft.com
 * 
 * 创建时间：2009-6-26 下午04:45:06
 * 
 * 版权所有：东软集团股份有限公司
 */
package cn.ursun.platform.ps.memorydb.dao.impl;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.jdbc.support.rowset.SqlRowSetMetaData;

import cn.ursun.platform.core.domain.Server;
import cn.ursun.platform.ps.sync.dao.MDBSyncDAO;
import cn.ursun.platform.core.util.ConfigUtils;
import cn.ursun.platform.ps.memorydb.dao.AbstractDatabaseSynchronizer;
import cn.ursun.platform.ps.memorydb.dto.UpdateRecord;
import cn.ursun.platform.ps.memorydb.dto.UpdateRecord.UpdateType;

/**
 * <p> Title:增量数据库同步实现类</p>
 * <p> Description: [描述]</p>
 * <p> Created on 2009-9-16</p>
 * <p> Copyright: Copyright (c) 2008</p>
 * <p> Company: 东软软件股份有限公司</p>
 * @author 宋成山 songchengshan@neusoft.com
 * @version 1.0
 */
public class IncrementDatabaseSynchronizer extends AbstractDatabaseSynchronizer {

	private MDBSyncDAO synchronizerDAO = null;

	/**
	 * 异常日志KEY
	 */
	private static final String WEE_EXCEPTION_LOGGER = "WeeException";

	private class UpdateSqlWrapper {

		String sql = null;

		Object[] params = null;

		public UpdateSqlWrapper(String sql, Object[] params) {
			super();
			this.sql = sql;
			this.params = params;
		}

		public Object[] getParams() {
			return params;
		}

		public void setParams(Object[] params) {
			this.params = params;
		}

		public String getSql() {
			return sql;
		}

		public void setSql(String sql) {
			this.sql = sql;
		}

	}

	/**
	 * <p>增量同步数据库</p>
	 * 
	 * @see cn.ursun.platform.core.memorydb.dao.DatabaseSynchronizer#synchronize(java.lang.String)
	 * @author: 段鹏 - duanp@neusoft.com 
	 * @date: Created on 2009-6-26 下午04:45:06
	 */
	public void synchronize(String tableName) {
		// 1. 根据当前服务器的时间戳来查询出需要更新的增量数据更新表中的记录
		// 2. 构建更新SQL语句
		// 3. 批量执行更新语句
		List<UpdateRecord> list = synchronizerDAO.getUpdateSqlList(getServer(), MDBSyncDAO.DATABASE_SYNC_FLAG);
		Iterator<UpdateRecord> it = list.iterator();
		List<String> sqlList = new ArrayList<String>();
		List<Object[]> paramsList = new ArrayList<Object[]>();
		while (it.hasNext()) {
			UpdateRecord record = it.next();
			UpdateSqlWrapper wrapper = null;
			if (UpdateType.INSERT.equals(record.getType())) {
				wrapper = constructInsertTableData(record);
			} else if (UpdateType.UPDATE.equals(record.getType())) {
				wrapper = constructUpdateTableData(record);
			} else if (UpdateType.DELETE.equals(record.getType())) {
				wrapper = constructDeleteSql(record);
			} else {
				wrapper = null;
			}
			if (wrapper != null) {
				sqlList.add(wrapper.getSql());
				paramsList.add(wrapper.getParams());
			}
		}
		// 将源数据库表中的数据插入目标数据库中
		targetDatabaseManager.executeBatchSql(sqlList, paramsList);
	}

	private UpdateSqlWrapper constructInsertTableData(UpdateRecord record) {
		String tableName = record.getTableName();

		SqlRowSetMetaData md = sourceDatabaseManager.queryTableMetadata(record.getTableName());
		String[] columnNames = md.getColumnNames();
		StringBuffer selectSql = makeDataExtractSql(tableName, columnNames, record);
		SqlRowSet rs = sourceDatabaseManager.queryBySql(selectSql.toString(), new Object[] { record.getPkValue() });
		List params = new ArrayList();
		if (rs.next()) {
			for (String columnName : columnNames) {
				Object obj = rs.getObject(columnName);
				params.add(obj);
			}
			// params.add(record.getPkValue());
		} else {
			warn("Syncronize data maybe has deleted from physical database.");
			return null;
		}
		StringBuffer insertSql = makeInsertSql(tableName, columnNames, md.getColumnCount());
		return new UpdateSqlWrapper(insertSql.toString(), params.toArray());
	}

	private UpdateSqlWrapper constructUpdateTableData(UpdateRecord record) {
		String tableName = record.getTableName();
		SqlRowSetMetaData md = sourceDatabaseManager.queryTableMetadata(tableName);
		String[] columnNames = md.getColumnNames();
		StringBuffer selectSql = makeDataExtractSql(tableName, columnNames, record);
		SqlRowSet rs = sourceDatabaseManager.queryBySql(selectSql.toString(), new Object[] { record.getPkValue() });
		List params = new ArrayList();
		if (rs.next()) {
			for (String columnName : columnNames) {
				Object obj = rs.getObject(columnName);
				params.add(obj);
			}
			params.add(record.getPkValue());
		} else {
			warn("Syncronize data maybe has deleted from physical database.");
			return null;
		}
		StringBuffer updateSql = makeUpdateSql(tableName, columnNames, record.getPkName(), record.getPkValue());
		return new UpdateSqlWrapper(updateSql.toString(), params.toArray());
	}

	private UpdateSqlWrapper constructDeleteSql(UpdateRecord record) {
		StringBuffer sb = new StringBuffer();
		sb.append("DELETE FROM ").append(record.getTableName()).append(" WHERE ").append(record.getPkName()).append(
				" = ? ");
		return new UpdateSqlWrapper(sb.toString(), new Object[] { record.getPkValue() });
	}

	/**
	 * <p>构造查询数据SQL</p>
	 * 
	 * @param tableName
	 * @param columnNames
	 * @param pkName
	 * @param pkValue
	 * @return
	 * @author: 段鹏 - duanp@neusoft.com 
	 * @date: Created on 2009-6-29 下午05:05:21
	 */
	private StringBuffer makeDataExtractSql(String tableName, String[] columnNames, UpdateRecord record) {
		StringBuffer sb = new StringBuffer();
		StringBuffer columnNamesBuffer = makeColumnNamesBuffer(columnNames);
		sb.append("SELECT ").append(columnNamesBuffer).append(" FROM ").append(tableName);
		sb.append(" WHERE ").append(record.getPkName()).append(" = ? ");
		return sb;
	}

	private StringBuffer makeInsertSql(String tableName, String[] columnNames, int allColumnCount) {
		StringBuffer sb = new StringBuffer();
		StringBuffer columnNamesBuffer = makeColumnNamesBuffer(columnNames);
		StringBuffer parameterBuffer = makeParameterBuffer(columnNames == null ? allColumnCount : columnNames.length);
		sb.append("INSERT INTO ").append(tableName).append(" (").append(columnNamesBuffer).append(") VALUES (").append(
				parameterBuffer).append(")");
		return sb;
	}

	private StringBuffer makeUpdateSql(String tableName, String[] columnNames, String pkName, String pkValue) {
		StringBuffer sb = new StringBuffer();
		StringBuffer columnNamesBuffer = makeUpdateColumnsBuffer(columnNames);
		sb.append("UPDATE  ").append(tableName).append(" SET ").append(columnNamesBuffer).append(" WHERE ").append(
				pkName).append(" = ?");
		return sb;
	}

	private StringBuffer makeColumnNamesBuffer(String[] columnNames) {
		StringBuffer sb = new StringBuffer();
		if (columnNames.length == 0)
			return sb.append("*");
		for (String name : columnNames)
			sb.append(name + ",");
		sb.deleteCharAt(sb.length() - 1);
		return sb;
	}

	private StringBuffer makeUpdateColumnsBuffer(String[] columnNames) {
		StringBuffer sb = new StringBuffer();
		for (String name : columnNames)
			sb.append(name + " = ? ,");
		sb.deleteCharAt(sb.length() - 1);
		return sb;
	}

	private StringBuffer makeParameterBuffer(int parameterCount) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < parameterCount; i++)
			sb.append("?,");
		sb.deleteCharAt(sb.length() - 1);
		return sb;
	}

	/**
	 *
	 * @param synchronizerDAO
	 */
	public void setSynchronizerDAO(MDBSyncDAO synchronizerDAO) {
		this.synchronizerDAO = synchronizerDAO;
	}

	/**
	 *
	 * @return 
	 */
	private Server getServer() {
		Server server = new Server();
		server.setIP(ConfigUtils.getInstance().getMessage(Server.SERVER_IP));
		server.setPort(ConfigUtils.getInstance().getMessage(Server.SERVER_PORT));
		server.setServerId(ConfigUtils.getInstance().getMessage(Server.SERVER_ID));
		server.setServerName(ConfigUtils.getInstance().getMessage(Server.SERVER_NAME));
		return server;
	}

	private void warn(String message) {
		Logger.getLogger(WEE_EXCEPTION_LOGGER).warn(message);
	}

	private void debug(Exception e) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		Logger.getLogger(WEE_EXCEPTION_LOGGER).debug(sw.getBuffer().toString());
	}

}
