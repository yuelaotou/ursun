package cn.ursun.demo.app.dao;

import java.util.List;

import cn.ursun.demo.app.domain.Demo;
import cn.ursun.platform.core.dto.Pagination;
import cn.ursun.platform.core.exception.BizException;

public interface DemoDAO {

	public int queryEmployeeCount() throws BizException;

	public List<Demo> queryEmployeeList(Pagination pagination) throws BizException;

	public List<Demo> queryEmployeeListByEmpno(Pagination pagination, String empno) throws BizException;

}
