/**
 * 文件名：MessageManageBSImpl.java
 * 
 * 创建人：陈乃明 - chennm@neusoft.com
 * 
 * 创建时间：Apr 2, 2009 10:33:36 AM
 * 
 * 版权所有：东软集团股份有限公司
 */
package cn.ursun.platform.ps.message.manage.bizservice.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import cn.ursun.platform.core.exception.BizException;
import cn.ursun.platform.ps.bizservice.WeeBizService;
import cn.ursun.platform.ps.dao.MessageDAO;
import cn.ursun.platform.ps.domain.Code;
import cn.ursun.platform.ps.domain.Message;
import cn.ursun.platform.ps.domain.MessageConfig;
import cn.ursun.platform.ps.message.manage.bizservice.MessageManageBS;

/**
 * <p>消息配置</p>
 *
 * @author 创建人：陈乃明 - chennm@neusoft.com
 * @version 1.0 Created on Apr 2, 2009 10:33:36 AM
 */
public class MessageManageBSImpl extends WeeBizService implements MessageManageBS {

	private MessageDAO messageDAO;

	/**
	 * <p>读取配置消息</p>
	 * 
	 * @see cn.ursun.platform.message.manage.bizservice.MessageManageBS#readMsgConfig()
	 * @author: 陈乃明 - chennm@neusoft.com
	 * @throws BizException 
	 * @date: Created on Apr 2, 2009 10:36:58 AM
	 */
	public MessageConfig readMsgConfig(String userId) throws BizException {

		return messageDAO.readMsgConfig(userId);
	}

	public void setMessageDAO(MessageDAO messageDAO) {
		this.messageDAO = messageDAO;
	}

	/**
	 * <p>保存消息配置</p>
	 * 
	 * @see cn.ursun.platform.message.manage.bizservice.MessageManageBS#saveConfig(cn.ursun.platform.domain.MessageConfig)
	 * @author: 陈乃明 - chennm@neusoft.com
	 * @throws BizException 
	 * @date: Created on Apr 2, 2009 2:05:14 PM
	 */
	public void saveConfig(MessageConfig msgConfig) throws BizException {
		messageDAO.saveConfig(msgConfig);
	}

	/**
	 * <p>更新消息状态</p>
	 * 
	 * @see cn.ursun.platform.message.manage.bizservice.MessageManageBS#updateMsgState(cn.ursun.platform.domain.Message)
	 * @author: 陈乃明 - chennm@neusoft.com
	 * @throws BizException 
	 * @date: Created on Apr 8, 2009 10:28:19 AM
	 */
	public int updateMsgState(Message message) throws BizException {
		return messageDAO.updateMsgState(message);
	}

	/**
	 * <p></p>
	 * 
	 * @see cn.ursun.platform.message.manage.bizservice.MessageManageBS#queryMsgType()
	 * @author: 陈乃明 - chennm@neusoft.com
	 * @throws BizException 
	 * @date: Created on Apr 9, 2009 1:53:17 PM
	 */
	public List<Code> queryMsgType(String queryMsgType) throws BizException {
		return messageDAO.queryMsgType(queryMsgType);
	}

	/**
	 * <p></p>
	 * 
	 * @see cn.ursun.platform.message.manage.bizservice.MessageManageBS#deleteMsgs(java.lang.String)
	 * @author: 陈乃明 - chennm@neusoft.com
	 * @throws BizException 
	 * @date: Created on Apr 9, 2009 7:57:56 PM
	 */
	public int deleteMsgs(String msgIds, int boxId) throws BizException {
		return messageDAO.deleteMsgs(msgIds, boxId);
	}

	/**
	 * <p>根据接收者类型列出所有的接收者</p>
	 * 
	 * @see cn.ursun.platform.message.manage.bizservice.MessageManageBS#queryAllReceiver(int)
	 * @author: 陈乃明 - chennm@neusoft.com
	 * @date: Created on Apr 14, 2009 9:34:23 PM
	 */
	public List<Code> queryAllReceiver(int receiverType,String msgType) {
		return messageDAO.queryAllReceiver(receiverType,msgType);
	}

	/**
	 * <p>发布消息</p>
	 * 
	 * @see cn.ursun.platform.message.manage.bizservice.MessageManageBS#pulbishMessage(cn.ursun.platform.domain.Message)
	 * @author: 陈乃明 - chennm@neusoft.com
	 * @throws BizException 
	 * @date: Created on Apr 15, 2009 4:37:21 PM
	 */
	public int pulbishMessage(Message message) throws BizException {
		messageDAO.saveMessage(message);
		int r = messageDAO.pulbishMessage(message);
		if(r==-1){
			Date currDate = new Date();
			 SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd H:mm:ss");
			 String da = dateformat.format(currDate);
			throw new BizException("9000101",new String[]{da});
		}
		return r;
	}

	/**
	 * <p>保存并发布消息</p>
	 * 
	 * @see cn.ursun.platform.message.manage.bizservice.MessageManageBS#saveAndPublishMessage(cn.ursun.platform.domain.Message)
	 * @author: 陈乃明 - chennm@neusoft.com
	 * @throws BizException 
	 * @date: Created on Apr 15, 2009 4:37:21 PM
	 */
	public int saveAndPublishMessage(Message message) throws BizException {
		messageDAO.saveMessage(message);
		int r = messageDAO.pulbishMessage(message);
		if(r==-1){
			Date currDate = new Date();
			 SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd H:mm:ss");
			 String da = dateformat.format(currDate);
			throw new BizException("9000101",new String[]{da});
		}
		return r;
	}

	/**
	 * <p>保存消息</p>
	 * 
	 * @see cn.ursun.platform.message.manage.bizservice.MessageManageBS#saveMessage(cn.ursun.platform.domain.Message)
	 * @author: 陈乃明 - chennm@neusoft.com
	 * @date: Created on Apr 15, 2009 4:37:21 PM
	 */
	public String saveMessage(Message message) {
		messageDAO.saveMessage(message);
		return message.getId();
	}

	/**
	 * <p></p>
	 * 
	 * @see cn.ursun.platform.message.manage.bizservice.MessageManageBS#queryEditSendMsg(java.lang.String)
	 * @author: 陈乃明 - chennm@neusoft.com
	 * @date: Created on Apr 17, 2009 5:42:01 PM
	 */
	public Message queryEditSendMsg(String msgId) {
		return messageDAO.queryEditSendMsg(msgId);
	}

	/**
	 * <p></p>
	 * 
	 * @see cn.ursun.platform.message.manage.bizservice.MessageManageBS#queryMsgReceiver(java.lang.String)
	 * @author: 陈乃明 - chennm@neusoft.com
	 * @date: Created on Apr 17, 2009 5:42:01 PM
	 */
	public List<Code> queryMsgReceiver(String msgId) {
		return messageDAO.queryMsgReceiver(msgId);
	}

}
