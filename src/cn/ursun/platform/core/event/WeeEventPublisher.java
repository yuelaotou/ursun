/**
 * 文件名：WeeEventPublisher.java
 * 
 * 创建人：兰硕 - lans@neusoft.com
 * 
 * 创建时间：Oct 15, 2008 9:56:36 AM
 * 
 * 版权所有：东软集团股份有限公司
 */
package cn.ursun.platform.core.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.acegisecurity.context.SecurityContextHolder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;

/**
 * <p>Wee事件发布器。</p>
 *
 * @author 兰硕 - lans@neusoft.com
 * @version 1.0 Created on Oct 15, 2008 9:56:36 AM
 */
public class WeeEventPublisher implements ApplicationContextAware {

	protected static final Log logger = LogFactory.getLog(WeeEventPublisher.class);

	private BlockingQueue<Runnable> eventQueue = null;

	private ThreadPoolExecutor executor = null;

	private Map<String, List<AbstractWeeAyncEventListener>> listenerMapper = new HashMap<String, List<AbstractWeeAyncEventListener>>();

	private ApplicationContext appCtx = null;

	private static WeeEventPublisher instance = null;

	private WeeEventPublisher() {
		// 创建任务队列
		eventQueue = new LinkedBlockingQueue<Runnable>(40);
		// 创建线程工厂
		CustomizableThreadFactory tf = new CustomizableThreadFactory();
		tf.setThreadGroupName("event-group");
		tf.setThreadNamePrefix("Wee-Event-Listener");
		// 创建线程执行器
		executor = new ThreadPoolExecutor(15, 40, 120L, TimeUnit.SECONDS, eventQueue, tf);
	}

	public static WeeEventPublisher getInstance() {
		if (instance == null)
			instance = new WeeEventPublisher();
		return instance;
	}

	public void setEventQueue(BlockingQueue<Runnable> eventQueue) {
		this.eventQueue = eventQueue;
	}

	public void setExecutor(ThreadPoolExecutor executor) {
		this.executor = executor;
	}

	public void setListenerMapper(Map<String, List<AbstractWeeAyncEventListener>> listenerMapper) {
		this.listenerMapper = listenerMapper;
	}

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.appCtx = applicationContext;
	}

	/**
	 * <p>发布异步事件。</p>
	 * 
	 * @param source
	 * @author: 兰硕 - lans@neusoft.com
	 * @date: Created on Oct 16, 2008 11:29:49 AM
	 */
	public void fireAyncEvent(Object source) {
		fireAyncEvent(new WeeEvent(null, source));
	}

	/**
	 * <p>发布异步事件。</p>
	 * 
	 * @param eventCode
	 * @author: 兰硕 - lans@neusoft.com
	 * @date: Created on Oct 16, 2008 11:29:48 AM
	 */
	public void fireAyncEvent(String eventCode) {
		fireAyncEvent(new WeeEvent(eventCode));
	}

	/**
	 * <p>发布异步事件。</p>
	 * 
	 * @param eventCode
	 * @param source
	 * @author: 兰硕 - lans@neusoft.com
	 * @date: Created on Oct 16, 2008 11:29:46 AM
	 */
	public void fireAyncEvent(String eventCode, Object source) {
		fireAyncEvent(new WeeEvent(eventCode, source));
	}

	/**
	 * <p>发布异步事件。</p>
	 * 
	 * @param event
	 * @author: 兰硕 - lans@neusoft.com
	 * @date: Created on Oct 16, 2008 11:29:44 AM
	 */
	public void fireAyncEvent(WeeEvent event) {
		logger.debug("fire AyncEvent : " + event);
		String eventCode = event.getEventCode();
		List<AbstractWeeAyncEventListener> listeners = listenerMapper.get(eventCode);
		if (listeners == null)
			return;
		for (AbstractWeeAyncEventListener listener : listeners) {
			if (!listener.support(event.getEventCode()))
				continue;
			// 必须使用cloneListener方法才能创建出一个non-singleton对象，否则相当于在多个线程中使用同一个task对象
			AbstractWeeAyncEventListener temp = (AbstractWeeAyncEventListener) listener.cloneListener();
			temp.setSecurityContext(SecurityContextHolder.getContext());
			temp.setEssEvent(event);
			executor.execute(temp);
		}
	}

	/**
	 * <p>发布同步时间，同步事件会广播给所有系统中注册的{@link ApplicationListener}，此事件处理器的执行是阻塞的。</p>
	 * 
	 * @param source
	 * @author: 兰硕 - lans@neusoft.com
	 * @date: Created on Oct 16, 2008 11:29:39 AM
	 */
	public void fireSyncEvent(Object source) {
		fireSyncEvent(new WeeEvent(null, source));
	}

	/**
	 * <p>发布同步时间，同步事件会广播给所有系统中注册的{@link ApplicationListener}，此事件处理器的执行是阻塞的。</p>
	 * 
	 * @param eventCode
	 * @author: 兰硕 - lans@neusoft.com
	 * @date: Created on Oct 16, 2008 11:29:36 AM
	 */
	public void fireSyncEvent(String eventCode) {
		fireSyncEvent(new WeeEvent(eventCode));
	}

	/**
	 * <p>发布同步时间，同步事件会广播给所有系统中注册的{@link ApplicationListener}，此事件处理器的执行是阻塞的。</p>
	 * 
	 * @param eventCode
	 * @param source
	 * @author: 兰硕 - lans@neusoft.com
	 * @date: Created on Oct 16, 2008 11:29:04 AM
	 */
	public void fireSyncEvent(String eventCode, Object source) {
		fireSyncEvent(new WeeEvent(eventCode, source));
	}

	/**
	 * <p>发布同步时间，同步事件会广播给所有系统中注册的{@link ApplicationListener}，此事件处理器的执行是阻塞的。</p>
	 * 
	 * @param event 发布的事件。
	 * @author: 兰硕 - lans@neusoft.com
	 * @date: Created on Oct 16, 2008 11:16:11 AM
	 */
	public void fireSyncEvent(WeeEvent event) {
		appCtx.publishEvent(event);
	}

	protected void addEventListener(String event, AbstractWeeAyncEventListener listener) {
		if (!listenerMapper.containsKey(event)) {
			listenerMapper.put(event, new ArrayList<AbstractWeeAyncEventListener>());
		}
		List<AbstractWeeAyncEventListener> list = listenerMapper.get(event);
		if (list == null) {
			listenerMapper.put(event, new ArrayList<AbstractWeeAyncEventListener>());
		}
		listenerMapper.get(event).add(listener);
	}
}
