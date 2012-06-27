package cn.ursun.platform.ps.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import cn.ursun.platform.core.jdbc.WeeJdbcDAO;
import cn.ursun.platform.core.exception.BizException;
import cn.ursun.platform.ps.dao.CodeListDAO;
import cn.ursun.platform.ps.domain.Code;

/**
 * 
 * <p>[获取内存代码表dao层实现类]</p>
 *
 * @author 邹甲乐 - zoujiale@neusoft.com
 * @version 1.0 Created on Nov 7, 2008 2:25:32 AM
 */
public class CodeListDAOImp extends WeeJdbcDAO implements CodeListDAO {

	/**
	 * 
	 * <p>[获取代码列表]</p>
	 * 
	 * @see cn.ursun.platform.ps.dao.CodeListDAO#queryCodeList(java.lang.String)
	 * @author: 邹甲乐 - zoujiale@neusoft.com 
	 * @date: Created on Nov 7, 2008 2:25:40 AM
	 */
	public List<Code> queryCodeList(String codeType) throws BizException {
		String sql = "SELECT s.CODE_NAME,s.CODE_VALUE,c.CODE_TYPE,c.TYPE_NAME FROM PS_CODE c,PS_CODE_SUB s WHERE c.ID = s.ID AND c.CODE_TYPE='"
				+ codeType + "' order by s.SORT_ORDER, s.CODE_VALUE";
		List<Code> codeList = this.query(sql, new CodeRowMaper());
		return codeList;
	}

	/**
	 * 
	 * <p>[静态类]</p>
	 *
	 * @author 邹甲乐 - zoujiale@neusoft.com
	 * @version 1.0 Created on Nov 7, 2008 2:25:36 AM
	 */
	static class CodeRowMaper implements RowMapper {

		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			Code code = new Code();
			code.setType(rs.getString("CODE_TYPE"));
			code.setTypeName(rs.getString("TYPE_NAME"));
			code.setCode(rs.getString("CODE_NAME"));
			code.setCodeName(rs.getString("CODE_VALUE"));
			return code;
		}
	}
}
