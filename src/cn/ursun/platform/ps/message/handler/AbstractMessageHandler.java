/**
 * 文件名：AbstractMessageHandler.java
 * 
 * 创建人：陈乃明 - chennm@neusoft.com
 * 
 * 创建时间：Mar 30, 2009 7:50:03 PM
 * 
 * 版权所有：东软集团股份有限公司
 */
package cn.ursun.platform.ps.message.handler;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.ursun.platform.core.event.WeeEvent;

/**
 * <p></p>
 *
 * @author 创建人：陈乃明 - chennm@neusoft.com
 * @version 1.0 Created on Mar 30, 2009 7:50:03 PM
 */
public abstract class AbstractMessageHandler implements MessageHandler {

	protected Log logger = LogFactory.getLog(AbstractMessageHandler.class);

	private WeeEvent weeEvent = null;

	public AbstractMessageHandler() {
	};

	public void setWeeEvent(WeeEvent essEvent) {
		this.weeEvent = essEvent;
	}

	public WeeEvent getWeeEvent() {
		return weeEvent;
	}
	//
	// /**
	// * <p>处理事件</p>
	// *
	// * @param event
	// * @author: 陈乃明 - chennm@neusoft.com
	// * @date: Created on Mar 31, 2009 11:10:48 AM
	// */
	// public void handle(WEEEvent event) {
	// }

}
