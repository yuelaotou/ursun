package cn.ursun.platform.core.scheduler;

import java.util.ArrayList;
import java.util.List;

import org.quartz.Trigger;

public class WeeSchedulerHolder {

	private static WeeSchedulerHolder instance = null;

	private List<Trigger> triggers;

	public static WeeSchedulerHolder getInstance() {
		if (instance == null) {
			instance = new WeeSchedulerHolder();
		}
		return instance;
	}

	protected void addTriggers(List<Trigger> triggerList) {
		if (triggerList != null) {
			if (this.triggers == null) {
				this.triggers = new ArrayList<Trigger>();
			}
			this.triggers.addAll(triggerList);
		}
	}

	protected List<Trigger> getTriggers() {
		return triggers;
	}
}
