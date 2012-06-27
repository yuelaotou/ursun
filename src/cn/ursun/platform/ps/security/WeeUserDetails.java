/**
 * 文件名：WEEUserDetails.java
 * 
 * 创建人：兰硕 - lans@neusoft.com
 * 
 * 创建时间：Aug 27, 2008 9:59:22 PM
 * 
 * 版权所有：东软集团股份有限公司
 */
package cn.ursun.platform.ps.security;

import java.util.Date;

import org.acegisecurity.GrantedAuthority;
import org.acegisecurity.userdetails.UserDetails;
import org.apache.commons.lang.Validate;

import cn.ursun.console.app.domain.Account;
import cn.ursun.console.app.domain.User;
import cn.ursun.console.app.domain.UserAccount;

/**
 * <p>[描述信息：说明类的基本功能]</p>
 *
 * @author 兰硕 - lans@neusoft.com
 * @version 1.0 Created on Aug 27, 2008 9:59:22 PM
 */
public class WeeUserDetails implements UserDetails {

	private Account account = null;

	private User user = null;

	private UserAccount userAccount = null;

	private GrantedAuthority[] grantedAuthorities = null;

	public WeeUserDetails(Account account, User user) {
		Validate.notNull(account, "account required");
		Validate.notNull(user, "user required");
		this.account = account;
		this.user = user;
		userAccount = new UserAccount();
		userAccount.setUser(this.user);
		userAccount.setAccount(this.account);
	}

	/**
	 * <p>获取帐户所拥有的角色信息。</p>
	 * 
	 * @see org.acegisecurity.userdetails.UserDetails#getAuthorities()
	 * @author: 兰硕 - lans@neusoft.com
	 * @date: Created on Aug 27, 2008 9:59:22 PM
	 */
	public GrantedAuthority[] getAuthorities() {
		return this.grantedAuthorities;
	}

	/**
	 * <p>设置帐户所拥有的角色信息。</p>
	 * 
	 * @param grantedAuthorities
	 * @author: 兰硕 - lans@neusoft.com
	 * @date: Created on Aug 27, 2008 10:01:17 PM
	 */
	public void setAuthorities(GrantedAuthority[] grantedAuthorities) {
		this.grantedAuthorities = grantedAuthorities;
	}

	/**
	 * <p>获取帐户密码</p>
	 * 
	 * @see org.acegisecurity.userdetails.UserDetails#getPassword()
	 * @author: 兰硕 - lans@neusoft.com
	 * @date: Created on Aug 27, 2008 9:59:22 PM
	 */
	public String getPassword() {
		return this.account.getPassword();
	}

	/**
	 * <p>获取用户登陆名</p>
	 * 
	 * @see org.acegisecurity.userdetails.UserDetails#getUsername()
	 * @author: 兰硕 - lans@neusoft.com
	 * @date: Created on Aug 27, 2008 9:59:22 PM
	 */
	public String getUsername() {
		return this.account.getUsername();
	}

	/**
	 * <p>是否帐户未过期。</p>
	 * 
	 * @see org.acegisecurity.userdetails.UserDetails#isAccountNonExpired()
	 * @author: 兰硕 - lans@neusoft.com
	 * @date: Created on Aug 27, 2008 9:59:22 PM
	 */
	public boolean isAccountNonExpired() {
		Date expiredDate = this.account.getExpiredDate();
		if (expiredDate == null) {
			return true;
		} else {
			long time = expiredDate.getTime();
			long current = new Date().getTime();
			if (time > current)
				return true;
			else
				return false;
		}
	}

	/**
	 * <p>是否帐户未被锁定。</p>
	 * 
	 * @see org.acegisecurity.userdetails.UserDetails#isAccountNonLocked()
	 * @author: 兰硕 - lans@neusoft.com
	 * @date: Created on Aug 27, 2008 9:59:22 PM
	 */
	public boolean isAccountNonLocked() {
		return !this.account.isLocked();
	}

	/**
	 * <p>是否认证信息（密码）过期，默认为不过期。</p>
	 * 
	 * @see org.acegisecurity.userdetails.UserDetails#isCredentialsNonExpired()
	 * @author: 兰硕 - lans@neusoft.com
	 * @date: Created on Aug 27, 2008 9:59:22 PM
	 */
	public boolean isCredentialsNonExpired() {
		return true;
	}

	/**
	 * <p>是否帐户信息启用。</p>
	 * 
	 * @see org.acegisecurity.userdetails.UserDetails#isEnabled()
	 * @author: 兰硕 - lans@neusoft.com
	 * @date: Created on Aug 27, 2008 9:59:22 PM
	 */
	public boolean isEnabled() {
		return this.account.isEnabled();
	}

	public UserAccount getUserAccount() {
		return userAccount;
	}
}
