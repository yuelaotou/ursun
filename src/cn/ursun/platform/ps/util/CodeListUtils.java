/**
 * 文件名：CodeListUtils.java
 * 
 * 创建人：段鹏 - duanp@neusoft.com
 * 
 * 创建时间：2009-3-9 下午01:27:08
 * 
 * 版权所有：东软集团股份有限公司
 */
package cn.ursun.platform.ps.util;

import java.util.ArrayList;
import java.util.List;

import cn.ursun.platform.core.exception.SystemException;
import cn.ursun.platform.ps.domain.Code;


/**
 * <p>[描述信息：说明类的基本功能]</p>
 *
 * @author 段鹏- duanp@neusoft.com
 * @version 1.0 Created on 2009-3-9 下午01:27:08
 */
public class CodeListUtils {

	/**
	 * <p>构造枚举类型的代码表</p>
	 * 
	 * @param clazz
	 * @param codeName
	 * @return  List<Code> 
	 * @author: 段鹏 - duanp@neusoft.com 
	 * @date: Created on 2009-3-9 下午02:05:09
	 */
	public static List<Code> getCodeList(Class clazz, String[] codeName) {
		Code code = null;
		List<Code> codeList = new ArrayList<Code>();
		if (!clazz.isEnum())
			throw new SystemException("代码类型错误");
		try {
			Enum[] codes = (Enum[]) clazz.getMethod("values").invoke(null);
			int size = codes.length;
			for (int i = 0; i < size; i++) {
				code = new Code();
				code.setCode(codes[i].name());
				if (codeName == null || codeName.length < i + 1)
					code.setCodeName(codes[i].name());
				else
					code.setCodeName(codeName[i]);
				codeList.add(code);
			}
			return codeList;
		} catch (Exception e) {
			throw new SystemException(e);
		}
	}

}
