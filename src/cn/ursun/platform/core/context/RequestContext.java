package cn.ursun.platform.core.context;

import java.util.HashMap;
import java.util.Map;

public class RequestContext {

	Map context;

	static ThreadLocal requestContext = new ThreadLocal();

	public RequestContext() {
		this.context = new HashMap();
	}

	public static RequestContext getContext() {
		RequestContext context = (RequestContext) requestContext.get();
		if (context == null) {
			context = new RequestContext();
			setContext(context);
		}
		return context;
	}

	public static void setContext(RequestContext context) {
		requestContext.set(context);
	}

	public Object get(Object key) {
		return context.get(key);
	}

	public void put(Object key, Object value) {
		context.put(key, value);
	}

}
