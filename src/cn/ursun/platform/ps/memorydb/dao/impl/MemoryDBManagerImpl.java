/**
 * 文件名：MemoryDBManagerImpl.java
 * 
 * 创建人：兰硕 - lans@neusoft.com
 * 
 * 创建时间：Sep 20, 2008 1:11:40 PM
 * 
 * 版权所有：东软集团股份有限公司
 */
package cn.ursun.platform.ps.memorydb.dao.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.core.io.Resource;

import cn.ursun.platform.core.exception.DBException;
import cn.ursun.platform.ps.memorydb.dao.AbstractDatabaseManager;

/**
 * <p>[描述信息：说明类的基本功能]</p>
 *
 * @author 兰硕 - lans@neusoft.com
 * @version 1.0 Created on Sep 20, 2008 1:11:40 PM
 */
public class MemoryDBManagerImpl extends AbstractDatabaseManager {

	private List<String> initSqlList = null;

	private List<Resource> scriptResources = null;

	public void setScriptResources(List<Resource> scriptResources) {
		this.scriptResources = scriptResources;
	}

	private void executeInitSql() {
		if (initSqlList == null)
			return;
		for (String sql : initSqlList) {
			if (sql == null)
				continue;
			if (sql.endsWith(";"))
				sql = sql.substring(0, sql.length() - 1);
			try {
				update(sql);
			} catch (DBException e) {
				if (sql.toLowerCase().startsWith("drop"))
					log.warn("MEMORY_DB: table not exist, drop failed; SQL [" + sql + "]");
				else
					log.warn("MEMORY_DB: sql execute failed; SQL [" + sql + "]",e);
			}
		}
	}

	protected void doInit() {
		initSqlList = readInitSqlList();
		executeInitSql();
	}

	protected List<String> readInitSqlList() {
		try {
			List<String> sqlList = new ArrayList<String>();
			for (Resource res : scriptResources) {
				log.info("loading memory-db-script : " + res.getFilename() + " ...");
				InputStream is = res.getInputStream();
				BufferedReader br = new BufferedReader(new InputStreamReader(is));
				String sql = "";
				String line = br.readLine();
				while (null != line) {
					if (!line.endsWith(";")) {
						sql += line + "\n";
					} else {
						sql += line;
						sqlList.add(sql);
						sql = "";
					}
					line = br.readLine();
				}
			}
			return sqlList;
		} catch (IOException e) {
			log.warn("Initialize MEMORY_DB error", e);
			return new ArrayList<String>();
		}
	}
}
