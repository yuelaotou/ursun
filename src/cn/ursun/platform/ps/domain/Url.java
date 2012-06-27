package cn.ursun.platform.ps.domain;

import cn.ursun.platform.core.domain.WeeDomain;

/**
 * <p>[描述信息：说明类的基本功能]</p>
 *
 * @author 刘君琦 - liujq@neusoft.com
 * @version 1.0 Created on Mar 10, 2009 10:58:03 AM
 */
public class Url extends WeeDomain {

	private String urlId = null;

	private String content = null;
	
	private String description = null;

	private String type = null;

	
	public String getUrlId() {
		return urlId;
	}


	
	public void setUrlId(String urlId) {
		this.urlId = urlId;
	}


	public String getDescription() {
		return description;
	}

	
	public void setDescription(String description) {
		this.description = description;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}


	
	public String getType() {
		return type;
	}


	
	public void setType(String type) {
		this.type = type;
	}

}
