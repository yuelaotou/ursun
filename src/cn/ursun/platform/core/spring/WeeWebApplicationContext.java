package cn.ursun.platform.core.spring;

import org.springframework.core.io.Resource;
import org.springframework.web.context.ConfigurableWebApplicationContext;

/**
 * <p> Title: [名称]</p>
 * <p> Description: [描述]</p>
 * <p> Created on 2009-8-28</p>
 * <p> Copyright: Copyright (c) 2008</p>
 * <p> Company: 东软软件股份有限公司</p>
 * @author 宋成山 songchengshan@neusoft.com
 * @version 1.0
 */
public interface WeeWebApplicationContext extends ConfigurableWebApplicationContext {

	public void setConfigResources(Resource[] resources);

	public Resource[] getConfigResources();

}
