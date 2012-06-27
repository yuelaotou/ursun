package cn.ursun.platform.ps.codelist.bizservice;

import java.util.List;

import cn.ursun.platform.core.exception.BizException;
import cn.ursun.platform.ps.domain.Code;
/**
 * 
 * <p>[获取内存代码表业务层接口]</p>
 *
 * @author 邹甲乐 - zoujiale@neusoft.com
 * @version 1.0 Created on Nov 7, 2008 2:22:57 AM
 */
public interface CodeListBS {
	/**
	 * 
	 * <p>[获取代码列表]</p>
	 * 
	 * @param codeType
	 * @return
	 * @throws BizException
	 * @author: 邹甲乐 - zoujiale@neusoft.com 
	 * @date: Created on Nov 7, 2008 2:23:01 AM
	 */
	public List <Code> queryCodeList(String codeType) throws BizException;
}
