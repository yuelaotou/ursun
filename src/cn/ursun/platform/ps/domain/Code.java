package cn.ursun.platform.ps.domain;
/**
 * 
 * <p>[代码表中代码信息bean：用于获取combox数据源]</p>
 *
 * @author 邹甲乐 - zoujiale@neusoft.com
 * @version 1.0 Created on Nov 7, 2008 2:43:14 AM
 */
public class Code {
	
	private String type = null;
	private String typeName = null;
	private String code = null;
	private String codeName = null;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getCodeName() {
		return codeName;
	}
	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}
}
