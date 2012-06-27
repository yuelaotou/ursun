/**
 * 文件名：SynchronizerDAOImpl.java
 * 
 * 创建人：兰硕 - lans@neusoft.com
 * 
 * 创建时间：Oct 14, 2008 8:28:26 PM
 * 
 * 版权所有：东软集团股份有限公司
 */
package cn.ursun.platform.ps.sync.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.Validate;
import org.springframework.jdbc.core.RowMapper;

import cn.ursun.platform.core.domain.Server;
import cn.ursun.platform.core.jdbc.WeeJdbcDAO;
import cn.ursun.platform.ps.sync.dao.MDBSyncDAO;
import cn.ursun.platform.core.util.IDGenerator;
import cn.ursun.platform.ps.memorydb.dto.UpdateRecord;
import cn.ursun.platform.ps.memorydb.dto.UpdateRecord.UpdateType;

/**
 * <p>[描述信息：说明类的基本功能]</p>
 *
 * @author 兰硕 - lans@neusoft.com
 * @version 1.0 Created on Oct 14, 2008 8:28:26 PM
 */
public class MDBSyncDAOImpl extends WeeJdbcDAO implements MDBSyncDAO {

	private static final String SQL_QUERY_CONTENT_TYPE_UPDATE_TIMESTAMP = "SELECT UPDATE_TIMESTAMP FROM WEE_SYNC_UPDATE_FLAG WHERE UPDATE_CONTENT_TYPE = ?";

	private static final String SQL_QUERY_SERVER_CONTENT_TYPE_SYNC_TIMESTAMP = "SELECT SYNC_TIMESTAMP FROM WEE_SYNC_SERVER_STATUS WHERE UPDATE_CONTENT_TYPE = ? AND SERVER_ID = ?";

	private static final String SQL_QUERY_SYNC_CONTENT_TYPE_EXISTENCE = "SELECT COUNT(1) FROM WEE_SYNC_SERVER_STATUS WHERE UPDATE_CONTENT_TYPE = ? AND SERVER_ID = ?";

	private static final String SQL_INSERT_SERVER_CONTENT_TYPE_SYNC_TIMESTAMP = "INSERT INTO WEE_SYNC_SERVER_STATUS (UPDATE_CONTENT_TYPE, SERVER_ID) VALUES (?, ?)";

	private static final String SQL_UPDATE_SERVER_CONTENT_TYPE_SYNC_TIMESTAMP = "UPDATE WEE_SYNC_SERVER_STATUS SET SYNC_TIMESTAMP = "
			+ "(SELECT UPDATE_TIMESTAMP FROM WEE_SYNC_UPDATE_FLAG WHERE UPDATE_CONTENT_TYPE = ?)"
			+ " WHERE UPDATE_CONTENT_TYPE = ? AND SERVER_ID = ?";

	private static final String SQL_QUERY_UPDATE_CONTENT_TYPE_EXISTENCE = "SELECT COUNT(1) FROM WEE_SYNC_UPDATE_FLAG WHERE UPDATE_CONTENT_TYPE = ?";

	private static final String SQL_UPDATE_CONTENT_TYPE_UPDATE_TIMESTAMP = "UPDATE WEE_SYNC_UPDATE_FLAG SET UPDATE_TIMESTAMP = sysdate WHERE UPDATE_CONTENT_TYPE = ?";

	private static final String SQL_INSERT_CONTENT_TYPE_UPDATE_TIMESTAMP = "INSERT INTO WEE_SYNC_UPDATE_FLAG (UPDATE_CONTENT_TYPE, UPDATE_TIMESTAMP) VALUES(?, sysdate)";

	private static final String SQL_INSERT_UPDATE_DATA = "INSERT INTO WEE_SYNC_UPDATE_DATA (ID,TABLE_NAME,PK_NAME,PK_VALUE,TYPE,UPDATE_DATE,SORT_NUM) VALUES(?,?,?,?,?,TO_DATE(?,'YYYY-MM-DD HH24:MI:SS'),?)";

	private static final String SQL_QUERY_UPDATE_DATA = "SELECT DATA.TABLE_NAME, DATA.PK_NAME, DATA.PK_VALUE , DATA.TYPE, DATA.UPDATE_DATE FROM WEE_SYNC_SERVER_STATUS SERVER, "
			+ " WEE_SYNC_UPDATE_FLAG FLAG, WEE_SYNC_UPDATE_DATA DATA"
			+ " WHERE SERVER.SERVER_ID = ? AND UPPER(SERVER.UPDATE_CONTENT_TYPE) = ? "
			+ " AND DATA.UPDATE_DATE > SERVER.SYNC_TIMESTAMP AND DATA.UPDATE_DATE <= FLAG.UPDATE_TIMESTAMP ";

