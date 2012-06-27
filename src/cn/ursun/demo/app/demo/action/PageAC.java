package cn.ursun.demo.app.demo.action;

import java.util.List;

import cn.ursun.demo.app.demo.bizservice.DemoBS;
import cn.ursun.demo.app.domain.Demo;
import cn.ursun.platform.core.action.WeeAction;
import cn.ursun.platform.core.dto.Pagination;
import cn.ursun.platform.core.exception.BizException;

public class PageAC extends WeeAction {

	private DemoBS demoBS = null;

	/**
	 * 雇员的list 
	 */
	private List<Demo> employeeList;

	private String empno;

	private String ename;

	private String job;

	private String gridType;
	
	private Pagination pagination = null;

	public Pagination getPagination() {
		return pagination;
	}

	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}

	/**
	 * <p>
	 * 查询雇员列表
	 * </p>
	 */
	public String forwardMainPage() throws BizException {
		if (pagination == null) {
			pagination = new Pagination();
		}
		pagination.setLimit(7);
		employeeList = demoBS.queryEmployeeList(pagination);
		
		return "page";
	}

	/**
	 * <p>
	 * 查询雇员列表
	 * </p>
	 */
	public String queryEmployeeList() throws BizException {
		if (pagination == null) {
			pagination = new Pagination();
		}
		pagination.setLimit(7);
		employeeList = demoBS.queryEmployeeListByEmpno(pagination, empno);
		
		return "pageSub";
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
		pagination.setLimit(7);
		employeeList = demoBS.queryEmployeeListByEmpno(pagination, empno);
		
		return "page";
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

	public String getGridType() {
		return gridType;
	}

	public void setGridType(String type) {
		this.gridType = type;
	}

}
