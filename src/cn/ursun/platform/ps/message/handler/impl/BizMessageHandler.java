/**
 * 文件名：BizMessageHandler.java
 * 
 * 创建人：陈乃明 - chennm@neusoft.com
 * 
 * 创建时间：Mar 30, 2009 7:48:18 PM
 * 
 * 版权所有：东软集团股份有限公司
 */
package cn.ursun.platform.ps.message.handler.impl;

import java.util.Date;

import cn.ursun.platform.core.event.WeeEvent;
import cn.ursun.platform.core.exception.BizException;
import cn.ursun.platform.core.util.IDGenerator;
import cn.ursun.platform.ps.dao.MessageDAO;
import cn.ursun.platform.ps.domain.Message;
import cn.ursun.platform.ps.message.handler.AbstractMessageHandler;
import cn.ursun.platform.ps.security.WeeSecurityInfo;

/**
 * <p>业务操作的信息处理</p>
 *
 * @author 创建人：陈乃明 - chennm@neusoft.com
 * @version 1.0 Created on Mar 30, 2009 7:48:18 PM
 */
public class BizMessageHandler extends AbstractMessageHandler {

	private MessageDAO messageDAO;

	/**
	 * <p>处理信息流程:1 存储消息 </p>
	 * 
	 * @see cn.ursun.platform.message.handler.MessageHandler#handle()
	 * @author: 陈乃明 - chennm@neusoft.com
	 * @date: Created on Mar 30, 2009 7:48:18 PM
	 */
	public void handle() throws BizException {
		Message message = (Message) this.getWeeEvent().getSource();
		messageDAO.insertMessage(message);
	}

	public MessageDAO getMessageDAO() {
		return messageDAO;
	}

	public void setMessageDAO(MessageDAO messageDAO) {
		this.messageDAO = messageDAO;
	}

	/**
	 * <p>保存消息</p>
	 * 
	 * @see cn.ursun.platform.message.handler.MessageHandler#handle(cn.ursun.platform.core.event.WeeEvent)
	 * @author: 陈乃明 - chennm@neusoft.com
	 * @date: Created on Mar 31, 2009 11:13:02 AM
	 */
	public void handle(WeeEvent event) throws BizException {
		messageDAO.insertMessage((Message) event.getSource());
	}

	/**
	 * <p>保存消息</p>
	 * 
	 * @see cn.ursun.platform.message.handler.MessageHandler#handle(cn.ursun.platform.domain.Message)
	 * @author: 陈乃明 - chennm@neusoft.com
	 * @date: Created on Apr 1, 2009 5:15:23 PM
	 */
	public void handle(Message message) throws BizException {
		Date now = new Date(System.currentTimeMillis());
		if (message.getId() == null) {
			message.setId(IDGenerator.generateId());
		}
		message.setStartDate(now);
		message.setEndDate(now);
		message.setUpdateDate(now);
		message.setFlag(2);
		message.setReceiverId(WeeSecurityInfo.getInstance().getUserId());
		message.setReceiverType(3);
		message.transferTypeToTypeId(message.getType());
		messageDAO.insertMessage(message);
	}

}
