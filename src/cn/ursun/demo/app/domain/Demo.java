package cn.ursun.demo.app.domain;

import java.sql.Date;

import cn.ursun.platform.core.domain.WeeDomain;

/**
 * <p>
 * 单位实体
 * </p>
 * 
 * @author 杨光 - admin@ursun.cn
 * @version 1.0
 */
public class Demo extends WeeDomain {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String empno = null;

	private String ename = null;

	private String job = null;

	private String mgr = null;

	private Date hiredate = null;

	private String sal = null;

	private String comm = null;

	private String deptno = null;

	public String getComm() {
		return comm;
	}

	public void setComm(String comm) {
		this.comm = comm;
	}

	public String getDeptno() {
		return deptno;
	}

	public void setDeptno(String deptno) {
		this.deptno = deptno;
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

	public Date getHiredate() {
		return hiredate;
	}

	public void setHiredate(Date hiredate) {
		this.hiredate = hiredate;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public String getMgr() {
		return mgr;
	}

	public void setMgr(String mgr) {
		this.mgr = mgr;
	}

	public String getSal() {
		return sal;
	}

	public void setSal(String sal) {
		this.sal = sal;
	}

	

}
