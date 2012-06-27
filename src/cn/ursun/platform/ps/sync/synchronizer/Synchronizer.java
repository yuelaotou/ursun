/**
 * 文件名：Synchronizer.java
 * 
 * 创建人：兰硕 - lans@neusoft.com
 * 
 * 创建时间：Oct 14, 2008 8:55:35 PM
 * 
 * 版权所有：东软集团股份有限公司
 */
package cn.ursun.platform.ps.sync.synchronizer;

/**
 * <p>同步处理器接口，提供对所关心的数据类型进行同步处理。</p>
 *
 * @author 兰硕 - lans@neusoft.com
 * @version 1.0 Created on Oct 14, 2008 8:55:35 PM
 */
public interface Synchronizer {

	/**
	 * <p>处理同步数据</p>
	 * 
	 * @author: 兰硕 - lans@neusoft.com
	 * @date: Created on Oct 14, 2008 9:26:58 PM
	 */
	public void process();
}
