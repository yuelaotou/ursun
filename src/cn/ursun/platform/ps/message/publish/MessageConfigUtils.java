/**
 * 文件名：MessageConfigUtils.java
 * 
 * 创建人：陈乃明 - chennm@neusoft.com
 * 
 * 创建时间：Apr 2, 2009 4:42:44 PM
 * 
 * 版权所有：东软集团股份有限公司
 */
package cn.ursun.platform.ps.message.publish;

/**
 * <p>消息配置工具类</p>
 *
 * @author 创建人：陈乃明 - chennm@neusoft.com
 * @version 1.0 Created on Apr 2, 2009 4:42:44 PM
 */
public class MessageConfigUtils {

	private static MessageConfigUtils instancce;

	private MessageConfigUtils() {

	}

	public static MessageConfigUtils getInstance() {
		if (instancce == null) {
			instancce = new MessageConfigUtils();
		}
		return instancce;
	}
}
