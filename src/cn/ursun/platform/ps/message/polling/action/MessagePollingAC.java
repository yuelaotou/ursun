/**
 * 文件名：MessagePollingAC.java
 * 
 * 创建人：段鹏 - duanp@neusoft.com
 * 
 * 创建时间：2009-3-30 上午10:40:05
 * 
 * 版权所有：东软集团股份有限公司
 */
package cn.ursun.platform.ps.message.polling.action;

import java.util.List;

import cn.ursun.platform.core.action.WeeAction;
import cn.ursun.platform.core.dto.Pagination;
import cn.ursun.platform.core.exception.BizException;
import cn.ursun.platform.ps.security.WeeSecurityInfo;
import cn.ursun.platform.ps.domain.Code;
import cn.ursun.platform.ps.domain.Message;
import cn.ursun.platform.ps.domain.MessageConfig;
import cn.ursun.platform.ps.message.polling.bizservice.MessagePollingBS;

/**
 * <p>信息轮询</p>
 *
 * @author 段鹏- duanp@neusoft.com
 * @version 1.0 Created on 2009-3-30 上午10:40:05
 */
public class MessagePollingAC extends WeeAction {

	private MessagePollingBS messagePollingBS;

	private Message message;

	private String messageId;

	private int start;

	private int limit;

	private int isRead;

	/**
	 *收件箱与发件箱标志 0--收件箱  1--发件箱 
	 */
	private int boxFlag;

	private MessageConfig msgConfig;

	private List<Message> msgList;

	private List<Message> popMsgList;

	private List<Code> msgType;

	/**
	 * 当前时间未读的消息总数
	 */
	private int total;

	/**
	 * 是否有权限发布消息
	 */
	private boolean hasPermission;

	/**
	 * <p>返回的可能消息的列表，可能在要显示的时间内有多种消息需要显示，</p>
	 * 
	 * @return
	 * @author: 陈乃明 - chennm@neusoft.com
	 * @throws BizException 
	 * @date: Created on Mar 30, 2009 8:07:29 PM
	 */
	public String poll() throws BizException {
		total = messagePollingBS.queryInActiveTotal(WeeSecurityInfo.getInstance().getUserId());
		popMsgList = messagePollingBS.queryMessageInActive(msgConfig);
		msgList = messagePollingBS.queryMessageIndex(msgConfig);
		return JSON;
	}

	public String reloadWelcomePage() throws BizException {
		msgConfig = new MessageConfig();
		msgList = messagePollingBS.queryMessageIndex(msgConfig);
		return JSON;
	}

	/**
	 * 
	 * <p>初始化欢迎页面消息列表</p>
	 * 
	 * @return
	 * @throws BizException
	 * @author: 开发人员姓名 付斌 - fu.bin@neusoft.com 
	 * @date: Created on Apr 13, 2009 11:16:42 AM
	 */
	public String init() throws BizException {
		msgType = messagePollingBS.queryMsgType("-1");
		msgConfig = new MessageConfig();
		msgList = messagePollingBS.queryMessageIndex(msgConfig);
		hasPermission = messagePollingBS.queryUserCanPublish(WeeSecurityInfo.getInstance().getUserId());
		return JSON;
	}

	/**
	 * 
	 * <p>更新首页消息</p>
	 * 
	 * @return
	 * @throws BizException
	 * @author: 开发人员姓名 付斌 - fu.bin@neusoft.com 
	 * @date: Created on Apr 13, 2009 11:16:42 AM
	 */
	public String updateIndexMsg() throws BizException {
		msgConfig = new MessageConfig();
		msgList = messagePollingBS.queryMessageIndex(msgConfig);
		return JSON;
	}

	public String queryAllMsgs() throws Exception {
		Pagination pagination = new Pagination();
		pagination.setLimit(limit);
		pagination.setStart(start);
		total = messagePollingBS.queryMsgTotal(message);
		msgList = messagePollingBS.queryAllMsgs(message, pagination);
		return JSON;
	}

	/**
	 * 
	 * <p>[查询用户已经发送的消息]</p>
	 * 
	 * @return
	 * @throws Exception
	 * @author: 开发人员姓名 付斌 - fu.bin@neusoft.com 
	 * @date: Created on Apr 16, 2009 12:06:06 PM
	 */
	public String querySendMsgs() throws Exception {
		Pagination pagination = new Pagination();
		pagination.setLimit(limit);
		pagination.setStart(start);
		total = messagePollingBS.querySendMsgTotal(message);
		msgList = messagePollingBS.querySendMsgs(message, pagination);
		return JSON;
	}

	public String queryMessageDetail() throws Exception {
		message = messagePollingBS.queryMessageDetail(messageId, isRead, boxFlag);
		return JSON;
	}

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public List<Message> getMsgList() {
		return msgList;
	}

	public void setMessagePollingBS(MessagePollingBS messagePollingBS) {
		this.messagePollingBS = messagePollingBS;
	}

	public MessageConfig getMsgConfig() {
		return msgConfig;
	}

	public void setMsgConfig(MessageConfig msgConfig) {
		this.msgConfig = msgConfig;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	/**
	 *
	 * @return 
	 */
	public MessagePollingBS getMessagePollingBS() {
		return messagePollingBS;
	}

	/**
	 *
	 * @return 
	 */
	public List<Code> getMsgType() {
		return msgType;
	}

	/**
	 *
	 * @param isRead
	 */
	public void setIsRead(int isRead) {
		this.isRead = isRead;
	}

	public int getBoxFlag() {
		return boxFlag;
	}

	public void setBoxFlag(int boxFlag) {
		this.boxFlag = boxFlag;
	}

	public List<Message> getPopMsgList() {
		return popMsgList;
	}

	public void setPopMsgList(List<Message> popMsgList) {
		this.popMsgList = popMsgList;
	}

	/**
	 *
	 * @return 
	 */
	public boolean isHasPermission() {
		return hasPermission;
	}

	/**
	 *
	 * @param hasPermission
	 */
	public void setHasPermission(boolean hasPermission) {
		this.hasPermission = hasPermission;
	}

}
