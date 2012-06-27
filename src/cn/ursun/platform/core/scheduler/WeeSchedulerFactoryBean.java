package cn.ursun.platform.core.scheduler;

import java.util.Arrays;

import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

/**
 * <p> Title: 计划任务工厂</p>
 * <p> Description: [描述]</p>
 * <p> Created on 2009-9-16</p>
 * <p> Copyright: Copyright (c) 2008</p>
 * <p> Company: 东软软件股份有限公司</p>
 * @author 宋成山 songchengshan@neusoft.com
 * @version 1.0
 */
public class WeeSchedulerFactoryBean extends SchedulerFactoryBean implements ApplicationListener {

	private WeeSchedulerHolder weeSchedulerHolder;

	private boolean isInit = false;

	/**
	 * <p>Discription:[方法功能中文描述]</p>
	 * Created on 2009-9-16
	 * @author:　宋成山 songchengshan@neusoft.com
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public void afterPropertiesSet() throws Exception {
		// 阻止SchedulerFactoryBean实例化时加载计划任务，当ApplicationContext发出ContextRefreshedEvent事件时再加载
	}

	/**
	 * <p>Discription:当ApplicationContext发出ContextRefreshedEvent事件时加载所有计划任务</p>
	 * Created on 2009-9-16
	 * @author:　宋成山 songchengshan@neusoft.com
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public void onApplicationEvent(ApplicationEvent applicationevent) {
		if (applicationevent instanceof ContextRefreshedEvent) {
			refresh();
			isInit = true;
		}
	}

	protected void refresh() {
		try {
			if (weeSchedulerHolder.getTriggers() != null) {
				super.setTriggers(weeSchedulerHolder.getTriggers().toArray(new Trigger[] {}));
			}
			super.afterPropertiesSet();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setTriggers(Trigger[] triggers) {
		weeSchedulerHolder.addTriggers(Arrays.asList(triggers));
		super.setTriggers(triggers);
	}

	// ---------------------------------------------------------------------
	// Implementation of DisposableBean interface
	// ---------------------------------------------------------------------

	/**
	 * Shut down the Quartz scheduler on bean factory shutdown,
	 * stopping all scheduled jobs.
	 */
	public void destroy() throws SchedulerException {
		if (isInit) {
			super.destroy();
		}
	}

	public void setWeeSchedulerHolder(WeeSchedulerHolder weeSchedulerHolder) {
		this.weeSchedulerHolder = weeSchedulerHolder;
	}
}
