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
import java.util.Date;

import org.apache.commons.lang.Validate;
import org.springframework.jdbc.core.RowMapper;

import cn.ursun.platform.core.domain.Server;
import cn.ursun.platform.core.jdbc.WeeJdbcDAO;
import cn.ursun.platform.ps.sync.dao.SynchronizerDAO;

/**
 * <p>[描述信息：说明类的基本功能]</p>
 *
 * @author 兰硕 - lans@neusoft.com
 * @version 1.0 Created on Oct 14, 2008 8:28:26 PM
 */
public class SynchronizerDAOImpl extends WeeJdbcDAO implements SynchronizerDAO {

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
}
