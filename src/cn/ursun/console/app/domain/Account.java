/**
 * 文件名：
 * 
 * 创建人：兰硕 - lans@neusoft.com
 * 
 * 创建时间：Aug 19, 2008 5:07:47 PM
 * 
 * 版权所有：东软集团股份有限公司
 */
package cn.ursun.console.app.domain;

import java.util.Date;

import com.googlecode.jsonplugin.annotations.JSON;
import cn.ursun.platform.core.domain.WeeDomain;

/**
 * <p>[描述信息：说明类的基本功能]</p>
 *
 * @author 兰硕 - lans@neusoft.com
 * @version 1.0 Created on Aug 19, 2008 5:07:47 PM
 */
public class Account extends WeeDomain {

	private String accountId = null;

	private String userId = null;

	private String username = null;

	private String password = null;

	private boolean enabled = true;

	private Date registerDate = null;

	private Date expiredDate = null;

	private String description = "";

	private String question = "";

	private String answer = "";

	private boolean locked = false;

	public String getDescription() {
		return description;
	}

	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getExpiredDate() {
		return expiredDate;
	}

	public String getPassword() {
		return password;
	}

	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getRegisterDate() {
		return registerDate;
	}

	public String getUserId() {
		return userId;
	}

	public String getUsername() {
		return username;
	}

	public boolean isLocked() {
		return locked;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	
	public boolean isEnabled() {
		return enabled;
	}

	
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public void setExpiredDate(Date expiredDate) {
		this.expiredDate = expiredDate;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
}
