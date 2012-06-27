/**
 * 文件名：WEEUrlBasedFilterInvocationDefinitionMap.java
 * 
 * 创建人：兰硕 - lans@neusoft.com
 * 
 * 创建时间：Aug 30, 2008 1:33:03 PM
 * 
 * 版权所有：东软集团股份有限公司
 */
package cn.ursun.platform.ps.security;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.regex.Pattern;

import org.acegisecurity.ConfigAttribute;
import org.acegisecurity.ConfigAttributeDefinition;
import org.acegisecurity.intercept.web.AbstractFilterInvocationDefinitionSource;
import org.acegisecurity.intercept.web.FilterInvocationDefinition;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.ursun.console.app.domain.Resource;
import cn.ursun.console.app.domain.Role;

/**
 * <p>[描述信息：说明类的基本功能]</p>
 *
 * @author 兰硕 - lans@neusoft.com
 * @version 1.0 Created on Aug 30, 2008 1:33:03 PM
 */
public class WeeDBBasedFilterInvocationDefinitionMap extends AbstractFilterInvocationDefinitionSource implements
		FilterInvocationDefinition {

	private static final Log logger = LogFactory.getLog(WeeDBBasedFilterInvocationDefinitionMap.class);

	private boolean convertUrlToLowercaseBeforeComparison = false;

	private List<EntryHolder> requestList = null;

	/**
	 * <p>根据访问的URL查找可访问此资源的角色信息。</p>
	 * 
	 * @see org.acegisecurity.intercept.web.AbstractFilterInvocationDefinitionSource#lookupAttributes(java.lang.String)
	 * @author: 兰硕 - lans@neusoft.com
	 * @date: Created on Aug 30, 2008 1:33:03 PM
	 */
	public ConfigAttributeDefinition lookupAttributes(String url) {
		if (isConvertUrlToLowercaseBeforeComparison()) {
			String temp = url;
			url = temp.toLowerCase();
			if (logger.isDebugEnabled()) {
				logger.debug("Converted URL to lowercase, from: '" + temp + "'; to: '" + url + "'");
			}
		}
		WeeAuthenticationHolder holder = WeeAuthenticationHolder.getInstance();
		requestList = holder.getUrlRoleMapping();
		ConfigAttributeDefinition cad = new ConfigAttributeDefinition();
		Iterator<EntryHolder> itor = requestList.iterator();
		// 通过系统中所有的URL与角色的的映射关系集合，来过滤出能访问该URL的角色的集合。
		while (itor.hasNext()) {
			EntryHolder entryHolder = (EntryHolder) itor.next();
			boolean matched = entryHolder.getCompiledPattern().matcher(url).matches();
			if (logger.isDebugEnabled()) {
				logger.debug("Candidate is: '" + url + "'; pattern is " + entryHolder.getCompiledPattern().pattern()
						+ "; matched=" + matched);
			}
			if (matched) {
				Iterator<ConfigAttribute> caItor = entryHolder.getConfigAttributeDefinition().getConfigAttributes();
				while (caItor.hasNext()) {
					ConfigAttribute ca = caItor.next();
					cad.addConfigAttribute(ca);
				}
			}
		}

		if (cad.getConfigAttributes().hasNext()) {// 黑名单中的资源
			holder.addAdminSecurityConfig(cad);// 添加超级管理员的访问权限。
			return cad;
		} else {// 白名单中的资源（即没有设置URL）
			return holder.getUnlimitedConfigAttributes();
		}
	}



	/**
	 * 当过滤的URL在资源列表中不能找到匹配的角色的时候，则查询所有的角色。即只要通过身份验证就能访问该资源
	 */
	/*
	 * private ConfigAttributeDefinition getUnlimitedConfigAttribute() { try { List<Role> roleList =
	 * OrgUtils.getInstance().queryRoleListAll(excludeRole); ConfigAttributeDefinition configDefinition = new
	 * ConfigAttributeDefinition(); for (Role role : roleList) { configDefinition.addConfigAttribute(new
	 * SecurityConfig(role.getId())); } return configDefinition; } catch (BizException e) { throw new
	 * SystemException(e); } }
	 */

	public Iterator getConfigAttributeDefinitions() {
		return null;
	}

	public void addSecureUrl(String expression, ConfigAttributeDefinition attr) {
		if (logger.isDebugEnabled()) {
			logger.debug("Skip addSecureUrl execution");
		}
	}

	public boolean isConvertUrlToLowercaseBeforeComparison() {
		return convertUrlToLowercaseBeforeComparison;
	}

	public void setConvertUrlToLowercaseBeforeComparison(boolean convertUrlToLowercaseBeforeComparison) {
		this.convertUrlToLowercaseBeforeComparison = convertUrlToLowercaseBeforeComparison;
	}

	protected class ResourceRoleMapping {

		private List<Role> roleList = new Vector<Role>();

		private Resource resource = new Resource();

		public ResourceRoleMapping(Resource resource, List<Role> roleList) {
			this.resource = resource;
			this.roleList = roleList;
		}

		public List<Role> getRoleList() {
			return roleList;
		}

		public Resource getResource() {
			return resource;
		}
	}

	public static class EntryHolder {

		private ConfigAttributeDefinition configAttributeDefinition;

		private Pattern compiledPattern;

		public EntryHolder(Pattern compiledPattern, ConfigAttributeDefinition attr) {
			this.compiledPattern = compiledPattern;
			this.configAttributeDefinition = attr;
		}

		protected EntryHolder() {
			throw new IllegalArgumentException("Cannot use default constructor");
		}

		public Pattern getCompiledPattern() {
			return compiledPattern;
		}

		public ConfigAttributeDefinition getConfigAttributeDefinition() {
			return configAttributeDefinition;
		}
	}

}
