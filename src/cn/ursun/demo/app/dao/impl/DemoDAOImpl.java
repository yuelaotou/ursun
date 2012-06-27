package cn.ursun.demo.app.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.RowMapper;

import cn.ursun.demo.app.dao.DemoDAO;
import cn.ursun.demo.app.domain.Demo;
import cn.ursun.platform.core.dto.Pagination;
import cn.ursun.platform.core.exception.BizException;
import cn.ursun.platform.core.jdbc.WeeJdbcDAO;

public class DemoDAOImpl extends WeeJdbcDAO implements DemoDAO {

	private static final String SQL_QUERY_EMPLOYEE_COUNT = "SELECT count(1) FROM EMP";

	private static final String SQL_QUERY_EMPLOYEE_INFO = "SELECT EMPNO, ENAME, JOB,  MGR, HIREDATE, SAL,  COMM, DEPTNO FROM EMP  ORDER BY DEPTNO";

	private static final String SQL_QUERY_EMPLOYEE = "SELECT EMPNO, ENAME, JOB,  MGR, HIREDATE, SAL,  COMM, DEPTNO FROM EMP where empno=? ORDER BY DEPTNO";

	private static class EmployeeRowMapper implements RowMapper {

		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			Demo employee = new Demo();
			employee.setEmpno(rs.getString("EMPNO"));
			employee.setEname(rs.getString("ENAME"));
			employee.setJob(rs.getString("JOB"));
			employee.setMgr(rs.getString("MGR"));
			employee.setHiredate(rs.getDate("HIREDATE"));
			employee.setSal(rs.getString("SAL"));
			employee.setComm(rs.getString("COMM"));
			employee.setDeptno(rs.getString("DEPTNO"));
			return employee;
		}
	}

	/**
	 * <p>
	 * 查询雇员总数 
	 * </p>
	 */
	// @MethodTimeDebugger(type = "DAO")
	public int queryEmployeeCount() {
		return this.queryForInt(SQL_QUERY_EMPLOYEE_COUNT);
	}

	/**
	 * <p>
	 * 查询雇员列表
	 * </p>
	 */
	@SuppressWarnings("unchecked")
	public List<Demo> queryEmployeeList(Pagination pagination) throws BizException {
		return query(SQL_QUERY_EMPLOYEE_INFO, new EmployeeRowMapper(), pagination);
	}

	/**
	 * <p>
	 * 查询雇员列表
	 * </p>
	 */
	@SuppressWarnings("unchecked")
	public List<Demo> queryEmployeeListByEmpno(Pagination pagination, String empno) throws BizException {
		if (StringUtils.isEmpty(empno)) {
			return query(SQL_QUERY_EMPLOYEE_INFO, new EmployeeRowMapper(), pagination);
		} else {
			return query(SQL_QUERY_EMPLOYEE, new Object[] { empno }, new EmployeeRowMapper(), pagination);
		}
	}

}
