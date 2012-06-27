/**
 * 文件名：AbstractDatabaseManager.java
 * 
 * 创建人：兰硕 - lans@neusoft.com
 * 
 * 创建时间：Sep 20, 2008 6:58:01 PM
 * 
 * 版权所有：东软集团股份有限公司
 */
package cn.ursun.platform.ps.memorydb.dao;

import java.util.List;

import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.jdbc.support.rowset.SqlRowSetMetaData;

import cn.ursun.platform.core.jdbc.WeeJdbcDAO;

/**
 * <p>[描述信息：说明类的基本功能]</p>
 *
 * @author 兰硕 - lans@neusoft.com
 * @version 1.0 Created on Sep 20, 2008 6:58:01 PM
 */
public abstract class AbstractDatabaseManager extends WeeJdbcDAO implements DatabaseManager {

	private boolean inited = false;

	private void setInited(boolean inited) {
		this.inited = inited;
	}

	public final boolean isInited() {
		return inited;
	}

	public final void init() {
		if (!isInited()) {
			doInit();
			setInited(true);
		}
	}

	public final void forceInit() {
		log.info("DatabaseManager execute force initialization ...");
		doInit();
	}

	protected abstract void doInit();

	public SqlRowSetMetaData queryTableMetadata(String tableName) {
		SqlRowSet rowSet = queryForRowSet("SELECT * FROM " + tableName + " WHERE 1 <> 1");
		return rowSet.getMetaData();
	}

	public void executeBatchSql(String sql, final List<Object[]> params) {
		batchUpdate(sql, params);
	}

	public void executeBatchSql(List<String> sql, final List<Object[]> params) {
		batchUpdate(sql, params);
	}

	public int executeSql(String sql) {
		return update(sql);
	}

	public SqlRowSet queryBySql(String sql) {
		return queryForRowSet(sql);
	}

	public SqlRowSet queryBySql(String sql, Object[] params) {
		return queryForRowSet(sql, params);
	}
}
