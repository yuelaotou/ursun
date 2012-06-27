package cn.ursun.console.app.dao;

import java.util.List;

import cn.ursun.console.app.domain.BizLog;
import cn.ursun.platform.core.dto.Pagination;
import cn.ursun.platform.core.exception.BizException;
import cn.ursun.platform.core.exception.SystemException;
import cn.ursun.platform.ps.bizlog.IBizLog;

public interface BizLogDAO {

	/**
	 * <p>记录日志DAO</p>
	 * 
	 * @param log
	 * @throws SystemException
	 * @author: 段鹏 - duanp@neusoft.com 
	 * @date: Created on Nov 26, 2008 3:40:03 PM
	 */
	public void records(IBizLog log) throws SystemException;
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
	public List<BizLog> queryBizLogList(BizLog itemBizLog, Pagination pagination);
	
	/**
	 * 
	 * <p>删除所选日志信息</p>
	 * @param logIds
	 * @throws BizException
	 * @author: 王敏 - wang.min@neusoft.com
	 * @data: Create on 2010-1-14 上午08:48:55
	 */
	public void deleteBizLog(String[] logIds) throws BizException;
	
	/**
	 * 
	 * <p>删除全部日志信息</p>
	 * @throws BizException
	 * @author: 王敏 - wang.min@neusoft.com
	 * @data: Create on 2010-1-14 上午08:48:51
	 */
	public void deleteAllBizLog() throws BizException;
	
	/**
	 * 
	 * <p>根据日志id查询该条日志所有详细信息</p>
	 * @param id
	 * @return
	 * @throws BizException
	 * @author: 王敏 - wang.min@neusoft.com
	 * @data: Create on 2010-1-14 上午08:48:45
	 */
	public BizLog queryBizLogById(String id)throws BizException;
	
	/**
	 * 
	 * <p>获得导出excel的日志数据</p>
	 * @param bizLog
	 * @return
	 * @author: 王敏 - wang.min@neusoft.com
	 * @data: Create on 2010-1-13 下午01:49:26
	 */
	public List<BizLog> queryBizLogExport(BizLog bizLog, int num);
}
