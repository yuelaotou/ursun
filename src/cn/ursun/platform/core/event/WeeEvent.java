package cn.ursun.platform.core.event;

import org.springframework.context.ApplicationEvent;

/**
 * <p>Wee事件，可以被Spring的ApplicationListener处理，也可以被Wee事件处理框架处理。</p>
 *
 * @author 顾力行 - gulx@neusoft.com
 * @version 1.0 Created on Aug 18, 2008 10:52:24 AM
 */
public class WeeEvent extends ApplicationEvent {

	private String eventCode = null;

	public WeeEvent() {
		super(new Object());
	}

	public WeeEvent(String eventCode) {
		super(new Object());
		this.eventCode = eventCode;
	}

	public WeeEvent(String eventCode, Object source) {
		super(source);
		this.eventCode = eventCode;
	}

	public String getEventCode() {
		return this.eventCode;
	}

	public String toString() {
		return "WeeEvent[code:" + eventCode + ",source:" + source + "]";
	}
}
