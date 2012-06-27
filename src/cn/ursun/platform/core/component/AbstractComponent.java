/**
 * 文件名：AbstractComponent.java
 * 
 * 创建人：兰硕 - lans@neusoft.com
 * 
 * 创建时间：Sep 20, 2008 6:08:34 PM
 * 
 * 版权所有：东软集团股份有限公司
 */
package cn.ursun.platform.core.component;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>[描述信息：说明类的基本功能]</p>
 *
 * @author 兰硕 - lans@neusoft.com
 * @version 1.0 Created on Sep 20, 2008 6:08:34 PM
 */
public abstract class AbstractComponent implements Component {

	protected static final Log logger = LogFactory.getLog(Component.class);

	public void init() {
	}

	public void destory() {
	}
}
