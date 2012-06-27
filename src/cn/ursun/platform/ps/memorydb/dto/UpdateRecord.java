/**
 * 文件名：UpdateRecord.java
 * 
 * 创建人：段鹏 - duanp@neusoft.com
 * 
 * 创建时间：2009-6-23 上午11:15:30
 * 
 * 版权所有：东软集团股份有限公司
 */
package cn.ursun.platform.ps.memorydb.dto;

import java.util.Date;

import cn.ursun.platform.core.domain.WeeDomain;

/**
 * <p>[描述信息：说明类的基本功能]</p>
 *
 * @author 段鹏- duanp@neusoft.com
 * @version 1.0 Created on 2009-6-23 上午11:15:30
 */
public class UpdateRecord extends WeeDomain {

	public enum UpdateType {
		UPDATE, INSERT, DELETE
	};

	private String tableName = null;

	private String pkName = null;

	private String pkValue = null;

	private UpdateType type = null;

	private Date updateDate = null;

	/**
	 *
	 * @return 
	 */
	public String getTableName() {
		return tableName;
	}

	/**
	 *
	 * @param tableName
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	/**
	 *
	 * @return 
	 */
	public String getPkName() {
		return pkName;
	}

	/**
	 *
	 * @param pkName
	 */
	public void setPkName(String pkName) {
		this.pkName = pkName;
	}

	/**
	 *
	 * @return 
	 */
	public String getPkValue() {
		return pkValue;
	}

	/**
	 *
	 * @param pkValue
	 */
	public void setPkValue(String pkValue) {
		this.pkValue = pkValue;
	}

	/**
	 *
	 * @return 
	 */
	public UpdateType getType() {
		return type;
	}

	/**
	 *
	 * @param type
	 */
	public void setType(UpdateType type) {
		this.type = type;
	}

	/**
	 * 
	 */
	public UpdateRecord() {
		super();
	}

	/**
	 *
	 * @return 
	 */
	public Date getUpdateDate() {
		return updateDate;
	}

	/**
	 *
	 * @param updateDate
	 */
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	/**
	 * @param tableName
	 * @param pkName
	 * @param pkValue
	 * @param type
	 * @param updateDate
	 */
	public UpdateRecord(String tableName, String pkName, String pkValue, UpdateType type, Date updateDate) {
		super();
		this.tableName = tableName;
		this.pkName = pkName;
		this.pkValue = pkValue;
		this.type = type;
		this.updateDate = updateDate;
	}

}
