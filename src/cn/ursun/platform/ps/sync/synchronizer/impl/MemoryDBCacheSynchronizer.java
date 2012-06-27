/**
 * 文件名：MemoryDBCacheRefreshSynchronizer.java
 * 
 * 创建人：段鹏 - duanp@neusoft.com
 * 
 * 创建时间：Aug 7, 2009 11:35:57 AM
 * 
 * 版权所有：东软集团股份有限公司
 */
package cn.ursun.platform.ps.sync.synchronizer.impl;

import java.util.List;

import cn.ursun.platform.ps.memorydb.dao.DatabaseSynchronizer;
import cn.ursun.platform.ps.sync.synchronizer.Synchronizer;

/**
 * <p>内存数据库缓存刷新同步器(全量同步)。</p>
 *
 * @author 段鹏- duanp@neusoft.com
 * @version 1.0 Created on Aug 7, 2009 11:35:57 AM
 */
public class MemoryDBCacheSynchronizer implements Synchronizer {

	protected DatabaseSynchronizer databaseSynchronizer = null;

	public void setDatabaseSynchronizer(DatabaseSynchronizer databaseSynchronizer) {
		this.databaseSynchronizer = databaseSynchronizer;
	}

	public void process() {
		List<String> tableNameList = databaseSynchronizer.fetchManagedTables();
		// 如果尚未初始化同步数据库（内存数据库表结构），则初始化target和source
		databaseSynchronizer.init();
		for (String tableName : tableNameList) {
			// 同步相应的表
			databaseSynchronizer.synchronize(tableName);
		}
	}

}
