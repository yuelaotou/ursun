/**
 * 文件名：WeeAction.java
 * 
 * 创建人：兰硕 - lans@neusoft.com
 * 
 * 创建时间：Aug 11, 2008 10:26:23 PM
 * 
 * 版权所有：东软集团股份有限公司
 */
package cn.ursun.platform.core.action;

import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * Wee框架的Action基类。基于此框架开发的Action可以继承此Action以提供有效的扩展机制。
 * 
 * @author 兰硕 - lans@neusoft.com
 */
public class WeeAction extends ActionSupport {

	/**
	 * 返回JSON数据的result Key
	 */
	public static final String JSON = "jsonResult";

	/**
	 * <p>以JSON类型返回指定的变量</p>
	 * 
	 * @param target
	 * @return
	 * @author: 段鹏 - duanp@neusoft.com 
	 * @date: Created on Sep 1, 2008 2:17:11 PM
	 */
	protected String toJSON(String... target) {
		// TODO 处理指定的JSON数据
		return JSON;
	}

	protected Map getSessionMap() {
		return ActionContext.getContext().getSession();
	}

	protected Object getSession(String key) {
		return ActionContext.getContext().getSession().get(key);
	}

	protected void putSession(String key, Object value) {
		ActionContext.getContext().getSession().put(key, value);
	}

	protected Object removeSession(String key) {
		return ActionContext.getContext().getSession().remove(key);
	}

	protected Map getApplicationMap() {
		return ActionContext.getContext().getApplication();
	}

	protected Object getApplication(String key) {
		return ActionContext.getContext().getApplication().get(key);
	}

	protected void putApplication(String key, Object value) {
		ActionContext.getContext().getApplication().put(key, value);
	}

	protected Object removeApplication(String key) {
		return ActionContext.getContext().getApplication().remove(key);
	}

	protected Object getRequestAttribute(String key) {
		return ActionContext.getContext().get(key);
	}

	protected void putRequestAttribute(String key, Object value) {
		ActionContext.getContext().put(key, value);
	}
	
}
