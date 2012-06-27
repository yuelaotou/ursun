package cn.ursun.platform.ps.codelist.action;

import java.util.List;

import cn.ursun.platform.core.action.WeeAction;
import cn.ursun.platform.core.exception.BizException;
import cn.ursun.platform.ps.codelist.bizservice.CodeListBS;
import cn.ursun.platform.ps.domain.Code;
/**
 * 
 * <p>[获取内存代码表action层]</p>
 *
 * @author 邹甲乐 - zoujiale@neusoft.com
 * @version 1.0 Created on Nov 7, 2008 2:18:21 AM
 */
public class CodeListAC extends WeeAction{
	/**
	 * bs层setter注入
	 */
	CodeListBS codeListBS;
	/**
	 * 代码列表,返回前台
	 */
	private List <Code> codeList;
	/**
	 * 代码表类型,前台获取setter注入
	 */
	private String codeType;
	/**
	 * 
	 * <p>[获取代码表列表]</p>
	 * 
	 * @return
	 * @throws BizException
	 * @author: 邹甲乐 - zoujiale@neusoft.com 
	 * @date: Created on Nov 7, 2008 2:19:56 AM
	 */
	public String queryCodeList() throws BizException {
		codeList = codeListBS.queryCodeList(codeType);
		return JSON;
	}
	public CodeListBS getCodeListBS() {
		return codeListBS;
	}
	public void setCodeListBS(CodeListBS codeListBS) {
		this.codeListBS = codeListBS;
	}
	public List<Code> getCodeList() {
		return codeList;
	}
	public void setCodeList(List<Code> codeList) {
		this.codeList = codeList;
	}
	public String getCodeType() {
		return codeType;
	}
	public void setCodeType(String codeType) {
		this.codeType = codeType;
	}
}
