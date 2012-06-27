package cn.ursun.demo.app.demo.action;

import java.util.List;

import cn.ursun.demo.app.demo.bizservice.DemoBS;
import cn.ursun.demo.app.domain.Demo;
import cn.ursun.platform.core.action.WeeAction;
import cn.ursun.platform.core.dto.Pagination;
import cn.ursun.platform.core.exception.BizException;

public class DemoShowAC extends WeeAction {

	private DemoBS demoBS = null;

	/**
	 * 雇员的list 
	 */
	private List<Demo> employeeList;

	private String empno;

	private String ename;

	private String job;

	private Pagination pagination = null;

	/**
	 * <p>
	 * 查询雇员列表
	 * </p>
	 */
	public String queryEmployeeList() throws BizException {
		if (pagination == null) {
			pagination = new Pagination();
		}
		employeeList = demoBS.queryEmployeeList(pagination);
		return "grid";

	}

	/**
	 * <p>
	 * 查询雇员列表
	 * </p>
	 */
	public String queryEmployeeListJson() throws BizException {
		if (pagination == null) {
			pagination = new Pagination();
		}
		employeeList = demoBS.queryEmployeeListByEmpno(pagination, empno);
		return JSON;
	}

	/**
	 * <p>
	 * 查询雇员列表
	 * </p>
	 */
	public String queryEmployeeByEmpno() throws BizException {
		if (pagination == null) {
			pagination = new Pagination();
		}
		employeeList = demoBS.queryEmployeeListByEmpno(pagination, empno);
		return "grid";

	}

	public List<Demo> getEmployeeList() throws BizException {
		return employeeList;
	}

	public void setEmployeeList(List<Demo> employeeList) {
		this.employeeList = employeeList;
	}

	public void setDemoBS(DemoBS demoBS) {
		this.demoBS = demoBS;
	}

	public String getEmpno() {
		return empno;
	}

	public void setEmpno(String empno) {
		this.empno = empno;
	}

	public String getEname() {
		return ename;
	}

	public void setEname(String ename) {
		this.ename = ename;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public Pagination getPagination() {
		return pagination;
	}

	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}

}
