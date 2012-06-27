/**
 * 文件名: WeeContextLoaderListener.java
 * 创建时间：Aug 15, 2009 12:59:34 PM
 * 创建人：兰硕
 * 版权：东软集团股份有限公司
 */
package cn.ursun.platform.core.spring;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.ContextLoaderListener;

/**
 * @author 兰硕
 */
public class WeeContextLoaderListener extends ContextLoaderListener {

	protected ContextLoader createContextLoader() {
		return new WeeContextLoader();
	}
}
