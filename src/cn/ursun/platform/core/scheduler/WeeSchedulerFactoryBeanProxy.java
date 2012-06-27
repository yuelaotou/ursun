package cn.ursun.platform.core.scheduler;

import java.util.List;

import org.apache.axis2.extensions.spring.receivers.ApplicationContextHolder;
import org.quartz.Trigger;
import org.springframework.beans.factory.InitializingBean;

public class WeeSchedulerFactoryBeanProxy implements InitializingBean {

	private WeeSchedulerHolder weeSchedulerHolder;

	private List<Trigger> triggers;

	public void afterPropertiesSet() throws Exception {

		ApplicationContextHolder.getContext();
		if (triggers != null) {
			weeSchedulerHolder.addTriggers(triggers);
		}
	}

	public void setTriggers(List<Trigger> triggers) {
		this.triggers = triggers;
	}

	public void setWeeSchedulerHolder(WeeSchedulerHolder weeSchedulerHolder) {
		this.weeSchedulerHolder = weeSchedulerHolder;
	}

}
