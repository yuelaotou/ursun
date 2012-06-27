/**
 * 文件名：MessageManageAC.java
 * 
 * 创建人：段鹏 - duanp@neusoft.com
 * 
 * 创建时间：2009-3-30 上午10:39:18
 * 
 * 版权所有：东软集团股份有限公司
 */
package cn.ursun.platform.ps.message.manage.action;

import java.util.Date;
import java.util.List;

import cn.ursun.platform.core.action.WeeAction;
import cn.ursun.platform.core.exception.BizException;
import cn.ursun.platform.ps.security.WeeSecurityInfo;
import cn.ursun.platform.core.util.IDGenerator;
import cn.ursun.platform.ps.domain.Code;
import cn.ursun.platform.ps.domain.Message;
import cn.ursun.platform.ps.domain.MessageConfig;
import cn.ursun.platform.ps.message.manage.bizservice.MessageManageBS;

/**
 * <p>[描述信息：说明类的基本功能]</p>
 *
 * @author 段鹏- duanp@neusoft.com
 * @version 1.0 Created on 2009-3-30 上午10:39:18
 */
public class MessageManageAC extends WeeAction {

	private MessageManageBS manageMessageBS;

	/**
	 * 消息配置
	 */
	private MessageConfig msgConfig;

	private Message message;

	private String msgId;

	private int receiverType;

	private String msgType;

	private List<Code> msgTypeList;

	private List<Code> msgRevList;

	private int boxId;// 0-收件箱 1-发件箱

	/**
	 * 更新的条数
	 */
	private int updateMsgCount;

	/**
	 * 要置状态的消息id
	 */
	private String msgIds;

	public String readConfig() throws Exception {
		String userId = WeeSecurityInfo.getInstance().getUserId();
		msgConfig = manageMessageBS.readMsgConfig(userId);
		return JSON;
	}

	public String saveConfig() throws Exception {
		manageMessageBS.saveConfig(msgConfig);
		return JSON;
	}

	public String updateAllMsgState() throws Exception {
		updateMsgCount = manageMessageBS.updateMsgState(message);
		return JSON;
	}

	public String queryMsgType() throws Exception {
		msgTypeList = manageMessageBS.queryMsgType(msgType);
		return JSON;
	}

	public String queryAllReceiver() throws Exception {
		msgTypeList = manageMessageBS.queryAllReceiver(receiverType,msgType);
		return JSON;
	}

	public String deleteMsgs() throws Exception {
		updateMsgCount = manageMessageBS.deleteMsgs(msgIds, boxId);
		return JSON;
	}

	public String saveMessage() {
		// 如果处于新建状态，那么生成id
		if (message.getEditMode() == 0) {
			message.setId(IDGenerator.generateId());
		}
		msgId = manageMessageBS.saveMessage(message);
		return JSON;
	}

	public String pulbishMessage() throws Exception {
		// 如果处于新建状态，那么生成id
		if (message.getEditMode() == 0) {
			message.setId(IDGenerator.generateId());
		}
		msgId = message.getId();
		updateMsgCount = manageMessageBS.pulbishMessage(message);
		return JSON;
	}

	public String saveAndPublishMessage() throws Exception {
		// 如果处于新建状态，那么生成id
		if (message.getEditMode() == 0) {
			message.setId(IDGenerator.generateId());
		}
		updateMsgCount = manageMessageBS.saveAndPublishMessage(message);
		msgId = message.getId();
		return JSON;
	}
	
	public String publishMessageImmediately() throws Exception {
		// 如果处于新建状态，那么生成id
		if (message.getEditMode() == 0) {
			message.setId(IDGenerator.generateId());
		}
		
		message.setStartDate(new Date());
		updateMsgCount = manageMessageBS.saveAndPublishMessage(message);
		msgId = message.getId();
		return JSON;
	}

	public String queryEditSendMsg() {
		message = manageMessageBS.queryEditSendMsg(msgId);
		msgRevList = manageMessageBS.queryMsgReceiver(msgId);
		return JSON;
	}

	public MessageConfig getMsgConfig() {

		return msgConfig;
	}

	public void setManageMessageBS(MessageManageBS manageMessageBS) {
		this.manageMessageBS = manageMessageBS;
	}

	public void setMsgConfig(MessageConfig msgConfig) {
		this.msgConfig = msgConfig;
	}

	public String getMsgIds() {
		return msgIds;
	}

	public void setMsgIds(String msgIds) {
		this.msgIds = msgIds;
	}

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public void setUpdateMsgCount(int updateMsgCount) {
		this.updateMsgCount = updateMsgCount;
	}

	public int getUpdateMsgCount() {
		return updateMsgCount;
	}

	public List<Code> getMsgTypeList() {
		return msgTypeList;
	}

	public void setMsgTypeList(List<Code> msgTypeList) {
		this.msgTypeList = msgTypeList;
	}

	public int getReceiverType() {
		return receiverType;
	}

	public void setReceiverType(int receiverType) {
		this.receiverType = receiverType;
	}

	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	public List<Code> getMsgRevList() {
		return msgRevList;
	}

	public void setMsgRevList(List<Code> msgRevList) {
		this.msgRevList = msgRevList;
	}

	/**
	 *
	 * @return 
	 */
	public int getBoxId() {
		return boxId;
	}

	/**
	 *
	 * @param boxId
	 */
	public void setBoxId(int boxId) {
		this.boxId = boxId;
	}

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

}
