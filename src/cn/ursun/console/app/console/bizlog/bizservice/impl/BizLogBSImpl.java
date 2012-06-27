package cn.ursun.console.app.console.bizlog.bizservice.impl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import org.springframework.core.io.Resource;

import cn.ursun.console.app.console.bizlog.bizservice.BizLogBS;
import cn.ursun.console.app.console.bizlog.bizservice.ExportExcelUtil;
import cn.ursun.console.app.dao.BizLogDAO;
import cn.ursun.console.app.domain.BizLog;
import cn.ursun.platform.core.dto.Pagination;
import cn.ursun.platform.core.exception.BizException;
import cn.ursun.platform.ps.bizlog.aop.annotation.BizExecution;
import cn.ursun.platform.ps.bizservice.WeeBizService;

public class BizLogBSImpl  extends WeeBizService implements BizLogBS{
	
	BizLogDAO bizlogDAO = null;
	
	private Resource xsltPathResource = null;

	/**
	 * 
	 * <p>导出日志</p>
	 * @param bizLog
	 * @param tempPath
	 * @param num
	 * @return
	 * @throws BizException
	 * @author: 王敏 - wang.min@neusoft.com
	 * @data: Create on 2010-1-15 下午02:28:52
	 */
	@BizExecution
	public InputStream exportLog(BizLog bizLog, String tempPath, int num) throws BizException {		
		try {
			List<BizLog> bizLogList = this.queryBizLogExport(bizLog, num);
			ExportExcelUtil excel = new ExportExcelUtil();
			try {
				//生成日志excel文件
				return new FileInputStream(excel.createExcelByFile(bizLogList, tempPath));
			} catch (FileNotFoundException e) {
				throw null;
			}
		} catch (Exception e) {
			throw new BizException("0400601A", e);
		}
	}
	
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
	public List<BizLog> queryBizLogList(BizLog itemBizLog, Pagination pagination)
		throws BizException {
		// TODO Auto-generated method stub
		try {
			return bizlogDAO.queryBizLogList(itemBizLog, pagination);
		} catch (Exception e) {
			throw new BizException("0400602A", e);
		}
	}
	
	/**
	 * 
	 * <p>删除所选日志信息</p>
	 * @param logIds
	 * @throws BizException
	 * @author: 王敏 - wang.min@neusoft.com
	 * @data: Create on 2010-1-14 上午08:48:55
	 */
	@BizExecution
	public void deleteBizLog(String[] logIds) throws BizException {
		// TODO Auto-generated method stub
		try {
			bizlogDAO.deleteBizLog(logIds);
		} catch (Exception e) {
			throw new BizException("0400604A", e);
		}
	}
	
	/**
	 * 
	 * <p>删除全部日志信息</p>
	 * @throws BizException
	 * @author: 王敏 - wang.min@neusoft.com
	 * @data: Create on 2010-1-14 上午08:48:51
	 */
	@BizExecution
	public void deleteAllBizLog() throws BizException {
		// TODO Auto-generated method stub
		try {
			bizlogDAO.deleteAllBizLog();
		} catch (Exception e) {
			throw new BizException("0400604A", e);
		}
	}

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
	public BizLog queryBizLogById(String id) throws BizException {
		// TODO Auto-generated method stub
		try {
			return bizlogDAO.queryBizLogById(id);
		} catch (Exception e) {
			throw new BizException("0400605A", e);
		}
	}

	/**
	 * 
	 * <p>获得导出的excle日志数据</p>
	 * @param bizLog
	 * @return
	 * @author: 王敏 - wang.min@neusoft.com
	 * @throws BizException 
	 * @data: Create on 2010-1-14 上午09:24:58
	 */
	@BizExecution
	public List<BizLog> queryBizLogExport(BizLog bizLog, int num) throws BizException{
		// TODO Auto-generated method stub
		try {
			return bizlogDAO.queryBizLogExport(bizLog, num);
		} catch (Exception e) {
			throw new BizException("0400606A", e);
		}
	}

	public BizLogDAO getBizlogDAO() {
		return bizlogDAO;
	}

	public void setBizlogDAO(BizLogDAO bizlogDAO) {
		this.bizlogDAO = bizlogDAO;
	}

	
	public Resource getXsltPathResource() {
		return xsltPathResource;
	}

	
	public void setXsltPathResource(Resource xsltPathResource) {
		this.xsltPathResource = xsltPathResource;
	}

	
}
