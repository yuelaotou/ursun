package cn.ursun.console.app.console.bizlog.bizservice;

import java.io.InputStream;
import java.util.List;

import cn.ursun.console.app.domain.BizLog;
import cn.ursun.platform.core.dto.Pagination;
import cn.ursun.platform.core.exception.BizException;
import cn.ursun.platform.ps.bizlog.aop.annotation.BizExecution;

public interface BizLogBS {

	/**
	 * 
	 * <p>列表显示日志信息（包括查询条件，带分页）</p>
	 * @param itemBizLog
	 * @param pagination
	 * @return
	 * @throws BizException
	 * @author: 王敏 - wang.min@neusoft.com
	 * @data: Create on 2010-1-14 上午08:49:01
	 */
	@BizExecution
	public List<BizLog> queryBizLogList(BizLog itemBizLog, Pagination pagination) throws BizException;
	
	/**
	 * 
	 * <p>删除所选日志信息</p>
	 * @param logIds
	 * @throws BizException
	 * @author: 王敏 - wang.min@neusoft.com
	 * @data: Create on 2010-1-14 上午08:48:55
	 */
	@BizExecution
	public void deleteBizLog(String[] logIds)throws BizException;
	
	/**
	 * 
	 * <p>删除全部日志信息</p>
	 * @throws BizException
	 * @author: 王敏 - wang.min@neusoft.com
	 * @data: Create on 2010-1-14 上午08:48:51
	 */
	@BizExecution
	public void deleteAllBizLog()throws BizException;
	
	/**
	 * 
	 * <p>根据日志id查询该条日志所有详细信息</p>
	 * @param id
	 * @return
	 * @throws BizException
	 * @author: 王敏 - wang.min@neusoft.com
	 * @data: Create on 2010-1-14 上午08:48:45
	 */
	@BizExecution
	public BizLog queryBizLogById(String id)throws BizException;
	
	/**
	 * 
	 * <p>获得导出excel的日志数据</p>
	 * @param bizLog
	 * @return
	 * @author: 王敏 - wang.min@neusoft.com
	 * @throws BizException 
	 * @data: Create on 2010-1-13 下午01:49:26
	 */
	@BizExecution
	public List<BizLog> queryBizLogExport(BizLog bizLog, int num) throws BizException;
	
	/**
	 * <p>导出日志</p>
	 * 
	 * @param date
	 * @return
	 * @author: 陈乃明 - chennm@neusoft.com
	 * @throws BizException 
	 * @date: Created on Feb 23, 2009 4:59:33 PM
	 */
	@BizExecution
	public InputStream exportLog(BizLog bizLog, String tempPath, int num) throws BizException;
}
