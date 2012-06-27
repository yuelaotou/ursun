package cn.ursun.demo.app.demo.bizservice.impl;

import java.util.List;

import cn.ursun.demo.app.dao.DemoDAO;
import cn.ursun.demo.app.demo.bizservice.DemoBS;
import cn.ursun.demo.app.domain.Demo;
import cn.ursun.platform.core.aop.annotation.MethodTimeDebugger;
import cn.ursun.platform.core.dto.Pagination;
import cn.ursun.platform.core.exception.BizException;
import cn.ursun.platform.ps.bizlog.IBizLog.OperationType;
import cn.ursun.platform.ps.bizlog.aop.annotation.BizExecution;
import cn.ursun.platform.ps.bizservice.WeeBizService;

public class DemoBSImpl extends WeeBizService implements DemoBS {

	DemoDAO demoDAO = null;

	/**
	 * <p>
	 * 查询雇员列表 
	 * </p>
	 */
	@MethodTimeDebugger(type = "EXECUTION")
	@BizExecution(type = OperationType.USE)
	public List<Demo> queryEmployeeList(Pagination pagination) throws BizException {
		if (pagination.getTotalCount() == 0) {
			pagination.setTotalCount(demoDAO.queryEmployeeCount());
		}
		log("导入检索式", "导入失败");
		return demoDAO.queryEmployeeList(pagination);
	}

	/**
	 * <p>
	 * 查询雇员列表 
	 * </p>
	 */
	@MethodTimeDebugger(type = "EXECUTION")
	@BizExecution(type = OperationType.USE)
	public List<Demo> queryEmployeeListByEmpno(Pagination pagination, String empno) throws BizException {

		return demoDAO.queryEmployeeListByEmpno(pagination, empno);
	}

	public DemoDAO getDemoDAO() {
		return demoDAO;
	}

	public void setDemoDAO(DemoDAO demoDAO) {
		this.demoDAO = demoDAO;
	}

}
