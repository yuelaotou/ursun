/**
 * 文件名：MessageDAO.java
 * 
 * 创建人：段鹏 - duanp@neusoft.com
 * 
 * 创建时间：2009-3-30 上午10:42:02
 * 
 * 版权所有：东软集团股份有限公司
 */
package cn.ursun.platform.ps.dao;

import java.util.List;

import cn.ursun.platform.core.dto.Pagination;
import cn.ursun.platform.core.exception.BizException;
import cn.ursun.platform.ps.domain.Code;
import cn.ursun.platform.ps.domain.Message;
import cn.ursun.platform.ps.domain.MessageConfig;

/**
 * <p>消息中心DAO</p>
 *
 * @author 段鹏- duanp@neusoft.com
 * @version 1.0 Created on 2009-3-30 上午10:42:02
 */
public interface MessageDAO {

	/**
	 * <p>插入信息</p>
	 * 
	 * @param m
	 * @author: 段鹏 - duanp@neusoft.com 
	 * @date: Created on 2009-3-30 上午10:46:40
	 */
	public void insertMessage(Message m) throws BizException;

	/**
	 * <p>读取消息配置</p>
	 * 
	 * @return
	 * @author: 陈乃明 - chennm@neusoft.com
	 * @param type 
	 * @date: Created on Apr 2, 2009 10:37:24 AM
	 */
	public MessageConfig readMsgConfig(String userId) throws BizException;

	/**
	 * <p>保存消息配置</p>
	 * 
	 * @param msgConfig
	 * @author: 陈乃明 - chennm@neusoft.com
	 * @throws BizException 
	 * @date: Created on Apr 2, 2009 2:05:55 PM
	 */
	public void saveConfig(MessageConfig msgConfig) throws BizException;

	/**
	 * <p>查询当前登录用户需要提醒的信息</p>
	 * 
	 * @param msgConfig
	 * @return
	 * @author: 陈乃明 - chennm@neusoft.com
	 * @date: Created on Apr 7, 2009 5:23:43 PM
	 */
	public List<Message> queryMessageInActive(MessageConfig msgConfig) throws BizException;

	/**
	 * <p>查询当前登录用户首页信息</p>
	 * 
	 * @param msgConfig
	 * @return
	 * @throws BizException
	 * @author: 开发人员姓名 付斌 - fu.bin@neusoft.com 
	 * @date: Created on Apr 13, 2009 11:35:27 AM
	 */
	public List<Message> queryMessageIndex(MessageConfig msgConfig) throws BizException;

	public int queryInActiveTotal(String userId) throws BizException;

	/**
	 * <p>获得消息详细</p>
	 * 
	 * @param messageId
	 * @return
	 * @author: 陈乃明 - chennm@neusoft.com
	 * @param boxFlag 
	 * @throws BizException 
	 * @date: Created on Apr 7, 2009 8:07:45 PM
	 */
	public Message queryMessageDetail(String messageId, int isRead, int boxFlag) throws BizException;

	/**
	 * <p>更新消息状态</p>
	 * 
	 * @param message
	 * @author: 陈乃明 - chennm@neusoft.com
	 * @throws BizException 
	 * @date: Created on Apr 8, 2009 10:28:35 AM
	 */
	public int updateMsgState(Message message) throws BizException;

	public List<Message> queryAllRevMsgs(Message message, Pagination pagination) throws BizException;

	/**
	 * 
	 * <p>查询所有已经发送的消息</p>
	 * 
	 * @param message
	 * @param pagination
	 * @return
	 * @throws BizException
	 * @author: 开发人员姓名 付斌 - fu.bin@neusoft.com 
	 * @date: Created on Apr 16, 2009 12:15:53 PM
	 */
	public List<Message> querySendMsgs(Message message, Pagination pagination) throws BizException;

	public int queryMsgRevTotal(Message message) throws BizException;

	/**
	 * 
	 * <p>查询已经发送消息总数</p>
	 * 
	 * @param message
	 * @return
	 * @throws BizException
	 * @author: 开发人员姓名 付斌 - fu.bin@neusoft.com 
	 * @date: Created on Apr 16, 2009 12:14:49 PM
	 */
	public int querySendMsgTotal(Message message) throws BizException;

	public List<Code> queryMsgType(String queryMsgType) throws BizException;


	public int deleteMsgs(String msgIds, int boxId) throws BizException;

	public List<Code> queryAllReceiver(int receiverType,String msgType);

	public int pulbishMessage(Message message);

	public int saveMessage(Message message);

	public List<Code> queryMsgReceiver(String msgId);

	public Message queryEditSendMsg(String msgId);
}
