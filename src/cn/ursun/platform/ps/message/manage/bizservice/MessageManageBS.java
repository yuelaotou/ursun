/**
 * 文件名：MessageManageBS.java
 * 
 * 创建人：段鹏 - duanp@neusoft.com
 * 
 * 创建时间：2009-3-30 上午10:39:44
 * 
 * 版权所有：东软集团股份有限公司
 */
package cn.ursun.platform.ps.message.manage.bizservice;

import java.util.List;

import cn.ursun.platform.core.exception.BizException;
import cn.ursun.platform.ps.domain.Code;
import cn.ursun.platform.ps.domain.Message;
import cn.ursun.platform.ps.domain.MessageConfig;

/**
 * <p>消息管理</p>
 *
 * @author 段鹏- duanp@neusoft.com
 * @version 1.0 Created on 2009-3-30 上午10:39:44
 */
public interface MessageManageBS {

	/**
	 * <p>读取配置消息</p>
	 * 
	 * @return
	 * @author: 陈乃明 - chennm@neusoft.com
	 * @param userId 
	 * @throws BizException 
	 * @date: Created on Apr 2, 2009 10:36:33 AM
	 */
	public MessageConfig readMsgConfig(String userId) throws BizException;

	/**
	 * <p>保存消息配置</p>
	 * 
	 * @param msgConfig
	 * @author: 陈乃明 - chennm@neusoft.com
	 * @throws BizException 
	 * @date: Created on Apr 2, 2009 2:04:31 PM
	 */
	public void saveConfig(MessageConfig msgConfig) throws BizException;

	/**
	 * <p>更新消息状态</p>
	 * 
	 * @param message
	 * @author: 陈乃明 - chennm@neusoft.com
	 * @throws BizException 
	 * @date: Created on Apr 8, 2009 10:28:05 AM
	 */
	public int updateMsgState(Message message) throws BizException;

	/**
	 * <p></p>
	 * 
	 * @return
	 * @author: 陈乃明 - chennm@neusoft.com
	 * @param message 
	 * @throws BizException 
	 * @date: Created on Apr 9, 2009 1:53:08 PM
	 */
	public List<Code> queryMsgType(String receiverType) throws BizException;

	/**
	 * <p></p>
	 * 
	 * @param msgIds
	 * @return
	 * @author: 陈乃明 - chennm@neusoft.com
	 * @throws BizException 
	 * @date: Created on Apr 9, 2009 7:57:38 PM
	 */
	public int deleteMsgs(String msgIds, int boxId) throws BizException;

	/**
	 * <p></p>
	 * 
	 * @param receiverType
	 * @return
	 * @author: 陈乃明 - chennm@neusoft.com
	 * @param loginMode 
	 * @date: Created on Apr 14, 2009 9:34:14 PM
	 */
	public List<Code> queryAllReceiver(int receiverType,String msgType);

	/**
	 * <p></p>
	 * 
	 * @param message
	 * @return
	 * @author: 陈乃明 - chennm@neusoft.com
	 * @throws BizException 
	 * @date: Created on Apr 15, 2009 4:36:55 PM
	 */
	public int saveAndPublishMessage(Message message) throws BizException;

	/**
	 * <p></p>
	 * 
	 * @param message
	 * @return
	 * @author: 陈乃明 - chennm@neusoft.com
	 * @throws BizException 
	 * @date: Created on Apr 15, 2009 4:37:03 PM
	 */
	public int pulbishMessage(Message message) throws BizException;

	/**
	 * <p></p>
	 * 
	 * @param message
	 * @return
	 * @author: 陈乃明 - chennm@neusoft.com
	 * @date: Created on Apr 15, 2009 4:37:09 PM
	 */
	public String saveMessage(Message message);

	/**
	 * <p></p>
	 * 
	 * @param msgId
	 * @return
	 * @author: 陈乃明 - chennm@neusoft.com
	 * @date: Created on Apr 17, 2009 5:41:21 PM
	 */
	public Message queryEditSendMsg(String msgId);

	/**
	 * <p></p>
	 * 
	 * @param msgId
	 * @return
	 * @author: 陈乃明 - chennm@neusoft.com
	 * @date: Created on Apr 17, 2009 5:41:55 PM
	 */
	public List<Code> queryMsgReceiver(String msgId);

}
