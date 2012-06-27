/**
 * 文件名：MemoryDBCacheSynchronizer.java
 * 
 * 创建人：兰硕 - lans@neusoft.com
 * 
 * 创建时间：Oct 16, 2008 10:58:24 AM
 * 
 * 版权所有：东软集团股份有限公司
 */
package cn.ursun.platform.ps.sync.synchronizer.impl;

import cn.ursun.platform.ps.memorydb.dao.DatabaseSynchronizer;
import cn.ursun.platform.ps.sync.synchronizer.Synchronizer;

/**
 * <p>内存数据库缓存同步器(增量同步)。</p>
 *
 * @author 兰硕 - lans@neusoft.com
 * @version 1.0 Created on Oct 16, 2008 10:58:24 AM
 */
public class MemoryDBCacheIncrementSynchronizer implements Synchronizer {

	protected DatabaseSynchronizer databaseSynchronizer = null;

	public void setDatabaseSynchronizer(DatabaseSynchronizer databaseSynchronizer) {
		this.databaseSynchronizer = databaseSynchronizer;
	}

	public void process() {
		databaseSynchronizer.init();
		databaseSynchronizer.synchronize(null);
	}

}
