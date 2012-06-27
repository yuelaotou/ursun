/**
 * 文件名：WeeAynchronizeEventListener.java
 * 
 * 创建人：兰硕 - lans@neusoft.com
 * 
 * 创建时间：Oct 15, 2008 9:25:26 AM
 * 
 * 版权所有：东软集团股份有限公司
 */
package cn.ursun.platform.core.event;
import org.acegisecurity.context.SecurityContext;
import org.acegisecurity.context.SecurityContextHolder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.ursun.platform.core.exception.SystemException;
/**
 * <p>[描述信息：说明类的基本功能]</p>
 *
 * @author 兰硕 - lans@neusoft.com
 * @version 1.0 Created on Oct 15, 2008 9:25:26 AM
 */
public abstract class AbstractWeeAyncEventListener implements WeeEventListener,Runnable {

	protected Log logger = LogFactory.getLog(AbstractWeeAyncEventListener.class);

	private WeeEvent weeEvent = null;

	private SecurityContext securityContext = null;

	public AbstractWeeAyncEventListener() {
	}

	public void setEssEvent(WeeEvent weeEvent) {
		this.weeEvent = weeEvent;
	}

	public void run() {
		SecurityContextHolder.setContext(securityContext);
		if (support(weeEvent.getEventCode()))
			concern(weeEvent);
	}

	public WeeEventListener cloneListener() {
		try {
			return (WeeEventListener) this.clone();
		} catch (CloneNotSupportedException ex) {
			throw new SystemException(ex);
		}
	}

	/**
	 *
	 * @return 
	 */
	public SecurityContext getSecurityContext() {
		return securityContext;
	}

	/**
	 *
	 * @param securityContext
	 */
	public void setSecurityContext(SecurityContext securityContext) {
		this.securityContext = securityContext;
	}
}
