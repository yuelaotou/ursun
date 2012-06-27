/**
 * 文件名：WeeDBSourceDataRefreshListener.java
 * 
 * 创建人：段鹏 - duanp@neusoft.com
 * 
 * 创建时间：2009-6-30 下午04:44:49
 * 
 * 版权所有：东软集团股份有限公司
 */
package cn.ursun.platform.ps.memorydb.listener;

import java.util.List;

import cn.ursun.platform.core.domain.Server;
import cn.ursun.platform.core.event.WeeEvent;
import cn.ursun.platform.core.event.WeeEventListener;
import cn.ursun.platform.core.event.WeeSyncEventListener;
import cn.ursun.platform.core.util.ConfigUtils;
import cn.ursun.platform.ps.memorydb.dao.AbstractDatabaseSynchronizer;
import cn.ursun.platform.ps.sync.dao.SynchronizerDAO;

/**
 * <p>[描述信息：说明类的基本功能]</p>
 *
 * @author 段鹏- duanp@neusoft.com
 * @version 1.0 Created on 2009-6-30 下午04:44:49
 */
public class MemoryDBSourceDataRefreshListener extends WeeSyncEventListener {

	private AbstractDatabaseSynchronizer databaseSynchronizer = null;

	private SynchronizerDAO synchronizerDAO = null;

	private String contentType = null;

	/**
	 * <p>[描述方法实现的功能]</p>
	 * 
	 * @see cn.ursun.platform.core.event.WeeEventListener#cloneListener()
	 * @author: 段鹏 - duanp@neusoft.com 
	 * @date: Created on 2009-6-30 下午04:44:49
	 */
	public WeeEventListener cloneListener() {
		return null;
	}

	/**
	 * <p>[描述方法实现的功能]</p>
	 * 
	 * @see cn.ursun.platform.core.event.WeeEventListener#concern(cn.ursun.platform.core.event.WeeEvent)
	 * @author: 段鹏 - duanp@neusoft.com 
	 * @date: Created on 2009-6-30 下午04:44:49
	 */
	public void concern(WeeEvent event) {
		List<String> tableList = databaseSynchronizer.fetchManagedTables();
		for (String tableName : tableList) {
			databaseSynchronizer.synchronize(tableName);
		}
		// 修改指定类型的Server时间戳，用于同步其他节点的内存数据库
		synchronizerDAO.fixUpdateTimestamp(contentType);
		// 修改当前节点的时间戳于服务器同步
		synchronizerDAO.fixSynchronizeTimestamp(getServer(), contentType);
	}

	/**
	 * <p>[描述方法实现的功能]</p>
	 * 
	 * @see cn.ursun.platform.core.event.WeeEventListener#support(java.lang.String)
	 * @author: 段鹏 - duanp@neusoft.com 
	 * @date: Created on 2009-6-30 下午04:44:49
	 */
	public boolean support(String eventCode) {
		if (contentType.equalsIgnoreCase(eventCode))
			return true;
		return false;
	}

	public AbstractDatabaseSynchronizer getDatabaseSynchronizer() {
		return databaseSynchronizer;
	}

	public void setDatabaseSynchronizer(AbstractDatabaseSynchronizer databaseSynchronizer) {
		this.databaseSynchronizer = databaseSynchronizer;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public SynchronizerDAO getSynchronizerDAO() {
		return synchronizerDAO;
	}

	public void setSynchronizerDAO(SynchronizerDAO synchronizerDAO) {
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

}
