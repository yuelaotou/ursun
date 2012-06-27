package cn.ursun.console.app.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.RowMapper;

import cn.ursun.console.app.dao.BizLogDAO;
import cn.ursun.console.app.domain.BizLog;
import cn.ursun.platform.core.dto.Pagination;
import cn.ursun.platform.core.exception.BizException;
import cn.ursun.platform.core.jdbc.WeeJdbcDAO;
import cn.ursun.platform.ps.bizlog.IBizLog;

public class BizLogDAOImpl extends WeeJdbcDAO implements BizLogDAO{

	private static final String INSERT_BIZ_LOG = "INSERT INTO PS_SYS_BIZLOG (ID, OPERATION_DATE," // OPERATION_TYPE,"
			+ " OPERATOR, OPERATOR_NAME, SEX, DEPT, IP, BIZ_CODE, FUNC_CODE,IS_ANONYMOUS,ROLE_NAME,LOGIN_NAME, MODULE_LEVEL_1, MODULE_LEVEL_2, MODULE_LEVEL_3,"
			+ " MODULE_LEVEL_4, MODULE_LEVEL_5, MODULE_LEVEL_6, MODULE_LEVEL_7)"
			+ " VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)";

	private static final String INSERT_BIZ_LOG_DETAIL = "INSERT INTO PS_SYS_BIZLOG_DETAIL ( ID, DETAIL ) VALUES ( ? , ? )";

	
	  
	 //获得日志信息列表
	private static final String SQL_QUERY_BIZLOG = " SELECT ID, OPERATION_DATE, OPERATION_TYPE, " +
			" OPERATOR_NAME, SEX, DEPT, IP, LOGIN_NAME, OPERATOR, " +
			" ROLE_NAME " +
			" FROM PS_SYS_BIZLOG p" +
			" WHERE 1=1";
	
	private static final String QUERY_BASIC_MODULE_NAME = "select c.MODULE_NAME from PS_SYS_BIZLOG_CODE c where c.module_code=";
	
	//联合查询日志信息和detail信息
	private static final String SQL_QUERY_BIZLOG_DETAIL =  "SELECT p.ID, OPERATION_DATE, OPERATION_TYPE, " +
			" OPERATOR_NAME, SEX, DEPT, IP, LOGIN_NAME, OPERATOR, " +
			"(" + QUERY_BASIC_MODULE_NAME +
			" MODULE_LEVEL_1) MODULE_NAME1," +
			"(" + QUERY_BASIC_MODULE_NAME +
			" MODULE_LEVEL_2) MODULE_NAME2, " +
			"(" + QUERY_BASIC_MODULE_NAME +
			" MODULE_LEVEL_3) MODULE_NAME3," +
			"(" + QUERY_BASIC_MODULE_NAME +
			" MODULE_LEVEL_4) MODULE_NAME4," +
			"(" + QUERY_BASIC_MODULE_NAME +
			" MODULE_LEVEL_5) MODULE_NAME5," +
			"(" + QUERY_BASIC_MODULE_NAME +
			" MODULE_LEVEL_6) MODULE_NAME6," +
			"(" + QUERY_BASIC_MODULE_NAME +
			" MODULE_LEVEL_7) MODULE_NAME7, " +
			" ROLE_NAME, DETAIL" +
			" FROM PS_SYS_BIZLOG p, PS_SYS_BIZLOG_DETAIL d" + 
			" WHERE p.ID = d.ID ";

	
	//查询某条日志详细信息
	private static final String QUERY_BIZLOG_BY_ID_SQL = SQL_QUERY_BIZLOG_DETAIL + " and p.ID = ?";
	
	//删除某些日志信息
	private static final String DELETE_BIZLOG_BY_LOGID = "DELETE FROM PS_SYS_BIZLOG where ID = ?";
	
	//删除某些日志详细内容
	private static final String DELETE_BIZLOG_DETAIL_BY_LOGID = "delete from PS_SYS_BIZLOG_DETAIL where ID = ?";
	
	//删除全部日志信息
	private static final String DELETE_ALL_BIZLOG = "delete from PS_SYS_BIZLOG where 1=1";
	
	//删除全部日志详细内容
	private static final String DELETE_ALL_BIZLOG_DETAIL = "delete from PS_SYS_BIZLOG_DETAIL where 1=1";
	
