/**
 * 文件名：MemoryDBSynchronizeListener.java
 * 
 * 创建人：兰硕 - lans@neusoft.com
 * 
 * 创建时间：Oct 15, 2008 3:41:18 PM
 * 
 * 版权所有：东软集团股份有限公司
 */
package cn.ursun.platform.ps.memorydb.listener;

import cn.ursun.platform.core.domain.Server;
import cn.ursun.platform.core.event.AbstractWeeAyncEventListener;
import cn.ursun.platform.core.event.WeeEvent;
import cn.ursun.platform.core.util.ConfigUtils;
import cn.ursun.platform.ps.sync.dao.MDBSyncDAO;

/**
 * <p>内存数据库初始化成功监听器。</p>
 *
 * @author 兰硕 - lans@neusoft.com
 * @version 1.0 Created on Oct 15, 2008 3:41:18 PM
 */
public class MemoryDBInitializeCompleteListener extends AbstractWeeAyncEventListener {

	private Server currentServer = null;

	private MDBSyncDAO synchronizerDAO = null;

	public void setCurrentServer(Server currentServer) {
		this.currentServer = currentServer;
	}

	public void setSynchronizerDAO(MDBSyncDAO synchronizerDAO) {
		this.synchronizerDAO = synchronizerDAO;
	}

	public void concern(WeeEvent event) {
		// String contentType = (String) event.getSource();
		// 更新当前服务器同步contentType数据的时间为contentType数据的最后更新时间
		synchronizerDAO.fixSynchronizeTimestamp(getCurrentServer(), MDBSyncDAO.DATABASE_SYNC_FLAG);
		logger.info("MEM_DB fix synchronize timestamp success ...");
	}

	public boolean support(String eventCode) {
		if ("MEMEORY_DB_INITIALIZED".equals(eventCode))
			return true;
		return false;
	}

	private static final String SERVER_IP = "currentServer.ip";

	private static final String SERVER_PORT = "currentServer.port";

	private static final String SERVER_NAME = "currentServer.name";

	private static final String SERVER_ID = "currentServer.id";

	/**
	 *
	 * @return 
	 */
	public Server getCurrentServer() {
		currentServer = new Server();
		currentServer.setIP(ConfigUtils.getInstance().getMessage(SERVER_IP));
		currentServer.setPort(ConfigUtils.getInstance().getMessage(SERVER_PORT));
		currentServer.setServerId(ConfigUtils.getInstance().getMessage(SERVER_ID));
		currentServer.setServerName(ConfigUtils.getInstance().getMessage(SERVER_NAME));
		return currentServer;
	}
}
