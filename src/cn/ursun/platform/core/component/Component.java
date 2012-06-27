/**
 * 文件名：Component.java
 *
 * 创建人：兰硕 - lans@neusoft.com
 *
 * 创建时间：Sep 11, 2008 9:22:40 PM
 *
 * 版权所有：东软集团股份有限公司
 */
package cn.ursun.platform.core.component;


/**
 * <p>[描述信息：说明类的基本功能]</p>
 *
 * @author 兰硕 - lans@neusoft.com
 * @version 1.0 Created on Sep 11, 2008 9:22:40 PM
 */
public interface Component {

	public void init();
	
	public void destory();
	
	public void execute();
}
