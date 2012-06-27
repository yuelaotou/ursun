package cn.ursun.console.app.console.bizlog.action;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import cn.ursun.console.app.console.bizlog.bizservice.BizLogBS;
import cn.ursun.console.app.domain.BizLog;
import cn.ursun.platform.core.action.WeeAction;
import cn.ursun.platform.core.dto.Pagination;
import cn.ursun.platform.core.exception.BizException;

public class ShowBizLogAC  extends WeeAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Pagination pagination = null;
	/**
	 * 业务操作对象
	 */
	private BizLogBS bizlogBS = null;

	private BizLog bizlogrow = null;
	
	private List<BizLog> bizlogList = null;
	
	private String id = null;
	
	private String flag = null;
	/**
	 * BizLog id数组对象
	 */
	private String[] logIds = null;
	
	/**
	 * 
	 * <p>跳转到日志详细信息页面</p>
	 * @return
	 * @throws BizException
	 * @author: 王敏 - wang.min@neusoft.com
	 * @data: Create on 2010-1-14 上午08:47:13
	 */
	public String forwardShowBizLogDetailPage() throws BizException{	
		bizlogrow = bizlogBS.queryBizLogById(id);
		String module = bizlogrow.getModuleLevel1();
		if(!StringUtils.isEmpty(bizlogrow.getModuleLevel2())){
			module += "->";
			module += bizlogrow.getModuleLevel2().trim();
		}
		if(!StringUtils.isEmpty(bizlogrow.getModuleLevel3())){
			module += "->";
			module += bizlogrow.getModuleLevel3().trim();
		}
		if(!StringUtils.isEmpty(bizlogrow.getModuleLevel4())){
			module += "->";
			module += bizlogrow.getModuleLevel4().trim();
		}
		if(!StringUtils.isEmpty(bizlogrow.getModuleLevel5())){
			module += "->";
			module += bizlogrow.getModuleLevel5().trim();
		}
		if(!StringUtils.isEmpty(bizlogrow.getModuleLevel6())){
			module += "->";
			module += bizlogrow.getModuleLevel6().trim();
		}
		if(!StringUtils.isEmpty(bizlogrow.getModuleLevel7())){
			module += "->";
			module += bizlogrow.getModuleLevel7().trim();
		}
		bizlogrow.setModuleLevel1(module);
		//return "showbizlogdetail";
		return JSON;
	}


	/**
	 * 
	 * <p>分页列表显示日志信息</p>
	 * @return
	 * @throws BizException
	 * @author: 王敏 - wang.min@neusoft.com
	 * @data: Create on 2010-1-13 下午12:28:15
	 */
	public String queryBizLogListJson() throws BizException{
		if (pagination == null) {
			pagination = new Pagination();
		}
		pagination.setLimit(10);// 设置默认每页显示数据条数
		bizlogList = bizlogBS.queryBizLogList(bizlogrow, pagination);
		return JSON;
	}
	
	/**
	 * 
	 * <p>分页列表显示日志信息</p>
	 * @return
	 * @throws BizException
	 * @author: 王敏 - wang.min@neusoft.com
	 * @data: Create on 2010-1-13 下午12:28:15
	 */
	public String queryBizLogList() throws BizException{
		if (pagination == null) {
			pagination = new Pagination();
		}
		pagination.setLimit(10);// 设置默认每页显示数据条数
		bizlogList = bizlogBS.queryBizLogList(bizlogrow, pagination);
		return "showbizlog";
	}

	/**
	 * 用户操作日志初始化
	 * @return
	 * @throws BizException
	 */
	public String initBizLog() throws BizException{
		if (pagination == null) {
			pagination = new Pagination();
		}
		pagination.setLimit(10);// 设置默认每页显示数据条数
		bizlogList = bizlogBS.queryBizLogList(bizlogrow, pagination);		
		return "showbizlog";
	}
	
	/**
	 * 
	 * <p>删除部分日志信息</p>
	 * @return
	 * @throws BizException
	 * @author: 王敏 - wang.min@neusoft.com
	 * @data: Create on 2010-1-14 上午08:45:57
	 */
	public String deleteBizLog() throws BizException {
		bizlogBS.deleteBizLog(logIds);
		this.setFlag("success");
		return JSON;
	}
	/**
	 * 
	 * <p>删除全部日志信息</p>
	 * @return
	 * @throws BizException
	 * @author: 王敏 - wang.min@neusoft.com
	 * @data: Create on 2010-1-14 上午08:46:21
	 */
	public String deleteAllBizLog() throws BizException {
		bizlogBS.deleteAllBizLog();
		this.setFlag("success");
		return JSON;
	}
	
	public void setBizlogBS(BizLogBS bizlogBS) {
		this.bizlogBS = bizlogBS;
	}

	public Pagination getPagination() {
		return pagination;
	}

	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}

	public BizLog getBizlogrow() {
		return bizlogrow;
	}

	public void setBizlogrow(BizLog bizlogrow) {
		this.bizlogrow = bizlogrow;
	}

	public List<BizLog> getBizlogList() {
		return bizlogList;
	}

	public void setBizlogList(List<BizLog> bizlogList) {
		this.bizlogList = bizlogList;
	}
	
	public String getId() {
		return id;
	}

	
	public void setId(String id) {
		this.id = id;
	}

	
	public String[] getLogIds() {
		return logIds;
	}

	
	public void setLogIds(String[] logIds) {
		this.logIds = logIds;
	}



	
	public String getFlag() {
		return flag;
	}



	
	public void setFlag(String flag) {
		this.flag = flag;
	}
}
