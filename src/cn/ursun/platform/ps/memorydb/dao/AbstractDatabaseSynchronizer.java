/**
 * 文件名：MemoryDBSynchronizor.java
 * 
 * 创建人：兰硕 - lans@neusoft.com
 * 
 * 创建时间：Sep 20, 2008 6:24:01 PM
 * 
 * 版权所有：东软集团股份有限公司
 */
package cn.ursun.platform.ps.memorydb.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>[描述信息：说明类的基本功能]</p>
 *
 * @author 兰硕 - lans@neusoft.com
 * @version 1.0 Created on Sep 20, 2008 6:24:01 PM
 */
public abstract class AbstractDatabaseSynchronizer implements DatabaseSynchronizer {

	protected static final Log logger = LogFactory.getLog(AbstractDatabaseSynchronizer.class);

	protected DatabaseManager sourceDatabaseManager = null;

	protected DatabaseManager targetDatabaseManager = null;

	private List<String> synchronizingTables = null;

	private String synchronizerType = null;

	public void setSynchronizerType(String name) {
		this.synchronizerType = name;
	}

	public String getSynchronizerType() {
		return synchronizerType;
	}

	public void setSourceDatabaseManager(DatabaseManager sourceDatabaseManager) {
		this.sourceDatabaseManager = sourceDatabaseManager;
	}

	public void setTargetDatabaseManager(DatabaseManager targetDatabaseManager) {
		this.targetDatabaseManager = targetDatabaseManager;
	}

	public void setSynchronizingTables(List<String> synchronizingTables) {
		this.synchronizingTables = synchronizingTables;
	}

	public final List<String> fetchManagedTables() {
		return synchronizingTables;
	}

	public void init() {
		sourceDatabaseManager.init();
		targetDatabaseManager.init();
	}
}