	private static class BizLogRowMaper implements RowMapper {

		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			BizLog bizLog = new BizLog();
			bizLog.setId(rs.getString("ID"));
			bizLog.setOperationDate(rs.getTimestamp("OPERATION_DATE"));
			bizLog.setOperationType(rs.getString("OPERATION_TYPE"));
			bizLog.setOperator(rs.getString("OPERATOR"));
			bizLog.setOperatorName(rs.getString("OPERATOR_NAME"));
			bizLog.setSex(rs.getString("SEX") == null ? " " : rs.getString("SEX"));
			bizLog.setDept(rs.getString("DEPT") == null ? " " : rs.getString("DEPT"));
			bizLog.setIp(rs.getString("IP"));
			bizLog.setRoleName(rs.getString("ROLE_NAME"));
			bizLog.setLoginName(rs.getString("LOGIN_NAME"));
			return bizLog;
		}
	}
	private static class BizLogDetailRowMaper implements RowMapper {

		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			BizLog bizLog = new BizLog();
			bizLog.setId(rs.getString("ID"));
			bizLog.setOperationDate(rs.getTimestamp("OPERATION_DATE"));
			bizLog.setOperationType(rs.getString("OPERATION_TYPE"));
			bizLog.setOperator(rs.getString("OPERATOR"));
			bizLog.setOperatorName(rs.getString("OPERATOR_NAME"));
			bizLog.setSex(rs.getString("SEX") == null ? " " : rs.getString("SEX"));
			bizLog.setDept(rs.getString("DEPT") == null ? " " : rs.getString("DEPT"));
			bizLog.setModuleLevel1(rs.getString("MODULE_NAME1") == null ? "" : rs.getString("MODULE_NAME1"));
			bizLog.setModuleLevel2(rs.getString("MODULE_NAME2") == null ? "" : rs.getString("MODULE_NAME2"));
			bizLog.setModuleLevel3(rs.getString("MODULE_NAME3") == null ? "" : rs.getString("MODULE_NAME3"));
			bizLog.setModuleLevel4(rs.getString("MODULE_NAME4") == null ? "" : rs.getString("MODULE_NAME4"));
			bizLog.setModuleLevel5(rs.getString("MODULE_NAME5") == null ? "" : rs.getString("MODULE_NAME5"));
			bizLog.setModuleLevel6(rs.getString("MODULE_NAME6") == null ? "" : rs.getString("MODULE_NAME6"));
			bizLog.setModuleLevel7(rs.getString("MODULE_NAME7") == null ? "" : rs.getString("MODULE_NAME7"));
			bizLog.setIp(rs.getString("IP"));
			bizLog.setRoleName(rs.getString("ROLE_NAME"));
			bizLog.setLoginName(rs.getString("LOGIN_NAME"));
			bizLog.setDetail(rs.getString("DETAIL"));
		//	bizLog.setDetail(getLobHandler().getClobAsString(rs, "DETAIL"));
			return bizLog;
		}
	}

	/**
	 * <p>记录日志</p>
	 * 
	 * @see cn.ursun.platform.ps.bizlog.dao.BizLogDAO#records(cn.ursun.platform.ps.bizlog.BizLog)
	 * @author: 段鹏 - duanp@neusoft.com 
	 * @date: Created on Nov 25, 2008 10:51:55 PM
	 */
	public void records(IBizLog log) {
		String id = cn.ursun.platform.core.util.IDGenerator.generateId();
		List params = new ArrayList();
		params.addAll(Arrays.asList(new Object[] { id,
				log.getOperationDate(), // log.getOperationType(),
				log.getOperator(), log.getOperatorName(), log.getSex(), log.getDepartment(), log.getAccessIP(),
				log.getBizCode(), log.getFuncCode(), log.isAnonymousUser() ? 1 : 0, log.getRoleName(), log.getLoginName()

		}));
		int cnt = log.getModuleLevelCount();
		for (int i = 0; i < 7; i++) {
			if (i < cnt)
				params.add(log.getModuleLevel(i));
			else
				params.add("");
		}
		this.update(INSERT_BIZ_LOG, params.toArray());
		this.update(INSERT_BIZ_LOG_DETAIL, new Object[] { id, log.getXmlData() });
	}
	/**
	 * 
	 * <p>用户操作日志列表分页显示（包括查询）</p>
	 * @param itemBizLog
	 * @param pagination
	 * @return
	 * @author: 王敏 - wang.min@neusoft.com
	 * @data: Create on 2010-1-13 上午11:25:12
	 */
	@SuppressWarnings("unchecked")
	public List<BizLog> queryBizLogList(BizLog itemBizLog, Pagination pagination) {
		// TODO Auto-generated method stub
		Map map = createQuery(itemBizLog);
		String querySql = SQL_QUERY_BIZLOG + map.get("sql") + " order by OPERATION_DATE desc";
		List<BizLog> bizLogList = this.query(querySql, new BizLogRowMaper(),pagination);
		return bizLogList;
	}

	/**
	 * 
	 * <p>根据查询条件整合查询SQL</p>
	 * @param itemBizLog
	 * @return
	 * @author: 王敏 - wang.min@neusoft.com
	 * @data: Create on 2010-1-13 下午04:20:20
	 */
	private Map<String, Object> createQuery(BizLog itemBizLog) {
		StringBuffer queryBizLog = new StringBuffer();
		if(itemBizLog!=null){
			if(itemBizLog.getLoginName() != null){
				String username = itemBizLog.getLoginName().trim();	
				if (!StringUtils.isEmpty(username)){//根据登录用户名
					queryBizLog.append(" and LOWER(LOGIN_NAME) like '%");
					queryBizLog.append(convertSQLParameter(username.trim().toLowerCase()));
					queryBizLog.append("%' ESCAPE '\\'");
				}
			}					
			if (itemBizLog.getIp() != null){//根据登录IP
				String ip = itemBizLog.getIp().trim();
				if(!StringUtils.isEmpty(ip)){
					queryBizLog.append(" and IP like '%");
					queryBizLog.append(convertSQLParameter(ip));
					queryBizLog.append("%' ESCAPE '\\'");
				}
			}
			if (itemBizLog.getRoleName() != null){
				String rolename = itemBizLog.getRoleName().trim();
				if (!StringUtils.isEmpty(rolename)){//根据登录用户名
					queryBizLog.append(" and LOWER(ROLE_NAME) like '%");
					queryBizLog.append(convertSQLParameter(rolename.trim().toLowerCase()));
					queryBizLog.append("%' ESCAPE '\\'");
				}
			}			
			if (itemBizLog.getStartDate() != null) {
				String startdate = itemBizLog.getStartDate().trim();
				if (!StringUtils.isEmpty(startdate)){
					queryBizLog.append(" and OPERATION_DATE >= ");
					queryBizLog.append("to_date('");
					queryBizLog.append(convertSQLParameter(startdate));
					queryBizLog.append("','yyyy-mm-dd hh:mi:ss')");
				}
			}
			if (itemBizLog.getEndDate() != null) {
				String enddate = itemBizLog.getEndDate().trim();
				if (!StringUtils.isEmpty(enddate)){
					queryBizLog.append(" and OPERATION_DATE <= ");
					queryBizLog.append("to_date('");
					queryBizLog.append(convertSQLParameter(enddate));
					queryBizLog.append("','yyyy-mm-dd  hh:mi:ss')+1");
				}
			}
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sql", queryBizLog.toString());
		return map;
	}
	
	/**
	 * 
	 * <p>批量删除日志</p>
	 * @param logIds
	 * @throws BizException
	 * @author: 王敏 - wang.min@neusoft.com
	 * @data: Create on 2010-1-13 上午11:25:24
	 */
	public void deleteBizLog(String[] logIds) throws BizException {
		// TODO Auto-generated method stub
		List<Object[]> params = new ArrayList<Object[]>();
		for (int i = 0; i < logIds.length; i++) {
			params.add(new Object[] { logIds[i] });
		}
		batchUpdate(DELETE_BIZLOG_BY_LOGID, params);
		batchUpdate(DELETE_BIZLOG_DETAIL_BY_LOGID, params);
	}

	/**
	 * 
	 * <p>删除全部日志</p>
	 * @throws BizException
	 * @author: 王敏 - wang.min@neusoft.com
	 * @data: Create on 2010-1-13 上午11:25:31
	 */
	public void deleteAllBizLog() throws BizException {
		// TODO Auto-generated method stub
		this.update(DELETE_ALL_BIZLOG);
		this.update(DELETE_ALL_BIZLOG_DETAIL);
	}
	
	/**
	 * 
	 * <p>根据日志id查询某条日志详细信息</p>
	 * @param id
	 * @return
	 * @throws BizException
	 * @author: 王敏 - wang.min@neusoft.com
	 * @data: Create on 2010-1-13 上午11:25:37
	 */
	public BizLog queryBizLogById(String id) throws BizException {
		return (BizLog) query(QUERY_BIZLOG_BY_ID_SQL,new Object[] { id }, new BizLogDetailRowMaper()).get(0);
	}

	/**
	 * 
	 * <p>获得导出excel的日志数据</p>
	 * @param bizLog
	 * @param num
	 * @return
	 * @author: 王敏 - wang.min@neusoft.com
	 * @data: Create on 2010-1-13 下午01:49:26
	 */
	@SuppressWarnings("unchecked")
	public List<BizLog> queryBizLogExport(BizLog bizLog, int num) {
		Map map = createQuery(bizLog);
		String querySql = SQL_QUERY_BIZLOG_DETAIL + map.get("sql") + " and rownum <= " + num;
		List<BizLog> bizLogList = this.query(querySql, new BizLogDetailRowMaper());
		return bizLogList;
	}
}
