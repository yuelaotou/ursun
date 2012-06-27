/**
 * 文件名: WeeDomain.java
 * 创建时间：Aug 13, 2009 10:14:06 PM
 * 创建人：兰硕
 * 版权：东软集团股份有限公司
 */
package cn.ursun.platform.core.domain;

import java.io.Serializable;

/**
 * @author 兰硕
 */
public class WeeDomain implements Serializable {

	private String id = null;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
