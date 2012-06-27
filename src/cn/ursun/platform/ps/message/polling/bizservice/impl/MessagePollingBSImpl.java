/**
 * 文件名：MessagePollingBSImpl.java
 * 
 * 创建人：陈乃明 - chennm@neusoft.com
 * 
 * 创建时间：Mar 31, 2009 12:45:02 PM
 * 
 * 版权所有：东软集团股份有限公司
 */
package cn.ursun.platform.ps.message.polling.bizservice.impl;

import java.util.List;

import cn.ursun.platform.core.dto.Pagination;
import cn.ursun.platform.core.exception.BizException;
import cn.ursun.platform.ps.bizservice.WeeBizService;
import cn.ursun.platform.ps.dao.MessageDAO;
import cn.ursun.platform.ps.domain.Code;
import cn.ursun.platform.ps.domain.Message;
import cn.ursun.platform.ps.domain.MessageConfig;
import cn.ursun.platform.ps.message.polling.bizservice.MessagePollingBS;
import cn.ursun.platform.ps.util.OrgUtils;

/**
 * <p>消息轮询</p>
 *
 * @author 创建人：陈乃明 - chennm@neusoft.com
 * @version 1.0 Created on Mar 31, 2009 12:45:02 PM
 */
public class MessagePollingBSImpl extends WeeBizService implements MessagePollingBS {

	private MessageDAO messageDAO;
	private String resourceId;
	

	/**
	 * 
	 * <p>查询欢迎页面消息</p>
	 * 
	 * @see cn.ursun.platform.message.polling.bizservice.MessagePollingBS#queryMessageInActive(cn.ursun.platform.domain.MessageConfig)
	 * @author: 开发人员姓名 付斌 - fu.bin@neusoft.com 
	 * @date: Created on Apr 13, 2009 11:33:30 AM
	 */
	public List<Message> queryMessageIndex(MessageConfig msgConfig) throws BizException {
		return messageDAO.queryMessageIndex(msgConfig);
	}

	public List<Code> queryMsgType(String msgType) throws BizException {
		return messageDAO.queryMsgType(msgType);
	}

	public void setMessageDAO(MessageDAO messageDAO) {
		this.messageDAO = messageDAO;
	}

	/**
	 * <p>查询消息</p>
	 * 
	 * @see cn.ursun.platform.message.polling.bizservice.MessagePollingBS#queryMessageInActive(cn.ursun.platform.domain.MessageConfig)
	 * @author: 陈乃明 - chennm@neusoft.com
	 * @date: Created on Apr 7, 2009 5:23:00 PM
	 */
	public List<Message> queryMessageInActive(MessageConfig msgConfig) throws BizException {
		return messageDAO.queryMessageInActive(msgConfig);
	}

	/**
	 * <p>查询消息总数</p>
	 * 
	 * @see cn.ursun.platform.message.polling.bizservice.MessagePollingBS#queryInActiveTotal(java.lang.String)
	 * @author: 陈乃明 - chennm@neusoft.com
	 * @date: Created on Apr 7, 2009 7:03:25 PM
	 */
	public int queryInActiveTotal(String userId) throws BizException {
		return messageDAO.queryInActiveTotal(userId);
	}

	/**
	 * <p>获得消息详细</p>
	 * 
	 * @see cn.ursun.platform.message.polling.bizservice.MessagePollingBS#queryMessageDetail(java.lang.String)
	 * @author: 陈乃明 - chennm@neusoft.com
	 * @date: Created on Apr 7, 2009 8:06:47 PM
	 */
	public Message queryMessageDetail(String messageId, int isRead, int boxFlag) throws BizException {
		return messageDAO.queryMessageDetail(messageId, isRead, boxFlag);
	}

	/**
	 * <p></p>
	 * 
	 * @see cn.ursun.platform.message.polling.bizservice.MessagePollingBS#queryAllRevMsgs(cn.ursun.platform.domain.Message, cn.ursun.platform.core.dto.Pagination)
	 * @author: 陈乃明 - chennm@neusoft.com
	 * @throws BizException 
	 * @date: Created on Apr 9, 2009 12:06:28 PM
	 */
	public List<Message> queryAllMsgs(Message message, Pagination pagination) throws BizException {
		return messageDAO.queryAllRevMsgs(message, pagination);
	}

	/**
	 * <p></p>
	 * 
	 * @see cn.ursun.platform.message.polling.bizservice.MessagePollingBS#queryMsgRevTotal(cn.ursun.platform.domain.Message)
	 * @author: 陈乃明 - chennm@neusoft.com
	 * @throws BizException 
	 * @date: Created on Apr 9, 2009 12:06:28 PM
	 */
	public int queryMsgTotal(Message message) throws BizException {
		return messageDAO.queryMsgRevTotal(message);
	}

	/**
	 * 
	 * <p>查询已经发送消息总数</p>
	 * 
	 * @see cn.ursun.platform.message.polling.bizservice.MessagePollingBS#queryMsgRevTotal(cn.ursun.platform.domain.Message)
	 * @author: 开发人员姓名 付斌 - fu.bin@neusoft.com 
	 * @date: Created on Apr 16, 2009 12:12:34 PM
	 */
	public int querySendMsgTotal(Message message) throws BizException {
		return messageDAO.querySendMsgTotal(message);
	}

	/**
	 * 
	 * <p>查询所有已经发送的消息</p>
	 * 
	 * @see cn.ursun.platform.message.polling.bizservice.MessagePollingBS#queryAllRevMsgs(cn.ursun.platform.domain.Message, cn.ursun.platform.core.dto.Pagination)
	 * @author: 开发人员姓名 付斌 - fu.bin@neusoft.com 
	 * @date: Created on Apr 16, 2009 12:13:50 PM
	 */
	public List<Message> querySendMsgs(Message message, Pagination pagination) throws BizException {
		return messageDAO.querySendMsgs(message, pagination);
	}

	/**
	 * <p>检查用户是否有权限发布消息</p>
	 * 
	 * @see cn.ursun.platform.ps.message.polling.bizservice.MessagePollingBS#queryUserCanPublish(java.lang.String)
	 * @author: 开发人员姓名 付斌 - fu.bin@neusoft.com 
	 * @date: Created on Apr 26, 2009 3:24:24 PM
	 */
	public boolean queryUserCanPublish(String userId) throws BizException {
		return OrgUtils.getInstance().hasPermission(userId,resourceId);
		
	}

	
	/**
	 *
	 * @param resourceId
	 */
	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}
}
