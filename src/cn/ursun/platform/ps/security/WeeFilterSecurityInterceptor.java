/**
 * 文件名：ESSFilterSecurityInterceptor.java
 * 
 * 创建人：兰硕 - lans@neusoft.com
 * 
 * 创建时间：Aug 29, 2008 10:22:17 PM
 * 
 * 版权所有：东软集团股份有限公司
 */
package cn.ursun.platform.ps.security;

import org.acegisecurity.intercept.web.FilterSecurityInterceptor;

/**
 * <p>ESS的安全过滤器，根据数据库中配置的资源，找到可访问资源的角色</p>
 *
 * @author 兰硕 - lans@neusoft.com
 * @version 1.0 Created on Aug 29, 2008 10:22:17 PM
 */
public class WeeFilterSecurityInterceptor extends FilterSecurityInterceptor {

//	private ObjectDefinitionSource objectDefinitionSource = null;

//	/**
//	 * <p>获取ESS中定义的资源-角色对应关系。</p>
//	 * 
//	 * @see org.acegisecurity.intercept.web.FilterSecurityInterceptor#obtainObjectDefinitionSource()
//	 * @author: 兰硕 - lans@neusoft.com
//	 * @date: Created on Aug 29, 2008 10:28:26 PM
//	 */
//	public ObjectDefinitionSource obtainObjectDefinitionSource() {
//		if (objectDefinitionSource == null) {
//			objectDefinitionSource = initObjectDefinitionSource();
//		}
//		return objectDefinitionSource;
//	}
//
//	protected ObjectDefinitionSource initObjectDefinitionSource() {
//		return new ESSDBBasedFilterInvocationDefinitionMap();
//	}
//	
//	public void setObjectDefinitionSource(ObjectDefinitionSource objectDefinitionSource){
//		this.objectDefinitionSource = objectDefinitionSource;
//	}
}
