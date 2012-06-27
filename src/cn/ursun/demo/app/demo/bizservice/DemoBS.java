package cn.ursun.demo.app.demo.bizservice;

import java.util.List;

import cn.ursun.demo.app.domain.Demo;
import cn.ursun.platform.core.aop.annotation.MethodTimeDebugger;
import cn.ursun.platform.core.dto.Pagination;
import cn.ursun.platform.core.exception.BizException;
import cn.ursun.platform.ps.bizlog.IBizLog.OperationType;
import cn.ursun.platform.ps.bizlog.aop.annotation.BizExecution;

public interface DemoBS {

	/**
	 * <p>
	 * 查询雇员列表
	 * </p>
	 */
	@MethodTimeDebugger(type = "EXECUTION")
	@BizExecution(type = OperationType.USE)
	public List<Demo> queryEmployeeList(Pagination pagination) throws BizException;

	/**
	 * <p>
	 * 查询雇员列表
	 * </p>
	 */
	@MethodTimeDebugger(type = "EXECUTION")
	@BizExecution(type = OperationType.USE)
	public List<Demo> queryEmployeeListByEmpno(Pagination pagination, String empno) throws BizException;

}
