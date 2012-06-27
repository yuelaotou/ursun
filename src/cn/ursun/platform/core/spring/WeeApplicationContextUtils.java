/**
 * 文件名：ApplicationContextUtils.java
 * 
 * 创建人：兰硕 - lans@neusoft.com
 * 
 * 创建时间：Sep 3, 2008 10:21:21 PM
 * 
 * 版权所有：东软集团股份有限公司
 */
package cn.ursun.platform.core.spring;


/**
 * <p>[描述信息：说明类的基本功能]</p>
 *
 * @author 兰硕 - lans@neusoft.com
 * @version 1.0 Created on Sep 3, 2008 10:21:21 PM
 */
public class WeeApplicationContextUtils {

	private static WeeApplicationContextUtils instance = null;

	private WeeWebApplicationContext ctx = null;

	private WeeApplicationContextUtils() {

	}

	public static WeeApplicationContextUtils getInstance() {
		if (instance == null)
			instance = new WeeApplicationContextUtils();
		return instance;
	}

	protected void setApplicationContext(WeeWebApplicationContext ctx) {
		this.ctx = ctx;
	}

	public WeeWebApplicationContext getApplicationContext() {
		return this.ctx;
	}
}
