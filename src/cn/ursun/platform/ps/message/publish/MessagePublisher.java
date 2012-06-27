/**
 * 文件名：MessagePublisher.java
 * 
 * 创建人：段鹏 - duanp@neusoft.com
 * 
 * 创建时间：2009-3-30 上午10:33:33
 * 
 * 版权所有：东软集团股份有限公司
 */
package cn.ursun.platform.ps.message.publish;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.ursun.platform.core.exception.BizException;
import cn.ursun.platform.ps.domain.Message;
import cn.ursun.platform.ps.message.handler.AbstractMessageHandler;
import cn.ursun.platform.ps.message.handler.MessageHandler;

/**
 * <p>信息发布</p>
 *
 * @author 段鹏- duanp@neusoft.com
 * @version 1.0 Created on 2009-3-30 上午10:33:33
 */
public class MessagePublisher {

	protected static final Log logger = LogFactory.getLog(MessagePublisher.class);

	public static final int MWEEAGE_STATUS_SUCCWEE = 1;

	public static final int MWEEAGE_STATUS_FAILURE = -1;

	private static MessagePublisher instance = null;

	/**
	 * 消息处理机制配置
	 */
	private Map<String, List<AbstractMessageHandler>> listenerMapper = new HashMap<String, List<AbstractMessageHandler>>();

	private MessageHandler messageHandler;

	private MessagePublisher() {

	}

	public static MessagePublisher getInstance() {
		if (instance == null) {
			instance = new MessagePublisher();
		}
		return instance;
	}

	public int publish(Message message) throws BizException {
		logger.info("publish message:" + message.getType());
		List<AbstractMessageHandler> listeners = listenerMapper.get(message.getType());
		if (listeners == null) {
			logger.error("message type:" + message.getType() + " doesnot config.");
			return MWEEAGE_STATUS_FAILURE;
		}
		for (AbstractMessageHandler listener : listeners) {
			listener.handle(message);
		}
		return MWEEAGE_STATUS_SUCCWEE;
	}

//	/**
//	 * <p>发布消息时要求生成完整的Message，消息的结束时间是必须有的，消息的开始时间如果没有就是当前时间</p>
//	 * 
//	 * @param event 包含消息的类型和消息实体
//	 * @return
//	 * @author: 陈乃明 - chennm@neusoft.com
//	 * @throws BizException 
//	 * @date: Created on Mar 30, 2009 8:12:58 PM
//	 */
//	@Deprecated
//	public int publish(WEEEvent event) throws BizException {
//		logger.info("publish message:" + event.getEventCode());
//
//		List<AbstractMessageHandler> listeners = listenerMapper.get(event.getEventCode());
//		if (listeners == null)
//			return MWEEAGE_STATUS_FAILURE;
//		for (AbstractMessageHandler listener : listeners) {
//			listener.handle(event);
//		}
//		// TODO 发布信息到信息栈中。
//		return MWEEAGE_STATUS_SUCCWEE;
//	}

//	public void publish(String eventCode, Object source) throws BizException {
//		publish(new WEEEvent(eventCode, source));
//	}

	public MessageHandler getMessageHandler() {
		return messageHandler;
	}

	public void setMessageHandler(MessageHandler messageHandler) {
		this.messageHandler = messageHandler;
	}

	public void setListenerMapper(Map<String, List<AbstractMessageHandler>> listenerMapper) {
		this.listenerMapper = listenerMapper;
	}

}
