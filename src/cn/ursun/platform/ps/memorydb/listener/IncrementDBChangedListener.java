/**
 * 文件名：IncrementDBChangedListener.java
 *
 * 创建人：段鹏 - duanp@neusoft.com
 *
 * 创建时间：2009-6-30 下午03:50:23
 *
 * 版权所有：东软集团股份有限公司
 */
package cn.ursun.platform.ps.memorydb.listener;


/**
 * <p>[描述信息：说明类的基本功能]</p>
 *
 * @author 段鹏- duanp@neusoft.com
 * @version 1.0 Created on 2009-6-30 下午03:50:23
 */
public class IncrementDBChangedListener extends MemoryDBSourceDataChangedListener {

	/**
	 * <p>[描述方法实现的功能]</p>
	 * 
	 * @see cn.ursun.platform.core.event.WeeEventListener#support(java.lang.String)
	 * @author: 段鹏 - duanp@neusoft.com 
	 * @date: Created on 2009-6-30 下午03:50:23
	 */
	public boolean support(String eventCode) {
		if ("DB_CHANGED".equals(eventCode))
			return true;
		return false;
	}

}
