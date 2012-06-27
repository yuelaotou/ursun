/**
 * 文件名：WEEUserDetailService.java
 * 
 * 创建人：兰硕 - lans@neusoft.com
 * 
 * 创建时间：Aug 27, 2008 9:48:45 PM
 * 
 * 版权所有：东软集团股份有限公司
 */
package cn.ursun.platform.ps.security;

import java.util.ArrayList;
import java.util.List;

import org.acegisecurity.GrantedAuthority;
import org.acegisecurity.GrantedAuthorityImpl;
import org.acegisecurity.userdetails.UserDetails;
import org.acegisecurity.userdetails.UserDetailsService;
import org.acegisecurity.userdetails.UsernameNotFoundException;
import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.dao.DataAccessException;

import cn.ursun.console.app.console.facade.AuthFacade;
import cn.ursun.console.app.domain.Account;
import cn.ursun.console.app.domain.Role;
import cn.ursun.console.app.domain.User;

/**
 * <p>[描述信息：说明类的基本功能]</p>
 *
 * @author 兰硕 - lans@neusoft.com
 * @version 1.0 Created on Aug 27, 2008 9:48:45 PM
 */
public class WeeUserDetailService implements UserDetailsService,InitializingBean {

	AuthFacade authFacade = null;

	/**
	 * <p>[描述方法实现的功能]</p>
	 * 
	 * @see org.acegisecurity.userdetails.UserDetailsService#loadUserByUsername(java.lang.String)
	 * @author: 兰硕 - lans@neusoft.com
	 * @date: Created on Aug 27, 2008 9:48:45 PM
	 */
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {
		try {
			Account account = authFacade.queryAccountByUsername(username);
			String userId = account.getUserId();
			User user = authFacade.queryUserById(userId);
			if (account == null || user == null)
				throw new UsernameNotFoundException("User [" + username + "] not found.");
			// 获取角色列表，设置人员角色
			List<Role> roleList = authFacade.queryRoleListOfUser(userId);
			List<GrantedAuthority> gaList = new ArrayList<GrantedAuthority>();
			for (Role r : roleList) {
				gaList.add(new GrantedAuthorityImpl(r.getId()));
			}
			// 如果用户存在，则为每个用户添加系统用户角色。当前实现，每个用户都有一个sysUserRole的角色。
			// gaList.add(new GrantedAuthorityImpl("SYS_USER"));
			// 如果用户存在，但没有角色，认为是不合法用户，禁止使用
			if(gaList.size()==0){
				throw new UsernameNotFoundException("Error to access Account data.");
			}
			GrantedAuthority[] ga = (GrantedAuthority[]) gaList.toArray(new GrantedAuthority[gaList.size()]);
			// 构造UserDetails
			WeeUserDetails userDetails = new WeeUserDetails(account, user);
			userDetails.setAuthorities(ga);
			return userDetails;
		} catch (Exception e) {
			if (e.getCause() instanceof DataAccessException)
				throw (DataAccessException) e.getCause();
			else
				throw new UsernameNotFoundException("Error to access Account data.");
		}
	}

	public void afterPropertiesSet() throws Exception {
		Validate.notNull(authFacade, "accountDAO must be set");
	}

	public AuthFacade getAuthFacade() {
		return authFacade;
	}

	public void setAuthFacade(AuthFacade authFacade) {
		this.authFacade = authFacade;
	}

}
