/**
 * 文件名：SynchronizerDAO.java
 * 
 * 创建人：兰硕 - lans@neusoft.com
 * 
 * 创建时间：Oct 14, 2008 8:25:07 PM
 * 
 * 版权所有：东软集团股份有限公司
 */
package cn.ursun.platform.ps.sync.dao;

import java.util.List;

import cn.ursun.platform.core.domain.Server;
import cn.ursun.platform.ps.memorydb.dto.UpdateRecord;

/**
 * <p>[描述信息：说明类的基本功能]</p>
 *
 * @author 兰硕 - lans@neusoft.com
 * @version 1.0 Created on Oct 14, 2008 8:25:07 PM
 */
public interface MDBSyncDAO {

	public static final String DATABASE_SYNC_FLAG = "MEM_DB";

	/**
	 * <p>检查是否指定服务器的指定数据类型需要进行数据同步。</p>
	 * 
	 * @param server 指定数据库。
	 * @param contentType 指定数据类型。
	 * @return true-是;false-否。
	 * @author: 兰硕 - lans@neusoft.com
	 * @date: Created on Oct 15, 2008 3:59:46 PM
	 */
	public boolean checkNeedSynchronize(Server server, String contentType);

	/**
	 * <p>查询指定数据类型的最新更新时间</p>
	 * 
	 * @param contentType 指定数据类型。
	 * @return 最新更新时间戳。
	 * @author: 兰硕 - lans@neusoft.com
	 * @date: Created on Oct 15, 2008 4:02:00 PM
	 */
	public long queryUpdateTimestamp(String contentType);

	/**
	 * <p>查询指定数据库的指定数据类型的同步时间</p>
	 * 
	 * @param server 指定数据库。
	 * @param contentType 指定数据类型。
	 * @return 同步时间戳。
	 * @author: 兰硕 - lans@neusoft.com
	 * @date: Created on Oct 15, 2008 4:02:18 PM
	 */
	public long querySynchronizeTimestamp(Server server, String contentType);

	/**
	 * <p>修改指定数据类型的最新更新时间</p>
	 * 
	 * @param contentType 指定数据类型。
	 * @author: 兰硕 - lans@neusoft.com
	 * @date: Created on Oct 15, 2008 4:02:00 PM
	 */
	public void fixUpdateTimestamp(String contentType);

	/**
	 * <p>修改指定数据库的指定数据类型的同步时间</p>
	 * 
	 * @param server 指定数据库。
	 * @param contentType 指定数据类型。
	 * @author: 兰硕 - lans@neusoft.com
	 * @date: Created on Oct 15, 2008 4:02:18 PM
	 */
	public void fixSynchronizeTimestamp(Server server, String contentType);

	/**
	 * <p>添加增量的更新记录</p>
	 * 
	 * @param records
	 * @author: 段鹏 - duanp@neusoft.com 
	 * @date: Created on 2009-6-24 下午04:29:02
	 */
	public void insertUpdateRecords(List<UpdateRecord> recordsr, String contentType);

	/**
	 * <p>查询当前服务器需要更新的数据列表</p>
	 * 
	 * @param server
	 * @return
	 * @author: 段鹏 - duanp@neusoft.com 
	 * @date: Created on 2009-6-29 上午11:56:44
	 */
	public List<UpdateRecord> getUpdateSqlList(Server serverr, String contentType);

}
