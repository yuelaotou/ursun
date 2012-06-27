package cn.ursun.platform.core.component;

import java.util.List;

import org.springframework.beans.factory.InitializingBean;

public class ComponentInitializerProxy implements InitializingBean {

	ComponentInitializer proxy = null;

	private List<Component> componentList = null;

	public void afterPropertiesSet() throws Exception {
		if (componentList != null) {
			for (Component component : componentList) {
				proxy.addComponent(component);
			}
		}
	}

	public void setProxy(ComponentInitializer proxy) {
		this.proxy = proxy;
	}

	public void setComponentList(List<Component> componentList) {
		this.componentList = componentList;
	}

}
