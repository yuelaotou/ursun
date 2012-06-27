/**
 * 文件名：BizLogDBRecorderListener.java
 * 
 * 创建人：段鹏 - duanp@neusoft.com
 * 
 * 创建时间：Nov 25, 2008 10:34:18 PM
 * 
 * 版权所有：东软集团股份有限公司
 */
package cn.ursun.platform.ps.bizlog.listener;

import cn.ursun.console.app.dao.BizLogDAO;
import cn.ursun.platform.core.event.AbstractWeeAyncEventListener;
import cn.ursun.platform.core.event.WeeEvent;
import cn.ursun.platform.ps.bizlog.IBizLog;

/**
 * <p>日志记录监听器</p>
 *
 * @author 段鹏- duanp@neusoft.com
 * @version 1.0 Created on Nov 25, 2008 10:34:18 PM
 */
public class BizLogDBRecorderListener extends AbstractWeeAyncEventListener {

	private BizLogDAO bizLogDAO = null;

	/**
	 * <p>关注处理方法</p>
	 * 
	 * @see cn.ursun.platform.core.event.WEEEventListener#concern(cn.ursun.platform.core.event.WEEEvent)
	 * @author: 段鹏 - duanp@neusoft.com 
	 * @date: Created on Nov 25, 2008 10:34:19 PM
	 */
	public void concern(WeeEvent event) {
		IBizLog bizLog = (IBizLog) event.getSource();
		bizLogDAO.records(bizLog);
	}

	/**
	 * <p>是否关注</p>
	 * 
	 * @see cn.ursun.platform.core.event.WEEEventListener#support(java.lang.String)
	 * @author: 段鹏 - duanp@neusoft.com 
	 * @date: Created on Nov 25, 2008 10:34:19 PM
	 */
	public boolean support(String eventCode) {
		return "BIZLOG_RECORDS".equalsIgnoreCase(eventCode);
	}

	/**
	 *
	 * @param bizLogDAO
	 */
	public void setBizLogDAO(BizLogDAO bizLogDAO) {
		this.bizLogDAO = bizLogDAO;
	}
}
