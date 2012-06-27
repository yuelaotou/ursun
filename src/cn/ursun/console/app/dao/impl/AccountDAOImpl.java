package cn.ursun.console.app.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.springframework.jdbc.core.RowMapper;

import cn.ursun.console.app.console.user.util.XmlUtils;
import cn.ursun.console.app.dao.AccountDAO;
import cn.ursun.console.app.domain.Account;
import cn.ursun.console.app.domain.UserAccount;
import cn.ursun.platform.core.exception.BizException;
import cn.ursun.platform.core.jdbc.DAOUtils;
import cn.ursun.platform.core.jdbc.WeeJdbcDAO;

/**
 * <p>
 * 帐户管理数据操作
 * </p>
 * 
 * @author 李志伟 - li.zhw@neusoft.com
 * @version 1.0
 */
public class AccountDAOImpl extends WeeJdbcDAO implements AccountDAO {

	/**
	 * 插入帐户信息
	 */
	private static final String INSERT_ACCOUNT_INFO = "INSERT INTO WEE_ORG_ACCOUNT (ACCOUNT_ID,USER_ID,USERNAME,PASSWORD,DESCRIPTION"
			+ " ,ENABLED,REGISTE_DATE ) VALUES (?,?,?,?,?,?,sysdate)";

	/**
	 * 查询帐户信息
	 */
	private static final String QUERY_ACCOUNT_INFO = "SELECT U.USER_ID UUID,"
			+ "    U.FULL_NAME   UFN," + "    U.SEX         US,"
			+ "    U.TEL         UT," + "    U.EMAIL       UE,"
			+ "    U.DESCRIPTION UD," + "    A.ACCOUNT_ID  AA,"
			+ "    A.USERNAME    AU," + "    A.PASSWORD    AP,"
			+ "    A.DESCRIPTION AD," + "    A.QUESTION    AQ,"
			+ "    A.ANSWER      AN, E.EXTENDINFO EI"
			+ " FROM WEE_ORG_USER  U," + " WEE_ORG_ACCOUNT A,"
			+ " WEE_ORG_USER_EXTEND E" + " WHERE U.USER_ID = A.USER_ID"
			+ " AND U.USER_ID = ?" + " AND E.USER_ID(+)=U.USER_ID";

	/**
	 * 查询帐户信息common
	 */
	private static final String QUERY_ACCOUNT_INFO_COMMON = "SELECT U.USER_ID UUID,"
			+ "    U.FULL_NAME   UFN,"
			+ "    U.SEX         US,"
			+ "    U.TEL         UT,"
			+ "    U.EMAIL       UE,"
			+ "    U.DESCRIPTION UD,"
			+ "    A.ACCOUNT_ID  AA,"
			+ "    A.USERNAME    AU,"
			+ "    A.PASSWORD    AP,"
			+ "    A.DESCRIPTION AD,"
			+ "    A.QUESTION    AQ,"
			+ "    A.ANSWER      AN, E.EXTENDINFO EI "
			+ " FROM WEE_ORG_USER  U,"
			+ " WEE_ORG_ACCOUNT A ,"
			+ " WEE_ORG_USER_EXTEND E" + " WHERE U.USER_ID = A.USER_ID";

	/**
	 * 查询帐户信息
	 */
	private static final String QUERY_ACCOUNT_INFO_BY_USERNAME = QUERY_ACCOUNT_INFO_COMMON
			+ " AND A.USERNAME = ?" + " AND E.USER_ID(+)=U.USER_ID";

	/**
	 * 更新帐户信息（包括密码）
	 */
	private static final String UPDATE_ACCOUNT_INFO = "  UPDATE WEE_ORG_ACCOUNT  SET   USERNAME=?,PASSWORD=? ,DESCRIPTION=?"
			+ " WHERE USER_ID=?";

	/**
	 * 更新帐户信息（不包括密码）
	 */
	private static final String UPDATE_ACCOUNT_INFO_NO_PASSWORD = "  UPDATE WEE_ORG_ACCOUNT  SET   USERNAME=? ,DESCRIPTION=?"
			+ " WHERE USER_ID=?";

	/**
	 * 启用帐户
	 */
	private static final String UPDATE_ACCOUNT_ABLE = "  UPDATE WEE_ORG_ACCOUNT  SET  ENABLED=1 WHERE USER_ID=?";

	/**
	 * 禁用帐户
	 */
	private static final String UPDATE_ACCOUNT_DISABLE = "  UPDATE WEE_ORG_ACCOUNT  SET  ENABLED=0 WHERE USER_ID=?";

