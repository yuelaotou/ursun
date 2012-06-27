/**
 * 文件名：SynchronizerThread.java
 * 
 * 创建人：兰硕 - lans@neusoft.com
 * 
 * 创建时间：Oct 14, 2008 8:25:32 PM
 * 
 * 版权所有：东软集团股份有限公司
 */
package cn.ursun.platform.ps.sync;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;

import cn.ursun.platform.core.domain.Server;
import cn.ursun.platform.core.util.ConfigUtils;
import cn.ursun.platform.ps.sync.dao.SynchronizerDAO;
import cn.ursun.platform.ps.sync.synchronizer.Synchronizer;

/**
 * <p>数据同步线程，负责在对需要进行同步的数据类型进行同步处理。</p>
 *
 * @author 兰硕 - lans@neusoft.com
 * @version 1.0 Created on Oct 14, 2008 8:25:32 PM
 */
public class CacheSynchronizer {

	protected static final Log logger = LogFactory.getLog(CacheSynchronizer.class);

	private SynchronizerDAO synchronizerDAO = null;

	/**
	 * 异常日志KEY
	 */
	private static final String WEE_EXCEPTION_LOGGER = "WeeException";

	private Server server = null;

	private Map<String, Synchronizer> synchronizerMapper = new HashMap<String, Synchronizer>();

	public void setSynchronizerDAO(SynchronizerDAO synchronizerDAO) {
		this.synchronizerDAO = synchronizerDAO;
	}

	public void setServer(Server server) {
		this.server = server;
	}

	public void setSynchronizerMapper(Map<String, Synchronizer> synchronizerMapper) {
		this.synchronizerMapper = synchronizerMapper;
	}

	public void process() {
		try {
			// 获取配置的所有同步数据类型

			Iterator<String> itor = synchronizerMapper.keySet().iterator();
			while (itor.hasNext()) {
				String contentType = itor.next();
				// 判断同步数据类型是否需要进行数据同步处理
				boolean needSync = synchronizerDAO.checkNeedSynchronize(getServer(), contentType);
				if (needSync) {
					logger.info("synchronizing contentType : " + contentType); 
					// 设置当前服务器的同步时间戳
					synchronizerDAO.fixSynchronizeTimestamp(getServer(), contentType); 
					// 获取数据类型对应的同步处理器 Synchronizer
					Synchronizer sync = synchronizerMapper.get(contentType);
					sync.process();
				}
			}
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			Logger.getLogger(WEE_EXCEPTION_LOGGER).debug(sw.getBuffer().toString());
		}
	}

	/**
	 *
	 * @return 
	 */
	public Server getServer() {
		server = new Server();
		server.setIP(ConfigUtils.getInstance().getMessage(Server.SERVER_IP));
		server.setPort(ConfigUtils.getInstance().getMessage(Server.SERVER_PORT));
		server.setServerId(ConfigUtils.getInstance().getMessage(Server.SERVER_ID));
		server.setServerName(ConfigUtils.getInstance().getMessage(Server.SERVER_NAME));
		return server;
	}

}