	public boolean checkNeedSynchronize(Server server, String contentType) {
		long sync = querySynchronizeTimestamp(server, contentType);
		long update = queryUpdateTimestamp(contentType);
		if (sync < update)
			return true;
		return false;
	}

	public long querySynchronizeTimestamp(Server server, String contentType) {
		Validate.notNull(server, "server required");
		Validate.notNull(server.getServerId(), "the property[serverId] of server required");
		Validate.notNull(contentType, "contentType required");
		Date d = (Date) queryForObject(SQL_QUERY_SERVER_CONTENT_TYPE_SYNC_TIMESTAMP, new Object[] { contentType,
				server.getServerId() }, new RowMapper() {

			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				Date dd = rs.getTimestamp("SYNC_TIMESTAMP");
				return dd;
			}
		});
		if (d != null)
			return d.getTime();
		return 0;
	}

	public long queryUpdateTimestamp(String contentType) {
		Validate.notNull(contentType, "contentType required");
		Date d = (Date) queryForObject(SQL_QUERY_CONTENT_TYPE_UPDATE_TIMESTAMP, new Object[] { contentType },
				new RowMapper() {

					public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
						Date dd = rs.getTimestamp("UPDATE_TIMESTAMP");
						return dd;
					}
				});
		if (d != null)
			return d.getTime();
		return 0;
	}

	public void fixSynchronizeTimestamp(Server server, String contentType) {
		Validate.notNull(server, "server required");
		Validate.notNull(server.getServerId(), "the property[serverId] of server required");
		Validate.notNull(contentType, "contentType required");
		int i = queryForInt(SQL_QUERY_SYNC_CONTENT_TYPE_EXISTENCE, new Object[] { contentType, server.getServerId() });
		if (i == 0) {
			update(SQL_INSERT_SERVER_CONTENT_TYPE_SYNC_TIMESTAMP, new Object[] { contentType, server.getServerId() });
		}
		update(SQL_UPDATE_SERVER_CONTENT_TYPE_SYNC_TIMESTAMP, new Object[] { contentType, contentType,
				server.getServerId() });
	}

	public void fixUpdateTimestamp(String contentType) {
		Validate.notNull(contentType, "contentType required");
		int i = queryForInt(SQL_QUERY_UPDATE_CONTENT_TYPE_EXISTENCE, new Object[] { contentType });
		if (i == 0) {
			update(SQL_INSERT_CONTENT_TYPE_UPDATE_TIMESTAMP, new Object[] { contentType });
		} else {
			update(SQL_UPDATE_CONTENT_TYPE_UPDATE_TIMESTAMP, new Object[] { contentType });
		}
	}

	public void insertUpdateRecords(List<UpdateRecord> records, String contentType) {
		List<Object[]> params = new ArrayList<Object[]>();
		Iterator<UpdateRecord> it = records.iterator();
		Date d = (Date) queryForObject(SQL_QUERY_CONTENT_TYPE_UPDATE_TIMESTAMP, new Object[] { contentType },
				new RowMapper() {

					public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
						Date dd = rs.getTimestamp("UPDATE_TIMESTAMP");
						return dd;
					}
				});
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = sdf.format(d);
		int i = 0;
		while (it.hasNext()) {
			UpdateRecord ur = it.next();
			params.add(new Object[] { IDGenerator.generateId(), ur.getTableName(), ur.getPkName(), ur.getPkValue(),
					ur.getType().name(), date, i++ });
		}
		if (params.size() > 0)
			this.batchUpdate(SQL_INSERT_UPDATE_DATA, params);
	}

	public List<UpdateRecord> getUpdateSqlList(Server server, String updateType) {
		Validate.notNull(server, "server required");
		List<UpdateRecord> list = this.query(SQL_QUERY_UPDATE_DATA
				+ " ORDER BY DATA.UPDATE_DATE ASC, DATA.SORT_NUM ASC ",
				new Object[] { server.getServerId(), updateType }, new RowMapper() {

					public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
						UpdateRecord record = new UpdateRecord();
						record.setTableName(rs.getString("TABLE_NAME"));
						record.setPkName(rs.getString("PK_NAME"));
						record.setPkValue(rs.getString("PK_VALUE"));
						record.setType(UpdateType.valueOf(rs.getString("TYPE")));
						record.setUpdateDate(rs.getDate("UPDATE_DATE"));
						return record;
					}
				});
		return list;
	}
}