	/**
	 * 通过用户名查询帐户信息
	 */
	private static final String SQL_QUERY_ACCOUNT_BY_USERNAME = "SELECT ACCOUNT_ID, USER_ID, USERNAME, PASSWORD, ENABLED, REGISTE_DATE, EXPIRED_DATE, DESCRIPTION, LOCKED FROM WEE_ORG_ACCOUNT WHERE UPPER(USERNAME) = UPPER(?)";

	/**
	 * 通过用户名和帐户ID查询帐户，用于检查用户名是否存在
	 */
	private static final String SQL_QUERY_COUNT_OF_ACCOUNT = "SELECT COUNT(1) FROM WEE_ORG_ACCOUNT WHERE lower(USERNAME) = ? AND ACCOUNT_ID <> ?";

	/**
	 * 修改密码
	 */
	private static final String SQL_UPDATE_PASSWORD = "UPDATE WEE_ORG_ACCOUNT SET PASSWORD=? WHERE ACCOUNT_ID=?";

	/**
	 * 通过帐户ID查询帐户信息
	 */
	private static final String QUERY_ACCOUNT_BY_ID = "SELECT * FROM WEE_ORG_ACCOUNT WHERE ACCOUNT_ID=?";

	/**
	 * 通过帐户ID和密码查询帐户信息,用于校验密码
	 */
	private static final String QUERY_COUNT_OF_ACCOUNT_BY_ID_AND_PASSWORD = "SELECT COUNT(1) FROM WEE_ORG_ACCOUNT WHERE ACCOUNT_ID=? AND PASSWORD=?";

	/**
	 * 通过帐户ID查询帐户,用于校验是否重名
	 */
	private static final String QUERY_COUNT_OF_ACCOUNT_BY_ID = "SELECT COUNT(1) FROM WEE_ORG_ACCOUNT WHERE ACCOUNT_ID = ?";

	/**
	 * 通过用户ID查询帐户,用于校验是否重名
	 */
	private static final String QUERY_COUNT_OF_ACCOUNT_BY_USER_ID = "SELECT COUNT(1) FROM WEE_ORG_ACCOUNT WHERE USER_ID=?";

	/**
	 * 锁定帐户
	 */
	private static final String UPDATE_ACCOUNT_LOCKED = "UPDATE WEE_ORG_ACCOUNT SET LOCKED = ? WHERE ACCOUNT_ID = ?";

	/**
	 * 更新用户过期时间
	 */
	private static final String UPDATE_ACCOUNT_EXPIRED_DATE = "UPDATE WEE_ORG_ACCOUNT SET EXPIRED_DATE = ? WHERE ACCOUNT_ID = ?";

