package cn.ursun.platform.core.event;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

/**
 * <p>同步事件处理器，该处理中执行的处理会引起阻塞主程序的运行。</p>
 *
 * @author 兰硕 - lans@neusoft.com
 * @version 1.0 Created on Oct 15, 2008 9:25:26 AM
 */
public abstract class WeeSyncEventListener implements WeeEventListener,ApplicationListener {

	protected Log logger = LogFactory.getLog(WeeSyncEventListener.class);

	public void onApplicationEvent(ApplicationEvent event) {
		if (event instanceof WeeEvent) {
			if (support(((WeeEvent) event).getEventCode()))
				concern((WeeEvent) event);
		}
	}
}
