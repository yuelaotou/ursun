package cn.ursun.platform.ps.codelist.bizservice.imp;

import java.util.List;

import cn.ursun.platform.core.exception.BizException;
import cn.ursun.platform.ps.codelist.bizservice.CodeListBS;
import cn.ursun.platform.ps.dao.CodeListDAO;
import cn.ursun.platform.ps.domain.Code;
/**
 * 
 * <p>[获取内存代码表bs业务实现类]</p>
 *
 * @author 邹甲乐 - zoujiale@neusoft.com
 * @version 1.0 Created on Nov 7, 2008 2:23:49 AM
 */
public class CodeListBSImp implements CodeListBS{
	/**
	 * 
	 */
	CodeListDAO codeListDAO;
	/**
	 * 
	 * <p>[获取代码列表]</p>
	 * 
	 * @see cn.ursun.platform.ps.codelist.bizservice.CodeListBS#queryCodeList(java.lang.String)
	 * @author: 邹甲乐 - zoujiale@neusoft.com 
	 * @date: Created on Nov 7, 2008 2:23:59 AM
	 */
	public List<Code> queryCodeList(String codeType) throws BizException {
		return codeListDAO.queryCodeList(codeType);
	}
	public void setCodeListDao(CodeListDAO codeListDAO) {
		this.codeListDAO = codeListDAO;
	}
}
