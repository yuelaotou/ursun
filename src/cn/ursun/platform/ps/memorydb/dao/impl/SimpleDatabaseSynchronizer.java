/**
 * 文件名：SimpleDatabaseSynchronizer.java
 * 
 * 创建人：兰硕 - lans@neusoft.com
 * 
 * 创建时间：Sep 20, 2008 7:52:51 PM
 * 
 * 版权所有：东软集团股份有限公司
 */
package cn.ursun.platform.ps.memorydb.dao.impl;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.jdbc.support.rowset.SqlRowSetMetaData;

import cn.ursun.platform.ps.memorydb.dao.AbstractDatabaseSynchronizer;

/**
 * <p>[描述信息：说明类的基本功能]</p>
 *
 * @author 兰硕 - lans@neusoft.com
 * @version 1.0 Created on Sep 20, 2008 7:52:51 PM
 */
public class SimpleDatabaseSynchronizer extends AbstractDatabaseSynchronizer {

	/**
	 * 异常日志KEY
	 */
	private static final String WEE_EXCEPTION_LOGGER = "WeeException";

	public void synchronize(String tableName) {
		try {
			SqlRowSetMetaData md = sourceDatabaseManager.queryTableMetadata(tableName);
			String[] columnNames = md.getColumnNames();
			StringBuffer selectSql = makeDataExtractSql(tableName, columnNames);
			SqlRowSet rs = sourceDatabaseManager.queryBySql(selectSql.toString());
			List<Object[]> params = new ArrayList<Object[]>();
			while (rs.next()) {
				List<Object> paramList = new ArrayList<Object>();
				for (String columnName : columnNames) {
					Object obj = rs.getObject(columnName);
					paramList.add(obj);
				}
				Object[] param = paramList.toArray(new Object[paramList.size()]);
				params.add(param);
			}
			StringBuffer insertSql = makeInsertSql(tableName, columnNames, md.getColumnCount());
			StringBuffer deleteSql = makeDeleteSql(tableName);
			// 删除目标数据库中指定表结构中的所有数据
			targetDatabaseManager.executeSql(deleteSql.toString());
			// 将源数据库表中的数据插入目标数据库中
			targetDatabaseManager.executeBatchSql(insertSql.toString(), params);
			logger.info("complete sychronize : " + tableName);
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			Logger.getLogger(WEE_EXCEPTION_LOGGER).debug(sw.getBuffer().toString());
		}
	}

	protected StringBuffer makeDataExtractSql(String tableName, String[] columnNames) {
		StringBuffer sb = new StringBuffer();
		StringBuffer columnNamesBuffer = makeColumnNamesBuffer(columnNames);
		sb.append("SELECT ").append(columnNamesBuffer).append(" FROM ").append(tableName);
		return sb;
	}

	protected StringBuffer makeInsertSql(String tableName, String[] columnNames, int allColumnCount) {
		StringBuffer sb = new StringBuffer();
		StringBuffer columnNamesBuffer = makeColumnNamesBuffer(columnNames);
		StringBuffer parameterBuffer = makeParameterBuffer(columnNames == null ? allColumnCount : columnNames.length);
		sb.append("INSERT INTO ").append(tableName).append(" (").append(columnNamesBuffer).append(") VALUES (").append(
				parameterBuffer).append(")");
		return sb;
	}

	protected StringBuffer makeDeleteSql(String tableName) {
		StringBuffer sb = new StringBuffer();
		sb.append("DELETE FROM ").append(tableName).append(" WHERE 1 = 1");
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

	private StringBuffer makeParameterBuffer(int parameterCount) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < parameterCount; i++)
			sb.append("?,");
		sb.deleteCharAt(sb.length() - 1);
		return sb;
	}
}
