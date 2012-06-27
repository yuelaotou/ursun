package cn.ursun.platform.ps.framework.header.action;

import java.util.Locale;

import org.acegisecurity.Authentication;
import org.acegisecurity.context.SecurityContextHolder;

import cn.ursun.console.app.domain.UserAccount;
import cn.ursun.platform.core.action.WeeAction;
import cn.ursun.platform.ps.security.WeeSecurityInfo;
import cn.ursun.platform.ps.security.WeeUserDetails;

import com.opensymphony.xwork2.ActionContext;

public class HeaderAC extends WeeAction {

	private static final long serialVersionUID = 1L;

	private Locale locale = ActionContext.getContext().getLocale();

	private UserAccount userAccount = null;

	private String userIP;

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public UserAccount getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(UserAccount userAccount) {
		this.userAccount = userAccount;
	}

	public String getUserIP() {
		return userIP;
	}

	public void setUserIP(String userIP) {
		this.userIP = userIP;
	}

	public String init() throws Exception {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Object principal = authentication.getPrincipal();
		if (principal instanceof WeeUserDetails) {
			WeeUserDetails d = (WeeUserDetails) principal;
			userAccount = d.getUserAccount();
		}
		WeeSecurityInfo ws = WeeSecurityInfo.getInstance();
		userIP = ws.getUserIP();
		return SUCCESS;
	}

}
