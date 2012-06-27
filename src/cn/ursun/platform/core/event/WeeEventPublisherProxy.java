package cn.ursun.platform.core.event;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.InitializingBean;

public class WeeEventPublisherProxy implements InitializingBean {

	private WeeEventPublisher proxy = null;

	private Map<String, List<AbstractWeeAyncEventListener>> listenerMapper = new HashMap<String, List<AbstractWeeAyncEventListener>>();

	public void setProxy(WeeEventPublisher proxy) {
		this.proxy = proxy;
	}

	public void setListenerMapper(Map<String, List<AbstractWeeAyncEventListener>> listenerMapper) {
		this.listenerMapper = listenerMapper;
	}

	public void afterPropertiesSet() throws Exception {
		if (listenerMapper != null) {
			for (Iterator<String> it = listenerMapper.keySet().iterator(); it.hasNext();) {
				String event = it.next();
				List<AbstractWeeAyncEventListener> list = listenerMapper.get(event);
				if (list != null) {
					for (AbstractWeeAyncEventListener listener : list) {
						proxy.addEventListener(event, listener);
					}
				}
			}
		}
	}

}
