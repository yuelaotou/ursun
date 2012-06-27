/**
 * 文件名：WeeEventListener.java
 * 
 * 创建人：兰硕 - lans@neusoft.com
 * 
 * 创建时间：Oct 15, 2008 9:45:41 AM
 * 
 * 版权所有：东软集团股份有限公司
 */
package cn.ursun.platform.core.event;
public interface WeeEventListener extends Cloneable {

	public boolean support(String eventCode);

	public void concern(WeeEvent event);

	public WeeEventListener cloneListener();
}
