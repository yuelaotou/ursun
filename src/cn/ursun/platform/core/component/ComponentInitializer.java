/**
 * 文件名：ComponentInitializer.java
 * 
 * 创建人：兰硕 - lans@neusoft.com
 * 
 * 创建时间：Sep 11, 2008 9:23:40 PM
 * 
 * 版权所有：东软集团股份有限公司
 */
package cn.ursun.platform.core.component;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * <p>组件初始化</p>
 *
 * @author 兰硕 - lans@neusoft.com
 * @version 1.0 Created on Sep 11, 2008 9:23:40 PM
 */
public class ComponentInitializer implements ApplicationListener {

	protected static final Log logger = LogFactory.getLog(ComponentInitializer.class);

	private List<Component> componentList = null;

	private ScheduledExecutorService ses = null;

	private final List<ScheduledFuture<?>> sfList = new Vector<ScheduledFuture<?>>();

	public ComponentInitializer() {
		ses = Executors.newScheduledThreadPool(2);
	}

	public void setComponentList(List<Component> componentList) {
		this.componentList = componentList;
	}

	public void init() {
		if (componentList != null) {
			logger.info("Component initializing ...");
			for (final Component comp : componentList) {
				if (comp instanceof AynchronizerComponent) {
					long delay = ((AynchronizerComponent) comp).getDelayTime();
					ScheduledFuture<?> sf = new Init(comp).start(delay);
					sfList.add(sf);
				} else {
					comp.init();
					comp.execute();
				}
			}
			listenExecution("initialized");
		}
	}

	/**
	 * <p>Discription:当ApplicationContext发出ContextRefreshedEvent事件时加载所有组件</p>
	 * Created on 2009-9-16
	 * @author:　宋成山 songchengshan@neusoft.com
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public void onApplicationEvent(ApplicationEvent applicationevent) {
		if (applicationevent instanceof ContextRefreshedEvent) {
			init();
		}
	}

	protected void addComponent(Component component) {
		if (componentList == null) {
			componentList = new ArrayList<Component>();
		}
		componentList.add(component);
	}

	public void destory() {
		if (componentList != null) {
			logger.info("Component destoring ...");
			for (final Component comp : componentList) {
				if (comp instanceof AynchronizerComponent) {
					long delay = ((AynchronizerComponent) comp).getDelayTime();
					ScheduledFuture<?> sf = new Destory(comp).start(delay);
					sfList.add(sf);
				} else {
					comp.destory();
				}
			}
			listenExecution("destoried");
		}
	}

	private void listenExecution(final String flag) {
		new Thread(new Runnable() {

			public void run() {
				while (true) {
					for (int i = 0; i < sfList.size();) {
						ScheduledFuture<?> innerSF = sfList.get(i);
						if (innerSF.isDone()) {
							sfList.remove(innerSF);
						} else {
							i++;
						}
					}
					if (sfList.size() == 0)
						break;
					try {
						Thread.sleep(1000);
					} catch (InterruptedException ex) {
						ex.printStackTrace();
					}
				}
				logger.info("All Component " + flag + " !");
			}
		}, "Component-Execution-Listener").start();
	}

	private abstract class AyncComp implements Runnable {

		protected Component comp = null;

		public AyncComp(Component comp) {
			this.comp = comp;
		}

		public ScheduledFuture<?> start(long delay) {
			return ses.schedule(this, delay, TimeUnit.MILLISECONDS);
		}
	}

	private class Init extends AyncComp {

		public Init(Component comp) {
			super(comp);
		}

		public void run() {
			comp.init();
			comp.execute();
		}
	}

	private class Destory extends AyncComp {

		public Destory(Component comp) {
			super(comp);
		}

		public void run() {
			comp.destory();
		}
	}
}
