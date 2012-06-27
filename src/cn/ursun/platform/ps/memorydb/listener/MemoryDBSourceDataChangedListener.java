/**
 * 文件名：MemoryDBSourceDataChangedListener.java
 * 
 * 创建人：兰硕 - lans@neusoft.com
 * 
 * 创建时间：Oct 16, 2008 7:10:11 PM
 * 
 * 版权所有：东软集团股份有限公司
 */
package cn.ursun.platform.ps.memorydb.listener;

import java.util.List;

import cn.ursun.platform.core.event.AbstractWeeAyncEventListener;
import cn.ursun.platform.core.event.WeeEvent;
import cn.ursun.platform.ps.sync.dao.MDBSyncDAO;
import cn.ursun.platform.ps.memorydb.dto.UpdateRecord;
import cn.ursun.platform.ps.memorydb.dto.WeeEventSource;

/**
 * <p>[描述信息：说明类的基本功能]</p>
 *
 * @author 兰硕 - lans@neusoft.com
 * @version 1.0 Created on Oct 16, 2008 7:10:11 PM
 */
public abstract class MemoryDBSourceDataChangedListener extends AbstractWeeAyncEventListener {

	private MDBSyncDAO synchronizerDAO = null;

	public void setSynchronizerDAO(MDBSyncDAO synchronizerDAO) {
		this.synchronizerDAO = synchronizerDAO;
	}

	public void concern(WeeEvent event) {
		WeeEventSource source = (WeeEventSource) event.getSource();
		// 修改 contentType 的更新时间
		synchronizerDAO.fixUpdateTimestamp(MDBSyncDAO.DATABASE_SYNC_FLAG);
		// 添加更新的记录值
		List<UpdateRecord> records = source.getRecords();
		synchronizerDAO.insertUpdateRecords(records, MDBSyncDAO.DATABASE_SYNC_FLAG);
	}
}
