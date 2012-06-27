/**
 * 文件名: WeeFilterDispatcher.java 创建时间：Aug 13, 2009 11:15:42 PM 创建人：兰硕 版权：东软集团股份有限公司
 */
package cn.ursun.platform.core.struts2;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.FilterConfig;
import javax.servlet.ServletException;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.dispatcher.Dispatcher;
import org.apache.struts2.dispatcher.FilterDispatcher;

import cn.ursun.platform.core.domain.AppConfig;
import cn.ursun.platform.core.domain.WeeConfig;
import cn.ursun.platform.core.domain.WeePlatformConfig;
import cn.ursun.platform.core.keep.AppConfigHolderProxy;

/**
 * @author 兰硕
 */
public class WeeFilterDispatcher extends FilterDispatcher {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		super.init(filterConfig);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected Dispatcher createDispatcher(FilterConfig filterConfig) {
		Map<String, String> params = new HashMap<String, String>();
		for (Enumeration e = filterConfig.getInitParameterNames(); e.hasMoreElements();) {
			String name = (String) e.nextElement();
			String value = filterConfig.getInitParameter(name);
			params.put(name, value);
		}
		// 加载struts配置文件
		getStrutsConfig(params);
		return new Dispatcher(filterConfig.getServletContext(), params);
	}

	/**
	 * <p>Discription: 加载struts配置文件</p>
	 * Created on 2009-8-28
	 * @author: 宋成山 songchengshan@neusoft.com
	 * @update: [日期YYYY-MM-DD] [更改人姓名]
	 */
	@SuppressWarnings("unchecked")
	private void getStrutsConfig(Map<String, String> m) {
		StringBuffer strutsConfigFiles = new StringBuffer("struts-default.xml,struts-plugin.xml,struts.xml");

		// 加载Wee平台核心struts配置文件
		WeeConfig weeConfig = AppConfigHolderProxy.getInstance().getWeeConfig();
		if (weeConfig!=null&&StringUtils.isNotEmpty(weeConfig.getStrutsConfig())) {
			strutsConfigFiles.append("," + weeConfig.getStrutsConfig());
		}
		
		// 加载Wee平台组件struts配置文件
		WeePlatformConfig weePlatformConfig = AppConfigHolderProxy.getInstance().getWeePlatformConfig();
		if (weePlatformConfig!=null&&StringUtils.isNotEmpty(weePlatformConfig.getStrutsConfig())) {
			strutsConfigFiles.append("," + weePlatformConfig.getStrutsConfig());
		}

		// 加载应用struts配置文件
		Iterator<AppConfig> appConfigs = AppConfigHolderProxy.getInstance().getAppConfigs();
		while (appConfigs.hasNext()) {
			AppConfig ac = appConfigs.next();
			if (StringUtils.isNotEmpty(ac.getStrutsConfig())) {
				strutsConfigFiles.append("," + ac.getStrutsConfig());
			}
		}

		String configPaths = (String) m.get("config");
		if (configPaths != null) {
			String files[] = configPaths.split("\\s*[,]\\s*");
			for (String file : files) {
				//过滤重复的文件
				if (strutsConfigFiles.toString().indexOf(file) == -1) {
					strutsConfigFiles.append("," + file);
				}
			}
		}
		m.put("config", strutsConfigFiles.toString());
	}
}
