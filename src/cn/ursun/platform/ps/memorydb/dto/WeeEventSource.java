/**
 * 文件名：WeeEventSource.java
 * 
 * 创建人：段鹏 - duanp@neusoft.com
 * 
 * 创建时间：2009-6-23 上午11:09:53
 * 
 * 版权所有：东软集团股份有限公司
 */
package cn.ursun.platform.ps.memorydb.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.ursun.platform.core.exception.SystemException;
import cn.ursun.platform.ps.memorydb.dto.UpdateRecord.UpdateType;

/**
 * <p>[描述信息：说明类的基本功能]</p>
 *
 * @author 段鹏- duanp@neusoft.com
 * @version 1.0 Created on 2009-6-23 上午11:09:54
 */
public class WeeEventSource {

	private String contentType = null;

	private List<UpdateRecord> records = null;

	/**
	 *
	 * @return 
	 */
	public String getContentType() {
		return contentType;
	}

	/**
	 *
	 * @param contentType
	 */
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	/**
	 *
	 * @return 
	 */
	public List<UpdateRecord> getRecords() {
		return records;
	}

	/**
	 *
	 * @param records
	 */
	public void setRecords(List<UpdateRecord> records) {
		this.records = records;
	}

	/**
	 * <p>添加更新记录</p>
	 * 
	 * @param record
	 * @author: 段鹏 - duanp@neusoft.com 
	 * @date: Created on 2009-6-23 上午11:51:57
	 */
	public void addRecord(UpdateRecord record) {
		if (records == null)
			records = new ArrayList<UpdateRecord>();
		records.add(record);
	}

	/**
	 * <p>添加更新记录</p>
	 * 
	 * @param tableName
	 * @param pkName
	 * @param pkValue
	 * @param type
	 * @param updateDate
	 * @author: 段鹏 - duanp@neusoft.com 
	 * @date: Created on 2009-6-23 上午11:51:59
	 */
	public void addRecord(String tableName, String pkName, String pkValue, UpdateType type, Date updateDate) {
		addRecord(new UpdateRecord(tableName, pkName, pkValue, type, updateDate));
	}

	/**
	 * <p>添加更新记录</p>
	 * 
	 * @param tableName
	 * @param pkName
	 * @param pkValue
	 * @param type
	 * @param updateDate
	 * @author: 段鹏 - duanp@neusoft.com 
	 * @date: Created on 2009-6-23 上午11:51:59
	 */
	public void addRecord(String tableName, String pkName, String pkValue, UpdateType type) {
		addRecord(new UpdateRecord(tableName, pkName, pkValue, type, null));
	}

	/**
	 * <p>添加更新记录</p>
	 * 
	 * @param tableName
	 * @param pkName
	 * @param pkValue
	 * @param type
	 * @param updateDate
	 * @author: 段鹏 - duanp@neusoft.com 
	 * @date: Created on 2009-6-23 上午11:51:59
	 */
	public void addRecord(String tableName, String[] pkName, String[] pkValue, UpdateType type) {
		if (pkName.length != pkValue.length)
			throw new SystemException("PrimaryKey count doesn't equals PrimaryKey Values ");
		addRecord(new UpdateRecord(tableName, makePrimaryKeyStr(pkName), makePrimaryKeyValuesStr(pkValue), type, null));
	}

	private String makePrimaryKeyStr(String[] pks) {
		StringBuffer sb = new StringBuffer("");
		for (String pk : pks) {
			sb.append(pk).append("||'|'||");
		}
		if (sb.length() > 1)
			return sb.substring(0, sb.length() - 7);
		return sb.toString();
	}

	private String makePrimaryKeyValuesStr(String[] pkvs) {
		StringBuffer sb = new StringBuffer("");
		for (String pkv : pkvs) {
			sb.append(pkv).append("|");
		}
		if (sb.length() > 1)
			return sb.substring(0, sb.length() - 1);
		return sb.toString();
	}
}
