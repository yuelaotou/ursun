/**
 * 文件名：MessageHandler.java
 * 
 * 创建人：段鹏 - duanp@neusoft.com
 * 
 * 创建时间：2009-3-30 上午10:30:28
 * 
 * 版权所有：东软集团股份有限公司
 */
package cn.ursun.platform.ps.message.handler;

import cn.ursun.platform.core.exception.BizException;
import cn.ursun.platform.ps.domain.Message;

/**
 * <p>[描述信息：说明类的基本功能]</p>
 *
 * @author 段鹏- duanp@neusoft.com
 * @version 1.0 Created on 2009-3-30 上午10:30:28
 */
public interface MessageHandler {

	public void handle() throws BizException;

	public void handle(Message message) throws BizException;
}
