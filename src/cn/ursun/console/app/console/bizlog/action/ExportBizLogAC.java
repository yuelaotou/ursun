package cn.ursun.console.app.console.bizlog.action;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import cn.ursun.console.app.console.bizlog.bizservice.BizLogBS;
import cn.ursun.console.app.domain.BizLog;
import cn.ursun.platform.core.action.WeeUploadFileAction;

public class ExportBizLogAC extends WeeUploadFileAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final int QUERY_BIZLOG_NUM = 10000;
	
	/**
	 * at： WEB-INF/conf/prosearch/sysmgr/action-app-context.xml <property name="tempPath" value="D:/xmlcache/excel_export/" />
	 * 清除临时文件at WEB-INF/conf/scheduler-app-context.xml <value>D:/xmlcache/excel_export</value>
	 */
	private String tempPath = null;

	private List<BizLog> bizLogList = null;

	private BizLogBS bizlogBS = null;
	
	private BizLog bizlogrow = null;
	
	private String downFileName="excel";
	

	/**
	 * 
	 * <p>日志导出</p>
	 * @return
	 * @throws Exception
	 * @author: 王敏 - wang.min@neusoft.com
	 * @data: Create on 2010-1-14 下午05:02:19
	 */
	public String exportLog() throws Exception {
		// 使用文件的方式
		//excelStream = bizlogBS.exportLog(bizlogrow, tempPath);
		return "excel";
	}

	public BizLog getBizlogrow() {
		return bizlogrow;
	}

	
	public void setBizlogrow(BizLog bizlogrow) {
		this.bizlogrow = bizlogrow;
	}

	public void setBizlogBS(BizLogBS bizlogBS) {
		this.bizlogBS = bizlogBS;
	}

	public List<BizLog> getBizLogList() {
		return bizLogList;
	}

	public void setBizLogList(List<BizLog> bizLogList) {
		this.bizLogList = bizLogList;
	}


	public InputStream getExcelStream() throws Exception{
		return bizlogBS.exportLog(bizlogrow, tempPath, QUERY_BIZLOG_NUM);
	}

	public String getTempPath() {
		return tempPath;
	}

	public void setTempPath(String tempPath) {
		this.tempPath = tempPath;
	}

	
	public String getDownFileName(){
		SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyyMMdd");
		Date date = new Date();
		downFileName=downFileName+bartDateFormat.format(date)+".xls";
		return downFileName;
	}

	
	public void setDownFileName(String downFileName) {
		this.downFileName = downFileName;
	}

}
