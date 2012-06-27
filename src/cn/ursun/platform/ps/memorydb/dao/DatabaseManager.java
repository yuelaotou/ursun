/**
 * 文件名：MemoryDBManager.java
 * 
 * 创建人：兰硕 - lans@neusoft.com
 * 
 * 创建时间：Sep 20, 2008 1:08:00 PM
 * 
 * 版权所有：东软集团股份有限公司
 */
package cn.ursun.platform.ps.memorydb.dao;

import java.util.List;

import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.jdbc.support.rowset.SqlRowSetMetaData;

/**
 * <p>[描述信息：说明类的基本功能]</p>
 *
 * @author 兰硕 - lans@neusoft.com
 * @version 1.0 Created on Sep 20, 2008 1:08:00 PM
 */
public interface DatabaseManager {

	public boolean isInited();

	public void init();

	public void forceInit();

	public SqlRowSetMetaData queryTableMetadata(String tableName);

	public SqlRowSet queryBySql(String sql);

	public SqlRowSet queryBySql(String sql, Object[] params);

	public int executeSql(String sql);

	public void executeBatchSql(String sql, List<Object[]> params);

	public void executeBatchSql(List<String> sql, List<Object[]> params);
}
