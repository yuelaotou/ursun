/**
 * 文件名：MessagePollingBS.java
 * 
 * 创建人：段鹏 - duanp@neusoft.com
 * 
 * 创建时间：2009-3-30 上午10:40:36
 * 
 * 版权所有：东软集团股份有限公司
 */
package cn.ursun.platform.ps.message.polling.bizservice;

import java.util.List;

import cn.ursun.platform.core.dto.Pagination;
import cn.ursun.platform.core.exception.BizException;
import cn.ursun.platform.ps.domain.Code;
import cn.ursun.platform.ps.domain.Message;
import cn.ursun.platform.ps.domain.MessageConfig;

/**
 * <p>消息轮询BS</p>
 *
 * @author 段鹏- duanp@neusoft.com
 * @version 1.0 Created on 2009-3-30 上午10:40:36
 */
public interface MessagePollingBS {

	/**
	 * <p>获得欢迎页面消息</p>
	 * 
	 * @param msgConfig
	 * @return
	 * @throws BizException
	 * @author: 开发人员姓名 付斌 - fu.bin@neusoft.com 
	 * @date: Created on Apr 13, 2009 11:35:52 AM
	 */
	public List queryMessageIndex(MessageConfig msgConfig) throws BizException;

	/**
	 * <p>获得消息类型列表</p>
	 * 
	 * @param msgConfig
	 * @return
	 * @throws BizException
	 * @author: 开发人员姓名 付斌 - fu.bin@neusoft.com 
	 * @param message 
	 * @date: Created on Apr 13, 2009 11:35:52 AM
	 */
	public List<Code> queryMsgType(String msgType) throws BizException;

	/**
	 * <p>获得消息</p>
	 * 
	 * @param msgConfig
	 * @return
	 * @author: 陈乃明 - chennm@neusoft.com
	 * @date: Created on Apr 7, 2009 5:22:21 PM
	 */
	public List<Message> queryMessageInActive(MessageConfig msgConfig) throws BizException;

	/**
	 * <p>获得消息总数</p>
	 * 
	 * @param userId
	 * @return
	 * @throws BizException
	 * @author: 陈乃明 - chennm@neusoft.com
	 * @date: Created on Apr 7, 2009 8:06:10 PM
	 */
	public int queryInActiveTotal(String userId) throws BizException;

	/**
	 * <p>获得消息详细</p>
	 * 
	 * @param messageId
	 * @return
	 * @author: 陈乃明 - chennm@neusoft.com
	 * @param boxFlag 
	 * @date: Created on Apr 7, 2009 8:06:04 PM
	 */
	public Message queryMessageDetail(String messageId, int isRead, int boxFlag) throws BizException;

	/**
	 * <p></p>
	 * 
	 * @param message
	 * @return
	 * @author: 陈乃明 - chennm@neusoft.com
	 * @throws BizException 
	 * @date: Created on Apr 9, 2009 12:05:17 PM
	 */
	public int queryMsgTotal(Message message) throws BizException;

	/**
	 * <p>查询已经发送消息总数</p>
	 * 
	 * @param message
	 * @return
	 * @author: 付斌 - fu.bin@neusoft.com
	 * @throws BizException 
	 * @date: Created on Apr 9, 2009 12:05:17 PM
	 */
	public int querySendMsgTotal(Message message) throws BizException;

	/**
	 * <p></p>
	 * 
	 * @param message
	 * @param pagination
	 * @return
	 * @author: 陈乃明 - chennm@neusoft.com
	 * @throws BizException 
	 * @date: Created on Apr 9, 2009 12:05:30 PM
	 */
	public List<Message> queryAllMsgs(Message message, Pagination pagination) throws BizException;

	/**
	 * <p>查询所有已经发送的消息</p>
	 * 
	 * @param message
	 * @param pagination
	 * @return
	 * @author: 付斌 - fu.bin@neusoft.com
	 * @throws BizException 
	 * @date: Created on Apr 9, 2009 12:05:30 PM
	 */
	public List<Message> querySendMsgs(Message message, Pagination pagination) throws BizException;
	/**
	 * 
	 * <p>检查用户是否有权限发布消息</p>
	 * 
	 * @param userId
	 * @return
	 * @throws BizException
	 * @author: 开发人员姓名 付斌 - fu.bin@neusoft.com 
	 * @date: Created on Apr 26, 2009 3:23:45 PM
	 */
	public boolean queryUserCanPublish(String userId) throws BizException;
}
