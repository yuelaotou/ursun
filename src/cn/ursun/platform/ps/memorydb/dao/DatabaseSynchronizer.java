/**
 * 文件名：MemoryDBSynchronizer.java
 * 
 * 创建人：兰硕 - lans@neusoft.com
 * 
 * 创建时间：Sep 20, 2008 6:24:33 PM
 * 
 * 版权所有：东软集团股份有限公司
 */
package cn.ursun.platform.ps.memorydb.dao;

import java.util.List;

/**
 * <p>[描述信息：说明类的基本功能]</p>
 *
 * @author 兰硕 - lans@neusoft.com
 * @version 1.0 Created on Sep 20, 2008 6:24:33 PM
 */
public interface DatabaseSynchronizer {

	public void init();

	public String getSynchronizerType();

	public List<String> fetchManagedTables();

	public void synchronize(String tableName);
}