	private class UserAccountRowMapper implements RowMapper {

		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			UserAccount userAccount = new UserAccount();
			userAccount.getUser().setId(rs.getString("UUID"));
			userAccount.getUser().setFullName(rs.getString("UFN"));
			userAccount.getUser().setTel(rs.getString("UT"));
			userAccount.getUser().setSex(rs.getString("US"));
			userAccount.getUser().setEmail(rs.getString("UE"));
			userAccount.getUser().setDescription(rs.getString("UD"));
			userAccount.getUser().setExtendInfo(
					XmlUtils.parseXmlToMap(getLobHandler().getClobAsString(rs,
							"EI")));
			userAccount.getAccount().setId(rs.getString("AA"));
			userAccount.getAccount().setUsername(rs.getString("AU"));
			userAccount.getAccount().setPassword(rs.getString("AP"));
			userAccount.getAccount().setDescription(rs.getString("AD"));
			userAccount.getAccount().setQuestion(rs.getString("AQ"));
			userAccount.getAccount().setAnswer(rs.getString("AN"));
			return userAccount;
		}
	}

	private static class AccountRowMapper implements RowMapper {

		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			Account account = new Account();
			account.setId(rs.getString("ACCOUNT_ID"));
			account.setUserId(rs.getString("USER_ID"));
			account.setUsername(rs.getString("USERNAME"));
			account.setDescription(rs.getString("DESCRIPTION"));
			account.setEnabled(DAOUtils.convertString2Boolean(rs
					.getString("ENABLED")));
			account.setExpiredDate(rs.getDate("EXPIRED_DATE"));
			account.setLocked(DAOUtils.convertString2Boolean(rs
					.getString("LOCKED")));
			account.setPassword(rs.getString("PASSWORD"));
			account.setRegisterDate(rs.getDate("REGISTE_DATE"));
			return account;
		}
	}

	/**
	 * <p>
	 * 根据accountId查询用户账户信息
	 * </p>
	 * 
	 * @param accountId
	 *            账户ID
	 * @return
	 * @throws BizException
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-23 下午08:02:22
	 */
	public Account queryAccountById(String accountId) throws BizException {
		return (Account) queryForObject(QUERY_ACCOUNT_BY_ID,
				new Object[] { accountId }, new AccountRowMapper());
	}

	/**
	 * <p>
	 * 根据userId查询用户账户信息
	 * </p>
	 * 
	 * @param userId
	 *            用户ID
	 * @return
	 * @throws BizException
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-23 下午08:02:22
	 */
	public UserAccount queryUserAccountByUserId(String userId)
			throws BizException {
		return (UserAccount) queryForObject(QUERY_ACCOUNT_INFO,
				new Object[] { userId }, new UserAccountRowMapper());
	}

	/**
	 * <p>
	 * 根据userName查询用户账户信息
	 * </p>
	 * 
	 * @param userName
	 *            用户名
	 * @return
	 * @throws BizException
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-23 下午08:02:22
	 */
	public UserAccount queryUserAccountByUserName(String userName)
			throws BizException {
		return (UserAccount) queryForObject(QUERY_ACCOUNT_INFO_BY_USERNAME,
				new Object[] { userName }, new UserAccountRowMapper());
	}

	/**
	 * <p>
	 * 根据userName查询用户账户信息
	 * </p>
	 * 20100519 wangmin 增加模糊查询
	 * 
	 * @param userName
	 *            用户名
	 * @return
	 * @throws BizException
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-23 下午08:02:22
	 */
	public List<UserAccount> queryUserAccountListByUserName(String userName)
			throws BizException {
		String sql = QUERY_ACCOUNT_INFO_COMMON
				+ " AND UPPER(A.USERNAME) like '%" + userName.toUpperCase()
				+ "%' AND E.USER_ID(+)=U.USER_ID";
		return this.query(sql, new UserAccountRowMapper());
	}

	/**
	 * <p>
	 * 新增用户账户
	 * </p>
	 * 
	 * @param account
	 *            需要添加的帐户信息。
	 * @throws BizException
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-23 下午08:05:00
	 */
	public void createAccount(UserAccount useraccount) throws BizException {
		this.update(INSERT_ACCOUNT_INFO, new Object[] {
				useraccount.getAccount().getAccountId(),
				useraccount.getUser().getId(),
				useraccount.getAccount().getUsername(),
				useraccount.getAccount().getPassword(),
				useraccount.getAccount().getDescription(), "1" });
	}

	/**
	 * <p>
	 * 新增用户账户
	 * </p>
	 * 
	 * @param account
	 *            需要添加的帐户信息。
	 * @throws BizException
	 * @author: 张猛 - zhang_meng@neusoft.com
	 * @data: Create on 2009年12月11日10:54:31
	 */
	public void updateAccount(UserAccount useraccount) throws BizException {
		String sql = null;
		Object[] params = null;
		if (StringUtils.isEmpty(useraccount.getAccount().getPassword())) {
			sql = UPDATE_ACCOUNT_INFO_NO_PASSWORD;
			params = new Object[] { useraccount.getAccount().getUsername(),
					useraccount.getAccount().getDescription(),
					useraccount.getUser().getId() };
		} else {
			sql = UPDATE_ACCOUNT_INFO;
			params = new Object[] { useraccount.getAccount().getUsername(),
					useraccount.getAccount().getPassword(),
					useraccount.getAccount().getDescription(),
					useraccount.getUser().getId() };
		}
		this.update(sql, params);
	}

	/**
	 * <p>
	 * 验证密码是否正确
	 * </p>
	 * 
	 * @param accountId
	 * @param pwd
	 * @return
	 * @throws BizException
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-23 下午08:05:57
	 */
	public boolean validatePwd(String accountId, String pwd)
			throws BizException {
		if (accountId == null || pwd == null) {
			return false;
		}
		return 0 != queryForInt(QUERY_COUNT_OF_ACCOUNT_BY_ID_AND_PASSWORD,
				new Object[] { accountId, pwd });
	}

	/**
	 * <p>
	 * 修改用户密码
	 * </p>
	 * 
	 * @param accountId
	 *            账户ID
	 * @param newPwd
	 *            新密码
	 * @return
	 * @throws BizException
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-23 下午08:06:14
	 */
	public boolean updatePassword(String accountId, String newPwd)
			throws BizException {
		this.update(SQL_UPDATE_PASSWORD, new Object[] { newPwd, accountId });
		return true;
	}

	/**
	 * <p>
	 * 是否帐户存在。
	 * </p>
	 * 
	 * @param accountId
	 *            帐户Id。
	 * @return
	 * @throws BizException
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @date: Created on Sep 6, 2008 12:28:48 PM
	 */
	public boolean isAccountExistedById(String accountId) throws BizException {
		if (accountId == null) {
			return false;
		}
		return 0 != queryForInt(QUERY_COUNT_OF_ACCOUNT_BY_ID,
				new Object[] { accountId });
	}

	/**
	 * <p>
	 * 是否帐户存在。
	 * </p>
	 * 
	 * @param userId
	 *            用户id。
	 * @return
	 * @throws BizException
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @date: Created on Sep 6, 2008 12:28:54 PM
	 */
	public boolean isAccountExistedByUserId(String userId) throws BizException {
		if (userId == null)
			return false;
		return 0 != queryForInt(QUERY_COUNT_OF_ACCOUNT_BY_USER_ID,
				new Object[] { userId });
	}

	/**
	 * <p>
	 * 检查账户是否存在
	 * </p>
	 * 
	 * @param username
	 *            用户名
	 * @return
	 * @throws BizException
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @data: Create on 2009-11-23 下午08:04:39
	 */
	public boolean isAccountExistedByUsername(String username, String accountId)
			throws BizException {
		if (StringUtils.isEmpty(username) || StringUtils.isEmpty(accountId)) {
			return false;
		}
		return 0 != queryForInt(SQL_QUERY_COUNT_OF_ACCOUNT, new Object[] {
				username.toLowerCase(), accountId });
	}

	/**
	 * <p>
	 * 使帐户无效
	 * </p>
	 * 
	 * @param accountId
	 *            需要管理的帐户Id。
	 * @throws BizException
	 *             如果帐户Id不存在或已经被删除。
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @date: Created on Sep 2, 2008 7:34:53 PM
	 */
	public void disableAccount(String[] accountId) throws BizException {
		ArrayList<Object[]> params = new ArrayList<Object[]>();
		for (String id : accountId) {
			params.add(new Object[] { id });
		}
		this.batchUpdate(UPDATE_ACCOUNT_DISABLE, params);
	}

	/**
	 * <p>
	 * 使帐户有效
	 * </p>
	 * 
	 * @param accountId
	 *            需要管理的帐户Id。
	 * @throws BizException
	 *             如果帐户Id不存在或已经被删除。
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @date: Created on Sep 2, 2008 7:34:53 PM
	 */
	public void enableAccount(String[] accountId) throws BizException {
		ArrayList<Object[]> params = new ArrayList<Object[]>();
		for (String id : accountId) {
			params.add(new Object[] { id });
		}
		this.batchUpdate(UPDATE_ACCOUNT_ABLE, params);
	}

	/**
	 * <p>
	 * 锁定帐户
	 * </p>
	 * 
	 * @param accountId
	 *            需要管理的帐户Id。
	 * @param locked
	 *            是否锁定。
	 * @throws BizException
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @date: Created on Sep 4, 2008 10:15:59 AM
	 */
	public void lockAccount(String accountId, boolean locked)
			throws BizException {
		update(UPDATE_ACCOUNT_LOCKED, new Object[] { new Boolean(locked),
				accountId });
	}

	/**
	 * <p>
	 * 设置帐户过期时间
	 * </p>
	 * 
	 * @param accountId
	 *            帐户Id。
	 * @param expiredDate
	 *            过期时间。
	 * @throws BizException
	 * @author: 宋成山 - songchengshan@neusoft.com
	 * @date: Created on Sep 4, 2008 10:16:03 AM
	 */
	public void assignExpiredDate(String accountId, Date expiredDate)
			throws BizException {
		update(UPDATE_ACCOUNT_EXPIRED_DATE, new Object[] { expiredDate,
				accountId });
	}

	/**
	 * <p>
	 * 通过用户名查询帐户信息
	 * </p>
	 * 
	 * @param username
	 * @return
	 * @throws BizException
	 * @author: 李志伟 - li.zhw@neusoft.com
	 * @data: Create on Dec 25, 2009 4:03:57 PM
	 */
	public Account queryAccountByUsername(String username) throws BizException {
		Validate.notNull(username, "username required");
		return (Account) queryForObject(SQL_QUERY_ACCOUNT_BY_USERNAME,
				new Object[] { username }, new AccountRowMapper());
	}

}
