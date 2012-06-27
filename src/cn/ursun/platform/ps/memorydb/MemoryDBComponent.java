/**
 * 文件名：HSqlDBComponent.java
 * 
 * 创建人：兰硕 - lans@neusoft.com
 * 
 * 创建时间：Sep 20, 2008 1:00:02 PM
 * 
 * 版权所有：东软集团股份有限公司
 */
package cn.ursun.platform.ps.memorydb;

import java.util.List;

import cn.ursun.platform.core.component.AbstractComponent;
import cn.ursun.platform.core.event.WeeEventPublisher;
import cn.ursun.platform.ps.memorydb.dao.DatabaseSynchronizer;

/**
 * <p>[描述信息：说明类的基本功能]</p>
 *
 * @author 兰硕 - lans@neusoft.com
 * @version 1.0 Created on Sep 20, 2008 1:00:02 PM
 */
public class MemoryDBComponent extends AbstractComponent {

	private long delayTime = 0;

	private DatabaseSynchronizer databaseSynchronizer = null;

	public long getDelayTime() {
		return delayTime;
	}

	public void setDelayTime(long delayTime) {
		this.delayTime = delayTime;
	}

	public void destory() {
	}

	public void execute() {
		logger.info("synchronizing MEMORY_DB start ...");
		// 数据同步器初始化
		databaseSynchronizer.init();
		// 获取需要同步的数据库表名称列表
		List<String> tableNameList = databaseSynchronizer.fetchManagedTables();
		// 对每个表进行数据同步
		for (String tableName : tableNameList)
			databaseSynchronizer.synchronize(tableName);
		// 发布初始化事件更新完毕
		WeeEventPublisher.getInstance().fireAyncEvent("MEMEORY_DB_INITIALIZED");
		
		logger.info("synchronizing MEMORY_DB success ...");
	}

	public void init() {
	}

	/**
	 *
	 * @param databaseSynchronizer
	 */
	public void setDatabaseSynchronizer(DatabaseSynchronizer databaseSynchronizer) {
		this.databaseSynchronizer = databaseSynchronizer;
	}
}
