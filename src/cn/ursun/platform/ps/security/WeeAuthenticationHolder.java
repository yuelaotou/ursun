/**
 * 文件名：WEEAuthenticationHolder.java
 * 
 * 创建人：段鹏 - duanp@neusoft.com
 * 
 * 创建时间：2009-5-8 下午07:15:05
 * 
 * 版权所有：东软集团股份有限公司
 */
package cn.ursun.platform.ps.security;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.regex.Pattern;

import org.acegisecurity.ConfigAttributeDefinition;
import org.acegisecurity.SecurityConfig;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.ursun.console.app.console.facade.AuthFacade;
import cn.ursun.console.app.domain.Role;
import cn.ursun.platform.core.exception.BizException;
import cn.ursun.platform.core.exception.SystemException;
import cn.ursun.platform.ps.security.WeeDBBasedFilterInvocationDefinitionMap.EntryHolder;

/**
 * <p>[描述信息：说明类的基本功能]</p>
 *
 * @author 段鹏- duanp@neusoft.com
 * @version 1.0 Created on 2009-5-8 下午07:15:05
 */
public class WeeAuthenticationHolder {

	private static WeeAuthenticationHolder instance = null;

	private static final Log logger = LogFactory.getLog(WeeDBBasedFilterInvocationDefinitionMap.class);

	private List<EntryHolder> urlRoleMapping = null;

	private List<String> anonymousRole = null;

	private List<String> adminRole = null;

	private ConfigAttributeDefinition configDefinition = null;

	AuthFacade authFacade = null;

	private WeeAuthenticationHolder() {
	}

	public static WeeAuthenticationHolder getInstance() {
		if (instance == null)
			instance = new WeeAuthenticationHolder();

		return instance;
	}

	protected void init() throws BizException {
		urlRoleMapping = initRoleMappingAttributes();
		configDefinition = initUnlimitedConfigAttributes();
	}

	/**
	 * <p>构建 资源所关联的URL 与 角色 的映射关系对象</p>
	 * 
	 * @param resource
	 * @param roleList
	 * @return
	 * @author: 段鹏 - duanp@neusoft.com 
	 * @throws BizException 
	 * @date: Created on 2009-3-3 下午03:14:42
	 */
	protected List<EntryHolder> initRoleMappingAttributes() throws BizException {
		List<EntryHolder> entryList = new Vector<EntryHolder>();
		Map<String, List<String>> urm = authFacade.queryUrlRoleMapping();
		Iterator it = urm.keySet().iterator();
		while (it.hasNext()) {
			ConfigAttributeDefinition conf = new ConfigAttributeDefinition();
			String key = (String) it.next();
			List<String> list = urm.get(key);
			for (String roleId : list) {
				conf.addConfigAttribute(new SecurityConfig(roleId));
			}
			Pattern p = null;
			try {
				p = Pattern.compile(key);
			} catch (Exception e) {
				logger.debug("URL Pattern Is ERROR ;");
				e.printStackTrace();
				continue;
			}
			entryList.add(new EntryHolder(p, conf));
		}
		return entryList;
	}

	/**
	 * <p>初始化所有角色列表信息</p>
	 * 
	 * @return
	 * @author: 段鹏 - duanp@neusoft.com 
	 * @date: Created on 2009-5-21 上午10:43:29
	 */
	private ConfigAttributeDefinition initUnlimitedConfigAttributes() {
		try {
			List<Role> roleList = authFacade.queryRoleListAll(null);
			configDefinition = new ConfigAttributeDefinition();
			for (Role role : roleList) {
				configDefinition.addConfigAttribute(new SecurityConfig(role.getId()));
			}
			return configDefinition;
		} catch (BizException e) {
			throw new SystemException(e);
		}
	}

	public void addAdminSecurityConfig(ConfigAttributeDefinition cad) {
		for (String adminRoleId : this.adminRole) {
			cad.addConfigAttribute(new SecurityConfig(adminRoleId));
		}
	}

	/**
	 *
	 * @return 
	 */
	public List<EntryHolder> getUrlRoleMapping() {
		return urlRoleMapping;
	}

	/**
	 *
	 * @param urlRoleMapping
	 */
	public void setUrlRoleMapping(List<EntryHolder> urlRoleMapping) {
		this.urlRoleMapping = urlRoleMapping;
	}

	/**
	 *
	 * @param anonymousRole
	 */
	public void setAnonymousRole(List<String> anonymousRole) {
		this.anonymousRole = anonymousRole;
	}

	public List<String> getAnonymousRole() {
		return anonymousRole;
	}

	/**
	 *
	 * @return 
	 */
	public ConfigAttributeDefinition getUnlimitedConfigAttributes() {
		return configDefinition;
	}

	public AuthFacade getAuthFacade() {
		return authFacade;
	}

	public void setAuthFacade(AuthFacade authFacade) {
		this.authFacade = authFacade;
	}

	/**
	 *
	 * @return 
	 */
	public List<String> getAdminRole() {
		return adminRole;
	}

	/**
	 *
	 * @param adminRole
	 */
	public void setAdminRole(List<String> adminRole) {
		this.adminRole = adminRole;
	}

}
